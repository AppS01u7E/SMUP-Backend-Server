package com.appsolute.soomapi.domain.schedule.repository;

import com.appsolute.soomapi.domain.schedule.data.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    Optional<TaskEntity> getByUUIDAndWriterId(Long UUID, String writerId);
    void removeByUUID(Long UUID);
}
