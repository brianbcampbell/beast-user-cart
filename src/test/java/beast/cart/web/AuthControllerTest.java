package beast.cart.web;

import beast.cart.web.jwt.AuthEntryPointJwt;
import beast.cart.web.jwt.JwtUtils;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static beast.cart._testdata.Users.USER_1_PASSWORD;
import static beast.cart._testdata.Users.USER_1_USERNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(
        controllers = AuthController.class,
        properties = "spring.main.allow-bean-definition-overriding=true")
@AutoConfigureMockMvc
@ContextConfiguration(classes = {SpringSecurityWebAuxTestConfig.class, WebSecurityConfig.class})
@Import({
        AuthController.class,
        JwtUtils.class,
//        WebSecurityConfig.class,
        AuthEntryPointJwt.class
})
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${jwt.CookieName}")
    private String jwtCookieName;

    @Test
//    @WithUserDetails(USER_1_USERNAME)
    public void test_signin_success() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/auth/signin")
                .param("username", USER_1_USERNAME)
                .param("password", USER_1_PASSWORD)
                .accept(MediaType.ALL);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.SET_COOKIE))
                .andReturn();
        Cookie sookie = result.getResponse().getCookie(jwtCookieName);
        String username = jwtUtils.getUserNameFromJwtToken(sookie.getValue());
        assertEquals(USER_1_USERNAME, username);
    }

    @Test
//    @WithUserDetails("username_doesnt_exist")
    public void test_signin_badCredentials() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/auth/signin")
                .param("username", USER_1_USERNAME)
                .param("password", "nope-wrong.password")
                .accept(MediaType.ALL);
        mockMvc.perform(request)
                .andExpect(status().isUnauthorized());
    }


    @Test
    void signinUserPost() {
    }

    @Test
    void signinUserGet() {
    }

    @Test
    void registerUser() {
    }

    @Test
    void logoutUser() {
    }
}