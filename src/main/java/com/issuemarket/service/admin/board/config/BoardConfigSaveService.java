package com.issuemarket.service.admin.board.config;

import com.issuemarket.commons.constants.Role;
import com.issuemarket.entities.Board;
import com.issuemarket.entities.BoardForm;
import com.issuemarket.exception.DuplicateBoardConfigException;
import com.issuemarket.repositories.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
public class BoardConfigSaveService {

    private final BoardRepository boardRepository;


    public void save(BoardForm boardForm) {
        save(boardForm, null);
    }

    public void save(BoardForm boardForm, Errors errors){
        if (errors != null && errors.hasErrors()) {
            return;
        }

        Board board = boardRepository.findById(boardForm.getBId()).orElseGet(Board::new);

        String mode = boardForm.getMode();
        if ((mode == null || !mode.equals("update")) && board.getBId() != null) {
            throw new DuplicateBoardConfigException();
        }

        board = Board.builder()
                .bId(boardForm.getBId())
                .bName(boardForm.getBName())
                .use(boardForm.isUse())
                .rowsOfPage(boardForm.getRowsOfPage())
                .showViewList(boardForm.isShowViewList())
                .category(boardForm.getCategory())

                .listAccessRole(Role.valueOf(boardForm.getListAccessRole()))
                .viewAccessRole(Role.valueOf(boardForm.getViewAccessRole()))
                .writeAccessRole(Role.valueOf(boardForm.getWriteAccessRole()))
                .replyAccessRole(Role.valueOf(boardForm.getReplyAccessRole()))
                .commentAccessRole(Role.valueOf(boardForm.getCommentAccessRole()))

                .useEditor(boardForm.isUseEditor())
                .useAttachFile(boardForm.isUseAttachFile())
                .useAttachImage(boardForm.isUseAttachImage())
                .locationAfterWriting(boardForm.getLocationAfterWriting())
                .useReply(boardForm.isUseReply())
                .useComment(boardForm.isUseComment())
                .build();

        boardRepository.saveAndFlush(board);
    }

}
