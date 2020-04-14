package com.alikhansfamily.registrator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_requests")
public class UserRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "iin")
    private String iin;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "management_id")
    @JsonIgnore
    private Management management;

    @Column(name = "department")
    private String department;

    @ManyToOne
    @JoinColumn(name = "degree_id")
    @JsonIgnore
    private Degree degree;

    @Column(name = "job")
    private String job;

    @Column(name = "phone")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "db_type_id")
    @JsonIgnore
    private DbType dbType;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "init_match")
    private Boolean initMatch;

    @Column(name = "emergency_input")
    private Boolean emergencyInput;

    @Column(name = "interrupted_cases")
    private Boolean interruptedCases;

    @Column(name = "reset_password")
    private Boolean resetPassword;

    @Column(name = "delete_user")
    private Boolean deleteUser;

    @Column(name = "request_date")
    private LocalDate requestDate;

    @ManyToOne
    @JoinColumn(name = "state_id")
    @JsonIgnore
    private State state;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "change_date")
    private LocalDateTime changeDate;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "change_user")
    private String changeUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIin() {
        return iin;
    }

    public void setIin(String iin) {
        this.iin = iin;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getInitMatch() {
        return initMatch;
    }

    public void setInitMatch(Boolean initMatch) {
        this.initMatch = initMatch;
    }

    public Boolean getEmergencyInput() {
        return emergencyInput;
    }

    public void setEmergencyInput(Boolean emergencyInput) {
        this.emergencyInput = emergencyInput;
    }

    public Boolean getInterruptedCases() {
        return interruptedCases;
    }

    public void setInterruptedCases(Boolean interruptedCases) {
        this.interruptedCases = interruptedCases;
    }

    public Boolean getResetPassword() {
        return resetPassword;
    }

    public void setResetPassword(Boolean resetPassword) {
        this.resetPassword = resetPassword;
    }

    public Boolean getDeleteUser() {
        return deleteUser;
    }

    public void setDeleteUser(Boolean deleteUser) {
        this.deleteUser = deleteUser;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public DbType getDbType() {
        return dbType;
    }

    public void setDbType(DbType dbType) {
        this.dbType = dbType;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(LocalDateTime changeDate) {
        this.changeDate = changeDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getChangeUser() {
        return changeUser;
    }

    public void setChangeUser(String changeUser) {
        this.changeUser = changeUser;
    }
}
