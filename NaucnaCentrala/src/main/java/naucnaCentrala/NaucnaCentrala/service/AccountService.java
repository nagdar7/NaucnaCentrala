package naucnaCentrala.NaucnaCentrala.service;

import java.util.List;

import naucnaCentrala.NaucnaCentrala.model.Account;

public interface AccountService {

    Account save(Account account);

    Account findByUsername(String username);

    Account findById(Long id);

    List<Account> getAllReviewers();

    List<Account> getAllEditors();
}
