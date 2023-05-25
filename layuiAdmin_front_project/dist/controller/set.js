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
      // var resultData=JSON.stringify(data);
      // console.log("find:"+resultData);
      console.log(data.id)
         $("#id").val(data.id);
         $("#accountnames").val(data.account);
         $("#password").val(data.password);
            $("#salt").val(data.salt);
         $("#status").val(data.status);
         if (data.innerType=="INNER"){
           $("#role").val("内置超级管理员");
         }else {
           $("#role").val("普通管理员");
         }
         $("#name").val(data.name);
         if (data.portrait!="") {
             console.log("1111")
             $('#imgZmList').html('<li style="position:relative">' +
                 '<img id="oldView" name="imgZmList" src="' + data.portrait + '"width="150" height="113"></div>' +
                 '<div class="img_edit layui-icon" onclick="croppers_pic(this)">&#xe642;</div>' +
                 '<div class="img_close" onclick="deleteElement(this)">X</div></li>');
         }else {
             console.log("2222")
             $('#imgZmList').html('')
         }
        form.render();
        imgMove("imgZmList");
        $("#description").val(data.description);
    })


  /**
   *   图片上传裁剪部分
   * @type {XMLHttpRequestUpload | string | upload | * | g.upload | g.upload}
   */
  //证明多图片上传
  upload.render({
    elem: '#test-upload-type4'
    ,url: setter.baseUrl+'/api/admin/upload/mediafile'
    , multiple: false
    , before: function (obj) {
      //预读本地文件示例，不支持ie8
      obj.preview(function (index, file, result) {
        /* $('#imgZmList').attr('src', result); //图片链接（base64）*/
        $('#imgZmList').html('<li style="position:relative">' +
            '<img id="oldView" name="imgZmList" src="' + result + '"width="150" height="113"></div>' +
            '<div class="img_edit layui-icon" onclick="croppers_pic(this)">&#xe642;</div>' +
            '<div class="img_close" onclick="deleteElement(this)">X</div></li>');
        form.render();
        imgMove("imgZmList");
      });
    }
    , done: function (res) {
      //上传完毕
      console.log("res" + JSON.stringify(res));
      console.log("res.data[0].fileSrc==   "   +res.data[0].fileSrc);
      $("#portrait").val(res.data[0].fileSrc);
    }
  });

  // 表单提交样例
  form.on('submit(LAY-sys-user-edite-submit)', function(obj){
    console.log(obj.field)
    $postAjaxWithJson("/api/admin/sysuser/edite",obj.field,function(data){
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
        return '两次密码输入不一致';
      }
    }
  });
  
  //网站设置
  form.on('submit(set_website)', function(obj){
    layer.msg(JSON.stringify(obj.field));
    
    //提交修改
    /*
    admin.req({
      url: ''
      ,data: obj.field
      ,success: function(){
        
      }
    });
    */
    return false;
  });
  
  //邮件服务
  form.on('submit(set_system_email)', function(obj){
    layer.msg(JSON.stringify(obj.field));
    
    //提交修改
    /*
    admin.req({
      url: ''
      ,data: obj.field
      ,success: function(){
        
      }
    });
    */
    return false;
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
  

  //上传头像
  var avatarSrc = $('#LAY_avatarSrc');
  
  //查看头像
  admin.events.avartatPreview = function(othis){
    var src = avatarSrc.val();
    layer.photos({
      photos: {
        "title": "查看头像" //相册标题
        ,"data": [{
          "src": src //原图地址
        }]
      }
      ,shade: 0.01
      ,closeBtn: 1
      ,anim: 5
    });
  };
  
  
  //设置密码
  form.on('submit(setmypass)', function(obj){
    layer.msg(JSON.stringify(obj.field));
    
    //提交修改
    /*
    admin.req({
      url: ''
      ,data: obj.field
      ,success: function(){
        
      }
    });
    */
    return false;
  });
  
  //对外暴露的接口
  exports('set', {});
});
//图片删除
function deleteElement(Obj) {
  Obj.parentNode.parentNode.removeChild(Obj.parentNode);
  document.getElementById('portrait').value = null;
  document.getElementById('imgZmList').html('');
}
function croppers_pic(obj) {
  console.log("bianjis")
  var src = document.getElementById('oldView').src;
  layui.use(["croppers"], function () {
    var setter = layui.setter;
    var croppers = layui.croppers;
    croppers.render({
      area: ['900px', '600px']  //弹窗宽度
      , imgUrl: src
      , url: setter.baseUrl+'/api/admin/upload/mediafile'  //图片上传接口返回和（layui 的upload 模块）返回的JOSN一样
      , done: function (res) { //上传完毕回调
        //更改图片src
        document.getElementById('oldView').src = res;
        //上传完毕
        document.getElementById('portrait').value = res;
      }
    });
  });
}


function getClass(cls){
    var ret = [];
    var els = document.getElementsByTagName("*");
    for (var i = 0; i < els.length; i++){
        //判断els[i]中是否存在cls这个className;.indexOf("cls")判断cls存在的下标，如果下标>=0则存在;
        if(els[i].className === cls || els[i].className.indexOf("cls")>=0 || els[i].className.indexOf(" cls")>=0 || els[i].className.indexOf(" cls ")>0){
            ret.push(els[i]);
        }
    }
    return ret;
}
function getStyle(obj,attr){//解决JS兼容问题获取正确的属性值
    return obj.currentStyle?obj.currentStyle[attr]:getComputedStyle(obj,false)[attr];
}
function startMove(obj,json,fun){
    clearInterval(obj.timer);
    obj.timer = setInterval(function(){
        var isStop = true;
        for(var attr in json){
            var iCur = 0;
            //判断运动的是不是透明度值
            if(attr=="opacity"){
                iCur = parseInt(parseFloat(getStyle(obj,attr))*100);
            }else{
                iCur = parseInt(getStyle(obj,attr));
            }
            var ispeed = (json[attr]-iCur)/8;
            //运动速度如果大于0则向下取整，如果小于0想上取整；
            ispeed = ispeed>0?Math.ceil(ispeed):Math.floor(ispeed);
            //判断所有运动是否全部完成
            if(iCur!=json[attr]){
                isStop = false;
            }
            //运动开始
            if(attr=="opacity"){
                obj.style.filter = "alpha:(opacity:"+(json[attr]+ispeed)+")";
                obj.style.opacity = (json[attr]+ispeed)/100;
            }else{
                obj.style[attr] = iCur+ispeed+"px";
            }
        }
        //判断是否全部完成
        if(isStop){
            clearInterval(obj.timer);
            if(fun){
                fun();
            }
        }
    },30);
}


function imgMove(obj) {
    var oUl = document.getElementById(obj);
    var aLi = oUl.getElementsByTagName("li");
    var disX = 0;
    var disY = 0;
    var minZindex = 1;
    var aPos = [];
    var leftbz = 0;
    var topbz = 0;
    for (var i = 0; i < aLi.length; i++) {
        if (leftbz == 5) {
            leftbz = 1;
            topbz += 1;
            var fdiv = (topbz + 1) * 140;
            oUl.style.height = fdiv + 'px';
        }
        else {
            leftbz += 1;
        }
        //var l = aLi[i].offsetLeft;
        //var t = aLi[i].offsetTop;
        //此处注意，我是按照控件算出来的。尴尬。。。/(ㄒoㄒ)/~~
        var l = 170 * (leftbz - 1) + 10;
        var t = 130 * topbz;

        aLi[i].style.top = t + "px";
        aLi[i].style.left = l + "px";
        aPos[i] = { left: l, top: t };
        aLi[i].index = i;


    }
    for (var i = 0; i < aLi.length; i++) {
        aLi[i].style.position = "absolute";
        aLi[i].style.margin = 0;
        setDrag(aLi[i]);
    }
    //拖拽
    function setDrag(obj) {
        obj.onmouseover = function () {
            obj.style.cursor = "move";
        }
        obj.onmousedown = function (event) {
            var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
            var scrollLeft = document.documentElement.scrollLeft || document.body.scrollLeft;
            obj.style.zIndex = minZindex++;
            //当鼠标按下时计算鼠标与拖拽对象的距离
            disX = event.clientX + scrollLeft - obj.offsetLeft;
            disY = event.clientY + scrollTop - obj.offsetTop;
            document.onmousemove = function (event) {
                //当鼠标拖动时计算div的位置
                var l = event.clientX - disX + scrollLeft;
                var t = event.clientY - disY + scrollTop;
                obj.style.left = l + "px";
                obj.style.top = t + "px";
                /*for(var i=0;i<aLi.length;i++){
                    aLi[i].className = "";
                    if(obj==aLi[i])continue;//如果是自己则跳过自己不加红色虚线
                    if(colTest(obj,aLi[i])){
                        aLi[i].className = "active";
                    }
                }*/
                for (var i = 0; i < aLi.length; i++) {
                    aLi[i].className = "";
                }
                var oNear = findMin(obj);
                if (oNear) {
                    oNear.className = "active";
                }
            }
            document.onmouseup = function () {
                document.onmousemove = null;//当鼠标弹起时移出移动事件
                document.onmouseup = null;//移出up事件，清空内存
                //检测是否普碰上，在交换位置
                var oNear = findMin(obj);
                if (oNear) {
                    oNear.className = "";
                    oNear.style.zIndex = minZindex++;
                    obj.style.zIndex = minZindex++;
                    startMove(oNear, aPos[obj.index]);
                    startMove(obj, aPos[oNear.index]);
                    //交换index
                    oNear.index += obj.index;
                    obj.index = oNear.index - obj.index;
                    oNear.index = oNear.index - obj.index;
                } else {

                    startMove(obj, aPos[obj.index]);
                }
            }
            clearInterval(obj.timer);
            return false;//低版本出现禁止符号
        }
    }
    //碰撞检测
    function colTest(obj1, obj2) {
        var t1 = obj1.offsetTop;
        var r1 = obj1.offsetWidth + obj1.offsetLeft;
        var b1 = obj1.offsetHeight + obj1.offsetTop;
        var l1 = obj1.offsetLeft;

        var t2 = obj2.offsetTop;
        var r2 = obj2.offsetWidth + obj2.offsetLeft;
        var b2 = obj2.offsetHeight + obj2.offsetTop;
        var l2 = obj2.offsetLeft;

        if (t1 > b2 || r1 < l2 || b1 < t2 || l1 > r2) {
            return false;
        } else {
            return true;
        }
    }
    //勾股定理求距离
    function getDis(obj1, obj2) {
        var a = obj1.offsetLeft - obj2.offsetLeft;
        var b = obj1.offsetTop - obj2.offsetTop;
        return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }
    //找到距离最近的
    function findMin(obj) {
        var minDis = 999999999;
        var minIndex = -1;
        for (var i = 0; i < aLi.length; i++) {
            if (obj == aLi[i]) continue;
            if (colTest(obj, aLi[i])) {
                var dis = getDis(obj, aLi[i]);
                if (dis < minDis) {
                    minDis = dis;
                    minIndex = i;
                }
            }
        }
        if (minIndex == -1) {
            return null;
        } else {
            return aLi[minIndex];
        }
    }
}