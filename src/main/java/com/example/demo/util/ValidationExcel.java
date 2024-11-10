
package com.example.demo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

@Service
public class ValidationExcel {
    private String validationName(Cell cellName){
        if(cellName == null){
            return "Trường này không được bỏ trống";
        }else if(cellName.getCellType() != CellType.STRING){
            return "name phải nhập kiểu dữ liệu là string.";
        }
        return null;
    }


    private String validationAddress(Cell cellAddress){
        if(cellAddress == null){
            return "Trường này không được bỏ trống";
        }else if(cellAddress.getCellType() != CellType.STRING){
            return "address phải nhập kiểu dữ liệu phải là string.";
        }
        return null;
    }

    public  List<String> listErrorRow(Row row, Map<String , Integer>ExcelHeaders ){
        List<String> listError =  new ArrayList();

        Integer indexName= ExcelHeaders.get("Tên");
        Integer indexAddress= ExcelHeaders.get("Địa chỉ");
        System.out.println("cellname:"+ indexName);
        System.out.println("celladdress:"+ indexAddress);
        String cellName = this.validationName(row.getCell(indexName));
        String cellAddress = this.validationAddress(row.getCell(indexAddress));
   
        listError.add(cellName != null ? cellName : null);
        listError.add( cellAddress != null ? cellAddress : null);

        List<String> listErrorStream= listError.stream().filter(item -> item !=null).toList();

        if(listErrorStream.size() > 0){
            return listErrorStream;
        }
        return new ArrayList<String>();
    }

    public static String getCellValue(Cell cell) {
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
                return null;
        }
    }

}
