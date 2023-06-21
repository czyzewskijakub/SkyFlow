package pl.ioad.skyflow.logic.paypal;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ioad.skyflow.database.repository.UserRepository;


@RestController
@RequiredArgsConstructor
@RequestMapping("/paypal")
public class PayPalController {


    private final PayPalService service;
    private final UserRepository userRepository;


    @GetMapping("/payment")
    public String home(@RequestParam Long userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        return "home.html";
    }

    @PostMapping("/pay")
    public ResponseEntity<String> payment(@RequestParam Long userId) throws PayPalRESTException {
        return ResponseEntity.ok(service.preparePayment(userId));
    }

    @GetMapping("/fail")
    public String cancelPay() {
        return "cancel.html";
    }

    @GetMapping("/success")
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) throws PayPalRESTException {
            Payment payment = service.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return "success.html";
            }
        return "redirect:/";
    }

}
