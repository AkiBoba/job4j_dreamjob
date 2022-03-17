package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.CandidateStore;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CandidateController {

    private final CandidateStore store = CandidateStore.instOf();

    @GetMapping("/candidates")
    public String candidates(Model model) {
        model.addAttribute("candidates", store.findAll());
        return "candidates";
    }

    @GetMapping("/addCandidate")
    public String addCandidates(Model model) {
        model.addAttribute("candidates", new Candidate(0, "Заполните поле"));
        return "addCandidate";
    }

    @GetMapping("/formAddaCandidate")
    public String formAddCandidate(Model model) {
        return "addCandidate";
    }

    @PostMapping("/createCandidate")
    public String createCandidate(HttpServletRequest req) {
        String name = req.getParameter("name");
        store.add(new Candidate(store.findAll().size() + 1, name));
        return "redirect:/candidates";
    }

    @GetMapping("/formUpdateCandidate/{candidateId}")
    public String updateCandidate(Model model, @PathVariable("candidateId") int id) {
        model.addAttribute("candidate", store.findById(id));
        return "updateCandidate";
    }

    @PostMapping("/updateCandidate")
    public String updateCandidate(@ModelAttribute Candidate candidate) {
        store.update(candidate);
        return "redirect:/candidates";
    }
}
