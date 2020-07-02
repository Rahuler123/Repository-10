package com.recentPageApi;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;

@Service
public class BlogRecentPageService {
	
	private final BlogRecentPageDao BlogRecentPageDao;
	
	
	@Autowired
	public BlogRecentPageService (BlogRecentPageDao BlogRecentPageDao ) {
		this.BlogRecentPageDao = BlogRecentPageDao;
		
	}
	public List<BlogRecentPage> findAll(){
		return BlogRecentPageDao.findAll();
	}
	
	public Optional <BlogRecentPage> findById(Long blogId){
		return BlogRecentPageDao.findById(blogId);
		
	}
	 
	public BlogRecentPage save (BlogRecentPage RecentPg) {
		return BlogRecentPageDao.save(RecentPg);
	}
	
	
	public void deleteById(Long blogId) {
		BlogRecentPageDao.deleteById(blogId);
		
	}
	

}
