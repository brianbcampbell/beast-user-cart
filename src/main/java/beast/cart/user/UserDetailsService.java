package beast.cart.user;

import beast.cart.models.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRecord userRecord = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return buildUser(userRecord);
    }

    @Autowired
    public UserDetailsService(
            UserRepository userRepository,
            PasswordEncoder encoder,
            RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
    }

    public void signup(
            String username,
            String password,
            String email,
            Collection<String> roles
    ) throws Exception {
        if (userRepository.existsByUsername(username)) {
            throw new Exception("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(email)) {
            throw new Exception("Error: Email is already in use!");
        }

        UserRecord userRecord = buildUserRecord(username, password, email, roles);

        userRepository.save(userRecord);
    }

    private UserRecord buildUserRecord(String username, String password, String email, Collection<String> roles) {
        // Create new user's account
        UserRecord userRecord = new UserRecord(
                username,
                email,
                encoder.encode(password)
        );
        userRecord.setRoles(roles.stream()
                .map(ERole::valueOf)
                .map(Role::new)
                .collect(Collectors.toSet())
        );
        return userRecord;
    }

    public static UserDetails buildUser(UserRecord userRecord) {
        Set<GrantedAuthority> authorities = userRecord.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toSet());

        return new UserDetails(
                userRecord.getUsername(),
                userRecord.getEmail(),
                userRecord.getPassword(),
                authorities);
    }
}
