package in.dudeapp.userservice.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by mohan on 22/06/22
 */
@Entity
@Data
public class UserIds extends BaseEntity {
    @Id
    private Long id;

    private String google;

    private String firebase;
}
