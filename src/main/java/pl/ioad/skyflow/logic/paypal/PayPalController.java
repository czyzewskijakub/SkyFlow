package pl.ioad.skyflow.logic.paypal;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Pay for flight")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully redirect to PayPal page", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                    "message": "Successfully redirect to PayPal page"
                                                }
                                                """
                    )
            )),
            @ApiResponse(responseCode = "400", description = "Not correct request", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "httpStatus": 400,
                                                  "exception": "Exception",
                                                  "message": "Bad request"
                                                }
                                                """
                    )
            )),
            @ApiResponse(responseCode = "401", description = "You are not authorized to pay for flight", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "httpStatus": 401,
                                                  "exception": "Exception",
                                                  "message": "Unauthorized"
                                                }
                                                """
                    )
            )),
            @ApiResponse(responseCode = "403", description = "You cannot perform the operation", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "httpStatus": 403,
                                                  "exception": "Exception",
                                                  "message": "Forbidden"
                                                }
                                                """
                    )
            )),
    })

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
