package cn.com.taiji.domain;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ResultDTO{
    private Integer code;
    private String message;
    private Object data;
}