package CBS_Technical_Assessment.CBS_Technical_Assessment.Repository;

import CBS_Technical_Assessment.CBS_Technical_Assessment.Entity.DepositedFundsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DepositedFundsRepo  extends JpaRepository<DepositedFundsEntity, Long> {

    List<DepositedFundsEntity> findByAccountNumber(String accountNumber);

}
