package pl.ioad.skyflow.logic.paypal;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.ioad.skyflow.database.repository.CartRepository;
import pl.ioad.skyflow.database.repository.UserRepository;


@Controller
@RequiredArgsConstructor
public class PayPalController {


    private final PayPalService service;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;



    @GetMapping("/payment")
    public String home(@RequestParam Long userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        return "home.html";
    }

    @PostMapping("/pay")
    public ResponseEntity<String> payment(@RequestParam Long userId) throws PayPalRESTException {
        try {
            service.preparePayment(userId);
        } catch (PayPalRESTException e) {
            throw new PayPalRESTException("Payment fail");
        }
        return ResponseEntity.ok("");
    }

    @GetMapping("/fail")
    public String cancelPay() {
        return "cancel.html";
    }

    @GetMapping("/success")
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) throws PayPalRESTException {
        try {
            Payment payment = service.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return "success.html";
            }
        } catch (PayPalRESTException e) {
            throw new PayPalRESTException("Payment fail");
        }
        return "redirect:/";
    }

}
