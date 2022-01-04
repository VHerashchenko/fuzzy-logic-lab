package com.example.fuzzylogic.controller;

import com.example.fuzzylogic.model.ComponentDto;
import com.example.fuzzylogic.service.ComponentLogicService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ComponentController {

    private final ModelMapper mapper;

    private final ComponentLogicService componentLogicService;

    @GetMapping("/component")
    public String getComponentPage(Model model) {
        model.addAttribute("settings", new ComponentDto());
        return "index";
    }

    @PostMapping("/component/settings")
    public String setComponentSettings(@ModelAttribute("settings") ComponentDto componentDto) {
        var componentEfficient = componentLogicService.process(componentDto);
        componentLogicService.addComponent(componentEfficient);

        return "redirect:/component/" + componentEfficient.getId() + "/result";
    }

    @GetMapping("/component/{id}/result")
    public String result(@PathVariable int id, Model model) {
        model.addAttribute("component", componentLogicService.getComponent(id));
        return "result";
    }
}
