package telran.java45.person.service;

import java.util.stream.Collectors;

import org.apache.tomcat.jni.Address;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java45.person.dao.PersonRepository;
import telran.java45.person.dto.AddressDto;
import telran.java45.person.dto.CityPopulationDto;
import telran.java45.person.dto.PersonDto;
import telran.java45.person.dto.exceptions.PersonNotFoundException;
import telran.java45.person.model.Person;
import telran.java45.post.dto.PostDto;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

	final PersonRepository personRepository;
	final ModelMapper modelMapper;

	@Override
	@Transactional
	public Boolean addPerson(PersonDto personDto) {
		if (personRepository.existsById(personDto.getId())) {
			return false;
		}
		personRepository.save(modelMapper.map(personDto, Person.class));
		return true;
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public PersonDto removePerson(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		personRepository.delete(person);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public PersonDto updatePersonName(Integer id, String name) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		person.setName(name);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public PersonDto updatePersonAddress(Integer id, AddressDto addressDto) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		if(addressDto.getCity()!=null) {
			person.setCity(addressDto.getCity());
		}
		if(addressDto.getStreet()!=null) {
			person.setStreet(addressDto.getStreet());
		}
		if(addressDto.getBuilding()!=null) {
			person.setBuilding(addressDto.getBuilding());
		}
        personRepository.save(person);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public Iterable<PersonDto> findPersonsByCity(String city) {

		return personRepository.findPersonsByCityIgnorCase(city) 
			.map(p -> modelMapper.map(p, PersonDto.class))
			.collect(Collectors.toList());
	}

	@Override
	public Iterable<PersonDto> findPersonsByName(String name) {
		return personRepository.findPersonsByNameIgnorCase(name) 
				.map(p -> modelMapper.map(p, PersonDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<PersonDto> findPersonsBetweenAges(Integer minAge, Integer maxAge) {
		return personRepository.findPersonsBetweenAges(minAge, maxAge) 
				.map(p -> modelMapper.map(p, PersonDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<CityPopulationDto> getCitiesPopulation() {
		// TODO Auto-generated method stub
		return null;
	}

}
