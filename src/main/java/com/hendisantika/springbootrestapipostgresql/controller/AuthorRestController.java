package com.hendisantika.springbootrestapipostgresql.controller;

import com.hendisantika.springbootrestapipostgresql.entity.Author;
import com.hendisantika.springbootrestapipostgresql.repository.AuthorRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {
    private static final Logger logger = LoggerFactory.getLogger(AuthorRestController.class);

    @Autowired
    private AuthorRepository repository;

    @PostMapping
    public ResponseEntity<?> addAuthor(@RequestBody Author author) {
        logger.info("Add author {}", author);
        return new ResponseEntity<>(repository.save(author), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<Author>> getAllAuthors() {
        logger.info("Get all authors");
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        logger.info("Get author with id {}", id);
        return new ResponseEntity<>(repository.findById(id).get(), HttpStatus.OK);
    }

    @GetMapping(params = {"name"})
    public ResponseEntity<Collection<Author>> findAuthorWithName(@RequestParam(value = "name") String name) {
        logger.info("Get author by name", name);
        return new ResponseEntity<>(repository.findByName(name), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable("id") long id, @RequestBody Author author) {
        Optional<Author> currentAuthorOpt = repository.findById(id);
        Author currentAuthor = currentAuthorOpt.get();
        currentAuthor.setName(author.getName());
        return new ResponseEntity<>(repository.save(currentAuthor), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthorById(@PathVariable Long id) {
        logger.info("Delete author with id", id);
        repository.deleteById(id);
    }

    @DeleteMapping
    public void deleteAllAuthors() {
        logger.info("Deleting all authors");
        repository.deleteAll();
    }
}