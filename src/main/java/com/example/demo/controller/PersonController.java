package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.response.BaseResponse;
import com.example.demo.dto.response.ExcelFinalResponse;
import com.example.demo.dto.response.ExcelResponse;
import com.example.demo.service.PersonService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PersonController {
     
    PersonService excelService;

    @PostMapping("/upload")
    public BaseResponse<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        BaseResponse<Object> excel=  excelService.processExcelFile(file);
        return  excel;
    }
}
