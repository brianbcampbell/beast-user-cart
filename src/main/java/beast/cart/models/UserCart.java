package beast.cart.models;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class UserCart {

    private List<CartItem> items=new ArrayList<>();

}
