package com.jasonli.homework;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HomeworkApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    // Testing For Create User
    @Test
    void testingForCreateUser() throws Exception {
        String user = "{\"userName\":\"Jason\",\"userPassword\":\"15918791298\"}";
        this.mockMvc.perform(post("/api/v1/users").content(user).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.status").value("Success"))
                    .andExpect(jsonPath("$.msg").value("Create successfully"));
    }

    // Testing For Delete User
    /*
    @Test
    void testingForDeleteUser() throws Exception {
        String user = "{\"userName\":\"Jason\",\"userPassword\":\"15918791298\"}";
        this.mockMvc.perform(delete("/api/v1/users").)
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.msg").value("Create successfully"));
    }
    */
}
