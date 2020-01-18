package com.satyam.adminlte.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 
 * @author satyam.kumar
 *
 */
@Controller
public class DashboardController {

    @RequestMapping("/")
    public String index() {
        return "/layout";
    }

}
