package ru.netology.cloud_storage;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import ru.netology.cloud_storage.controller.FilesController;
import ru.netology.cloud_storage.dto.JwtRequest;

import javax.servlet.ServletContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ControllersIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    MockMvc mockMvc;

    private static final String WRONG_USER_LOGIN = "user_1@mail.ru";
    private static final String USER_LOGIN = "user@mail.ru";
    private static final String USER_PASSWORD = "user1234";
    private static final String LOGIN = "/login";
    private static final String LOGOUT = "/logout";

    private static JwtRequest validJwtRequest;
    private static JwtRequest invalidJwtRequest;
    private static final Gson gson = new Gson();

    @BeforeAll
    public static void beforeAll(){
        validJwtRequest = new JwtRequest(USER_LOGIN,USER_PASSWORD);
        invalidJwtRequest = new JwtRequest(WRONG_USER_LOGIN,USER_PASSWORD);
    }

    @Test
    void FilesControllerTest() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        Assertions.assertNotNull(servletContext);
        Assertions.assertTrue(servletContext instanceof MockServletContext);
        Assertions.assertNotNull(webApplicationContext.getBean(FilesController.class));
    }

    @Test
    void testLoginSuccess() throws Exception {
        mockMvc.perform(post(LOGIN).contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(validJwtRequest))).andExpect(status().isOk());
    }

    @Test
    void testLoginFail() throws Exception {
        mockMvc.perform(post(LOGIN).contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(invalidJwtRequest))).andExpect(status().isUnauthorized());
    }

    @Test
    void testLogoutSuccess() throws Exception {
        mockMvc.perform(post(LOGOUT).contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(validJwtRequest))).andExpect(status().is3xxRedirection());
    }
}
