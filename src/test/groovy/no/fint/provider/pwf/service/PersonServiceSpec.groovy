package no.fint.provider.pwf.service

import spock.lang.Specification

class PersonServiceSpec extends Specification {
    private PersonService personService

    void setup() {
        personService = new PersonService()
    }

    def "Get all person"() {
        when:
        def personList = personService.getAll()

        then:
        personList.size() == 5
    }
}
