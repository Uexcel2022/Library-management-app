package com.uexcel.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthenticationController {
    @GetMapping("/login")
    public String login(@RequestParam(value = "error",required = false) String error,
                        @RequestParam(value = "logout",required = false) String logout, Model model) {

        if(error != null && error.equals("invalidSession")) {
            model.addAttribute("msg","Session expired.");
        }

        if(error != null && error.equals("access_denied")) {
            model.addAttribute("msg","Access denied.");
        }

        if(error != null && error.equals("fail")) {
            model.addAttribute("msg","Bad credentials");
        }
        if(logout != null) {
            model.addAttribute("msg","You have logged out successfully.");
        }
        return "loginPage";
    }


}
