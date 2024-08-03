package com.example.meetpro.controller.Board;

import com.example.meetpro.service.MemberService;
import com.example.meetpro.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;
    private final BoardService boardService;

    @GetMapping(value = {"", "/"})
    public String home(Model model) {
        model.addAttribute("userCntDto", memberService.getUserCnt());
        model.addAttribute("boardCntDto", boardService.getBoardCnt());
        return "home";
    }
}