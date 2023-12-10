package org.ddiachenko.post;

public class CommentDto {
    public int postId;
    public int id;
    public String name;
    public String email;
    public String body;

    public CommentDto(PostDto postDtoId, int id, String name, String email, String body) {
        this.postId = postDtoId.getId();
        this.id = id;
        this.name = name;
        this.email = email;
        this.body = body;
    }
}
