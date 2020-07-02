package com.recentPageApi;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BlogRecentPageDao extends JpaRepository<BlogRecentPage,Long>{

	Page<BlogRecentPage> findByTitle(String title, Pageable pageable);
	


}
