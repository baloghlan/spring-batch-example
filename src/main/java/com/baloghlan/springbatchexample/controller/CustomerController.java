package com.baloghlan.springbatchexample.controller;

import com.baloghlan.springbatchexample.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/public/customers/import")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<HttpStatus> importCustomers(@RequestPart MultipartFile file) {
        customerService.importCustomers(file);
        return ResponseEntity.ok().build();
    }
}
