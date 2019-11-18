package com.zwh.website.model;

import lombok.*;

import java.io.Serializable;

/**
 * @author ZhangWeihui
 * @date 2019/11/7 18:06
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User implements Serializable {

    private static final long serialVersionUID = 8395556855044983963L;
    private String name;
    private String password;
}
