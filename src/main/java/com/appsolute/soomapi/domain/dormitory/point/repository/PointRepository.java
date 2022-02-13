package com.appsolute.soomapi.domain.dormitory.point.repository;

import com.appsolute.soomapi.domain.dormitory.point.data.entity.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<PointEntity, String> {
    boolean existsByAccountUUID(String accountUUID);

    PointEntity getByAccountUUID(String accountUUID);
}
