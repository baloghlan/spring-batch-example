package com.baloghlan.springbatchexample.batch.customerimport;

import com.baloghlan.springbatchexample.entity.Customer;
import com.baloghlan.springbatchexample.model.CustomerCsvRecord;
import com.baloghlan.springbatchexample.repository.CustomerRepository;
import com.baloghlan.springbatchexample.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.FileInputStream;

@Configuration
@RequiredArgsConstructor
public class CustomerCsvImportJobConfig {
    private final CustomerRepository customerRepository;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final FileService fileService;


    @Bean
    @StepScope
    @SneakyThrows
    public FlatFileItemReader<CustomerCsvRecord> customerFlatFileItemReader(@Value("#{jobParameters[filename]}") String filename) {
        FlatFileItemReader<CustomerCsvRecord> reader = new FlatFileItemReader<>();
        reader.setResource(new InputStreamResource(new FileInputStream(fileService.getFileByFilename(filename))));
        reader.setName("Customer-CSV-Reader");
        reader.setLinesToSkip(1);
        reader.setLineMapper(lineMapper());
        return reader;
    }

    private LineMapper<CustomerCsvRecord> lineMapper() {
        DefaultLineMapper<CustomerCsvRecord> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("first_name", "last_name", "email", "street_address", "city", "country", "postal_code");

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(new CustomerFieldMapper());

        return lineMapper;
    }

    @Bean("customerCsvImportStep")
    @JobScope
    public Step importWhatsappContactImport(FlatFileItemReader<CustomerCsvRecord> reader, ItemProcessor<CustomerCsvRecord, Customer> processor, CustomerItemWriter writer) {
        return new StepBuilder("customerCsvImport", jobRepository)
                .<CustomerCsvRecord, Customer>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean("customerCsvImportJob")
    public Job runJob(@Qualifier("customerCsvImportStep") Step step) {
        return new JobBuilder("customerCsvImport", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(10);
        return taskExecutor;
    }

    @Bean
    @StepScope
    public CustomerItemWriter customerItemWriter() {
        return new CustomerItemWriter(customerRepository);
    }

    @Bean
    @StepScope
    public ItemProcessor<CustomerCsvRecord, Customer> customerItemProcessor() {
        return new CustomerItemProcessor();
    }
}
