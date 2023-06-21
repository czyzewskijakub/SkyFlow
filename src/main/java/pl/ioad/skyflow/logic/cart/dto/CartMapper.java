package pl.ioad.skyflow.logic.cart.dto;

import org.springframework.stereotype.Service;
import pl.ioad.skyflow.database.model.Cart;

@Service
public class CartMapper {
    public CartDto map(Cart cart) {
        return new CartDto.CartDTOBuilder()
                .ticketId(cart.getTicket().getTicketId())
                .additionDate(cart.getAdditionDate())
                .build();
    }
}
