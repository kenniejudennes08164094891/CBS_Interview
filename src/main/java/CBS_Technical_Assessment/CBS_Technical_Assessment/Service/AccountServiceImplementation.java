package CBS_Technical_Assessment.CBS_Technical_Assessment.Service;

import CBS_Technical_Assessment.CBS_Technical_Assessment.DTO.*;
import CBS_Technical_Assessment.CBS_Technical_Assessment.Entity.AccountEntity;
import CBS_Technical_Assessment.CBS_Technical_Assessment.Entity.DepositedFundsEntity;
import CBS_Technical_Assessment.CBS_Technical_Assessment.Repository.AccountRepo;
import CBS_Technical_Assessment.CBS_Technical_Assessment.Repository.DepositedFundsRepo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccountServiceImplementation extends AccountService{

    @Autowired()
    private AccountRepo accountRepo;

    @Autowired()
    private DepositedFundsRepo depositedFundsRepo;


    private ModelMapper modelMapper;
    @Autowired()
    public void AccountService(ModelMapper modelMapper){ this.modelMapper = modelMapper;}

    private void convertToDTO(AccountEntity account){
        modelMapper.map(account, AccountDTO.class, "account creation");
    }

    private CreateDepositDTO convertDepositToDTO(DepositedFundsEntity depositedFunds){
      return modelMapper.map(depositedFunds, CreateDepositDTO.class, "account deposit");
    }


//    private AccountEntity convertToEntity(AccountDTO accountDTO){
//        return modelMapper.map(accountDTO, AccountEntity.class);
//    }
private AccountEntity convertToEntity(AccountDTO accountDTO, String accountNumber, String cifId, BigDecimal currentAcctBalance){
   // Append the cifId and AccountNumber with the accountDTO before submitting to the accountEntity
        AccountEntity accountEntity = new AccountEntity();
        // copy the properties from the dto to the entity
    BeanUtils.copyProperties(accountDTO, accountEntity);
        accountEntity.setAccountNumber(accountNumber);
        accountEntity.setCifId(cifId);
        accountEntity.setCurrentAcctBalance(currentAcctBalance);
        return accountEntity;
}

private DepositedFundsEntity convertDepositToEntity(BigDecimal overallAcctBalance, BigDecimal amountDeposited, String formattedDate, String randomString, String accountNumber){
// update new Acct Balance to the Bigger Account entity
    AccountEntity accountEntity = new AccountEntity();
    accountEntity.setCurrentAcctBalance(overallAcctBalance);

    // save the other deposited entity details to the deposit_funds_entity
    DepositedFundsEntity depositedFundsEntity = new DepositedFundsEntity();
    depositedFundsEntity.setOverallBalance(overallAcctBalance);
    depositedFundsEntity.setAmountDeposited(amountDeposited);
    depositedFundsEntity.setTimeOfDeposit(formattedDate);
    depositedFundsEntity.setGeneratedTransactionId(randomString);
    depositedFundsEntity.setAccountNumber(accountNumber);
   // System.out.println("deposited entity>> " + depositedFundsEntity);
    return depositedFundsEntity;
}

    @Override
    public void createAccountDetails(AccountDTO accountDTO){

        //step1: Generate account number
        List<Integer> accountNumber = new ArrayList<>();
        Random random = new Random();
        for(int i=0; i<10; i++){
           int randomNumbers = random.nextInt(10);
            accountNumber.add(randomNumbers);
        }

        //convert accountNumber to a string
        List<List<Integer>> accNum = Arrays.asList(accountNumber);
        String AcctNumberArray = accNum.stream().map(Object::toString).collect(Collectors.joining());
        String requiredAcctNum = AcctNumberArray.replaceAll("[^0-9]", "");

        // Generate a unique 4-digit cifId attached to the account number
        Set<Integer> generatedCIFID = new HashSet<>();
        Random randomNumbers = new Random();
        while (generatedCIFID.isEmpty()){
            int randomcifId = randomNumbers.nextInt(9000) + 1000;
            generatedCIFID.add(randomcifId);
        }
        String requiredCifId = generatedCIFID.toString().replaceAll("[\\[\\]]", "");

        BigDecimal currentAcctBalance = BigDecimal.valueOf(0);

       // Call the convertToEntity
        AccountEntity accountVal = convertToEntity(accountDTO, requiredAcctNum, requiredCifId, currentAcctBalance);
        AccountEntity savedAccountData = accountRepo.save(accountVal);
        convertToDTO(savedAccountData);
    }

    @Override
    public FullCustomerProfileDTO getByCifId(String cifId){
        Optional<AccountEntity> accountEntity = accountRepo.findByCifId(cifId);
        if(accountEntity.isPresent()){
            AccountEntity myAccountEntity = accountEntity.get();
            List<DepositedFundsEntity> myDepositedEntity = depositedFundsRepo.findByAccountNumber(myAccountEntity.getAccountNumber());
            FullCustomerProfileDTO fullCustomerProfileDTO = new FullCustomerProfileDTO();
            fullCustomerProfileDTO.setBvn(myAccountEntity.getBvn());
            fullCustomerProfileDTO.setAddress(myAccountEntity.getAddress());
            fullCustomerProfileDTO.setDob(myAccountEntity.getDob());
            fullCustomerProfileDTO.setEmail(myAccountEntity.getEmail());
            fullCustomerProfileDTO.setGender(myAccountEntity.getGender());
            fullCustomerProfileDTO.setFirstName(myAccountEntity.getFirstName());
            fullCustomerProfileDTO.setLastName(myAccountEntity.getLastName());
            fullCustomerProfileDTO.setAccountNumber(myAccountEntity.getAccountNumber());
            fullCustomerProfileDTO.setOccupation(myAccountEntity.getOccupation());
            fullCustomerProfileDTO.setTitle(myAccountEntity.getTitle());
            fullCustomerProfileDTO.setRecentDeposits(myDepositedEntity);
            fullCustomerProfileDTO.setMiddleName(myAccountEntity.getMiddleName());
            fullCustomerProfileDTO.setRecentWithdrawals(myAccountEntity.getRecentWithdrawals());
            fullCustomerProfileDTO.setPhoneNumber(myAccountEntity.getPhoneNumber());
            fullCustomerProfileDTO.setMaritalStatus(myAccountEntity.getMaritalStatus());
            fullCustomerProfileDTO.setCountryOfResidence(myAccountEntity.getCountryOfResidence());
            fullCustomerProfileDTO.setCurrentAcctBalance(myAccountEntity.getCurrentAcctBalance());
            fullCustomerProfileDTO.setCifId(myAccountEntity.getCifId());
            return fullCustomerProfileDTO;
        }else{
            throw new IllegalStateException("This account does not exist!");
        }
       // return accountRepo.findByCifId(cifId);
    }

    @Override
    public BigDecimal getAccountBalanceValue(String accountNumber){
        Optional<AccountEntity> accountEntity = accountRepo.findByAccountNumber(accountNumber);
        if(accountEntity.isPresent()){
            return accountEntity.orElseThrow().getCurrentAcctBalance();
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account does not exist");
        }
    }

    @Override
    public CreateDepositDTO createDeposit(AmountDepositedDTO amountDeposited, String accountNumber) {
            // Check if account number exists in the database table
        Optional<AccountEntity> accountEntity = accountRepo.findByAccountNumber(accountNumber);
        if(accountEntity.isPresent()){

            // Get current Account Balance
            BigDecimal previousAccountBalance = new BigDecimal(String.valueOf(accountEntity.orElseThrow().getCurrentAcctBalance()));
            BigDecimal currentAmountDeposited = new BigDecimal(String.valueOf(amountDeposited.getAmountDeposited()));
            BigDecimal overallAcctBalance = previousAccountBalance.add(currentAmountDeposited);
//            System.out.println("overall summed act balance>> " + overallAcctBalance);

            //update the customer's account table with the new overall account balance and save it
            AccountEntity account = accountEntity.get();
            account.setCurrentAcctBalance(overallAcctBalance);
            accountRepo.save(account);

            // Get current Date and Time of Deposit
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = formatDate.format(new Date());

            //System.out.println("timeOfDeposit specified>> " + formattedDate);

            //Generate 11 digits transactionId
            StringBuilder randomString = new StringBuilder();
            Random random = new Random();
            for (int i=0; i< 11; i++){
                randomString.append(random.nextInt(10));
            }
          //  System.out.println("Generated Transaction Id>> " + randomString.toString());

            DepositedFundsEntity depositedFundsEntity = convertDepositToEntity( overallAcctBalance, amountDeposited.getAmountDeposited(),formattedDate, randomString.toString(),accountNumber);
           // System.out.println("DTO deposited>>> " + depositedFundsEntity);

            DepositedFundsEntity saveDepositedEntity = depositedFundsRepo.save(depositedFundsEntity);

//        Optional<AccountEntity> accountEntityOptional = accountRepo.findByAccountNumber(accountNumber);
            return convertDepositToDTO(saveDepositedEntity);
        }else{
            throw new IllegalStateException("This account does not exist!");
        }

    }

    @Override
    public List<AccountEntity> getAllCustomerData(){
        System.out.println("all deposited arrays>> " + accountRepo.findAll());
        return accountRepo.findAll();
    }


    @Override
    public List<DepositedFundsEntity> getCustomerTransactions(){
        return depositedFundsRepo.findAll();
    }


}
