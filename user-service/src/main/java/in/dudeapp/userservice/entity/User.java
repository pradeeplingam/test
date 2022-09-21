package in.dudeapp.userservice.entity;

import in.dudeapp.common.converter.SecuredConverter;
import in.dudeapp.userservice.entity.constants.OnBoardingStage;
import in.dudeapp.userservice.entity.constants.UserType;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohan on 10/06/22
 */
@Entity
@Data
@Table(indexes = {
        @Index(name = "e_index", columnList = "email"),
        @Index(name = "m_index", columnList = "mobileNumber"),
        @Index(name = "o_index", columnList = "onBoardingStage")
})
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String motherName;

    @Column
    private String email;

    @Column
    private String profileImageUrl;

    @Column
    private String mobileNumber;

    @Column
    private Boolean mobileVerified = Boolean.FALSE;

    @Column
    private Boolean emailVerified = Boolean.FALSE;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private UserType userType;

    @Column
    @Convert(converter = SecuredConverter.class)
    private String panCardNumber;

    @Column
    private String selfieUrl;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private OnBoardingStage onBoardingStage;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Verification> verifications;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Address> addresses;

    @OneToMany(fetch = FetchType.LAZY)
    private List<UserIds> userIds;

    @OneToOne(fetch = FetchType.LAZY)
    private StudentDetails studentDetails;
}
