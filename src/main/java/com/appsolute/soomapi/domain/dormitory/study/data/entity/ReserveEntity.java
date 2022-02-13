package com.appsolute.soomapi.domain.dormitory.study.data.entity;

import com.appsolute.soomapi.domain.dormitory.study.data.dto.ReserveDto;
import com.appsolute.soomapi.domain.dormitory.study.data.response.ReserveResponse;
import com.appsolute.soomapi.global.school.data.type.SchoolType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReserveEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountUUID;
    private LocalDate reserveAt;
    private SchoolType school;
    private Integer roomNum;
    private Integer seatNum;

    public ReserveEntity(String accountUUID, LocalDate reserveAt, SchoolType school, Integer roomNum, Integer seatNum) {
        this.accountUUID = accountUUID;
        this.reserveAt = reserveAt;
        this.school = school;
        this.roomNum = roomNum;
        this.seatNum = seatNum;
    }

    public ReserveDto toDto() {
        return new ReserveDto(id, accountUUID, reserveAt, school, roomNum, seatNum);
    }

    public ReserveResponse toReserveResponse() {
        return new ReserveResponse(
                id,
                accountUUID,
                reserveAt,
                roomNum,
                seatNum
        );
    }


}
