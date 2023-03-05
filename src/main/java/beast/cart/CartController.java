package beast.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping(value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserCart getCart(
            @RequestHeader(value = "userid", required = false) String userId
    ) throws Exception {
        validateUser(userId);
        UserCart result = cartService.getUserCart(userId);
        return result;
    }

    @PostMapping(value = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void saveCart(
            @RequestBody UserCart cart,
            @RequestHeader(value = "userid", required = false) String userId
    ) throws Exception {
        validateUser(userId);
        cartService.saveCart(userId, cart);
    }

    private void validateUser(String userId) throws Exception {
        if (userId == null) throw new Exception("HTTP header 'userid' is required");
        if (userId.equals("invalid")) throw new Exception("Unauthorized access");

        return;
    }

}
