package com.example.demo.VO;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author liuxinghong
 * @Description:
 * @date 2019/5/7 000710:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("添加用户")
public class USerAddVO implements Serializable {

    @ApiModelProperty("用户编号")
    @NotNull(message = "编号不能为空！")
    private String userNo;
    @ApiModelProperty("手机号码")
    private String mobileNo;
    /**
     * 登录用户名
     */
    @ApiModelProperty("用户名")
    private String loginName;

    @ApiModelProperty("姓名")
    private String name;
    /**
     * 登录密码
     */
    @ApiModelProperty("密码")
    private String pwd;
    /**
     * 微信号
     */
    @ApiModelProperty("微信号")
    private String weixinId;
    /**
     * 注册时间
     */
    @ApiModelProperty("注册时间")
    private Date regTime;

}
