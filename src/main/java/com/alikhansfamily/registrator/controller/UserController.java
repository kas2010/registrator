package com.alikhansfamily.registrator.controller;

import com.alikhansfamily.registrator.model.Role;
import com.alikhansfamily.registrator.repository.ManagementRepository;
import com.alikhansfamily.registrator.service.UserService;
import com.alikhansfamily.registrator.to.UserTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
//import java.util.logging.Logger;

@Controller
public class UserController {
    //private static Logger log = Logger.getLogger(UserController.class.getName());
    private ManagementRepository managementRepository;
    private UserService service;

//    @Autowired
//    public static void setLog(Logger log) {
//        UserController.log = log;
//    }

    @Autowired
    public void setManagementRepository(ManagementRepository managementRepository) {
        this.managementRepository = managementRepository;
    }

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public String getUsersPage(Model model) {
        //log.info("Add model managements list.");
        model.addAttribute("managements", managementRepository.findAll());

        //log.info("Add model new User.");
        model.addAttribute("userTo", new UserTo());

        //log.info("Add model users list.");
        model.addAttribute("users", service.getAll());

        //log.info("Add model roles list");
        model.addAttribute("roleList", Arrays.asList(Role.values()));

        //log.info("Open page users.");
        return "users";
    }

    @PostMapping("/users")
    public String postUsers(@ModelAttribute UserTo userTo, Model model) {
        //log.info("Save User");
        service.save(userTo);

        //log.info("Redirect to page /users");
        return "redirect:/users";
    }

    @GetMapping("/users/get/{id}")
    public @ResponseBody
    UserTo getUser(@PathVariable Long id) {
        //log.info("Get User by id: " + id);
        return service.get(id);
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        //log.info("Delete User by id: " + id);
        service.delete(id);

        //log.info("Redirect to page /users");
        return "redirect:/users";
    }

    @GetMapping("/users/checkUser")
    public @ResponseBody
    Long checkUsername(@RequestParam String username) {
        //log.info("Checking User for username: " + username);
        Long result = service.checkUsername(username);

        //log.info("Result checking by username: " + result);
        return result;
    }

}
