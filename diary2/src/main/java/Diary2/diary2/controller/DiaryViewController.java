package Diary2.diary2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DiaryViewController {

    @GetMapping("/diary")
    public String diaryView() {
        return "diary";
    }
}