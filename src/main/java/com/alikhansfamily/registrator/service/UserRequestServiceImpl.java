package com.alikhansfamily.registrator.service;

import com.alikhansfamily.registrator.exception.NotFoundException;
import com.alikhansfamily.registrator.model.UserRequest;
import com.alikhansfamily.registrator.repository.*;
import com.alikhansfamily.registrator.to.UserRequestOrder;
import com.alikhansfamily.registrator.to.UserRequestTo;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserRequestServiceImpl implements UserRequestService {
    private UserRequestRepository repository;
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public void setRepository(UserRequestRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserRequestTo> getListByFilter(UserRequestTo userRequestTo) {
        if (userRequestTo == null) {
            throw new NotFoundException("The object userRequestTo is null.");
        }

        Specification<UserRequest> specification = Specification.where(
                filterStringField("iin", userRequestTo.getIin())
                        .and(filterStringField("firstName", userRequestTo.getFirstName()))
                        .and(filterStringField("middleName", userRequestTo.getMiddleName()))
                        .and(filterStringField("lastName", userRequestTo.getLastName()))
                        .and(filterLongField("management", userRequestTo.getManagement() == null ? null : userRequestTo.getManagement().getId()))
                        .and(filterStringField("department", userRequestTo.getDepartment()))
                        .and(filterLongField("degree", userRequestTo.getDegree() == null ? null : userRequestTo.getDegree().getId()))
                        .and(filterStringField("job", userRequestTo.getJob()))
                        .and(filterStringField("phone", userRequestTo.getPhone()))
                        .and(filterLongField("dbType", userRequestTo.getDbType() == null ? null : userRequestTo.getDbType().getId()))
                        .and(filterBooleanField("init_match", userRequestTo.getInitMatch()))
                        .and(filterBooleanField("emergency_input", userRequestTo.getEmergencyInput()))
                        .and(filterBooleanField("interrupted_cases", userRequestTo.getInterruptedCases()))
                        .and(filterBooleanField("reset_password", userRequestTo.getResetPassword()))
                        .and(filterBooleanField("delete_user", userRequestTo.getDeleteUser()))
                        .and(filterStringField("login", userRequestTo.getLogin()))
                        .and(filterStringField("password", userRequestTo.getPassword()))
                        .and(filterDateField("requestDate", userRequestTo.getMinRequestDate(), userRequestTo.getMaxRequestDate()))
                        .and(filterLongField("state",  userRequestTo.getState() == null ? null: userRequestTo.getState().getId()))
                        .and(filterDateTimeField("createDate", userRequestTo.getMinCreateDate(), userRequestTo.getMaxCreateDate()))
                        .and(filterDateTimeField("changeDate", userRequestTo.getMinChangeDate(), userRequestTo.getMinChangeDate()))
                        .and(filterStringField("createUser", userRequestTo.getCreateUser()))
                        .and(filterStringField("changeUser", userRequestTo.getChangeUser())));

        return repository.findAll(specification, Sort.by(userRequestTo.getOrder().getFieldName()))
                .stream()
                .map(x -> new UserRequestTo(x, userRequestTo.getOrder()))
                .collect(Collectors.toList());
    }

    @Override
    public UserRequestTo getById(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("The object UserRequest not found by id=" + id);
        }

        UserRequest userRequest = repository.findById(id).orElse(null);

        if (userRequest != null) {
            UserRequestTo userRequestTo = new UserRequestTo(userRequest, UserRequestOrder.FIRSTNAME);
            return userRequestTo;
        }

        return null;
    }

    @Override
    public void save(UserRequestTo userRequestTo) {
        if (userRequestTo == null) {
            throw new NotFoundException("The object userRequestTo is null.");
        }

        UserRequest userRequest;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (userRequestTo.getId() == null) {
            userRequest = new UserRequest();
            userRequest.setCreateDate(LocalDateTime.now());
            userRequest.setCreateUser(auth.getName());
        } else {
            userRequest = repository.findById(userRequestTo.getId()).orElse(new UserRequest());
            userRequest.setChangeDate(LocalDateTime.now());
            userRequest.setChangeUser(auth.getName());
        }

        userRequest.setIin(userRequestTo.getIin());
        userRequest.setFirstName(userRequestTo.getFirstName().trim());
        userRequest.setMiddleName(userRequestTo.getMiddleName().trim());
        userRequest.setLastName(userRequestTo.getLastName().trim());
        userRequest.setManagement(userRequestTo.getManagement());
        userRequest.setDepartment(userRequestTo.getDepartment());
        userRequest.setDegree(userRequestTo.getDegree());
        userRequest.setJob(userRequestTo.getJob().trim());
        userRequest.setPhone(userRequestTo.getPhone());
        userRequest.setDbType(userRequestTo.getDbType());
        userRequest.setLogin(userRequestTo.getLogin());
        userRequest.setPassword(userRequestTo.getPassword());
        userRequest.setInitMatch(userRequestTo.getInitMatch());
        userRequest.setEmergencyInput(userRequestTo.getEmergencyInput());
        userRequest.setInterruptedCases(userRequestTo.getInterruptedCases());
        userRequest.setResetPassword(userRequestTo.getResetPassword());
        userRequest.setDeleteUser(userRequestTo.getDeleteUser());
        userRequest.setRequestDate(userRequestTo.getRequestDate());
        userRequest.setState(userRequestTo.getState());
        repository.save(userRequest);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null || !repository.existsById(id)) {
            throw new NotFoundException("The object UserRequest not found be id=" + id);
        }
        repository.deleteById(id);
    }

    @Override
    public Long checkIin(String iin, Long dbTypeId) {
        return repository.checkIin(iin, dbTypeId);
    }

    @Override
    public Long checkFIO(String firstName, String middleName, String lastName, Long dbTypeId) {
        return repository.checkFIO(firstName, middleName, lastName, dbTypeId);
    }

    @Override
    public Long checkPhone(String phone, Long dbTypeId) {
        return repository.checkPhone(phone, dbTypeId);
    }

    @Override
    public String printVedomost(String[] ids) {
        Resource wordBlank = new ClassPathResource("doc/zayavka.docx");
        String randomUUID = UUID.randomUUID().toString();
        String copied = uploadPath + randomUUID + ".docx";

        try (InputStream in = wordBlank.getInputStream();
             FileOutputStream out = new FileOutputStream(copied)) {
            XWPFDocument doc = new XWPFDocument(OPCPackage.open(in));
            for (String id : ids) {
                UserRequestTo userRequestTo = getById(Long.valueOf(id));
                List<XWPFParagraph> paragraphs = doc.getParagraphs();
                for (XWPFTable tbl : doc.getTables()) {
                    XWPFTableRow row = tbl.createRow();
                    List<String> stringRow = new ArrayList<>();
                    stringRow.add(userRequestTo.getFirstName().toUpperCase() + " " +
                            userRequestTo.getMiddleName().toUpperCase() + " " +
                            userRequestTo.getLastName().toUpperCase());
                    stringRow.add(userRequestTo.getManagement().getName());
                    stringRow.add(userRequestTo.getDepartment().toUpperCase());
                    stringRow.add(userRequestTo.getJob().toLowerCase() + ", " + userRequestTo.getDegree().getName().toLowerCase());
                    stringRow.add("Обычные");
                    stringRow.add(userRequestTo.getInitMatch() ? "Да" : "");
                    stringRow.add(userRequestTo.getEmergencyInput() ? "Да" : "");
                    stringRow.add(userRequestTo.getInterruptedCases() ? "Да" : "");
                    stringRow.add(userRequestTo.getResetPassword() ? "Да" : "");
                    stringRow.add(userRequestTo.getDeleteUser() ? "Да" : "");
                    int cell_index = 0;
                    for (XWPFTableCell cell : row.getTableCells()) {
                        cell.setText(stringRow.get(cell_index));
                        cell_index++;
                    }
                }
                userRequestTo.setRequestDate(LocalDate.now());
                save(userRequestTo);
            }
            doc.write(out);
            doc.close();
            return randomUUID;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String printPassword(String[] ids) {
        Resource wordBlank = new ClassPathResource("doc/spisok.docx");
        String randomUUID = UUID.randomUUID().toString();
        String copied = uploadPath + randomUUID + ".docx";

        try (InputStream in = wordBlank.getInputStream();
             FileOutputStream out = new FileOutputStream(copied)) {
            XWPFDocument doc = new XWPFDocument(OPCPackage.open(in));
            int row_index = 1;
            for (String id : ids) {
                UserRequestTo userRequestTo = getById(Long.valueOf(id));
                List<XWPFParagraph> paragraphs = doc.getParagraphs();
                for (XWPFTable tbl : doc.getTables()) {
                    XWPFTableRow row = tbl.createRow();
                    List<String> stringRow = new ArrayList<>();
                    stringRow.add(String.valueOf(row_index++));
                    stringRow.add(userRequestTo.getDbType().getName().toUpperCase());
                    stringRow.add(userRequestTo.getFirstName().toUpperCase() + " " +
                            userRequestTo.getMiddleName().toUpperCase() + " " +
                            userRequestTo.getLastName().toUpperCase());
                    stringRow.add(userRequestTo.getManagement().getName());
                    stringRow.add(userRequestTo.getDepartment().toUpperCase());
                    stringRow.add(userRequestTo.getLogin().toUpperCase().equals("ИИН") ? userRequestTo.getIin() : userRequestTo.getLogin());
                    stringRow.add(userRequestTo.getPassword());
                    int cell_index = 0;
                    for (XWPFTableCell cell : row.getTableCells()) {
                        cell.setText(stringRow.get(cell_index));
                        cell_index++;
                    }
                }
            }
            doc.write(out);
            doc.close();
            return randomUUID;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Specification<UserRequest> filterStringField(String fieldName, String value) {
        return (root, query, cb) -> value == null || value.trim().equals("") ? null : cb.like(root.get(fieldName), value);
    }

    private Specification<UserRequest> filterDateField(String fieldName, LocalDate start, LocalDate end) {
        return (root, query, cb) -> {
            if (start == null && end == null)
                return null;
            if (start == null) {
                return cb.lessThanOrEqualTo(root.get(fieldName), end.plusDays(1));
            }
            if (end == null) {
                return cb.greaterThanOrEqualTo(root.get(fieldName), start);
            }
            return cb.between(root.get(fieldName), start, end.plusDays(1));
        };
    }

    private Specification<UserRequest> filterDateTimeField(String fieldName, LocalDateTime start, LocalDateTime end) {
        return (root, query, cb) -> {
            if (start == null && end == null)
                return null;
            if (start == null) {
                return cb.lessThanOrEqualTo(root.get(fieldName), end.plusDays(1));
            }
            if (end == null) {
                return cb.greaterThanOrEqualTo(root.get(fieldName), start);
            }
            return cb.between(root.get(fieldName), start, end.plusDays(1));
        };
    }

    private Specification<UserRequest> filterLongField(String fieldName, Long value) {
        return (root, query, cb) -> value == null ? null :
                root.<String>get(fieldName).in(value);
    }

    private Specification<UserRequest> filterBooleanField(String fieldName, Boolean value) {
        return (root, query, cb) -> value == null ? null : value ? cb.isTrue(root.get(fieldName)) : cb.isFalse(root.get(fieldName));
    }
}
