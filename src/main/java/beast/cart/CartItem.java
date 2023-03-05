package beast.cart;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class CartItem {

    private String id;
    private String productId;
    private int quantity;
    private float priceEachInDollars;

}
