package beast.cart.web;

import beast.cart.models.UserDetails;
import beast.cart.user.SignupService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class LoadSampleData {

    private final SignupService signupService;

    @Autowired
    public LoadSampleData(SignupService signupService) {
        this.signupService = signupService;
    }

    @PostConstruct
    private void loadSampleData() {
        SAMPLE_USERS
                .forEach(userDetails -> {
                    try {
                        signupService.signup(userDetails);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public static final Collection<? extends UserDetails> SAMPLE_USERS = List.of(
            UserDetails.builder()
                    .username("bcadmin")
                    .email("brian@admin")
                    .password("w00tw00t")
                    .build()
    );
}
