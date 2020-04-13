package com.alikhansfamily.registrator.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "db_types")
public class DbType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = true)
    private String name;

    @OneToMany(mappedBy = "dbType")
    private List<UserRequest> userRequests;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserRequest> getUserRequests() {
        return userRequests;
    }

    public void setUserRequests(List<UserRequest> userRequests) {
        this.userRequests = userRequests;
    }
}
