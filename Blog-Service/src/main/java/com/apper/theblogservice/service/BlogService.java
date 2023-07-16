package com.apper.theblogservice.service;

import com.apper.theblogservice.model.Blog;
import com.apper.theblogservice.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Blog createBlog(String title, String body, String bloggerId) {
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setBody(body);
        blog.setBloggerId(bloggerId);

        return blogRepository.save(blog);
    }

    public Blog getBlog(String id) {
        Optional<Blog> blog = blogRepository.findById(id);
        return blog.get();
    }

    public Iterable<Blog> getAllBlogs() {
        Iterable<Blog> blogResult = blogRepository.findAll();
        return blogResult;
    }

    public Blog updateBlog(String title, String body, String id) {
        Optional<Blog> blog = blogRepository.findById(id);
        blog.get().setTitle(title);
        blog.get().setBody(body);
        blogRepository.save(blog.get());
        return blog.get();
    }

    public Iterable<Blog> getAllUserBlogs(String bloggerId) {
        Iterable<Blog> blogResult = blogRepository.findByBloggerId(bloggerId);
        return blogResult;
    }
}
