// package com.example.demo.controller;

// import java.io.IOException;
// import java.security.InvalidKeyException;
// import java.security.NoSuchAlgorithmException;
// import java.util.List;

// import org.springframework.core.io.ByteArrayResource;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Controller;

// import lombok.AccessLevel;
// import lombok.Data;
// import lombok.experimental.FieldDefaults;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RequestPart;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.multipart.MultipartFile;

// import com.example.demo.service.UploadFileMinio;

// import io.minio.errors.ErrorResponseException;
// import io.minio.errors.InsufficientDataException;
// import io.minio.errors.InternalException;
// import io.minio.errors.InvalidResponseException;
// import io.minio.errors.ServerException;
// import io.minio.errors.XmlParserException;
// import io.minio.messages.Bucket;

// @Controller
// @Data
// @RestController
// @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
// @RequestMapping("/api/v1")
// public class UploadFileMinioController {
//     UploadFileMinio uploadFileMinio;

//     @GetMapping(path = "/buckets")
//     public List<Bucket> listBuckets() {
//         return uploadFileMinio.getAllBuckets();
//     }

//       @PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//     public String uploadFile(@RequestPart(value = "file", required = false) MultipartFile files) throws IOException, InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, XmlParserException, ServerException, IllegalArgumentException {
//        return  uploadFileMinio.uploadFile(files);
//     }

//      @GetMapping(path = "/download")
//     public ResponseEntity<ByteArrayResource> uploadFile(@RequestParam(value = "file") String file) throws IOException {
//         byte[] data = uploadFileMinio.getFile(file);
//         ByteArrayResource resource = new ByteArrayResource(data);

//         return ResponseEntity
//                 .ok()
//                 .contentLength(data.length)
//                 .header("Content-type", "application/octet-stream")
//                 .header("Content-disposition", "attachment; filename=\"" + file + "\"")
//                 .body(resource);
//     }

// }
