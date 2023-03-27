package beast.cart.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
class UserRecord {
    private Long id;

    private String username;

    private String email;

    private String password;

    private Set<Role> roles = new HashSet<>();

    public UserRecord(
            String username,
            String email,
            String encodedPassword
    ) {
        this.username = username;
        this.email = email;
        this.password = encodedPassword;
    }
}
