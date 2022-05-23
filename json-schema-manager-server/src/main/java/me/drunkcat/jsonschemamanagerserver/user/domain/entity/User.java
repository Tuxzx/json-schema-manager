package me.drunkcat.jsonschemamanagerserver.user.domain.entity;

import com.google.common.hash.Hashing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.drunkcat.jsonschemamanagerserver.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.nio.charset.StandardCharsets;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "t_user",
        indexes = @Index(name = "username_unique_index", columnList = "username")
)
public class User extends BaseEntity {

    @Column(unique = true)
    private String username;

    private String password;

    public static User of(String username, String password) {
        return User.of(
                username,
                Hashing.murmur3_128()
                        .hashBytes(password.getBytes(StandardCharsets.UTF_8))
                        .toString()
        );
    }

}
