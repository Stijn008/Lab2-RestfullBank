package slim_shady.REST.bank;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="api/bank")
public class BankController {
    private final BankService service;

    @Autowired  // Dependency injection, service will be automatically instantiated and injected into the constructor
    public BankController(BankService service) {
        this.service = service;
    }

    // POST functionality
    @PostMapping
    public void addAccount(@RequestBody BankAccount account) {
        service.addAccount(account);
    }

    // GET functionality
    @GetMapping
    public float getBalance(String cardNumber) {
        return service.getBalance(cardNumber);
    }

    // PUT functionality
    @PutMapping(path = "add/{cardNumber}")
    public void addMoney(@PathVariable("cardNumber") String cardNumber, @RequestParam(required = true) float amount) {
        service.addMoney(cardNumber, amount);
    }

    // PUT functionality
    @PutMapping(path = "remove/{cardNumber}")
    public void removeMoney(@PathVariable("cardNumber") String cardNumber, @RequestParam(required = true) float amount) {
        service.removeMoney(cardNumber, amount);
    }

    // DELETE functionality
    @DeleteMapping(path = "{cardNumber}")
    public void deleteStudent(@PathVariable("cardNumber") String cardNumber) {
        service.deleteAccount(cardNumber);
    }
}
