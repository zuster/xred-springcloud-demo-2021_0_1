package com.xred.service.discovery.multi.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Test Controller
 *
 * @author songyh
 * @date 2022/5/13 12:44 下午
 */
@RestController
public class TestController {
    @GetMapping("/echo")
    public String echo(HttpServletRequest request) {
        return "echo: " + request.getParameter("name");
    }
}
