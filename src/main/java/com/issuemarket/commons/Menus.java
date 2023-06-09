package com.issuemarket.commons;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class Menus {

    public List<MenuForm> _gets(String code) {
        return Menus.gets(code);
    }

    public static List<MenuForm> gets(String code) { // _submenu
        List<MenuForm> menus = new ArrayList<>();

        /** admin */
        if (code.equals("board")) { // board 하위 메뉴
            menus.add(new MenuForm("board", "게시판 목록", "/admin/board"));
            menus.add(new MenuForm("register", "게시판 등록 & 수정", "/admin/board/register"));
            menus.add(new MenuForm("posts", "게시글 관리", "/admin/board/posts"));
        }


        /** front */
        if (code.equals("freetalk")) {
            menus.add(new MenuForm("freetalk", "자유게시판@", "/front/board/postlist"));
        }

        return menus;
    }

    public static String getSubMenuCode(HttpServletRequest request){
        String uri = request.getRequestURI();


        return uri.substring(uri.lastIndexOf('/') + 1);
    }
}
