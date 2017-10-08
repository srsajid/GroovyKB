package springboot.management.store.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
public class Publisher {
    private int id;
    private String name;
    private Collection<Book> books;

    public Publisher(){

    }

    public Publisher(String name){
        this.name = name;
    }

    public Publisher(String name, Set<Book> books){
        this.name = name;
        this.books = books;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "publishers")
    public Collection<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}