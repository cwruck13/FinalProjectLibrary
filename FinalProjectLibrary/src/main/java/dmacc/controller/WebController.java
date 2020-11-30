package dmacc.controller;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

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
	
	// staticP is a static variable that references a Patron object.
	// The purpose of this is to be able to persist a Patron object
	// during the checkout process when navigating from the 
	// patronResults.html page to the checkout method to the
	// checkout.html page and then finally to the 
	// addCheckout method that creates and persists the
	// checkout object.
	static Patron staticP = new Patron();
	
	@GetMapping("/viewAll")
		public String viewAllBooks(Model model) {
			if(repo.findAll().isEmpty()) {    
				return addNewBook(model);   
			}   
			model.addAttribute("books", repo.findAll());
			return "results";
		}
	
	@GetMapping("/inputBook")
		public String addNewBook(Model model) {
			Book c = new Book();
			model.addAttribute("newBook", c);
			return "input";
			}
	
	@PostMapping("/inputBook")
		public String addNewBook(@ModelAttribute Book c, Model model) {
			repo.save(c);
			return viewAllBooks(model);
		}

	@GetMapping("/edit/{id}")
		public String showUpdateBook(@PathVariable("id") long id, Model model) {
			Book c = repo.findById(id).orElse(null);
			System.out.println("BOOK TO EDIT: " + c.toString());
			model.addAttribute("newBook", c);
			return "input";
	}
	@PostMapping("/update/{id}")
	public String reviseBook(Book c, Model model) {
	repo.save(c);
	return viewAllBooks(model);
	}
	@GetMapping("/delete/{id}")
		public String deleteUser(@PathVariable("id") long id, Model model) {
			Book c = repo.findById(id).orElse(null);
			repo.delete(c);
			return viewAllBooks(model);
	}
	
	@GetMapping("/viewAllPatrons")
	public String viewAllPatrons(Model model) {
		if(repop.findAll().isEmpty()) {    
			return addNewPatron(model);   
		}   
		model.addAttribute("patrons", repop.findAll());
		return "patronResults";
	}
	
	@GetMapping("/inputPatron")
	public String addNewPatron(Model model) {
		Patron p = new Patron();
		model.addAttribute("newPatron", p);
		return "patron";
	}
	
	@PostMapping("/inputPatron")		
	public String addNewPatron(@ModelAttribute Patron p, Model model) {
		repop.save(p);
		return viewAllPatrons(model);
	}
	
	@GetMapping("/editPatron/{id}")
	public String revisePatron(@PathVariable("id") long id,Model model) {
		Patron p = repop.findById(id).orElse(null);
		model.addAttribute("newPatron", p);
		return "patron";
		
	}
	@PostMapping("/updatePatron/{id}")
	public String revisePatron(Patron p, Model model) {
		repop.save(p);
		return viewAllPatrons(model);
	}
	 
	@GetMapping("/checkout/{id}")
	public String addNewCheckouts(@PathVariable("id") long id, Model model) {
		
		staticP = repop.findById(id).orElse(null);
		
		model.addAttribute("patron", staticP);
		
		List<Book> allBooks = new ArrayList<Book>();
		
		List<Book> availableBooks = new ArrayList<Book>();
		
		allBooks = repo.findAll();
		
		for (Book b : allBooks) {
			if (b.getAvailableCopies() > 0) {
				availableBooks.add(b);
			}
		}
		
    	model.addAttribute("availableBooks", availableBooks);
		
		return "checkout";
	}
	
	@GetMapping("/addCheckout/{id}")
	public String addCheckout(@PathVariable("id") long id,  Model model) {
			
		Book b = repo.findById(id).orElse(null);
		
		Checkout c = new Checkout();
		
		// Get today's date
		LocalDate today = LocalDate.now();
		
		// Book is due in two weeks
		LocalDate dueDate = today.plusDays(14);
		
		c.setBook(b);
		c.setPatron(staticP);
		c.setCheckoutDate(today);
		c.setDueDate(dueDate);
		
		// Verify book is available.
		// If not available then don't
		// persist the checkout object.
		// Otherwise, decrement the number
		// of available copies and persist
		if (b.getAvailableCopies() < 1) {
			return viewAllPatrons(model);
		} else {
			b.setAvailableCopies(b.getAvailableCopies() - 1);
			repo.save(b);
		}
		
		repoc.save(c);
		
		return viewAllPatrons(model);    
	}
}