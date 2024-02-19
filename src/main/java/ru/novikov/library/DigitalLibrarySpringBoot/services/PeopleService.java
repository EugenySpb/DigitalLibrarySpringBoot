package ru.novikov.library.DigitalLibrarySpringBoot.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.novikov.library.DigitalLibrarySpringBoot.models.Book;
import ru.novikov.library.DigitalLibrarySpringBoot.models.Person;
import ru.novikov.library.DigitalLibrarySpringBoot.repositories.PeopleRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        person.setCreatedAt(new Date());
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setPersonId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public Optional<Person> getPersonName(String name) {
        return peopleRepository.findByName(name);
    }

    public List<Book> getBooksByPersonId(int personId) {
        Optional<Person> person = peopleRepository.findById(personId);

        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());

            // Проверка просроченности книг
            person.get().getBooks().forEach(book -> {
                long diffInMilli = Math.abs(book.getTakenAt().getTime() - new Date().getTime());
                if (diffInMilli > 864000000)
                    book.setExpired(true); // книга просрочена
            });

            return person.get().getBooks();
        } else {
            return Collections.emptyList();
        }
    }
}
