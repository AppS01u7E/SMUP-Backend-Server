package com.appsolute.soomapi.domain.dormitory.study.repository;

import com.appsolute.soomapi.domain.dormitory.study.data.entity.ReserveEntity;
import com.appsolute.soomapi.global.school.data.type.SchoolType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface ReserveRepository extends JpaRepository<ReserveEntity, Long> {
    boolean existsByAccountUUIDAndReserveAt(String accountUUID, LocalDate reserveAt);

    ReserveEntity getByAccountUUIDAndReserveAt(String accountUUID, LocalDate reserveAt);

    List<ReserveEntity> getAllBySchoolAndReserveAtAndRoomNum(SchoolType school, LocalDate reserveAt, Integer roomNum);

    @Transactional
    void deleteByAccountUUIDAndReserveAt(String accountUUID, LocalDate reserveAt);

    boolean existsByReserveAtAndSchoolAndRoomNumAndSeatNum(LocalDate date, SchoolType schoolType, Integer roomNum, Integer seatNum);

}
