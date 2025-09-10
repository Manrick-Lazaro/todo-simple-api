package com.manricklazaro.todosimple.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.manricklazaro.todosimple.models.User;
import com.manricklazaro.todosimple.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
   
    public User findByID(long id) {
        Optional<User> user = this.userRepository.findById(id);

        // retorna o user se não tiver vazio
        return user.orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, 
            "User not found, id: " + id
        ));
    }

    // transactional serve para garantir a inserção 
    // completa do objeto do banco.
    @Transactional
    public User create(User obj) {
        // previne de criar um outro user com o mesmo id 
        obj.setId(null);

        obj = this.userRepository.save(obj);

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
