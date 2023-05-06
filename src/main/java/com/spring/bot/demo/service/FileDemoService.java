package com.spring.bot.demo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.jodconverter.DocumentConverter;
import org.jodconverter.office.OfficeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.bot.demo.dto.FileDemoDto;
import com.spring.bot.demo.dto.FileResDto;
import com.spring.bot.demo.utils.DemoUtils;

import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileDemoService {

    @Value("${demo.file.multipart.location}")
    private String location;

    @Value("${demo.file.onlineview.location}")
    private String online;

    @Autowired
    private DocumentConverter dConverter;

    @Autowired
    private Executor serviceExecutor;

    private final Lock DIR_LOCK = new ReentrantLock();

    /**
     * upload file
     * 
     * @param file
     * @return
     * @throws IOException
     * @throws IllegalStateException
     * @throws OfficeException
     */
    public FileResDto upload(MultipartFile file) throws IOException, IllegalStateException, OfficeException {
        FileResDto resDto = fileUploadCheck(file);
        if (ObjectUtils.isNotEmpty(resDto)) {
            return resDto;
        }
        String filename = file.getOriginalFilename();
        File desFile = new File(location + filename);
        mkdir(desFile);
        file.transferTo(desFile);
        asyncOnlineView(filename);
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

    /**
     * convert to online view supported file
     * 
     * @param fileName
     * @throws OfficeException
     */
    public void asyncOnlineView(String fileName) {
        File oriFile = new File(location + fileName);
        File tarFile = toOnline(fileName);
        mkdir(tarFile);
        CompletableFuture.supplyAsync(() -> convertWithRetry(oriFile, tarFile, 3, 1000), serviceExecutor)
                .thenAccept(result -> {
                    System.out.println("Result: " + result);
                    // todo need to inform this reuslt?
                }).exceptionally(ex -> {
                    log.error("Exception occurred: {}", ex);
                    // todo how to handle this exception.
                    return null;
                });
    }

    private String convertWithRetry(File oriFile, File tarFile, int maxRetries, int retryDelayMillis) {
        while (maxRetries > 0) {
            try {
                return convert(oriFile, tarFile);
            } catch (OfficeException | IOException e) {
                log.warn("online view failed.", e);
                maxRetries--;
                try {
                    Thread.sleep(retryDelayMillis);
                } catch (InterruptedException ie) {
                    // ignore
                }
            }
        }
        throw new RuntimeException("online view retry failed.");
    }

    private String convert(File oriFile, File tarFile) throws OfficeException, IOException {
        String suffix = DemoUtils.fileSuffix(oriFile.getAbsolutePath());
        if ("pdf".equals(suffix) || "html".equals(suffix) || "png".equals(suffix) || "jpg".equals(suffix)) {
            try (InputStream iStream = new FileInputStream(oriFile);
                    OutputStream oStream = new FileOutputStream(tarFile)) {
                IOUtils.copy(iStream, oStream);
            }
        } else {
            dConverter.convert(oriFile).to(tarFile).execute();
        }
        return "success";
    }

    private File toOnline(String fileName) {
        String path = DemoUtils.fileConverts(fileName);
        File file = new File(online + path);
        return file;
    }

    private FileDemoDto initFileDemoDto(File file) {
        String name = file.getName();
        String download = "/api/files/download/" + name;
        File onlineFile = toOnline(name);
        /**
         * because of async onlineView function and onlineView spends much time. maybe
         * online view file is not ready.
         */
        String preview = onlineFile.exists() ? onlineFile.getAbsolutePath() : "";
        String encodePreview = DemoUtils.encode(preview);
        String href = "/api/files/preview?filename=" + encodePreview;
        return FileDemoDto.builder().name(name).downloadUrl(download).previewUrl(href).build();
    }

    private FileResDto fileUploadCheck(MultipartFile file) {
        String name = DemoUtils.fileName(file);
        if (name.lastIndexOf(".") == -1) {
            return FileResDto.builder().status(403).message("file can not be uploaded").build();
        }
        if (!DemoUtils.fileSupports(name)) {
            return FileResDto.builder().status(403).message("file can not be uploaded").build();
        }
        if (existed(name)) {
            return FileResDto.builder().status(403).message("file is existed").build();
        }
        return null;
    }

    /**
     * need syncnized
     * 
     * @param fileName
     * @return
     */
    @Synchronized
    private boolean existed(String fileName) {
        File file = new File(location + fileName);
        return file.exists();
    }

    /**
     * need syncnized
     * 
     * @param file
     */
    private void mkdir(File file) {
        File dir = file.getParentFile();
        if (!dir.exists()) {
            try {
                DIR_LOCK.lock();
                if (!dir.exists()) {
                    dir.mkdir();
                }
            } finally {
                DIR_LOCK.unlock();
            }
        }
    }
}
