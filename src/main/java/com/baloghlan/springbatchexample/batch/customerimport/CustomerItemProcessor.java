package com.baloghlan.springbatchexample.batch.customerimport;

import com.baloghlan.springbatchexample.entity.Customer;
import com.baloghlan.springbatchexample.model.CustomerCsvRecord;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;

public class CustomerItemProcessor implements ItemProcessor<CustomerCsvRecord, Customer> {
    @Override
    public Customer process(@NonNull CustomerCsvRecord customerCsvRecord) throws Exception {
        // TODO: Business code.
        // If there will be no business code there is no need to implement the component

        return convertToCustomer(customerCsvRecord);
    }
    public Customer convertToCustomer(CustomerCsvRecord record) {
        return Customer.builder()
                .firstName(record.getFirstName())
                .lastName(record.getLastName())
                .country(record.getCountry())
                .streetAddress(record.getStreetAddress())
                .postalCode(record.getPostalCode())
                .email(record.getEmail())
                .city(record.getCity())
                .build();
    }
}
