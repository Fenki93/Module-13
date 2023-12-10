package org.ddiachenko.post;

import lombok.Getter;
import org.ddiachenko.user.UserDto;

public class PostDto {
    public int userId;
    @Getter
    public int id;
    public String title;
    public String body;

    public PostDto(UserDto userId, int id, String title, String body) {
        this.userId = userId.getId();
        this.id = id;
        this.title = title;
        this.body = body;
    }

}
