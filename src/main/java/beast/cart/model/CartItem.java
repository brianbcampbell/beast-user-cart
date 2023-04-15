package beast.cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItem {

    private String productId;
    private int quantity;
    private float priceEachInDollars;

}
