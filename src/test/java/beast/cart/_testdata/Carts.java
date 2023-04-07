package beast.cart._testdata;

import beast.cart.models.CartItem;
import beast.cart.models.UserCart;

import java.util.List;

public class Carts {

    public final static UserCart CART_1 = new UserCart(
            List.of(
                    CartItem.builder()
                            .productId("product_1")
                            .quantity(2)
                            .priceEachInDollars(33.33F)
                            .build(),
                    CartItem.builder()
                            .productId("product_2")
                            .quantity(3)
                            .priceEachInDollars(44.44F)
                            .build()
            )
    );
}
