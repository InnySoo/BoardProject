package com.sooin.boardback.service;

import org.springframework.http.ResponseEntity;

import com.sooin.boardback.dto.response.user.GetSignInUserResponseDto;
import com.sooin.boardback.dto.response.user.GetUserResponseDto;

public interface UserService {

  ResponseEntity<? super GetUserResponseDto> getUser(String email);

  ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email);
  
}
