package com.dohoon.board.dto.response.board;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dohoon.board.common.response.ResponseCode;
import com.dohoon.board.common.response.ResponseMessage;
import com.dohoon.board.dto.response.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class GetUserListResponsedto extends ResponseDto {

  private List<BoardListResponseDto> boardList;

  private GetUserListResponsedto(String code, String message, List<BoardListResponseDto> boardList) {
    super(code, message);
    this.boardList = boardList;
  }

  public static ResponseEntity<GetUserListResponsedto> success(List<BoardListResponseDto> boardList){
    GetUserListResponsedto result = new GetUserListResponsedto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, boardList);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  } 
}