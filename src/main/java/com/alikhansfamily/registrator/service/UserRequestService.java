package com.alikhansfamily.registrator.service;

import com.alikhansfamily.registrator.to.UserRequestTo;

import java.util.List;
import java.util.Set;

public interface UserRequestService {
    List<UserRequestTo> getListByFilter(UserRequestTo userRequestTo);
    UserRequestTo getById(Long id);
    void save(UserRequestTo userRequestTo);
    void deleteById(Long id);
    Long checkIin(String iin, Long dbTypeId);
    Long checkFIO(String firstName, String middleName, String lastName, Long dbTypeId);
    Long checkPhone(String phone, Long dbTypeId);
    String printVedomost(String[] ids);
    String printPassword(String[] ids);
}
