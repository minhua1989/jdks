webpackJsonp([19],{0:function(e,t,a){e.exports=a(487)},77:function(e,t,a){var i,s;a(760);i=a(294);s=a(1063);e.exports=i||{};if(e.exports.__esModule)e.exports=e.exports.default;if(s){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=s}},78:function(e,t,a){var i,s;a(761);i=a(295);s=a(1064);e.exports=i||{};if(e.exports.__esModule)e.exports=e.exports.default;if(s){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=s}},79:function(e,t,a){var i,s;a(762);i=a(296);s=a(1065);e.exports=i||{};if(e.exports.__esModule)e.exports=e.exports.default;if(s){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=s}},292:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:true});var i=a(8);var s=r(i);var d=a(9);var l=r(d);var n=a(1368);var o=r(n);function r(e){return e&&e.__esModule?e:{"default":e}}t.default={data:function u(){return{currentMenu:"questionManage",currentSubmenu:"questionsList1"}},components:{Navbar:s.default,Topmenu:l.default,Mainarea:o.default}}},293:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:true});var i=a(1369);var s=f(i);var d=a(77);var l=f(d);var n=a(78);var o=f(n);var r=a(79);var u=f(r);function f(e){return e&&e.__esModule?e:{"default":e}}t.default={data:function c(){return{currentView:"PxDefault",id:""}},events:{reload:"reload"},methods:{reload:function v(){this.currentView="PxDefault";window.app.$broadcast("reloadList")}},components:{PxDefault:s.default,MxAdd:l.default,MxEdit:o.default,MxView:u.default},ready:function p(){}}},294:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:true});var i=a(1);var s=r(i);var d=a(2);var l=r(d);var n=a(488);var o=r(n);function r(e){return e&&e.__esModule?e:{"default":e}}t.default={editmode:"add",mixins:[o.default],events:{doAdd:"doAdd"},methods:{doAdd:function u(e){this.validateAll(function(){this.entity.pwd="ceshi123";window.post({url:"/jdks/question/addtk",data:(0,s.default)(this.entity)},function(t){if(t.status==="success"){e.close();window.app.$broadcast("reloadList");window.Alert("操作成功")}else{window.Alert(t.message)}})}.bind(this))}},ready:function f(){this.beforeInit();this.$nextTick(function(){this.afterInit();this.validateInit()})},components:{Vf:l.default}}},295:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:true});var i=a(1);var s=f(i);var d=a(2);var l=f(d);var n=a(489);var o=f(n);var r=a(7);var u=f(r);function f(e){return e&&e.__esModule?e:{"default":e}}t.default={editmode:"edit",mixins:[o.default],events:{doEdit:"doEdit"},props:["id"],methods:{doEdit:function c(e,t){this.validateAll(function(){this.entity.pwd="ceshi123";window.post({url:"/jdks/question/edittk",data:(0,s.default)(this.entity)},function(e){if(e.status==="success"){t.close();window.app.$broadcast("reloadList");window.Alert("操作成功")}else{window.Alert(e.message)}})}.bind(this))}},ready:function v(){u.default.series([function(e){this.beforeInit();e(null,"")}.bind(this),function(e){window.post({url:"/jdks/question/searchOnetk",data:(0,s.default)({id:this.id})},function(t){this.dataPreHandle(t);e(null,"")}.bind(this))}.bind(this)],function(e,t){if(e){window.winLog(e)}this.$nextTick(function(){this.afterInit();this.validateInit()})}.bind(this))},components:{Vf:l.default}}},296:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:true});var i=a(1);var s=n(i);var d=a(2);var l=n(d);function n(e){return e&&e.__esModule?e:{"default":e}}t.default={data:function o(){return{entity:{}}},props:["id"],ready:function r(){window.post({url:"/jdks/question/searchOnetk",data:(0,s.default)({id:this.id})},function(e){this.entity=e.data}.bind(this))},components:{Vf:l.default}}},297:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:true});var i=a(5);var s=j(i);var d=a(1);var l=j(d);var n=a(11);var o=j(n);var r=a(3);var u=j(r);var f=a(10);var c=j(f);var v=a(13);var p=j(v);var m=a(4);var b=j(m);var h=a(77);var x=j(h);var w=a(79);var _=j(w);var y=a(78);var g=j(y);var M=a(12);var k=j(M);function j(e){return e&&e.__esModule?e:{"default":e}}t.default={data:function A(){var e=window.__global.user.roleid;var t=(0,c.default)(["0","1","2","3","4"],"questionsList1_table",e,this);if(!t){return{}}return t},events:{add:"add",edit:"edit",del:"del",view:"view",used:"used",use:"use",list:"list",reloadList:"reloadList"},methods:{reloadList:function L(){this.dataHandle()},view:function P(e){var t=b.default.extend({data:function a(){return{id:e}},template:'<mx-view :id="id"></mx-view>',components:{MxView:_.default}});this.dialogModal({did:"modal_view",title:"查看",cssClass:"big-dialog-umeditor",buttons:[{label:"关闭",action:function i(e){e.close()}}]},t)},add:function q(){var e=b.default.extend({template:"<mx-add></mx-add>",components:{MxAdd:x.default}});this.dialogModal({did:"modal_add",title:"添加",cssClass:"big-dialog-umeditor",buttons:[{label:"提交",cssClass:"btn-primary",action:function t(e){window.app.$broadcast("doAdd",e)}},{label:"关闭",action:function a(e){e.close()}}]},e)},edit:function T(e){var t=b.default.extend({data:function a(){return{id:e}},template:'<mx-edit :id="id"></mx-edit>',components:{MxEdit:g.default}});this.dialogModal({did:"modal_edit",title:"修改",cssClass:"big-dialog-umeditor",buttons:[{label:"提交",cssClass:"btn-primary",action:function i(t){window.app.$broadcast("doEdit",e,t)}},{label:"关闭",action:function s(e){e.close()}}]},t)},del:function z(e){var t=[];var a=0;var i=true;var d=false;var n=undefined;try{for(var o=(0,s.default)(window["dataTable"]["table"].bootstrapTable("getData")),r;!(i=(r=o.next()).done);i=true){var f=r.value;console.log(f);if(!f["ischeck"]){continue}t.push(f["id"]);a++}}catch(c){d=true;n=c}finally{try{if(!i&&o.return){o.return()}}finally{if(d){throw n}}}if(a===0){window.Alert("请先选择要进行删除的记录前的复选框");return}e=t.join(",");this.dialogWaiting({title:"操作",message:'<div id="modal_plsh_msg">正在处理，请稍候...</div>'},{url:"/jdks/admin/wulideleted",data:(0,l.default)({ids:e})},function(e){if(e.status==="success"){(0,u.default)("#modal_plsh_msg").html("操作成功");this.dataHandle()}else{(0,u.default)("#modal_plsh_msg").html(e.message)}}.bind(this))},used:function I(e){var t=[];var a=0;var i=true;var d=false;var n=undefined;try{for(var o=(0,s.default)(window["dataTable"]["table"].bootstrapTable("getData")),r;!(i=(r=o.next()).done);i=true){var f=r.value;console.log(f);if(!f["ischeck"]){continue}t.push(f["id"]);a++}}catch(c){d=true;n=c}finally{try{if(!i&&o.return){o.return()}}finally{if(d){throw n}}}if(a===0){window.Alert("请先选择要进行设置无效的记录前的复选框");return}e=t.join(",");this.dialogWaiting({title:"操作",message:'<div id="modal_plsh_msg">正在处理，请稍候...</div>'},{url:"/jdks/admin/deleted",data:(0,l.default)({ids:e,used:"1"})},function(e){if(e.status==="success"){(0,u.default)("#modal_plsh_msg").html("操作成功");this.dataHandle()}else{(0,u.default)("#modal_plsh_msg").html(e.message)}}.bind(this))},use:function V(e){var t=[];var a=0;var i=true;var d=false;var n=undefined;try{for(var o=(0,s.default)(window["dataTable"]["table"].bootstrapTable("getData")),r;!(i=(r=o.next()).done);i=true){var f=r.value;console.log(f);if(!f["ischeck"]){continue}t.push(f["id"]);a++}}catch(c){d=true;n=c}finally{try{if(!i&&o.return){o.return()}}finally{if(d){throw n}}}if(a===0){window.Alert("请先选择要进行设置有效的记录前的复选框");return}e=t.join(",");this.dialogWaiting({title:"操作",message:'<div id="modal_plsh_msg">正在处理，请稍候...</div>'},{url:"/jdks/admin/deleted",data:(0,l.default)({ids:e,used:"0"})},function(e){if(e.status==="success"){(0,u.default)("#modal_plsh_msg").html("操作成功");this.dataHandle()}else{(0,u.default)("#modal_plsh_msg").html(e.message)}}.bind(this))},list:function C(e,t){var a="questionsList";var i={page:"questionManage-questionsList",condition:[{type:"select",searchbarid:"stlx",searchvalue:e},{type:"select",searchbarid:"sydj",searchvalue:t}]};o.default.set("searchbar_param",i);window.open(a+".html")}},ready:function O(){this.pageNav();this.dataTableInit()},components:{Searchbar:p.default,Va:k.default}}},487:function(e,t,a){"use strict";var i=a(4);var s=o(i);var d=a(1367);var l=o(d);var n=a(6);function o(e){return e&&e.__esModule?e:{"default":e}}window.__pageInit=function(){n.km.init(function(){window.app=new s.default({el:"body",components:{App:l.default}})})}},488:function(e,t){"use strict";e.exports={data:function a(){var e={tkname:{m:1,type:"text",show:false,disabled:false},kslx:{m:1,type:"select",show:false,disabled:false},sydj:{m:1,type:"select",show:false,disabled:false},stlx:{m:1,type:"select",show:false,disabled:false},allnum:{m:1,type:"text",show:false,disabled:false},fenshu:{m:1,type:"text",show:false,disabled:false},outnum:{m:1,type:"text",show:false,disabled:false}};var t={};for(var a in e){t[a]=""}return{entity:t,validateConfig:e,selector:"#modal_add"}}}},489:function(e,t){"use strict";e.exports={data:function a(){var e={tkname:{m:1,type:"text",show:false,disabled:false},kslx:{m:1,type:"select",show:false,disabled:false},sydj:{m:1,type:"select",show:false,disabled:false},stlx:{m:1,type:"select",show:false,disabled:false},allnum:{m:1,type:"text",show:false,disabled:false},fenshu:{m:1,type:"text",show:false,disabled:false},outnum:{m:1,type:"text",show:false,disabled:false}};var t={};for(var a in e){t[a]=""}return{entity:t,validateConfig:e,selector:"#modal_edit"}}}},758:function(e,t){},759:function(e,t){},760:function(e,t){},761:function(e,t){},762:function(e,t){},763:function(e,t){},1061:function(e,t){e.exports=" <div id=app class=appv> <navbar></navbar> <topmenu :current-menu=currentMenu :current-submenu=currentSubmenu :current-parentmenu=currentParentmenu></topmenu> <mainarea v-ref:mainarea></mainarea> <helpbar></helpbar> </div> "},1062:function(e,t){e.exports=" <div class=mainarea> <component :is=currentView :id=id></component> </div> "},1063:function(e,t){e.exports=" <div> <div class=d-tbl> <vf field=tkname label=题库名称 maxlength=20></vf> <vf field=kslx label=考试类型 maxlength=20 data-dict=bz_kslx></vf> <vf field=sydj label=属于等级 data-dict=bz_sydj></vf> <vf field=stlx label=试题类型 data-dict=bz_stlx></vf> <vf field=allnum label=总条数></vf> <vf field=fenshu label=每题分数></vf> <vf field=outnum label=出题条数 maxlength=20></vf> </div> </div> "},1064:function(e,t){e.exports=" <div> <div class=d-tbl> <vf field=tkname label=题库名称 maxlength=20></vf> <vf field=kslx label=考试类型 maxlength=20 data-dict=bz_kslx></vf> <vf field=sydj label=属于等级 data-dict=bz_sydj></vf> <vf field=stlx label=试题类型 data-dict=bz_stlx></vf> <vf field=allnum label=总条数></vf> <vf field=fenshu label=每题分数></vf> <vf field=outnum label=出题条数 maxlength=20></vf> </div> </div> "},1065:function(e,t){e.exports=" <div> <div class=d-tbl> <vf mode=view field=tkname label=题库名称 maxlength=20></vf> <vf mode=view field=kslx label=考试类型 maxlength=20 data-dict=bz_kslx></vf> <vf mode=view field=sydj label=属于等级 data-dict=bz_sydj></vf> <vf mode=view field=stlx label=试题类型 data-dict=bz_stlx></vf> <vf mode=view field=allnum label=总条数></vf> <vf mode=view field=fenshu label=每题分数></vf> <vf mode=view field=outnum label=出题条数 maxlength=20></vf> </div> </div> "},1066:function(e,t){e.exports=' <div class="pagetitle clearfix"> <div class=t>题库管理 / 题库类别</div> <div class=a></div> </div> <div class=pagebody> <div class=row> <div class=col-sm-12> <searchbar v-ref:searchbar :searchbar=searchbarId :searchbar-col=searchbarCol :searchbar-fields=searchbarFields></searchbar> </div> </div> <div class=row> <div class=col-sm-12> <div id=toolbar class=btn-toolbar> <va></va> </div> <table id=table class=tbl></table> </div> </div> </div> '},1367:function(e,t,a){var i,s;a(758);i=a(292);s=a(1061);e.exports=i||{};if(e.exports.__esModule)e.exports=e.exports.default;if(s){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=s}},1368:function(e,t,a){var i,s;a(759);i=a(293);s=a(1062);e.exports=i||{};if(e.exports.__esModule)e.exports=e.exports.default;if(s){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=s}},1369:function(e,t,a){var i,s;a(763);i=a(297);s=a(1066);e.exports=i||{};if(e.exports.__esModule)e.exports=e.exports.default;if(s){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=s}}});