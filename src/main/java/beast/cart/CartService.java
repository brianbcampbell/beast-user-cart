package beast.cart;

import org.springframework.stereotype.Service;

import static java.time.LocalTime.now;

@Service
class CartService {

//    private final CartRepository cartRepository;
//
//    @Autowired
//    public CartService(CartRepository cartRepository) {
//        this.cartRepository = cartRepository;
//    }


    public UserCart getUserCart(String userId) {
        return new UserCart();
    }

    public void saveCart(String userId, UserCart cart) {
        System.out.println("SAVED CART " + now());
    }
}
