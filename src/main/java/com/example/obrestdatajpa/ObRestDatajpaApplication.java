package com.example.obrestdatajpa;

import com.example.obrestdatajpa.entities.Book;
import com.example.obrestdatajpa.repositories.BookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
public class ObRestDatajpaApplication {

	public static void main(String[] args) {

		ApplicationContext context = run(ObRestDatajpaApplication.class, args);
		BookRepository repository = context.getBean(BookRepository.class);

		//Operaciones CRUD (no se hace aqui pero para la pruebas)
		//Crear Libro
		Book book01 = new Book(null, "Danza Dragones", "RR Martin", 1900, 55.90, LocalDate.of(2008, 12, 1), true);
		Book book02 = new Book(null, "Festin de Cuervos", "RR Martin", 1453, 60.99, LocalDate.of(2004, 12, 1), true);

		//almacenas libro
		repository.save(book01);
		repository.save(book02);
		//recuperar los libros
		System.out.println(repository.findAll().size());
		//borrar un libro
		//repository.deleteById(1L);

		System.out.println(repository.findAll().size());
	}

}
