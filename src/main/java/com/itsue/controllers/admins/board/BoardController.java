package com.itsue.controllers.admins.board;

import com.itsue.commons.MenuForm;
import com.itsue.commons.Menus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller("adminBoardController")
@RequestMapping("admin/board")
public class BoardController {

    @GetMapping
    public String index(Model model){
        commonProcess(model);

        return "admin/board/index";
    }

    private void commonProcess(Model model){
        List<MenuForm> submenus = Menus.gets("board");
        model.addAttribute("submenus",submenus);
    }

}
