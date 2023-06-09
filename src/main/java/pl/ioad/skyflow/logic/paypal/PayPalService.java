package pl.ioad.skyflow.logic.paypal;

import static pl.ioad.skyflow.database.model.TicketStatus.VALID;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ioad.skyflow.database.model.Ticket;
import pl.ioad.skyflow.database.model.User;
import pl.ioad.skyflow.database.repository.CartRepository;
import pl.ioad.skyflow.database.repository.TicketRepository;
import pl.ioad.skyflow.database.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class PayPalService {
    public static final String PAYMENT_METHOD = "paypal";
    public static final String INTENT = "sale";

    private static final String URL = "http://localhost:8080";

    private static final String CURRENCY = "EUR";

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final TicketRepository ticketRepository;

    public static final String SUCCESS_URL = "/success";
    public static final String CANCEL_URL = "/fail";

    private final APIContext apiContext;

    public Payment createPayment(
            Double total,
            String currency,
            String cancelUrl,
            String successUrl) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setIntent(INTENT);
        payment.setPayer(getPayer());
        payment.setTransactions(getTransactionList(getAmount(currency, total)));
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);
        return payment.create(apiContext);
    }

    private Amount getAmount(String currency, Double total) {
        Amount amount = new Amount();
        amount.setCurrency(currency);
        total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
        DecimalFormat df = new DecimalFormat("#.##");
        amount.setTotal(df.format(total));
        return amount;
    }

    private List<Transaction> getTransactionList(Amount amount) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        return transactions;
    }

    private Payer getPayer() {
        Payer payer = new Payer();
        payer.setPaymentMethod(PAYMENT_METHOD);
        return payer;
    }


    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        try {
            Payment payment = new Payment();
            payment.setId(paymentId);
            PaymentExecution paymentExecution = new PaymentExecution();
            paymentExecution.setPayerId(payerId);
            return payment.execute(apiContext, paymentExecution);
        } catch (PayPalRESTException e) {
            throw new PayPalRESTException("Payment fail");
        }
    }

    public String preparePayment(Long userId) throws PayPalRESTException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        List<Ticket> userTickets = ticketRepository.findAll()
                .stream()
                .filter(ticket -> ticket.getUser() == user.get())
                .toList();

        double totalPrice = cartRepository.findAll().stream().filter(ticket ->
                        ticket.getUser() == user.get())
                .mapToDouble(cart -> cart.getTicket().getPrice()).sum();
        cartRepository.deleteAll(cartRepository.findAll().stream().filter(ticket ->
                ticket.getUser() == user.get()).collect(Collectors.toList()));
        if (totalPrice == 0) {
            throw new EntityNotFoundException("Total price is equal to 0.");
        }
        try {
            Payment payment = createPayment(
                    totalPrice,
                    CURRENCY,
                    URL + CANCEL_URL,
                    URL + SUCCESS_URL);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    String test = link.getHref();
                    test = test.replace("https://", "");
                    userTickets.forEach(ticket -> {
                        ticket.setStatus(VALID);
                        ticketRepository.save(ticket);
                    });

                    return test;
                }
            }
            return "";
        } catch (PayPalRESTException e) {
            throw new PayPalRESTException("Payment fail");
        }
    }


}
