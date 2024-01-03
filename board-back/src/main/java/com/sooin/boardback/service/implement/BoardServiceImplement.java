package com.sooin.boardback.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sooin.boardback.dto.request.board.PatchBoardRequestDto;
import com.sooin.boardback.dto.request.board.PostBoardRequestDto;
import com.sooin.boardback.dto.request.board.PostCommentRequestDto;
import com.sooin.boardback.dto.response.ResponseDto;
import com.sooin.boardback.dto.response.board.DeleteBoardResponseDto;
import com.sooin.boardback.dto.response.board.GetBoardResponseDto;
import com.sooin.boardback.dto.response.board.GetCommentListResponseDto;
import com.sooin.boardback.dto.response.board.GetFavoriteListResponseDto;
import com.sooin.boardback.dto.response.board.GetLatestBoardListResponseDto;
import com.sooin.boardback.dto.response.board.IncreaseViewCountResponseDto;
import com.sooin.boardback.dto.response.board.PatchBoardResponseDto;
import com.sooin.boardback.dto.response.board.PostBoardResponseDto;
import com.sooin.boardback.dto.response.board.PostCommentResponseDto;
import com.sooin.boardback.dto.response.board.PutFavoriteResponseDto;
import com.sooin.boardback.entity.BoardEntity;
import com.sooin.boardback.entity.BoardListViewEntity;
import com.sooin.boardback.entity.CommentEntity;
import com.sooin.boardback.entity.FavoriteEntity;
import com.sooin.boardback.entity.ImageEntity;
import com.sooin.boardback.repository.BoardListViewRepository;
import com.sooin.boardback.repository.BoardRepository;
import com.sooin.boardback.repository.CommentRepository;
import com.sooin.boardback.repository.FavoriteRepository;
import com.sooin.boardback.repository.ImageRepository;
import com.sooin.boardback.repository.UserRepository;
import com.sooin.boardback.repository.resultSet.GetBoardResultSet;
import com.sooin.boardback.repository.resultSet.GetCommentListResultSet;
import com.sooin.boardback.repository.resultSet.GetFavoriteListResultSet;
import com.sooin.boardback.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImplement implements BoardService {

  private final UserRepository userRepository;
  private final BoardRepository boardRepository;
  private final ImageRepository imageRepository;
  private final CommentRepository commentRepository;
  private final FavoriteRepository favoriteRepository;
  private final BoardListViewRepository boardListViewRepository;

  @Override
  public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber) {

    GetBoardResultSet resultSet = null;
    List<ImageEntity> imageEntities = new ArrayList<>();

    try {

      resultSet = boardRepository.getBoard(boardNumber);
      if (resultSet == null) return GetBoardResponseDto.noExistBoard();

      imageEntities = imageRepository.findByBoardNumber(boardNumber);

    } catch(Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetBoardResponseDto.success(resultSet, imageEntities);

  }

  
  @Override
  public ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer boardNumber) {

    List<GetFavoriteListResultSet> resultSets = new ArrayList<>();

    try {

      boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
      if (!existedBoard) return GetFavoriteListResponseDto.noExistBoard();

      resultSets = favoriteRepository.getFavoriteList(boardNumber);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetFavoriteListResponseDto.success(resultSets);

  }
  
  @Override
  public ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer boardNumber) {

    List<GetCommentListResultSet> resultSets = new ArrayList<>();

    try {

      boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
      if (!existedBoard) return GetCommentListResponseDto.noExistBoard();

      resultSets = commentRepository.getCommentList(boardNumber);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetCommentListResponseDto.success(resultSets);

  }

  
  @Override
  public ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList() {

    List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();

    try {

      boardListViewEntities = boardListViewRepository.findByOrderByWriteDatetimeDesc();

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetLatestBoardListResponseDto.success(boardListViewEntities);

  }

  
  @Override
  public ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email) {

    try {

      boolean existedEmail = userRepository.existsByEmail(email);
      if (!existedEmail) return PostBoardResponseDto.noExistUser();

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

  
  @Override
  public ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer boardNumber, String email) {

    try {

      BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
      if (boardEntity == null) return PostCommentResponseDto.noExistBoard();

      boolean existedUser = userRepository.existsByEmail(email);
      if (!existedUser) return PostCommentResponseDto.noExistUser();

      CommentEntity commentEntity = new CommentEntity(dto, boardNumber, email);
      commentRepository.save(commentEntity);

      boardEntity.increaseCommentCount();
      boardRepository.save(boardEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return PostCommentResponseDto.success();

  }


  @Override
  public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber, String email) {

    try {

      boolean existedUser = userRepository.existsByEmail(email);
      if (!existedUser) return PutFavoriteResponseDto.noExistUser();

      BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
      if (boardEntity == null) return PutFavoriteResponseDto.noExistBoard();

      FavoriteEntity favoriteEntity = favoriteRepository.findByBoardNumberAndUserEmail(boardNumber, email);
      if (favoriteEntity == null) {
        favoriteEntity = new FavoriteEntity(email, boardNumber);
        favoriteRepository.save(favoriteEntity);
        boardEntity.increaseFavoriteCount();
      }
      else {
        favoriteRepository.delete(favoriteEntity);
        boardEntity.decreaseFavoriteCount();
      }

      boardRepository.save(boardEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return PutFavoriteResponseDto.success();

  }
  
  @Override
  public ResponseEntity<? super PatchBoardResponseDto> patchBoard(PatchBoardRequestDto dto, Integer boardNumber, String email) {
    
    try {

      BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
      if (boardEntity == null) return PatchBoardResponseDto.noExistBoard();

      boolean existedUser = userRepository.existsByEmail(email);
      if (!existedUser) return PatchBoardResponseDto.noExistUser();

      String writerEmail = boardEntity.getWriterEmail();
      boolean isWriter = writerEmail.equals(email);
      if (!isWriter) return PatchBoardResponseDto.noPermission();

      boardEntity.PatchBoard(dto);
      boardRepository.save(boardEntity);

      imageRepository.deleteByBoardNumber(boardNumber);
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

    return PatchBoardResponseDto.success();

  }


  @Override
  public ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(Integer boardNumber) {

    try {
      BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
      if (boardEntity == null) return IncreaseViewCountResponseDto.noExistBoard();

      boardEntity.increaseViewCount();
      boardRepository.save(boardEntity);
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return IncreaseViewCountResponseDto.success();

  }


  @Override
  public ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(Integer boardNumber, String email) {

    try {

      boolean existedUser = userRepository.existsByEmail(email);
      if (!existedUser) return DeleteBoardResponseDto.noExistUser();

      BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
      if (boardEntity == null) return DeleteBoardResponseDto.noExistBoard();

      String writerEmail = boardEntity.getWriterEmail();
      boolean isWriter = writerEmail.equals(email);
      if (!isWriter) return DeleteBoardResponseDto.noPermission();

      imageRepository.deleteByBoardNumber(boardNumber);
      commentRepository.deleteByBoardNumber(boardNumber);
      favoriteRepository.deleteByBoardNumber(boardNumber);
      
      boardRepository.delete(boardEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return DeleteBoardResponseDto.success();

  }



  
}
