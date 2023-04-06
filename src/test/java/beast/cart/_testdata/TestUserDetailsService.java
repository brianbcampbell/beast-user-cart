package beast.cart._testdata;

import beast.cart.models.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static beast.cart._testdata.Users.ALL_TEST_USERS;


/**
 * Emulate beast UserDetailsService without repository etc
 */
public class TestUserDetailsService implements UserDetailsService {

    private final Map<String, UserDetails> users;
    private final PasswordEncoder passwordEncoder;

    public TestUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        users = new HashMap<>();
        Arrays.stream(ALL_TEST_USERS).forEach(this::addUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.get(username.toLowerCase());
    }

    private UserDetails makeUserWithEncodedPassword(UserDetails ud) {
        return UserDetails.builder()
                .username(ud.getUsername())
                .password(passwordEncoder.encode(ud.getPassword()))
                .email(ud.getEmail())
                .authorities(
                        Optional.ofNullable(ud.getAuthorities())
                                .orElse(Set.of())
                )
                .build();
    }

    public void addUser(UserDetails userDetails) {
        userDetails = makeUserWithEncodedPassword(userDetails);
        users.put(userDetails.getUsername().toLowerCase(), userDetails);
    }
}
