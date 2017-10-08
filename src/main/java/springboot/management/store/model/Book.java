package springboot.management.store.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Entity
public class Book {
    private int id;
    private String name;


    private Collection<Publisher> publishers;

    public Book() {
        publishers = new HashSet<>();
    }

    public Book(String name) {
        this.name = name;
    }

    public Book(String name, Collection<Publisher> publishers){
        this.name = name;
        this.publishers = publishers;
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

    @ManyToMany(cascade = CascadeType.DETACH)
    public Collection<Publisher> getPublishers() {
        return publishers;
    }

    public void setPublishers(Collection<Publisher> publishers) {
        this.publishers = publishers;
    }

    @Override
    public String toString() {
        String result = String.format(
                "Book [id=%d, name='%s']%n",
                id, name);
        if (publishers != null) {
            for(Publisher publisher : publishers) {
                result += String.format(
                        "Publisher[id=%d, name='%s']%n",
                        publisher.getId(), publisher.getName());
            }
        }

        return result;
    }
}
