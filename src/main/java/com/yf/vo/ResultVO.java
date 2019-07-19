package com.yf.vo;/*
 *
 *
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//无参构造
@NoArgsConstructor
//有参构造
@AllArgsConstructor
public class ResultVO {
    private Integer code;

    private String msg;

    private Object data;
}
