/**

 @Name：layuiAdmin 用户登入和注册等
 @Author：贤心
 @Site：http://www.layui.com/admin/
 @License: LPPL

 */

layui.define(['admin','form', 'form', 'common','table','sliderVerify'], function (exports) {
    var $ = layui.$
        , layer = layui.layer
        , laytpl = layui.laytpl
        , setter = layui.setter
        , view = layui.view
        , admin = layui.admin
        , form = layui.form
        , table = layui.table
        , router = layui.router()
        ,sliderVerify = layui.sliderVerify
        , search = router.search;
    var $body = $('body');
    var remember = layui.data(setter.tableName)[setter.tableKeyRemember];
    if(remember){
        $("#LAY-user-login-username").val(layui.data(setter.tableName)[setter.tableKeyAccount]);
        $("#LAY-user-login-password").val(layui.data(setter.tableName)[setter.tableKeyPassword]);
        $("#rememberAff").prop("checked", true);
    }
    $("#account").focus();//聚焦输入框
    //enter登录
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $('#loginBnt').click();//换成按钮的id即可
        }
    });
    form.render();


    //自定义验证
    form.verify({
        nickname: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
                return '用户名不能有特殊字符';
            }
            if (/(^\_)|(\__)|(\_+$)/.test(value)) {
                return '用户名首尾不能出现下划线\'_\'';
            }
            if (/^\d+\d+\d$/.test(value)) {
                return '用户名不能全为数字';
            }
        }
        //我们既支持上述函数式的方式，也支持下述数组的形式
        //数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
        , pass: [
            /^[\S]{6,12}$/
            , '密码必须6到12位，且不能出现空格'
        ]
    });


    //更换图形验证码
    $body.on('click', '#LAY-user-get-vercode', function () {
        var othis = $(this);
        this.src = 'https://www.oschina.net/action/user/captcha?t=' + new Date().getTime()
    });


    var slider = sliderVerify.render({
        elem: '#slider',
        onOk: function(){//当验证通过回调
            layer.msg("滑块验证通过");
        }
    })

    //提交
    form.on('submit(LAY-user-login-submit)', function (obj) {
        if(slider.isOk()){
        $postAjaxWithJsonSynch('/api/admin/login/user', obj.field, function (result) {


            //登入成功的提示与跳转
            layer.msg('登入成功', {
                offset: '15px'
                , icon: 1
                , time: 1000
            }, function () {
                /**
                 * * * * * * * * * * * * * * * * * * * * * * * * * *
                 *   todo 登陆成功后进行全局的table设置            *
                 * * * * * * * * * * * * * * * * * * * * * * * * * *
                 */


                table.set({
                    parseData: function (res) {
                        return {
                            "code": res.code, //解析接口状态
                            "msg": res.msg, //解析提示文本
                            "count": res.data.totalCount, //解析数据长度
                            "data": res.data.list //解析数据列表
                        };
                    }
                    , response: {
                        statusCode: 200 //规定成功的状态码，默认：0
                    }
                });

                if(obj.field.remember!=undefined){
                    layui.data(setter.tableName, {
                        key: setter.tableKeyRemember,
                        value: true
                    });
                    layui.data(setter.tableName, {
                        key: setter.tableKeyAccount,
                        value: $("#LAY-user-login-username").val()
                    });
                    layui.data(setter.tableName, {
                        key: setter.tableKeyPassword,
                        value: $("#LAY-user-login-password").val()
                    });
                }else{
                    layui.data(setter.tableName, {
                        key: setter.tableKeyRemember,
                        value: false
                    });
                    layui.data(setter.tableName, {
                        key: setter.tableKeyAccount,
                        remove: true
                    });
                    layui.data(setter.tableName, {
                        key: setter.tableKeyPassword,
                        remove: true
                    });
                }


                $getAjaxWithJsonSynch("/api/admin/login/perms/list", null, function (res) {
                    layui.data(setter.tableName, {
                        key: setter.tableKeyPerms ,value: res
                    });
                    location.hash = search.redirect ? decodeURIComponent(search.redirect) : '/';
                })

            });
        })

        }else{
            layer.msg("请先通过滑块验证");
        }
        return false;
    });


    //对外暴露的接口
    exports('userlogin', {});
});
