package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@ThreadSafe
public class UserController {

    private final UserService userService;
    private final CityService cityService;

    public UserController(UserService userService, CityService cityService) {
        this.userService = userService;
        this.cityService = cityService;
    }

    @GetMapping("/addUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User(
                0,
                "Заполните поле",
                "Заполните поле",
                "Заполните поле"));
        model.addAttribute("cities", cityService.findAll());
        return "addUser";
    }
    /**
    @PostMapping("/createUser")
    public String createPost(HttpServletRequest req, Model model) {
        String mail = req.getParameter("mail");
        model.addAttribute("user", new User(0, mail));
        return "/registration";
    }
    */

    @PostMapping("/registration")
    public String registration(HttpServletRequest req, Model model) {
        String name = req.getParameter("name");
        String mail = req.getParameter("mail");
        String password = req.getParameter("password");
        int cityId = Integer.parseInt(req.getParameter("city.id"));
        Optional<User> regUser = userService.add(new User(
                0,
                name,
                mail,
                password,
                cityService.findById(cityId).getName())
        );

        if (regUser.isEmpty()) {
            model.addAttribute("message", "Пользователь с такой почтой уже существует");
            model.addAttribute("mark", "mark");
            return "/registration";
        }
        model.addAttribute("message", "Пользователь успешно зарегистрирован");
        return "/registration";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = userService.findUserByEmailAndPwd(
                user.getEmail(), user.getPassword()
        );
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }
}
