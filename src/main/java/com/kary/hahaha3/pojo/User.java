package com.kary.hahaha3.pojo;

import io.swagger.v3.oas.annotations.tags.Tag;
import jdk.jfr.DataAmount;
import lombok.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author:123
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Tag(name = "User")
//   /picture/leyun.jpg
public class User implements Serializable {
    private String username;
    private String pwd;
    private int scoreTotal1v1;
    private int scoreTotalBrawl;
    private String email;
    private Date regdate;
    private int gamesCount;
    private String gamesId;
    private String avatar;
}
