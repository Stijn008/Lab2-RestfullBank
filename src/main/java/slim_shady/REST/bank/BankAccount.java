package slim_shady.REST.bank;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@ToString(callSuper=true, includeFieldNames=true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class BankAccount {
    @Id
    @SequenceGenerator(
            name = "account_sequence",
            sequenceName = "account_sequence",
            allocationSize = 1
    )

    @Getter @Setter private String cardNumber;
    @Getter @Setter private String name;
    @Getter @Setter private float balance=0;
}