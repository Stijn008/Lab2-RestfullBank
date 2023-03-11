package slim_shady.REST.bank;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service        // Automatically instantiate
public class BankService {
    private final BankAccountRepository repository;

    @Autowired
    public BankService(BankAccountRepository repository) {
        this.repository = repository;
    }

    // POST functionality
    public synchronized void addAccount(BankAccount account) {
        Optional<BankAccount> accountOptional = repository.findById(account.getCardNumber());
        if (accountOptional.isPresent()) {
            throw new IllegalStateException("Card-number is already being used");
        }
        repository.save(account);
    }

    // GET functionality
    @Transactional
    public synchronized float getBalance(String cardNumber) {
        BankAccount account = repository.findBankAccountByCardNumber(cardNumber).orElseThrow(() ->
                new IllegalStateException("There is no account with card-number " + cardNumber));
        return account.getBalance();
    }

    // PUT functionality
    @Transactional
    public synchronized void addMoney(String cardNumber, float amount) {
        BankAccount account = repository.findBankAccountByCardNumber(cardNumber).orElseThrow(() ->
                new IllegalStateException("There is no account with card-number " + cardNumber));
        account.setBalance(account.getBalance() + amount);
    }

    // PUT functionality
    @Transactional
    public synchronized void removeMoney(String cardNumber, float amount) {
        BankAccount account = repository.findBankAccountByCardNumber(cardNumber).orElseThrow(() ->
                new IllegalStateException("There is no account with card-number " + cardNumber));
        if (account.getBalance() < amount) {
            throw new IllegalStateException("Not enough funds");
        }
        account.setBalance(account.getBalance() - amount);
    }

    // DELETE functionality
    public synchronized void deleteAccount(String cardNumber) {
        if (!repository.existsById(cardNumber)) {
            throw new IllegalStateException("There is no account with card-number" + cardNumber);
        }
        repository.deleteById(cardNumber);
    }
}
