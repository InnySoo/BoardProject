package com.sooin.boardback.service;

import org.springframework.http.ResponseEntity;

import com.sooin.boardback.dto.response.search.GetPopularListResponseDto;
import com.sooin.boardback.dto.response.search.GetRelationListResponseDto;

public interface SearchService {

  ResponseEntity<? super GetPopularListResponseDto> getPopularList();

  ResponseEntity<? super GetRelationListResponseDto> getRelationList(String searchWord);

}
