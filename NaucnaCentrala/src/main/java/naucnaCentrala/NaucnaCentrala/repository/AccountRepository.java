package naucnaCentrala.NaucnaCentrala.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import naucnaCentrala.NaucnaCentrala.model.Account;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

   Account findByUsername(String username);

   Account findByUsernameAndActiveIsTrue(String username);

}
