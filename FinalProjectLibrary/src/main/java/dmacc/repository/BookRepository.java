package dmacc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dmacc.beans.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
