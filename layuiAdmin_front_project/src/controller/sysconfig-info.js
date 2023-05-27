layui.config({
    base: '../../../static/layuiadmin/' //静态资源所在路径
}).extend({
    index: 'lib/index' //主入口模块
}).use(['index','form','layer'], function(){
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var $ = layui.jquery;
    form.on('submit(editBtn)', function(data){
        $('#editBtnView').hide();
        //请求登入接口
        admin.req({
            contentType: 'application/json; charset=utf-8'
            ,url:'/admin/sys/config/edit' //实际使用请改成服务端真实接口
            ,type: 'post'
            ,datType: "json"
            ,data: JSON.stringify(data.field)
            ,success: function(res){
                if (res.code == 1) {
                    layer.msg("修改成功!");
                    setTimeout(function () {
                        window.location.href ='/admin/sys/config/toConfigView'
                    }, 1000);
                }else {
                    layer.msg(res.msg);
                    $('#editBtnView').show();
                }
            }
        });
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
    exports('sysconfig-info', {})
});