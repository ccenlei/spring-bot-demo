package com.spring.bot.demo.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

public class DemoUtils {

    private static Map<String, String> SUPPORT_FILES = new HashMap<>();

    static {
        // ==== convert to pdf ====
        SUPPORT_FILES.put("pdf", "pdf");
        SUPPORT_FILES.put("ppt", "pdf");
        SUPPORT_FILES.put("pptx", "pdf");
        SUPPORT_FILES.put("csv", "pdf");
        SUPPORT_FILES.put("doc", "pdf");
        SUPPORT_FILES.put("docx", "pdf");
        // ==== convert to html ====
        SUPPORT_FILES.put("html", "html");
        SUPPORT_FILES.put("xls", "html");
        SUPPORT_FILES.put("xlsx", "html");
        SUPPORT_FILES.put("xlsm", "html");
        // ==== convert to none ====
        SUPPORT_FILES.put("png", "png");
        SUPPORT_FILES.put("jpg", "jpg");
    }

    /**
     * temporary
     * 
     * unkown reason, useless:
     * 
     * <pre>
     *
     * @Bean
     * RoleHierarchy roleHierarchy() {
     *     RoleHierarchyImpl rHierarchy = new RoleHierarchyImpl();
     *     rHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
     *     return rHierarchy;
     * }
     * 
     * </pre>
     * 
     * @param role
     * @return
     */
    public static String[] roles(String role) {
        if (StringUtils.equals("ADMIN", role)) {
            return new String[] { role, "USER" };
        }
        return new String[] { role };
    }

    /**
     * get file name from MultipartFile
     * 
     * @param file
     * @return
     */
    public static String fileName(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        assert StringUtils.isNotBlank(fileName);
        return fileName(fileName);
    }

    public static String fileName(String fileName) {
        fileName = HtmlUtils.htmlEscape(fileName, "UTF-8");
        // Check for Unix-style path
        int unixSep = fileName.lastIndexOf('/');
        // Check for Windows-style path
        int winSep = fileName.lastIndexOf('\\');
        // Cut off at latest possible point
        int pos = Math.max(winSep, unixSep);
        if (pos != -1) {
            fileName = fileName.substring(pos + 1);
        }
        return fileName;
    }

    /**
     * get file suffix by file name
     * 
     * @param fileName
     * @return
     */
    public static String fileSuffix(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        String suffix = fileName.substring(dotIndex + 1);
        return suffix;
    }

    public static boolean fileSupports(String fileName) {
        String suffix = fileSuffix(fileName);
        return SUPPORT_FILES.containsKey(suffix);
    }

    public static String fileConverts(String fileName) {
        String suffix = fileSuffix(fileName);
        String targetSuffix = SUPPORT_FILES.get(suffix);
        String targetFileName = StringUtils.replace(fileName, suffix, targetSuffix);
        return targetFileName;
    }

    public static String encode(String path) {
        try {
            return URLEncoder.encode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return path;
        }
    }

    public static String decode(String path) {
        try {
            return URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return path;
        }
    }

    public static int ipStr2Int(String ipStr) {
        String[] lines = StringUtils.split(ipStr, "\\.");
        int ipInt = 0;
        for (String line :lines) {
            int num = Integer.parseInt(line);
            ipInt = num | (ipInt << 8);
        }
        return ipInt;
    }

    public static String ipInt2Str(int ipInt) {
        String ipStr = ((ipInt >> 24) & 0xff) + "." + ((ipInt >> 16) & 0xff) + "." + ((ipInt >> 8) & 0xff) + "." + (ipInt & 0xff);
        return ipStr;
    }
}
