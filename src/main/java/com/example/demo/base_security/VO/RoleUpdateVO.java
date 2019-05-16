package com.example.demo.base_security.VO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author liuxinghong
 * @Description:
 * @date 2019/5/15 001518:25
 */
@Data
public class RoleUpdateVO implements Serializable {
    @NotNull()
    private String id;
    private String name;
    private String code;
    private Integer sortNum;
    private String des;
    private List<String> menuIds;
}
