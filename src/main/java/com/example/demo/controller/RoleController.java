package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.Role;
import com.example.demo.dto.request.AggregateRequest;
import com.example.demo.dto.request.ApiResponse;
import com.example.demo.dto.response.AggregateRespones;
import com.example.demo.dto.response.UploadFileResponse;
import com.example.demo.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
    RoleService roleService;

    @PostMapping("/role")
    public ApiResponse<AggregateRespones<Role>> findAll(@RequestBody AggregateRequest request) {
        String currentDirectory = System.getProperty("user.dir");
        var pathFolder= currentDirectory + "/src/main/java/com/example/demo";
        File folder= new File(pathFolder + "/upload");
        File fileTXT=  new File(pathFolder + "/test.txt");
        // if(!folder.exists()){
        //     folder.mkdir();
        // }

        // // kiểm tra xem folder có folder con hay không
        // if(folder.isDirectory()){
        //   String[]  files=   folder.list();
        //   for(String file: files){
        //     System.out.println("file"+ file);
        //   }
        // }else{
        //     System.out.println("folder empty:");
        // }
        
        // // xóa folder
        // if(folder.exists()){
        //     folder.delete();
        // }


        try {
            if(fileTXT.createNewFile()){
                System.out.println("file txt được  tạo:" + fileTXT.getName());
            }else{
                System.out.println("file đã tồn tại.");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // ghi dữ liệu vào file
        try (FileWriter fileWriter = new FileWriter(pathFolder+ "/test.txt")){
            // FileWriter fileWriter = new FileWriter(pathFolder+ "/test.txt");
            fileWriter.write("hello this is message");
        } catch (IOException e) {
            System.out.println("error:"+ e);
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       

        // dọc  dữ liệu từ  file

        try(BufferedReader  fileRead= new BufferedReader(new FileReader(pathFolder+ "/test.txt"))){
            String line;
            while ((line = fileRead.readLine()) !=null) {
                System.out.println("read file:"+ line);
            }

        }catch(IOException e){
            e.printStackTrace();
        }


        // xoas file
        // eTXT.delete();
        // }


    Path path = Paths.get(pathFolder + "/test.txt");
    
        return ApiResponse.<AggregateRespones<Role>>builder()
                .result(roleService.customAggregateRole(request))
                .build();
    }

    // @PostMapping("/role")
    // public ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest entity)
    // {

    // retif(fileTXT.exists()){
        //     filurn ApiResponse.<RoleResponse>builder()
    // .result(roleService.create(entity))
    // .build();
    // }

    @DeleteMapping("/role/{name}")
    public ApiResponse<Void> deleteRole(@PathVariable String name) {
        return ApiResponse.<Void>builder()
                // .result(roleService.delete(name))
                .build();
    }


    @PostMapping("/role/upload")
    public List<UploadFileResponse> UploadImage(@RequestParam("file") List<MultipartFile> file) {
       return  roleService.uploadImage(file);
    }
    

}
