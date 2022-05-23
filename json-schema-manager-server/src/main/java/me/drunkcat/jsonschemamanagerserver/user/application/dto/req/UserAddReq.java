package me.drunkcat.jsonschemamanagerserver.user.application.dto.req;

import lombok.Data;
import me.drunkcat.jsonschemamanagerserver.user.domain.entity.User;

@Data
public class UserAddReq {
    private String username;
    private String password;

    public User toEntity() {
        return User.of(this.username, this.password);
    }
}
