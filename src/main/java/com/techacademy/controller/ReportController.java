package com.techacademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.techacademy.entity.Report;
import com.techacademy.service.ReportService;
import com.techacademy.service.UserDetail;
import com.techacademy.entity.Employee;
import com.techacademy.service.EmployeeService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
@RequestMapping("report")
public class ReportController {
    private final ReportService service;
    private final EmployeeService employeeService;

    public ReportController(ReportService service,EmployeeService employeeService) {
        this.service = service;
        this.employeeService = employeeService;
    }

    /** 一覧画面を表示 */
    @GetMapping("/list")
    public String getReportList(Model model) {
        // 全件検索結果をModelに登録
        model.addAttribute("reportlist", service.getReportList());
        // 検索件数をModelに登録
        model.addAttribute("reportlistSize", service.getReportList().size());
        return "report/list";
    }

    /** 詳細画面を表示 */
    @GetMapping("/detail/{id}/")
    public String getReport(@PathVariable("id") Integer id, Model model,@AuthenticationPrincipal UserDetail userdetail) {
        // Modelに登録
        model.addAttribute("report", service.getReport(id));
        model.addAttribute("employee",userdetail.getEmployee());
        // Employee詳細画面に遷移
        return "report/detail";
    }

    /** report登録画面を表示 */
    @GetMapping("/register")
    public String getRegister(@ModelAttribute Report report,Model model,@AuthenticationPrincipal UserDetails userdetails) {
        model.addAttribute("employee",employeeService.findByName(userdetails.getUsername()));
        //model.addAttribute("loginname",userdetails.getUsername());
        return "report/register";
    }

    /** report登録処理*/
    @PostMapping("/register")
    public String postRegister(@ModelAttribute Report report, Model model,@AuthenticationPrincipal UserDetail userdetail) {
        if("".equals(report.getTitle())||"".equals(report.getContent())||report.getReportDate()==null){
            model.addAttribute("error","必須項目が空欄となっています。");
            model.addAttribute("employee",userdetail.getEmployee());
            return "report/register";
        }
        report.setEmployee(userdetail.getEmployee());
        service.saveReport(report);
        // 一覧画面にリダイレクト
        return "redirect:/report/list";
    }

    /** Report更新画面を表示 */
    @GetMapping("/update/{id}/")
    public String getUpdate(@PathVariable("id") Integer id, Model model,@AuthenticationPrincipal UserDetails userdetails) {
        // Modelに登録
        model.addAttribute("employee",employeeService.findByName(userdetails.getUsername()));
        model.addAttribute("report", service.getReport(id));
        // employee更新画面に遷移
        return "report/update";
    }

    /** Report更新処理 */
    @PostMapping("/update/{id}/")
    public String postUpdate(@PathVariable("id") Integer id,@ModelAttribute("report") Report report, Model model,@AuthenticationPrincipal UserDetails userdetails) {
        if("".equals(report.getTitle())||"".equals(report.getContent())) {
            model.addAttribute("error","必須項目が空欄となっています。");
            model.addAttribute("report", service.getReport(id));
            return "report/update";
        }
        // report登録
        report.setEmployee(employeeService.findByName(userdetails.getUsername()));
        service.saveReport(report);
        // 一覧画面にリダイレクト
        return "redirect:/report/list";
    }


}
