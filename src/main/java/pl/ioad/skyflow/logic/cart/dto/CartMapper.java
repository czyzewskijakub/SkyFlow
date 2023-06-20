package pl.ioad.skyflow.logic.cart.dto;

import org.springframework.stereotype.Service;
import pl.ioad.skyflow.database.model.Cart;

@Service
public class CartMapper {
    public CartDTO map(Cart cart) {
        return new CartDTO.CartDTOBuilder()
                .ticketId(cart.getTicket().getTicketId())
                .additionDate(cart.getAdditionDate())
                .build();
    }
}
