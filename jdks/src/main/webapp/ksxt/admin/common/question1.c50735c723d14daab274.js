webpackJsonp([9],{0:function(t,e,a){t.exports=a(483)},283:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:true});var i=a(8);var d=r(i);var n=a(9);var o=r(n);var s=a(1358);var l=r(s);function r(t){return t&&t.__esModule?t:{"default":t}}e.default={data:function u(){return{currentMenu:"questionManage",currentSubmenu:"question1"}},components:{Navbar:d.default,Topmenu:o.default,Mainarea:l.default}}},284:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:true});var i=a(1363);var d=n(i);function n(t){return t&&t.__esModule?t:{"default":t}}e.default={data:function o(){return{currentView:"PxDefault"}},components:{PxDefault:d.default},ready:function s(){}}},285:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:true});var i=a(1);var d=r(i);var n=a(2);var o=r(n);var s=a(484);var l=r(s);function r(t){return t&&t.__esModule?t:{"default":t}}e.default={editmode:"add",mixins:[l.default],events:{doAdd:"doAdd"},methods:{doAdd:function u(t){this.validateAll(function(){window.post({url:"/jdks/user/add",data:(0,d.default)(this.entity)},function(e){if(e.status==="success"){t.close();window.app.$broadcast("reloadList");window.Alert("操作成功")}else{window.Alert(e.message)}})}.bind(this))}},ready:function f(){this.beforeInit();this.$nextTick(function(){this.afterInit();this.validateInit()})},components:{Vf:o.default}}},286:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:true});var i=a(1);var d=f(i);var n=a(2);var o=f(n);var s=a(485);var l=f(s);var r=a(7);var u=f(r);function f(t){return t&&t.__esModule?t:{"default":t}}e.default={editmode:"edit",mixins:[l.default],events:{doEdit:"doEdit"},props:["id"],methods:{doEdit:function c(t,e){console.log(this.entity);this.validateAll(function(){window.post({url:"/jdks/user/edit",data:(0,d.default)(this.entity)},function(t){if(t.status==="success"){e.close();window.app.$broadcast("reloadList");window.Alert("操作成功")}else{window.Alert(t.message)}})}.bind(this))}},ready:function v(){u.default.series([function(t){this.beforeInit();t(null,"")}.bind(this),function(t){window.post({url:"/jdks/user/searchOne",data:(0,d.default)({id:this.id})},function(e){this.dataPreHandle(e);this.entity.oldksccid=this.entity.ksccid;t(null,"")}.bind(this))}.bind(this)],function(t,e){if(t){window.winLog(t)}this.$nextTick(function(){this.afterInit();this.validateInit()})}.bind(this))},components:{Vf:o.default}}},287:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:true});var i=a(3);var d=n(i);function n(t){return t&&t.__esModule?t:{"default":t}}e.default={data:function o(){return{entity:{fileid:"",fileurl:""}}},methods:{initUpload:function s(){try{window["km_upload"].uList["attachment"].destroy()}catch(t){}window.__global.checkquit="";var e='\n        <div class="d-upload">\n          <div class="up">\n            <div id="d_upload_attachment" name="attachment"></div>\n          </div>\n          <div class="uploadholder uploadholder-attachment ua-normal">\n            <div class="icon"></div>\n            <div class="pro"></div>\n          </div>\n          <div>文件大小不超过 20M，文件类型为 xlsx</div>\n        </div>\n      ';(0,d.default)("#d_file").html(e);window["funcUploadFile"]("attachment",".uploadholder-attachment","/jdks/user/importUsers","*.xlsx",1024*20,1,function(t){if(typeof t==="string"){t=JSON.parse(t)}if(t["data"]){this.entity.fileid=t.data.fileid;this.entity.fileurl=t.data.fileurl}var e="";e+=t.message;if(t.status==="success"){if(t.data["url1"]){e+='<br><br><a href="'+t.data["url1"]+'" target="_blank">点击下载查看操作成功的数据</a>'}if(t.data["url2"]){e+='<br><br><a href="'+t.data["url2"]+'" target="_blank">点击下载查看操作失败的数据</a>'}if(t.data["url"]){e+='<br><br><a href="'+t.data["url"]+'" target="_blank">操作结果</a>'}}(0,d.default)("#d_result").html(e);setTimeout(function(){this.initUpload()}.bind(this),1e3)}.bind(this))}},ready:function l(){window["loadUpload"](function(){this.initUpload()}.bind(this))}}},288:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:true});var i=a(1);var d=s(i);var n=a(2);var o=s(n);function s(t){return t&&t.__esModule?t:{"default":t}}e.default={data:function l(){return{entity:{}}},props:["id"],ready:function r(){window.post({url:"/jdks/user/searchOne",data:(0,d.default)({id:this.id})},function(t){this.entity=t.data}.bind(this))},components:{Vf:o.default}}},289:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:true});var i=a(5);var d=j(i);var n=a(1);var o=j(n);var s=a(3);var l=j(s);var r=a(10);var u=j(r);var f=a(13);var c=j(f);var v=a(4);var p=j(v);var m=a(1359);var x=j(m);var h=a(1362);var b=j(h);var _=a(1360);var w=j(_);var g=a(1361);var y=j(g);var M=a(12);var k=j(M);function j(t){return t&&t.__esModule?t:{"default":t}}e.default={data:function A(){var t=window.__global.user.roleid;var e=(0,u.default)(["0","1","2","3","4"],"question1_table",t,this);if(!e){return{}}return e},events:{add:"add",edit:"edit",del:"del",view:"view",used:"used",use:"use","import":"import",reloadList:"reloadList"},methods:{reloadList:function P(){this.dataHandle()},"import":function T(){if(!this.preventDoubleClick()){return}var t=p.default.extend({template:"<mx-import></mx-import>",components:{MxImport:y.default}});this.dialogModal({did:"modal_import",title:"导入成绩",draggable:true,closable:false,closeByBackdrop:false,closeByKeyboard:false,buttons:[{label:"关闭",action:function e(t){t.close();window.app.$broadcast("reloadList")}}]},t)},view:function O(t){var e=p.default.extend({data:function a(){return{id:t}},template:'<mx-view :id="id"></mx-view>',components:{MxView:b.default}});this.dialogModal({did:"modal_view",title:"查看",cssClass:"big-dialog-umeditor",buttons:[{label:"关闭",action:function i(t){t.close()}}]},e)},add:function I(){var t=p.default.extend({template:"<mx-add></mx-add>",components:{MxAdd:x.default}});this.dialogModal({did:"modal_add",title:"添加",cssClass:"big-dialog-umeditor",buttons:[{label:"提交",cssClass:"btn-primary",action:function e(t){window.app.$broadcast("doAdd",t)}},{label:"关闭",action:function a(t){t.close()}}]},t)},edit:function C(t){var e=p.default.extend({data:function a(){return{id:t}},template:'<mx-edit :id="id"></mx-edit>',components:{MxEdit:w.default}});this.dialogModal({did:"modal_edit",title:"修改",cssClass:"big-dialog-umeditor",buttons:[{label:"提交",cssClass:"btn-primary",action:function i(e){window.app.$broadcast("doEdit",t,e)}},{label:"关闭",action:function d(t){t.close()}}]},e)},del:function L(t){var e=[];var a=0;var i=true;var n=false;var s=undefined;try{for(var r=(0,d.default)(window["dataTable"]["table"].bootstrapTable("getData")),u;!(i=(u=r.next()).done);i=true){var f=u.value;console.log(f);if(!f["ischeck"]){continue}e.push(f["id"]);a++}}catch(c){n=true;s=c}finally{try{if(!i&&r.return){r.return()}}finally{if(n){throw s}}}if(a===0){window.Alert("请先选择要进行删除的记录前的复选框");return}t=e.join(",");this.dialogWaiting({title:"操作",message:'<div id="modal_plsh_msg">正在处理，请稍候...</div>'},{url:"/jdks/user/wulideleted",data:(0,o.default)({ids:t})},function(t){if(t.status==="success"){(0,l.default)("#modal_plsh_msg").html("操作成功");this.dataHandle()}else{(0,l.default)("#modal_plsh_msg").html(t.message)}}.bind(this))},used:function V(t){var e=[];var a=0;var i=true;var n=false;var s=undefined;try{for(var r=(0,d.default)(window["dataTable"]["table"].bootstrapTable("getData")),u;!(i=(u=r.next()).done);i=true){var f=u.value;console.log(f);if(!f["ischeck"]){continue}e.push(f["id"]);a++}}catch(c){n=true;s=c}finally{try{if(!i&&r.return){r.return()}}finally{if(n){throw s}}}if(a===0){window.Alert("请先选择要进行设置无效的记录前的复选框");return}t=e.join(",");this.dialogWaiting({title:"操作",message:'<div id="modal_plsh_msg">正在处理，请稍候...</div>'},{url:"/jdks/user/deleted",data:(0,o.default)({ids:t,used:"1"})},function(t){if(t.status==="success"){(0,l.default)("#modal_plsh_msg").html("操作成功");this.dataHandle()}else{(0,l.default)("#modal_plsh_msg").html(t.message)}}.bind(this))},use:function $(t){var e=[];var a=0;var i=true;var n=false;var s=undefined;try{for(var r=(0,d.default)(window["dataTable"]["table"].bootstrapTable("getData")),u;!(i=(u=r.next()).done);i=true){var f=u.value;console.log(f);if(!f["ischeck"]){continue}e.push(f["id"]);a++}}catch(c){n=true;s=c}finally{try{if(!i&&r.return){r.return()}}finally{if(n){throw s}}}if(a===0){window.Alert("请先选择要进行设置有效的记录前的复选框");return}t=e.join(",");this.dialogWaiting({title:"操作",message:'<div id="modal_plsh_msg">正在处理，请稍候...</div>'},{url:"/jdks/user/deleted",data:(0,o.default)({ids:t,used:"0"})},function(t){if(t.status==="success"){(0,l.default)("#modal_plsh_msg").html("操作成功");this.dataHandle()}else{(0,l.default)("#modal_plsh_msg").html(t.message)}}.bind(this))}},ready:function D(){this.pageNav();this.dataTableInit()},components:{Searchbar:c.default,Va:k.default}}},483:function(t,e,a){"use strict";var i=a(4);var d=l(i);var n=a(1357);var o=l(n);var s=a(6);function l(t){return t&&t.__esModule?t:{"default":t}}window.__pageInit=function(){s.km.init(function(){window.app=new d.default({el:"body",components:{App:o.default}})})}},484:function(t,e){"use strict";t.exports={data:function a(){var t={tklb:{m:1,type:"text",show:false,disable:0},tmlx:{m:1,type:"text",show:false,disable:0}};var e={};for(var a in t){e[a]=""}return{entity:e,validateConfig:t,selector:"#modal_add"}}}},485:function(t,e){"use strict";t.exports={data:function a(){var t={tklb:{m:1,type:"text",show:false,disable:0},tmlx:{m:1,type:"text",show:false,disable:0}};var e={};for(var a in t){e[a]=""}return{entity:e,validateConfig:t,selector:"#modal_edit"}}}},749:function(t,e){},750:function(t,e){},751:function(t,e){},752:function(t,e){},753:function(t,e){},754:function(t,e){},755:function(t,e){},1051:function(t,e){t.exports=" <div id=app class=appv> <navbar></navbar> <topmenu :current-menu=currentMenu :current-submenu=currentSubmenu :current-parentmenu=currentParentmenu></topmenu> <mainarea v-ref:mainarea></mainarea> <helpbar></helpbar> </div> "},1052:function(t,e){t.exports=" <div class=mainarea> <component :is=currentView></component> </div> "},1053:function(t,e){t.exports=" <div> <div class=d-tbl> <vf field=tklb label=题目名称 maxlength=20></vf> <vf field=tmlx label=题目类型 maxlength=20></vf> </div> </div> "},1054:function(t,e){t.exports=" <div> <div class=d-tbl> <vf field=tklb label=题目名称 maxlength=20></vf> <vf field=tmlx label=题目类型 maxlength=20></vf> </div> </div> "},1055:function(t,e){t.exports=' <div style="margin-top:10px;padding:10px 0 5px 0;background-color:#f0f0f0"> <ol> <li>请点击 <a href=../resource/down/新手报名登记.xlsx target=_blank>这里</a> 下载模板</li> <li>请严格按照模板中的导入要求进行数据的输入</li> </ol> </div> <div class=i> <div style="padding:10px 0 0 0">上传文件</div> <span class=t id=d_file> <div class=d-upload> <div class=up> <div id=d_upload_attachment name=attachment></div> </div> <div class="uploadholder uploadholder-attachment ua-normal"> <div class=icon></div> <div class=pro></div> </div> <div>文件大小不超过 20M，文件类型为 xlsx</div> </div> </span> </div> <div style="padding:18px 0">处理结果</div> <div id=d_result class=d-result></div> '},1056:function(t,e){t.exports=" <div> <div class=d-tbl> <vf mode=view field=tklb label=题目名称 maxlength=20></vf> <vf mode=view field=tmlx label=题目类型 maxlength=20></vf> </div> </div> "},1057:function(t,e){t.exports=' <div class="pagetitle clearfix"> <div class=t></div> <div class=a></div> </div> <div class=pagebody> <div class=row> <div class=col-sm-12> </div> </div> <div class=row> <div class=col-sm-12> <div id=toolbar class=btn-toolbar> <va></va> </div> <table id=table class=tbl></table> </div> </div> </div> '},1357:function(t,e,a){var i,d;a(749);i=a(283);d=a(1051);t.exports=i||{};if(t.exports.__esModule)t.exports=t.exports.default;if(d){(typeof t.exports==="function"?t.exports.options||(t.exports.options={}):t.exports).template=d}},1358:function(t,e,a){var i,d;a(750);i=a(284);d=a(1052);t.exports=i||{};if(t.exports.__esModule)t.exports=t.exports.default;if(d){(typeof t.exports==="function"?t.exports.options||(t.exports.options={}):t.exports).template=d}},1359:function(t,e,a){var i,d;a(751);i=a(285);d=a(1053);t.exports=i||{};if(t.exports.__esModule)t.exports=t.exports.default;if(d){(typeof t.exports==="function"?t.exports.options||(t.exports.options={}):t.exports).template=d}},1360:function(t,e,a){var i,d;a(752);i=a(286);d=a(1054);t.exports=i||{};if(t.exports.__esModule)t.exports=t.exports.default;if(d){(typeof t.exports==="function"?t.exports.options||(t.exports.options={}):t.exports).template=d}},1361:function(t,e,a){var i,d;a(753);i=a(287);d=a(1055);t.exports=i||{};if(t.exports.__esModule)t.exports=t.exports.default;if(d){(typeof t.exports==="function"?t.exports.options||(t.exports.options={}):t.exports).template=d}},1362:function(t,e,a){var i,d;a(754);i=a(288);d=a(1056);t.exports=i||{};if(t.exports.__esModule)t.exports=t.exports.default;if(d){(typeof t.exports==="function"?t.exports.options||(t.exports.options={}):t.exports).template=d}},1363:function(t,e,a){var i,d;a(755);i=a(289);d=a(1057);t.exports=i||{};if(t.exports.__esModule)t.exports=t.exports.default;if(d){(typeof t.exports==="function"?t.exports.options||(t.exports.options={}):t.exports).template=d}}});