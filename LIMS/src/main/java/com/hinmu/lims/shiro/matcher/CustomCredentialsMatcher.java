package com.hinmu.lims.shiro.matcher;

/**
 * 自定义匹配器
 */
public class CustomCredentialsMatcher {
//public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {
//   @Override
//    public boolean doCredentialsMatch(AuthenticationToken authcToken,
//                                      AuthenticationInfo info) {
//        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
//        SysAdminUserEntity sysAdminUserEntity = (SysAdminUserEntity)info.getPrincipals().getPrimaryPrincipal();
//        String pwd = String.valueOf(token.getPassword());
//        Object tokenCredentials = GeneratePass.SHA512(pwd+ sysAdminUserEntity.getSalt());
//        Object accountCredentials = getCredentials(info);
//        return equals(tokenCredentials, accountCredentials);
//       }


}
