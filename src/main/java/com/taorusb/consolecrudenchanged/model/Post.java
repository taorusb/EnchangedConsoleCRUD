package com.taorusb.consolecrudenchanged.model;


public class Post extends BaseModel {

    private String content;
    private String created;
    private String updated;

    public Post() {
        super(0L);
    }

    public Post(Long id) {
        super(id);
    }

    public Post(String content) {
        super(0L);
        this.content = content;
    }

    public Post(Long id, String content) {
        super(id);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return super.getId() == post.getId();
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + super.getId() +
                ", content='" + content + '\'' +
                ", created='" + created + '\'' +
                ", updated='" + updated + '\'' +
                '}';
    }
}
