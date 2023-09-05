package com.dohoon.board.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dohoon.board.dto.response.search.GetPopularListResponseDto;
import com.dohoon.board.dto.response.search.GetRelationListResponseDto;
import com.dohoon.board.service.SearchService;

import lombok.RequiredArgsConstructor;

// controller: 검색 컨트롤러 //

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchController {

  private final SearchService SearchService;

  // API : 인기 검색어 리스트 불러오기  메서드 //
  @GetMapping("/popular")
  public ResponseEntity<? super GetPopularListResponseDto> getPopularList() {
    ResponseEntity<? super GetPopularListResponseDto> response = SearchService.getPopularList();
    return response;
  }
    
  
  // API : 연관 검색어 리스트 불러오기 메서드 //
  @GetMapping("/relation/{searchWord}")
  public ResponseEntity<? super GetRelationListResponseDto> getRelationList(
    @PathVariable(value = "searchWord", required=true) String searchWord
  ){
    ResponseEntity<? super GetRelationListResponseDto> response = SearchService.getRealtionList(searchWord);
    return response;
  }
  

}
