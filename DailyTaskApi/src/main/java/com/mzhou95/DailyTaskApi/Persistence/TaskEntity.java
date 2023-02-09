package com.mzhou95.DailyTaskApi.Persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="tasks", schema="DailyTaskApp" )
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer taskId;
    private Integer userId;
    private String taskName;
    private Integer taskDailyCount;
    private Integer taskCompleteCount;
    private Integer taskRestBonus;
    private String taskSchedule;

    public TaskEntity(Integer userId, String taskName, Integer taskDailyCount, Integer taskCompleteCount, Integer taskRestBonus, String taskSchedule) {
        this.userId = userId;
        this.taskName = taskName;
        this.taskDailyCount = taskDailyCount;
        this.taskCompleteCount = taskCompleteCount;
        this.taskRestBonus = taskRestBonus;
        this.taskSchedule = taskSchedule;
    }
}
