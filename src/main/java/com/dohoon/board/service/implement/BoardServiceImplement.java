package com.dohoon.board.service.implement;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dohoon.board.dto.request.board.PatchBoardRequestDto;
import com.dohoon.board.dto.request.board.PostBoardRequestDto;
import com.dohoon.board.dto.request.board.PostCommentRequestDto;
import com.dohoon.board.dto.response.ResponseDto;
import com.dohoon.board.dto.response.board.BoardListResponseDto;
import com.dohoon.board.dto.response.board.CommentListResponseDto;
import com.dohoon.board.dto.response.board.DeleteBoardResponseDto;
import com.dohoon.board.dto.response.board.FavoriteListResponseDto;
import com.dohoon.board.dto.response.board.GetBoardResponseDto;
import com.dohoon.board.dto.response.board.GetCommentListResponseDto;
import com.dohoon.board.dto.response.board.GetCurrentBoardResponseDto;
import com.dohoon.board.dto.response.board.GetFavoriteListResponseDto;
import com.dohoon.board.dto.response.board.GetSearchBoardResponseDto;
import com.dohoon.board.dto.response.board.GetTop3ResponseDto;
import com.dohoon.board.dto.response.board.GetUserListResponsedto;
import com.dohoon.board.dto.response.board.PatchBoardResponseDto;
import com.dohoon.board.dto.response.board.PostBoardResponseDto;
import com.dohoon.board.dto.response.board.PostCommentResponseDto;
import com.dohoon.board.dto.response.board.PutFavoriteResponseDto;
import com.dohoon.board.entity.BoardEntity;
import com.dohoon.board.entity.BoardViewEntity;
import com.dohoon.board.entity.CommentEntity;
import com.dohoon.board.entity.FavoriteEntity;
import com.dohoon.board.entity.SearchLogEntity;
import com.dohoon.board.entity.UserEntity;
import com.dohoon.board.entity.resultSet.BoardListResultSet;
import com.dohoon.board.entity.resultSet.CommentListResultSet;
import com.dohoon.board.repository.BoardRepository;
import com.dohoon.board.repository.BoardViewRepository;
import com.dohoon.board.repository.CommentRepository;
import com.dohoon.board.repository.FavoriteRepository;
import com.dohoon.board.repository.SearchLogRepository;
import com.dohoon.board.repository.UserRepository;
import com.dohoon.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImplement implements BoardService {

  private final UserRepository userRepository;
  private final BoardRepository boardRepository;
  private final CommentRepository commentRepository;
  private final FavoriteRepository favoriteRepository;
  private final BoardViewRepository boardViewRepository;
  private final SearchLogRepository searchLogRepository;

  @Override
  public ResponseEntity<? super GetTop3ResponseDto> getTop3() {
    
    List<BoardListResponseDto> top3 = null;

    try {
      // description: 좋아요 순으로 상위 3개 게시물 조회 //
      List<BoardViewEntity> boardViewEntities = boardViewRepository.findTop3ByOrderByFavoriteCountDesc();

      // description: entity를 dto 형태로 변환 //
      top3 = BoardListResponseDto.copyEntityList(boardViewEntities);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetTop3ResponseDto.success(top3);

  }

  @Override
  public ResponseEntity<? super GetCurrentBoardResponseDto> getCurrentBoard(Integer section) {
    
    List<BoardListResponseDto> boardList = null;

    try {

      // description: 최신 게시물 리스트 불러오기 //
      Integer limit = (section - 1) * 50;
      List<BoardListResultSet> resultSets = boardRepository.getCurrentList(limit);

      // description: 검색 결과를 ResponseDto 형태로 변환 //
      boardList = BoardListResponseDto.copyList(resultSets);

    } catch(Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetCurrentBoardResponseDto.success(boardList);

  }

  @Override
  public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber) {
    
    BoardViewEntity boardViewEntity = null;

    try {
      // description: 게시물 번호에 해당하는 게시물 조회 //
      boardViewEntity = boardViewRepository.findByBoardNumber(boardNumber);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetBoardResponseDto.success(boardViewEntity);

  }

  @Override
  public ResponseEntity<? super GetSearchBoardResponseDto> getSearchBoard(String searchWord, String relationWord) {
    
    List<BoardListResponseDto> boardList = null;

    try {

      // description: 검색어가 제목과 내용에 포함되어 있는 데이터 조회 //
      List<BoardViewEntity> boardViewEntities = boardViewRepository.findByTitleContainsOrContentsContainsOrderByWriteDatetimeDesc(searchWord, searchWord);

      // description: entity를 dto형태로 변환 //
      boardList = BoardListResponseDto.copyEntityList(boardViewEntities);

      // description: 검색어 로그 저장 //
      SearchLogEntity searchLogEntity = new SearchLogEntity(searchWord, relationWord);
      searchLogRepository.save(searchLogEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetSearchBoardResponseDto.success(boardList);

  }

  @Override
  public ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer boardNumber) {
   
     List<FavoriteListResponseDto> favoriteList = null;

    try {
      
      // description: 게시물 번호의 좋아요 리스트 조회 //
      List<UserEntity> userEntities = userRepository.getFavoriteList(boardNumber);

      // description: entity를 dto로 변환 //
      favoriteList = FavoriteListResponseDto.copyEntityList(userEntities);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetFavoriteListResponseDto.success(favoriteList);
  }

  @Override
  public ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer boardNumber) {
    
    List<CommentListResponseDto> commentList = null;

    try {

      // description: 게시물의 댓글 리스트 조회 //
      List<CommentListResultSet> resultSets = commentRepository.getCommentList(boardNumber);

      // description: resultSet을 dto로 변환 //
      commentList = CommentListResponseDto.copyList(resultSets);

    } catch(Exception exception){
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetCommentListResponseDto.success(commentList);
  }

  @Override
  public ResponseEntity<? super GetUserListResponsedto> getUserList(String email) {
    
    List<BoardListResponseDto> boardList = null;

    try {
      // description: 특정 이메일에 해당하는 게시물 리스트 조회 //
      List<BoardViewEntity> boardViewEntities = boardViewRepository.findByWriterEmailOrderByWriteDatetimeDesc(email);

      // description: entity를 dto로 변환 //
      boardList = BoardListResponseDto.copyEntityList(boardViewEntities);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetUserListResponsedto.success(boardList);

  }

  @Override
  public ResponseEntity<? super PostBoardResponseDto> postBoard(String writerEmail, PostBoardRequestDto dto) {
    
    try {
      // description: 작성자 이메일이 존재하는 이메일인지 확인 //
      boolean hasUser = userRepository.existsByEmail(writerEmail);
      if (!hasUser) return PostBoardResponseDto.noExistedUser();

      // description: entity 생성 //
      BoardEntity boardEntity = new BoardEntity(writerEmail, dto);

      // description: 데이터베이스에 저장 //
      boardRepository.save(boardEntity);

    } catch(Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return PostBoardResponseDto.success();

  }

  @Override
  public ResponseEntity<? super PostCommentResponseDto> postComment(Integer boardNumber, String userEmail, PostCommentRequestDto dto) {

    try {
      // description: boardNumber가 null 인지 확인 //
      // todo: (추후 controller로 이동) //
      if (boardNumber == null) return PostCommentResponseDto.noExistedBoard();

      // description: 존재하는 회원인지 확인 //
      boolean hasUser = userRepository.existsByEmail(userEmail);
      if (!hasUser) return PostCommentResponseDto.noExistedUser();

      // description: 존재하는 게시물인지 확인 //
      boolean hasBoard = boardRepository.existsByBoardNumber(boardNumber);
      if (!hasBoard) return PostCommentResponseDto.noExistedBoard();

      // description: entity 생성 //
      CommentEntity commentEntity = new CommentEntity(boardNumber, userEmail, dto);

      // description: 데이터베이스 저장 //
      commentRepository.save(commentEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return PostCommentResponseDto.success();

  }

  @Override
  public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber, String userEmail) {

    try {

      // description: boardNumber가 null 인지 확인 //
      // todo: (추후 controller로 이동) //
      if (boardNumber == null) return PutFavoriteResponseDto.noExistedBoard();

      // description: 존재하는 회원인지 확인 //
      boolean hasUser = userRepository.existsByEmail(userEmail);
      if (!hasUser) return PutFavoriteResponseDto.noExistedUser();

      // description: 존재하는 게시물인지 확인 //
      boolean hasBoard = boardRepository.existsByBoardNumber(boardNumber);
      if (!hasBoard) return PutFavoriteResponseDto.noExistedBoard();

      // description: 해당 유저가 해당 게시물에 좋아요 했는지 확인 //
      boolean isFavorite = favoriteRepository.existsByUserEmailAndBoardNumber(userEmail, boardNumber);
      
      // description: entity 생성 //
      FavoriteEntity favoriteEntity = new FavoriteEntity(boardNumber, userEmail);
        
      // description: 이미 좋아요 했을 때 //
      if (isFavorite) favoriteRepository.delete(favoriteEntity);
      // description: 아직 좋아요 하지 않았을 때 //
      else favoriteRepository.save(favoriteEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return PutFavoriteResponseDto.success();

  }

  @Override
  public ResponseEntity<? super PatchBoardResponseDto> patchBoard(Integer boardNumber, String userEmail, PatchBoardRequestDto dto) {

    try {
      // todo: 추후 controller로 이동 //
      if (boardNumber == null) return PatchBoardResponseDto.noExistedBoard();

      // description: 존재하는 유저인지 확인 //
      boolean hasUser = userRepository.existsByEmail(userEmail);
      if (!hasUser) return PatchBoardResponseDto.noExistedUser();

      // description: 존재하는 게시물인지 확인 //
      BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
      if (boardEntity == null) return PatchBoardResponseDto.noExistedBoard();

      // description: 작성자 이메일과 입력받은 이메일이 같은지 확인 //
      boolean equalWriter = boardEntity.getWriterEmail().equals(userEmail);
      if (!equalWriter) return PatchBoardResponseDto.noPermission();

      // description: 수정 //
      boardEntity.patch(dto);
      // description: 데이터베이스에 저장 //
      boardRepository.save(boardEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return PatchBoardResponseDto.success();

  }

  @Override
  public ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(Integer boardNumber, String email) {
    
    try {

      // todo: 추후 controller로 이동 //
      if (boardNumber == null) return DeleteBoardResponseDto.noExistedBoard();

      // todo: 추후 controller로 이동 //
      if (email == null) return DeleteBoardResponseDto.noExistedUser();

      // description: 존재하는 유저인지 확인 //
      boolean hasUser = userRepository.existsByEmail(email);
      if (!hasUser) return DeleteBoardResponseDto.noExistedUser();

      // description: 존재하는 게시물인지 확인 //
      BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
      if (boardEntity == null) return DeleteBoardResponseDto.noExistedBoard();

      // description: 게시물 작성자 이메일과 입력받은 이메일이 같은지 확인 //
      boolean equalWriter = boardEntity.getWriterEmail().equals(email);
      if (!equalWriter) return DeleteBoardResponseDto.noPermission();

      // description: 댓글 데이터 삭제 //
      commentRepository.deleteByBoardNumber(boardNumber);

      // description: 좋아요 데이터 삭제 //
      favoriteRepository.deleteByBoardNumber(boardNumber);

      // description: 게시물 삭제 //
      boardRepository.delete(boardEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return DeleteBoardResponseDto.success();

  }
  
}