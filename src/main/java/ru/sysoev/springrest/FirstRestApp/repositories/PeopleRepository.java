package ru.sysoev.springrest.FirstRestApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sysoev.springrest.FirstRestApp.models.Person;
@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
