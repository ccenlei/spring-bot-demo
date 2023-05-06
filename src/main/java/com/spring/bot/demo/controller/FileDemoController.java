package com.spring.bot.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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

import com.spring.bot.demo.dto.FileDemoDto;
import com.spring.bot.demo.dto.FileResDto;
import com.spring.bot.demo.service.FileDemoService;
import com.spring.bot.demo.utils.DemoUtils;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Validated
@RestController
@RequestMapping("/api/files")
@Slf4j
public class FileDemoController {

    @Autowired
    private FileDemoService demoService;

    @PostMapping("/upload")
    public ResponseEntity<FileResDto> uploadFile(@RequestParam("file") MultipartFile file) {
        FileResDto resDto;
        try {
            resDto = demoService.upload(file);
        } catch (Exception e) {
            log.error("upload file failed : ", e);
            resDto = FileResDto.builder().status(500).message("file uploads failed").build();
        }
        return ResponseEntity.status(resDto.getStatus()).body(resDto);
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<?> downloadFile(@PathVariable("filename") String filename) {
        try {
            Resource resource = demoService.download(filename);
            String contentDisposition = "attachment; filename=" + resource.getFilename();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(resource.contentLength())
                    .body(resource);
        } catch (Exception e) {
            log.error("download file failed : ", e);
            return ResponseEntity.status(500)
                    .body(FileResDto.builder().status(500).message("file downloads failed").build());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<FileDemoDto>> listFiles() {
        List<FileDemoDto> fList = demoService.list();
        return ResponseEntity.ok().body(fList);
    }

    @GetMapping("/preview")
    public void previewPdf(HttpServletResponse response, @RequestParam("filename") String filepath) throws IOException {
        String filename = DemoUtils.fileName(filepath);
        String suffix = DemoUtils.fileSuffix(filepath);
        response.setContentType(String.format("application/%s", suffix));
        response.setHeader("Content-Disposition", "inline; filename=" + DemoUtils.encode(filename));
        File file = new File(filepath);
        try (FileInputStream iStream = new FileInputStream(file); OutputStream oStream = response.getOutputStream()) {
            IOUtils.copy(iStream, oStream);
            oStream.flush();
        }
    }

}
