package beast.cart.web;

import beast.cart.models.UserDetails;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

import static beast.cart._testdata.Users.*;

@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {

        UserDetails[] userDetails = {USER_1, MOD_1, ADMIN_1};
        Arrays.stream(userDetails)
                .forEach(u -> u.setPassword(passwordEncoder.encode(u.getPassword())));
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(USER_1);
        return inMemoryUserDetailsManager;
    }
}