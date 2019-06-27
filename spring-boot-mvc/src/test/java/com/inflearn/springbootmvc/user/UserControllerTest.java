package com.inflearn.springbootmvc.user;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello"))
        ;
    }

    @Test
    public void createUser_Json() throws Exception {
        String userJson = "{\"username\":\"juyoung\",\"password\":\"123\"}";
        mockMvc.perform(post("/user/create")
                .contentType(MediaType.APPLICATION_JSON)    // spring framework
                .accept(MediaType.APPLICATION_JSON_UTF8)       // response 응답을 원하는 타입
                .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(equalTo("juyoung"))))
                .andExpect(jsonPath("$.password", is(equalTo("123"))));
    }

    @Test
    public void createUser_xml() throws Exception {
        String userJson = "{\"username\":\"juyoung\",\"password\":\"123\"}";
        mockMvc.perform(post("/user/create")
                .contentType(MediaType.APPLICATION_JSON)    // spring framework
                .accept(MediaType.APPLICATION_XML)       // response 응답을 원하는 타입
                .content(userJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/User/username").string("juyoung"))
                .andExpect(xpath("/User/password").string("123"));
    }
}
