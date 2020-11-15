package dmacc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import dmacc.beans.Book;
import dmacc.repository.BookRepository;


@Controller
public class WebController {
	@Autowired
	BookRepository repo;
	
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
	 
}