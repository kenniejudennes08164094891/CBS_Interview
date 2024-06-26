package CBS_Technical_Assessment.CBS_Technical_Assessment.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity()
@Table(name = "account_table")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
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

    @OneToMany(cascade = CascadeType.ALL)
    private List<DepositedFundsEntity> recentDeposits;
    private String recentWithdrawals;
}


