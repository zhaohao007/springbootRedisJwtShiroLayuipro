


layui.define(['table', 'form','laydate','shiro-perms'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form
        ,laydate = layui.laydate
        , router = layui.router()
        , search = router.search;

    table.render({
        elem: '#LAY-config-list-table'
        ,url: layui.setter.baseUrl+'/api/admin/sys/config/list'
        ,method:'post'
        ,page:true
        ,where:{keyValue: $("#keyValue").val()}
        ,cols: [[
            {type: 'checkbox', fixed: 'left'}
            ,{field: 'id', title: 'ID'}
            ,{field: 'keyName', title: 'key值'}
            ,{field: 'keyValue', title: 'value值'}
            ,{field: 'desrc', title: '描述信息'}
            ,{field: 'ordernum', title: '排序号'}
            ,{field: 'whenCreated', title: '创建时间'}
            ,{title: '操作', align: 'center', fixed: 'right', toolbar: '#tool'}
        ]]
        ,text: {none: '无数据'}
    });


    form.on('submit(LAY-config-list-search-filter)', function (data) {
        //执行重载
        table.reload('LAY-config-list-table', {
            where:{keyValue: $("#keyValue").val()},
            text: {none: '无数据'}
        });
    });


    //监听行工具条
    table.on('tool(LAY-config-list-table-filter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'reload') {
            $getAjaxWithJson("/api/admin/sys/config/reload", null, function (data) {
               console.log(data);
            })
        } else if (obj.event === 'edit') {
            location.hash = "/sysconfig/edit/id=" + data.id;
        }
    });


    exports('sysconfig-list', {})
});