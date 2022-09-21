package in.dudeapp.userservice.entity;

import in.dudeapp.userservice.constant.VerificationType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by mohan on 11/06/22
 */
@Entity
@Data
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private VerificationType verificationType;

    @Column
    @NotEmpty
    private String data;

    @CreationTimestamp
    @Column
    private LocalDateTime createdOn;

    @Column
    private LocalDateTime verifiedOn;
}
