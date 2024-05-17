package CBS_Technical_Assessment.CBS_Technical_Assessment.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class DepositedFundsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private BigDecimal amountDeposited;
    private BigDecimal overallBalance;
    private String timeOfDeposit;
    private String generatedTransactionId;
    private String accountNumber;

}
