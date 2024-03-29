package seoultech.startapp.event.adapter.out;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import seoultech.startapp.event.application.port.out.LoadUserEscapePort;
import seoultech.startapp.event.application.port.out.SaveUserEscapePort;
import seoultech.startapp.event.domain.UserEscape;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEscapePersistenceAdapter implements LoadUserEscapePort, SaveUserEscapePort {

    private final JpaUserEscapeRepository jpaUserEscapeRepository;
    private final UserEscapeMapper mapper;

    @Override
    public List<UserEscape> loadListByMemberId(Long memberId) {
        List<JpaUserEscape> jpaUserEscapeList = jpaUserEscapeRepository.findAllByMemberId(memberId);
        return mapper.mapToDomainUserEscapeList(jpaUserEscapeList);
    }

    @Override
    public void save(UserEscape userEscape) {
        JpaUserEscape jpaUserEscape = mapper.mapToJpaUserEscape(userEscape);

        jpaUserEscapeRepository.save(jpaUserEscape);
    }
}
