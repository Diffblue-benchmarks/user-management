package io.pockid.backend.usermanagement.service;

import io.pockid.backend.usermanagement.api.model.ApiUserModel;
import io.pockid.backend.usermanagement.persistence.model.PUserModel;

import java.time.LocalDate;

class UserModelUtils {

    static ApiUserModel map(PUserModel pUserModel) {
        ApiUserModel apiUserModel = new ApiUserModel();
        apiUserModel.setDateOfBirth(pUserModel.getDateOfBirth().toString());
        apiUserModel.setFirstName(pUserModel.getFirstName());
        apiUserModel.setLastName(pUserModel.getLastName());
        apiUserModel.setGender(pUserModel.getGender());
        apiUserModel.setUid(pUserModel.getUid());
        apiUserModel.setIsParent(Boolean.toString(pUserModel.isParent()));
        apiUserModel.setNickName(pUserModel.getNickName());
        return apiUserModel;
    }

    static PUserModel map(ApiUserModel apiUserModel) {
        PUserModel pUserModel = new PUserModel();
        pUserModel.setFirstName(apiUserModel.getFirstName());
        pUserModel.setLastName(apiUserModel.getLastName());
        pUserModel.setUid(apiUserModel.getUid());
        pUserModel.setGender(apiUserModel.getGender());
        pUserModel.setDateOfBirth(LocalDate.parse(apiUserModel.getDateOfBirth()));
        pUserModel.setParent(Boolean.parseBoolean(apiUserModel.getIsParent()));
        pUserModel.setNickName(apiUserModel.getNickName());
        return pUserModel;
    }

    static void update(PUserModel pUserModel, ApiUserModel apiUserModel) {
        pUserModel.setFirstName(apiUserModel.getFirstName());
        pUserModel.setLastName(apiUserModel.getLastName());
        pUserModel.setGender(apiUserModel.getGender());
        pUserModel.setDateOfBirth(LocalDate.parse(apiUserModel.getDateOfBirth()));
        pUserModel.setNickName(apiUserModel.getNickName());
    }
}
