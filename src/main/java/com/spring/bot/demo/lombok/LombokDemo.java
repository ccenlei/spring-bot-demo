package com.spring.bot.demo.lombok;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@ToString
public class LombokDemo {

    @NonNull
    private Integer id;

    @NonNull
    private String name;

    private Integer age;

    public static void main(String[] args) {
        LombokDemo demo1 = new LombokDemo();
        System.out.println(demo1);

        LombokDemo demo2 = new LombokDemo(1, "cc1", 10);
        System.out.println(demo2);

        LombokDemo demo3 = LombokDemo.of(2, "ccc");
        System.out.println(demo3);
    }
}
