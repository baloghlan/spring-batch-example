package com.baloghlan.springbatchexample.batch.customerimport;

import com.baloghlan.springbatchexample.entity.Customer;
import com.baloghlan.springbatchexample.repository.CustomerRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

@RequiredArgsConstructor
public class CustomerItemWriter implements ItemWriter<Customer> {
    private final CustomerRepository customerRepository;

    @Override
    public void write(@NonNull Chunk<? extends Customer> chunk) throws Exception {
        if (chunk.isEmpty())
            return;

        customerRepository.saveAll(chunk.getItems());
    }
}
