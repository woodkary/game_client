package com.kary.hahaha3.pojo;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;

import java.util.Date;

/**
 * @author:123
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Tag(name = "Record")
public class Record {
    private int gameId;
    private String username;
    private int kill;
    private int death;
    private int assist;
}
