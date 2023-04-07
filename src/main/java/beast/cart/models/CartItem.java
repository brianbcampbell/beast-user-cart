package beast.cart.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItem {

    private String productId;
    private int quantity;
    private float priceEachInDollars;

}
