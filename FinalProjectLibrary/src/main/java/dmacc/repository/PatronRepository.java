package dmacc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dmacc.beans.Patron;

public interface PatronRepository extends JpaRepository<Patron, Long> {

}
