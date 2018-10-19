package com.example.sweater.controller;


import com.example.sweater.domain.Messege;
import com.example.sweater.domain.User;
import com.example.sweater.repository.MessegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpRequest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    private MessegeRepository repository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, value = "") String filtr, Model model) {
        Iterable<Messege> messeges;
        if (filtr != null && !filtr.isEmpty()) {
            messeges = repository.findByTag(filtr);
        } else {
            messeges = repository.findAll();
        }
        model.addAttribute("some", "hello, letsCode!");
        model.addAttribute("messeges", messeges);
        model.addAttribute("filtr", filtr);
        return "main";
    }

    @PostMapping("/main")
    public String addSMS(
            @AuthenticationPrincipal User user,
            @RequestParam("file") MultipartFile file,
            @Validated Messege messege,
            BindingResult bindingResult,
            Model model) throws IOException {

        messege.setAuthor(user);
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("messege", messege);
            System.out.println(errorsMap);
        } else {
            if (!file.isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + resultFilename));

                messege.setFilename(resultFilename);
            }
            repository.save(messege);
            model.addAttribute("messege", null);
        }
        Iterable<Messege> messeges = repository.findAll();
        model.addAttribute("messeges", messeges);
        return "main";
    }
}
