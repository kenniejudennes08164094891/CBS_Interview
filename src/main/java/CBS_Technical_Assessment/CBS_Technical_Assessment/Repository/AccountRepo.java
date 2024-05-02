package CBS_Technical_Assessment.CBS_Technical_Assessment.Repository;

import CBS_Technical_Assessment.CBS_Technical_Assessment.Entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<AccountEntity, Long> {

  Optional<AccountEntity> findByCifId(String cifId);

  //@Query("SELECT a.accountNumber FROM AccountEntity a") //where a stands for the first letter in AccountEntity
  AccountEntity findByAccountNumber(String accountNumber);

  //  List<AccountEntity> getAccountsByCifId(String cifId);
}
