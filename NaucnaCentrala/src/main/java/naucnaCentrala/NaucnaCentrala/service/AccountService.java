package naucnaCentrala.NaucnaCentrala.service;

import naucnaCentrala.NaucnaCentrala.model.Account;

public interface AccountService {

    Account save(Account account);

    Account findByUsername(String username);

    Account findById(Long id);
}
