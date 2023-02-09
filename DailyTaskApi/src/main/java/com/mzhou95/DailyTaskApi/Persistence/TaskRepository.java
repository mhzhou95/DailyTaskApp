package com.mzhou95.DailyTaskApi.Persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {
    TaskEntity getTaskEntityByTaskId(Integer id);
    List<TaskEntity> findAllByUserId(Integer id);
}
