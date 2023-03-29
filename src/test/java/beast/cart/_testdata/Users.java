package beast.cart._testdata;

import beast.cart.models.UserDetails;
import beast.cart.util.Utils;

public class Users {

    public static final String USER_1_USERNAME = "user_1";
    public static final String USER_1_PASSWORD = "user1_password";
    public static final String USER_1_EMAIL = "user1@test.test";
    public static final UserDetails USER_1 = new UserDetails(
            USER_1_USERNAME,
            USER_1_EMAIL,
            USER_1_PASSWORD,
            Utils.setOfGrantedAuthorities("ROLE_USER")
    );
    public static final UserDetails MOD_1 = new UserDetails(
            "mod_1",
            "mod1@test.test",
            "mod1_password",
            Utils.setOfGrantedAuthorities("ROLE_USER", "ROLE_MOD")
    );
    public static final UserDetails ADMIN_1 = new UserDetails(
            "admin_1",
            "admin1@test.test",
            "admin1_password",
            Utils.setOfGrantedAuthorities("ROLE_USER", "ROLE_MOD", "ROLE_ADMIN")
    );

}
