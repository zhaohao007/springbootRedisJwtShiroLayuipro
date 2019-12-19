/**

 @Name：layuiAdmin 公共业务
 @Author：贤心
 @Site：http://www.layui.com/admin/
 @License：LPPL
    
 */
 
layui.define(function(exports){
  var $ = layui.$
  ,layer = layui.layer
  ,table = layui.table
  ,laytpl = layui.laytpl
  ,setter = layui.setter
  ,view = layui.view
  ,admin = layui.admin;
  
  //公共业务的逻辑处理可以写在此处，切换任何页面都会执行
  //……
  $postAjaxWithJson = function (url, param, callback){
    $AjaxWithJson("post",true,url, param, callback);
  };
  //同步
  $postAjaxWithJsonSynch = function (url, param, callback){
    $AjaxWithJson("post",false,url, param, callback);
  };
  $getAjaxWithJson = function (url, param, callback){
    $AjaxWithJson("get",true,url, param, callback);
  };
  $getAjaxWithJsonSynch = function (url, param, callback){
    $AjaxWithJson("get",false,url, param, callback);
  };
  $AjaxWithJson = function (method,async,url, param, callback) {

      if(param==null){
          admin.req({
              contentType: 'application/json; charset=utf-8'
              , url: setter.baseUrl + url //实际使用请改成服务端真实接口
              , type: method
              , async: async
              , datType: "json"
              , success: function (res,headerAuthorization) {
                  console.log("res -----"+JSON.stringify(res));
                  switch (res.code) {
                      case 200://成功
                          //返回的结果中有Authorization覆盖本地表
                          //todo 目前后台框架采用过时自动刷新token机制，因此只要每次请求返回的有token就说明进行了刷新重置，未返回说明未过时
                          if(headerAuthorization!=='' && headerAuthorization!=null && headerAuthorization!=='null' && headerAuthorization!==undefined){
                              layui.data(setter.tableName, {
                                  key: setter.request.tokenName
                                  ,value: headerAuthorization
                              });

                              /**
                               * * * * * * * * * * * * * * * * * * * * * * * * * *
                               *   todo 全局的table设置                          *
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
                                  , parseData: function (res) {
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
                          }


                          callback(res.data);
                          layer.msg(res.msg, {
                              offset: '15px'
                              , icon: 1
                              , time: 1000
                          });
                          break;
                      case 401://无权限退出登陆
                          layer.msg(res.msg, {icon: 4});
                          admin.exit();
                          break;
                      case 422://表单验证 /多个参数 校验失败异常
                          layer.msg(res.msg, {icon: 2});
                          break;
                      case 500://服务器内部异常
                          layer.msg(res.msg, {icon: 2});
                          break;
                      default:
                          layer.msg(res.msg, {icon: 5});
                  }
              }
              ,error: function (res){
                  console.log("res" + JSON.stringify(res))
              }
          });
      }else{
          admin.req({
              contentType: 'application/json; charset=utf-8'
              , url: setter.baseUrl + url //实际使用请改成服务端真实接口
              , type: method
              , async: async
              , datType: "json"
              , data: JSON.stringify(param)
              , success: function (res,headerAuthorization) {
                  console.log("res -----"+JSON.stringify(res));
                  switch (res.code) {
                      case 200://成功

                          //返回的结果中有Authorization覆盖本地表
                          //todo 目前后台框架采用过时自动刷新token机制，因此只要每次请求返回的有token就说明进行了刷新重置，未返回说明未过时
                          if(headerAuthorization!==''&& headerAuthorization!=null && headerAuthorization!=='null' && headerAuthorization!==undefined){
                              layui.data(setter.tableName, {
                                  key: setter.request.tokenName
                                  ,value: headerAuthorization
                              });

                              /**
                               * * * * * * * * * * * * * * * * * * * * * * * * * *
                               *   todo 全局的table设置                          *
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
                                  , parseData: function (res) {
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
                          }




                          callback(res.data);
                          layer.msg(res.msg, {
                              offset: '15px'
                              , icon: 1
                              , time: 1000
                          });
                          break;
                      case 401://无权限退出登陆
                          layer.msg(res.msg, {icon: 4});
                          admin.exit();
                          break;
                      case 422://表单验证 /多个参数 校验失败异常
                          layer.msg(res.msg, {icon: 2});
                          break;
                      case 500://服务器内部异常
                          layer.msg(res.msg, {icon: 2});
                          break;
                      default:
                          layer.msg(res.msg, {icon: 5});
                  }
              }
              ,error: function (res){
                  console.log("res" + JSON.stringify(res))
              }
          });
      }



  };
  
  
  //退出
  admin.events.logout = function(){
      $getAjaxWithJsonSynch("/api/admin/login/logout",null,function (data) {
          //清空本地记录的 token，并跳转到登入页
          admin.exit();
      })
  };

  
  //对外暴露的接口
  exports('common', {});
});
