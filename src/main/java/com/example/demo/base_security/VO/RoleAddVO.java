package com.example.demo.base_security.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liugh
 * @since 2018-06-30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleAddVO implements Serializable {
    private String roleName;
    private String roleCode;
    private List<String> menuIds;

}
