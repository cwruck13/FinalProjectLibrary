package dmacc.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String title;
	private String author;
	private int totalCopies;
	private int availableCopies;

	public Book(String title) {
			this.title = title;
		}

	public Book(String title, String author, int totalCopies, int availableCopies) {
			this.title = title;
			this.author = author;
			this.totalCopies = totalCopies;
			this.availableCopies = availableCopies;
		}

}