package CBS_Technical_Assessment.CBS_Technical_Assessment.Service;

import CBS_Technical_Assessment.CBS_Technical_Assessment.DTO.AccountDTO;
import CBS_Technical_Assessment.CBS_Technical_Assessment.DTO.AmountDepositedDTO;
import CBS_Technical_Assessment.CBS_Technical_Assessment.DTO.CreateDepositDTO;
import CBS_Technical_Assessment.CBS_Technical_Assessment.DTO.FullCustomerProfileDTO;
import CBS_Technical_Assessment.CBS_Technical_Assessment.Entity.AccountEntity;
import CBS_Technical_Assessment.CBS_Technical_Assessment.Entity.DepositedFundsEntity;

import java.math.BigDecimal;
import java.util.List;

public abstract class AccountService {
    public abstract void createAccountDetails(AccountDTO accountDTO);

    public abstract FullCustomerProfileDTO getByCifId(String cifId);

    public abstract BigDecimal getAccountBalanceValue(String accountNumber);

    public abstract CreateDepositDTO createDeposit(AmountDepositedDTO amountDeposited, String accountNumber);

    public abstract List<AccountEntity> getAllCustomerData();

    public abstract List<DepositedFundsEntity> getCustomerTransactions();
}
