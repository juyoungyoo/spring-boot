package com.inflearn.springbootmvc.hateoas;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest
public class SampleHateoasControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void sample() throws Exception {
        mockMvc.perform(get("/hateoas"))
                .andDo(print())
                .andExpect(jsonPath("$._links.self").exists())
        ;
    }
}