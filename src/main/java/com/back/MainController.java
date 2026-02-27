package com.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/index")
    @ResponseBody //브라우저에 표시하도록 응답 본문에 직접 문자열을 반환하도록 지정
    public String index() {
        return "sbb";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello";

    }
}
