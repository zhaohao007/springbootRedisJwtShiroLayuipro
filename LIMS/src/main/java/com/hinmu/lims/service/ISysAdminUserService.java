package com.hinmu.lims.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hinmu.lims.model.entity.SysAdminUserEntity;
import com.hinmu.lims.model.reqbean.BatchDelReqValid;
import com.hinmu.lims.model.reqbean.EditeSysAdminUserReqValid;
import com.hinmu.lims.model.reqbean.GrantSysAdminUserReqValid;
import com.hinmu.lims.model.reqbean.LoginReqValid;
import com.hinmu.lims.model.respbean.PageResult;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author zhaohao
 * @since 2019-08-28
 */
public interface ISysAdminUserService extends IService<SysAdminUserEntity> {
    SysAdminUserEntity findSysAdminUserEntityByAccount(String account);
    SysAdminUserEntity findLoginByAccountPassword(LoginReqValid loginReq);
    PageResult selectSysAdminUserListPage(Map map);
    List<SysAdminUserEntity> pageResult(Integer userid);
    EditeSysAdminUserReqValid map(Integer userId);
    SysAdminUserEntity mapto(Integer userId);
    Map selectMapListByUserId(Integer userId);
    void updateAdminUser(EditeSysAdminUserReqValid req, Integer sessionAdminUserId);
    Boolean saveAdminUser(EditeSysAdminUserReqValid req, Integer sessionAdminUserId);
    void saveGrantRole(GrantSysAdminUserReqValid req, Integer sessionAdminUserId);
    void deleteAdminUser(Integer userId);
    void deleteBatchAdminUser(BatchDelReqValid req);
}
