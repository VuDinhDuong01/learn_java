// package com.example.demo.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// // import com.google.api.client.util.Value;

// import io.minio.MinioClient;

// @Configuration
// public class MinioConfig {
//     // @Value("${minio.access.name}")
//     // String accessKey;
//     // @Value("${minio.access.secret}")
//     // String accessSecret;
//     // @Value("${minio.url}")
//     // String minioUrl;
    
// @Bean
//     public MinioClient generateMinioClient() {
//         try {
//             MinioClient client = MinioClient.builder()
//             .endpoint("http://127.0.0.1:9000")
//             .credentials("3RjYu5O8xuAatc7RLora", "gZpfTD6UyO9qcgffylOgygZg7qpWzniKEDNUD2GR")
//             .build();
//             return client;
//         } catch (Exception e) {
//             System.out.println("error:"+  e);
//             throw new RuntimeException(e.getMessage());
//         }

//     }
// }
