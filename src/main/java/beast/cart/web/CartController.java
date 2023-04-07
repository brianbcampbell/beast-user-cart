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

import java.util.Optional;

@RestController
class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping(value = "/api/cart", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserCart getCart(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        UserCart userCart = cartService.getUserCart(userDetails.getUsername());
        return Optional.ofNullable(userCart).orElse(new UserCart());

    }

    @PostMapping(value = "/api/cart", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserCart cart
    ) {
        cartService.saveCart(userDetails.getUsername(), cart);
    }

}
