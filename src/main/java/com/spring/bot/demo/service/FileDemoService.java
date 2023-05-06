package com.spring.bot.demo.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.bot.demo.dto.FileDemoDto;
import com.spring.bot.demo.dto.FileResDto;
import com.spring.bot.demo.utils.DemoUtils;

@Service
public class FileDemoService {

    @Value("${demo.file.multipart.location}")
    private String location;

    /**
     * upload file
     * 
     * @param file
     * @return
     * @throws IOException
     * @throws IllegalStateException
     */
    public FileResDto upload(MultipartFile file) throws IOException, IllegalStateException {
        FileResDto resDto = fileUploadCheck(file);
        if (ObjectUtils.isNotEmpty(resDto)) {
            return resDto;
        }
        String filename = file.getOriginalFilename();
        File desFile = new File(location + filename);
        file.transferTo(desFile);
        return FileResDto.builder().status(200).message("file uploads succeed").build();
    }

    /**
     * download file
     * 
     * @param filename
     * @return
     * @throws MalformedURLException
     */
    public Resource download(String filename) throws MalformedURLException {
        assert existed(filename);
        Path file = Paths.get(location + filename);
        Resource resource = new UrlResource(file.toUri());
        return resource;
    }

    /**
     * list uploaded files
     * 
     * @return
     */
    public List<FileDemoDto> list() {
        File dir = new File(location);
        if (dir.exists()) {
            File[] files = dir.listFiles();
            return Arrays.stream(files).sorted((f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()))
                    .map(f -> initFileDemoDto(f)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private FileDemoDto initFileDemoDto(File file) {
        String name = file.getName();
        String download = "/api/files/download/" + name;
        return FileDemoDto.builder().name(name).downloadUrl(download).build();
    }

    private FileResDto fileUploadCheck(MultipartFile file) {
        String name = DemoUtils.fileName(file);
        if (name.lastIndexOf(".") == -1) {
            return FileResDto.builder().status(403).message("file can not be uploaded").build();
        }
        // todo check file type supports.
        if (existed(name)) {
            return FileResDto.builder().status(403).message("file is existed").build();
        }
        return null;
    }

    private boolean existed(String fileName) {
        File file = new File(location + fileName);
        return file.exists();
    }
}
