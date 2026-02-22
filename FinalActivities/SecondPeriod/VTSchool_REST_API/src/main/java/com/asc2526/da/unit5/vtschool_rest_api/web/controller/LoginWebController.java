package com.asc2526.da.unit5.vtschool_rest_api.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginWebController {

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

}