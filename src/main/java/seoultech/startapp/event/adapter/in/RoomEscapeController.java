package seoultech.startapp.event.adapter.in;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seoultech.startapp.event.application.RoomQuestionResponse;
import seoultech.startapp.event.application.SuccessEscapeResponse;
import seoultech.startapp.event.application.UserEscapeResponse;
import seoultech.startapp.event.application.port.in.RoomEscapeGetUseCase;
import seoultech.startapp.event.application.port.in.UserEscapeGetUseCase;
import seoultech.startapp.event.application.port.in.UserEscapeRegisterUseCase;
import seoultech.startapp.global.config.web.AuthMember;
import seoultech.startapp.global.config.web.LoginMember;
import seoultech.startapp.global.response.JsonResponse;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/room-escapes")
class RoomEscapeController {

    private final RoomEscapeGetUseCase roomEscapeGetUseCase;
    private final UserEscapeGetUseCase userEscapeGetUseCase;
    private final UserEscapeRegisterUseCase userEscapeRegisterUseCase;

    /**
     * 방 문제를 조회하는 api
     */
    @GetMapping
    public ResponseEntity<?> getQuestionList() {
        List<RoomQuestionResponse> responses = roomEscapeGetUseCase.getQuestionList();
        return JsonResponse.okWithData(HttpStatus.OK, "문제 조회를 성공했습니다.", responses);
    }

    /**
     * 유저가 특정 문제의 정답이 맞았는지 조회하는 api
     */
    @PostMapping("/answer")
    public ResponseEntity<?> postUserEscape(@LoginMember AuthMember member, @RequestBody RoomAnswerRequest request) {
        SuccessEscapeResponse successEscapeResponse = userEscapeRegisterUseCase.checkUserAnswer(
                member.getMemberId(),
                request.getRoomId(),
                request.getAnswer()
        );
        return JsonResponse.okWithData(HttpStatus.OK, "정답 제출을 성공했습니다.", successEscapeResponse);
    }

    /**
     * 유저가 몇번방까지 풀었는지 조회하는 api
     */
    @GetMapping("/history")
    public ResponseEntity<?> getUserRoomEscapeHistory(@LoginMember AuthMember member) {
        UserEscapeResponse response = new UserEscapeResponse(userEscapeGetUseCase.getRecentEscapeRoomId(member.getMemberId()));
        return JsonResponse.okWithData(HttpStatus.OK, "유저의 방 위치 조회를 성공했습니다.", response);
    }
}