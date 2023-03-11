package slim_shady.REST;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import slim_shady.REST.bank.BankAccount;

import java.util.HashMap;
import java.util.Map;

//@RestController
//@RequestMapping(path="api/bank")
public class Client2 implements Runnable {
    private final String name;
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public Client2(String name) {
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
        getBalance("BE32 0079 1246 9756");
        addMoney("BE32 0079 1246 9756", 100);
        getBalance("BE32 0079 1246 9756");
        sleep(100);
        removeMoney("BE32 0079 1246 9756", 100);
        getBalance("BE32 0079 1246 9756");
        sleep(100);
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
    public void addAccount(@RequestBody BankAccount account) {
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
    @GetMapping
    public void getBalance(String cardNumber) {
        String url = baseUrl + "?cardNumber=" + cardNumber;
        float balance = restTemplate.getForObject(url, Float.class);
        System.out.println("<" + this.name + "> - Balance of card-number " + cardNumber + " is: " + balance);
    }

    // PUT functionality
    @PutMapping(path = "/add/{cardNumber}")
    public void addMoney(@PathVariable("cardNumber") String cardNumber, @RequestParam(required = true) float amount) {
        String url = baseUrl + "/add/" + cardNumber + "?amount=" + amount;
        restTemplate.put(url, null);
        System.out.println("<" + this.name + "> - Added " + amount + " from card-number " + cardNumber);
    }

    // PUT functionality
    @PutMapping(path = "/remove/{cardNumber}")
    public void removeMoney(@PathVariable("cardNumber") String cardNumber, @RequestParam(required = true) float amount) {
        String url = baseUrl + "/remove/" + cardNumber + "?amount=" + amount;
        restTemplate.put(url, null);
        System.out.println("<" + this.name + "> - Removed " + amount + " from card-number " + cardNumber);
    }

    // DELETE functionality
    @DeleteMapping(path = "/{cardNumber}")
    public void deleteAccount(@PathVariable("cardNumber") String cardNumber) {
        String url = baseUrl + "/" + cardNumber;
        restTemplate.delete(url);
        System.out.println("<" + this.name + "> - Deleted Account with card-number " + cardNumber);
    }
}