$(function() {
	/*$.ajax({
        type : 'POST',
        url : '/hotel/main/triggerChartDate',
        dataType : "json",
        success : function(data){
            if (data.code == 200) {
                lineChartInit(data)
                pieChartInit(data);
            } else {
                layer.open({
                    title: '系统提示',
                    content: (data.msg || '入住图表数据加载异常'),
                    icon: '2'
                });
            }
        }
    });
	$.ajax({
        type : 'POST',
        url : '/hotel/main/triggerinstChartDate',
        dataType : "json",
        success : function(data){
            if (data.code == 200) {
                lineinstChartInit(data)
                pieinstChartInit(data);
            } else {
                layer.open({
                    title: '系统提示',
                    content: (data.msg || '安装报表数据加载异常'),
                    icon: '2'
                });
            }
        }
    });*/
});

// function lineChartInit(data) {
//     var option = {
//         title: {
//             text: '日期分布图'
//         },
//         tooltip : {
//             trigger: 'axis',
//             axisPointer: {
//                 type: 'cross',
//                 label: {
//                     backgroundColor: '#6a7985'
//                 }
//             }
//         },
//         legend: {
//             data:['入住人数','在住人数']
//         },
//         toolbox: {
//             feature: {
//                 /*saveAsImage: {}*/
//             }
//         },
//         grid: {
//             left: '3%',
//             right: '4%',
//             bottom: '3%',
//             containLabel: true
//         },
//         xAxis : [
//             {
//                 type : 'category',
//                 boundaryGap : false,
//                 data : data.content.triggerDayList
//             }
//         ],
//         yAxis : [
//             {
//                 type : 'value'
//             }
//         ],
//         series : [
//             {
//                 name:'入住人数',
//                 type:'line',
//                 stack: '总量',
//                 areaStyle: {normal: {}},
//                 data: data.content.triggerDayCountSucList
//             },
//             {
//                 name:'在住人数',
//                 type:'line',
//                 stack: '总量',
//                 label: {
//                     normal: {
//                         show: true,
//                         position: 'top'
//                     }
//                 },
//                 areaStyle: {normal: {}},
//                 data: data.content.triggerDayCountFailList
//             }
//         ],
//         color:['#00A65A', '#F39C12']
//     };
//
//     var lineChart = echarts.init(document.getElementById('lineChart'));
//     lineChart.setOption(option);
// }
//
// /**
//  * 入住饼图
//  */
// function pieChartInit(data) {
//     var option = {
//         title : {
//             text: '入住比例图',
//             /*subtext: 'subtext',*/
//             x:'center'
//         },
//         tooltip : {
//             trigger: 'item',
//             formatter: "{a} <br/>{b} : {c} ({d}%)"
//         },
//         legend: {
//             orient: 'vertical',
//             left: 'left',
//             data: ['入住人数','在住人数']
//         },
//         series : [
//             {
//                 name: '',
//                 type: 'pie',
//                 radius : '55%',
//                 center: ['50%', '60%'],
//                 data:[
//                     {
//                         value:data.content.triggerCountSucTotal,
//                         name:'入住人数'
//                     },
//                     {
//                         value:data.content.triggerCountFailTotal,
//                         name:'在住人数'
//                     }
//                 ],
//                 itemStyle: {
//                     emphasis: {
//                         shadowBlur: 10,
//                         shadowOffsetX: 0,
//                         shadowColor: 'rgba(0, 0, 0, 0.5)'
//                     }
//                 }
//             }
//         ],
//         color:['#00A65A', '#F39C12']
//     };
//     var pieChart = echarts.init(document.getElementById('pieChart'));
//     pieChart.setOption(option);
// }
//
// //安装总数线图
// function lineinstChartInit(data) {
//     var option = {
//         title: {
//             text: '日期分布图'
//         },
//         tooltip : {
//             trigger: 'axis',
//             axisPointer: {
//                 type: 'cross',
//                 label: {
//                     backgroundColor: '#6a7985'
//                 }
//             }
//         },
//         legend: {
//             data:['安装数量']
//         },
//         toolbox: {
//             feature: {
//                 /*saveAsImage: {}*/
//             }
//         },
//         grid: {
//             left: '3%',
//             right: '4%',
//             bottom: '3%',
//             containLabel: true
//         },
//         xAxis : [
//             {
//                 type : 'category',
//                 boundaryGap : false,
//                 data : data.content.triggerDayList
//             }
//         ],
//         yAxis : [
//             {
//                 type : 'value'
//             }
//         ],
//         series : [
//             {
//                 name:'安装数量',
//                 //type:'line',
//                 type:'bar',
//                 stack: '总量',
//                 areaStyle: {normal: {}},
//                 data: data.content.triggerDayCountSucList
//             },
//
//         ],
//         color:['#00A65A', '#F39C12']
//     };
//
//     var linepecChart = echarts.init(document.getElementById('linepecChart'));
//     linepecChart.setOption(option);
// }
// //安装总数饼图
// function pieinstChartInit(data) {
//     var option = {
//         title : {
//             text: '区域安装分布图 安装总数：'+ data.content.triggerinstsum+'家' ,
//             /*subtext: 'subtext',*/
//             x:'center'
//         },
//         tooltip : {
//             trigger: 'item',
//             formatter: "{a} <br/>{b} : {c} ({d}%)"
//         },
//         legend: {
//             orient: 'vertical',
//             left: 'left',
//             data: ['安装总数']
//         },
//         series : [
//             {
//                 name: '',
//                 type: 'pie',
//                 radius : '55%',
//                 center: ['50%', '60%'],
//                 /* data:[
//
//                  {
//                  value:${data.content.triggerareaDayCountSucList},
//                  name:${data.content.triggerareaList}
//                  }
//
//                  ],*/
//                 data:(function(){
//                     var res = [];
//                     var len = data.content.triggerareaDayCountSucList.length;
//                     var tname="";
//                     var tval="";
//                     for(var i=0,size=len;i<size;i++) {
//                         tname=data.content.triggerareaList[i];
//                         tval=data.content.triggerareaDayCountSucList[i]
//                         res.push({
//                             //通过把result进行遍历循环来获取数据并放入Echarts中
//                             name: tname,
//                             value:tval
//                         });
//                     }
//                     return res;
//                 })(),
//                 itemStyle: {
//                     emphasis: {
//                         shadowBlur: 10,
//                         shadowOffsetX: 0,
//                         shadowColor: 'rgba(0, 0, 0, 0.5)'
//                     }
//                 }
//             }
//         ],
//         color:['#00A65A', '#F39C12','#8B008B','#0000FF','#DC143C','	#EE82EE']
//     };
//     var pieinstChart = echarts.init(document.getElementById('pieinstChart'));
//     pieinstChart.setOption(option);
// }
