package ru.novikov.library.DigitalLibrarySpringBoot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.novikov.library.DigitalLibrarySpringBoot.models.Book;
import ru.novikov.library.DigitalLibrarySpringBoot.services.BookService;

@Component
public class BookValidator implements Validator {
    private final BookService bookService;

    @Autowired
    public BookValidator(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals((aClass));
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;

        if (bookService.findBooksByTitle(book.getTitle()).isPresent()) {
            errors.rejectValue("title", "", "A book with this title already exists");
        }

        if (!Character.isUpperCase(book.getTitle().codePointAt(0))) {
            errors.rejectValue("title", "", "Title should start with a capital letter");
        }

        if (!Character.isUpperCase(book.getNameAuthor().codePointAt(0))) {
            errors.rejectValue("nameAuthor", "", "The author's name must begin with a capital letter.");
        }
    }
}
