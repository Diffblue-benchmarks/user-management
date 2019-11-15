package io.pockid.backend.usermanagement.api.controller;

import io.pockid.backend.usermanagement.api.model.ApiUserModel;
import io.pockid.backend.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static io.pockid.backend.usermanagement.api.ApiConstants.API_V_1_USERS;
import static io.pockid.backend.usermanagement.api.ApiConstants.USER_ID_REQUEST_HEADER;

@RestController
@RequestMapping(API_V_1_USERS)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiUserModel> createOrUpdateUser(@RequestBody @Validated ApiUserModel apiUserModel,
                                                           @RequestHeader(USER_ID_REQUEST_HEADER) String uid) {
        apiUserModel.setUid(uid);
        var user = userService.createOrUpdateUser(apiUserModel);
        return ResponseEntity.ok(user);
    }

    @GetMapping("{uid}")
    public ResponseEntity<ApiUserModel> getActiveUsers(@PathVariable("uid") String uid) {
        var existingUser = userService.getActiveUser(uid);
        return ResponseEntity.ok(existingUser);
    }

    @DeleteMapping("{uid}")
    public ResponseEntity<Void> deleteUser(@PathVariable("uid") String uid) {
        userService.deleteUser(uid);
        return ResponseEntity.noContent().build();
    }
}
