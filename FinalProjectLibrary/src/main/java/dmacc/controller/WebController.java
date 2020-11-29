package dmacc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import dmacc.beans.Book;
import dmacc.beans.Checkout;
import dmacc.beans.Patron;
import dmacc.repository.BookRepository;
import dmacc.repository.CheckoutRepository;
import dmacc.repository.PatronRepository;

@Controller
public class WebController {
	@Autowired
	BookRepository repo;

	@Autowired
	PatronRepository repop;

	@Autowired
	CheckoutRepository repoc;

	@GetMapping("/viewAll") // viewing list of all books
	public String viewAllBooks(Model model) {
		if (repo.findAll().isEmpty()) {
			return addNewBook(model);
		}
		model.addAttribute("books", repo.findAll());
		return "results";
	}

	@GetMapping("/inputBook") // adding books
	public String addNewBook(Model model) {
		Book c = new Book();
		model.addAttribute("newBook", c);
		return "input";
	}

	@PostMapping("/inputBook") // landing adding book
	public String addNewBook(@ModelAttribute Book c, Model model) {
		repo.save(c);
		return viewAllBooks(model);
	}

	@GetMapping("/edit/{id}") // editing a book
	public String showUpdateBook(@PathVariable("id") long id, Model model) {
		Book c = repo.findById(id).orElse(null);
		System.out.println("BOOK TO EDIT: " + c.toString());
		model.addAttribute("newBook", c);
		return "input";
	}

	@PostMapping("/update/{id}") // landing page to update book
	public String reviseBook(Book c, Model model) {
		repo.save(c);
		return viewAllBooks(model);
	}

	@GetMapping("/delete/{id}") // deleting book
	public String deleteUser(@PathVariable("id") long id, Model model) {
		if (!repoc.findAll().isEmpty()) {
			Checkout c = repoc.findById(id).orElse(null);
			repoc.delete(c);
		}
		Book b = repo.findById(id).orElse(null);
		repo.delete(b);
		return viewAllBooks(model);
	}

	@GetMapping("/viewAllPatrons") // viewing all patrons checking out book
	public String viewAllPatrons(Model model) {

		if (repop.findAll().isEmpty()) {
			return addNewPatron(model);
		}
		model.addAttribute("patrons", repop.findAll());
		return "patronResults";
	}

	@GetMapping("/inputPatron") // adding a person
	public String addNewPatron(Model model) {
		Patron p = new Patron();
		model.addAttribute("newPatron", p);
		return "patron";
	}

	@PostMapping("/inputPatron") // landing page from adding person
	public String addNewPatron(@ModelAttribute Patron p, Model model) {
		repop.save(p);
		return viewAllPatrons(model);
	}

	@GetMapping("/editPatron/{id}") // editing a patron
	public String showUpdatePatron(@PathVariable("id") long id, Model model) {
		Patron p = repop.findById(id).orElse(null);
		System.out.println("PATRON TO EDIT: " + p.toString());
		model.addAttribute("newPatron", p);
		return "patron";
	}

	@PostMapping("/updatePatron/{id}") // landing adding patron
	public String revisePatron(Patron p, Model model) {
		repop.save(p);
		return viewAllPatrons(model);
	}

	@GetMapping("/deletePatron/{id}") // deleting patron
	public String deletePatron(@PathVariable("id") long id, Model model) {
		// method to check if patron is empty.
		if (!repoc.findAll().isEmpty()) {
			Checkout c = repoc.findById(id).orElse(null);
			repoc.delete(c);
		}
		Patron p = repop.findById(id).orElse(null);
		repop.delete(p);
		return viewAllPatrons(model);
	}

}