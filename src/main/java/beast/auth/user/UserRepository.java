package beast.auth.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
class UserRepository {

    List<UserRecord> users = new ArrayList<>();

    Optional<UserRecord> findByUsername(String username) {
        return users.stream()
                .filter(u -> u.username().equals(username))
                .findFirst();
    }

    Boolean existsByUsername(String username) {
        return users.stream()
                .anyMatch(u -> u.username().equals(username));
    }

    Boolean existsByEmail(String email) {
        return users.stream()
                .anyMatch(u -> u.email().equals(email));
    }

    public void save(UserRecord userRecord) throws Exception {
        if (users.stream()
                .anyMatch(u ->
                        u.username().equals(userRecord.username()) || u.email().equals(userRecord.email())
                )
        ) throw new Exception("user already exists");

        users.add(userRecord);
    }
}
