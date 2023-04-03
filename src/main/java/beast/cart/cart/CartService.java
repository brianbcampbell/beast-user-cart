package beast.cart.cart;

import beast.cart.models.UserCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.time.LocalTime.now;

@Service
public class CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public UserCart getUserCart(String userId) {
        return cartRepository.getCart(userId);
    }

    public void saveCart(String userId, UserCart cart) {
        cartRepository.saveCart(userId, cart);
        System.out.println("SAVED CART " + now());
    }
}
