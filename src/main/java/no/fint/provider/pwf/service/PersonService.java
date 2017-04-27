package no.fint.provider.pwf.service;

import io.codearte.jfairy.Fairy;
import no.fint.model.felles.*;
import no.fint.model.relation.FintResource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private List<Person> personList;

    public PersonService() {
        personList = new ArrayList<>();
        personList.add(createPerson());
        personList.add(createPerson());
        personList.add(createPerson());
        personList.add(createPerson());
        personList.add(createPerson());
    }

    private Person createPerson() {
        Fairy fairy = Fairy.create(Locale.forLanguageTag("sv"));
        io.codearte.jfairy.producer.person.Person fairyPerson = fairy.person();
        Personnavn personnavn1 = new Personnavn(fairyPerson.getFirstName(), fairyPerson.getLastName(), "");
        Kontaktinformasjon kontaktinformasjon1 = new Kontaktinformasjon(fairyPerson.getEmail(), fairyPerson.getTelephoneNumber(), fairyPerson.getTelephoneNumber(), String.format("http://blogg.%s.no", fairyPerson.getLastName()), "");
        Adresse adresse1 = new Adresse(fairyPerson.getAddress().getStreet(), fairyPerson.getAddress().getPostalCode(), fairyPerson.getAddress().getCity());
        Identifikator fodselsnummer = new Identifikator(fairyPerson.getNationalIdentificationNumber(), null);
        return new Person(kontaktinformasjon1, adresse1, fodselsnummer, personnavn1, fairyPerson.getDateOfBirth().toDate(), adresse1);
    }

    public List<FintResource<Person>> getAll() {
        return personList.stream().map(FintResource::with).collect(Collectors.toList());
    }
}
