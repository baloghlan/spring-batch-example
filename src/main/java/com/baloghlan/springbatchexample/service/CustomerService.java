package com.baloghlan.springbatchexample.service;

import org.springframework.web.multipart.MultipartFile;

public interface CustomerService {
    void importCustomers(MultipartFile file);
}
