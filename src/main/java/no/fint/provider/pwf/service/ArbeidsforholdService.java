package no.fint.provider.pwf.service;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.BaseProducer;
import no.fint.model.administrasjon.personal.Arbeidsforhold;
import no.fint.model.felles.Identifikator;
import no.fint.model.relation.FintResource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArbeidsforholdService {

    private List<Arbeidsforhold> arbeidsforholdList;

    public ArbeidsforholdService() {
        arbeidsforholdList = new ArrayList<>();
        arbeidsforholdList.add(createArbeidsforhold());
        arbeidsforholdList.add(createArbeidsforhold());
        arbeidsforholdList.add(createArbeidsforhold());
        arbeidsforholdList.add(createArbeidsforhold());
        arbeidsforholdList.add(createArbeidsforhold());
    }

    private Arbeidsforhold createArbeidsforhold() {
        String systemId = randomString();
        String stillingsnummer = randomString();
        Double arslonn = randomDouble();
        Double stillingsprosent = randomPercent();
        Double lonnsprosent = randomPercent();


        Identifikator identifikator = new Identifikator(systemId, null);
        return new Arbeidsforhold(identifikator, stillingsnummer, arslonn, "lærer", stillingsprosent, lonnsprosent, (stillingsprosent > 50), null);
    }

    private String randomString() {
        Fairy fairy = Fairy.create();
        BaseProducer baseProducer = fairy.baseProducer();
        return String.valueOf(baseProducer.randomInt(700000));
    }


    private Double randomDouble() {
        Fairy fairy = Fairy.create();
        BaseProducer baseProducer = fairy.baseProducer();
        return baseProducer.randomBetween(300000d, 600000d);
    }

    private Double randomPercent() {
        Fairy fairy = Fairy.create();
        BaseProducer baseProducer = fairy.baseProducer();
        return (double) baseProducer.randomInt(100);
    }

    public List<FintResource<Arbeidsforhold>> getAll() {
        return arbeidsforholdList.stream().map(FintResource::with).collect(Collectors.toList());
    }

}
