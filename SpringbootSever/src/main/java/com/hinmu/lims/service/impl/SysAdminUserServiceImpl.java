package com.hinmu.lims.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hinmu.lims.dao.SysAdminUserMapper;
import com.hinmu.lims.exception.ApiClientException;
import com.hinmu.lims.model.entity.SysAdminUserEntity;
import com.hinmu.lims.model.entity.SysRoleEntity;
import com.hinmu.lims.model.entity.SysUserRoleEntity;
import com.hinmu.lims.model.enums.DisableStatusEnum;
import com.hinmu.lims.model.enums.InnerTypeEnum;
import com.hinmu.lims.model.reqbean.BatchDelReqValid;
import com.hinmu.lims.model.reqbean.EditeSysAdminUserReqValid;
import com.hinmu.lims.model.reqbean.GrantSysAdminUserReqValid;
import com.hinmu.lims.model.reqbean.LoginReqValid;
import com.hinmu.lims.model.respbean.PageResult;
import com.hinmu.lims.service.ISysAdminUserService;
import com.hinmu.lims.service.ISysRoleService;
import com.hinmu.lims.service.ISysUserRoleService;
import com.hinmu.lims.util.common.GeneratePass;
import com.hinmu.lims.util.page.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author zhaohao
 * @since 2019-08-28
 */
@Service
public class SysAdminUserServiceImpl extends ServiceImpl<SysAdminUserMapper, SysAdminUserEntity> implements ISysAdminUserService {

    @Autowired
    private ISysUserRoleService sysUserRoleService;
    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysAdminUserService adminUserService;

    @Override
    public SysAdminUserEntity findSysAdminUserEntityByAccount(String account) {
        QueryWrapper<SysAdminUserEntity> ew = new QueryWrapper<>();
        ew.eq("account", account);
        return this.getOne(ew);
    }
    @Override
    public EditeSysAdminUserReqValid map(Integer userId){
        return this.baseMapper.find(userId);
    }
    @Override
    public SysAdminUserEntity mapto(Integer userId){
        return this.baseMapper.findto(userId);
    }
    @Override
    public SysAdminUserEntity findLoginByAccountPassword(LoginReqValid loginReq) {
        QueryWrapper<SysAdminUserEntity> ew = new QueryWrapper<>();
        ew.eq("account", loginReq.getAccount());
        SysAdminUserEntity sysAdminUserEntity = this.getOne(ew);
        if (sysAdminUserEntity != null) {
            String salt = sysAdminUserEntity.getSalt();
            String inputPwd = GeneratePass.SHA512(loginReq.getPassword() + salt);
            if (sysAdminUserEntity.getPassword().equals(inputPwd)) {
                return sysAdminUserEntity;
            }
        }
        return null;
    }



    @Override
    public PageResult selectSysAdminUserListPage(Map map) {
        QueryWrapper<SysAdminUserEntity> queryWrapper = new QueryWrapper<>();
        if (map.get("account") != null && StringUtils.isNotEmpty((String) map.get("account"))) {
            String account = (String) map.get("account");
            queryWrapper.like("account", "%"+account+"%");
        }
        if (map.get("companyId") !=null && StringUtils.isNotEmpty((String) map.get("companyId"))){
            int companyId= (int) map.get("companyId");
            queryWrapper.eq("company_id",companyId);
        }
        if (map.get("salesCaseId") !=null && StringUtils.isNotEmpty((String) map.get("salesCaseId"))){
            int salesCaseId= (int) map.get("salesCaseId");
            queryWrapper.eq("sales_case_id",salesCaseId);
        }
        if (map.get("innerType") != null && StringUtils.isNotEmpty((String) map.get("innerType"))) {
            String innerType = (String) map.get("innerType");
            queryWrapper.eq("inner_type", innerType);
        }
        if (map.get("status") != null && StringUtils.isNotEmpty((String) map.get("status"))) {
            String status = (String) map.get("status");
            queryWrapper.eq("status", status);
        }
        if (map.get("beginTime") != null) {
            Date beginTime = (Date) map.get("beginTime");
            queryWrapper.gt("when_created", beginTime);
        }
        if (map.get("endTime") != null) {
            Date endTime = (Date) map.get("endTime");
            queryWrapper.gt("when_created", endTime);
        }
        if (map.get("portrait") != null && StringUtils.isNotEmpty((String) map.get("portrait"))) {
            String portrait = (String) map.get("portrait");
            queryWrapper.gt("portrait", portrait);
        }
        IPage<SysAdminUserEntity> page = this.page(new Query<SysAdminUserEntity>().getPage(map), queryWrapper);
        if (page != null) {
            for (SysAdminUserEntity sysAdminUser : page.getRecords()) {
                //获取角色-用户关系
                List<SysUserRoleEntity> sysUserRoleList = sysUserRoleService.getSysUserRoleList(sysAdminUser.getId());
                if (sysUserRoleList != null) {//组装
                    //组装角色信息
                    Set<SysRoleEntity> roles = new HashSet<>();
                    for (SysUserRoleEntity userRole : sysUserRoleList) {
                        SysRoleEntity sysRoleEntity = sysRoleService.getById(userRole.getRoleId());
                        roles.add(sysRoleEntity);
                    }
                    sysAdminUser.setRoles(roles);
                }
            }
        }
        return new PageResult(page);
    }

    @Override
    public Map selectMapListByUserId(Integer adminUserId) {
        SysAdminUserEntity sysAdminUserEntity = this.getById(adminUserId);
        if (sysAdminUserEntity == null) throw new ApiClientException("不存在的数据");
        if (sysAdminUserEntity.getInnerType().equals(InnerTypeEnum.INNER))
            throw new ApiClientException("内置用户无法编辑");
        Map result = new HashMap();
        result.putAll(BeanMap.create(sysAdminUserEntity));

        //获取角色
        List<SysUserRoleEntity> chooseSysRoleList = sysUserRoleService.getSysUserRoleList(adminUserId);


        SysUserRoleEntity sysUserRoleEntity = sysUserRoleService.getOne(new QueryWrapper<SysUserRoleEntity>().eq("user_id", adminUserId));
        result.put("sysUserRoleEntity",sysUserRoleEntity);
        //所有角色
        List<SysRoleEntity> sysRoleEntityList = sysRoleService.list();
        if (sysRoleEntityList != null) {
            List<Map> chooseAll = new ArrayList<>();
            for (SysRoleEntity roleEntity : sysRoleEntityList) {
                Map role = new HashMap();
                role.putAll(BeanMap.create(roleEntity));
                role.put("choose", chooseFlag(roleEntity.getId(), chooseSysRoleList));
                chooseAll.add(role);
            }
            result.put("roleList", chooseAll);
        }
        return result;
    }

    private boolean chooseFlag(Integer roleId, List<SysUserRoleEntity> list) {
        for (SysUserRoleEntity sysUserRole : list) {
            if (sysUserRole.getRoleId().intValue() == roleId.intValue())
                return true;
        }
        return false;
    }

    @Override
    public void updateAdminUser(EditeSysAdminUserReqValid req, Integer sessionAdminUserId) {
        if (req.getId() == null || req.getId().intValue() == 0)
            throw new ApiClientException("参数异常");
        //更新用户
        SysAdminUserEntity sysAdminUserEntity = this.getById(req.getId());
        if (sysAdminUserEntity == null) throw new ApiClientException("不存在该用户");
        if (sysAdminUserEntity.getInnerType().equals(InnerTypeEnum.INNER))
            throw new ApiClientException("内置用户不允许更新");
        if (!sysAdminUserEntity.getAccount().equals(req.getAccount())) {
            SysAdminUserEntity sysAdminUserEntity2 = findSysAdminUserEntityByAccount(req.getAccount());
            if (sysAdminUserEntity2 != null) throw new ApiClientException("登录账户已存在");
        }
        if (!sysAdminUserEntity.getPassword().equals(req.getPassword())) {
            String newPwd = GeneratePass.SHA512(req.getPassword() + sysAdminUserEntity.getSalt());
            sysAdminUserEntity.setPassword(newPwd);
        }
        Date now = new Date();
        sysAdminUserEntity.setAccount(req.getAccount());
        sysAdminUserEntity.setDescription(req.getDescription());
        sysAdminUserEntity.setName(req.getName());
        sysAdminUserEntity.setStatus(DisableStatusEnum.valueOf(req.getStatus()));
        sysAdminUserEntity.setInnerType(InnerTypeEnum.NORMAL);
        sysAdminUserEntity.setWhenModified(now);
        if (req.getCompanyId()==null){
            sysAdminUserEntity.setCompanyId(null);
        }else {
            sysAdminUserEntity.setCompanyId(req.getCompanyId());
        }
        if (req.getSalesCaseId()==null){
            sysAdminUserEntity.setSalesCaseId(null);
        }else {
            sysAdminUserEntity.setSalesCaseId(req.getSalesCaseId());
        }
        sysAdminUserEntity.setWhoModified(sessionAdminUserId);
        sysAdminUserEntity.setPortrait(req.getPortrait());
        updateById(sysAdminUserEntity);
    }

    @Override
    public Boolean saveAdminUser(EditeSysAdminUserReqValid req, Integer sessionAdminUserId) {
        //更新用户
        SysAdminUserEntity sysAdminUserEntity = findSysAdminUserEntityByAccount(req.getAccount());
        if (sysAdminUserEntity != null)throw new ApiClientException("user.error.notfound");
        sysAdminUserEntity = new SysAdminUserEntity();
        String salt = GeneratePass.createSalt();
        String newPwd = GeneratePass.SHA512(req.getPassword() + salt);
        sysAdminUserEntity.setSalt(salt);
        sysAdminUserEntity.setPassword(newPwd);
        Date now = new Date();
        sysAdminUserEntity.setAccount(req.getAccount());
        sysAdminUserEntity.setCompanyId(req.getCompanyId());
        sysAdminUserEntity.setSalesCaseId(req.getSalesCaseId());
        sysAdminUserEntity.setDescription(req.getDescription());
        sysAdminUserEntity.setName(req.getName());
        sysAdminUserEntity.setStatus(DisableStatusEnum.valueOf(req.getStatus()));
        sysAdminUserEntity.setInnerType(InnerTypeEnum.NORMAL);

        sysAdminUserEntity.setWhenCreated(now);
        sysAdminUserEntity.setWhoCreated(sessionAdminUserId);
        sysAdminUserEntity.setPortrait(req.getPortrait());
        return save(sysAdminUserEntity);
    }


    @Override
    public void saveGrantRole(GrantSysAdminUserReqValid req, Integer sessionAdminUserId) {
        SysAdminUserEntity sysAdminUserEntity =getById(req.getId());
        if(sysAdminUserEntity == null)throw new ApiClientException("用户不存在");
        if(sysAdminUserEntity.getInnerType().equals(InnerTypeEnum.INNER))
            throw new ApiClientException("内部管理员无法编辑权限");

        //删除用户角色
        sysUserRoleService.removeByUserId(req.getId());
        //创建新用户角色
        if (req.getRoleListId() != null && req.getRoleListId().size()>0) {

            List<SysUserRoleEntity> sysUserRoleEntityList = new ArrayList<>();
            for (String roleId : req.getRoleListId()) {
                SysUserRoleEntity roleEntity = new SysUserRoleEntity();
                roleEntity.setRoleId(Integer.valueOf(roleId));
                roleEntity.setUserId(req.getId());
                sysUserRoleEntityList.add(roleEntity);
            }
            sysUserRoleService.saveBatch(sysUserRoleEntityList);


        }



    }

    @Override
    public void deleteAdminUser(Integer userId) {
        SysAdminUserEntity sysAdminUserEntity =getById(userId);
        if(sysAdminUserEntity!=null && sysAdminUserEntity.getInnerType().equals(InnerTypeEnum.INNER)){
            throw new ApiClientException("内部管理员无法删除");
        }
        //删除用户
        removeById(userId);
        //删除关联角色关系
        sysUserRoleService.removeByUserId(userId);
    }

    public void deleteBatchAdminUser(BatchDelReqValid req){
        for(Integer userId:req.getIdList()){
            deleteAdminUser(userId);
        }
    }
    public List<SysAdminUserEntity> pageResult(Integer userid){
        QueryWrapper<SysAdminUserEntity> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",userid);
        List<SysAdminUserEntity> pageResult= adminUserService.list(queryWrapper);
        return pageResult;
    }
}
