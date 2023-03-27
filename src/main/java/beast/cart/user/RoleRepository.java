package beast.cart.user;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
class RoleRepository {
    Optional<Role> findByName(ERole name) {
        return Optional.empty();
    }
}
