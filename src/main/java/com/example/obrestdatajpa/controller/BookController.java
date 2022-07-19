package com.example.obrestdatajpa.controller;

import com.example.obrestdatajpa.entities.Book;
import com.example.obrestdatajpa.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    private final Logger log = LoggerFactory.getLogger(BookController.class);
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

    /**
     *
     * @param id
     * @return
     */
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

    /**
     * Metodo POST, no coliciona la URL porque es diferente tipo que el Get
     * @param book
     * @return
     */
    //crear un nuevo libro en base de datos
    @PostMapping("/api/books")
    public ResponseEntity<Book> create(@RequestBody Book book){
        //Guardar el libro recibido en la base de datos y le asigna una clave primaria
        if(book.getId() != null){   //Quiere decir que el libro ya existe y se debe actualizar no crear
            log.warn("trying to create a book with id");
            return ResponseEntity.badRequest().build(); //Peticion mal hecha
        }
        Book result = bookRepository.save(book);
        return ResponseEntity.ok(result);
    }


    /**
     * Actualizar un libro existente en la base de datos
     * @param book
     * @return
     */
    //actualizar un libro existente en base de datos
    @PutMapping("/api/books")   //Por lo general usamos put para editar y post para crear
    public ResponseEntity<Book> update(@RequestBody Book book){
        if(book.getId() == null){   //Si no existe el ID es un libro nuevo y debe crearse no actulizar
            log.warn("trying to update a non existent book");
            return ResponseEntity.badRequest().build(); //Peticion mal hecha
        }
        if(!bookRepository.existsById(book.getId())){
            log.warn("trying to update a non existent book");
            return ResponseEntity.notFound().build(); //No existe el libro que se quiere actualizar
        }
        //Proceso de actualization
        Book result = bookRepository.save(book);
        return ResponseEntity.ok(result);
    }

    /**
     * Metodo para borrar un libro a partir de un ID como parameter
     * @param id
     * @return
     */
    //borrar un libro en base de datos
    @DeleteMapping("/api/books/{id}")
    public ResponseEntity<Book> delete(@PathVariable Long id){
        if(!bookRepository.existsById(id)){
            log.warn("trying to update a non existent book");
            return ResponseEntity.notFound().build(); //No existe el libro que se quiere actualizar
        }
        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Eliminar todos los libros de la base de datos
     * @return
     */
    @DeleteMapping("/api/books")
    public ResponseEntity<Book> deleteAll(){
        log.info("REST Request Deleting All Books");
        bookRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

}
