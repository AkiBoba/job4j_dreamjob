package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.PostStore;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PostController {

    private final PostStore store = PostStore.instOf();

    @GetMapping("/posts")
    public String posts(Model model) {
        model.addAttribute("posts", store.findAll());
        return "posts";
    }

    @GetMapping("/addPost")
    public String addPost(Model model) {
        model.addAttribute("post", new Post(0, "Заполните поле"));
        return "addPost";
    }

    @GetMapping("/formAddPost")
    public String formAddPost(Model model) {
        return "addPost";
    }

    @PostMapping("/savePost")
    public String savePost(HttpServletRequest req) {
        String name = req.getParameter("name");
        System.out.println(name);
        store.add(new Post(store.findAll().size() + 1, name));
        return "redirect:/posts";
    }

}