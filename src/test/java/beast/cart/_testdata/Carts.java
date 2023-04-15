package beast.cart._testdata;

import beast.cart.model.CartItem;
import beast.cart.model.UserCart;

import java.util.List;

public class Carts {

    public final static UserCart CART_1 = new UserCart(
            List.of(
                    new CartItem("product_1", 2, 33.33F),
                    new CartItem("product_2", 3, 44.44F)
            )
    );
}
