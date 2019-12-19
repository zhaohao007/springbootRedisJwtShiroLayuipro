/**
 @Name： 按钮颗粒权限验证
 @Author：zhaohao
 @Version：1.0
 */
layui.define(function (exports) {
    var $ = layui.$
        , setter = layui.setter;
    hasPermission = function (perm) {
        var permsArray = layui.data(setter.tableName)[setter.tableKeyPerms];
        if (permsArray != undefined) {
            for (var i = 0; i < permsArray.length; i++) {
                if (permsArray[i].perms == perm) {
                    return true;
                }
            }
        }
        return false;
    }
    exports('shiro-perms', {})
});
