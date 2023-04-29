package com.spring.bot.demo.lombok;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Getter
@Setter
public class BuilderDemo {

    private Integer id;

    private String name;

    private Integer age;

    public static void main(String[] args) {
        BuilderDemo bDemo = BuilderDemo.builder().id(1).name("bbb").age(100).build();
        System.out.println(bDemo);
    }
}
