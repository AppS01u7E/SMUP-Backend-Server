package com.appsolute.soomapi.domain.dormitory.study.controller;

import com.appsolute.soomapi.domain.account.service.AccountService;
import com.appsolute.soomapi.domain.dormitory.study.data.dto.ReserveDto;
import com.appsolute.soomapi.domain.dormitory.study.data.response.GetReserveListResponse;
import com.appsolute.soomapi.domain.dormitory.study.data.response.IsReserveResponse;
import com.appsolute.soomapi.domain.dormitory.study.data.response.ReserveResponse;
import com.appsolute.soomapi.domain.dormitory.study.exception.ReserveNotFoundException;
import com.appsolute.soomapi.domain.dormitory.study.service.StudyReserveService;
import com.appsolute.soomapi.global.school.data.type.SchoolType;
import com.appsolute.soomapi.global.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dormitory/study")
@RequiredArgsConstructor
public class StudyController {
    private final StudyReserveService studyReserveService;
    private final AccountService accountService;
    private final CurrentUser current;

    @PostMapping //계정 UUID 를 통해 해당 계정을 당일 기숙사 자습 예약열에 추가한다
    public ResponseEntity<ReserveResponse> reserve(@RequestParam Integer room, @RequestParam Integer seat) {
        String accountUUID = current.getPk();
        //accountUUID 를 통해 예약자의 소속학교를 가져온다.
        SchoolType type = accountService.getDepartmentByAccountUUID(accountUUID);
        //예약을 진행한다.
        ReserveDto dto = studyReserveService.reserve(type, accountUUID, room, seat);
        //결과값을 응답객체에 담아서 반환한다.
        ReserveResponse response = new ReserveResponse(dto.id(), dto.accountUUID(), dto.reserveAt(), dto.roomNum(), dto.seatNum());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    // 만약, 자습실 예약열에 해당 계정 UUID 가 없다면 오류를 반환한다"
    public ResponseEntity<?> cancelReserve() {
        String accountUUID = current.getPk();
        studyReserveService.cancelReserve(accountUUID);
        return ResponseEntity.noContent().build();
    }

    @GetMapping //계정 UUID 를 통해 해당 계정이 당일 기숙사 자습 예약열에 있는지 조회한다
    public ResponseEntity<IsReserveResponse> getReserve() {
        String accountUUID = current.getPk();
        // 계정 UUID 를 통해 예약정보를 조회한다. 예약정보가 존재하지 않을경우 예외를 반환한다.
        ReserveDto dto = studyReserveService.getReserve(accountUUID);
        // 예약정보를 응답객체에 담아서 반환한다.
        IsReserveResponse response = new IsReserveResponse(dto.id(), dto.roomNum(), dto.seatNum());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list") //당일 기숙사 자습 예약열을 조회한다.
    public ResponseEntity<GetReserveListResponse> getReserveList(@RequestParam SchoolType school, @RequestParam Integer roomNum) {
        //모든 예약정보를 조회한다.
        List<ReserveResponse> list = studyReserveService.getAllAccountUUIDAtReserve(school, roomNum);
        // 예약정보를 응답객체에 담아서 반환한다.
        GetReserveListResponse response = new GetReserveListResponse(school, LocalDate.now(), list);
        return ResponseEntity.ok(response);
    }

}
