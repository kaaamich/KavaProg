package com.example.demo.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PatchExchange;

import com.example.demo.entity.Product;
import com.example.demo.model.UrlRequest;
import com.example.demo.repository.ICoffeeRepository;
import com.example.demo.services.ExcelService;
import com.example.demo.services.WebParserService;

import org.springframework.http.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.HttpHeadResponseDecorator;
import com.example.demo.services.ExcelService;

@RestController
@RequestMapping("/list")
public class ListController {

    @Autowired
    private ICoffeeRepository coffeeRepository;

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<byte[]> getProducts() {
        ExcelService service = new ExcelService(coffeeRepository);

        service.createWorkbook();
        byte[] excelBytes = service.getWorkBookInBytes();

        return ResponseEntity.ok()
                .contentType(
                        MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=products.xlsx")
                .contentLength(excelBytes.length)
                .body(excelBytes);
    }

    @PostMapping("/")
    public ResponseEntity<String> createProductsList(@RequestBody UrlRequest request) {
        WebParserService parser = new WebParserService(request.getUrl());
        coffeeRepository.saveAll(parser.parsePage());

        return ResponseEntity.ok("List successfully added");
    }
}
