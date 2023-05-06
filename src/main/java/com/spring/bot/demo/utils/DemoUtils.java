package com.spring.bot.demo.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

public class DemoUtils {

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
}
