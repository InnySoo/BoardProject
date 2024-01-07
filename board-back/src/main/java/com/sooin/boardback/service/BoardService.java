package com.sooin.boardback.service;

import org.springframework.http.ResponseEntity;

import com.sooin.boardback.dto.request.board.PatchBoardRequestDto;
import com.sooin.boardback.dto.request.board.PostBoardRequestDto;
import com.sooin.boardback.dto.request.board.PostCommentRequestDto;
import com.sooin.boardback.dto.response.board.DeleteBoardResponseDto;
import com.sooin.boardback.dto.response.board.GetBoardResponseDto;
import com.sooin.boardback.dto.response.board.GetCommentListResponseDto;
import com.sooin.boardback.dto.response.board.GetFavoriteListResponseDto;
import com.sooin.boardback.dto.response.board.GetLatestBoardListResponseDto;
import com.sooin.boardback.dto.response.board.GetSearchBoardListResponseDto;
import com.sooin.boardback.dto.response.board.GetTop3BoardListResponseDto;
import com.sooin.boardback.dto.response.board.IncreaseViewCountResponseDto;
import com.sooin.boardback.dto.response.board.PatchBoardResponseDto;
import com.sooin.boardback.dto.response.board.PostBoardResponseDto;
import com.sooin.boardback.dto.response.board.PostCommentResponseDto;
import com.sooin.boardback.dto.response.board.PutFavoriteResponseDto;

public interface BoardService {

  ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber);

  ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer boardNumber);

  ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer boardNumber);

  ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList();

  ResponseEntity<? super GetTop3BoardListResponseDto> getTop3BoardList();

  ResponseEntity<? super GetSearchBoardListResponseDto> getSearchBoardList(String searchWord, String preSearchWord);

  ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email);

  ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer boardNumber, String email);

  ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber, String email);

  ResponseEntity<? super PatchBoardResponseDto> patchBoard(PatchBoardRequestDto dto, Integer boardNumber, String email);

  ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(Integer boardNumber);

  ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(Integer boardNumber, String email);

  
}
