package beast.cart.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Builder
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private String username;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  //disallow serialize
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
