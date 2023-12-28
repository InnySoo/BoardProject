package com.sooin.boardback.service;

import org.springframework.http.ResponseEntity;

import com.sooin.boardback.dto.response.user.GetSignInUserResponseDto;

public interface UserService {

  ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email);
  
}
