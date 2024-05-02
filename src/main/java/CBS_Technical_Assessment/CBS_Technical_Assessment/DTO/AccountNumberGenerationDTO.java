package CBS_Technical_Assessment.CBS_Technical_Assessment.DTO;

import java.util.List;

public class AccountNumberGenerationDTO {
    private List<Integer> numbers;

    public List<Integer> getAccountNumberGenerated(){
        return this.numbers;
    }

    public void setAccountNumberToGenerate(List<Integer> accountNumber){
         this.numbers = accountNumber;
    }
}
