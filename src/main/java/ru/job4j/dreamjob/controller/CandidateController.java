package ru.job4j.dreamjob.controller;

import lombok.extern.slf4j.Slf4j;
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
import java.io.IOException;
import java.util.*;

@Slf4j
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
//        model.addAttribute("photos", candidateService.findPhotos());
        return "candidates";
    }

    @GetMapping("/addCandidate")
    public String addCandidates(Model model) {
        model.addAttribute("candidates", new Candidate(0, "Заполните поле", new HashSet<>()));
        return "addCandidate";
    }

    @PostMapping("/createCandidate")
    public String createCandidate(@ModelAttribute Candidate candidate,
                                  @RequestParam("files") MultipartFile[] files) {
        Arrays.stream(files).forEach(file -> {
            try {
                candidate.setPhoto(candidate.getId(), file.getBytes());
            } catch (IOException e) {
                log.info("Ошибка добавления фото {}", e.getMessage());
            }
        });
        candidateService.add(candidate);
        /* imageService.add(file, candidate);*/
       /* imageService.add(newImage(file, candidate)); */
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
                                  @RequestParam("files") MultipartFile[] files) {

        Arrays.stream(files).forEach(file -> {
            try {
                candidate.setPhoto(candidate.getId(), file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        candidateService.update(candidate);

        return "redirect:/candidates";
    }

    @GetMapping("/photoCandidate/{candidateId}")
    public List<ResponseEntity<Resource>> download(@PathVariable("candidateId") int candidateId) {
        List<ResponseEntity<Resource>> list = new ArrayList<>();
        Candidate candidate = candidateService.getById(candidateId);
        Set<byte[]> set = candidateService.getPhotoRepo(candidateId);
//        if (candidate.getPhotos() != null) {
        if (set != null) {
            log.info("name can" + candidate.getName());
//            candidate.getPhotos().forEach(bytes -> list.add(
            set.forEach(bytes -> list.add(
                            ResponseEntity.ok()
                                    .headers(new HttpHeaders())
                                    .contentLength(bytes.length)
                                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                                    .body(new ByteArrayResource(bytes)
                                    )
                    )
            );
            log.info(list.toString());
            return list;
        }
        return null;
    }
}
