package CBS_Technical_Assessment.CBS_Technical_Assessment.Service;

import CBS_Technical_Assessment.CBS_Technical_Assessment.DTO.AccountDTO;
import CBS_Technical_Assessment.CBS_Technical_Assessment.DTO.AccountNumberGenerationDTO;
import CBS_Technical_Assessment.CBS_Technical_Assessment.Entity.AccountEntity;
import CBS_Technical_Assessment.CBS_Technical_Assessment.Repository.AccountRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service()
public class AccountServiceImplementation extends AccountService{

    @Autowired()
    private AccountRepo accountRepo;

    private ModelMapper modelMapper;
    @Autowired()
    public void AccountService(ModelMapper modelMapper){ this.modelMapper = modelMapper;}

    private AccountDTO convertToDTO(AccountEntity account){
        return modelMapper.map(account, AccountDTO.class, "account creation");
    }


//    private AccountEntity convertToEntity(AccountDTO accountDTO){
//        return modelMapper.map(accountDTO, AccountEntity.class);
//    }
private AccountEntity convertToEntity(AccountDTO accountDTO, String accountNumber, String cifId){
   // Append the cifId and AccountNumber with the accountDTO before submitting to the accountEntity
        AccountEntity accountEntity = new AccountEntity();
        // copy the properties from the dto to the entity
    BeanUtils.copyProperties(accountDTO, accountEntity);
        accountEntity.setAccountNumber(accountNumber);
        accountEntity.setCifId(cifId);
        return accountEntity;
}

    @Override
    public void createAccountDetails(AccountDTO accountDTO){

        //step1: Generate account number
        AccountNumberGenerationDTO accountGenerationDTO = new AccountNumberGenerationDTO();
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
      // System.out.println("unique cifId>>> " + requiredCifId);

       // Call the convertToEntity
        AccountEntity accountVal = convertToEntity(accountDTO, requiredAcctNum, requiredCifId);
        AccountEntity savedAccountData = accountRepo.save(accountVal);
        convertToDTO(savedAccountData);
    }

    @Override
    public Optional<AccountEntity> getByCifId(String cifId){
//        Optional<AccountEntity> accountEntityOptional = accountRepo.findByCifId(cifId);
//        return accountEntityOptional.map(this::convertToDTO).orElse(null);
        return accountRepo.findByCifId(cifId);
    }

    @Override
    public Long getAccountBalanceValue(String accountNumber){
        AccountEntity accountEntity = accountRepo.findByAccountNumber(accountNumber);
        if(accountEntity != null){
            return accountEntity.getAccountBalance();
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account cannot be null");
        }
    }


}
