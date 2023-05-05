package com.spring.bot.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Validated
@RestController
@RequestMapping("/api/files")
@Slf4j
public class FileDemoController {

    @Value("${demo.file.multipart.location}")
    private String location;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (ObjectUtils.isEmpty(file)) {
            return ResponseEntity.accepted().body(resMap(401, "file can not be empty"));
        }
        String filename = file.getOriginalFilename();
        File desFile = new File(location + filename);
        try {
            file.transferTo(desFile);
        } catch (IllegalStateException | IOException e) {
            log.error("upload file failed : ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .body(resMap(500, "file uploads failed"));
        }
        return ResponseEntity.accepted().body(resMap(200, "file uploads succeed"));
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<?> downloadFile(@PathVariable("filename") String filename) {
        String filePath = location + filename;
        Path file = Paths.get(filePath);
        try {
            Resource resource = new UrlResource(file.toUri());
            String contentDisposition = "attachment; filename=" + resource.getFilename();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(resource.contentLength())
                    .body(resource);
        } catch (IOException e) {
            log.error("download file failed : ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .body(resMap(500, "file downloads failed"));
        }
    }

    Map<String, Object> resMap(Integer status, String message) {
        Map<String, Object> res = new HashMap<>();
        res.put("status", status);
        res.put("message", message);
        return res;
    }
}
