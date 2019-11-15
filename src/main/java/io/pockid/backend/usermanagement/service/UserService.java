package io.pockid.backend.usermanagement.service;

import io.pockid.backend.usermanagement.api.model.ApiUserModel;
import io.pockid.backend.usermanagement.exception.EventNotPublishedException;
import io.pockid.backend.usermanagement.exception.IllegalUserUpdateException;
import io.pockid.backend.usermanagement.exception.UserNotFoundException;
import io.pockid.backend.usermanagement.persistence.model.PUserModel;
import io.pockid.backend.usermanagement.persistence.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final UserEventService userEventService;

    @Autowired
    public UserService(UserRepository userRepository, UserEventService userEventService) {
        this.userRepository = userRepository;
        this.userEventService = userEventService;
    }

    public ApiUserModel getActiveUser(String uid) {
        var pUserModel = getActiveUserFromUserRepository(uid);
        return UserModelUtils.map(pUserModel);
    }

    @Transactional
    public ApiUserModel createOrUpdateUser(ApiUserModel apiUserModel) {
        var existingUserOptional = userRepository.findByUid(apiUserModel.getUid());
        if (existingUserOptional.isPresent()) {
            var existingUser = existingUserOptional.get();
            if (!existingUser.isActive()) {
                throw new IllegalUserUpdateException("User with uid: " + apiUserModel.getUid() +
                        " cannot be updated in de-activated state");
            }
            return handleUpdate(apiUserModel, existingUser);
        }
        var pUserModel = UserModelUtils.map(apiUserModel);
        var savedUser = userRepository.saveAndFlush(pUserModel);
        if (!userEventService.userCreated(savedUser.getUid())) {
            throw new EventNotPublishedException("Unknown error");
        }
        return UserModelUtils.map(savedUser);
    }

    @Transactional
    public void deleteUser(String uid) {
        var pUserModel = getActiveUserFromUserRepository(uid);
        pUserModel.setActive(false);
        var deletedUser = userRepository.save(pUserModel);
        if (!userEventService.userDeleted(deletedUser.getUid())) {
            throw new EventNotPublishedException("Unknown error");
        }
    }

    private ApiUserModel handleUpdate(ApiUserModel apiUserModel, PUserModel existingUser) {
        if (!hasChanges(existingUser, apiUserModel)) {
            return apiUserModel;
        } else {
            updateExistingUser(existingUser, apiUserModel);
            var updatedUser = userRepository.saveAndFlush(existingUser);
            if (!userEventService.userUpdated(updatedUser.getUid())) {
                throw new EventNotPublishedException("Unknown error");
            }
            return UserModelUtils.map(updatedUser);
        }
    }

    private boolean hasChanges(PUserModel existingUser, ApiUserModel apiUserModel) {
        return !(UserModelUtils.map(existingUser).hashCode() == apiUserModel.hashCode());
    }

    private void updateExistingUser(PUserModel pUserModel, ApiUserModel apiUserModel) {
        UserModelUtils.update(pUserModel, apiUserModel);
    }

    private PUserModel getActiveUserFromUserRepository(String uid) {
        return Optional.of(userRepository
                .findByUid(uid))
                .get()
                .filter(PUserModel::isActive)
                .orElseThrow(
                        () -> new UserNotFoundException("No active accounts found for user with id " + uid)
                );
    }
}
