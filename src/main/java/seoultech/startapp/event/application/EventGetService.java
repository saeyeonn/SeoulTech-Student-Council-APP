package seoultech.startapp.event.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seoultech.startapp.event.application.port.in.EventGetUseCase;
import seoultech.startapp.event.application.port.out.LoadEventPort;
import seoultech.startapp.event.domain.Event;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
class EventGetService implements EventGetUseCase {

    private final LoadEventPort loadEventPort;

    @Override
    @Transactional(readOnly = true)
    public EventResponse getEventOne(Long eventId) {
        Event event = loadEventPort.loadEventById(eventId);

        return EventResponse.eventToEventResponse(event);
    }



    @Override
    @Transactional(readOnly = true)
    public List<EventResponse> getAllEvent() {
        return loadEventPort.loadAllEvent().stream()
                            .map(EventResponse::eventToEventResponse)
                            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EventPagingResponse getAllEventByPaging(int page, int count) {

        Page<EventResponse> eventResponses = loadEventPort.loadAllEventByPaging(PageRequest.of(page,count))
                                               .map(EventResponse::eventToEventResponse);

        return new EventPagingResponse(eventResponses.getTotalPages(), eventResponses.getContent());
    }
}
