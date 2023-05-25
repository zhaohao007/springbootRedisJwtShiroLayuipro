layui.define(['form'], function (exports) {
    var $ = layui.$
        , setter = layui.setter
        , form = layui.form;

    //在 JS 中获取路由参数
    var router = layui.router();


    $getAjaxWithJson("/api/admin/sys/config/get/"+router.search.id,null,function (resultData) {
        $("#keyName").val(resultData.keyName);
        $("#keyValue").val(resultData.keyValue);
        $("#desrc").val(resultData.desrc);
        $("#ordernum").val(resultData.ordernum);
    })

    form.on('submit(LAY-config-edit-submit)', function(obj){
        $postAjaxWithJson("/api/admin/sys/config/edit", obj.field, function (data) {
            location.hash = '/sysconfig/list';
        });
        return false;
    });



    exports('sysconfig-edit', {})
});

