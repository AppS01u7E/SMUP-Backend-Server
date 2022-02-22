package com.appsolute.soomapi.domain.schedule.service;

import com.appsolute.soomapi.domain.account.data.entity.user.User;
import com.appsolute.soomapi.domain.schedule.data.dto.TaskDto;
import com.appsolute.soomapi.domain.schedule.data.entity.TaskEntity;
import com.appsolute.soomapi.domain.schedule.data.request.AddGroupTaskRequest;
import com.appsolute.soomapi.domain.schedule.data.request.AddPersonalTaskRequest;
import com.appsolute.soomapi.domain.schedule.data.request.AddTaskRequest;
import com.appsolute.soomapi.domain.schedule.data.type.PeriodType;
import com.appsolute.soomapi.domain.schedule.exception.TaskNotFoundException;
import com.appsolute.soomapi.domain.schedule.repository.TaskRepository;
import com.appsolute.soomapi.domain.soom.data.dto.GroupAndUserDto;
import com.appsolute.soomapi.domain.soom.data.entity.Soom;
import com.appsolute.soomapi.domain.soom.util.CheckGroupUtil;
import com.appsolute.soomapi.global.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;


@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService{
    private final TaskRepository taskRepository;
    private final CheckGroupUtil check;
    private final CurrentUser current;

    @Override @Transactional
    public TaskDto addTask(AddTaskRequest request) {
        try {
            return addGroupTask(((AddGroupTaskRequest) request)).toDto();
        } catch (NullPointerException e){
            return addPersonalTask(((AddPersonalTaskRequest) request)).toDto();
        }
    }

    private TaskEntity addGroupTask(AddGroupTaskRequest request) {
        GroupAndUserDto dto = check.checkIsGroupMember(request.getGroupId());
        Soom group = dto.getSoom();
        List<String> taskListenerList = new ArrayList<>();

        taskListenerList.addAll(
                group.getMemberList().stream().map(User::getUuid).toList()
        );

        return taskRepository.save(
                TaskEntity.builder()
                        .date(request.getDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                        .period(request.getPeriod())
                        .title(request.getTitle())
                        .message(request.getMessage())
                        .listenerList(taskListenerList)
                        .build()
        );
    }

    private TaskEntity addPersonalTask(AddPersonalTaskRequest request) {
        String userPk = current.getPk();
        List<String> listenerList = new ArrayList<>();
        listenerList.add(userPk);


        return taskRepository.save(
                TaskEntity.builder()
                        .date(request.getDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                        .period(request.getPeriod())
                        .title(request.getTitle())
                        .message(request.getMessage())
                        .listenerList(listenerList)
                        .build()
        );
    }



//    @Override @Transactional
//    public List<TaskDto> getMonthlySchedule(String accountUUID, Integer year, Integer month, Pageable pageable) {
//        return getScheduleByAccountUUIDAndDateBetween(
//                accountUUID,
//                getFirstDayOfMonth(year, month),
//                getLastDayOfMonth(year, month),
//                pageable
//        );
//    }

//    @Override @Transactional
//    public List<TaskDto> getWeeklySchedule(String accountUUID, Integer year, Integer month, Integer week, Pageable pageable) {
//        return getScheduleByAccountUUIDAndDateBetween(
//                accountUUID,
//                getFirstDayOfWeek(year, month, week),
//                getLastDayOfWeek(year, month, week),
//                pageable
//        );
//    }
//
//    @Override @Transactional
//    public List<TaskDto> getDailySchedule(String accountUUID, Integer year, Integer month, Integer day, Pageable pageable) {
//        return toTaskDtoList(
//                taskRepository.getTaskEntitiesByScheduleUUIDAndDate(
//                        scheduleRepository.getOrCreateScheduleByAccountUUID(accountUUID).getUUID(),
//                        LocalDate.of(year, month, day), pageable
//        ));
//    }

    @Override
    public void removeTask(Long uuid) {
        taskRepository.removeByUUID(uuid);
    }

    @Override @Transactional
    public TaskDto updateTask(Long uuid, String message, String date, PeriodType period) {
        return makeUpdateTask(
                taskRepository.getByUUIDAndWriterId(uuid, current.getPk())
                        .orElseThrow(() -> new TaskNotFoundException(uuid.toString()))
                , message
                , date
                , period
        ).toDto();
    }

    private TaskEntity makeUpdateTask(TaskEntity entity, String message, String date, PeriodType period) {
        return entity.change(message, date, period);
    }
//
//    //TODO taskRepository 나 scheduleRepository 에서 다른 하나의 Repository 를 DI 받고 해당 Repository 로 메서드 이전하는 것에 대해 고민해보기
//    private List<TaskDto> getScheduleByAccountUUIDAndDateBetween(String accountUUID, LocalDate rangeStart, LocalDate rangeEnd, Pageable pageable) {
//        return toTaskDtoList(
//                taskRepository.getTaskEntitiesByScheduleUUIDAndDateBetween(
//                        scheduleRepository.getOrCreateScheduleByAccountUUID(accountUUID).getUUID(),
//                        rangeStart, rangeEnd, pageable
//        ));
//    }

    private List<TaskDto> toTaskDtoList(Iterable<TaskEntity> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(TaskEntity::toDto).toList();
    }

}
