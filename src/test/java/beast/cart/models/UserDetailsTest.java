package beast.cart.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDetailsTest {

    private final ObjectMapper mapper = new ObjectMapper();
    private final String PASSWORD = "1a2b3c4d5e6f";

    @Test
    void testSerializationIO() throws JsonProcessingException {

        UserDetails user = new UserDetails(
                123L,
                "testguy",
                "testguy@testtest.test",
                PASSWORD,
                Set.of(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_MOD"))
        );

        //password is NOT serialized
        String json = mapper.writeValueAsString(user);
        assertTrue(json.toLowerCase().contains("testtest"));
        assertFalse(json.toLowerCase().contains("password"));
    }
}