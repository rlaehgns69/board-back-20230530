package com.dohoon.board.service;

import org.springframework.http.ResponseEntity;

import com.dohoon.board.dto.response.search.GetPopularListResponseDto;
import com.dohoon.board.dto.response.search.GetRelationListResponseDto;

public interface SearchService {
  // method: 인기 검색어 리스트 불러오기 메서드 //
  ResponseEntity<? super GetPopularListResponseDto> getPopularList();
  // method: 연관 검색어 리스트 불러오기 메서드 //
  ResponseEntity<? super GetRelationListResponseDto> getRealtionList(String searchWord); 
}
