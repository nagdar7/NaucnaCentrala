package naucnaCentrala.NaucnaCentrala.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import naucnaCentrala.NaucnaCentrala.model.ScientificCommittee;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface ScientificCommitteeRepository extends JpaRepository<ScientificCommittee, String> {
}
