package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.service.CandidateService;

import java.io.IOException;

@Controller
@ThreadSafe
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {

        this.candidateService = candidateService;

    }

    @GetMapping("/candidates")
    public String candidates(Model model) {
        model.addAttribute("candidates", candidateService.findAll());
        return "candidates";
    }

    @GetMapping("/addCandidate")
    public String addCandidates(Model model) {
        model.addAttribute("candidates", new Candidate(0, "Заполните поле"));
        return "addCandidate";
    }

    @PostMapping("/createCandidate")
    public String createCandidate(@ModelAttribute Candidate candidate,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        candidate.setPhoto(file.getBytes());
        candidateService.add(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/formUpdateCandidate/{candidateId}")
    public String updateCandidate(Model model, @PathVariable("candidateId") int id) {
        model.addAttribute("candidate", candidateService.getById(id));
        return "updateCandidate";
    }

    @PostMapping("/updateCandidate")
    public String updateCandidate(@ModelAttribute Candidate candidate,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        candidate.setPhoto(file.getBytes());
        candidateService.update(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/photoCandidate/{candidateId}")
    public ResponseEntity<Resource> download(@PathVariable("candidateId") int candidateId) {
        Candidate candidate = candidateService.getById(candidateId);
            return ResponseEntity.ok()
                    .headers(new HttpHeaders())
                    .contentLength(candidate.getPhoto().length)
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new ByteArrayResource(candidate.getPhoto()));
    }
}
