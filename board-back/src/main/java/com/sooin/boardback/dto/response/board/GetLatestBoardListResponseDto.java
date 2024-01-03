package com.sooin.boardback.dto.response.board;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sooin.boardback.common.ResponseCode;
import com.sooin.boardback.common.ResponseMessage;
import com.sooin.boardback.dto.object.BoardListItem;
import com.sooin.boardback.dto.response.ResponseDto;
import com.sooin.boardback.entity.BoardListViewEntity;

import lombok.Getter;

@Getter
public class GetLatestBoardListResponseDto extends ResponseDto {
  
  private List<BoardListItem> latestList;

  private GetLatestBoardListResponseDto(List<BoardListViewEntity> boardListViewEntities) {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    this.latestList = BoardListItem.getList(boardListViewEntities);
  }

  public static ResponseEntity<GetLatestBoardListResponseDto> success(List<BoardListViewEntity> boardListViewEntities) {
    GetLatestBoardListResponseDto result = new GetLatestBoardListResponseDto(boardListViewEntities);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
