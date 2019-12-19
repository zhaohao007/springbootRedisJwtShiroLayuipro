package com.hinmu.lims.shiro.realm;

import com.hinmu.lims.model.Constant;
import com.hinmu.lims.model.entity.SysAdminUserEntity;
import com.hinmu.lims.model.enums.LoginClientTypeEnum;
import com.hinmu.lims.service.ISysAdminUserService;
import com.hinmu.lims.service.UserRoleMenuConService;
import com.hinmu.lims.shiro.jwt.JwtToken;
import com.hinmu.lims.util.common.StringUtil;
import com.hinmu.lims.util.jwt.JwtUtil;
import com.hinmu.lims.util.redis.JedisUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  web端登陆认证域
 *  多域权限限制可以解决多端数据相互隔离，比如常见的多端用户不同访问端，权限数据不一样
 *
 */

public class WebClienUserRealm extends AuthorizingRealm {

    @Autowired
    private ISysAdminUserService sysAdminUserService;
    @Autowired
    private UserRoleMenuConService userRoleMenuConService;

    @Override
    public String getName() {
        return LoginClientTypeEnum.WEB_CLENT.name();
    }

    /**
     * 大坑，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String account = JwtUtil.getClaim(principalCollection.toString(), Constant.ACCOUNT);
        String userId = JwtUtil.getClaim(principalCollection.toString(), Constant.USERID);
        // 添加角色
        simpleAuthorizationInfo.addRole("WEBROLE");
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        // 解密获得account，用于和数据库进行对比
        String account = JwtUtil.getClaim(token, Constant.ACCOUNT);
        String loginclienttype = JwtUtil.getClaim(token, Constant.LOGINCLIENTTYPE);
        // 帐号为空
        if (StringUtil.isBlank(account)) {
            throw new AuthenticationException("Token中帐号为空(The account in Token is empty.)");
        }
        // 查询用户是否存在
        // 查询用户角色
        SysAdminUserEntity adminUserEntity = sysAdminUserService.findSysAdminUserEntityByAccount(account);
        if (adminUserEntity == null) {
            throw new AuthenticationException("该帐号不存在(The account does not exist.)");
        }
        // 开始认证，要AccessToken认证通过，且Redis中存在RefreshToken，且两个Token时间戳一致
        if (JwtUtil.verify(token) && JedisUtil.exists(Constant.getPREFIX_SHIRO_REFRESH_TOKEN(LoginClientTypeEnum.valueOf(loginclienttype))+ account)) {
            // 获取RefreshToken的时间戳
            String currentTimeMillisRedis = JedisUtil.getObject(Constant.getPREFIX_SHIRO_REFRESH_TOKEN(LoginClientTypeEnum.valueOf(loginclienttype)) + account).toString();
            // 获取AccessToken时间戳，与RefreshToken的时间戳对比
            if (JwtUtil.getClaim(token, Constant.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
                return new SimpleAuthenticationInfo(token, token, this.getName());
            }
        }
        throw new AuthenticationException("Token已过期(Token expired or incorrect.)");
    }
}
