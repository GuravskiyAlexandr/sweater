package com.example.sweater.controller;

import com.example.sweater.domain.User;
import com.example.sweater.domain.dto.CaptchaResponsDto;
import com.example.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    private static final String CAPTCHA_URL =
            "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate getRestTemplate;
    @Value("${recaptcha.secret}")
    private String secret;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("g-recaptcha-response")String recaptchaResponse,
            @Valid User user,
            BindingResult bindingResult,
            Model model){
        String url = String.format(CAPTCHA_URL, secret, recaptchaResponse);
        CaptchaResponsDto captchaResponsDto =
                getRestTemplate.postForObject(url, CaptchaResponsDto.class, CaptchaResponsDto.class);
        if (!captchaResponsDto.isSuccess())
            model.addAttribute("captchaError", "Fill captcha");

        if (bindingResult.hasErrors() || !captchaResponsDto.isSuccess()){
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            return "registration";
        }
        if (user.getPassword() != null && !user.getPassword().equals(user.getPassword2())){
            model.addAttribute("passwordError", "Password are different");
            return "registration";
        }
        String registration = "registration";

        if (!userService.addUser(user, registration)){
            model.addAttribute("usernameError", "User exist");
            return "/registration";
        }

        System.out.println(user.getUsername());
        return "redirect:/login";
    }

    @GetMapping("/activate/{registration}/{code}")
    public String activate(Model model, @PathVariable String code , @PathVariable String registration){
        boolean isActivation = userService.isActivationUser(code);
        if (isActivation){
            model.addAttribute("messege", "User successfully activation");
            model.addAttribute("messegeType", "success");
        }else {
            model.addAttribute("messegeType", "danger");
            model.addAttribute("messege", "Activation code is not found");
        }
        if (registration.equals("registration"))
        return "login";
        return "profile";
    }
}
