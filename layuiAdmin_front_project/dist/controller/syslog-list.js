/*
 * @Description: In User Settings Edit
 * @Author: your name
 * @Date: 2019-08-29 11:29:53
 * @LastEditTime: 2019-08-29 14:30:23
 * @LastEditors: Please set LastEditors
 */
layui.define(['table','layer','form','setter','view','common',"laydate"], function(exports){
    var $ = layui.$
        ,table = layui.table,
        layer = layui.layer
        ,laydate=layui.laydate
        ,admin = layui.admin
        ,setter = layui.setter
        ,view = layui.view,
        form = layui.form;
    form.render();
    //日期范围
    laydate.render({
        elem: '#whenCreated'
        , range: true,
        trigger: 'click'
    });
  /*  laydate.render({
        elem: '#whenCreated'
        ,trigger: 'click' //采用click弹出

    });*/

//日志管理
    var tablelist = table.render({
        elem: '#test-table-page'
        ,url: setter.baseUrl+'/sys-log-entity/selectLog'
        ,method:'post'
        ,where:{
            userName:$("#userName").val(),
            ip:$("#ip").val(),
            whenCreated:$("#whenCreated").val(),
            type:$("#type").val()
        }
        ,page:true
        ,limit: 30
        ,height: 'full-320'

        , cols: [[
            /* {type: 'checkbox', width: '10%', fixed: 'left'}*/
            {field: 'id', width: '10%', title: '序号', sort: true}
            , {field: 'userName', width: '10%', title: '操作用户名'}
            , {field: 'ip', width: '10%', title: 'IP地址'}
            , {field: 'url', width: '15%', title: '请求url'}
            , {field: 'type', width: '10%', title: '日志级别'}
            , {field: 'title', width: '10%', title: '日志标题'}
            , {field: 'description', width: '10%', title: '扫描记录'}
            , {field: 'exception', width: '10%', title: '异常'}
            , {field: 'whenCreated', width: '15%', title: '时间'}
        ]]
        , toolbar: '#table-user-list-toolbar'
        ,text: '对不起，加载出现异常！'
    });

    $('.test-table-reload-btn .layui-btn').on('click', function () {
        /*alert("a");*/
        var userName = document.getElementById("userName").value;
        var whenCreated = document.getElementById("whenCreated").value;
        var ip = document.getElementById("ip").value;
        var type = document.getElementById("type").value;
        table.render({
            elem: '#test-table-page'
            , toolbar: '#toolbar'
            , loading: true
            , url: setter.baseUrl+'/sys-log-entity/selectLogTo'
            , page: true
            ,where: {
                userName: userName
                ,whenCreated: whenCreated
                ,ip: ip
                ,type: type
            }
            , cols: [[
                /* {type: 'checkbox', width: '10%', fixed: 'left'}*/
                {field: 'id', width: '10%', title: '序号', sort: true}
                , {field: 'userName', width: '10%', title: '操作用户名'}
                , {field: 'ip', width: '20%', title: 'IP地址'}
                , {field: 'url', width: '10%', title: '请求url'}
                , {field: 'type', width: '20%', title: '日志级别'}
                , {field: 'title', width: '10%', title: '日志标题'}
                , {field: 'description', width: '10%', title: '扫描记录'}
                , {field: 'exception', width: '10%', title: '异常'}
                , {field: 'whenCreated', width: '10%', title: '时间'}
            ]]
            , text: {none: '无数据'}
            , response: {
                statusCode: 1 //规定成功的状态码，默认：0
            }
            , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
                console.log(res);
                return {
                    "code": 1, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.data.totalCount, //解析数据长度
                    "data": res.data.list //解析数据列表
                };
            }

        });
    });
    exports('syslog-list', {})
});
function getIds(checkStatus) {
    var array = new Array();
    for (i = 0; i < checkStatus.length; i++) {
        array.push(checkStatus[i].id);
    }
    return array;

}