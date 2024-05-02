package CBS_Technical_Assessment.CBS_Technical_Assessment.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Setter @Getter
@Data
public class AccountDTO {
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
}

//For deposits/withdrawals...you need acctNum, narration, amount, transactionType: 'deposit or withdrawals'