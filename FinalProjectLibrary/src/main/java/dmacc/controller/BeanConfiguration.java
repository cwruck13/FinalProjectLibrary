package dmacc.controller;

import org.springframework.context.annotation.Bean;

import dmacc.beans.Book;

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

}
