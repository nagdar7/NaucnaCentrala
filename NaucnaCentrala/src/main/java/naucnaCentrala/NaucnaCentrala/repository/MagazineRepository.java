package naucnaCentrala.NaucnaCentrala.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import naucnaCentrala.NaucnaCentrala.model.Magazine;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface MagazineRepository extends JpaRepository<Magazine, String> {

    Magazine findOneById(Long id);

    List<Magazine> findByActiveIsTrue();
}
