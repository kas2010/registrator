package com.alikhansfamily.registrator.to;

import com.alikhansfamily.registrator.model.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserRequestTo {
    private Long id;
    private String iin;
    private String firstName;
    private String middleName;
    private String lastName;
    private Management management;
    private String department;
    private Degree degree;
    private String job;
    private String phone;
    private DbType dbType;
    private String login;
    private String password;
    private Boolean initMatch;
    private Boolean emergencyInput;
    private Boolean interruptedCases;
    private Boolean resetPassword;
    private Boolean deleteUser;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate requestDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate maxRequestDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate minRequestDate;
    private State state;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime minCreateDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime maxCreateDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime changeDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime minChangeDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime maxChangeDate;
    private String createUser;
    private String changeUser;
    private int resultCount;
    private int pageNumber;
    private UserRequestOrder order;

    public UserRequestTo(UserRequest userRequest, UserRequestOrder order) {
        this.id = userRequest.getId();
        this.iin = userRequest.getIin();
        this.firstName = userRequest.getFirstName();
        this.middleName = userRequest.getMiddleName();
        this.lastName = userRequest.getLastName();
        this.management = userRequest.getManagement();
        this.department = userRequest.getDepartment();
        this.degree = userRequest.getDegree();
        this.job = userRequest.getJob();
        this.phone = userRequest.getPhone();
        this.dbType = userRequest.getDbType();
        this.login = userRequest.getLogin();
        this.password = userRequest.getPassword();
        this.initMatch = userRequest.getInitMatch();
        this.emergencyInput = userRequest.getEmergencyInput();
        this.interruptedCases = userRequest.getInterruptedCases();
        this.resetPassword = userRequest.getResetPassword();
        this.deleteUser = userRequest.getDeleteUser();
        this.requestDate = userRequest.getRequestDate();
        this.maxRequestDate = userRequest.getRequestDate();
        this.minRequestDate = userRequest.getRequestDate();
        this.state = userRequest.getState();
        this.createDate = userRequest.getCreateDate();
        this.minChangeDate = userRequest.getCreateDate();
        this.maxCreateDate = userRequest.getCreateDate();
        this.changeDate = userRequest.getChangeDate();
        this.minChangeDate = userRequest.getChangeDate();
        this.maxChangeDate = userRequest.getChangeDate();
        this.createUser = userRequest.getCreateUser();
        this.changeUser = userRequest.getChangeUser();
        this.resultCount = 10;
        this.pageNumber = 0;
        this.order = order;
    }

    public UserRequestTo() {
        this.management = new Management();
        this.dbType = new DbType();
        this.state = new State();
        this.degree = new Degree();
        this.pageNumber = 0;
        this.resultCount = 10;
        this.order = UserRequestOrder.FIRSTNAME;
    }

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

    public DbType getDbType() {
        return dbType;
    }

    public void setDbType(DbType dbType) {
        this.dbType = dbType;
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

    public LocalDate getMaxRequestDate() {
        return maxRequestDate;
    }

    public void setMaxRequestDate(LocalDate maxRequestDate) {
        this.maxRequestDate = maxRequestDate;
    }

    public LocalDate getMinRequestDate() {
        return minRequestDate;
    }

    public void setMinRequestDate(LocalDate minRequestDate) {
        this.minRequestDate = minRequestDate;
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

    public LocalDateTime getMinCreateDate() {
        return minCreateDate;
    }

    public void setMinCreateDate(LocalDateTime minCreateDate) {
        this.minCreateDate = minCreateDate;
    }

    public LocalDateTime getMaxCreateDate() {
        return maxCreateDate;
    }

    public void setMaxCreateDate(LocalDateTime maxCreateDate) {
        this.maxCreateDate = maxCreateDate;
    }

    public LocalDateTime getMinChangeDate() {
        return minChangeDate;
    }

    public void setMinChangeDate(LocalDateTime minChangeDate) {
        this.minChangeDate = minChangeDate;
    }

    public LocalDateTime getMaxChangeDate() {
        return maxChangeDate;
    }

    public void setMaxChangeDate(LocalDateTime maxChangeDate) {
        this.maxChangeDate = maxChangeDate;
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

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public UserRequestOrder getOrder() {
        return order;
    }

    public void setOrder(UserRequestOrder order) {
        this.order = order;
    }
}
