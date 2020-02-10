package naucnaCentrala.NaucnaCentrala.service;

import java.util.List;

import naucnaCentrala.NaucnaCentrala.model.Magazine;

public interface MagazineService {

    List<Magazine> findAll();

    Magazine getOne(Long id);

    void payMembership(Long id, String username);
}
