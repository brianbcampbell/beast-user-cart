package beast.cart.web;

import beast.cart.models.UserDetails;
import beast.cart.user.SignupService;
import beast.cart.web.jwt.JwtUtils;
import beast.cart.web.payload.request.LoginRequest;
import beast.cart.web.payload.request.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final SignupService signupService;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthController(
            JwtUtils jwtUtils,
            AuthenticationManager authenticationManager,
            SignupService signupService
    ) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.signupService = signupService;
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
        Authentication authentication = authenticationManager.authenticate(token);
        UserDetails principal = (UserDetails) authentication.getPrincipal();

        // SET AUTH COOKIE IN RESPONSE
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(principal);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body("Login successful for: " + username);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest req) {
        try {
            UserDetails userDetails = UserDetails.builder()
                    .username(req.getUsername())
                    .password(req.getPassword())
                    .email(req.getEmail())
                    .build();
            signupService.signup(userDetails);
            return ResponseEntity.ok("User " + req.getUsername() + " registered successfully!");
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .body(ex.getMessage());
        }
    }

    @GetMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("You've been signed out!");
    }
}

