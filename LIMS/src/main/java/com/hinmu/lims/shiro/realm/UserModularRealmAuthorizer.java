package com.hinmu.lims.shiro.realm;

import com.hinmu.lims.model.enums.LoginClientTypeEnum;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Set;

/**
 *   多reaml授权
 *
 * 对于授权时没有控制的，使用资源注入时会发现，使用的是myShiroRealmSHOP的doGetAuthorizationInfo方法（上面SHOP的定义在前），
 * 没有走对应的realm的授权，产生问题错乱
 */
public class UserModularRealmAuthorizer extends ModularRealmAuthorizer {

    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        assertRealmsConfigured();
        Set<String> realmNames = principals.getRealmNames();
        //获取realm的名字
        String realmName = realmNames.iterator().next();
        for (Realm realm : getRealms()) {
            if (!(realm instanceof Authorizer)) continue;
            //匹配名字
            if(realmName.equals(LoginClientTypeEnum.ADMIN_CLENT.name())) {
                if (realm instanceof AdminClientUserRealm) {
                    return ((AdminClientUserRealm) realm).isPermitted(principals, permission);
                }
            }
            if(realmName.equals(LoginClientTypeEnum.APP_CLENT.name())) {
                if (realm instanceof AppClienUserRealm) {
                    return ((AppClienUserRealm) realm).isPermitted(principals, permission);
                }
            }

            if(realmName.equals(LoginClientTypeEnum.WEB_CLENT.name())) {
                if (realm instanceof WebClienUserRealm) {
                    return ((WebClienUserRealm) realm).isPermitted(principals, permission);
                }
            }
        }
        return false;
    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, Permission permission) {
        assertRealmsConfigured();
        Set<String> realmNames = principals.getRealmNames();
        //获取realm的名字
        String realmName = realmNames.iterator().next();
        for (Realm realm : getRealms()) {
            if (!(realm instanceof Authorizer)) continue;
            //匹配名字
            if(realmName.equals(LoginClientTypeEnum.ADMIN_CLENT.name())) {
                if (realm instanceof AdminClientUserRealm) {
                    return ((AdminClientUserRealm) realm).isPermitted(principals, permission);
                }
            }
            //匹配名字
            if(realmName.equals(LoginClientTypeEnum.APP_CLENT.name())) {
                if (realm instanceof AppClienUserRealm) {
                    return ((AppClienUserRealm) realm).isPermitted(principals, permission);
                }
            }

            //匹配名字
            if(realmName.equals(LoginClientTypeEnum.WEB_CLENT.name())) {
                if (realm instanceof WebClienUserRealm) {
                    return ((WebClienUserRealm) realm).isPermitted(principals, permission);
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
        assertRealmsConfigured();
        Set<String> realmNames = principals.getRealmNames();
        //获取realm的名字
        String realmName = realmNames.iterator().next();
        for (Realm realm : getRealms()) {
            if (!(realm instanceof Authorizer)) continue;

            //匹配名字
            if(realmName.equals(LoginClientTypeEnum.ADMIN_CLENT.name())) {
                if (realm instanceof AdminClientUserRealm) {
                    return ((AdminClientUserRealm) realm).hasRole(principals, roleIdentifier);
                }
            }
            //匹配名字
            if(realmName.equals(LoginClientTypeEnum.APP_CLENT.name())) {
                if (realm instanceof AppClienUserRealm) {
                    return ((AppClienUserRealm) realm).hasRole(principals, roleIdentifier);
                }
            }

            //匹配名字
            if(realmName.equals(LoginClientTypeEnum.WEB_CLENT.name())) {
                if (realm instanceof WebClienUserRealm) {
                    return ((WebClienUserRealm) realm).hasRole(principals, roleIdentifier);
                }
            }

        }
        return false;
    }
}
