package naucnaCentrala.NaucnaCentrala.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import naucnaCentrala.NaucnaCentrala.model.Account;
import naucnaCentrala.NaucnaCentrala.model.Authority;
import naucnaCentrala.NaucnaCentrala.model.Magazine;
import naucnaCentrala.NaucnaCentrala.repository.AccountRepository;
import naucnaCentrala.NaucnaCentrala.service.AccountService;
import naucnaCentrala.NaucnaCentrala.service.MagazineService;

import java.util.List;
import java.util.stream.Collectors;

import naucnaCentrala.NaucnaCentrala.repository.AuthorityRepository;
import naucnaCentrala.NaucnaCentrala.repository.MagazineRepository;

@Service
public class MagazineServiceImpl implements MagazineService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MagazineRepository magazineRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public List<Magazine> findAll() {
        log.info("findAll");
        return magazineRepository.findByActiveIsTrue();
    }

}
