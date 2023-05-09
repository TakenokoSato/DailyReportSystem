package com.techacademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import com.techacademy.entity.Report;
import com.techacademy.service.ReportService;
import com.techacademy.entity.Employee;
import com.techacademy.service.EmployeeService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
public class TopController {

    private final ReportService service;
    private final EmployeeService employeeService;

    public TopController(ReportService service,EmployeeService employeeService) {
        this.service = service;
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String getTop(@AuthenticationPrincipal UserDetails userdetails,Model model) {
        Employee employee = employeeService.findByName(userdetails.getUsername());
        model.addAttribute("employee",employeeService.findByName(userdetails.getUsername()));
        model.addAttribute("reportlist", service.findByEmployee(employee));
        // top.htmlに画面遷移
       return "top";
    }

}
