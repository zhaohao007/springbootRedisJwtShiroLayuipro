package com.hinmu.lims.shiro.realm;

import com.hinmu.lims.shiro.jwt.JwtToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 获得AuthenticationToken，判断是单realm还是多realm,分别去不同的方法验证
 * 参考:  https://blog.csdn.net/u012954380/article/details/84338224
 */
public class UserModularRealmAuthenticator extends ModularRealmAuthenticator {
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        System.out.println("UserModularRealmAuthenticator:method doAuthenticate() execute ");
        // 判断getRealms()是否返回为空
        assertRealmsConfigured();
        // 强制转换回自定义的CustomizedToken
        JwtToken jwtToken = (JwtToken) authenticationToken;
        // 登录类型
        String loginType = jwtToken.getLoginClientType();
        // 所有Realm
        Collection<Realm> realms = getRealms();
        // 登录类型对应的所有Realm
        List<Realm> typeRealms = new ArrayList<>();
        for (Realm realm : realms) {
            if (realm.getName().equals(loginType)) {//todo 保证各个realm中得getname和type一直一致性
                typeRealms.add(realm);
            }
        }

        // 判断是单Realm还是多Realm
        if (typeRealms.size() == 1) {
            System.out.println("doSingleRealmAuthentication() execute ");
            return doSingleRealmAuthentication(typeRealms.get(0), jwtToken);
        } else {
            System.out.println("doMultiRealmAuthentication() execute ");
            return doMultiRealmAuthentication(typeRealms, jwtToken);
        }
    }
}
