package com.sooin.boardback.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sooin.boardback.dto.request.board.PostBoardRequestDto;
import com.sooin.boardback.dto.response.ResponseDto;
import com.sooin.boardback.dto.response.board.PostBoardResponseDto;
import com.sooin.boardback.entity.BoardEntity;
import com.sooin.boardback.entity.ImageEntity;
import com.sooin.boardback.repository.BoardRepository;
import com.sooin.boardback.repository.ImageRepository;
import com.sooin.boardback.repository.UserRepository;
import com.sooin.boardback.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImplement implements BoardService {

  private final UserRepository userRepository;
  private final BoardRepository boardRepository;
  private final ImageRepository imageRepository;
  
  @Override
  public ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email) {

    try {

      boolean existedEmail = userRepository.existsByEmail(email);
      if (!existedEmail) return PostBoardResponseDto.notExistUser();

      BoardEntity boardEntity = new BoardEntity(dto, email);
      boardRepository.save(boardEntity);

      int boardNumber = boardEntity.getBoardNumber();

      List<String> boardImageList = dto.getBoardImageList();
      List<ImageEntity> imageEntities = new ArrayList<>();

      for (String image: boardImageList) {
        ImageEntity imageEntity = new ImageEntity(boardNumber, image);
        imageEntities.add(imageEntity);
      }
      
      imageRepository.saveAll(imageEntities);
      
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return PostBoardResponseDto.success();

  }
  
}
