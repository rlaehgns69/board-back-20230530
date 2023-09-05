package com.dohoon.board.dto.response.board;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dohoon.board.common.response.ResponseCode;
import com.dohoon.board.common.response.ResponseMessage;
import com.dohoon.board.dto.response.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteBoardResponseDto extends ResponseDto {
  
  private DeleteBoardResponseDto(String code, String message) {
    super(code, message);
  }

  public static ResponseEntity<DeleteBoardResponseDto> success() {
    DeleteBoardResponseDto result = new DeleteBoardResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    return ResponseEntity.status(HttpStatus.OK).body(result); 
  }

  public static ResponseEntity<ResponseDto> noExistedUser() {
    ResponseDto result = new ResponseDto(ResponseCode.NO_EXISTED_USER, ResponseMessage.NO_EXISTED_USER);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result); 
  }

  public static ResponseEntity<ResponseDto> noExistedBoard() {
    ResponseDto result = new ResponseDto(ResponseCode.NO_EXISTED_BOARD, ResponseMessage.NO_EXISTED_BOARD);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result); 
  }

  public static ResponseEntity<ResponseDto> noPermission() {
    ResponseDto result = new ResponseDto(ResponseCode.NO_PERMISSION, ResponseMessage.NO_PERMISSION);
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result); 
  }

}
