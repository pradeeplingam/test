package in.dudeapp.userservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by mohan on 20/07/22
 */
@Data
@Entity
public class StudentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate graduationDate;

    @Column
    private String stay;

    @Column
    private String parentsOccupation;


}
