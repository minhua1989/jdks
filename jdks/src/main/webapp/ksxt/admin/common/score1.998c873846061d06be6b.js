webpackJsonp([8],{0:function(t,e,a){t.exports=a(493)},307:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:true});var i=a(8);var d=l(i);var s=a(9);var n=l(s);var r=a(1384);var o=l(r);function l(t){return t&&t.__esModule?t:{"default":t}}e.default={data:function u(){return{currentMenu:"scoreManage",currentSubmenu:"score1"}},components:{Navbar:d.default,Topmenu:n.default,Mainarea:o.default}}},308:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:true});var i=a(1389);var d=s(i);function s(t){return t&&t.__esModule?t:{"default":t}}e.default={data:function n(){return{currentView:"PxDefault"}},components:{PxDefault:d.default},ready:function r(){}}},309:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:true});var i=a(1);var d=l(i);var s=a(2);var n=l(s);var r=a(494);var o=l(r);function l(t){return t&&t.__esModule?t:{"default":t}}e.default={editmode:"add",mixins:[o.default],events:{doAdd:"doAdd"},methods:{doAdd:function u(t){this.validateAll(function(){window.post({url:"/jdks/user/add",data:(0,d.default)(this.entity)},function(e){if(e.status==="success"){t.close();window.app.$broadcast("reloadList");window.Alert("操作成功")}else{window.Alert(e.message)}})}.bind(this))}},ready:function f(){this.beforeInit();this.$nextTick(function(){this.afterInit();this.validateInit()})},components:{Vf:n.default}}},310:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:true});var i=a(1);var d=f(i);var s=a(2);var n=f(s);var r=a(495);var o=f(r);var l=a(7);var u=f(l);function f(t){return t&&t.__esModule?t:{"default":t}}e.default={editmode:"edit",mixins:[o.default],events:{doEdit:"doEdit"},props:["id"],methods:{doEdit:function c(t,e){console.log(this.entity);this.validateAll(function(){window.post({url:"/jdks/kscj/edit",data:(0,d.default)(this.entity)},function(t){if(t.status==="success"){e.close();window.app.$broadcast("reloadList");window.Alert("操作成功")}else{window.Alert(t.message)}})}.bind(this))}},ready:function v(){u.default.series([function(t){this.beforeInit();t(null,"")}.bind(this),function(t){window.post({url:"/jdks/kscj/searchOne",data:(0,d.default)({id:this.id})},function(e){this.dataPreHandle(e);this.entity.oldksccid=this.entity.ksccid;t(null,"")}.bind(this))}.bind(this)],function(t,e){if(t){window.winLog(t)}this.$nextTick(function(){this.afterInit();this.validateInit()})}.bind(this))},components:{Vf:n.default}}},311:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:true});var i=a(3);var d=s(i);function s(t){return t&&t.__esModule?t:{"default":t}}e.default={data:function n(){return{entity:{fileid:"",fileurl:""}}},methods:{initUpload:function r(){try{window["km_upload"].uList["attachment"].destroy()}catch(t){}window.__global.checkquit="";var e='\n        <div class="d-upload">\n          <div class="up">\n            <div id="d_upload_attachment" name="attachment"></div>\n          </div>\n          <div class="uploadholder uploadholder-attachment ua-normal">\n            <div class="icon"></div>\n            <div class="pro"></div>\n          </div>\n          <div>文件大小不超过 20M，文件类型为 xlsx</div>\n        </div>\n      ';(0,d.default)("#d_file").html(e);window["funcUploadFile"]("attachment",".uploadholder-attachment","/jdks/kscj/impKSCJ","*.xlsx",1024*20,1,function(t){if(typeof t==="string"){t=JSON.parse(t)}if(t["data"]){this.entity.fileid=t.data.fileid;this.entity.fileurl=t.data.fileurl}var e="";e+=t.message;if(t.status==="success"){if(t.data["url1"]){e+='<br><br><a href="'+t.data["url1"]+'" target="_blank">点击下载查看操作成功的数据</a>'}if(t.data["url2"]){e+='<br><br><a href="'+t.data["url2"]+'" target="_blank">点击下载查看操作失败的数据</a>'}if(t.data["url"]){e+='<br><br><a href="'+t.data["url"]+'" target="_blank">操作结果</a>'}}(0,d.default)("#d_result").html(e);setTimeout(function(){this.initUpload()}.bind(this),1e3)}.bind(this))}},ready:function o(){window["loadUpload"](function(){this.initUpload()}.bind(this))}}},312:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:true});var i=a(1);var d=r(i);var s=a(2);var n=r(s);function r(t){return t&&t.__esModule?t:{"default":t}}e.default={data:function o(){return{entity:{}}},props:["id"],ready:function l(){window.post({url:"/jdks/user/searchOne",data:(0,d.default)({id:this.id})},function(t){this.entity=t.data}.bind(this))},components:{Vf:n.default}}},313:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:true});var i=a(5);var d=z(i);var s=a(1);var n=z(s);var r=a(3);var o=z(r);var l=a(10);var u=z(l);var f=a(13);var c=z(f);var v=a(4);var p=z(v);var h=a(1385);var m=z(h);var x=a(1388);var b=z(x);var _=a(1386);var w=z(_);var g=a(1387);var y=z(g);var k=a(12);var M=z(k);var j=a(11);var A=z(j);function z(t){return t&&t.__esModule?t:{"default":t}}e.default={data:function T(){var t=window.__global.user.roleid;var e=(0,u.default)(["0","1","2","3","4"],"score1_table",t,this);if(!e){return{}}return e},events:{add:"add",edit:"edit",del:"del",view:"view",used:"used",use:"use","import":"import",reloadList:"reloadList"},methods:{reloadList:function P(){this.dataHandle()},"import":function C(){if(!this.preventDoubleClick()){return}var t=p.default.extend({template:"<mx-import></mx-import>",components:{MxImport:y.default}});this.dialogModal({did:"modal_import",title:"导入成绩",draggable:true,closable:false,closeByBackdrop:false,closeByKeyboard:false,buttons:[{label:"关闭",action:function e(t){t.close();window.app.$broadcast("reloadList")}}]},t)},view:function I(t){var e=p.default.extend({data:function a(){return{id:t}},template:'<mx-view :id="id"></mx-view>',components:{MxView:b.default}});this.dialogModal({did:"modal_view",title:"查看",cssClass:"big-dialog-umeditor",buttons:[{label:"关闭",action:function i(t){t.close()}}]},e)},add:function O(){var t=p.default.extend({template:"<mx-add></mx-add>",components:{MxAdd:m.default}});this.dialogModal({did:"modal_add",title:"添加",cssClass:"big-dialog-umeditor",buttons:[{label:"提交",cssClass:"btn-primary",action:function e(t){window.app.$broadcast("doAdd",t)}},{label:"关闭",action:function a(t){t.close()}}]},t)},edit:function L(t){var e=p.default.extend({data:function a(){return{id:t}},template:'<mx-edit :id="id"></mx-edit>',components:{MxEdit:w.default}});this.dialogModal({did:"modal_edit",title:"修改",cssClass:"big-dialog-umeditor",buttons:[{label:"提交",cssClass:"btn-primary",action:function i(e){window.app.$broadcast("doEdit",t,e)}},{label:"关闭",action:function d(t){t.close()}}]},e)},del:function V(t){var e=[];var a=0;var i=true;var s=false;var r=undefined;try{for(var l=(0,d.default)(window["dataTable"]["table"].bootstrapTable("getData")),u;!(i=(u=l.next()).done);i=true){var f=u.value;console.log(f);if(!f["ischeck"]){continue}e.push(f["id"]);a++}}catch(c){s=true;r=c}finally{try{if(!i&&l.return){l.return()}}finally{if(s){throw r}}}if(a===0){window.Alert("请先选择要进行删除的记录前的复选框");return}t=e.join(",");this.dialogWaiting({title:"操作",message:'<div id="modal_plsh_msg">正在处理，请稍候...</div>'},{url:"/jdks/user/wulideleted",data:(0,n.default)({ids:t})},function(t){if(t.status==="success"){(0,o.default)("#modal_plsh_msg").html("操作成功");this.dataHandle()}else{(0,o.default)("#modal_plsh_msg").html(t.message)}}.bind(this))},used:function $(t){var e=[];var a=0;var i=true;var s=false;var r=undefined;try{for(var l=(0,d.default)(window["dataTable"]["table"].bootstrapTable("getData")),u;!(i=(u=l.next()).done);i=true){var f=u.value;console.log(f);if(!f["ischeck"]){continue}e.push(f["id"]);a++}}catch(c){s=true;r=c}finally{try{if(!i&&l.return){l.return()}}finally{if(s){throw r}}}if(a===0){window.Alert("请先选择要进行设置无效的记录前的复选框");return}t=e.join(",");this.dialogWaiting({title:"操作",message:'<div id="modal_plsh_msg">正在处理，请稍候...</div>'},{url:"/jdks/user/deleted",data:(0,n.default)({ids:t,used:"1"})},function(t){if(t.status==="success"){(0,o.default)("#modal_plsh_msg").html("操作成功");this.dataHandle()}else{(0,o.default)("#modal_plsh_msg").html(t.message)}}.bind(this))},use:function D(t){var e=[];var a=0;var i=true;var s=false;var r=undefined;try{for(var l=(0,d.default)(window["dataTable"]["table"].bootstrapTable("getData")),u;!(i=(u=l.next()).done);i=true){var f=u.value;console.log(f);if(!f["ischeck"]){continue}e.push(f["id"]);a++}}catch(c){s=true;r=c}finally{try{if(!i&&l.return){l.return()}}finally{if(s){throw r}}}if(a===0){window.Alert("请先选择要进行设置有效的记录前的复选框");return}t=e.join(",");this.dialogWaiting({title:"操作",message:'<div id="modal_plsh_msg">正在处理，请稍候...</div>'},{url:"/jdks/user/deleted",data:(0,n.default)({ids:t,used:"0"})},function(t){if(t.status==="success"){(0,o.default)("#modal_plsh_msg").html("操作成功");this.dataHandle()}else{(0,o.default)("#modal_plsh_msg").html(t.message)}}.bind(this))}},ready:function E(){this.pageNav();this.dataTableInit({ajax:""});setTimeout(function(){var t=A.default.get("customer_param",true);if(t["condition"]){var e=t["condition"][0];A.default.set("customer_param",{});(0,o.default)("#search_bar [data-filter-id=ksccid]").selectpicker("val",e["searchvalue"]);(0,o.default)("#search_bar [data-filter-id=ksccid]").selectpicker("refresh");this.search();(0,o.default)("#search_bar [data-filter-id=ksccid]").selectpicker("val",e["searchvalue"]);(0,o.default)("#search_bar [data-filter-id=ksccid]").selectpicker("refresh")}else{this.search()}}.bind(this),1e3)},components:{Searchbar:c.default,Va:M.default}}},493:function(t,e,a){"use strict";var i=a(4);var d=o(i);var s=a(1383);var n=o(s);var r=a(6);function o(t){return t&&t.__esModule?t:{"default":t}}window.__pageInit=function(){r.km.init(function(){window.app=new d.default({el:"body",components:{App:n.default}})})}},494:function(t,e,a){"use strict";var i=a(5);var d=r(i);var s=a(3);var n=r(s);function r(t){return t&&t.__esModule?t:{"default":t}}t.exports={data:function o(){var t={ksxtxm:{m:1,type:"text",show:false,disable:0},ksxtsfzjh:{m:1,type:"text",show:false,disable:0},ksxtzkzh:{m:1,type:"text",show:false,disable:0},ksccid:{m:1,type:"select",show:false,disable:0,ready:function(t,e){window.post({url:"/jdks/ksccmg/list"},function(e){console.error(e);var a=(0,n.default)(this.selector+" [name=ksccid]");a.find("option").remove();a.append('<option value="">请选择</option>');var i=true;var s=false;var r=undefined;try{for(var o=(0,d.default)(e.data),l;!(i=(l=o.next()).done);i=true){var u=l.value;a.append('<option value="'+u.id+'">'+u.kscc+"</option>")}}catch(f){s=true;r=f}finally{try{if(!i&&o.return){o.return()}}finally{if(s){throw r}}}if(this.entity[t]){a.selectpicker("val",this.entity[t])}a.selectpicker("refresh")}.bind(this));e(null,"")}.bind(this)}};var e={};for(var a in t){e[a]=""}return{entity:e,validateConfig:t,selector:"#modal_add"}}}},495:function(t,e){"use strict";t.exports={data:function a(){var t={llksfs:{m:1,type:"text",show:false,disable:0},scksfs:{m:1,type:"text",show:false,disable:0}};var e={};for(var a in t){e[a]=""}return{entity:e,validateConfig:t,selector:"#modal_edit"}}}},771:function(t,e){},772:function(t,e){},773:function(t,e){},774:function(t,e){},775:function(t,e){},776:function(t,e){},777:function(t,e){},1079:function(t,e){t.exports=" <div id=app class=appv> <navbar></navbar> <topmenu :current-menu=currentMenu :current-submenu=currentSubmenu :current-parentmenu=currentParentmenu></topmenu> <mainarea v-ref:mainarea></mainarea> <helpbar></helpbar> </div> "},1080:function(t,e){t.exports=" <div class=mainarea> <component :is=currentView></component> </div> "},1081:function(t,e){t.exports=" <div> <div class=d-tbl> <vf field=ksccid label=考试场次 data-dict=bz_role></vf> <hr> <vf field=ksxtxm label=考生姓名 maxlength=20></vf> <vf field=ksxtsfzjh label=考生身份证件号 maxlength=20></vf> <vf field=ksxtzkzh label=考生准考证号 data-dict=bz_sex></vf> </div> </div> "},1082:function(t,e){t.exports=" <div> <div class=d-tbl> <vf field=llksfs label=理论考试分数 maxlength=20></vf> <vf field=scksfs label=实操考试分数 maxlength=20></vf> </div> </div> "},1083:function(t,e){t.exports=' <div style="margin-top:10px;padding:10px 0 5px 0;background-color:#f0f0f0"> <ol> <li>请点击 <a href=../resource/down/考生成绩录入.xlsx target=_blank>这里</a> 下载模板</li> <li>请严格按照模板中的导入要求进行数据的输入</li> </ol> </div> <div class=i> <div style="padding:10px 0 0 0">上传文件</div> <span class=t id=d_file> <div class=d-upload> <div class=up> <div id=d_upload_attachment name=attachment></div> </div> <div class="uploadholder uploadholder-attachment ua-normal"> <div class=icon></div> <div class=pro></div> </div> <div>文件大小不超过 20M，文件类型为 xlsx</div> </div> </span> </div> <div style="padding:18px 0">处理结果</div> <div id=d_result class=d-result></div> '},1084:function(t,e){t.exports=" <div> <div class=d-tbl> <vf mode=view field=kscc label=考试场次></vf> <hr> <vf mode=view field=ksxtxm label=考生姓名 maxlength=20></vf> <vf mode=view field=ksxtsfzjh label=考生身份证件号 maxlength=20></vf> <vf mode=view field=ksxtzkzh label=考生准考证号 data-dict=bz_sex></vf> </div> </div> "},1085:function(t,e){t.exports=' <div class="pagetitle clearfix"> <div class=t>成绩管理 / 学生成绩录入</div> <div class=a></div> </div> <div class=pagebody> <div class=row> <div class=col-sm-12> <searchbar v-ref:searchbar :searchbar=searchbarId :searchbar-col=searchbarCol :searchbar-fields=searchbarFields></searchbar> </div> </div> <div class=row> <div class=col-sm-12> <div id=toolbar class=btn-toolbar> <va></va> </div> <table id=table class=tbl></table> </div> </div> </div> '},1383:function(t,e,a){var i,d;a(771);i=a(307);d=a(1079);t.exports=i||{};if(t.exports.__esModule)t.exports=t.exports.default;if(d){(typeof t.exports==="function"?t.exports.options||(t.exports.options={}):t.exports).template=d}},1384:function(t,e,a){var i,d;a(772);i=a(308);d=a(1080);t.exports=i||{};if(t.exports.__esModule)t.exports=t.exports.default;if(d){(typeof t.exports==="function"?t.exports.options||(t.exports.options={}):t.exports).template=d}},1385:function(t,e,a){var i,d;a(773);i=a(309);d=a(1081);t.exports=i||{};if(t.exports.__esModule)t.exports=t.exports.default;if(d){(typeof t.exports==="function"?t.exports.options||(t.exports.options={}):t.exports).template=d}},1386:function(t,e,a){var i,d;a(774);i=a(310);d=a(1082);t.exports=i||{};if(t.exports.__esModule)t.exports=t.exports.default;if(d){(typeof t.exports==="function"?t.exports.options||(t.exports.options={}):t.exports).template=d}},1387:function(t,e,a){var i,d;a(775);i=a(311);d=a(1083);t.exports=i||{};if(t.exports.__esModule)t.exports=t.exports.default;if(d){(typeof t.exports==="function"?t.exports.options||(t.exports.options={}):t.exports).template=d}},1388:function(t,e,a){var i,d;a(776);i=a(312);d=a(1084);t.exports=i||{};if(t.exports.__esModule)t.exports=t.exports.default;if(d){(typeof t.exports==="function"?t.exports.options||(t.exports.options={}):t.exports).template=d}},1389:function(t,e,a){var i,d;a(777);i=a(313);d=a(1085);t.exports=i||{};if(t.exports.__esModule)t.exports=t.exports.default;if(d){(typeof t.exports==="function"?t.exports.options||(t.exports.options={}):t.exports).template=d}}});