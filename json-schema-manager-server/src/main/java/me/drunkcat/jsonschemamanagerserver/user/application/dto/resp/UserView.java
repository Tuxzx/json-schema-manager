package me.drunkcat.jsonschemamanagerserver.user.application.dto.resp;

import lombok.Data;
import me.drunkcat.jsonschemamanagerserver.user.domain.entity.User;

@Data(staticConstructor = "of")
public class UserView {
    private final long id;
    private final String username;

    public static UserView toView(User user) {
        return UserView.of(user.getId(), user.getUsername());
    }
}
