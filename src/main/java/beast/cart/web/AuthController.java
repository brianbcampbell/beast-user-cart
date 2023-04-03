package beast.cart.web;

import beast.cart.web.jwt.JwtUtils;
import beast.cart.web.payload.request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userService;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthController(UserDetailsService userService, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signinUserPost(@RequestBody LoginRequest loginRequest) {
        return signInAndBuildResponseEntity(loginRequest.getUsername(), loginRequest.getPassword());
    }

    // obv in a production app we wouldn't put a password on the url
    @GetMapping("/signin")
    public ResponseEntity<?> signinUserGet(@RequestParam String username, @RequestParam String password) {
        return signInAndBuildResponseEntity(username, password);
    }

    /**
     * Authenticate user, set cookie, return user details
     *
     * @param username
     * @param password
     * @return
     */
    private ResponseEntity<?> signInAndBuildResponseEntity(String username, String password) {

        // AUTHENTICATE
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(token);

        // SET AUTH COOKIE IN RESPONSE
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(username);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body("Login successful for: " + username);
    }

//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@RequestBody SignupRequest req) {
//        try {
//            userService.signup(
//                    req.getUsername(),
//                    req.getPassword(),
//                    req.getEmail(),
//                    req.getRoles()
//            );
//            return ResponseEntity.ok("User " + req.getUsername() + " registered successfully!");
//        } catch (Exception ex) {
//            return ResponseEntity.badRequest()
//                    .body(ex.getMessage());
//        }
//    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("You've been signed out!");
    }
}

