package com.recentPageApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recent")
public class BlogRecentPageController {
	
	@Autowired
	BlogRecentPageDao  BlogRecentPageDao;
	
	
private final BlogRecentPageService BlogRecentPageService;
	
	@Autowired
	public BlogRecentPageController(BlogRecentPageService BlogRecentPageService) {
		this.BlogRecentPageService = BlogRecentPageService;
	}
	
	
	
	 private Sort.Direction getSortDirection(String direction) {
		    if (direction.equals("asc")) {
		      return Sort.Direction.ASC;
		    } else if (direction.equals("desc")) {
		      return Sort.Direction.DESC;
		    }

		    return Sort.Direction.ASC;
		  }
	
	@GetMapping
	public ResponseEntity<List<BlogRecentPage>> findAll() {
		return ResponseEntity.ok(BlogRecentPageService.findAll());
	}
	
	@GetMapping("/RecentPage")
	public ResponseEntity<Map<String, Object>> getAllPages(
			@RequestParam(required = false) String title,
		      @RequestParam(defaultValue = "0") int page,
		      @RequestParam(defaultValue = "6") int size,
		      @RequestParam(defaultValue = "blogId,desc") String[] sort) {
		try {
		      List<Order> orders = new ArrayList<Order>();

		      if (sort[0].contains(",")) {
		        
		        for (String sortOrder : sort) {
		          String[] _sort = sortOrder.split(",");
		          orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
		        }
		      } else {
		      
		        orders.add(new Order(getSortDirection(sort[1]), sort[0]));
		      }

		      List<BlogRecentPage> BlogRecentPages = new ArrayList<BlogRecentPage>();
		      Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

		      Page<BlogRecentPage> pageTuts;
		      if (title == null)
		        pageTuts = BlogRecentPageDao.findAll(pagingSort);
		      else
		        pageTuts = BlogRecentPageDao.findAll(pagingSort);

		      BlogRecentPages = pageTuts.getContent();

		      if (BlogRecentPages.isEmpty()) {
		        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		      }

		
		Map<String, Object> response = new HashMap<>();
		response.put("RecentPages",BlogRecentPages);
		response.put("currentPage", pageTuts.getNumber());
		response.put("totalBlogs", pageTuts.getTotalElements());
	    response.put("totalPages", pageTuts.getTotalPages());
	    return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
	
	    @GetMapping("/{blogId}")
		public ResponseEntity <BlogRecentPage> findById(@RequestBody @PathVariable Long blogId){
		Optional<BlogRecentPage> blogRecentPage = BlogRecentPageService.findById(blogId);
		return ResponseEntity.ok(BlogRecentPageService.findById(blogId).get());
			 
		}
	    
	    
	    @PostMapping("/create")
		 public ResponseEntity <BlogRecentPage> create (@RequestBody BlogRecentPage blogRecentPage){
			 return ResponseEntity.status(HttpStatus.CREATED).body(BlogRecentPageService.save(blogRecentPage));
		}
	    
	    
	    
	    @PutMapping("/{blogId}")
		public ResponseEntity <BlogRecentPage> update (@PathVariable Long blogId, @RequestBody BlogRecentPage blogRecentPage){
			return ResponseEntity.accepted().body(BlogRecentPageService.save(blogRecentPage));
		}
	    

		@DeleteMapping("/{blogId}")
		public ResponseEntity delete(@PathVariable Long blogId) {
			BlogRecentPageService.deleteById(blogId);
			return ResponseEntity.accepted().build();
			
		}
}
