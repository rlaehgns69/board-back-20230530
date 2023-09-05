package com.dohoon.board.service;

import org.springframework.http.ResponseEntity;

import com.dohoon.board.dto.request.auth.SignInRequestDto;
import com.dohoon.board.dto.request.auth.SignUpRequestDto;
import com.dohoon.board.dto.response.auth.SignInResponseDto;
import com.dohoon.board.dto.response.auth.SignUpResponseDto;

public interface AuthService {
  
  // method : 로그인 메서드 //
  ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);
  // method : 회원가입 메서드 //
  ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);

}
