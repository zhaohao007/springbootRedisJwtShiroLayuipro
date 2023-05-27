/**

 @Name：layuiAdmin Echarts集成
 @Author：star1029
 @Site：http://www.layui.com/admin/
 @License：GPL-2
 */

layui.define([ 'treeTable','shiro-perms','laytpl', 'admin', 'form'], function (exports) {
    var $ = layui.$,
        laytpl = layui.laytpl,
        admin = layui.admin,
        form = layui.form,
        setter = layui.setter,
        view = layui.view,
        treeTable = layui.treeTable;
    form.render();


    var re ;
    $treeTableRender = function(){
        var data = [];
        $getAjaxWithJsonSynch('/api/admin/menu/list', null, function (resultData) {
            data = resultData;
        });
        re = treeTable.render({
            elem: '#menu-tree-table',
            data: data,
            icon_key: 'name',
            is_checkbox: true,
            top_value: 0,
            primary_key: 'id',
            parent_key: 'parentId',
            end: function (e) {
                form.render();
            },
            icon: {
                open: 'layui-icon layui-icon-triangle-d',
                close: 'layui-icon layui-icon-triangle-r',
                left: 16,
            },
            cols: [
                {
                    key: 'name',
                    title: '名称',
                    width: '200px',
                    template: function (item) {
                        if (item.level == 0) {
                            return '<span style="color:red;">' + item.name + '</span>';
                        } else if (item.level == 1) {
                            return '<span style="color:green;">' + item.name + '</span>';
                        } else if (item.level == 2) {
                            return '<span style="color:#aaa;">' + item.name + '</span>';
                        }
                    }
                },
                {
                    key: 'id',
                    title: 'ID',
                    width: '100px',
                    align: 'center',
                },
                {
                    key: 'parentId',
                    title: '父ID',
                    width: '100px',
                    align: 'center',
                },
                {
                    key: 'frontUrl',
                    title: '前端路由',
                    width: '100px',
                    align: 'center',
                },
                {
                    key: 'serverUrl',
                    title: '服务端路由',
                    width: '100px',
                    align: 'center',
                },
                {
                    key: 'perms',
                    title: '菜单控制权限',
                    width: '100px',
                    align: 'center',
                },

                {
                    key: 'type',
                    title: '权限类型',
                    width: '100px',
                    align: 'center',
                    template: function (item) {
                        if (item.type == 'TOP_MENU') return '一级菜单';
                        if (item.type == 'SECOND_MENU') return '二级菜单';
                        if (item.type == 'BUTTON') return '按钮';
                    }
                },
                {
                    key: 'type',
                    title: '操作',
                    align: 'center',
                    width: '250px',
                    template: function (data) {
                        var getTpl = toolbarTemplet.innerHTML;
                        return laytpl(getTpl).render(data);
                    }

                }
            ]
        });
    };
    $treeTableRender();
    treeTable.openAll(re);

    // 全部展开
    $('.open-all').click(function () {
        treeTable.openAll(re);
    })
    // 全部关闭
    $('.close-all').click(function () {
        treeTable.closeAll(re);
    })
    // 新增顶部菜单
    $('.top-menu-all').click(function () {
        addPopup(0)
    })


    // 监听展开关闭
    treeTable.on('tree(flex)', function (data) {
        //  layer.msg(JSON.stringify(data));
    })
    // 监听checkbox选择
    treeTable.on('tree(box)', function (data) {
        if ($(data.elem).parents('#tree-table1').length) {
            var text = [];
            $(data.elem).parents('#tree-table1').find('.cbx.layui-form-checked').each(function () {
                $(this).parents('[data-pid]').length && text.push(o(this).parents('td').next().find('span').text());
            })
            $(data.elem).parents('#tree-table1').prev().find('input').val(text.join(','));
        }
        // layer.msg(JSON.stringify(data));
    })
    // 监听自定义
    treeTable.on('tree(add)', function (data) {
        var parentId = data.item.id;
        addPopup(parentId)
    })
    treeTable.on('tree(edite)', function (data) {
        var menuId = data.item.id;
        editPopup(menuId);

    });
    treeTable.on('tree(delete)', function (data) {
        var id = data.item.id;
        layer.confirm('确认删除吗', function (index) {
            $getAjaxWithJson("/api/admin/menu/delete/" + id, null, function (data) {
                if (data) {
                    $treeTableRender();
                } else {
                    layer.msg('删除失败', {icon: 2});
                }
            })
        });
    });



    addPopup = function(parentId){
        admin.popup({
        title: '添加菜单'
        ,area: ['550px', '550px']
        ,id: 'LAY-popup-content-add'
        ,success: function(layero, index){
            view(this.id).render('menu/add').done(function(){
                //加载下拉框数据
                $getAjaxWithJsonSynch("/api/admin/public/enumdata",null,function (data) {
                    layui.each(data.menuTypeList ,function (index,value) {
                        $("#menuTypeList").append("<option value='"+value.key+"'>"+value.desc+"</option>")
                    })
                })
                form.render(null, 'layuiadmin-form-menu-add');

                //监听提交
                form.on('submit(LAY-sys-menu-add-submit)', function(data){
                    var field = data.field; //获取提交的字段
                    field.parentId = parentId;//添加父级菜单
                    $postAjaxWithJson("/api/admin/menu/add", field, function (data) {
                        if (data) {
                            $treeTableRender();//先加载新数据
                        } else {
                            layer.msg('新增失败', {icon: 2});
                        }
                        layer.close(index); //关闭弹层
                    })
                });
            });
        }
    });
    };
    editPopup = function(menuId){
        admin.popup({
            title: '编辑菜单'
            ,area: ['550px', '550px']
            ,id: 'LAY-popup-content-add'
            ,success: function(layero, index){
                view(this.id).render('menu/edite').done(function(){
                    var menutype;
                    //加载下拉框数据
                    $getAjaxWithJsonSynch("/api/admin/menu/get/"+menuId,null,function (data) {
                        $("#edite_id").val(data.id);
                        $("#edite_parentId").val(data.parentId);
                        $("#edite_name").val(data.name);
                        $("#edite_serverUrl").val(data.serverUrl);
                        $("#edite_frontUrl").val(data.frontUrl);
                        $("#edite_perms").val(data.perms);
                        $("#edite_icon").val(data.icon);
                        $("#edite_orderNum").val(data.orderNum);
                        menutype = data.type;

                    })
                    //加载下拉框数据
                    $getAjaxWithJsonSynch("/api/admin/public/enumdata",null,function (data) {
                        layui.each(data.menuTypeList ,function (index,value) {
                            if(menutype==value.key){
                                $("#edite_menuTypeList").append("<option value='"+value.key+"' selected>"+value.desc+"</option>")
                            }else{
                                $("#edite_menuTypeList").append("<option value='"+value.key+"'>"+value.desc+"</option>")
                            }

                        })
                    })
                    form.render(null, 'layuiadmin-form-menu-edit');

                    //监听提交
                    form.on('submit(LAY-sys-menu-edite-submit)', function(data){
                        var field = data.field; //获取提交的字段
                        $postAjaxWithJson("/api/admin/menu/edite", field, function (data) {
                            if (data) {
                                $treeTableRender();//先加载新数据
                            } else {
                                layer.msg('编辑失败', {icon: 2});
                            }
                            layer.close(index); //关闭弹层
                        })
                    });
                });
            }
        });
    };
// 获取选中值，返回值是一个数组（定义的primary_key参数集合）
    $('.get-checked').click(function () {
        layer.msg('选中参数' + treeTable.checked(re).join(','))
    })
// 刷新重载树表（一般在异步处理数据后刷新显示）
    $('.refresh').click(function () {
        re.data.push({"id": 50, "pid": 0, "title": "1-4"}, {"id": 51, "pid": 50, "title": "1-4-1"});
        treeTable.render(re);
    })


    $(document).on("click", function (i) {
        !$(i.target).parent().hasClass('layui-select-title') && !$(i.target).parents('table').length && !(!$(i.target).parents('table').length && $(i.target).hasClass('layui-icon')) && $(".layui-form-select").removeClass("layui-form-selected").find('.layui-anim').hide();
    })

    exports('sysmenu-list', {})
});
