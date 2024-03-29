package beast.cart.web;

import beast.cart.cart.CartService;
import beast.cart.models.UserCart;
import beast.cart.models.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping(value = "/api/cart",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserCart getCart(
            @AuthenticationPrincipal UserDetails userDetails
    ) throws Exception {
        UserCart result = cartService.getUserCart(userDetails.getUsername());
        return result;
    }

    @PostMapping(value = "/api/cart",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void saveCart(
            @RequestBody UserCart cart,
            @AuthenticationPrincipal UserDetails userDetails
    ) throws Exception {
        cartService.saveCart(userDetails.getUsername(), cart);
    }

}
