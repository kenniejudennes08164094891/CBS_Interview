package CBS_Technical_Assessment.CBS_Technical_Assessment.Controller;

import CBS_Technical_Assessment.CBS_Technical_Assessment.DTO.AccountDTO;
import CBS_Technical_Assessment.CBS_Technical_Assessment.DTO.HttpResponseDTO;
import CBS_Technical_Assessment.CBS_Technical_Assessment.Entity.AccountEntity;
import CBS_Technical_Assessment.CBS_Technical_Assessment.Service.AccountService;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
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

    @GetMapping("/fetch-account-profile/{cifId}")
    //Get customers details by cifId
//    public Optional<AccountEntity> getAccountByCifId(@PathVariable String cifId){
//
//        if(cifId != null){
//             return accountService.getByCifId(cifId);
//        }else{
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cifId cannot be null");
//        }
//    }
    public ResponseEntity<Map<String, Object>> getAccountByCifId(@PathVariable String cifId){
        Optional<AccountEntity> accountEntity = accountService.getByCifId(cifId);

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
        Long accountBalance = accountService.getAccountBalanceValue(accountNumber);
        if(accountBalance != null){
            Map<String, Object> responseBody = new HashMap<>();
            String responseMessage = "Your account balance has been retrieved successfully";
            responseBody.put("message", responseMessage);
            responseBody.put("accountBalance", accountBalance);
            return ResponseEntity.ok().body(responseBody);
        }else{
            Map<String , Object> errorResponse = new HashMap<>();
            String errorMessage = "Account not found for the provided account number";
            errorResponse.put("message",errorMessage);
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
