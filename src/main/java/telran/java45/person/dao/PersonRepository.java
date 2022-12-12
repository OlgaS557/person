package telran.java45.person.dao;

import java.util.stream.Stream;

import org.springframework.data.repository.CrudRepository;

import telran.java45.person.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
	Stream<Person> findPersonsByCityIgnorCase(String city);

	Stream<Person> findPersonsByNameIgnorCase(String name);

	Stream<Person> findPersonsBetweenAges(Integer minAge, Integer maxAge);

}
