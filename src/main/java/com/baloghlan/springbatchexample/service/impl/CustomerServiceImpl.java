package com.baloghlan.springbatchexample.service.impl;

import com.baloghlan.springbatchexample.service.CustomerService;
import com.baloghlan.springbatchexample.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final JobLauncher jobLauncher;
    private final FileService fileService;
    private final Job customerCsvImportJob;

    public CustomerServiceImpl(JobLauncher jobLauncher,
                               FileService fileService,
                               @Qualifier("customerCsvImportJob") Job customerCsvImportJob) {
        this.jobLauncher = jobLauncher;
        this.fileService = fileService;
        this.customerCsvImportJob = customerCsvImportJob;
    }

    @Override
    @SneakyThrows
    public void importCustomers(MultipartFile file) {
        String filename = generateFilename(file.getOriginalFilename());

        fileService.saveFileToResourceFolder(file.getBytes(), filename);

        JobParameters jobParameters = buildJobParams(filename);

        jobLauncher.run(customerCsvImportJob, jobParameters);
    }

    private static JobParameters buildJobParams(String filename) {
        return new JobParametersBuilder()
                .addString("filename", filename)
                .toJobParameters();
    }

    private static String generateFilename(String originalName) {
        return new StringBuilder(UUID.randomUUID().toString())
                .append("-")
                .append(originalName)
                .toString();
    }
}
