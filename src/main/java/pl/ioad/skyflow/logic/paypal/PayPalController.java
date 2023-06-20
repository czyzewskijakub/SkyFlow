package pl.ioad.skyflow.logic.paypal;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.ioad.skyflow.database.model.User;
import pl.ioad.skyflow.database.repository.UserRepository;
import pl.ioad.skyflow.logic.paypal.payload.Order;

@Controller
@RequiredArgsConstructor
public class PayPalController {


    private final PayPalService service;

    public static final String URL = "http://localhost:8080";
    public static final String SUCCESS_URL = "/success";
    public static final String CANCEL_URL = "/fail";
    public static final String CURRENCY = "EUR";



    @GetMapping("/payment")
    public String home() {
        return "home.html";
    }

    @PostMapping("/pay")
    public String payment() {
        try {
            Payment payment = service.createPayment(
                   25.0,
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
