package com.techacademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.techacademy.entity.Employee;
import com.techacademy.service.EmployeeService;
import com.techacademy.entity.Authentication;
import com.techacademy.service.AuthenticationService;
import java.util.Optional;


@Controller
@RequestMapping("employee")
public class EmployeeController {
    private final EmployeeService service;
    private final AuthenticationService authenticationservice;

    public EmployeeController(EmployeeService service,AuthenticationService authenticationservice) {
        this.service = service;
        this.authenticationservice = authenticationservice;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    /** 一覧画面を表示 */
    @GetMapping("/list")
    public String getList(Model model) {
     // 全件検索結果をModelに登録
     model.addAttribute("employeelist",service.getEmployeeList());
     // 検索件数をModelに登録
     model.addAttribute("employeelistSize",service.getEmployeeList().size());
     // employee/list.htmlに画面遷移
     return "employee/list";
    }

    /** 詳細画面を表示 */
    @GetMapping("/detail/{id}/")
    public String getEmployee(@PathVariable("id") Integer id, Model model) {
        // Modelに登録
        model.addAttribute("employee", service.getEmployee(id));
        // Employee詳細画面に遷移
        return "employee/detail";
    }

    /** employee登録画面を表示 */
    @GetMapping("/register")
    public String getRegister(@ModelAttribute Employee employee) {
        return "employee/register";
    }

    /** employee登録*/
    @PostMapping("/register")
    public String postRegister(@ModelAttribute Employee employee, Model model) {
    if("".equals(employee.getAuthentication().getCode())||"".equals(employee.getAuthentication().getPassword())|| "".equals(employee.getName())) {
            model.addAttribute("error","必須項目が空欄となっています。");
            return "employee/register";
        } else if(authenticationservice.findByCode(employee.getAuthentication().getCode()) != null) {
            model.addAttribute("error2","既に登録済の社員番号です。");
            return "employee/register";
            }
        employee.getAuthentication().setEmployee(employee);
        employee.getAuthentication().setPassword(passwordEncoder.encode(employee.getAuthentication().getPassword()));
        // Employee登録
        service.saveEmployee(employee);
        // 一覧画面にリダイレクト
        return "redirect:/employee/list";
    }

    /** Employee更新画面を表示 */
    @GetMapping("/update/{id}/")
    public String getUpdate(@PathVariable("id") Integer id, Model model) {
        // Modelに登録
        model.addAttribute("employee", service.getEmployee(id));
        // employee更新画面に遷移
        return "employee/update";
    }

    /** Employee更新処理 */
    @PostMapping("/update/{id}/")
    public String postUpdate(@PathVariable("id") Integer id,@ModelAttribute("employee") Employee employee,Employee existing, Model model) {
        if("".equals(employee.getName())) {
           model.addAttribute("error","必須項目が空欄となっています。");
           model.addAttribute("employee", service.getEmployee(id));
           employee.getAuthentication().setEmployee(employee);
           return "employee/update";
        }
        // パスワードの入力がnullかどうか確認し、nullの場合は、前のパスワードをセットする。
        if ((employee.getAuthentication().getPassword().isEmpty())){
            existing = service.getEmployee(id);
            employee.getAuthentication().setEmployee(existing);
            employee.getAuthentication().setPassword(existing.getAuthentication().getPassword());
        }
        // Employee登録
        employee.getAuthentication().setEmployee(employee);
        employee.getAuthentication().setPassword(passwordEncoder.encode(employee.getAuthentication().getPassword()));
        service.saveEmployee(employee);
        // 一覧画面にリダイレクト
        return "redirect:/employee/list";
    }

    /** Employee更新処理 */
    @GetMapping("/delete/{id}/")
    @PostMapping("/delete/{id}/")
    public String postDelete(@PathVariable("id") Integer id,Employee employee) {
        employee = service.getEmployee(id);
        employee.setDeleteFlag(1);
        service.saveEmployee(employee);
        // 一覧画面にリダイレクト
        return "redirect:/employee/list";
    }
}
