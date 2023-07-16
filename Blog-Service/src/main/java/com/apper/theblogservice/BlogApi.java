package com.apper.theblogservice;

import com.apper.theblogservice.exceptions.CustomErrorResponse;
import com.apper.theblogservice.model.Blog;
import com.apper.theblogservice.model.Blogger;
import com.apper.theblogservice.payload.*;
import com.apper.theblogservice.service.BlogService;
import jakarta.validation.Valid;
import org.hibernate.sql.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("blog")
public class BlogApi {
    private final BlogService blogService;

    public BlogApi(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateBlogResponse createBlog(@RequestBody @Valid CreateBlogRequest request) {
        Blog createdBlog = blogService.createBlog(request.getTitle(), request.getBody(), request.getBloggerId());
        CreateBlogResponse response = new CreateBlogResponse();
        response.setId(createdBlog.getId());
        response.setBloggerId(createdBlog.getBloggerId());
        response.setCreatedAt(createdBlog.getCreatedAt());
        response.setLastUpdated(createdBlog.getLastUpdate());
        System.out.println("request: " + request);
        System.out.println("response: " + response);
        return response;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBlog(@RequestBody @Valid UpdateBlogRequest request, @PathVariable String id) throws NoSuchElementException {
       try {
           blogService.updateBlog(request.getTitle(), request.getBody(), id);
       } catch(NoSuchElementException e) {
           throw new NoSuchElementException("Blog post not found.");
       }
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public BlogDetails getBlog(@PathVariable String id) throws NoSuchElementException {
        try {
            Blog blog = blogService.getBlog(id);
            BlogDetails blogDetails = new BlogDetails();
            blogDetails.setBloggerId(blog.getBloggerId());
            blogDetails.setTitle(blog.getTitle());
            blogDetails.setBody(blog.getBody());
            blogDetails.setCreatedAt(blog.getCreatedAt());
            blogDetails.setLastUpdated(blog.getLastUpdate());
            return blogDetails;
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Blog post not found.");
        }
    }

    @GetMapping
    public ResponseEntity<Object> getListOfBlogs() {
        Iterable<Blog> allBlogs = blogService.getAllBlogs();
        ArrayList<BlogDetails> allBlogDetails = new ArrayList<>();
        for (Blog blog:allBlogs) {
            BlogDetails blogDetails = new BlogDetails();
            blogDetails.setBloggerId(blog.getBloggerId());
            blogDetails.setTitle(blog.getTitle());
            blogDetails.setBody(blog.getBody());
            blogDetails.setCreatedAt(blog.getCreatedAt());
            blogDetails.setLastUpdated(blog.getLastUpdate());

            allBlogDetails.add(blogDetails);
        }
        if (allBlogDetails.isEmpty()) {
            CustomErrorResponse customMessage = new CustomErrorResponse();
            customMessage.setMessage("No blog posts were found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customMessage);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(allBlogDetails);
        }
    }

    @GetMapping("/blogger/{bloggerId}")
    public ResponseEntity<Object> getAllUserBlogs(@PathVariable String bloggerId) {
        Iterable<Blog> allBlogs = blogService.getAllUserBlogs(bloggerId);
        ArrayList<BlogDetails> allBlogDetails = new ArrayList<>();
        for (Blog blog:allBlogs) {
            BlogDetails blogDetails = new BlogDetails();
            blogDetails.setBloggerId(blog.getBloggerId());
            blogDetails.setTitle(blog.getTitle());
            blogDetails.setBody(blog.getBody());
            blogDetails.setCreatedAt(blog.getCreatedAt());
            blogDetails.setLastUpdated(blog.getLastUpdate());

            allBlogDetails.add(blogDetails);
        }
        if (allBlogDetails.isEmpty()) {
            CustomErrorResponse customMessage = new CustomErrorResponse();
            customMessage.setMessage("No blog posts by the requested user were found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customMessage);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(allBlogDetails);
        }
    }
}
