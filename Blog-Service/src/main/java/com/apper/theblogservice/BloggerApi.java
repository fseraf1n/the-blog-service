package com.apper.theblogservice;

import com.apper.theblogservice.exceptions.CustomErrorResponse;
import com.apper.theblogservice.model.Blogger;
import com.apper.theblogservice.payload.BloggerDetails;
import com.apper.theblogservice.service.BloggerService;
import com.apper.theblogservice.payload.CreateBloggerRequest;
import com.apper.theblogservice.payload.CreateBloggerResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("blogger")
public class BloggerApi {
    private final BloggerService bloggerService;

    public BloggerApi(BloggerService bloggerService) {
        this.bloggerService = bloggerService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateBloggerResponse createBlogger(@RequestBody @Valid CreateBloggerRequest request) throws NullPointerException {
        //System.out.println(request);
        try {
            Blogger createdBlogger = bloggerService.createBlogger(request.getEmail(), request.getName(), request.getPassword());
            //
            CreateBloggerResponse response = new CreateBloggerResponse();
            response.setId(createdBlogger.getId());
            response.setDateRegistration(createdBlogger.getCreatedAt());
            return response;
        } catch (NullPointerException e) {
            throw new NullPointerException("This email is already registered.");
//
        }
    }

    @GetMapping("{id}")
    public BloggerDetails getBlogger(@PathVariable String id) throws NoSuchElementException {
        try {
            Blogger blogger = bloggerService.getBlogger(id);

            BloggerDetails bloggerDetails = new BloggerDetails();
            bloggerDetails.setId(blogger.getId());
            bloggerDetails.setName(blogger.getName());
            bloggerDetails.setEmail(blogger.getEmail());
            bloggerDetails.setDateRegistration(blogger.getCreatedAt());

            return bloggerDetails;
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("The account requested was not found.");
        }

    }

    @GetMapping
    public ResponseEntity<Object> getListOfBloggers() {
        Iterable<Blogger> allBloggers = bloggerService.getAllBloggers();
        ArrayList<BloggerDetails> allBloggerDetails = new ArrayList<>();
        for (Blogger blogger:allBloggers) {
            BloggerDetails bloggerDetails = new BloggerDetails();
            bloggerDetails.setId(blogger.getId());
            bloggerDetails.setName(blogger.getName());
            bloggerDetails.setEmail(blogger.getEmail());
            bloggerDetails.setDateRegistration(blogger.getCreatedAt());
            allBloggerDetails.add(bloggerDetails);
        }
        if (allBloggerDetails.isEmpty()) {
            CustomErrorResponse customMessage = new CustomErrorResponse();
            customMessage.setMessage("No blog accounts were found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customMessage);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(allBloggerDetails);
        }
    }

    //

    @GetMapping("mail/{email}")
    public List<BloggerDetails> getBloggerByEmail(@PathVariable String email) {
        List<Blogger> allBloggers = bloggerService.getByEmail(email);

        ArrayList<BloggerDetails> allBloggerDetails = new ArrayList<>();
        for (Blogger blogger:allBloggers) {
            BloggerDetails bloggerDetails = new BloggerDetails();
            bloggerDetails.setId(blogger.getId());
            bloggerDetails.setName(blogger.getName());
            bloggerDetails.setEmail(blogger.getEmail());
            bloggerDetails.setDateRegistration(blogger.getCreatedAt());

            allBloggerDetails.add(bloggerDetails);
        }

        return allBloggerDetails;
    }


}
