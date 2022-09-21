package in.dudeapp.userservice.entity;

import in.dudeapp.userservice.entity.constants.AddressType;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by mohan on 10/06/22
 */
@Data
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String street;

    @Column
    private String pinCode;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private String district;

    @Enumerated(EnumType.ORDINAL)
    private AddressType addressType;
}
