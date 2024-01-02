package com.sooin.boardback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sooin.boardback.entity.ImageEntity;
import java.util.List;

import javax.transaction.Transactional;


@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {

  List<ImageEntity> findByBoardNumber(Integer boardNumber);

  @Transactional
  void deleteByBoardNumber(Integer boardNumber);
  
}
