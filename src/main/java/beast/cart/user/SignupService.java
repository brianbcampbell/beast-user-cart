package beast.cart.user;

import beast.cart.models.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignupService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public SignupService(
            UserRepository userRepository,
            PasswordEncoder encoder
    ) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public void signup(UserDetails userDetails) throws Exception {
        // VALIDATE
        validateSignup(userDetails);

        // SAVE
        UserRecord userRecord = UserRecord.builder()
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .password(encoder.encode(userDetails.getPassword()))    // encode before persist
                .build();
        userRepository.save(userRecord);
    }

    private void validateSignup(UserDetails userDetails) throws Exception {
        validatePassword(userDetails.getPassword());

        if (userRepository.existsByUsername(userDetails.getUsername())) {
            throw new Exception("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(userDetails.getEmail())) {
            throw new Exception("Error: Email is already in use!");
        }
    }

    private void validatePassword(String password) throws Exception {
        if (password == null || password.isBlank()) {
            throw new Exception("Error: Invalid password; cannot be blank");
        }
        if (password.length() < 8) {
            throw new Exception("Error: Invalid password; must be >= 8 chars");
        }
        //TODO add password complexity requirements
    }
}
