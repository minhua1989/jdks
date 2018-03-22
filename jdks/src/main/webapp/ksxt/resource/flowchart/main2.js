/**
 * Created by septem on 7/18/16.
 */

$("g[id^='node']").css({
    "position": "relative"
});


//popup box
$("g[id^='node']").on('mouseover', function (e) {

    $("[id^='box']").hide();
    var $mesBox = $('#box' + this.id.match(/\d/)[0]).show();
    var boxRect=$mesBox[0].getBoundingClientRect();

    var coord = this.getBoundingClientRect();

    $mesBox.css({
        "top": coord.top - boxRect.height-30+$('body').scrollTop(),
        "left": coord.left-(boxRect.width-coord.width)/2
    });
});


$("g[id^='node']").on('mouseout', function (e) {
    $("[id^='box']").hide();
    });


//insert count number to legend area
var cntEltArr=$('svg .cnt');
var counts=[]
var strTemp=''

//svg version
function drawPie(elt,obj){
    // debugger;
    //@inner paras
    var radius=65;
    //green for completed amount blue for uncompleted
    var green='#48ED25';
    var blue='#747EEE';
    var colors=[green,blue];

    elt=$(elt);
    //prepare for piety to work create a short string inside a span tag with format 2,3/5
    var arr=obj.results;
    var counts=[];
    var total=arr.reduce(function(sum,current){
        counts.push(current.count);
        return sum+=current.count;

    },0);

    var str='';
    counts.forEach(function(count){
        str+=count+','
    })
    str=str.slice(0,str.length-1);
    // [0,100] [100,0]
    var temp=str.split(',')
    if(temp[0]==0){//no single complete amount
      //reverse the default color pair
        elt.attr('data-peity','{\'fill\':[\''+blue+'\',\''+green+'\']}');
        //change str to match the uncompleted amout to fake completed piechart
        str=temp[1]+'/'+temp[1]
    }else if(temp[1]==0){//all completed
      str=temp[0]+'/'+temp[0]
    }
    elt.text(str);

    function drawChart(elt,radius,colors,text){
        //init
        var fill=elt.data().peity;
        if(fill){
          elt.peity('donut',{
            radius:radius,
            //reverse the default color pair
            fill:[blue,green],
            innerRadius:0.1
          })
        }
          // console.log(elt.data().peity+'!!!!')
          else{
            elt.peity('donut', {
                radius: radius,
                fill: colors,
                innerRadius:0.1
            });
          }



        //draw Text

        var newText = document.createElementNS("http://www.w3.org/2000/svg", "text");


        newText.setAttributeNS(null, "x", radius);
        newText.setAttributeNS(null, "y", radius);
        newText.setAttributeNS(null, "font-size", "30");
        newText.setAttributeNS(null,'fill',"#646366");

        var textNode = document.createTextNode(text);
        $(newText).append(textNode).hide().css({visibility:'hidden'}).show();
        var svg=elt.next();
        svg.append(newText);
        var width=newText.clientWidth;
        var height=newText.clientHeight;
        $(newText).attr({
            x:radius-width/2,
            y:radius+height/3
        })
        // .css({visibility:'visible'});

        return svg;
    }

    return drawChart(elt,radius,colors,obj.title);

}

function main(){
    //add different class
    obj.status.forEach(function(done,index){

        done?$('#node-copy-'+index).addClass('done'):$('#node-copy-'+index).addClass('notdone');

    });
    //insert count number to legend area
    obj.counts.forEach(function(subarr,index,arr){
        if(!length)
            var length=arr.length;
        subarr.forEach(function(count,i){
            nodes[index].results[i].count=count;
            counts.push(count);
            // console.log(count)
            // $(cntEltArr[index*length+i]).find('tspan').text(count);
            })
        });

    counts.forEach(function(count,i){

      if(i%2==0){
        strTemp='<span>已完成: <i class="complete"></i> '+count+'<br />';
      }
      else{
        strTemp+='未完成: <i class="uncomplete"></i> '+count+'</span>'
        var $box=$('#box'+Math.floor(i/2))
        var $p=$box.find('p')
        var content=$p.text()
        // console.log(content)
        $p[0].innerHTML=content+'<br>'+strTemp
        strTemp=''
      }
    });

    obj.counts.forEach(function(_,i){
        var svg=drawPie('#span'+(i+1),nodes[i]);
        var parent=$('#node-copy-'+i)
        parent.append(svg);
        parent.find('circle').remove()
    })

}
// main entry
$(function() {
    if (typeof window['obj'] === 'undefined') {
        obj = parent.__chartdata
    }
    main();
});



//svg text position target only ie
$(function(){

    if(navigator.appName == "Microsoft Internet Explorer" &&
        ((navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE6.0")||
        (navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE7.0")||
        (navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE8.0")||
        (navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE9.0"))){
        $('[id^="node"] tspan').each(function(){
            // debugger;
            var $this=$(this)
            var y=parseInt($this.attr('y'),10)
            $this.attr('y',y+105)
        })
    }

})
