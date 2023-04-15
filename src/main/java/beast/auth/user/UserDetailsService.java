package beast.auth.user;

import beast.auth.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRecord userRecord = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        //Set<GrantedAuthority> authorities = userRecord.getAuthorities().stream().map(SimpleGrantedAuthority::new).toSet();
        Set<GrantedAuthority> authorities = Set.of();
        return UserDetails.builder()
                .username(userRecord.username())
                .email(userRecord.email())
                .password(userRecord.password())
                .authorities(authorities)
                .build();
    }
}
