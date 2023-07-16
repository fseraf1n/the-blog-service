package com.apper.theblogservice.service;

import com.apper.theblogservice.model.Blogger;
import com.apper.theblogservice.repository.BloggerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BloggerService {
    private final BloggerRepository bloggerRepository;

    public BloggerService(BloggerRepository BloggerRepository) {
        this.bloggerRepository = BloggerRepository;
    }
    public Blogger createBlogger(String email, String name, String password) {
        if (bloggerRepository.findByEmail(email).isEmpty()) {
            Blogger blogger = new Blogger();
            blogger.setEmail(email);
            blogger.setName(name);
            blogger.setPassword(password);

            return bloggerRepository.save(blogger);
        } else {
            return null;
        }
    }
    public Blogger getBlogger(String id) {
        Optional<Blogger> bloggerResult = bloggerRepository.findById(id);

        return bloggerResult.get();
    }

    public Iterable<Blogger> getAllBloggers() {
        Iterable<Blogger> bloggerResult = bloggerRepository.findAll();
        return bloggerResult;
    }

    public List<Blogger> getByEmail(String email) {
        List<Blogger> bloggerResult = bloggerRepository.findByEmail(email);
        System.out.println(bloggerResult);
        return bloggerResult;
    }
}
