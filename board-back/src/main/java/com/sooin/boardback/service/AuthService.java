package com.sooin.boardback.service;

import org.springframework.http.ResponseEntity;

import com.sooin.boardback.dto.request.auth.SignInRequestDto;
import com.sooin.boardback.dto.request.auth.SignUpRequestDto;
import com.sooin.boardback.dto.response.auth.SignInResponseDto;
import com.sooin.boardback.dto.response.auth.SignUpResponseDto;

public interface AuthService {

  ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);
  ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);
  
}
