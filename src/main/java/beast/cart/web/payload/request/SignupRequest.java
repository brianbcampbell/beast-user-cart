package beast.cart.web.payload.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class SignupRequest {
    private String username;
    private String email;
    private Set<String> roles;
    private String password;
}
