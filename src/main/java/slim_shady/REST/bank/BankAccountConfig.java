package slim_shady.REST.bank;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BankAccountConfig {
    @Bean
    CommandLineRunner commandLineRunner(BankAccountRepository repository) {
        return args -> {
            BankAccount bart = new BankAccount("BE32 0079 1246 9756", "Bart De Bruyne", 500);
            BankAccount peter = new BankAccount("BE64 2639 6592 3381", "Peter De Keyser", 12000);
            repository.saveAll(List.of(bart, peter));
        };
    }
}
