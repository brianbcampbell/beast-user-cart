package beast.cart;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
class CartRepository {

    Map<String, UserCart> carts = new HashMap<>();

    public UserCart getCart(String userId) {
        return carts.get(userId);
    }

    public void saveCart(String userId, UserCart cart) {
        carts.put(userId, cart);
    }

}
