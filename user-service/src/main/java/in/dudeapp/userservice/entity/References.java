package in.dudeapp.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author nandhan, Created on 23/07/22
 */
@Data
@Builder
@Entity
@Table(name="user_references")
@NoArgsConstructor
@AllArgsConstructor
public class References {

    @Id
    @GeneratedValue(generator = "dudeid")
    @GenericGenerator(name="dudeid", strategy = "in.dudeapp.common.util.IDGenerator")
    private String id;

    @NotNull
    @Column
    private String name;

    @NotNull
    @Column
    private String contact;

    @Column
    private String collegeId;

    @NotNull
    @Column
    private String userId;
}
