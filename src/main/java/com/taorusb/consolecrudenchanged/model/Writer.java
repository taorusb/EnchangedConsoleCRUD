package com.taorusb.consolecrudenchanged.model;

import java.util.LinkedList;
import java.util.List;

public class Writer extends BaseModel{

    private String firstName;
    private String lastName;
    private List<Post> posts = new LinkedList<>();
    private Region region;
    private Role role;

    public Writer() {
        super(0L);
    }

    public Writer(Long id) {
        super(id);
    }

    public Writer(String firstName, String lastName) {
        super(0L);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Writer(String firstName, String lastName, long regId) {
        super(0L);
        this.region = new Region(regId);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Writer(Long id, String firstName, String lastName) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public int getPostsCount() {
        return posts.size();
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Writer writer = (Writer) o;
        return super.getId() == writer.getId();
    }

    @Override
    public String toString() {
        return "Writer{" +
                "id='" + getId() + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", region=" + region.getId() +
                ", role=" + role.name() +
                '}';
    }
}
