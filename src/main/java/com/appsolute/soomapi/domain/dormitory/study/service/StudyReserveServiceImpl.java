package com.appsolute.soomapi.domain.dormitory.study.service;

import com.appsolute.soomapi.domain.dormitory.study.data.dto.ReserveDto;
import com.appsolute.soomapi.domain.dormitory.study.data.entity.ReserveEntity;
import com.appsolute.soomapi.domain.dormitory.study.data.response.ReserveResponse;
import com.appsolute.soomapi.domain.dormitory.study.exception.AlreadySomeoneReservedSeatException;
import com.appsolute.soomapi.domain.dormitory.study.exception.ReserveNotFoundException;
import com.appsolute.soomapi.domain.dormitory.study.repository.ReserveRepository;
import com.appsolute.soomapi.global.school.data.type.SchoolType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyReserveServiceImpl implements StudyReserveService{
    private final ReserveRepository reserveRepository;

    @Override
    public ReserveDto  reserve(SchoolType type, String accountUUID, Integer roomNum, Integer seatNum) {
        checkReserveIsExistsByDateAndSchoolAndRoomAndSeat(LocalDate.now(), type, roomNum, seatNum);
        if (reserveRepository.existsByAccountUUIDAndReserveAt(accountUUID, LocalDate.now())){
            reserveRepository.deleteByAccountUUIDAndReserveAt(accountUUID, LocalDate.now());
        }
        ReserveEntity entity = new ReserveEntity(accountUUID, LocalDate.now(), type, roomNum, seatNum);
        return reserveRepository.save(entity).toDto();
    }

    @Override @Transactional
    public ReserveDto getReserve(String accountUUID) {
        checkReserveIsExists(accountUUID);
        ReserveEntity entity = reserveRepository.getByAccountUUIDAndReserveAt(accountUUID, LocalDate.now());
        return entity.toDto();
    }

    @Override @Transactional
    public void cancelReserve(String accountUUID) {
        checkReserveIsExists(accountUUID);
        reserveRepository.deleteByAccountUUIDAndReserveAt(accountUUID, LocalDate.now());
    }

    private void checkReserveIsExists(String accountUUID) {
        if(!reserveRepository.existsByAccountUUIDAndReserveAt(accountUUID, LocalDate.now())) throw new ReserveNotFoundException(accountUUID);
    }

    private void checkReserveIsExistsByDateAndSchoolAndRoomAndSeat(LocalDate date, SchoolType schoolType, Integer roomNum, Integer seatNum) {
        if (reserveRepository.existsByReserveAtAndSchoolAndRoomNumAndSeatNum(date, schoolType, roomNum, seatNum)) {
            throw new AlreadySomeoneReservedSeatException(date.toString()+"일 " +roomNum.toString()+ "번방 " +seatNum.toString() + "번자리");
        }
    }

    @Override
    public List<ReserveResponse> getAllAccountUUIDAtReserve(SchoolType school, Integer roomNum) {
        return reserveRepository.getAllBySchoolAndReserveAtAndRoomNum(school, LocalDate.now(), roomNum).stream().map(ReserveEntity::toReserveResponse).toList();
    }
}
