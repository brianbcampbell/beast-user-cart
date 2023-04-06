package beast.cart.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static beast.cart._testdata.Users.USER_1;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDetailsTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testSerializationIO() throws JsonProcessingException {

        UserDetails user = USER_1;

        // password is NOT serialized
        String json = mapper.writeValueAsString(user);
        assertTrue(json.toLowerCase().contains(user.getEmail()));
        assertFalse(json.toLowerCase().contains("password"));
    }
}