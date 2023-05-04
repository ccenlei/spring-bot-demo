package com.spring.bot.demo.utils;

import org.apache.commons.lang3.StringUtils;

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
}
