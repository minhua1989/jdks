webpackJsonp([14],{0:function(e,t,a){e.exports=a(409)},116:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:true});var i=a(8);var s=r(i);var d=a(9);var l=r(d);var o=a(1191);var n=r(o);function r(e){return e&&e.__esModule?e:{"default":e}}t.default={data:function f(){return{currentMenu:"scoreManage",currentSubmenu:"exam12"}},components:{Navbar:s.default,Topmenu:l.default,Mainarea:n.default}}},117:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:true});var i=a(1196);var s=d(i);function d(e){return e&&e.__esModule?e:{"default":e}}t.default={data:function l(){return{currentView:"PxDefault"}},components:{PxDefault:s.default},ready:function o(){}}},118:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:true});var i=a(1);var s=r(i);var d=a(2);var l=r(d);var o=a(410);var n=r(o);function r(e){return e&&e.__esModule?e:{"default":e}}t.default={editmode:"add",mixins:[n.default],events:{doAdd:"doAdd"},methods:{doAdd:function f(e){this.validateAll(function(){window.post({url:"/jdks/ksccmg/add",data:(0,s.default)(this.entity)},function(t){if(t.status==="success"){e.close();window.app.$broadcast("reloadList");window.Alert("操作成功")}else{window.Alert(t.message)}})}.bind(this))}},ready:function c(){this.beforeInit();this.$nextTick(function(){this.validateInit()})},components:{Vf:l.default}}},119:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:true});var i=a(1);var s=c(i);var d=a(2);var l=c(d);var o=a(411);var n=c(o);var r=a(7);var f=c(r);function c(e){return e&&e.__esModule?e:{"default":e}}t.default={editmode:"edit",mixins:[n.default],events:{doEdit:"doEdit"},props:["id"],methods:{doEdit:function u(e,t){this.validateAll(function(){window.post({url:"/jdks/ksccmg/edit",data:(0,s.default)(this.entity)},function(e){if(e.status==="success"){t.close();window.app.$broadcast("reloadList");window.Alert("操作成功")}else{window.Alert(e.message)}})}.bind(this))}},ready:function v(){f.default.series([function(e){this.beforeInit();e(null,"")}.bind(this),function(e){window.post({url:"/jdks/ksccmg/searchOne",data:(0,s.default)({id:this.id})},function(t){this.dataPreHandle(t);e(null,"")}.bind(this))}.bind(this)],function(e,t){if(e){window.winLog(e)}this.$nextTick(function(){this.afterInit();this.validateInit()})}.bind(this))},components:{Vf:l.default}}},120:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:true});var i=a(1);var s=r(i);var d=a(2);var l=r(d);var o=a(7);var n=r(o);function r(e){return e&&e.__esModule?e:{"default":e}}t.default={editmode:"edit",data:function f(){var e={kcname:{m:0,type:"",show:false,disable:0},kcid:{m:1,type:"",show:false,disable:0},ksccid:{m:1,type:"",show:false,disable:0}};var t={};for(var a in e){t[a]=""}return{entity:t,validateConfig:e,selector:"#modal_edit",list:[],kcidsadd:[],kcidsrm:[],source:[]}},events:{doSet:"doSet"},props:["id","kcname"],methods:{doSet:function c(e){var t=this;this.list.forEach(function(e){if(e.checked){if(t.source.indexOf(e.id)===-1){t.kcidsadd.push(e.id)}}});console.log(this.source);this.list.forEach(function(e){if(!e.checked){if(t.source.indexOf(e.id)!==-1){t.kcidsrm.push(e.id)}}});var a={ksccid:this.id,kcidsadd:this.kcidsadd.toString(),kcidsrm:this.kcidsrm.toString()};window.post({url:"/jdks/ksccmg/addKcinfo",data:(0,s.default)(a)},function(t){if(t.status==="success"){e.close();window.app.$broadcast("reloadList");window.Alert("操作成功")}else{window.Alert(t.message)}})},itemClass:function u(e){return"l"+e.deep}},ready:function v(){n.default.series([function(e){this.beforeInit();this.entity.kcname=this.kcname;e(null,"")}.bind(this),function(e){window.post({url:"/jdks/kcmg/yxpageList"},function(t){var a=this;this.list=[];t["data"].forEach(function(e){a.list.push({id:e["id"],kcname:e["kcname"],checked:false})});console.log(t);e(null,"")}.bind(this))}.bind(this),function(e){window.post({url:"/jdks/ksccmg/pageListKcKscc",data:(0,s.default)({ksccid:this.id})},function(t){var a=this;t["data"].forEach(function(e){a.source.push(e.kcid)});console.log(this.source);this.list.forEach(function(e){console.log(e);if(a.source.indexOf(e.id)!==-1){e.checked=true}});console.log(this.list);e(null,"")}.bind(this))}.bind(this)],function(e,t){if(e){window.winLog(e)}this.$nextTick(function(){this.afterInit();this.validateInit()})}.bind(this))},components:{Vf:l.default}}},121:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:true});var i=a(1);var s=o(i);var d=a(2);var l=o(d);function o(e){return e&&e.__esModule?e:{"default":e}}t.default={data:function n(){return{entity:{}}},props:["id"],ready:function r(){window.post({url:"/jdks/ksccmg/searchOne",data:(0,s.default)({id:this.id})},function(e){this.entity=e.data}.bind(this))},components:{Vf:l.default}}},122:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:true});var i=a(5);var s=H(i);var d=a(1);var l=H(d);var o=a(3);var n=H(o);var r=a(10);var f=H(r);var c=a(13);var u=H(c);var v=a(4);var m=H(v);var p=a(1192);var h=H(p);var b=a(1195);var w=H(b);var x=a(1193);var k=H(x);var _=a(1194);var g=H(_);var y=a(12);var j=H(y);var M=a(11);var A=H(M);function H(e){return e&&e.__esModule?e:{"default":e}}t.default={data:function z(){var e=window.__global.user.roleid;var t=(0,f.default)(["0","1","2","3","4"],"exam12_table",e,this);if(!t){return{}}return t},events:{add:"add",edit:"edit",del:"del",view:"view",used:"used",use:"use",setkc:"setkc",reloadList:"reloadList",scksj:"scksj",sctzd:"sctzd",kscjsc:"kscjsc",viewstudentscore:"viewstudentscore"},methods:{viewstudentscore:function C(e,t){var a="score3";var i={page:"scoreManage-score3",condition:[{type:"select",searchbarid:"ksccid",searchvalue:e},{type:"select",searchbarid:"dj",searchvalue:t}]};if(!a){return}A.default.set("searchbar_param",i);window.location=a+".html"},kscjsc:function D(e){this.dialogWaiting({title:"操作",message:'<div id="modal_plsh_msg">正在处理，请稍候...</div>'},{url:"/jdks/kscj/cjhc",data:(0,l.default)({ksccid:e})},function(e){if(e.status==="success"){(0,n.default)("#modal_plsh_msg").html("操作成功");this.dataHandle()}else{(0,n.default)("#modal_plsh_msg").html(e.message)}}.bind(this))},sctzd:function O(e){this.dialogWaiting({title:"操作",message:'<div id="modal_plsh_msg">正在处理，请稍候...</div>'},{url:"/jdks/kcmg/createKSTZD",data:(0,l.default)({ksccid:e})},function(e){if(e.status==="success"){(0,n.default)("#modal_plsh_msg").html("操作成功");this.dataHandle()}else{(0,n.default)("#modal_plsh_msg").html(e.message)}}.bind(this))},scksj:function T(e){this.dialogWaiting({title:"操作",message:'<div id="modal_plsh_msg">正在处理，请稍候...</div>'},{url:"/jdks/kscc/createKssj",data:(0,l.default)({id:e})},function(e){if(e.status==="success"){(0,n.default)("#modal_plsh_msg").html("操作成功");this.dataHandle()}else{(0,n.default)("#modal_plsh_msg").html(e.message)}}.bind(this))},reloadList:function Y(){this.dataHandle()},view:function I(e){var t=m.default.extend({data:function a(){return{id:e}},template:'<mx-view :id="id"></mx-view>',components:{MxView:w.default}});this.dialogModal({did:"modal_view",title:"查看",cssClass:"big-dialog-umeditor",buttons:[{label:"关闭",action:function i(e){e.close()}}]},t)},add:function P(){var e=m.default.extend({template:"<mx-add></mx-add>",components:{MxAdd:h.default}});this.dialogModal({did:"modal_add",title:"添加",cssClass:"big-dialog-umeditor",buttons:[{label:"提交",cssClass:"btn-primary",action:function t(e){window.app.$broadcast("doAdd",e)}},{label:"关闭",action:function a(e){e.close()}}]},e)},edit:function E(e){var t=m.default.extend({data:function a(){return{id:e}},template:'<mx-edit :id="id"></mx-edit>',components:{MxEdit:k.default}});this.dialogModal({did:"modal_edit",title:"修改",cssClass:"big-dialog-umeditor",buttons:[{label:"提交",cssClass:"btn-primary",action:function i(t){window.app.$broadcast("doEdit",e,t)}},{label:"关闭",action:function s(e){e.close()}}]},t)},setkc:function L(e,t){var a=m.default.extend({data:function i(){return{id:e,kcname:t}},template:'<mx-kc :id="id" :kcname="kcname"></mx-kc>',components:{MxKc:g.default}});this.dialogModal({did:"modal_edit",title:"添加",cssClass:"big-dialog-umeditor",buttons:[{label:"确定",cssClass:"btn-primary",action:function s(e){window.app.$broadcast("doSet",e)}},{label:"关闭",action:function d(e){e.close()}}]},a)},del:function S(e){var t=[];var a=0;var i=true;var d=false;var o=undefined;try{for(var n=(0,s.default)(window["dataTable"]["table"].bootstrapTable("getData")),r;!(i=(r=n.next()).done);i=true){var f=r.value;console.log(f);if(!f["ischeck"]){continue}t.push(f["id"]);a++}}catch(c){d=true;o=c}finally{try{if(!i&&n.return){n.return()}}finally{if(d){throw o}}}if(a===0){window.Alert("请先选择要进行删除的记录前的复选框");return}e=t.join(",");this.dialogConfirmWithoutAjax({title:"操作",message:'<div id="modal_plsh_msg">确认删除?...</div>'},["取消","确定"],function(){window.post({url:"/jdks/ksccmg/wulideleted",data:(0,l.default)({ids:e})},function(e){if(e.status==="success"){window.Alert("操作成功");this.dataHandle()}else{window.Alert(e.message)}}.bind(this))}.bind(this))},used:function $(e){var t=[];var a=0;var i=true;var d=false;var o=undefined;try{for(var r=(0,s.default)(window["dataTable"]["table"].bootstrapTable("getData")),f;!(i=(f=r.next()).done);i=true){var c=f.value;console.log(c);if(!c["ischeck"]){continue}t.push(c["id"]);a++}}catch(u){d=true;o=u}finally{try{if(!i&&r.return){r.return()}}finally{if(d){throw o}}}if(a===0){window.Alert("请先选择要进行设置无效的记录前的复选框");return}e=t.join(",");this.dialogWaiting({title:"操作",message:'<div id="modal_plsh_msg">正在处理，请稍候...</div>'},{url:"/jdks/ksccmg/deleted",data:(0,l.default)({ids:e,used:"1"})},function(e){if(e.status==="success"){(0,n.default)("#modal_plsh_msg").html("操作成功");this.dataHandle()}else{(0,n.default)("#modal_plsh_msg").html(e.message)}}.bind(this))},use:function V(e){var t=[];var a=0;var i=true;var d=false;var o=undefined;try{for(var r=(0,s.default)(window["dataTable"]["table"].bootstrapTable("getData")),f;!(i=(f=r.next()).done);i=true){var c=f.value;console.log(c);if(!c["ischeck"]){continue}t.push(c["id"]);a++}}catch(u){d=true;o=u}finally{try{if(!i&&r.return){r.return()}}finally{if(d){throw o}}}if(a===0){window.Alert("请先选择要进行设置有效的记录前的复选框");return}e=t.join(",");this.dialogWaiting({title:"操作",message:'<div id="modal_plsh_msg">正在处理，请稍候...</div>'},{url:"/jdks/ksccmg/deleted",data:(0,l.default)({ids:e,used:"0"})},function(e){if(e.status==="success"){(0,n.default)("#modal_plsh_msg").html("操作成功");this.dataHandle()}else{(0,n.default)("#modal_plsh_msg").html(e.message)}}.bind(this))}},ready:function K(){console.log("你确定我进来了？");this.pageNav();this.dataTableInit()},components:{Searchbar:u.default,Va:j.default}}},409:function(e,t,a){"use strict";var i=a(4);var s=n(i);var d=a(1190);var l=n(d);var o=a(6);function n(e){return e&&e.__esModule?e:{"default":e}}window.__pageInit=function(){o.km.init(function(){window.app=new s.default({el:"body",components:{App:l.default}})})}},410:function(e,t){"use strict";e.exports={data:function a(){var e={nf:{m:1,type:"date",show:false,disable:0},ksrq:{m:1,type:"date",show:false,disable:0},starttime:{m:1,type:"date",show:false,disable:0},endtime:{m:1,type:"date",show:false,disable:0},kscc:{m:1,type:"text",show:false,disable:0},dj:{m:1,type:"select",show:false,disable:0},lljk:{m:1,type:"text",show:false,disable:0},scks:{m:1,type:"text",show:false,disable:0},zfs:{m:1,type:"text",show:false,disable:0},kslx:{m:1,type:"select",show:false,disabled:0}};var t={};for(var a in e){t[a]=""}return{entity:t,validateConfig:e,selector:"#modal_add"}}}},411:function(e,t){"use strict";e.exports={data:function a(){var e={nf:{m:1,type:"date",show:false,disable:0},ksrq:{m:1,type:"date",show:false,disable:0},starttime:{m:1,type:"date",show:false,disable:0},endtime:{m:1,type:"date",show:false,disable:0},kscc:{m:1,type:"text",show:false,disable:0},dj:{m:1,type:"select",show:false,disable:0},lljk:{m:1,type:"text",show:false,disable:0},scks:{m:1,type:"text",show:false,disable:0},zfs:{m:1,type:"text",show:false,disable:0},kslx:{m:1,type:"select",show:false,disabled:0}};var t={};for(var a in e){t[a]=""}return{entity:t,validateConfig:e,selector:"#modal_edit"}}}},583:function(e,t){},584:function(e,t){},585:function(e,t){},586:function(e,t){},587:function(e,t){},588:function(e,t){},589:function(e,t){},884:function(e,t){e.exports=" <div id=app class=appv> <navbar></navbar> <topmenu :current-menu=currentMenu :current-submenu=currentSubmenu :current-parentmenu=currentParentmenu></topmenu> <mainarea v-ref:mainarea></mainarea> <helpbar></helpbar> </div> "},885:function(e,t){e.exports=" <div class=mainarea> <component :is=currentView></component> </div> "},886:function(e,t){e.exports=" <div> <div class=d-tbl> <vf field=nf label=年份 data-date-format=YYYY></vf> <vf field=ksrq label=日期 data-date-format=MM-DD></vf> <vf field=starttime label=开始日期 data-date-format=HH:mm></vf> <vf field=endtime label=结束日期 data-date-format=HH:mm></vf> <vf field=kscc label=考试场次></vf> <vf field=dj label=等级 data-dict=bz_sydj></vf> <vf field=kslx label=考试类型 data-dict=bz_kslx></vf> <hr> <div class=kszh>考试组合</div> <vf field=lljk label=理论机考></vf> <vf field=scks label=实操考试></vf> <vf field=zfs label=总分数></vf> </div> </div> "},887:function(e,t){e.exports=" <div> <div class=d-tbl> <vf field=nf label=年份 data-date-format=YYYY></vf> <vf field=ksrq label=日期 data-date-format=MM-DD></vf> <vf field=starttime label=开始日期 data-date-format=HH:mm></vf> <vf field=endtime label=结束日期 data-date-format=HH:mm></vf> <vf field=kscc label=考试场次></vf> <vf field=dj label=等级 data-dict=bz_sydj></vf> <vf field=kslx label=考试类型 data-dict=bz_kslx></vf> <hr> <div class=kszh>考试组合</div> <vf field=lljk label=理论机考></vf> <vf field=scks label=实操考试></vf> <vf field=zfs label=总分数></vf> </div> </div> "},888:function(e,t){e.exports=' <div> <div class=d-tbl> <vf mode=view field=kcname label=考试场次></vf> <hr> <div class=d-title> 考场名称 </div> <div class=index-menu> <template v-for="i in list"> <label class=l1> <i><input type=checkbox name=chk_menuitem value={{$key}} v-model=i.checked :checked={{i.kcname}}></i> {{i.kcname}} </label> </template> </div> </div> </div> '},889:function(e,t){e.exports=" <div> <div class=d-tbl> <vf mode=view field=nf label=年份 data-date-format=YYYY></vf> <vf mode=view field=ksrq label=日期 data-date-format=MM-DD></vf> <vf mode=view field=starttime label=开始日期 data-date-format=HH:mm></vf> <vf mode=view field=endtime label=结束日期 data-date-format=HH:mm></vf> <vf mode=view field=kscc label=考试场次></vf> <vf mode=view field=dj label=等级 data-dict=bz_sydj></vf> <vf mode=view field=kslx label=考试类型 data-dict=bz_kslx></vf> <hr> <div class=kszh>考试组合</div> <vf mode=view field=kszcll label=理论机考></vf> <vf mode=view field=kszcsc label=实操考试></vf> <vf mode=view field=zfs label=总分数></vf> </div> </div> "},890:function(e,t){e.exports=' <div class="pagetitle clearfix"> <div class=t></div> <div class=a></div> </div> <div class=pagebody> <div class=row> <div class=col-sm-12> <searchbar v-ref:searchbar :searchbar=searchbarId :searchbar-col=searchbarCol :searchbar-fields=searchbarFields></searchbar> </div> </div> <div class=row> <div class=col-sm-12> <div id=toolbar class=btn-toolbar> <va></va> </div> <table id=table class=tbl></table> </div> </div> </div> '},1190:function(e,t,a){var i,s;a(583);i=a(116);s=a(884);e.exports=i||{};if(e.exports.__esModule)e.exports=e.exports.default;if(s){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=s}},1191:function(e,t,a){var i,s;a(584);i=a(117);s=a(885);e.exports=i||{};if(e.exports.__esModule)e.exports=e.exports.default;if(s){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=s}},1192:function(e,t,a){var i,s;a(585);i=a(118);s=a(886);e.exports=i||{};if(e.exports.__esModule)e.exports=e.exports.default;if(s){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=s}},1193:function(e,t,a){var i,s;a(586);i=a(119);s=a(887);e.exports=i||{};if(e.exports.__esModule)e.exports=e.exports.default;if(s){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=s}},1194:function(e,t,a){var i,s;a(587);i=a(120);s=a(888);e.exports=i||{};if(e.exports.__esModule)e.exports=e.exports.default;if(s){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=s}},1195:function(e,t,a){var i,s;a(588);i=a(121);s=a(889);e.exports=i||{};if(e.exports.__esModule)e.exports=e.exports.default;if(s){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=s}},1196:function(e,t,a){var i,s;a(589);i=a(122);s=a(890);e.exports=i||{};if(e.exports.__esModule)e.exports=e.exports.default;if(s){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=s}}});