package com.issuemarket.service.admin.board.config;

import com.issuemarket.dto.BoardSearch;
import com.issuemarket.entities.Board;
import com.issuemarket.entities.QBoard;
import com.issuemarket.repositories.BoardRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardConfigListService {

    private final BoardRepository boardRepository;

    public Page<Board> gets(BoardSearch boardSearch){
        QBoard board = QBoard.board;
        BooleanBuilder builder = new BooleanBuilder();

        int page = boardSearch.getPage();
        int pageSize = boardSearch.getPageSize();

        page = page < 1 ? 1 : page;
        pageSize = pageSize < 1 ? 20 : pageSize;


        String sopt = boardSearch.getSopt();
        String skey = boardSearch.getSkey();
        if (sopt != null && !sopt.isBlank() && skey != null && !skey.isBlank())  {
            sopt = sopt.trim();
            skey = skey.trim();

            if (sopt.equals("all")) {
                builder.and(board.bId.contains(skey))
                        .or(board.bName.contains(skey));
            } else if (sopt.equals("bId")) {
                builder.and(board.bId.contains(skey));
            } else if (sopt.equals("bName")) {
                builder.and(board.bName.contains(skey));
            }
        }

        Pageable pageable = PageRequest.of(page -1, pageSize, Sort.by(Sort.Order.asc("createdAt")));
        Page<Board> boardsList = boardRepository.findAll(builder, pageable);

        return boardsList;
    }

}
