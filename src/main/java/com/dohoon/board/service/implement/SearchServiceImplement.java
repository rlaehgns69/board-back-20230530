package com.dohoon.board.service.implement;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dohoon.board.dto.response.ResponseDto;
import com.dohoon.board.dto.response.search.GetPopularListResponseDto;
import com.dohoon.board.dto.response.search.GetRelationListResponseDto;
import com.dohoon.board.entity.resultSet.SearchWordResultSet;
import com.dohoon.board.repository.SearchLogRepository;
import com.dohoon.board.service.SearchService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchServiceImplement implements SearchService {

  private final SearchLogRepository searchLogRepository;

  @Override
  public ResponseEntity<? super GetPopularListResponseDto> getPopularList() {
    List<SearchWordResultSet> resultSets = null;
    
    try {
      
      // description: 인기 검색어 리스트 불러오기 //
      resultSets = searchLogRepository.getTop15SearchWord();

    } catch(Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetPopularListResponseDto.success(resultSets);
  }

  @Override
  public ResponseEntity<? super GetRelationListResponseDto> getRealtionList(String searchWord) {
    
    List<SearchWordResultSet> resultSets = null;

    try {

      // description: 연관 검색어 리스트 불러오기 //
      resultSets=searchLogRepository.getTop15RelationWord(searchWord);

    } catch(Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return GetRelationListResponseDto.success(resultSets);
  }
  
}
