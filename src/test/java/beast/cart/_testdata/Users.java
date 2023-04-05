package beast.cart._testdata;

import beast.cart.models.UserDetails;

public class Users {

    public static final String USER_1_USERNAME = "user_1";
    public static final String USER_1_PASSWORD = "user1_password";
    public static final String USER_1_EMAIL = "user1@test.test";
    public static final UserDetails USER_1 = UserDetails.builder()
            .username(USER_1_USERNAME)
            .email(USER_1_EMAIL)
            .password(USER_1_PASSWORD)
            .build();
    public static final UserDetails MOD_1 = UserDetails.builder()
            .username("mod_1")
            .email("mod1@test.test")
            .password("mod1_password")
            .build();
    public static final UserDetails ADMIN_1 = UserDetails.builder()
            .username("admin_1")
            .email("admin1@test.test")
            .password("admin1_password")
            .build();

    public static UserDetails[] ALL_TEST_USERS = {USER_1, MOD_1, ADMIN_1};
}
