package com.dohoon.board.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
  
  // method: 파일 업로드 메서드 //
  String upload(MultipartFile file);

  // method: 이미지 불러오기 메서드 //
  Resource getFile(String fileName);
}

