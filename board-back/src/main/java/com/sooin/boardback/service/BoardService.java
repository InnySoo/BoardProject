package com.sooin.boardback.service;

import org.springframework.http.ResponseEntity;

import com.sooin.boardback.dto.request.board.PostBoardRequestDto;
import com.sooin.boardback.dto.response.board.PostBoardResponseDto;

public interface BoardService {

  ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email);
  
}
