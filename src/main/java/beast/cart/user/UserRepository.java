package beast.cart.user;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
class UserRepository {

    List<UserRecord> users = new ArrayList();

    @PostConstruct
    private void loadSampleData() {
        users.add(
                new UserRecord(
                        "bcadmin",
                        "brian@admin",
                        "$2a$10$/vlr3WEVd9kPX34QymXSJuBIya3U1ffjl2dG2dsEfryGvS9G/p1Ea"
                )
        );
    }

    Optional<UserRecord> findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    Boolean existsByUsername(String username) {
        return users.stream()
                .anyMatch(u -> u.getUsername().equals(username));
    }

    Boolean existsByEmail(String email) {
        return users.stream()
                .anyMatch(u -> u.getEmail().equals(email));
    }

    public void save(UserRecord userRecord) throws Exception {
        if (users.stream()
                .anyMatch(u ->
                        u.getUsername().equals(userRecord.getUsername()) || u.getEmail().equals(userRecord.getEmail())
                )
        ) throw new Exception("user already exists");

        users.add(userRecord);
    }
}
