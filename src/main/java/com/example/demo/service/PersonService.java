package com.example.demo.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.response.ExcelRespnse;
import com.example.demo.dto.response.PersionResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class PersonService {
    public ExcelRespnse upload(MultipartFile files) {
        List<String> listError = new ArrayList<>();
        List<PersionResponse> listSuccess = new ArrayList<>();
        try (InputStream inputStream = files.getInputStream();) {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Row firstRow = sheet.getRow(0);

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);

                for(int columnIndex =0 ;columnIndex < row.getLastCellNum();columnIndex ++){
                    String name = row.getCell(columnIndex) != null ? row.getCell(columnIndex).getStringCellValue() : null;
                    String address = row.getCell(columnIndex) != null ? row.getCell(columnIndex).getStringCellValue() : null;
                    if (name == null || name.trim().isEmpty()) {
                        listError.add("Tên không được bỏ trống");
                    } 
                    else if(address ==  null || address.trim().isEmpty()){
                        
                    }
                    else {
                        listSuccess.add(PersionResponse.builder().username(name).address(address).build());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ExcelRespnse(listError, listSuccess);
    }
}
