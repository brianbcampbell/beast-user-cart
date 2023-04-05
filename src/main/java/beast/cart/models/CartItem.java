package beast.cart.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class CartItem {

    private String productId;
    private int quantity;
    private float priceEachInDollars;

}
