package com.security.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
public class RESTResourceApplicationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    public void 엑세스토큰으로_접속() throws Exception {
        String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJqdXlvdW5nIiwic2NvcGUiOlsicmVhZCJdLCJvcmdhbml6YXRpb24iOiJqdXlvdW5nQmFiVyIsImV4cCI6MTU1NzgxMDM2OCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6ImMxZjExMThhLTE4MTQtNDE4ZC04ZGM1LTA5MzkwNDU4ZTY4NyIsImNsaWVudF9pZCI6ImZvbyJ9.z0igV7PRiB5HuUqzSVlNTEHR9C0Fuf6sro5Pz_5fcbE";
        mockMvc.perform(get("/foo")
                .header("authorization", "bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
