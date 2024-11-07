package com.example.demo.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


import org.springframework.web.multipart.MultipartFile;

import com.example.demo.util.ErrorData;
import com.example.demo.util.ExcelValidator;
import com.example.demo.util.UserData;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Service
@Slf4j
public class PersonService {

    private final ExcelValidator validator = new ExcelValidator();
    private List<UserData> listSuccess = new ArrayList<>();
    private List<ErrorData> listError = new ArrayList<>();
    private int nameColumnIndex = -1;
    private int addressColumnIndex = -1;

    // Constructor mặc định, không cần tiêm gì nếu không có dependency nào
    public PersonService() {
    }

    // Phương thức xử lý file Excel
    public void processExcelFile(MultipartFile file) {
        listSuccess.clear();
        listError.clear();

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            // get sheet first in excel.
            Sheet sheet = workbook.getSheetAt(0);

            // Kiểm tra và lấy vị trí cột cho name và address
            Row headerRow = sheet.getRow(0);
            System.out.println("headerRow" +  headerRow.getCell(0));

            if (!isValidHeader(headerRow)) {
                listError.add(new ErrorData("1", "Tiêu đề không đúng hoặc thiếu. Cần có 'name' và 'address'.")); 
                return; // Dừng xử lý nếu tiêu đề không hợp lệ
            }

            // Duyệt qua các dòng dữ liệu từ hàng thứ 2
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {  // Bỏ qua hàng tiêu đề
                Row row = sheet.getRow(i);
                UserData data = new UserData();

                // Lấy dữ liệu từ cột name và address
                String name = getCellValue(row.getCell(nameColumnIndex));
                if (name != null && !name.isEmpty()) {
                    data.setName(name);
                } else {
                    listError.add(new ErrorData(String.valueOf(i + 1), "Tên không được để trống ở hàng " + (i + 1)));
                }

                String address = getCellValue(row.getCell(addressColumnIndex));
                if (address != null && !address.isEmpty()) {
                    data.setAddress(address);
                } else {
                    listError.add(new ErrorData(String.valueOf(i + 1), "Địa chỉ không được để trống ở hàng " + (i + 1)));
                }

                // Validate dữ liệu trong dòng
                List<ErrorData> errors = validator.validateRow(data, i);
                if (errors.isEmpty()) {
                    listSuccess.add(data);
                } else {
                    listError.addAll(errors);
                }
            }
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Phương thức kiểm tra và lấy vị trí cột name và address
    private boolean isValidHeader(Row headerRow) {
        if (headerRow == null) {
            return false;
        }
        for (Cell cell : headerRow) {
            String cellValue = cell.getStringCellValue();
            if ("Tên".equals(cellValue)) {
                nameColumnIndex = cell.getColumnIndex(); // Lưu chỉ số cột của name
            }
            if ("Địa chỉ".equals(cellValue)) {
                addressColumnIndex = cell.getColumnIndex(); // Lưu chỉ số cột của address
            }
        }
        return nameColumnIndex != -1 && addressColumnIndex != -1; // Kiểm tra cả hai cột name và address
    }

    // Phương thức lấy giá trị của ô, kiểm tra kiểu dữ liệu
    private String getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return null; // Trả về null nếu kiểu dữ liệu không xác định
        }
    }

    // Getter để lấy danh sách thành công
    public List<UserData> getListSuccess() {
        return listSuccess;
    }

    // Getter để lấy danh sách lỗi
    public List<ErrorData> getListError() {
        return listError;
    }
}
