package com.appsolute.soomapi.domain.dormitory.remain.controller;

import com.appsolute.soomapi.domain.account.service.AccountService;
import com.appsolute.soomapi.domain.dormitory.remain.data.dto.RemainDto;
import com.appsolute.soomapi.domain.dormitory.remain.data.entity.RemainType;
import com.appsolute.soomapi.domain.dormitory.remain.data.response.AddRemainResponse;
import com.appsolute.soomapi.domain.dormitory.remain.data.response.GetRemainListResponse;
import com.appsolute.soomapi.domain.dormitory.remain.data.response.IsRemainResponse;
import com.appsolute.soomapi.domain.dormitory.remain.exception.AlreadyRemainedException;
import com.appsolute.soomapi.domain.dormitory.remain.exception.RemainNotFoundException;
import com.appsolute.soomapi.domain.dormitory.remain.service.DateService;
import com.appsolute.soomapi.domain.dormitory.remain.service.RemainService;
import com.appsolute.soomapi.global.school.data.type.SchoolType;
import com.appsolute.soomapi.global.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/api/v1/dormitory/remain")
@RequiredArgsConstructor
public class RemainController {
    private final AccountService accountService;
    private final RemainService remainService;
    private final DateService dateService;
    private final String ALREADY_REMAIN_MESSAGE = "이미 신청하셧습니다!";
    private final CurrentUser current;


    @PostMapping //계정 UUID를 통해 해당 계정을 이번주 잔류 예약열에 추가한다
    public ResponseEntity<AddRemainResponse> addRemain(@RequestBody RemainType remainType) {
        String accountUUID = current.getPk();
        //계정 UUID 를 통해 소속학교정보를 불러온다
        SchoolType school = accountService.getDepartmentByAccountUUID(accountUUID);
        //해당 학교 이번주 잔류 예약열에 해당 계정을 추가한다
        RemainDto dto = remainService.addRemain(accountUUID, school, remainType);
        //추가한 예약정보를 응답객체에 담에서 반환한다.
        AddRemainResponse response = new AddRemainResponse(dto.id(), dto.accountUUID(), dto.remainAt(), dto.school(), dto.remainType());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping //계정 UUID를 통해 해당 계정이 이번주 잔류 예약열에 있는지 조회한다
    public ResponseEntity<IsRemainResponse> isRemain() {
        String accountUUID = current.getPk();
        //소속학교 잔류 예약열에서 해당 계정 UUID 를 통한 예약이 존재하는지 조회한다
        RemainDto dto = remainService.getRemain(accountUUID);
        //존재할 경우 응답객체에 해당 예약정보를 담아서 반환한다.
        IsRemainResponse response = new IsRemainResponse(dto.id(), dto.remainType());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping ("/list")//이번주 잔류 예약열을 조회한다. //선생 권한
    public ResponseEntity<GetRemainListResponse> getRemainList(@RequestParam SchoolType school) {
        //해당 학교 잔류 에약열을 구한다
        List<RemainDto> dto = remainService.getRemainList(school);
        //응답객체에 예약열을 담아서 반환한다
        GetRemainListResponse response = new GetRemainListResponse(dateService.getRemainStartDay(), school, dto.stream().map(RemainDto::toShortnessRemainResponse).toList());
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    //만약, 잔류 예약열에 해당 계정 UUID 가 없다면 오류를 반환한다"
    public ResponseEntity<?> cancelRemain() {
        String accountUUID = current.getPk();
        //잔류예약열에서 해당 계정UUID를 가진 예약을 삭제한다.
        remainService.removeRemain(accountUUID);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(RemainNotFoundException.class)
    public ResponseEntity<?> handling(RemainNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AlreadyRemainedException.class)
    public ResponseEntity<HashMap<String, String>> handling(AlreadyRemainedException e) {
        HashMap<String, String> map = new HashMap<>();
        map.put("message", ALREADY_REMAIN_MESSAGE);
        return ResponseEntity.badRequest().body(map);
    }




}
