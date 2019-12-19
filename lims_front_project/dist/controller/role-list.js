/**
 @Name： 角色列表功能
 @Author：zhaohao
 @Version：1.0
 */
layui.define(['table', 'form', 'shiro-perms', 'layer'], function (exports) {
    var $ = layui.$
        , table = layui.table
        , admin = layui.admin
        , layer = layui.layer
        , setter = layui.setter
        , view = layui.view
        , form = layui.form;
    form.render();

    table.render({
        elem: '#LAY-sys-role-list-table'
        , url: setter.baseUrl + '/api/admin/role/list'
        , method: 'post'
        , where: {roleName: $("#roleName").val(), innerType: $("#innerType").val()}
        , page: true
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 80, title: 'ID', sort: true}
            , {field: 'roleName', title: '角色名称'}
            , {
                field: 'innerType', title: '角色类型', templet: function (data) {
                    if (data.innerType == "INNER") return "<span style='color: red'>内置超级管理员角色</span>";
                    if (data.innerType == "NORMAL") return "其他角色";
                }
            }
            , {field: 'remark', title: '角色描述'}
            , {field: 'whenCreated', title: '角色创建时间'}
            , {title: '操作', width: 150, align: 'center', fixed: 'right', toolbar: '#table-role-tpl'}
        ]]
        , toolbar: '#table-role-list-toolbar'
        , text: {none: '无数据'}
    });

    form.on('submit(LAY-sys-role-list-search-filter)', function (data) {
        //执行重载
        table.reload('LAY-sys-role-list-table', {
            where: {roleName: $("#roleName").val(), innerType: $("#innerType").val()},
            text: {none: '无数据'}
        });
    });

    //监听行工具条
    table.on('tool(LAY-sys-role-list-table-filter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('确定删除此角色？', function (index) {
                $getAjaxWithJson("/api/admin/role/delete/" + data.id, null, function (data) {
                    obj.del();
                    layer.close(index);
                })
            });
        } else if (obj.event === 'edit') {
            location.hash = "/role/edite/roleId=" + data.id;
        }
    });

//监听头部事件
    table.on('toolbar(LAY-sys-role-list-table-filter)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'add':
                location.hash = "/role/add";
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
                    $postAjaxWithJson("/api/admin/role/delete/batch", param, function (data) {
                        //执行重载
                        table.reload('LAY-sys-role-list-table', {
                            where: {roleName: $("#roleName").val(), innerType: $("#innerType").val()}
                        });
                    })
                });
                break;
            case 'update':
                layer.msg('编辑');
                break;
        };
    });


    exports('role-list', {})
});
