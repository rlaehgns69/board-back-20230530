package com.dohoon.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dohoon.board.entity.SearchLogEntity;
import com.dohoon.board.entity.resultSet.SearchWordResultSet;

@Repository
public interface SearchLogRepository extends JpaRepository<SearchLogEntity, Integer> {
  @Query(
    value=
    "SELECT search_word AS searchWord, count(*) AS count " +
    "FROM search_log " +
    "GROUP BY search_word " +
    "ORDER BY count DESC " +
    "LIMIT 15",
    nativeQuery=true
  )
  List<SearchWordResultSet> getTop15SearchWord();

  @Query(
    value = 
    "SELECT search_word as searchWord, count(*) AS count "+
    "FROM search_log "+
    "WHERE search_word = ?1 "+
    "AND relation_word IS NOT NULL "+
    "GROUP BY search_word "+
    "ORDER BY count DESC "+
    "LIMIT 15",
    nativeQuery = true
  )
  List<SearchWordResultSet> getTop15RelationWord(String searchWord);
}
