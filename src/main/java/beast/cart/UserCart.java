package beast.cart;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
class UserCart {

    private List<CartItem> items=new ArrayList<>();

}
