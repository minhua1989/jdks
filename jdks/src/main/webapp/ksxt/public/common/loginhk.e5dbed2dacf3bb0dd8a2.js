webpackJsonp([45],{0:function(e,t,a){e.exports=a(518)},14:function(e,t){"use strict";Object.defineProperty(t,"__esModule",{value:true});t.default={data:function a(){return{}},ready:function o(){}}},15:function(e,t){},16:function(e,t){e.exports=' <div class=mynavbar> <span class=logo><span class="glyphicon glyphicon-leaf"></span></span> <span class=h>安防技术鉴定系统&nbsp;————&nbsp;考试平台</span> <div class=act></div> </div> '},17:function(e,t,a){var o,n;a(15);o=a(14);n=a(16);e.exports=o||{};if(e.exports.__esModule)e.exports=e.exports.default;if(n){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=n}},388:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:true});var o=a(17);var n=i(o);var s=a(1442);var r=i(s);function i(e){return e&&e.__esModule?e:{"default":e}}t.default={data:function u(){return{}},components:{Navbarb:n.default,Login:r.default}}},389:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:true});var o=a(1);var n=i(o);var s=a(11);var r=i(s);function i(e){return e&&e.__esModule?e:{"default":e}}t.default={methods:{},ready:function d(){r.default.set("user","");r.default.set("menu","");window.__global.checkquit="";var e=u("key");if(!e){return}var t={key:e};window.post({url:"/xjxt/manage/adminLoginForHk",data:(0,n.default)(t)},function(e){window.winLog(e);if(e.status!=="success"){window.Alert(e.message);return}r.default.set("showsystemnotice","1");r.default.set("dataaccess","");r.default.set("menuaccess","");var t=0;if(e["data"]["rolelist"]){for(var a=0;a<e["data"].rolelist.length;a++){if(e["data"].id===e["data"].rolelist[a].id){t=a}}}sessionStorage.setItem("userid",e["data"].id);r.default.set("currrole",t);r.default.set("user",e["data"]);r.default.set("areaname",e["data"].areaname);r.default.set("__iscas","1");var o=window.__global.homepageurl+"?v="+window.__global["pagever"];window.location.replace(o)})}};function u(e,t){if(!t){t=top.location.href}if(t.indexOf(e+"=")===-1){return""}var a=t.split(e+"=");var o=a[1].indexOf("&")!==-1?a[1].split("&"):[a[1]];var n=o[0].indexOf("#")!==-1?o[0].split("#"):[o[0]];return String(n[0])}},518:function(e,t,a){"use strict";var o=a(4);var n=u(o);var s=a(1441);var r=u(s);var i=a(6);function u(e){return e&&e.__esModule?e:{"default":e}}window.__pageInit=function(){i.km.init(function(){window.app=new n.default({el:"body",components:{App:r.default}})})}},837:function(e,t){},838:function(e,t){},1158:function(e,t){e.exports=" <div id=app class=appv> <navbarb></navbarb> <login></login> </div> "},1159:function(e,t){e.exports=" <div class=wrapper> </div> "},1441:function(e,t,a){var o,n;a(837);o=a(388);n=a(1158);e.exports=o||{};if(e.exports.__esModule)e.exports=e.exports.default;if(n){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=n}},1442:function(e,t,a){var o,n;a(838);o=a(389);n=a(1159);e.exports=o||{};if(e.exports.__esModule)e.exports=e.exports.default;if(n){(typeof e.exports==="function"?e.exports.options||(e.exports.options={}):e.exports).template=n}}});