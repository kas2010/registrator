package com.alikhansfamily.registrator.service;

import com.alikhansfamily.registrator.exception.NotFoundException;
import com.alikhansfamily.registrator.model.User;
import com.alikhansfamily.registrator.repository.UserRepository;
import com.alikhansfamily.registrator.to.UserTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса сервиса обслуживания сущности Пользователь системы
 *
 * @author Smakov
 */

@Service
public class UserServiceImpl implements UserService{
    private UserRepository repository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserTo> getAll() {
        return repository.findAll().stream()
                .map(UserTo::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserTo get(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException("User not found by id = " + id));
        return new UserTo(user);
    }

    @Override
    public void save(UserTo userTo) {
        if (userTo == null) {
            throw new NotFoundException("The userTo is null.");
        } else {
            User user;
            if (userTo.getId() == null) {
                user = new User();
            } else {
                user = repository.findById(userTo.getId()).orElse(new User());
            }
            user.setUsername(userTo.getUsername());
            if (userTo.getPassword() != "") {
                user.setPassword(bCryptPasswordEncoder.encode(userTo.getPassword()));
            }
            user.setActive(userTo.getActive());
            user.setRoles(userTo.getRoles());
            user.setFirstName(userTo.getFirstName());
            user.setMiddleName(userTo.getMiddleName());
            user.setLastName(userTo.getLastName());
            user.setManagement(userTo.getManagement());

            repository.save(user);
        }
    }

    @Override
    public void delete(Long id) {
        repository.findById(id).orElseThrow(() -> new NotFoundException("User not found by id = " + id));
        repository.deleteById(id);
    }

    @Override
    public Long checkUsername(String username) {
        return repository.checkUser(username);
    }

}
