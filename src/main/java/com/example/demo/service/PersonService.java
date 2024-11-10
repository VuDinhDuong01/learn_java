package com.example.demo.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.response.BaseResponse;

import com.example.demo.dto.response.PersonResponse;
import com.example.demo.util.ValidationExcel;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Data
public class PersonService {
    @Autowired
    private ValidationExcel validationExcel;

    public BaseResponse<Object> processExcelFile(MultipartFile file) {

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            // get sheet first in excel.
            Sheet sheet = workbook.getSheetAt(0);
            // Kiểm tra và lấy vị trí cột cho name và address
            Row headerRow = sheet.getRow(0);
            BaseResponse baseResponse = new BaseResponse();
            if (!isValidHeader(headerRow)) {
                String message = "Tiêu đề không đúng hoặc thiếu. Cần có 'tên' và 'Địa chỉ'.";
                baseResponse.setMessage(message);
                baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
                baseResponse.setResponse(null);
                return baseResponse;
            }

            // gắn index vào cho từng cột
            Map<String, Integer> ExcelHeaders = new HashMap<>();
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell cell = headerRow.getCell(i);
                String cellValue = cell.getStringCellValue();
                if (cellValue == null) {
                    continue;
                }
                ExcelHeaders.put(cellValue, cell.getColumnIndex());
            }

            List<PersonResponse> listResponseSuccess = new ArrayList();
            List<PersonResponse> listResponseError = new ArrayList();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);

                Integer indexAddress = ExcelHeaders.get("Địa chỉ");
                Integer indexName = ExcelHeaders.get("Tên");
              
                // lấy giá trị value của 1 cell
                Object cellValueName = ValidationExcel.getCellValue(row.getCell(indexName));
                Object cellValueAddress = ValidationExcel.getCellValue(row.getCell(indexAddress));
                PersonResponse dataResponse = new PersonResponse();

                // set dữ liệu vào
                dataResponse.setAddress(cellValueAddress);
                dataResponse.setName(cellValueName);

                List<String> listErrorRow = new ArrayList<>();
                listErrorRow = validationExcel.listErrorRow(row, ExcelHeaders);
                if (listErrorRow.size() > 0) {
                    dataResponse.setListError(listErrorRow);
                    listResponseError.add(dataResponse);
                    continue;
                }
                dataResponse.setListError(null);
                listResponseSuccess.add(dataResponse);
            }
            workbook.close();

            Map<String, Object> finalResponse  = new HashMap();
            finalResponse.put("success", listResponseSuccess);
            finalResponse.put("error",listResponseError);
            baseResponse.setResponse(finalResponse);
            return baseResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isValidHeader(Row headerRow) {
        List<String> header = new ArrayList<>();
        header.add("Tên");
        header.add("Địa chỉ");

        List<String> headerExcel = new ArrayList<>();
        for (Cell cell : headerRow) {
            if (cell.getCellType() == CellType.NUMERIC) {
                continue;
            }
            headerExcel.add(cell.getStringCellValue());
        }
        return headerExcel.containsAll(header);
    }
}


