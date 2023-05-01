package com.spring.bot.demo.utils;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageHelperUtil {

    private Long total;

    private Integer pageTotal;

    private Integer page;

    private Integer pageRows;

    private List<?> dataTs;
}
