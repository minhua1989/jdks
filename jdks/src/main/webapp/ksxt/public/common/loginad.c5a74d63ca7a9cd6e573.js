webpackJsonp([39],{0:function(e,t,o){e.exports=o(517)},368:function(e,t){"use strict";Object.defineProperty(t,"__esModule",{value:true});t.default={data:function o(){return{}},ready:function i(){}}},383:function(e,t,o){"use strict";Object.defineProperty(t,"__esModule",{value:true});var i=o(1442);var n=d(i);var s=o(1430);var a=d(s);function d(e){return e&&e.__esModule?e:{"default":e}}t.default={data:function c(){return{}},components:{PxDefault:n.default,Navbara:a.default}}},384:function(e,t,o){"use strict";Object.defineProperty(t,"__esModule",{value:true});var i=o(3);var n=d(i);var s=o(11);var a=d(s);function d(e){return e&&e.__esModule?e:{"default":e}}t.default={data:function c(){return{zwh:"",ksxtsfzjh:"",ksxtzkzh:"",vcode:""}},computed:{isActive:function l(){if(this.zwh!==""&&this.ksxtsfzjh!==""&&this.ksxtzkzh!==""&&this.vcode!==""){return true}else{return false}}},methods:{getDict:function r(){if(!window.__global.zxks_dict){window.__global.zxks_dict={}}(0,n.default)(".preload").html('<span class="glyphicon glyphicon-time"></span>系统基础数据正在缓存...');n.default.ajax({type:"post",url:"/jdks/bztable/list"}).done(function(e){var t=e["data"];var o=[];var i=[];var n=[];var s=[];var d=[];var c=[];var l=[];var r=[];t.forEach(function(e){if(e.type==="1"){o.push({name:e["name"],code:e["code"]})}if(e.type==="2"){i.push({name:e["name"],code:e["code"]})}if(e.type==="3"){n.push({name:e["name"],code:e["code"]})}if(e.type==="5"){s.push({name:e["name"],code:e["code"]})}if(e.type==="8"){d.push({name:e["name"],code:e["code"]})}});c.push({name:"理论考",code:"L"},{name:"实操考",code:"C"});l.push({name:"登录",code:"1"},{name:"考试记录",code:"2"});r.push({name:"单选题",code:"1"},{name:"判断题",code:"3"},{name:"操作题",code:"4"});window.__global["zxks_dict"]["bz_stlx"]=r;window.__global["zxks_dict"]["bz_sex"]=o;window.__global["zxks_dict"]["bz_used"]=i;window.__global["zxks_dict"]["bz_role"]=n;window.__global["zxks_dict"]["bz_sydj"]=s;window.__global["zxks_dict"]["bz_zjlx"]=d;window.__global["zxks_dict"]["bz_kslx"]=c;window.__global["zxks_dict"]["bz_logtype"]=l;a.default.set("zxks_dict",window.__global["zxks_dict"]);console.log(window.__global["zxks_dict"])}).fail(function(e){window.Alert("基础数据加载失败")})},submit:function u(){if(!this.isActive){return}var e={zwh:this.zwh,ksxtsfzjh:this.ksxtsfzjh,ksxtzkzh:this.ksxtzkzh,vcode:this.vcode};window.post({url:"/jdks/user/userLogin",data:e},function(e){if(e.status!=="success"){this.dialogConfirmNotAjax({title:"提示信息",message:e.message},["取消","确认"],function(){this.clearInfo()}.bind(this));return}var t=e["data"];a.default.set("user",t);console.log(e);var o=window.__global.studenthomepageurl+"?v="+window.__global["pagever"];window.location.replace(o)}.bind(this))},clearInfo:function p(){this.zwh="";this.ksxtsfzjh="";this.ksxtzkzh="";this.vcode="";this.changeCode()},changeCode:function f(){if(window.__env==="development"){(0,n.default)("#checkImg").attr("src","../jdks/check.jsp?_="+(new Date).getTime())}else{(0,n.default)("#checkImg").attr("src","../../check.jsp?_="+(new Date).getTime())}}},ready:function v(){a.default.set("user","");a.default.set("menu","");this.changeCode();this.getDict()}}},385:function(e,t,o){"use strict";Object.defineProperty(t,"__esModule",{value:true});var i=o(1);var n=l(i);var s=o(3);var a=l(s);var d=o(11);var c=l(d);function l(e){return e&&e.__esModule?e:{"default":e}}t.default={data:function r(){return{ename:"",pwd:"",vcode:""}},computed:{isActive:function u(){if(this.ename!==""&&this.pwd!==""&&this.vcode!==""){return true}else{return false}}},methods:{getDict:function p(){if(!window.__global.zxks_dict){window.__global.zxks_dict={}}(0,a.default)(".preload").html('<span class="glyphicon glyphicon-time"></span>系统基础数据正在缓存...');a.default.ajax({type:"post",url:"/jdks/bztable/list"}).done(function(e){var t=e["data"];var o=[];var i=[];var n=[];var s=[];var a=[];var d=[];var l=[];var r=[];t.forEach(function(e){if(e.type==="1"){o.push({name:e["name"],code:e["code"]})}if(e.type==="2"){i.push({name:e["name"],code:e["code"]})}if(e.type==="3"){n.push({name:e["name"],code:e["code"]})}if(e.type==="5"){s.push({name:e["name"],code:e["code"]})}if(e.type==="8"){a.push({name:e["name"],code:e["code"]})}});d.push({name:"理论考",code:"L"},{name:"实操考",code:"C"});l.push({name:"登录",code:"1"},{name:"考试记录",code:"2"});r.push({name:"单选题",code:"1"},{name:"判断题",code:"3"},{name:"操作题",code:"4"});window.__global["zxks_dict"]["bz_stlx"]=r;window.__global["zxks_dict"]["bz_sex"]=o;window.__global["zxks_dict"]["bz_used"]=i;window.__global["zxks_dict"]["bz_role"]=n;window.__global["zxks_dict"]["bz_sydj"]=s;window.__global["zxks_dict"]["bz_zjlx"]=a;window.__global["zxks_dict"]["bz_kslx"]=d;window.__global["zxks_dict"]["bz_logtype"]=l;c.default.set("zxks_dict",window.__global["zxks_dict"]);console.log(window.__global["zxks_dict"])}).fail(function(e){window.Alert("基础数据加载失败")})},submit:function f(){if(!this.isActive){return}var e={ename:this.ename,pwd:this.pwd,vcode:this.vcode};window.post({url:"/jdks/admin/adminLogin",data:e},function(e){if(e.status!=="success"){this.dialogConfirmNotAjax({title:"提示信息",message:e.message},["取消","确认"],function(){this.clearInfo()}.bind(this));return}var t=(0,n.default)(e.data);c.default.set("user",t);var o=window.__global.homepageurl+"?v="+window.__global["pagever"];window.location.replace(o)}.bind(this))},clearInfo:function v(){this.ename="";this.pwd="";this.vcode="";this.changeCode()},changeCode:function _(){if(window.__env==="development"){(0,a.default)("#checkImg").attr("src","../jdks/check.jsp?_="+(new Date).getTime())}else{(0,a.default)("#checkImg").attr("src","../../check.jsp?_="+(new Date).getTime())}}},ready:function h(){c.default.set("user","");c.default.set("menu","");c.default.set("menuaccess","");this.changeCode();this.getDict()}}},386:function(e,t,o){"use strict";Object.defineProperty(t,"__esModule",{value:true});var i=o(1440);var n=l(i);var s=o(1441);var a=l(s);var d=o(11);var c=l(d);function l(e){return e&&e.__esModule?e:{"default":e}}t.default={data:function r(){return{currentView:"LoginAdmin",isActive:true}},methods:{teacherVer:function u(){this.isActive=false;this.currentView="LoginAdmin"},studentVer:function p(){this.isActive=true;this.currentView="Login"},login:function f(){var e={datarole:"1",name:"张三"};c.default.set("user",e);var t=window.__global.homepageurl+"?v="+window.__global["pagever"];console.log(t);window.location.href=t},login1:function v(){var e={datarole:"0",name:"张三"};c.default.set("user",e);var t=window.__global.studenthomepageurl+"?v="+window.__global["pagever"];console.log(t);window.location.href=t}},components:{Login:n.default,LoginAdmin:a.default}}},517:function(e,t,o){"use strict";var i=o(4);var n=c(i);var s=o(1439);var a=c(s);var d=o(6);function c(e){return e&&e.__esModule?e:{"default":e}}window.__pageInit=function(){d.km.init(function(){window.app=new n.default({el:"body",components:{App:a.default}})})}},835:function(e,t){},836:function(e,t){},863:function(e,t){},1140:function(e,t){e.exports=' <div class=mynavbar> <span class=logo><span class="glyphicon glyphicon-leaf"></span></span> <span class=h>安防技术鉴定系统</span> <div class=act></div> </div> '},1155:function(e,t){e.exports=" <div id=app class=appv> <navbara></navbara> <px-default></px-default> </div> "},1156:function(e,t){e.exports=' <div> <div class=int> <input type=text placeholder=身份证件号码 v-model=ksxtsfzjh> </div> <div class=int> <input type=text placeholder=准考证号 v-model=ksxtzkzh> </div> <div class=int> <input type=text placeholder=座位号 v-model=zwh @focus=changeCode> </div> <div class="int v"> <input type=text placeholder=验证码 v-model=vcode> <img id=checkImg width=90 height=37 v-on:click=changeCode()> </div> <div class=int4> <button type=button class="btn btn-primary" v-bind:disabled=!isActive v-bind:class="{disable: !isActive}" v-on:click=submit()>登录</button> </div> </div> '},1157:function(e,t){e.exports=' <div style="padding-top: 15px"> <div class=int> <input type=text placeholder=用户名 v-model=ename> </div> <div class=int> <input id=pwd type=password placeholder=密码 v-model=pwd> </div> <div class="int v"> <input id=vcode type=text placeholder=验证码 v-model=vcode> <img id=checkImg width=90 height=37 v-on:click=changeCode()> </div> <div class=error_message></div> <div class=int4> <button id=btn_submit type=button class=btn-primary v-bind:class="{disable: !isActive}" v-bind:disabled=!isActive v-on:click=submit()>登录</button> </div> </div> '},1158:function(e,t){e.exports=' <div class=wrapper> <div class=logon-banner></div> <div class=login> <div class=mask></div> <div class=body> <div class="int login-title"> 管理员登录 </div> <component :is=currentView></component> </div> </div> <div class=footer> <span>系统推荐使用 IE11 浏览器、谷歌浏览器、火狐浏览器</span><br> </div> </div> '},1430:function(e,t,o){var i,n;o(863);i=o(368);n=o(1140);e.exports=i||{};if(e.exports.__esModule)e.exports=e.exports.default;if(n){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=n}},1439:function(e,t,o){var i,n;o(835);i=o(383);n=o(1155);e.exports=i||{};if(e.exports.__esModule)e.exports=e.exports.default;if(n){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=n}},1440:function(e,t,o){var i,n;i=o(384);n=o(1156);e.exports=i||{};if(e.exports.__esModule)e.exports=e.exports.default;if(n){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=n}},1441:function(e,t,o){var i,n;i=o(385);n=o(1157);e.exports=i||{};if(e.exports.__esModule)e.exports=e.exports.default;if(n){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=n}},1442:function(e,t,o){var i,n;o(836);i=o(386);n=o(1158);e.exports=i||{};if(e.exports.__esModule)e.exports=e.exports.default;if(n){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=n}}});