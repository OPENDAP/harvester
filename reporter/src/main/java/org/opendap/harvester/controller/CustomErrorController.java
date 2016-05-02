package org.opendap.harvester.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CustomErrorController implements ErrorController {
    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String error(HttpServletRequest request, HttpServletResponse response) {
        return "404.html";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
