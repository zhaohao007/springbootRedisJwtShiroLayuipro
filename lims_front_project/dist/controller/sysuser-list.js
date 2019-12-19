/**

 @Name：layuiAdmin 用户管理 管理员管理 角色管理
 @Author：star1029
 @Site：http://www.layui.com/admin/
 @License：LPPL
    
 */
layui.define(['table','layer','shiro-perms','form','setter','view','common'], function(exports){
  var $ = layui.$
  ,table = layui.table,
  layer = layui.layer
      ,admin = layui.admin
      ,setter = layui.setter
      ,view = layui.view,
  form = layui.form;
  form.render();
    layer.ready(function(){

        $getAjaxWithJson("/api/admin/sysuser/status", null, function (data) {
            console.log(data);
            var htmls='<option value="">全部状态</option>';
            for(var x in data){
                htmls += '<option value = "' + data[x].key + '">' + data[x].desc + '</option>';
            }
            $("#status").html(htmls);
            form.render('select');//需要渲染一下
        });
        $getAjaxWithJson("/api/admin/sysuser/innerType", null, function (dataa) {
            console.log(dataa);
            var htmla='<option value="">全部类型</option>';
            for(var x in dataa){
                htmla += '<option value = "' + dataa[x].key + '">' + dataa[x].desc + '</option>';
            }
            $("#innerType").html(htmla);
            form.render('select');//需要渲染一下
        });
    });
  //角色管理
  var tablelist = table.render({
    elem: '#LAY-sys-user-list-table'
    ,url: setter.baseUrl+"/api/admin/sysuser/list"
    ,method:'post'
    ,where:{
      account:$("#account").val(),
      status:$("#status").val(),
      innerType:$("#innerType").val()
    }
    ,page:true
    ,cols: [[
      {type: 'checkbox', fixed: 'left'}
      ,{field: 'id', width: 60, title: 'ID', sort: true}
          ,{field: 'portrait', title: '用户头像',height: 100,width: 90, templet: function (data) {
                 console.log(data);
                 if (data.portrait!=null&&data.portrait!=""){
                     return '<img style="height: 30px ;width: 30px;margin: auto"  src="'+data.portrait+'"/>'
                 } else {
                     return '<p style="color: #9a9fa4;font-size: 12px" >暂无头像</p>'
                 }
              }}
      ,{field: 'account', width: 100,title: '登录账户'}
      ,{field: 'innerType', width: 130, title: '账户类型', templet: function (data) {
          if (data.innerType == "INNER") return "<span style='color: red'>内置超级管理员</span>";
          if (data.innerType == "NORMAL") return "普通管理员";
        }
      }
      ,{field: 'roles', width: 150, title: '角色', templet: function (data) {
          var descrname = '  ';
          data.roles.forEach(function (currentValue, index, arr) {
                  descrname = descrname + "[" + (index + 1) + "]" + currentValue.roleName
          });
          return descrname ;
        }
      }
      ,{field: 'name',width: 160,  title: '姓名'}
      ,{field: 'status',width: 60,  title: '状态', templet: function (data) {
          if (data.status == "USABLE") return "可用";
          if (data.status == "DISABLE") return "<span style='color: red'>不可用</span>";
        }
      }
      ,{field: 'status',width: 80,  title: '归属', templet: function (data) {
                  if (data.salesCaseId != null)
                      return "案场";
                  if (data.companyId != null) return "合作商";
                  if (data.companyId == null&&data.salesCaseId==null) return "总后台";
              }
          }
      ,{field: 'loginTime',width: 160,  title: '登录时间'}
      ,{field: 'whenCreated',width: 160,  title: '创建时间'}
      ,{title: '操作', width: 250, align: 'center', fixed: 'right', toolbar: '#table-sys-user-tpl'}
      ]]
      , toolbar: '#table-user-list-toolbar'
      , text: {none: '无数据'}
  });

    form.on('submit(LAY-user-back-search-filter)', function (data) {
        //执行重载
        table.reload('LAY-sys-user-list-table', {
            where: {
                account: $("#account").val(),
                status:$("#status").val(),
                innerType: $("#innerType").val()},
            text: {none: '无数据'}
        });
    });
    form.on('submit(LAY-grant-back-delet)', function (data) {
        layer.closeAll();
    });
    form.on('submit(LAY-grant-back-submit)', function (data2) {
        // var req= $('#myradio input[name="roleListId"]:checked ').val()
        // obj.field.permissions = req;
        var field = data2.field; //获取提交的字段
        var field2 =  filterData(field);
        $postAjaxWithJson("/api/admin/sysuser/grant",field2, function (flag) {
            if (flag) {
                layer.msg("赋权成功");
                tablelist.reload();
                } else {
                layer.msg('新增失败')
            }
            layer.closeAll();
        });
    });
  //头部
    //监听头部事件
    table.on('toolbar(LAY-sys-user-list-table-filter)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'add':
                location.hash = "/sysuser/add";
                break;
            case 'batchdel':
                var checkData = checkStatus.data; //得到选中的数据
                if (checkData.length === 0) {
                    return layer.msg('请选择数据', {icon: 2});
                }
                var idList = [];
                checkData.forEach(function (element, index) {
                    idList.push(element.id)
                })
                layer.confirm('确定批量删除吗？', function (index) {
                    var param = {idList: idList};
                    var id=param.idList[0];
                    if (id==1) {
                        layer.msg('内置角色禁止删除!')
                    }else {
                        $postAjaxWithJson("/api/admin/sysuser/delete/batch", param, function (data) {
                            //执行重载
                            table.reload('LAY-sys-user-list-table', {
                                where: {
                                    account: $("#account").val(),
                                    innerType: $("#innerType").val()
                                }
                            });
                        })
                    }
                });
                break;
            case 'update':
                layer.msg('编辑');
                break;
        }
        ;
    });



  //监听行工具条
  table.on('tool(LAY-sys-user-list-table-filter)', function(obj){
    var data = obj.data;
    if(obj.event === 'del'){
      if(obj.data.id==1) {
        layer.msg('内置角色禁止删除!')
      }else {
        layer.confirm('确定删除此管理员？', function(index){
          $getAjaxWithJson("/api/admin/sysuser/delete/"+data.id,null,function (data) {
            obj.del();
            layer.close(index);
          })
        });
      }

    }else if(obj.event === 'edit'){
      //location.href=layui.setter.serverBase + 'admin/sysuser/toEdite/'+data.id;
      //top.layui.index.openTabsPage(layui.setter.serverBase + 'admin/sysuser/toEdite/'+data.id, "编辑用户");
      if(obj.data.id==1) {
        layer.msg('内置角色禁止编辑!')
      }else {
          location.hash = '/sysuser/edite.html?id='+data.id;
      }
    }else if(obj.event === 'grant'){
      if(obj.data.id==1) {
        layer.msg('内置角色禁止赋权!')
      }else {
          var userid=data.id
          layui.use(['admin','view','layer'],function() {
              var admin = layui.admin,
                  view = layui.view;
              admin.popup({
                  "title": "窗口名称"
                  , area: ['500px', '500px']
                  , id: data.id//id唯一
                  , success: function () {
                      view(this.id).render('sysuser/grant').done(function () {
                          $getAjaxWithJson("/api/admin/sysuser/toGrant/"+userid,null,function (resultData) {
                              var list=resultData.roleList;
                              var htmls='';
                              for (var a in list) {
                                  htmls += '<input type="radio" name="roleListId[]"  lay-filter="roleListIdFilter" id="'+list[a].id+'" value="'+list[a].id+'" title="'+list[a].roleName+'"><br>';
                              }
                              $("#grantid").val(userid);
                              $("#myradio").html(htmls);
                              if (resultData.sysUserRoleEntity != null){
                                  $("#"+resultData.sysUserRoleEntity.roleId).prop("checked", true);
                              }
                              form.render();//需要渲染一下

                              // $getAjaxWithJson("/api/admin/sysuser/toGrant/"+userid,null,function (resultData) {
                              //
                              //  })

                          })

                      });
                  }
              });


          });
      }}
  });

  filterData = function (data) {
    var roleListIds = {};
    var temp = {};
    for (var key in data) {
      if (key.indexOf("[") === -1) {
        roleListIds[key] = data[key];
      } else {
        temp[key] = data[key];
      }
    }

    var temp_arr = [];
    for (var key in temp) {
      var _arr = key.split("[");
      var field = _arr[0];
      if (temp_arr.indexOf(field) === -1) {
        temp_arr.push(field);
      }
    }

    for (var i in temp_arr) {
      var _f = temp_arr[i];
      var _temp = [];
      for (var k in temp) {
        console.log(k);
        if (k.indexOf(_f) !== -1) {
          _temp.push(temp[k])
        }
      }
      roleListIds[_f] = _temp;
    }
    return roleListIds;
  };
  exports('sysuser-list', {})
});
