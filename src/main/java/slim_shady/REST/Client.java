package slim_shady.REST;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import slim_shady.REST.bank.BankAccount;
import slim_shady.REST.bank.*;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

//@RestController
//@RequestMapping(path="api/bank")
public class Client implements Runnable {
    private final String name;
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public Client(String name) {
        this.name = name;
        this.restTemplate = new RestTemplate();
        this.baseUrl = "http://localhost:8080/api/bank";
        System.out.println("<---> " + name + " Instantiated <--->");
    }

    public void run() {
        while (true) {
            try {
                runSequence();
            } catch (Exception e) {
                System.out.println("\t"+e.getMessage());
            }
        }
    }

    public void runSequence() {
        addAccount(new BankAccount("BE52 1644 5197 8315", "Mario De Ridder", 500));
        getBalance("BE52 1644 5197 8315");
        sleep(500);
        addMoney("BE52 1644 5197 8315", 100);
        getBalance("BE52 1644 5197 8315");
        sleep(500);
        removeMoney("BE52 1644 5197 8315", 50);
        getBalance("BE52 1644 5197 8315");
        sleep(500);
        deleteAccount("BE52 1644 5197 8315");
        sleep(5000);
    }

    public void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // POST functionality
    @PostMapping
    public void addAccount(BankAccount account) {
        String url = baseUrl;
        //restTemplate.postForObject(url, account, Void.class);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("cardNumber", account.getCardNumber());
        requestBody.put("name", account.getName());
        requestBody.put("balance", account.getBalance());
        restTemplate.postForObject(url, requestBody, Void.class);

        System.out.println("<" + this.name + "> - Add account with card-number " + account.getCardNumber());
    }

    // GET functionality
    public void getBalance(String cardNumber) {
        String url = baseUrl + "?cardNumber=" + cardNumber;
        float balance = restTemplate.getForObject(url, Float.class);
        System.out.println("<" + this.name + "> - Balance of card-number " + cardNumber + " is: " + balance);
    }

    // PUT functionality
    public void addMoney(String cardNumber, float amount) {
        String url = baseUrl + "/add/" + cardNumber + "?amount=" + amount;
        restTemplate.put(url, null);
        System.out.println("<" + this.name + "> - Added " + amount + " from card-number " + cardNumber);
    }

    // PUT functionality
    public void removeMoney(String cardNumber, float amount) {
        String url = baseUrl + "/remove/" + cardNumber + "?amount=" + amount;
        restTemplate.put(url, null);
        System.out.println("<" + this.name + "> - Removed " + amount + " from card-number " + cardNumber);
    }

    // DELETE functionality
    public void deleteAccount(String cardNumber) {
        String url = baseUrl + "/" + cardNumber;
        restTemplate.delete(url);
        System.out.println("<" + this.name + "> - Deleted Account with card-number " + cardNumber);
    }
}