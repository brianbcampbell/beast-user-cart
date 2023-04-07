package beast.cart.web;

import beast.cart.cart.CartService;
import beast.cart.models.UserCart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static beast.cart._testdata.Carts.CART_1;
import static beast.cart._testdata.Users.USER_1;
import static beast.cart._testdata.Users.USER_1_USERNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private CartService cartService;


    @Test
    public void test_getCart() {

        when(cartService.getUserCart(USER_1_USERNAME))
                .thenReturn(CART_1);

        // TEST
        UserCart cart = cartController.getCart(USER_1);

        // VERIFY
        assertEquals(2, cart.getItems().size());
    }

    @Test
    void test_saveCart() {
        cartController.saveCart(USER_1, CART_1);

        verify(cartService).saveCart(USER_1_USERNAME, CART_1);
    }
}