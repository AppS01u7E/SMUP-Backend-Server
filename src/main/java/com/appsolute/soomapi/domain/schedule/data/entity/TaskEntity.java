package com.appsolute.soomapi.domain.schedule.data.entity;

import com.appsolute.soomapi.domain.account.data.entity.user.User;
import com.appsolute.soomapi.domain.schedule.data.dto.TaskDto;
import com.appsolute.soomapi.domain.schedule.data.type.PeriodType;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UUID;
    @Column(name = "date_")
    private String date;
    @Enumerated(EnumType.ORDINAL) //String 보다 오히려 가독성이 좋다....
    @Column(name = "period_")
    private PeriodType period;
    private String title;
    @Length(max = 50)
    private String message;
    @ElementCollection
    @Builder.Default
    private List<String> listenerList = new ArrayList<>();

    //TODO 그룹 탈퇴시 listener에서 없어지는 로직 구현
    //TODO fcm 알림 전송 로직 구현

    private String writerId;

    public TaskDto toDto() {
        return new TaskDto(
                UUID,
                date,
                period,
                message
        );
    }

    public TaskEntity change(String message, String date, PeriodType period){
        this.message = message;
        this.date = date;
        this.period = period;
        return this;
    }


}
