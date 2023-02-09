package com.mzhou95.DailyTaskApi.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private Integer userId;
    private String taskName;
    private Integer taskDailyCount;
    private Integer taskCompleteCount;
    private Integer taskRestBonus;
    private String taskSchedule;

}
