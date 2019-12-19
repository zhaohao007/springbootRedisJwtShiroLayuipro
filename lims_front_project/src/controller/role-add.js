/**
 @Name： 角色添加
 @Author：zhaohao
 @Version：1.0
 */
layui.define(['form', 'authtree'], function (exports) {
    var $ = layui.$
        , authtree = layui.authtree
        , setter = layui.setter
        , form = layui.form;
    form.render();

    $getAjaxWithJsonSynch('/api/admin/menu/list', null, function (resultData) {
        var trees = fn2(resultData)
        // 如果后台返回的不是树结构，请使用 authtree.listConvert 转换
        authtree.render('#LAY-auth-tree-index', trees, {
            inputname: 'authids[]',
            layfilter: 'lay-check-auth',
            autowidth: true,
            valueKey: 'id',
            childKey: 'childrenNode',
            nameKey: 'name',
            themePath:setter.base+ 'style/tree_themes/',
            theme: 'auth-skin-default',
            autowidth: true
        });
    });

    form.verify({ // value：表单的值、item：表单的DOM对象
        roleName: function (value, item) { // 可4个空格，非空，可以有特殊字符
          if (value.length<1){
              return '角色名称不能为空'
          }else if (value.length>20){
              return '角色名称不能大于20位'
          }
        },
        remark: function (value, item) { // 可4个空格，非空，可以有特殊字符
            if (value.length<1){
                return '角色描述不能为空'
            }else if (value.length>20){
                return '角色描述不能大于20位'
            }
        }
    });

    /**
     * 表单提交样例
     */
    form.on('submit(LAY-auth-tree-submit)', function (obj) {
        var authids = authtree.getChecked('#LAY-auth-tree-index');
        console.log('Choosed authids is', authids);
        obj.field.permissions = authids;
        if (obj.field.permissions.length<=0) {
            layer.msg("至少选择一种权限");
            return false;
        }
        $postAjaxWithJson("/api/admin/role/add", obj.field, function (data) {
            location.hash = '/role/list';
        });
        return false;
    });

    $("#treecheckAll").click(function (event) {
        authtree.checkAll('#LAY-auth-tree-index');
    });

    $("#treeuncheckAll").click(function (event) {
        authtree.uncheckAll('#LAY-auth-tree-index');
    });

    exports('role-add', {})
});

fn = function (jsonData) {
    //取得顶级的数据
    var resultObj = []
    var baseNode = jsonData.filter(function (element) {
        return element.pid === 0
    })
    resultObj.push(baseNode)
    getChildren(resultObj, jsonData)
};

fn2 = function (jsonData) {
    //取得顶级的数据
    var resultObj = []
    jsonData.forEach(function (element, index) {
        if (element.parentId === 0) {
            resultObj.push(element)
            getChildren(resultObj, jsonData)
        }
    })
    return resultObj;
};
getChildren = function (nodeList, jsonData) {
    nodeList.forEach(function (element, index) {
        element.childrenNode = jsonData.filter(function (item, indexI) {
            return item.parentId === element.id
        })
        if (element.childrenNode.length > 0) {
            getChildren(element.childrenNode, jsonData)
        }
    })
};
