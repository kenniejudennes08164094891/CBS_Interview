package CBS_Technical_Assessment.CBS_Technical_Assessment.DTO;

import CBS_Technical_Assessment.CBS_Technical_Assessment.Entity.DepositedFundsEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class FullCustomerProfileDTO {
    private String bvn;
    private String title;
    private String firstName;
    private String middleName;
    private String lastName;
    private String dob;
    private String email;
    private String phoneNumber;
    private String address;
    private String occupation;
    private String gender;
    private String maritalStatus;
    private String countryOfResidence;
    private BigDecimal currentAcctBalance;
    private String accountNumber;
    private String cifId;

    private List<DepositedFundsEntity> recentDeposits;
    private String recentWithdrawals;
}
