package pl.ioad.skyflow.logic.paypal;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.ioad.skyflow.database.model.User;
import pl.ioad.skyflow.database.repository.CartRepository;
import pl.ioad.skyflow.database.repository.UserRepository;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PayPalController {


    private final PayPalService service;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public static final String URL = "http://localhost:8080";
    public static final String SUCCESS_URL = "/success";
    public static final String CANCEL_URL = "/fail";
    public static final String CURRENCY = "EUR";



    @GetMapping("/payment")
    public String home(@RequestParam Long userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        return "home.html";
    }

    @PostMapping("/pay")
    public String payment(@RequestParam Long userId) {
        try {
            Optional<User> user = userRepository.findById(userId);
            if (user.isEmpty()) {
                throw new EntityNotFoundException("User not found");
            }
            double totalPrice = cartRepository.findAll().stream().filter(ticket ->
                            ticket.getUser() == user.get())
                    .mapToDouble(cart -> cart.getTicket().getPrice()).sum();
            if (totalPrice == 0) {
                throw new EntityNotFoundException("Total price is equal to 0.");
            }
            Payment payment = service.createPayment(
                    totalPrice,
                    CURRENCY,
                    URL + CANCEL_URL,
                    URL + SUCCESS_URL);
            for (Links link: payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return "redirect:" + link.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "cancel.html";
    }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam ("PayerID") String payerId) {
        try {
            Payment payment = service.executePayment(paymentId,payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return "success.html";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/";
    }

}
