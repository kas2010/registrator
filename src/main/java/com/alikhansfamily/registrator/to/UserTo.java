package com.alikhansfamily.registrator.to;

import com.alikhansfamily.registrator.model.Management;
import com.alikhansfamily.registrator.model.Role;
import com.alikhansfamily.registrator.model.User;

import java.util.Set;

public class UserTo {
    private Long id;
    private String username;
    private String password;
    private String passwordReplay;
    private Boolean active;
    private Set<Role> roles;
    private String firstName;
    private String middleName;
    private String lastName;
    private Management management;

    public UserTo(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        //this.password = user.getPassword();
        //this.passwordReplay = user.getPassword();
        this.active = user.getActive();
        this.roles = user.getRoles();
        this.firstName = user.getFirstName();
        this.middleName = user.getMiddleName();
        this.lastName = user.getLastName();
        this.management = user.getManagement();
    }

    public UserTo() {
        this.active = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordReplay() {
        return passwordReplay;
    }

    public void setPasswordReplay(String passwordReplay) {
        this.passwordReplay = passwordReplay;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Management getManagement() {
        return management;
    }

    public void setManagement(Management management) {
        this.management = management;
    }
}
