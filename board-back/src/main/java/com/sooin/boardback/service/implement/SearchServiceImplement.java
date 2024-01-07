package com.sooin.boardback.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sooin.boardback.dto.response.ResponseDto;
import com.sooin.boardback.dto.response.search.GetPopularListResponseDto;
import com.sooin.boardback.dto.response.search.GetRelationListResponseDto;
import com.sooin.boardback.repository.SearchLogRepository;
import com.sooin.boardback.repository.resultSet.GetPopularListResultSet;
import com.sooin.boardback.repository.resultSet.GetRelationListResultSet;
import com.sooin.boardback.service.SearchService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchServiceImplement implements SearchService {

  private final SearchLogRepository searchLogRepository;

  @Override
  public ResponseEntity<? super GetPopularListResponseDto> getPopularList() {

    List<GetPopularListResultSet> resultSets = new ArrayList<>();

    try {

      resultSets = searchLogRepository.getPopularList();

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetPopularListResponseDto.success(resultSets);

  }

  @Override
  public ResponseEntity<? super GetRelationListResponseDto> getRelationList(String searchWord) {

    List<GetRelationListResultSet> resultSets = new ArrayList<>();

    try {

      resultSets = searchLogRepository.getRelationList(searchWord);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetRelationListResponseDto.success(resultSets);
    
  }
  
}
