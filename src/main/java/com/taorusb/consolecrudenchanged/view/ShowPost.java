package com.taorusb.consolecrudenchanged.view;

import com.taorusb.consolecrudenchanged.controller.PostController;
import com.taorusb.consolecrudenchanged.model.Post;

import java.util.LinkedList;
import java.util.List;

import static com.taorusb.consolecrudenchanged.controller.Validator.*;
import static java.lang.Long.parseLong;

public class ShowPost {

    private PostController postController;
    private static final String[] template = {"%-8s%-26.20s%-12s%-12s%n", "id", "content", "created", "updated"};
    private List<Post> container = new LinkedList<>();

    public ShowPost(PostController postController) {
        this.postController = postController;
    }

    public void showByWriterId(String id) {

        String result;
        if (!checkId(id)) {
            System.out.println(idError);
            return;
        }

        result = postController.showByWriterId(parseLong(id), container);

        if (result.equals(elementNotFoundError)) {
            System.out.println(elementNotFoundError);
            return;
        }
        printPosts();
    }

    public void addPost(String writerId, String content) {

        String result;
        if (!checkId(writerId)) {
            System.out.println(idError);
            return;
        }

        result = postController.addNewPost(parseLong(writerId), content, container);

        if (result.equals(elementNotFoundError)) {
            System.out.println(elementNotFoundError);
            return;
        }
        printPosts();
    }

    public void updatePost(String id, String content) {

        String result;
        Post post = new Post();

        if (!checkId(id)) {
            System.out.println(idError);
            return;
        }

        result = postController.updatePost(parseLong(id), content, post);

        if (result.equals(elementNotFoundError)) {
            System.out.println(elementNotFoundError);
            return;
        }

        System.out.printf(template[0], template[1], template[2], template[3], template[4]);
        System.out.printf(template[0], post.getId(), post.getContent(), post.getCreated(), post.getUpdated());
        System.out.print("\n");
    }

    public void deletePost(String id) {

        if (!checkId(id)) {
            System.out.println(idError);
            return;
        }
        System.out.println(postController.deletePost(parseLong(id)));
    }

    private void printPosts() {
        System.out.printf(template[0], template[1], template[2], template[3], template[4]);
        container.forEach(post -> System.out.printf
                (template[0], post.getId(), post.getContent(), post.getCreated(), post.getUpdated()));
        System.out.print("\n");
        container.clear();
    }
}