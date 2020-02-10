package naucnaCentrala.NaucnaCentrala.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import naucnaCentrala.NaucnaCentrala.model.Account;
import naucnaCentrala.NaucnaCentrala.model.Authority;
import naucnaCentrala.NaucnaCentrala.repository.AccountRepository;
import naucnaCentrala.NaucnaCentrala.service.AccountService;

import java.util.List;
import java.util.stream.Collectors;

import naucnaCentrala.NaucnaCentrala.repository.AuthorityRepository;

@Service
public class AccountServiceImpl implements AccountService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public Account save(Account account) {
        log.info("save, account: {}", account.toString());
        return this.accountRepository.save(account);
    }

    @Override
    public Account findByUsername(String username) {
        log.info("findByUsername, username: {}", username);
        return this.accountRepository.findByUsernameAndActiveIsTrue(username);
    }

    @Override
    public Account findById(Long id) {
        log.info("findByUsername, id: {}", id);
        return this.accountRepository.getOne(id);
    }

    /**
     * @return a list of all the authorities
     */
    public List<String> getAuthorities() {
        log.info("getAuthorities");
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }
}
