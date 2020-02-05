package cn.chen.POJO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


/**
 * @author haixin
 * @time 2020/1/10
 */
@Builder
public class UserDTO {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private int age;
}
