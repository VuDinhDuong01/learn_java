package com.example.demo.service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.example.demo.dto.response.UploadFileResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UploadFileService {
    Cloudinary cloudinary;
    private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

    public List<UploadFileResponse> uploadImage(List<MultipartFile> file) {
        Path staticPath = Paths.get("static");
        Path imagesPath = Paths.get("upload");
        var checkFolderExists = Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagesPath));
        if (!checkFolderExists) {
            try {
                Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagesPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Path fileImage;
        // Path fileImage =
        // CURRENT_FOLDER.resolve(staticPath).resolve(imagesPath).resolve(file.getOriginalFilename());
        // dùng để ghi đè nếu upload file trùng filename
        for (MultipartFile f : file) {
            // Tạo đường dẫn cho file
            fileImage = CURRENT_FOLDER.resolve(staticPath).resolve(imagesPath).resolve(f.getOriginalFilename());
            try (OutputStream os = Files.newOutputStream(fileImage)) {
                os.write(f.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Map<String, Object> params = new HashMap<>();
            params.put("folder", "upload_file");
            List<UploadFileResponse> uploadedImages = new ArrayList<>();
            for (MultipartFile f : file) {
                Map data = cloudinary.uploader().upload(f.getBytes(), params);
                uploadedImages.add(UploadFileResponse.builder()
                        .resource_type((String) data.get("resource_type"))
                        .secure_url((String) data.get("secure_url"))
                        .build());
            }
            return uploadedImages;

        } catch (IOException e) {
            System.out.println("upload f");
            throw new RuntimeException("Image upload fail");
        }

    }
}
