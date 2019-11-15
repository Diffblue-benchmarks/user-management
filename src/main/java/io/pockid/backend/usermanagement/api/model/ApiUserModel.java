package io.pockid.backend.usermanagement.api.model;

import io.pockid.backend.usermanagement.api.validations.DateValidator;
import io.pockid.backend.usermanagement.api.validations.EnumValidator;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ApiUserModel {

    private String uid;

    @NotNull(message = "firstName is required")
    private String firstName;

    @NotNull(message = "lastName is required")
    private String lastName;

    private String nickName;

    @DateValidator(message = "Invalid dateOfBirth")
    @NotNull(message = "dateOfBirth is required")
    private String dateOfBirth;

    @NotNull(message = "gender is required")
    @EnumValidator(enumClazz = Gender.class, message = "Invalid gender")
    private String gender;

    private String isParent = "false";
}
