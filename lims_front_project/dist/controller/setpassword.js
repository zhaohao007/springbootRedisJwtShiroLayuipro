/**

 @Name：layuiAdmin 设置
 @Author：贤心
 @Site：http://www.layui.com/admin/
 @License: LPPL
    
 */

layui.define(['admin','form', 'form', 'common','table','upload'], function (exports) {
  var $ = layui.$
      , layer = layui.layer
      , laytpl = layui.laytpl
      , setter = layui.setter
      , view = layui.view
      , admin = layui.admin
      , form = layui.form
      , table = layui.table
      , upload=layui.upload
      , router = layui.router()
      , search = router.search;
  var $body = $('body');

  form.render();

    $getAjaxWithJson("/api/admin/sysuser/find/",null,function (data) {
         $("#id").val(data.id);
         $("#password").val(data.password);
            $("#salt").val(data.salt);
    })


  // 表单提交样例
  form.on('submit(LAY-sys-user-edite-submit)', function(obj){
    console.log(obj.field)
    $postAjaxWithJson("/api/admin/sysuser/uppassword",obj.field,function(data){
      location.hash="../start/index";
    });
    return false;
  });


  //自定义验证
  form.verify({
    nickname: function(value, item){ //value：表单的值、item：表单的DOM对象
      if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
        return '用户名不能有特殊字符';
      }
      if(/(^\_)|(\__)|(\_+$)/.test(value)){
        return '用户名首尾不能出现下划线\'_\'';
      }
      if(/^\d+\d+\d$/.test(value)){
        return '用户名不能全为数字';
      }
    }
    
    //我们既支持上述函数式的方式，也支持下述数组的形式
    //数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
    ,pass: [
      /^[\S]{6,12}$/
      ,'密码必须6到12位，且不能出现空格'
    ]
    
    //确认密码
    ,repass: function(value){
      if(value !== $('#mytext').val()){
        return '请再次输入新密码';
      }
    }
  });


    form.verify({ // value：表单的值、item：表单的DOM对象
        password: function (value, item) { // 可4个空格，非空，可以有特殊字符
            if (value == "") {
                return "请输入密码";
            }else{
                if(!value.trim().match(/^(?![^a-zA-Z]+$)(?!\D+$).{8,16}/)){
                    return "请以不能少于8位的数字加字母的形式输入"
                }
            }
        }
    });

    // 判断密码强度
    $("#mytext").keyup(function() {
        var inputlength = $("#mytext").val().length; // 获取输入值的个数
        if(inputlength <= 8){
            $(".spans").html("<span style='color: #ff3300;'>弱</span>");
        }else{
            if($("#mytext").val().match(/\d/) && $("#mytext").val().match(/[a-zA-Z]/)) {
                $(".spans").html("<span style='color: #060'>强</span>");
            } else {
                $(".spans").html("<span style='color: #099'>中</span>");
            }
        }
    });

  //对外暴露的接口
  exports('setpassword', {});
});
