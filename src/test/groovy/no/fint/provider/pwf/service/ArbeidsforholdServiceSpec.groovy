package no.fint.provider.pwf.service

import spock.lang.Specification

class ArbeidsforholdServiceSpec extends Specification {
    private ArbeidsforholdService arbeidsforholdService

    void setup() {
        arbeidsforholdService = new ArbeidsforholdService()
    }

    def "Get all arbeidsforhold"() {
        when:
        def arbeidsforholdList = arbeidsforholdService.getAll()

        then:
        arbeidsforholdList.size() > 0
    }
}
