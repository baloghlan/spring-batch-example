package com.baloghlan.springbatchexample.batch.customerimport;

import com.baloghlan.springbatchexample.model.CustomerCsvRecord;
import lombok.NonNull;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class CustomerFieldMapper implements FieldSetMapper<CustomerCsvRecord> {
    @Override
    @NonNull
    public CustomerCsvRecord mapFieldSet(FieldSet fieldSet) throws BindException {
        return CustomerCsvRecord.builder()
                .lastName(fieldSet.readString("last_name"))
                .email(fieldSet.readString("email"))
                .firstName(fieldSet.readString("first_name"))
                .city(fieldSet.readString("city"))
                .country(fieldSet.readString("country"))
                .streetAddress(fieldSet.readString("street_address"))
                .postalCode(fieldSet.readString("postal_code"))
                .build();
    }
}
