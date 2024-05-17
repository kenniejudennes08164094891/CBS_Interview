package CBS_Technical_Assessment.CBS_Technical_Assessment.Controller;

import CBS_Technical_Assessment.CBS_Technical_Assessment.DTO.*;
import CBS_Technical_Assessment.CBS_Technical_Assessment.Entity.AccountEntity;
import CBS_Technical_Assessment.CBS_Technical_Assessment.Entity.DepositedFundsEntity;
import CBS_Technical_Assessment.CBS_Technical_Assessment.Service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("v1/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/create-account-details")
    //create customer's account
    public HttpResponseDTO createAccount(@RequestBody AccountDTO accountDTO){
       accountService.createAccountDetails(accountDTO);
           HttpResponseDTO httpResponseDTO = new HttpResponseDTO();
            String responseMessage = "Your Account has been created successfully!";
            httpResponseDTO.setResponseMessage(responseMessage);
            return httpResponseDTO;
    }

    @GetMapping("/fetch-all-account-details")
    public ResponseEntity<Map<String, Object>> getAllAccounts(){
     //   return accountService.getAllCustomerData();
        List<AccountEntity> allAccountData = accountService.getAllCustomerData();
        Map<String, Object> responseBody = new HashMap<>();
        String responseMessage = "All account details has been retrieved successfully";
        responseBody.put("message", responseMessage);
        responseBody.put("details", allAccountData);
        return ResponseEntity.ok().body(responseBody);
    }



    @GetMapping("/fetch-account-profile/{cifId}")
    //Get customers details by cifId
    public ResponseEntity<Map<String, Object>> getAccountByCifId(@PathVariable String cifId){
        FullCustomerProfileDTO accountEntity = accountService.getByCifId(cifId);
        if(cifId != null){
            Map<String, Object> responseBody = new HashMap<>();
            String responseMessage = "Your account details has been retrieved successfully";
            responseBody.put("message", responseMessage);
            responseBody.put("details", accountEntity);
            return ResponseEntity.ok().body(responseBody);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cifId cannot be null");
        }
    }

    @GetMapping("/fetch-account-balance")
    // Get customer's account balance
    public ResponseEntity<Map<String, Object>> getCustomerAccountBalance(@RequestParam("accountNumber") String accountNumber){
        BigDecimal currentAcctBalance = accountService.getAccountBalanceValue(accountNumber);
        if(currentAcctBalance != null){
            Map<String, Object> responseBody = new HashMap<>();
            String responseMessage = "Your account balance has been retrieved successfully";
            responseBody.put("message", responseMessage);
            responseBody.put("currentAcctBalance", currentAcctBalance);
            return ResponseEntity.ok().body(responseBody);
        }else{
            Map<String , Object> errorResponse = new HashMap<>();
            String errorMessage = "Account not found for the provided account number";
            errorResponse.put("message",errorMessage);
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/deposit-funds")
    public ResponseEntity<Map<String, Object>> createDeposit(@RequestBody AmountDepositedDTO amountDeposited , @RequestParam String accountNumber) throws JsonProcessingException {
       CreateDepositDTO depositAmount = accountService.createDeposit(amountDeposited, accountNumber);
        Map<String, Object> responseBody = new HashMap<>();
        String responseMessage = "Amount's deposited successfully";
        responseBody.put("message", responseMessage);
        responseBody.put("deposit details", depositAmount);
//        log.info("createDeposit>> {}", amountDeposited);
        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping("/fetch-all-transactions")
    public ResponseEntity<Map<String, Object>> getAllDepositedFunds(){

        List<DepositedFundsEntity> allTransactions = accountService.getCustomerTransactions();
        Map<String, Object> responseBody = new HashMap<>();
        String responseMessage = "All Transaction details has been retrieved successfully";
        responseBody.put("message", responseMessage);
        responseBody.put("details", allTransactions);
        return ResponseEntity.ok().body(responseBody);
    }
}
