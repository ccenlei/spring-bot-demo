package com.spring.bot.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class MintFunControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void should_return_user_if_id_1() throws Exception {
        mockMvc.perform(get("/mint/user/1"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.name").value("sjaskillz"));
    }

    @Test
    void should_return_404_if_id_none() throws Exception {
        mockMvc.perform(get("/mint/user/0"))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.code").value(3101));
    }
}
