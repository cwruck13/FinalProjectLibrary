package dmacc.controller;

import java.time.LocalDate;

import org.springframework.context.annotation.Bean;

import dmacc.beans.Book;
import dmacc.beans.Checkout;
import dmacc.beans.Patron;

public class BeanConfiguration {

	@Bean
	public Book book() {
		Book bean = new Book();
		bean.setTitle("Jurassic Park");
		bean.setAuthor("Steven Spielberg");
		bean.setTotalCopies(3);
		bean.setAvailableCopies(2);
		return bean;
	}
	
	@Bean
	public Patron patron() {
		Patron bean = new Patron();
		bean.setName("Jenny McCarthy");
		return bean;
	}
	
	@Bean
	public Checkout checkout() {
		Checkout bean = new Checkout();
		bean.setCheckoutDate(LocalDate.now());
		bean.setDueDate(bean.getCheckoutDate().plusDays(14));  // add 14 days to checkout date to get due date
		bean.setCheckinDate(null);
		return bean;
	}
}
