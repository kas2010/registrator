package com.alikhansfamily.registrator.controller;

import com.alikhansfamily.registrator.model.Management;
import com.alikhansfamily.registrator.model.User;
import com.alikhansfamily.registrator.repository.*;
import com.alikhansfamily.registrator.service.UserRequestService;
import com.alikhansfamily.registrator.to.UserRequestTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.Logger;

@Controller
public class MainController {
    private static Logger log = Logger.getLogger(MainController.class.getName());
    private UserRequestService service;
    private ManagementRepository managementRepository;
    private DegreeRepository degreeRepository;
    private DbTypeRepository dbTypeRepository;
    private StateRepository stateRepository;
    private UserRepository userRepository;
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public void setService(UserRequestService service) {
        this.service = service;
    }

    @Autowired
    public void setManagementRepository(ManagementRepository managementRepository) {
        this.managementRepository = managementRepository;
    }

    @Autowired
    public void setDegreeRepository(DegreeRepository degreeRepository) {
        this.degreeRepository = degreeRepository;
    }

    @Autowired
    public void setDbTypeRepository(DbTypeRepository dbTypeRepository) {
        this.dbTypeRepository = dbTypeRepository;
    }

    @Autowired
    public void setStateRepository(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/userRequest")
    public String pageUserRequest(@ModelAttribute UserRequestTo userRequestFind, Model model) {
        log.info("Add model new UserRequestTo.");
        model.addAttribute("userRequestTo", new UserRequestTo());

        log.info("Add model dictionaries(managements, degree, dbTypes, states).");
        model.addAttribute("managements", managementRepository.findAll());
        model.addAttribute("degrees", degreeRepository.findAll());
        model.addAttribute("dbTypes", dbTypeRepository.findAll());
        model.addAttribute("states", stateRepository.findAll());


        log.info("Add model user info.");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findUserByUsername(username);
        Management management = user.getManagement();
        String userInfo = "Пользователь: " + (user.getFirstName() == null ? "" : user.getFirstName().toUpperCase()) + " " +
                (user.getMiddleName() == null ? "" : user.getMiddleName().toUpperCase()) + " " +
                (user.getLastName() == null ? "" : user.getLastName().toUpperCase()) +
                ", Подразделение: " + (management == null ? "" : management.getName());
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("userManagement", management == null ? 0 : management.getId());
        model.addAttribute("userRoles", user.getRoles());

        log.info("Add model UserRequest list.");
        if (management != null) {
            userRequestFind.setManagement(management);
        }
        model.addAttribute("userRequestFind", userRequestFind);
        model.addAttribute("userRequestList", service.getListByFilter(userRequestFind));

        log.info("Add model User roles.");
        StringBuilder roles = new StringBuilder();
        for (GrantedAuthority authority : auth.getAuthorities()) {
            roles.append(authority.getAuthority()).append(";");
        }
        model.addAttribute("userRoles", roles);

        return "userRequest";
    }

    @PostMapping("/userRequest/save")
    public String postUserRequest(@ModelAttribute UserRequestTo userRequestTo) {
        log.info("Save UserRequest");
        service.save(userRequestTo);

        log.info("Redirect to page /userRequest");
        return "redirect:/userRequest";
    }

    @GetMapping("/userRequest/get/{id}")
    public @ResponseBody
    UserRequestTo getUserRequest(@PathVariable Long id) {
        log.info("Get UserRequest for id: " + id);
        return service.getById(id);
    }

    @GetMapping("/userRequestDelete/{id}")
    public String deleteUserRequest(@PathVariable Long id) {
        log.info("Delete UserRequest for id: " + id);
        service.deleteById(id);

        log.info("Redirect to page /userRequest");
        return "redirect:/userRequest";
    }

    @GetMapping("/userRequest/checkIin")
    public @ResponseBody
    Long checkIin(@RequestParam String iin, @RequestParam Long dbTypeId) {
        log.info("Checking IIN: " + iin);
        Long result = service.checkIin(iin, dbTypeId);
        log.info("Result checking IIN: " + result);
        return result;
    }

    @GetMapping("/userRequest/checkFIO")
    public @ResponseBody
    Long checkFIL(@RequestParam String firstName,
                  @RequestParam String middleName,
                  @RequestParam String lastName,
                  @RequestParam Long dbTypeId) {
        log.info("Checking FIO: " + firstName + " " + middleName + " " + lastName);
        Long result = service.checkFIO(firstName, middleName, lastName, dbTypeId);
        log.info("Result checking FIO: " + result);
        return result;
    }

    @GetMapping("/userRequest/checkPhone")
    public @ResponseBody
    Long checkPhone(@RequestParam String phone, @RequestParam Long dbTypeId) {
        String phoneWithPlus = "+" + phone.trim();
        log.info("Checking phone: " + phoneWithPlus);
        Long result = service.checkPhone(phoneWithPlus, dbTypeId);
        log.info("Result checking phone: " + result);
        return result;
    }

    @GetMapping("/userRequest/downloadRegister")
    public ResponseEntity<Resource> downloadFile() throws IOException {
        log.info("Downloading register file.");
        MediaType mediaType = MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");

        Resource resource = new ClassPathResource("doc/vedomost.docx");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; vedomost.docx")
                .contentType(mediaType)
                .contentLength(resource.contentLength())
                .body(resource);
    }

    @GetMapping("/userRequest/printVedomost")
    public @ResponseBody
    String printEmployeeVedomost(@RequestParam(value="ids[]", required = false) String[] ids) {
        log.info("Printing requests.");
        return service.printVedomost(ids);
    }

    @GetMapping("/userRequest/printPassword")
    public @ResponseBody
    String printEmployeePassword(@RequestParam(value="ids[]", required = false) String[] ids) {
        log.info("Printing requests.");
        return service.printPassword(ids);
    }

    @GetMapping("/userRequest/downloadReport")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileName) throws IOException {
        log.info("Downloading file: " + fileName + ".docx");
        MediaType mediaType = MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");

        Resource resource = new FileSystemResource(uploadPath + fileName + ".docx");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName" + fileName)
                .contentType(mediaType)
                .contentLength(resource.contentLength())
                .body(resource);
    }

    @DeleteMapping("/userRequest/deleteReport")
    public @ResponseBody
    String deleteFile(@RequestParam String fileName) {
        log.info("Deleting resource: " + fileName);

        Resource resource = new FileSystemResource(uploadPath + fileName + ".docx");

        if (resource.exists()) {
            try {
                resource.getFile().delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        log.info("Redirect to page /userRequest");
        return "redirect:/userRequest";
    }

}
