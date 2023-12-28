package com.sooin.boardback.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sooin.boardback.dto.response.ResponseDto;
import com.sooin.boardback.dto.response.user.GetSignInUserResponseDto;
import com.sooin.boardback.entity.UserEntity;
import com.sooin.boardback.repository.UserRepository;
import com.sooin.boardback.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {

  private final UserRepository userRepository;
  
  @Override
  public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email) {

    UserEntity userEntity = null;

    try {

      userEntity = userRepository.findByEmail(email);
      if (userEntity == null) return GetSignInUserResponseDto.notExistUser();

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetSignInUserResponseDto.success(userEntity);

  }
  
}
