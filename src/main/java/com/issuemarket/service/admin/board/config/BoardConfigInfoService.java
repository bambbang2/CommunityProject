package com.issuemarket.service.admin.board.config;

import com.issuemarket.commons.MemberUtil;
import com.issuemarket.commons.constants.Role;
import com.issuemarket.entities.Board;
import com.issuemarket.exception.BoardConfigNotExistException;
import com.issuemarket.exception.BoardNotAllowAccessException;
import com.issuemarket.repositories.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardConfigInfoService {

    private final BoardRepository boardRepository;
    private final MemberUtil memberUtil;


    public Board get(String bId, String location){ // 프론트, 접근 권한 체크

        return get(bId, false, location);
    }

    public Board get(String bId, boolean isAdmin, String location) {

        Board board = boardRepository.findById(bId).orElseThrow(BoardConfigNotExistException::new);

        if (!isAdmin) {
            accessCheck(board, location);
        }

        return board;
    }

    public Board get(String bId, boolean isAdmin) {
        return get(bId, isAdmin, null);
    }

    private void accessCheck(Board board, String location) {
        if (!board.isUse() && !memberUtil.isAdmin()) {
            throw new BoardNotAllowAccessException();
        }

        Role role = Role.ALL;
        if (location.equals("list")) { // 게시글 목록
            role = board.getListAccessRole();
        } else if (location.equals("view")) { // 게시글 보기
            role = board.getViewAccessRole();
        } else if (location.equals("write")) { // 글쓰기
            role = board.getWriteAccessRole();

            if (!memberUtil.isLogin()) board.setGuest(true); // 비회원도 가능

        } else if (location.equals("comment")) { // 댓글
            role = board.getCommentAccessRole();
        }


        if ((role == Role.USER && !memberUtil.isLogin()) ||
             role == Role.ADMIN && !memberUtil.isAdmin()) {
            throw new BoardNotAllowAccessException();
        }


    }
}
