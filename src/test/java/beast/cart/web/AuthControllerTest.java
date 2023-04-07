package beast.cart.web;

import beast.cart.models.UserDetails;
import beast.cart.user.SignupService;
import beast.cart.web.jwt.JwtUtils;
import beast.cart.web.payload.request.LoginRequest;
import beast.cart.web.payload.request.SignupRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static beast.cart._testdata.Users.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private SignupService signupService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Captor
    private ArgumentCaptor<UserDetails> userDetailsCaptor;

    @Test
    public void test_signin_success_post() throws Exception {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(USER_1_USERNAME);
        loginRequest.setPassword(USER_1_PASSWORD);

        Authentication authResult = new UsernamePasswordAuthenticationToken(USER_1, null);

        when(authenticationManager.authenticate(any()))
                .thenReturn(authResult);

        ResponseCookie responseCookie = ResponseCookie.from("acookie", "cookie_value_1").build();
        when(jwtUtils.generateJwtCookie(USER_1))
                .thenReturn(responseCookie);

        // TEST
        ResponseEntity<?> response = authController.signinUserPost(loginRequest);

        // VERIFY
        verify(jwtUtils).generateJwtCookie(USER_1);
        List<String> cookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);
        assertEquals(1, cookies.size());
        assertEquals("acookie=cookie_value_1", cookies.get(0));
    }

    @Test
    void registerUser_success() throws Exception {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername(USER_1_USERNAME);


        // TEST
        ResponseEntity<?> response = authController.registerUser(signupRequest);

        // VERIFY
        verify(signupService).signup(userDetailsCaptor.capture());
        assertEquals(USER_1_USERNAME, userDetailsCaptor.getValue().getUsername());
        assertTrue(response.getBody().toString().contains(USER_1_USERNAME));
    }

    @Test
    void logoutUser_success() {

        ResponseCookie responseCookie = ResponseCookie.from("acookie", "cookie_value_1").build();
        when(jwtUtils.getCleanJwtCookie())
                .thenReturn(responseCookie);

        // TEST
        ResponseEntity<?> response = authController.logoutUser();

        // VERIFY
        verify(jwtUtils).getCleanJwtCookie();
        List<String> cookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);
        assertEquals(1, cookies.size());
        assertEquals("acookie=cookie_value_1", cookies.get(0));
    }
}