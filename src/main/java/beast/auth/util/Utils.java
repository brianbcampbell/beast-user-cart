package beast.auth.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Utils {
    public static Set<? extends GrantedAuthority> setOfGrantedAuthorities(String... roles) {
        return Arrays.stream(roles).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }
}
