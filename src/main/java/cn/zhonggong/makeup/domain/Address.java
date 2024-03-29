package cn.zhonggong.makeup.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 军辉
 * 2019-07-10 21:27
 */
@Data
@Entity(name = "tb_address")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;
    private String address;
    private String phone;
    private String temp;


    public Address(Integer userId, String address, String phone) {

        this.userId = userId;
        this.address = address;
        this.phone = phone;
    }
}
