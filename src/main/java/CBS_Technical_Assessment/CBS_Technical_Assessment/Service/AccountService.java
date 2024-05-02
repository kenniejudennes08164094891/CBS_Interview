package CBS_Technical_Assessment.CBS_Technical_Assessment.Service;

import CBS_Technical_Assessment.CBS_Technical_Assessment.DTO.AccountDTO;
import CBS_Technical_Assessment.CBS_Technical_Assessment.Entity.AccountEntity;

import java.util.Optional;

public abstract class AccountService {
    public abstract void createAccountDetails(AccountDTO accountDTO);

    public abstract Optional<AccountEntity> getByCifId(String cifId);

    public abstract Long getAccountBalanceValue(String accountNumber);
}
