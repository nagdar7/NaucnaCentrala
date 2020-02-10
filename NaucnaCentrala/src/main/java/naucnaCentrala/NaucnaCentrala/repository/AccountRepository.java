package naucnaCentrala.NaucnaCentrala.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import naucnaCentrala.NaucnaCentrala.model.Account;
import naucnaCentrala.NaucnaCentrala.model.Authority;

import java.util.List;
import java.util.Set;

public interface AccountRepository extends JpaRepository<Account, Long> {

   Account findByUsername(String username);

   Account findByUsernameAndActiveIsTrue(String username);

   List<Account> findAllByAuthoritiesIn(Set<Authority> authorities);
}
