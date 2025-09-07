package com.manricklazaro.todosimple.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manricklazaro.todosimple.models.Task;

@Repository
public interface TaskRepository extends  JpaRepository<Task, Long>{
    // CONSULTA COM O JPA
    List<Task> findByUser_id(Long id);

    // CONSULTA COM O JQL
    // @Query(value = "SELECT t FROM Task WHERE t.user.id = :id")
    // List<Task> findByUserID(@Param("id") Long id);

    // CONSULTA COM SQL
    // @Query(nativeQuery = true, value = "SELECT * FROM task WHERE task.user_id = :id")
    // List<Task> findByUserID(@Param("id") Long id);
}
