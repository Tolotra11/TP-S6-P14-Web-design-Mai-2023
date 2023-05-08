package com.tpSeo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tpSeo.model.Admin;

@Controller
public class AdminController {
    @GetMapping("/login")
    public String logForm(@RequestParam(defaultValue = "") String error,Model model){
        model.addAttribute("error", error);
        return "loginForm";
    }
    @PostMapping("/login")
    public String login(@RequestParam(defaultValue = "") String identifiant, @RequestParam(defaultValue = "")String password, HttpServletRequest session){
        try{
            Admin admin = new Admin().login(identifiant, password);
            System.out.println(admin.getId());
            session.getSession().setAttribute("idAdmin", admin.getId());
        }
        catch(Exception e){
            return "redirect:/login?error="+e.getMessage();
        }
        return "redirect:/";
    }
    
    @GetMapping("/logout")
    public String deconnect(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }
}
