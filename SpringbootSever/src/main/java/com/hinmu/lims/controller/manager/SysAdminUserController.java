package com.hinmu.lims.controller.manager;


import com.hinmu.lims.config.aoplog.MethodLog;
import com.hinmu.lims.controller.BaseController;
import com.hinmu.lims.model.entity.SysAdminUserEntity;
import com.hinmu.lims.model.enums.DisableStatusEnum;
import com.hinmu.lims.model.enums.InnerTypeEnum;
import com.hinmu.lims.model.reqbean.BatchDelReqValid;
import com.hinmu.lims.model.reqbean.EditeSysAdminUserReqValid;
import com.hinmu.lims.model.reqbean.GrantSysAdminUserReqValid;
import com.hinmu.lims.model.respbean.PageResult;
import com.hinmu.lims.model.respbean.RespResultMessage;
import com.hinmu.lims.service.ISysAdminUserService;
import com.hinmu.lims.service.ISysRoleService;
import com.hinmu.lims.shiro.annotation.LoginUser;
import com.hinmu.lims.shiro.annotation.LoginUserBean;
import com.hinmu.lims.util.common.GeneratePass;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台用户表 前端控制器
 * </p>
 *
 * @author zhaohao
 * @since 2019-08-28
 */
@RestController
@RequestMapping("/api/admin/sysuser")
public class SysAdminUserController extends BaseController {
    @Autowired
    private ISysAdminUserService sysAdminUserServicee;
    @Autowired
    private ISysRoleService sysRoleService;



    /**
     * 管理员查询
     *
     * @return
     */
    @RequiresPermissions(value = {"admin:sys:user:list"}, logical = Logical.AND)
    @PostMapping(value = "/list")
    @MethodLog(remarks = "查询账户列表")
    public RespResultMessage list(@RequestParam Map map) {
        PageResult pageResult = sysAdminUserServicee.selectSysAdminUserListPage(map);
        return this.renderPageSuccess(pageResult);
    }

    @GetMapping(value = "/listTable")
    @MethodLog(remarks = "查询账户列表")
    public RespResultMessage listTable(@RequestParam Map map) {
        PageResult pageResult = sysAdminUserServicee.selectSysAdminUserListPage(map);
        return this.renderPageSuccess(pageResult);
    }

    @GetMapping(value = "/innerType")
    @MethodLog(remarks = "查询账户类型")
    public RespResultMessage listinnerType() {
        RespResultMessage respResultMessage=new RespResultMessage();
        List<Map> stateList = new ArrayList<>();
        for (InnerTypeEnum type : InnerTypeEnum.values()) {
            Map map = new HashMap();
            map.put("key", type);
            map.put("desc", type.getDesc());
            stateList.add(map);
        }
        return respResultMessage.ok(stateList);
    }

    @GetMapping(value = "/status")
    @MethodLog(remarks = "查询账状态")
    public RespResultMessage liststatus() {
        RespResultMessage respResultMessage=new RespResultMessage();
        List<Map> stateList = new ArrayList<>();
        for (DisableStatusEnum type : DisableStatusEnum.values()) {
            Map map = new HashMap();
            map.put("key", type);
            map.put("desc", type.getDesc());
            stateList.add(map);
        }
        return respResultMessage.ok(stateList);
    }
    @RequiresPermissions(value={"admin:sys:user:edite"},logical= Logical.AND)
    @GetMapping(value = "/get/{userid}")
    @MethodLog(remarks = "进入账户编辑页面")
    public RespResultMessage toEdite(@LoginUser LoginUserBean loginUser, @PathVariable("userid")Integer userid) {
        RespResultMessage respResultMessage=new RespResultMessage();
        List<SysAdminUserEntity> pageResult= sysAdminUserServicee.pageResult(userid);
        return respResultMessage.ok(pageResult);
    }

    @PostMapping(value = "/edite")
    @MethodLog(remarks = "编辑账户信息")
    public RespResultMessage edite(@LoginUser LoginUserBean loginUser, @Validated @RequestBody EditeSysAdminUserReqValid req) {
        sysAdminUserServicee.updateAdminUser(req,loginUser.getUserId());
        return renderSuccess(true);
    }
    @PostMapping(value = "/uppassword")
    @MethodLog(remarks = "修改密码")
    public RespResultMessage uppassword(@RequestBody SysAdminUserEntity sysAdminUserEntity) {
            String newPwd = GeneratePass.SHA512(sysAdminUserEntity.getPassword() + sysAdminUserEntity.getSalt());
            sysAdminUserEntity.setPassword(newPwd);
            sysAdminUserServicee.updateById(sysAdminUserEntity);
            return renderSuccess(true);
    }


    @RequiresPermissions(value={"admin:sys:user:add"},logical= Logical.AND)
    @PostMapping(value = "/add")
    @MethodLog(remarks = "添加新账户")
    public RespResultMessage add(@LoginUser LoginUserBean loginUser, @Validated @RequestBody EditeSysAdminUserReqValid req) {
        sysAdminUserServicee.saveAdminUser(req,loginUser.getUserId());
        return renderSuccess(true);
    }

    @RequiresPermissions(value={"admin:sys:user:delete"},logical= Logical.AND)
    @GetMapping(value = "/delete/{userId}")
    @MethodLog(remarks = "删除账户")
    public RespResultMessage delete(@PathVariable("userId") Integer userId) {
        sysAdminUserServicee.deleteAdminUser(userId);
        return renderSuccess(true);
    }
    @RequiresPermissions(value={"admin:sys:user:delete:batch"},logical= Logical.AND)
    @PostMapping(value = "/delete/batch")
    @MethodLog(remarks = "批量删除账户")
    public RespResultMessage deleteBatch(@Validated @RequestBody BatchDelReqValid req) {
        sysAdminUserServicee.deleteBatchAdminUser(req);
        return renderSuccess(true);
    }

    /**
     * 授权界面
     * @param
     * @return
     */
    @GetMapping(value = "/toGrant/{userid}")
    @MethodLog(remarks = "进入角色授权界面")
    public RespResultMessage toGrant(@PathVariable("userid") Integer userid) {
        Map map = sysAdminUserServicee.selectMapListByUserId(userid);
        return renderSuccess(map);
    }

    /**
     * 授权
     * @param req
     * @return
     */
    @RequiresPermissions(value={"admin:sys:user:grant"},logical= Logical.AND)
    @PostMapping(value = "/grant")
    @MethodLog(remarks = "授权角色")
    public RespResultMessage grant(@LoginUser LoginUserBean loginUser, @Validated @RequestBody GrantSysAdminUserReqValid req) {
        sysAdminUserServicee.saveGrantRole(req,loginUser.getUserId());
        return this.renderSuccess(true);
    }

    @GetMapping(value = "/find")
    @MethodLog(remarks = "当前用户")
    public RespResultMessage findUser(@LoginUser LoginUserBean loginUser) {
        return this.renderSuccess(sysAdminUserServicee.getById(loginUser.getUserId()));
    }

}
