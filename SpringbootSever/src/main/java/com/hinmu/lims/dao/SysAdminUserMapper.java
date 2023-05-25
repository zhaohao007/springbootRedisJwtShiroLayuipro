package com.hinmu.lims.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hinmu.lims.model.entity.SysAdminUserEntity;
import com.hinmu.lims.model.reqbean.EditeSysAdminUserReqValid;

/**
 * <p>
 * 后台用户表 Mapper 接口
 * </p>
 *
 * @author zhaohao
 * @since 2019-08-28
 */
public interface SysAdminUserMapper extends BaseMapper<SysAdminUserEntity> {
    EditeSysAdminUserReqValid find(Integer userId);
    SysAdminUserEntity findto(Integer userId);
}
