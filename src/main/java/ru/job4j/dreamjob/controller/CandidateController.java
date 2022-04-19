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
import ru.job4j.dreamjob.model.Image;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.CandidateService;
import ru.job4j.dreamjob.service.ImageService;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@ThreadSafe
public class CandidateController {

    private final CandidateService candidateService;
    private final ImageService imageService;

    public CandidateController(CandidateService candidateService, ImageService imageService) {

        this.candidateService = candidateService;

        this.imageService = imageService;
    }

    @GetMapping("/candidates")
    public String candidates(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("candidates", candidateService.findAll());
        model.addAttribute("photos", candidateService.findPhotos());
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
        /* imageService.add(file, candidate);
        imageService.add(newImage(file, candidate)); */
        return "redirect:/candidates";
    }

    static Image newImage(MultipartFile file, Candidate candidate) throws IOException {
        Image image = new Image();
        image.setId(0);
        image.setName(file.getOriginalFilename());
        image.setBytes(file.getBytes());
        image.setCandidateId(candidate.getId());
        return image;
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
                    .contentLength(candidateService.getPhotoRepo(candidateId).length)
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new ByteArrayResource(candidateService.getPhotoRepo(candidateId)));
    }
}
