package com.hinmu.lims.model.reqbean;

import com.hinmu.lims.model.enums.DisableStatusEnum;
import com.hinmu.lims.model.enums.SexEnum;
import com.hinmu.lims.util.validator.EnumValid;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class EditeSysAdminUserReqValid {
    private Integer id;


    /**
     * 合作商
     */
    private Integer companyId;

    /**
     * 案场
     */
    private Integer salesCaseId;

    /**
     * 账户
     */
    @NotEmpty(message = "账户不能为空")
    private String account;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空")
    private String password;

    /**
     * email
     */
    private String email;

    /**
     * nickname
     */
    private String nickName;

    /**
     * 1男 0女
     */
    @EnumValid(target = SexEnum.class, message = "性别填写有误")
    private String sex;

    /**
     * 姓名
     */
    private String name;


    /**
     * 描述
     */
    private String description;

    /**
     * 可用 不可用
     */
    @EnumValid(target = DisableStatusEnum.class, message = "状态未填写")
    private String status;
    /**
     * 角色集合
     */
    private List<Integer> roleListId;
    /**
     * 头像
     */
    private String portrait;


    /**
     * 合作商
     */
    private String companyNanem;

    /**
     * 案场
     */
    private String salesCaseName;
}
