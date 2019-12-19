/**

 @Name：layuiAdmin 主页控制台
 @Author：贤心
 @Site：http://www.layui.com/admin/
 @License：LPPL
    
 */


layui.define(function(exports){
  
  /*
    下面通过 layui.use 分段加载不同的模块，实现不同区域的同时渲染，从而保证视图的快速呈现
  */
  
  
  //区块轮播切换
  layui.use(['admin', 'carousel'], function(){
    var $ = layui.$
    ,admin = layui.admin
    ,carousel = layui.carousel
    ,element = layui.element
    ,device = layui.device();

    //轮播切换
    $('.layadmin-carousel').each(function(){
      var othis = $(this);
      carousel.render({
        elem: this
        ,width: '100%'
        ,arrow: 'none'
        ,interval: othis.data('interval')
        ,autoplay: othis.data('autoplay') === true
        ,trigger: (device.ios || device.android) ? 'click' : 'hover'
        ,anim: othis.data('anim')
      });
    });
    
    element.render('progress');
    
  });



  //数据概览
  layui.use(['admin', 'carousel', 'echarts'], function(){
    var $ = layui.$
    ,admin = layui.admin
    ,carousel = layui.carousel
    ,echarts = layui.echarts;

    // 数据统计
    $getAjaxWithJson("/api/admin/index/dataStatistics", null, function (data) {
      // console.log(data)
      // console.log(data.company)
      $("#company").html(data.company);
      $("#salesCase").html(data.salesCase);
      $("#report").html(data.report);
      $("#yiChang").html(data.yiChang);
      console.log(data.abnormityName)

      var echartsApp = [], options = [
        //今日流量趋势
        //新增的用户量
        {
          title: {
            text: '本月每日异常检测人数',
            x: 'center',
            textStyle: {
              fontSize: 14
            }
          },
          tooltip : { //提示框
            trigger: 'axis',
            formatter: "{b}<br>异常检测人数：{c}"
          },
          xAxis : [{ //X轴
            type : 'category',
            data : data.detectionDay
          }],
          yAxis : [{  //Y轴
            type : 'value'
          }],
          series : [{ //内容
            type: 'line',
            data:data.detectionCount
          }]
        }
      ]
          ,elemDataView = $('#LAY-index-dataview1').children('div')
          ,renderDataView = function(index){
        echartsApp[index] = echarts.init(elemDataView[index], layui.echartsTheme);
        echartsApp[index].setOption(options[index]);
        //window.onresize = echartsApp[index].resize;
        admin.resize(function(){
          echartsApp[index].resize();
        });
      };
      //没找到DOM，终止执行
      if(!elemDataView[0]) return;
      renderDataView(0);
      //监听数据概览轮播
      var carouselIndex = 0;
      carousel.on('change(LAY-index-dataview)', function(obj){
        renderDataView(carouselIndex = obj.index);
      });
      //监听侧边伸缩
      layui.admin.on('side', function(){
        setTimeout(function(){
          renderDataView(carouselIndex);
        }, 300);
      });
      //监听路由
      layui.admin.on('hash(tab)', function(){
        layui.router().path.join('') || renderDataView(carouselIndex);
      });


      var echartsApp1 = [], options1 = [
        {
          title: {
            text: '本月每日客流量',
            x: 'center',
            textStyle: {
              fontSize: 14
            }
          },
          tooltip : { //提示框
            trigger: 'axis',
            formatter: "{b}<br>客流量：{c}"
          },
          xAxis : [{ //X轴
            type : 'category',
            data : data.ridershipDay
          }],
          yAxis : [{  //Y轴
            type : 'value'
          }],
          series : [{ //内容
            type: 'line',
            data:data.ridershipCount,
          }]
        }
      ]
          ,elemDataView = $('#LAY-index-dataview3').children('div')
          ,renderDataView = function(index){
        echartsApp1[index] = echarts.init(elemDataView[index], layui.echartsTheme);
        echartsApp1[index].setOption(options1[index]);
        //window.onresize = echartsApp[index].resize;
        admin.resize(function(){
          echartsApp[index].resize();
        });
      };
      //没找到DOM，终止执行
      if(!elemDataView[0]) return;
      renderDataView(0);
      //监听数据概览轮播
      var carouselIndex = 0;
      carousel.on('change(LAY-index-dataview)', function(obj){
        renderDataView(carouselIndex = obj.index);
      });
      //监听侧边伸缩
      layui.admin.on('side', function(){
        setTimeout(function(){
          renderDataView(carouselIndex);
        }, 300);
      });
      //监听路由
      layui.admin.on('hash(tab)', function(){
        layui.router().path.join('') || renderDataView(carouselIndex);
      });



      var echartsApp2 = [], options2 = [
        {
          title : {
            text: '每个案场的异常数量',
            x: 'center',
            textStyle: {
              fontSize: 14
            }
          },
          tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
          },
          legend: {
            orient : 'vertical',
            x : 'left',
            data:data.abnormityName
          },
          series : [{
            name:'案场',
            type:'pie',
            radius : '55%',
            center: ['50%', '50%'],
            data: data.abnormity
          }]
        }
      ]
          ,elemDataView = $('#LAY-index-dataview2').children('div')
          ,renderDataView = function(index){
        echartsApp2[index] = echarts.init(elemDataView[index], layui.echartsTheme);
        echartsApp2[index].setOption(options2[index]);
        //window.onresize = echartsApp[index].resize;
        admin.resize(function(){
          echartsApp[index].resize();
        });
      };
      //没找到DOM，终止执行
      if(!elemDataView[0]) return;
      renderDataView(0);
      //监听数据概览轮播
      var carouselIndex = 0;
      carousel.on('change(LAY-index-dataview)', function(obj){
        renderDataView(carouselIndex = obj.index);
      });
      //监听侧边伸缩
      layui.admin.on('side', function(){
        setTimeout(function(){
          renderDataView(carouselIndex);
        }, 300);
      });
      //监听路由
      layui.admin.on('hash(tab)', function(){
        layui.router().path.join('') || renderDataView(carouselIndex);
      });

    });
  });

  
  exports('console', {})
});