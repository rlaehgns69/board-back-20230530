package com.dohoon.board.dto.response.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dohoon.board.common.response.ResponseCode;
import com.dohoon.board.common.response.ResponseMessage;
import com.dohoon.board.dto.response.ResponseDto;
import com.dohoon.board.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetSignInResponseDto extends ResponseDto {
  
  private String email;
  private String nickname;
  private String profileImageUrl;

  private GetSignInResponseDto(String code, String message, UserEntity userEntity) {
    super(code, message);
    this.email=userEntity.getEmail();
    this.nickname=userEntity.getNickname();
    this.profileImageUrl=userEntity.getProfileImageUrl();
  }

  public static ResponseEntity<GetSignInResponseDto> success(UserEntity userEntity) {
    GetSignInResponseDto result = new GetSignInResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, userEntity);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  public static ResponseEntity<ResponseDto> noExistedUser() {
    ResponseDto result = new ResponseDto(ResponseCode.NO_EXISTED_USER, ResponseMessage.NO_EXISTED_USER);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
  }


}
