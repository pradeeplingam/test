package in.dudeapp.userservice.entity;

import in.dudeapp.userservice.entity.constants.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by mohan on 10/06/22
 */
@Data
@Entity
public class Document extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private DocumentType documentType;

    @Column
    private String imageUrl;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String fullName;

    @Column
    private String fatherName;

    @Column
    private String motherName;

    @Column
    private String identificationNumber;

    @Column
    private LocalDateTime dateOfBirth;

    @Column
    private String userId;

    @Column
    private boolean extracted = false;

}
