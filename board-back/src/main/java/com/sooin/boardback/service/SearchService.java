package com.sooin.boardback.service;

import org.springframework.http.ResponseEntity;

import com.sooin.boardback.dto.response.search.GetPopularListResponseDto;

public interface SearchService {

  ResponseEntity<? super GetPopularListResponseDto> getPopularList();

}
