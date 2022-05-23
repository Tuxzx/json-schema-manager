package me.drunkcat.jsonschemamanagerserver.user.application.res;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.drunkcat.jsonschemamanagerserver.user.application.dto.req.UserAddReq;
import me.drunkcat.jsonschemamanagerserver.user.application.dto.req.UserUpdateReq;
import me.drunkcat.jsonschemamanagerserver.user.application.dto.resp.UserView;
import me.drunkcat.jsonschemamanagerserver.user.domain.entity.User;
import me.drunkcat.jsonschemamanagerserver.user.domain.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

/**
 * 用户
 * - 注册
 * - 登录
 */
@Tag(name = "User")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserResource {

    private final UserRepository userRepository;

    @Operation(summary = "Get user by ID", operationId = "findUserById")
    @GetMapping("/{id}")
    public ResponseEntity<UserView> user(
            @PathVariable long id
    ) {
        return userRepository.findById(id)
                .map(UserView::toView)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get user by username", operationId = "findUserByUsername")
    @GetMapping("/{username}")
    public ResponseEntity<UserView> user(
            @PathVariable String username
    ) {
        return userRepository.findByUsername(username)
                .map(UserView::toView)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @Operation(summary = "Create user", operationId = "createUser")
    @PostMapping
    public ResponseEntity<UserView> userCreate(
            @RequestBody UserAddReq req
    ) {
        userRepository.findByUsername(req.getUsername())
                .ifPresent((user) -> {
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT,
                            String.format("用户 [%s] 已存在", user.getUsername()));
                });
        User save = userRepository.save(req.toEntity());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserView.toView(save));
    }

    @Operation(summary = "Update user", operationId = "updateUser")
    @PutMapping("/{id}")
    public ResponseEntity<UserView> userUpdate(
            @PathVariable long id,
            @RequestBody UserUpdateReq req
    ) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User NOT exist"));

        // update
        user.setUsername(req.getUsername());
        user.setPassword(req.getPassword());

        // persist
        User save = userRepository.save(user);

        return ResponseEntity.ok(UserView.toView(save));
    }

    @Operation(summary = "Delete user", operationId = "deleteUser")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> userDel(
            @PathVariable long id
    ) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
