package com.manricklazaro.todosimple.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import com.manricklazaro.todosimple.models.Task;
import com.manricklazaro.todosimple.models.User;

import org.springframework.stereotype.Service;

import com.manricklazaro.todosimple.repositories.TaskRepository;
import com.manricklazaro.todosimple.services.exceptions.DataBindingViolationException;
import com.manricklazaro.todosimple.services.exceptions.ObjectNotFoundException;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findByID(Long id) {
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new ObjectNotFoundException(
            "Task not found, id: " + id + ", type: " + Task.class.getName()
        ));
    }

    public List<Task> findAllByUserId(Long userId){
        List<Task> tasks = this.taskRepository.findByUser_id(userId);
        return tasks;
    }

    @Transactional
    public Task create(Task obj) {
        User user = this.userService.findByID(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;        
    }

    @Transactional
    public Task update(Task obj) {
        Task newObj = findByID(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    @Transactional
    public void delete(Long id) {
        findByID(id);

        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("\"It is not possible to delete because there are other related entities.\"");
        }
    }
}
