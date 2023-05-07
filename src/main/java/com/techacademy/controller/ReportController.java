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

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
@RequestMapping("report")
public class ReportController {
    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    /** 一覧画面を表示 */
    @GetMapping("/list")
    public String getReportList(Model model) {
        // 全件検索結果をModelに登録
        model.addAttribute("reportlist", service.getReportList());
        // 検索件数をModelに登録
        model.addAttribute("reportlistsize", service.getReportList().size());
        return "report/list";
    }

    /** 詳細画面を表示 */
    @GetMapping("/detail/{id}/")
    public String getReport(@PathVariable("id") Integer id, Model model) {
        // Modelに登録
        model.addAttribute("report", service.getReport(id));
        // Employee詳細画面に遷移
        return "report/detail";
    }

    /** report登録画面を表示 */
    @GetMapping("/register")
    public String getRegister(@ModelAttribute Report report,Model model,@AuthenticationPrincipal UserDetails userdetails) {
        model.addAttribute("loginname",userdetails.getUsername());
        return "report/register";
    }

    /** report登録処理*/
    @PostMapping("/register")
    public String postRegister(@ModelAttribute Report report, Model model) {
    if("".equals(report.getTitle())||"".equals(report.getContent())||report.getReportDate()==null){
            model.addAttribute("error","必須項目が空欄となっています。");
            return "employee/register";
            }
        service.saveReport(report);
        // 一覧画面にリダイレクト
        return "redirect:/report/list";
    }

    /** Report更新画面を表示 */
    @GetMapping("/update/{id}/")
    public String getUpdate(@PathVariable("id") Integer id, Model model) {
        // Modelに登録
        model.addAttribute("report", service.getReport(id));
        // employee更新画面に遷移
        return "report/update";
    }

    /** Report更新処理 */
    @PostMapping("/update/{id}/")
    public String postUpdate(@PathVariable("id") Integer id,@ModelAttribute("report") Report report, Model model) {
        if("".equals(report.getTitle())||"".equals(report.getContent())) {
           model.addAttribute("error","必須項目が空欄となっています。");
           model.addAttribute("report", service.getReport(id));
           return "report/update";
        }
        // report登録
        service.saveReport(report);
        // 一覧画面にリダイレクト
        return "redirect:/report/list";
    }


}
