package naucnaCentrala.NaucnaCentrala.security;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import naucnaCentrala.NaucnaCentrala.model.Account;
import naucnaCentrala.NaucnaCentrala.service.AccountService;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Account account = null;
        try {
            account = this.accountService.findByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (account == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserWithRoles(account.getId().toString(), account.getUsername(), account.getPassword(),
                account.getAuthorities().stream().map(a -> a.getName()).collect(Collectors.joining(",")));
    }
}
