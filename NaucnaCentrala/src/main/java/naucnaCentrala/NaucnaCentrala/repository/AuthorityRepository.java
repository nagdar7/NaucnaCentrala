package naucnaCentrala.NaucnaCentrala.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import naucnaCentrala.NaucnaCentrala.model.Authority;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
