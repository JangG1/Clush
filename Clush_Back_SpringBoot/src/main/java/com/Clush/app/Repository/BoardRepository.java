package com.Clush.app.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.Clush.app.Domain.Board;

// EntityManager나 SessionFactory와 같은 기술을 사용하면 더 많은 기능을 제어 가능하고 
// 이런 스프링 데이터 JPA 인터페이스는  CRUD중점으로 간단하게 사용이 가능.
// extends JpaRepository<> 를 통해 자동으로 bean이 등록이 돼서 @Repository 생략 가능.
public interface BoardRepository extends JpaRepository<Board, Integer> {
	Optional<Board> findByBoardNo(Board board);
	 
	 void deleteByBoardNo(Integer board_no);

	 
}

