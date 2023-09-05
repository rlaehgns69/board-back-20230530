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

public class GetCommentListResponseDto extends ResponseDto {

  private List<CommentListResponseDto> commentList;

  private GetCommentListResponseDto(String code, String message, List<CommentListResponseDto> commentList) {
    super(code, message);
    this.commentList = commentList;
  }

  public static ResponseEntity<GetCommentListResponseDto> success(List<CommentListResponseDto> commentList) {
    GetCommentListResponseDto result = new GetCommentListResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, commentList);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
