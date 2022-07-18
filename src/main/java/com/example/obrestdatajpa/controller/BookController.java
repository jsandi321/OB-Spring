package com.example.obrestdatajpa.controller;

import com.example.obrestdatajpa.entities.Book;
import com.example.obrestdatajpa.repositories.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    //Atributos
    private BookRepository bookRepository;
    //Constructores
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    //Getter and Setters

    //Operaciones CRUD para la entidad Book

    //Buscar todos los libros en base de datos (lista de libros)
    @GetMapping("/api/books")
    public List<Book> finAll(){
        //recuperar y devolver los libros en base de datos
        return bookRepository.findAll();
    }

    //buscar un solo libro en la base de datos segun id
    @GetMapping("/api/books/{id}")
    public ResponseEntity<Book> findOneById(@PathVariable Long id){

        Optional<Book> bookOpt = bookRepository.findById(id);
        //Opcion 1
        //Opcion reducida = return bookOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        if(bookOpt.isPresent()){
            return ResponseEntity.ok(bookOpt.get());
        }
        else{
            return ResponseEntity.notFound().build();
        }
        //Opcion 2
        //return bookOpt.orElse(null);
    }

    //crear un nuevo libro en base de datos
    @PostMapping("/api/books")
    public Book create(@RequestBody Book book){
        //Guardar el libro recibido en la base de datos
        return bookRepository.save(book);
    }

    //actualizar un libro existente en base de datos

    //borrar un libro en base de datos

}
