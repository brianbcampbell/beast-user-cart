package beast.cart.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
class Role {
    private Integer id;

    private ERole name;

    public Role(ERole name) {
        this.name = name;
    }
}