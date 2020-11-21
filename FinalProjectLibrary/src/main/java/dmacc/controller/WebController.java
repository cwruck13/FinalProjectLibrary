package dmacc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import dmacc.beans.Book;
import dmacc.beans.Patron;
import dmacc.repository.BookRepository;
import dmacc.repository.PatronRepository;


@Controller
public class WebController {
	@Autowired
	BookRepository repo;
	@Autowired
	PatronRepository repop;
	
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
	
	@PostMapping("/updatePatron/{id}")
	public String revisePatron(Patron p, Model model) {
		repop.save(p);
		return viewAllPatrons(model);
	}
	 
}