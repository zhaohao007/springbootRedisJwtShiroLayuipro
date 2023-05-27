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
  form.render();


    $getAjaxWithJson("/api/admin/sysuser/find/",null,function (data) {
         $("#account").html(data.account);
    })

    $("#LAY_app_flexible").click(function () {
       var classs = $("#LAY_app_flexible").attr("class");
       if (classs == 'layui-icon layui-icon-shrink-right') {
           $("#img").remove();
       }else {
           $("#logo").html('<img src="'+setter.base+'/style/res/case_ms_logo.png" style="width: 180px" id="img"/>');
       }
    })
  //对外暴露的接口
  exports('layouname', {});
});
