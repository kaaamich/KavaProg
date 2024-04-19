package com.example.demo.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ICoffeeRepository;

@Service
public class ExcelService {

    private ICoffeeRepository coffeeRepository;

    private XSSFWorkbook workbook;

    private static final String[] headers = { "Code", "Title", "Link", "Description", "Price" };

    public ExcelService(ICoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    public void createWorkbook() {
        workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Products");
        int rowNum = 0;

        rowNum = setHeader(rowNum, sheet);
        setBody(rowNum, sheet);
    }

    private int setHeader(int rowNum, XSSFSheet sheet) {
        XSSFRow row = sheet.createRow(rowNum++);
        int cellNum = 0;

        for (String column : headers) {
            XSSFCell cell = row.createCell(cellNum++);
            cell.setCellValue(column);
        }

        return rowNum;
    }

    private void setBody(int rowNum, XSSFSheet sheet) {
        List<Product> pList = coffeeRepository.findAll();

        for (Product prod : pList) {
            XSSFRow row = sheet.createRow(rowNum++);
            String[] data = prod.getDataToString();

            for (int col = 0; col < data.length; col++) {
                XSSFCell cell = row.createCell(col);
                cell.setCellValue(data[col]);
            }
        }
    }

    public byte[] getWorkBookInBytes() {
        ByteArrayOutputStream byteArr = new ByteArrayOutputStream();
        try {
            workbook.write(byteArr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArr.toByteArray();
    }

}