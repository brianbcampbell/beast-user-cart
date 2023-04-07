package beast.cart.web;

import beast.cart._testdata.SpringSecurityWebAuxTestConfig;
import beast.cart.user.SignupService;
import beast.cart.web.jwt.AuthEntryPointJwt;
import beast.cart.web.jwt.JwtUtils;
import beast.cart.web.payload.request.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
@ContextConfiguration(classes = {
        WebSecurityConfig.class,                // this is what we want to test; routes and auth
        SpringSecurityWebAuxTestConfig.class    // overrides beans with test beans
})
@Import({
        AuthController.class,
        JwtUtils.class,
        AuthEntryPointJwt.class
})
class WebSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private AuthController authController;

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${jwt.CookieName}")
    private String jwtCookieName;

    @MockBean
    private SignupService signupService;


    @Test
    public void test_signin_success_post() throws Exception {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(USER_1_USERNAME);
        loginRequest.setPassword(USER_1_PASSWORD);
        String json = new ObjectMapper().writeValueAsString(loginRequest);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.SET_COOKIE))
                .andReturn();
        Cookie sookie = result.getResponse().getCookie(jwtCookieName);
        String username = jwtUtils.parseClaimsFromJwt(sookie.getValue()).getSubject();
        assertEquals(USER_1_USERNAME, username);
    }

    @Test
    public void test_signin_badCredentials_get() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/auth/signin")
                .param("username", USER_1_USERNAME)
                .param("password", "nope-wrong.password");
        mockMvc.perform(request)
                .andExpect(status().isUnauthorized());
    }

    @Test
//    @WithUserDetails("username_doesnt_exist")
    public void test_signin_badCredentials_post() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(USER_1_USERNAME);
        loginRequest.setPassword("nope-wrong.password");
        String json = new ObjectMapper().writeValueAsString(loginRequest);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
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