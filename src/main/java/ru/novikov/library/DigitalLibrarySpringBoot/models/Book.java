package ru.novikov.library.DigitalLibrarySpringBoot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "The book title must not be empty")
    @Size(min = 1, max = 200, message = "The book title must be between 1 and 200 characters")
    @Column(name = "name_book")
    private String title;
    @NotEmpty(message = "Author name must not be empty")
    @Size(min = 2, max = 30, message = "Author name must be between 2 and 30 characters")
    @Column(name = "name_author")
    private String nameAuthor;
    @Min(value = 1500, message = "The year must be greater than 1500")
    @Digits(integer = 4, fraction =  0, message = "Year should be a number with up to 4 digits")
    @Column(name = "year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person owner;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    @Transient
    private boolean expired;

    public Book() {
    }

    public Book(String title, String nameAuthor, int year) {
        this.title = title;
        this.nameAuthor = nameAuthor;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        this.nameAuthor = nameAuthor;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", nameAuthor='" + nameAuthor + '\'' +
                ", year=" + year +
                ", owner=" + owner +
                '}';
    }
}
