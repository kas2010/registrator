package com.alikhansfamily.registrator.service;

import com.alikhansfamily.registrator.to.UserTo;

import java.util.List;

/**
 * Интерфейс для сервиса обслуживания сущности Пользователь системы
 *
 * @author Smakov
 */

public interface UserService {
    List<UserTo> getAll();
    UserTo get(Long id);
    void save(UserTo userTo);
    void delete(Long id);
    Long checkUsername(String username);
}
