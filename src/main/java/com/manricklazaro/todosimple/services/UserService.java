package com.manricklazaro.todosimple.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manricklazaro.todosimple.TodosimpleApplication;
import com.manricklazaro.todosimple.models.User;
import com.manricklazaro.todosimple.repositories.TaskRepository;
import com.manricklazaro.todosimple.repositories.UserRepository;

@Service
public class UserService {

    private final TodosimpleApplication todosimpleApplication;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TaskRepository taskRepository;

    UserService(TodosimpleApplication todosimpleApplication) {
        this.todosimpleApplication = todosimpleApplication;
    }

    public User findByID(long id) {
        Optional<User> user = this.userRepository.findById(id);

        // retorna o user se não tiver vazio
        return user.orElseThrow( () -> new RuntimeException(
            "User not found, id: " + id + ", type: " + User.class.getName()
        ));
    }

    // transactional serve para garantir a inserção 
    // completa do objeto do banco.
    @Transactional
    public User create(User obj) {
        // previne de criar um outro user com o mesmo id 
        obj.setId(null);

        obj = this.userRepository.save(obj);

        this.taskRepository.saveAll(obj.getTasks());

        return obj;
    }

    @Transactional
    public User update(User obj) {
        User newObj = findByID(obj.getId());

        newObj.setPassword(obj.getPassword());

        return this.userRepository.save(newObj);
    }

    public void delete(Long id) {
        findByID(id);

        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("It is not possible to delete because there are other related entities.");
        }
    }

}
