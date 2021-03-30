package com.taorusb.consolecrudenchanged.controller;

import com.taorusb.consolecrudenchanged.model.Post;
import com.taorusb.consolecrudenchanged.model.Writer;
import com.taorusb.consolecrudenchanged.repository.PostRepository;
import com.taorusb.consolecrudenchanged.repository.WriterRepository;

import java.util.List;
import java.util.NoSuchElementException;

import static com.taorusb.consolecrudenchanged.controller.Validator.*;

public class PostController {

    private PostRepository postRepository;
    private WriterRepository writerRepository;

    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void setWriterRepository(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    public String showByWriterId(long id, List<Post> postList) {

        try {
            postList.addAll(writerRepository.getById(id).getPosts());
        } catch (NoSuchElementException e) {
            return elementNotFoundError;
        }
        return allRight;
    }

    public String addNewPost(long writerId, String content, List<Post> postList) {

        try {
            Writer writer = writerRepository.getById(writerId);
            writer.addPost(postRepository.save(new Post(content)));
            writerRepository.save();
            postList.addAll(writer.getPosts());
        } catch (NoSuchElementException e) {
            return elementNotFoundError;
        }
        return successful;
    }

    public String updatePost(long id, String content, Post post) {

        Post temp;
        try {
            temp = postRepository.update(new Post(id, content));
            post.setId(temp.getId());
            post.setUpdated(temp.getUpdated());
            post.setCreated(temp.getCreated());
            post.setContent(temp.getContent());
        } catch (NoSuchElementException e) {
            return elementNotFoundError;
        }
        return successful;
    }

    public String deletePost(long id) {
        try {
            postRepository.deleteById(id);
        } catch (NoSuchElementException e) {
            return elementNotFoundError;
        }
        return successful;
    }
}