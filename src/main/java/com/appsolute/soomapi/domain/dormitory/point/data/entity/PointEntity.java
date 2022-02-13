package com.appsolute.soomapi.domain.dormitory.point.data.entity;

import com.appsolute.soomapi.domain.dormitory.point.data.dto.PointDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class PointEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uuid;
    @Column(unique = true)
    private String accountUUID;
    private int rewardPoint;
    private int penaltyPoint;

    public PointEntity(String accountUUID) {
        this.accountUUID = accountUUID;
    }

    public PointDto toDto() {
        return new PointDto(rewardPoint, penaltyPoint);
    }
}
