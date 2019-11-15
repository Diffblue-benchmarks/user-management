package io.pockid.backend.usermanagement.persistence.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "auth_user")
public class PUserModel extends BaseModel {

    @Column(name = "uid", unique = true)
    private String uid;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @Column(name = "is_parent", nullable = false)
    private boolean isParent = false;
}
