webpackJsonp([13],{0:function(e,t,a){e.exports=a(416)},131:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:true});var i=a(8);var s=r(i);var d=a(9);var l=r(d);var o=a(1209);var n=r(o);function r(e){return e&&e.__esModule?e:{"default":e}}t.default={data:function f(){return{currentMenu:"examManage",currentSubmenu:"exam1"}},components:{Navbar:s.default,Topmenu:l.default,Mainarea:n.default}}},132:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:true});var i=a(1214);var s=d(i);function d(e){return e&&e.__esModule?e:{"default":e}}t.default={data:function l(){return{currentView:"PxDefault"}},components:{PxDefault:s.default},ready:function o(){}}},133:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:true});var i=a(1);var s=r(i);var d=a(2);var l=r(d);var o=a(417);var n=r(o);function r(e){return e&&e.__esModule?e:{"default":e}}t.default={editmode:"add",mixins:[n.default],events:{doAdd:"doAdd"},methods:{doAdd:function f(e){this.validateAll(function(){window.post({url:"/jdks/ksccmg/add",data:(0,s.default)(this.entity)},function(t){if(t.status==="success"){e.close();window.app.$broadcast("reloadList");window.Alert("操作成功")}else{window.Alert(t.message)}})}.bind(this))}},ready:function c(){this.beforeInit();this.$nextTick(function(){this.validateInit()})},components:{Vf:l.default}}},134:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:true});var i=a(1);var s=c(i);var d=a(2);var l=c(d);var o=a(418);var n=c(o);var r=a(7);var f=c(r);function c(e){return e&&e.__esModule?e:{"default":e}}t.default={editmode:"edit",mixins:[n.default],events:{doEdit:"doEdit"},props:["id"],methods:{doEdit:function u(e,t){this.validateAll(function(){window.post({url:"/jdks/ksccmg/edit",data:(0,s.default)(this.entity)},function(e){if(e.status==="success"){t.close();window.app.$broadcast("reloadList");window.Alert("操作成功")}else{window.Alert(e.message)}})}.bind(this))}},ready:function v(){f.default.series([function(e){this.beforeInit();e(null,"")}.bind(this),function(e){window.post({url:"/jdks/ksccmg/searchOne",data:(0,s.default)({id:this.id})},function(t){this.dataPreHandle(t);e(null,"")}.bind(this))}.bind(this)],function(e,t){if(e){window.winLog(e)}this.$nextTick(function(){this.afterInit();this.validateInit()})}.bind(this))},components:{Vf:l.default}}},135:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:true});var i=a(1);var s=r(i);var d=a(2);var l=r(d);var o=a(7);var n=r(o);function r(e){return e&&e.__esModule?e:{"default":e}}t.default={editmode:"edit",data:function f(){var e={kcname:{m:0,type:"",show:false,disable:0},kcid:{m:1,type:"",show:false,disable:0},ksccid:{m:1,type:"",show:false,disable:0}};var t={};for(var a in e){t[a]=""}return{entity:t,validateConfig:e,selector:"#modal_edit",list:[],kcidsadd:[],kcidsrm:[],source:[]}},events:{doSet:"doSet"},props:["id","kcname"],methods:{doSet:function c(e){var t=this;this.list.forEach(function(e){if(e.checked){if(t.source.indexOf(e.id)===-1){t.kcidsadd.push(e.id)}}});console.log(this.source);this.list.forEach(function(e){if(!e.checked){if(t.source.indexOf(e.id)!==-1){t.kcidsrm.push(e.id)}}});var a={ksccid:this.id,kcidsadd:this.kcidsadd.toString(),kcidsrm:this.kcidsrm.toString()};window.post({url:"/jdks/ksccmg/addKcinfo",data:(0,s.default)(a)},function(t){if(t.status==="success"){e.close();window.app.$broadcast("reloadList");window.Alert("操作成功")}else{window.Alert(t.message)}})},itemClass:function u(e){return"l"+e.deep}},ready:function v(){n.default.series([function(e){this.beforeInit();this.entity.kcname=this.kcname;e(null,"")}.bind(this),function(e){window.post({url:"/jdks/kcmg/yxpageList"},function(t){var a=this;this.list=[];t["data"].forEach(function(e){a.list.push({id:e["id"],kcname:e["kcname"],checked:false})});console.log(t);e(null,"")}.bind(this))}.bind(this),function(e){window.post({url:"/jdks/ksccmg/pageListKcKscc",data:(0,s.default)({ksccid:this.id})},function(t){var a=this;t["data"].forEach(function(e){a.source.push(e.kcid)});console.log(this.source);this.list.forEach(function(e){console.log(e);if(a.source.indexOf(e.id)!==-1){e.checked=true}});console.log(this.list);e(null,"")}.bind(this))}.bind(this)],function(e,t){if(e){window.winLog(e)}this.$nextTick(function(){this.afterInit();this.validateInit()})}.bind(this))},components:{Vf:l.default}}},136:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:true});var i=a(1);var s=o(i);var d=a(2);var l=o(d);function o(e){return e&&e.__esModule?e:{"default":e}}t.default={data:function n(){return{entity:{}}},props:["id"],ready:function r(){window.post({url:"/jdks/ksccmg/searchOne",data:(0,s.default)({id:this.id})},function(e){this.entity=e.data}.bind(this))},components:{Vf:l.default}}},137:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:true});var i=a(5);var s=M(i);var d=a(1);var l=M(d);var o=a(3);var n=M(o);var r=a(10);var f=M(r);var c=a(13);var u=M(c);var v=a(4);var m=M(v);var p=a(1210);var h=M(p);var b=a(1213);var w=M(b);var x=a(1211);var k=M(x);var _=a(1212);var g=M(_);var y=a(12);var j=M(y);function M(e){return e&&e.__esModule?e:{"default":e}}t.default={data:function A(){var e=window.__global.user.roleid;var t=(0,f.default)(["0","1","2","3","4"],"exam1_table",e,this);if(!t){return{}}return t},events:{add:"add",edit:"edit",del:"del",view:"view",used:"used",use:"use",setkc:"setkc",reloadList:"reloadList",scksj:"scksj",sctzd:"sctzd",kscjsc:"kscjsc"},methods:{kscjsc:function H(e){this.dialogWaiting({title:"操作",message:'<div id="modal_plsh_msg">正在处理，请稍候...</div>'},{url:"/jdks/kscj/cjhc",data:(0,l.default)({ksccid:e})},function(e){if(e.status==="success"){(0,n.default)("#modal_plsh_msg").html("操作成功");this.dataHandle()}else{(0,n.default)("#modal_plsh_msg").html(e.message)}}.bind(this))},sctzd:function z(e){this.dialogWaiting({title:"操作",message:'<div id="modal_plsh_msg">正在处理，请稍候...</div>'},{url:"/jdks/kcmg/createKSTZD",data:(0,l.default)({ksccid:e})},function(e){if(e.status==="success"){(0,n.default)("#modal_plsh_msg").html("操作成功");this.dataHandle()}else{(0,n.default)("#modal_plsh_msg").html(e.message)}}.bind(this))},scksj:function C(e){this.dialogWaiting({title:"操作",message:'<div id="modal_plsh_msg">正在处理，请稍候...</div>'},{url:"/jdks/kscc/createKssj",data:(0,l.default)({id:e})},function(e){if(e.status==="success"){(0,n.default)("#modal_plsh_msg").html("操作成功");this.dataHandle()}else{(0,n.default)("#modal_plsh_msg").html(e.message)}}.bind(this))},reloadList:function D(){this.dataHandle()},view:function O(e){var t=m.default.extend({data:function a(){return{id:e}},template:'<mx-view :id="id"></mx-view>',components:{MxView:w.default}});this.dialogModal({did:"modal_view",title:"查看",cssClass:"big-dialog-umeditor",buttons:[{label:"关闭",action:function i(e){e.close()}}]},t)},add:function T(){var e=m.default.extend({template:"<mx-add></mx-add>",components:{MxAdd:h.default}});this.dialogModal({did:"modal_add",title:"新增考场计划安排",cssClass:"big-dialog-umeditor",buttons:[{label:"提交",cssClass:"btn-primary",action:function t(e){window.app.$broadcast("doAdd",e)}},{label:"关闭",action:function a(e){e.close()}}]},e)},edit:function Y(e){var t=m.default.extend({data:function a(){return{id:e}},template:'<mx-edit :id="id"></mx-edit>',components:{MxEdit:k.default}});this.dialogModal({did:"modal_edit",title:"修改",cssClass:"big-dialog-umeditor",buttons:[{label:"提交",cssClass:"btn-primary",action:function i(t){window.app.$broadcast("doEdit",e,t)}},{label:"关闭",action:function s(e){e.close()}}]},t)},setkc:function I(e,t){var a=m.default.extend({data:function i(){return{id:e,kcname:t}},template:'<mx-kc :id="id" :kcname="kcname"></mx-kc>',components:{MxKc:g.default}});this.dialogModal({did:"modal_edit",title:"添加",cssClass:"big-dialog-umeditor",buttons:[{label:"确定",cssClass:"btn-primary",action:function s(e){window.app.$broadcast("doSet",e)}},{label:"关闭",action:function d(e){e.close()}}]},a)},del:function P(e){var t=[];var a=0;var i=true;var d=false;var o=undefined;try{for(var n=(0,s.default)(window["dataTable"]["table"].bootstrapTable("getData")),r;!(i=(r=n.next()).done);i=true){var f=r.value;console.log(f);if(!f["ischeck"]){continue}t.push(f["id"]);a++}}catch(c){d=true;o=c}finally{try{if(!i&&n.return){n.return()}}finally{if(d){throw o}}}if(a===0){window.Alert("请先选择要进行删除的记录前的复选框");return}e=t.join(",");this.dialogConfirmWithoutAjax({title:"操作",message:'<div id="modal_plsh_msg">确认删除?...</div>'},["取消","确定"],function(){window.post({url:"/jdks/ksccmg/wulideleted",data:(0,l.default)({ids:e})},function(e){if(e.status==="success"){window.Alert("操作成功");this.dataHandle()}else{window.Alert(e.message)}}.bind(this))}.bind(this))},used:function E(e){var t=[];var a=0;var i=true;var d=false;var o=undefined;try{for(var r=(0,s.default)(window["dataTable"]["table"].bootstrapTable("getData")),f;!(i=(f=r.next()).done);i=true){var c=f.value;console.log(c);if(!c["ischeck"]){continue}t.push(c["id"]);a++}}catch(u){d=true;o=u}finally{try{if(!i&&r.return){r.return()}}finally{if(d){throw o}}}if(a===0){window.Alert("请先选择要进行设置无效的记录前的复选框");return}e=t.join(",");this.dialogWaiting({title:"操作",message:'<div id="modal_plsh_msg">正在处理，请稍候...</div>'},{url:"/jdks/ksccmg/deleted",data:(0,l.default)({ids:e,used:"1"})},function(e){if(e.status==="success"){(0,n.default)("#modal_plsh_msg").html("操作成功");this.dataHandle()}else{(0,n.default)("#modal_plsh_msg").html(e.message)}}.bind(this))},use:function L(e){var t=[];var a=0;var i=true;var d=false;var o=undefined;try{for(var r=(0,s.default)(window["dataTable"]["table"].bootstrapTable("getData")),f;!(i=(f=r.next()).done);i=true){var c=f.value;console.log(c);if(!c["ischeck"]){continue}t.push(c["id"]);a++}}catch(u){d=true;o=u}finally{try{if(!i&&r.return){r.return()}}finally{if(d){throw o}}}if(a===0){window.Alert("请先选择要进行设置有效的记录前的复选框");return}e=t.join(",");this.dialogWaiting({title:"操作",message:'<div id="modal_plsh_msg">正在处理，请稍候...</div>'},{url:"/jdks/ksccmg/deleted",data:(0,l.default)({ids:e,used:"0"})},function(e){if(e.status==="success"){(0,n.default)("#modal_plsh_msg").html("操作成功");this.dataHandle()}else{(0,n.default)("#modal_plsh_msg").html(e.message)}}.bind(this))}},ready:function S(){this.pageNav();this.dataTableInit()},components:{Searchbar:u.default,Va:j.default}}},416:function(e,t,a){"use strict";var i=a(4);var s=n(i);var d=a(1208);var l=n(d);var o=a(6);function n(e){return e&&e.__esModule?e:{"default":e}}window.__pageInit=function(){o.km.init(function(){window.app=new s.default({el:"body",components:{App:l.default}})})}},417:function(e,t){"use strict";e.exports={data:function a(){var e={nf:{m:1,type:"date",show:false,disable:0},ksrq:{m:1,type:"date",show:false,disable:0},starttime:{m:1,type:"date",show:false,disable:0},endtime:{m:1,type:"date",show:false,disable:0},kscc:{m:1,type:"text",show:false,disable:0},dj:{m:1,type:"select",show:false,disable:0},lljk:{m:1,type:"text",show:false,disable:0},scks:{m:1,type:"text",show:false,disable:0},zfs:{m:1,type:"text",show:false,disable:0},kslx:{m:1,type:"select",show:false,disabled:0}};var t={};for(var a in e){t[a]=""}return{entity:t,validateConfig:e,selector:"#modal_add"}}}},418:function(e,t){"use strict";e.exports={data:function a(){var e={nf:{m:1,type:"date",show:false,disable:0},ksrq:{m:1,type:"date",show:false,disable:0},starttime:{m:1,type:"date",show:false,disable:0},endtime:{m:1,type:"date",show:false,disable:0},kscc:{m:1,type:"text",show:false,disable:0},dj:{m:1,type:"select",show:false,disable:0},lljk:{m:1,type:"text",show:false,disable:0},scks:{m:1,type:"text",show:false,disable:0},zfs:{m:1,type:"text",show:false,disable:0},kslx:{m:1,type:"select",show:false,disabled:0}};var t={};for(var a in e){t[a]=""}return{entity:t,validateConfig:e,selector:"#modal_edit"}}}},599:function(e,t){},600:function(e,t){},601:function(e,t){},602:function(e,t){},603:function(e,t){},604:function(e,t){},605:function(e,t){},901:function(e,t){e.exports=" <div id=app class=appv> <navbar></navbar> <topmenu :current-menu=currentMenu :current-submenu=currentSubmenu :current-parentmenu=currentParentmenu></topmenu> <mainarea v-ref:mainarea></mainarea> <helpbar></helpbar> </div> "},902:function(e,t){e.exports=" <div class=mainarea> <component :is=currentView></component> </div> "},903:function(e,t){e.exports=" <div> <div class=d-tbl> <vf field=nf label=年份 data-date-format=YYYY></vf> <vf field=ksrq label=日期 data-date-format=MM-DD></vf> <vf field=starttime label=开始时间 data-date-format=HH:mm></vf> <vf field=endtime label=结束时间 data-date-format=HH:mm></vf> <vf field=kscc label=考试场次></vf> <vf field=dj label=等级 data-dict=bz_sydj></vf> <vf field=kslx label=考试类型 data-dict=bz_kslx></vf> <hr> <div class=kszh>考试组合</div> <vf field=lljk label=理论机考></vf> <vf field=scks label=实操考试></vf> <vf field=zfs label=总分数></vf> </div> </div> "},904:function(e,t){e.exports=" <div> <div class=d-tbl> <vf field=nf label=年份 data-date-format=YYYY></vf> <vf field=ksrq label=日期 data-date-format=MM-DD></vf> <vf field=starttime label=开始时间 data-date-format=HH:mm></vf> <vf field=endtime label=结束时间 data-date-format=HH:mm></vf> <vf field=kscc label=考试场次></vf> <vf field=dj label=等级 data-dict=bz_sydj></vf> <vf field=kslx label=考试类型 data-dict=bz_kslx></vf> <hr> <div class=kszh>考试组合</div> <vf field=lljk label=理论机考></vf> <vf field=scks label=实操考试></vf> <vf field=zfs label=总分数></vf> </div> </div> "},905:function(e,t){e.exports=' <div> <div class=d-tbl> <vf mode=view field=kcname label=考试场次></vf> <hr> <div class=d-title> 考场名称 </div> <div class=index-menu> <template v-for="i in list"> <label class=l1> <i><input type=checkbox name=chk_menuitem value={{$key}} v-model=i.checked :checked={{i.kcname}}></i> {{i.kcname}} </label> </template> </div> </div> </div> '},906:function(e,t){e.exports=" <div> <div class=d-tbl> <vf mode=view field=nf label=年份 data-date-format=YYYY></vf> <vf mode=view field=ksrq label=日期 data-date-format=MM-DD></vf> <vf mode=view field=starttime label=开始时间 data-date-format=HH:mm></vf> <vf mode=view field=endtime label=结束时间 data-date-format=HH:mm></vf> <vf mode=view field=kscc label=考试场次></vf> <vf mode=view field=dj label=等级 data-dict=bz_sydj></vf> <vf mode=view field=kslx label=考试类型 data-dict=bz_kslx></vf> <hr> <div class=kszh>考试组合</div> <vf mode=view field=kszcll label=理论机考></vf> <vf mode=view field=kszcsc label=实操考试></vf> <vf mode=view field=zfs label=总分数></vf> </div> </div> "},907:function(e,t){e.exports=' <div class="pagetitle clearfix"> <div class=t></div> <div class=a></div> </div> <div class=pagebody> <div class=row> <div class=col-sm-12> <searchbar v-ref:searchbar :searchbar=searchbarId :searchbar-col=searchbarCol :searchbar-fields=searchbarFields></searchbar> </div> </div> <div class=row> <div class=col-sm-12> <div id=toolbar class=btn-toolbar> <va></va> </div> <table id=table class=tbl></table> </div> </div> </div> '},1208:function(e,t,a){var i,s;a(599);i=a(131);s=a(901);e.exports=i||{};if(e.exports.__esModule)e.exports=e.exports.default;if(s){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=s}},1209:function(e,t,a){var i,s;a(600);i=a(132);s=a(902);e.exports=i||{};if(e.exports.__esModule)e.exports=e.exports.default;if(s){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=s}},1210:function(e,t,a){var i,s;a(601);i=a(133);s=a(903);e.exports=i||{};if(e.exports.__esModule)e.exports=e.exports.default;if(s){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=s}},1211:function(e,t,a){var i,s;a(602);i=a(134);s=a(904);e.exports=i||{};if(e.exports.__esModule)e.exports=e.exports.default;if(s){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=s}},1212:function(e,t,a){var i,s;a(603);i=a(135);s=a(905);e.exports=i||{};if(e.exports.__esModule)e.exports=e.exports.default;if(s){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=s}},1213:function(e,t,a){var i,s;a(604);i=a(136);s=a(906);e.exports=i||{};if(e.exports.__esModule)e.exports=e.exports.default;if(s){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=s}},1214:function(e,t,a){var i,s;a(605);i=a(137);s=a(907);e.exports=i||{};if(e.exports.__esModule)e.exports=e.exports.default;if(s){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=s}}});