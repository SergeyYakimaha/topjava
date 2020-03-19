package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.javawebinar.topjava.bean_test.AppContextBean;
import ru.javawebinar.topjava.bean_test.ServletContextBean;
import ru.javawebinar.topjava.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

@Controller
public class RootController {
    @Autowired
    private UserService service;

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", service.getAll());
        return "users";
    }

    @PostMapping("/users")
    public String setUser(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        SecurityUtil.setAuthUserId(userId);
        return "redirect:meals";
    }
}
