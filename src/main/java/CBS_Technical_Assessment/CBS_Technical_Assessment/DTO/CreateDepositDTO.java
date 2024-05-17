package CBS_Technical_Assessment.CBS_Technical_Assessment.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@Data
@ToString
public class CreateDepositDTO {
    private BigDecimal amountDeposited;
    private BigDecimal overallBalance;

    private String timeOfDeposit;

    private  String generatedTransactionId;

}

