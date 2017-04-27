package no.fint.provider.pwf.service;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.EventUtil;
import no.fint.event.model.Status;
import no.fint.model.administrasjon.personal.Arbeidsforhold;
import no.fint.model.felles.Person;
import no.fint.model.relation.FintResource;
import no.fint.provider.adapter.Health;
import no.fint.provider.adapter.service.EventResponseService;
import no.fint.provider.adapter.service.EventStatusService;
import no.fint.provider.pwf.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventHandlerService {

    @Autowired
    private EventResponseService eventResponseService;

    @Autowired
    private EventStatusService eventStatusService;

    @Autowired
    private PersonService personService;

    @Autowired
    private ArbeidsforholdService arbeidsforholdService;

    public void handleEvent(String event) {
        Event eventObj = EventUtil.toEvent(event);
        if (eventObj != null && eventStatusService.verifyEvent(eventObj).getStatus() == Status.PROVIDER_ACCEPTED) {
            Action action = Action.valueOf(eventObj.getAction());
            Event<?> responseEvent = null;

            switch (action) {
                case HEALTH:
                    responseEvent = onHealthCheck(event);
                    break;
                case GET_ALL_PERSON:
                    responseEvent = onGetAllPerson(event);
                    break;
                case GET_ALL_ARBEIDSFORHOLD:
                    responseEvent = onGetAllArbeidsforhold(event);
                    break;
                case GET_ALL_PERSONALRESSURS:
            }


            /**
             * Add if statements for all the actions
             */

            if (responseEvent != null) {
                responseEvent.setStatus(Status.PROVIDER_RESPONSE);
                eventResponseService.postResponse(responseEvent);
            }
        }
    }

    private Event<?> onGetAllPerson(String json) {
        Event<FintResource<Person>> event = EventUtil.toEvent(json);
        event.setData(personService.getAll());
        return event;
    }

    private Event<?> onGetAllArbeidsforhold(String json) {
        Event<FintResource<Arbeidsforhold>> event = EventUtil.toEvent(json);
        event.setData(arbeidsforholdService.getAll());
        return null;
    }

    public Event onHealthCheck(String event) {
        Event<String> healthEvent = EventUtil.toEvent(event);

        if (healthCheck()) {
            healthEvent.setData(Lists.newArrayList(Health.APPLICATION_HEALTHY.name()));
        } else {
            healthEvent.setData(Lists.newArrayList(Health.APPLICATION_NOT_HEALTHY.name()));
            healthEvent.setMessage("The adapter is unable to communicate with the application.");
        }

        return healthEvent;
    }

    private boolean healthCheck() {
        /**
         * Check application connectivity etc.
         */
        return true;
    }
}
