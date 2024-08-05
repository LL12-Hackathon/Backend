package com.example.meetpro.controller;

import com.example.meetpro.entities.UserEntity;
import com.example.meetpro.service.MemberService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("com.example.meetpro.controller.MemberController")
@RequestMapping("member")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegister() {
        return "member/register";
    }

    @RequestMapping(value = "/registerDetail", method = RequestMethod.GET)
    public String getRegisterDetail() {
        return "member/registerDetail";
    }

    @RequestMapping(value = "/registerDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postRegister(UserEntity user) throws Exception {
        Enum<?> result = this.memberService.register(user);
        JSONObject responseObject = new JSONObject();
        responseObject.put("result", result.name().toLowerCase());
        responseObject.put("name", user.getName());
        return responseObject.toString();
    }

    @GetMapping("/api/hello")
    public String hello() {
        return "hi, reactBoot";
    }
}
