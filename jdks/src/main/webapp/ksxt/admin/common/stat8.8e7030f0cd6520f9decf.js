webpackJsonp([16],{0:function(a,t,i){a.exports=i(456)},20:function(a,t,i){var d,e;i(60);d=i(59);e=i(61);a.exports=d||{};if(a.exports.__esModule)a.exports=a.exports.default;if(e){(typeof a.exports==="function"?a.exports.options||(a.exports.options={}):a.exports).template=e}},59:function(a,t){"use strict";Object.defineProperty(t,"__esModule",{value:true});t.default={data:function i(){return{}},computed:{fieldValue:function d(){if(typeof this.$parent["entity"]==="undefined"||typeof this.$parent.entity[this.field]==="undefined"){return""}if(this.type==="array"){if(this.dataDict){var a=[];for(var t=0;t<this.$parent.entity[this.field].length;t++){a.push(window.tranDict(this.$parent.entity[this.field][t],this.dataDict))}return a.join("&nbsp;&nbsp;")}else{return this.$parent.entity[this.field].join("&nbsp;&nbsp;")}}else if(this.type==="pretext"){return'<pre class="pretext">'+this.$parent.entity[this.field]+"</pre>"}else{return this.dataDict?window.tranDict(this.$parent.entity[this.field],this.dataDict,this.$parent.entity[this.field]):this.$parent.entity[this.field]}}},props:["type","mode","field","label","labelstr","maxlength","id","multiple","style","value","dataTip","dataDict","dataValue","dataName","dataRef","dataFilter","dataHideid","dataLiveSearch","dataDateFormat","dataDateMinViewMode","dataDateStartDate","dataDateEndDate"],methods:{tClass:function e(a){if(a){return"t tt"}else{return"t"}}},ready:function s(){}}},60:function(a,t){},61:function(a,t){a.exports=" <div class=i v-bind:class=$parent.fieldClass(field)> <i>{{labelstr || label}}</i> <span v-bind:class=tClass(dataTip)>{{{fieldValue || '-'}}}<slot></slot></span> </div> "},63:function(a,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:true});var d=i(1);var e=v(d);var s=i(20);var l=v(s);function v(a){return a&&a.__esModule?a:{"default":a}}t.default={data:function c(){return{entity:{},list:[],selector:"#d_operation"}},editmode:"view",filters:{tran:function f(a){return window.tranDict(a,"t_bz_jtgx")},transf:function o(a){return window.tranDict(a,"t_bz_sfzd")}},components:{vf:l.default},computed:{},props:["comeHere"],methods:{sectionToggle:function r(a){var t=(0,e.default)(a.target);t.siblings(".body").toggle()}},ready:function n(){var a="/yeyzsxjxh/register/registerDetail";if(this.$parent.comeHere==="enroll1"){a="/yeyzsxjxh/admission/luquDetail"}window.post({url:a,data:window.myEncode({id:this.$parent.id})},function(a){a=window.myDecode(a);this.dataPreHandle(a);this.entity.xzzsf="上海市";this.list=this.entity.concatlist;(0,e.default)(".mainarea-f .pagetitle .t").text("查看【"+this.entity.xm+"】信息")}.bind(this));this.$nextTick(function(){this.viewHeight()})}}},64:function(a,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:true});var d=i(1);var e=v(d);var s=i(20);var l=v(s);function v(a){return a&&a.__esModule?a:{"default":a}}t.default={data:function c(){return{entity:{},selector:"#d_operation"}},editmode:"view",components:{vf:l.default},computed:{},methods:{sectionToggle:function f(a){var t=(0,e.default)(a.target);t.siblings(".body").toggle()}},ready:function o(){window.post({url:"/yeyzsxjxh/register/registerDetail",data:window.myEncode({id:this.$parent.id})},function(a){a=window.myDecode(a);this.dataPreHandle(a);this.entity.xzzsf="上海市";(0,e.default)(".mainarea-f .pagetitle .t").text("查看【"+this.entity.xm+"】信息")}.bind(this));this.$nextTick(function(){this.viewHeight()})}}},69:function(a,t){},70:function(a,t){a.exports=' <div class="section section-edit"> <div class=head @click.stop=sectionToggle>基本信息<span class=caret></span></div> <div class="body no-padding"> <div class="row a3"> <div class=col-sm-4> <vf label=幼儿园 field=schoolname></vf> </div> <div class=col-sm-4> <vf label=年级 field=njdm data-dict=t_bz_nj></vf> </div> <div class=col-sm-4> <vf label=姓名 field=xm></vf> </div> </div> <div class="row a3"> <div class=col-sm-4> <vf label=性别 field=xb data-dict=t_bz_xb></vf> </div> <div class=col-sm-4> <vf label=证件类型 field=sfzlx id=int_sfzlx data-dict=t_bz_sfzlx></vf> </div> <div class=col-sm-4> <vf label=证件号码 field=sfzjh></vf> </div> </div> <div class="row a3"> <div class=col-sm-4> <vf label=出生日期 field=csrq data-date-format=yyyy-mm-dd data-date-min-view-mode=0 data-date-start-date=1700-01-01 data-date-end-date=0d></vf> </div> <div class=col-sm-4> <vf label=是否独生子女 field=sfdszv data-dict=t_bz_sfdszv></vf> </div> <div class=col-sm-4> <vf label=国籍 field=gjdq data-dict=t_bz_gj></vf> </div> </div> <div class="row a3"> <div class=col-sm-4> <vf label=民族 field=mz data-dict=t_bz_mz></vf> </div> <div class=col-sm-4> <vf label=健康状况 field=jkzk data-dict=t_bz_jkzk></vf> </div> <div class=col-sm-4> <vf label=曾患何种慢性病遗传病（如心脏病、哮喘等） field=jbzk></vf> </div> </div> </div> </div> <div class="section section-edit"> <div class=head @click.stop=sectionToggle> 户口地址信息<span class=caret></span> </div> <div class="body no-padding"> <div class="row a3"> <div class=col-sm-4> <vf label=户口省份 field=hksf id=int_hksf data-dict=t_bz_city data-live-search=true data-filter=-1 data-tip=""></vf> </div> <div class=col-sm-4> <vf label=户口市 field=hkcity id=int_hkcity data-dict=t_bz_city :data-value=entity.hkcity data-live-search=true data-ref=int_hksf data-tip=""></vf> </div> <div class=col-sm-4> <vf label=户口所在区县 field=hkqx id=int_hkqx data-dict=t_bz_city :data-value=entity.hkqx data-live-search=true data-ref=int_hkcity data-tip=""></vf> </div> </div> <div class="row a3"> <div class=col-sm-4> <vf label=户口街镇 field=hkjz id=int_hkjz data-dict=t_bz_jd :data-value=entity.hkjz data-live-search=true data-ref=int_hkqx data-tip=""></vf> </div> <div class=col-sm-4> <vf label=户口居委会/村 field=hkjwh id=int_hkjwh data-dict=t_bz_jw :data-value=entity.hkjwh data-live-search=true data-ref=int_hkjz data-tip=""></vf> </div> <div class=col-sm-4> <vf label=户口性质 field=hkxz data-dict=t_bz_hkxz></vf> </div> </div> <div class="row a3"> <div class=col-sm-12> <vf label=户口详细地址 field=wsshkdz></vf> </div> </div> </div> </div> <div class="section section-edit"> <div class=head @click.stop=sectionToggle>现住地址信息<span class=caret></span></div> <div class="body no-padding"> <div class="row a3"> <div class=col-sm-4> <vf mode=view label=现住址省市 field=xzzsf></vf> </div> <div class=col-sm-4> <vf label=现住址区县 field=xzzqx id=int_xzzqx data-dict=t_bz_city data-live-search=true data-filter=310100 data-tip=""></vf> </div> <div class=col-sm-4> <vf label=现住址所属街道 field=xzzjd id=int_xzzjd data-dict=t_bz_jd :data-value=entity.xzzjd data-live-search=true data-ref=int_xzzqx data-tip=""></vf> </div> </div> <div class="row a3"> <div class=col-sm-4> <vf label=现住址所属居委 field=xzzjw id=int_xzzjw data-dict=t_bz_jw :data-value=entity.xzzjw data-live-search=true data-ref=int_xzzjd data-tip=""></vf> </div> <div class=col-sm-4> <vf label=居住地址 field=jzdz></vf> </div> <div class=col-sm-4> <vf label=邮政编码 field=xzzyzbm></vf> </div> </div> <div class="row a3"> <div class=col-sm-4> <vf label=房屋属性 field=zfxz data-dict=t_bz_zfxz></vf> </div> <div class=col-sm-8> <vf label=备注 field=remark></vf> </div> </div> </div> </div> <div class="section section-edit"> <div class=head @click.stop=sectionToggle>家庭联系信息<span class=caret></span></div> <div class="body no-padding"> <div class="row t-list-head"> <div class=col-sm-2><label>称谓</label></div> <div class=col-sm-2><label>姓名</label></div> <div class=col-sm-2><label>联系方式</label></div> <div class=col-sm-3><label>工作单位</label></div> <div class=col-sm-2><label>是否监护人</label></div> <div class=col-sm-1></div> </div> <div class="row t-list-body" v-for="item in list"> <div class=col-sm-2> {{item.gx | tran}} </div> <div class=col-sm-2> {{item.xm}} </div> <div class=col-sm-2> {{item.dh}} </div> <div class=col-sm-3> {{item.gzdw}} </div> <div class=col-sm-2> {{item.sfjhr | transf}} </div> <div class=col-sm-1> </div> </div> </div> </div> <div class="section section-edit"> <div class=head @click.stop=sectionToggle>户籍类型<span class=caret></span></div> <div class="body no-padding"> <div class="row a3"> <div class=col-sm-3> <vf label=户籍类型 field=ryyj id=int_ryyj data-dict=t_bz_ryyj data-tip=""></vf> </div> <div class=col-sm-3> <vf label=入园依据类别 field=ryyjlb data-dict=t_bz_ryyjlb :data-value=entity.ryyjlb data-live-search=true data-ref=int_ryyj data-tip=""></vf> </div> <div class=col-sm-3> <vf label=持产证人与幼儿关系 field=gfrgx data-dict=t_bz_hzgxdm></vf> </div> <div class=col-sm-3> <vf label=报名类别 field=bmlb data-dict=t_bz_bmlb></vf> </div> </div> </div> </div> '},71:function(a,t){a.exports=' <div class="section section-edit"> <div class=head @click.stop=sectionToggle>报名信息<span class=caret></span></div> <div class="body no-padding"> <div class="row a3"> <div class=col-sm-3> <vf label=报名学校 field=schoolname></vf> </div> <div class=col-sm-3> <vf label=入学年级 field=njdm data-dict=t_bz_nj></vf> </div> <div class=col-sm-3> <vf label=姓名 field=xm> </vf></div> <div class=col-sm-3> <vf label=性别 field=xb data-dict=t_bz_xb></vf> </div> </div> <div class="row a3"> <div class=col-sm-3> <vf label=证件类型 field=sfzlx id=int_sfzlx data-dict=t_bz_sfzlx1></vf> </div> <div class=col-sm-3> <vf label=证件号码 field=sfzjh></vf> </div> <div class=col-sm-3> <vf label=出生日期 field=csrq data-date-format=yyyy-mm-dd data-date-min-view-mode=0 data-date-start-date=1700-01-01 data-date-end-date=0d></vf> </div> <div class=col-sm-3> <vf label=民族 field=mz data-dict=t_bz_mz></vf> </div> </div> </div> </div> <div class="section section-edit"> <div class=head @click.stop=sectionToggle>户籍信息<span class=caret></span></div> <div class="body no-padding"> <div class="row a3"> <div class=col-sm-4> <vf label=国籍 field=gjdq data-dict=t_bz_gj></vf> </div> <div class=col-sm-4> <vf label=户口性质 field=hkxz data-dict=t_bz_hkxz></vf> </div> <div class=col-sm-4> <vf label=非农户口类型 field=fnhklx data-dict=t_bz_fnyhklx></vf> </div> </div> <div class="row a3"> <div class=col-sm-4> <vf label=户籍类别 field=hjlb data-dict=t_bz_hklb1 :data-value=entity.hjlb data-live-search=true data-ref=int_sfzlx data-tip=""></vf> </div> <div class=col-sm-4> <vf label=与户主关系 field=hzgx data-dict=t_bz_hzgxdm></vf> </div> <div class=col-sm-4> <vf label=户籍登记日 field=hjdjr data-date-format=yyyy-mm-dd data-date-min-view-mode=0 data-date-start-date=1700-01-01 data-date-end-date=0d></vf> </div> </div> <div class="row a3"> <div class=col-sm-4> <vf label=户口省份 field=hksf id=int_hksf data-dict=t_bz_city data-live-search=true data-filter=-1 data-tip=""></vf> </div> <div class=col-sm-4> <vf label=户口市 field=hkcity id=int_hkcity data-dict=t_bz_city :data-value=entity.hkcity data-live-search=true data-ref=int_hksf data-tip=""></vf> </div> <div class=col-sm-4> <vf label=户口所在区县 field=hkqx id=int_hkqx data-dict=t_bz_city :data-value=entity.hkqx data-live-search=true data-ref=int_hkcity data-tip=""></vf> </div> </div> <div class="row a3"> <div class=col-sm-4> <vf label=户口街镇 field=hkjz id=int_hkjz data-dict=t_bz_jd :data-value=entity.hkjz data-live-search=true data-ref=int_hkqx data-tip=""></vf> </div> <div class=col-sm-4> <vf label=户口居委会/村 field=hkjwh id=int_hkjwh data-dict=t_bz_jw :data-value=entity.hkjwh data-live-search=true data-ref=int_hkjz data-tip=""></vf> </div> <div class=col-sm-4> <vf label=是否人户一致 field=sfrhyz data-dict=t_bz_sfzd></vf> </div> </div> <div class="row a3"> <div class=col-sm-12> <vf label=户口详细地址 field=wsshkdz></vf> </div> </div> </div> </div> <div class="section section-edit"> <div class=head @click.stop=sectionToggle>基本信息<span class=caret></span></div> <div class="body no-padding"> <div class="row a3"> <div class=col-sm-4> <vf label=居住证类型 field=jzzlx data-dict=t_bz_jzzlx></vf> </div> <div class=col-sm-4> <vf label=居住证号码 field=jzzhm></vf> </div> <div class=col-sm-4> <vf label=健康状况 field=jkzk data-dict=t_bz_jkzk></vf> </div> </div> <div class="row a3"> <div class=col-sm-4> <vf mode=view label=现住址省市 field=xzzsf></vf> </div> <div class=col-sm-4> <vf label=现住址区县 field=xzzqx id=int_xzzqx data-dict=t_bz_city data-live-search=true data-filter=310100 data-tip=""></vf> </div> <div class=col-sm-4> <vf label=现住址所属街道 field=xzzjd id=int_xzzjd data-dict=t_bz_jd :data-value=entity.xzzjd data-live-search=true data-ref=int_xzzqx data-tip=""></vf> </div> </div> <div class="row a3"> <div class=col-sm-4> <vf label=现住址所属居委 field=xzzjw id=int_xzzjw data-dict=t_bz_jw :data-value=entity.xzzjw data-live-search=true data-ref=int_xzzjd data-tip=""></vf> </div> <div class=col-sm-8> <vf label=现住址地址 field=jzdz></vf> </div> </div> <div class="row a3"> <div class=col-sm-4> <vf label=联系电话 field=lxdh></vf> </div> <div class=col-sm-4> <vf label=现住地邮编 field=xzzyzbm></vf> </div> <div class=col-sm-4> <vf label=住房性质 field=zfxz data-dict=t_bz_zfxz></vf> </div> </div> <div class="row a3"> <div class=col-sm-4> <vf label=住房其他 field=zfqt></vf> </div> <div class=col-sm-4> <vf label=(租赁)合同编号 field=htbh></vf> </div> <div class=col-sm-4> <vf label=(租赁)起租日期 field=qzrq data-date-format=yyyy-mm-dd data-date-min-view-mode=0 data-date-start-date=1700-01-01 data-date-end-date=0d></vf> </div> </div> <div class="row a3"> <div class=col-sm-4> <vf label=(产权房)地址 field=cqfdz></vf> </div> <div class=col-sm-4> <vf label=(产权房)产证编号 field=czbh></vf> </div> <div class=col-sm-4> <vf label=(产权房)登记日期 field=djrq data-date-format=yyyy-mm-dd data-date-min-view-mode=0 data-date-start-date=1700-01-01 data-date-end-date=0d></vf> </div> </div> <div class="row a3"> <div class=col-sm-4> <vf label=与购房人关系 field=gfrgx data-dict=t_bz_hzgxdm></vf> </div> <div class=col-sm-4> <vf label=入园依据 field=ryyj id=int_ryyj data-dict=t_bz_ryyj data-tip=""></vf> </div> <div class=col-sm-4> <vf label=入园依据类别 field=ryyjlb data-dict=t_bz_ryyjlb :data-value=entity.ryyjlb data-live-search=true data-ref=int_ryyj data-tip=""></vf> </div> </div> <div class="row a3"> <div class=col-sm-12> <vf label=申公积分 field=sgjf></vf> </div> </div> </div> </div> <div class="section section-edit"> <div class=head @click.stop=sectionToggle>其他信息<span class=caret></span></div> <div class="body no-padding"> <div class="row a3"> <div class=col-sm-4> <vf label=是否符合计划生育政策 field=sffhjs data-dict=t_bz_sfzd></vf> </div> <div class=col-sm-4> <vf label=是否农民工同住子女 field=sfnmgtzzn data-dict=t_bz_sfzd></vf> </div> <div class=col-sm-4> <vf label=生源情况 field=syqk data-dict=t_bz_syqk></vf> </div> </div> <div class="row a3"> <div class=col-sm-4> <vf label=是否领取残疾证 field=sflqcjz data-dict=t_bz_sfzd data-value=0></vf> </div> <div class=col-sm-4> <vf label=残疾类别 field=cjlb data-dict=t_bz_cjlb></vf> </div> <div class=col-sm-4> <vf label=残疾证编号 field=cjzbh></vf> </div> </div> <div class="row a3"> <div class=col-sm-4> <vf label=残疾证发证日期 field=cjzfzrq data-date-format=yyyy-mm-dd data-date-min-view-mode=0 data-date-start-date=1700-01-01 data-date-end-date=0d></vf> </div> <div class=col-sm-8> <vf label=残疾证发证机关 field=cjzfzjg></vf> </div> </div> </div> </div> <div class="section section-edit"> <div class=head @click.stop=sectionToggle>家庭成员信息<span class=caret></span></div> <div class="body no-padding"> <div class="row a3"> <div class=col-sm-4> <vf label=姓名 field=jhrxm></vf> </div> <div class=col-sm-4> <vf label=性别 field=jhrxb data-dict=t_bz_xb></vf> </div> <div class=col-sm-4> <vf label=家庭关系 field=jhrgx data-dict=t_bz_jtgx></vf> </div> </div> <div class="row a3"> <div class=col-sm-4> <vf label=联系电话 field=jhrdh></vf> </div> <div class=col-sm-4> <vf label=证件类型 field=jhrzjlx data-dict=t_bz_sfzlx></vf> </div> <div class=col-sm-4> <vf label=证件号码 field=jhrzjhm></vf> </div> </div> <div class="row a3"> <div class=col-sm-4> <vf label=学历 field=jhrxl data-dict=t_bz_xl></vf> </div> <div class=col-sm-4> <vf label=户口省份 field=jhrhksf id=int_jhrhksf data-dict=t_bz_city data-filter=-1></vf> </div> <div class=col-sm-4> <vf label=户口市 field=jhrhks id=int_jhrhks data-dict=t_bz_city :data-value=entity.jhrhks data-live-search=true data-ref=int_jhrhksf data-tip=""></vf> </div> </div> <div class="row a3"> <div class=col-sm-4> <vf label=户口区县 field=jhrqx id=int_jhrqx data-dict=t_bz_city :data-value=entity.jhrqx data-live-search=true data-ref=int_jhrhks data-tip=""></vf> </div> <div class=col-sm-4> <vf label=居住证类型 field=jhrjzzlx data-dict=t_bz_jzzlx></vf> </div> <div class=col-sm-4> <vf label=居住证件号码 field=jhrjzzhm></vf> </div> </div> <div class="row a3"> <div class=col-sm-4> <vf label=居住证有效期 field=jhrjzzyxq data-date-format=yyyy-mm-dd data-date-min-view-mode=0 data-date-start-date=1700-01-01 data-date-end-date=2099-01-01></vf> </div> <div class=col-sm-4> <vf label=积分是否达到标准分值 field=jhrsfbzf data-dict=t_bz_sfzd></vf> </div> <div class=col-sm-4> <vf label=居住证首次办理时间 field=jhrlssj data-date-format=yyyy-mm-dd data-date-min-view-mode=0 data-date-start-date=1700-01-01 data-date-end-date=0d></vf> </div> </div> <div class="row a3"> <div class=col-sm-4> <vf label=是否持有灵活就业证 field=jhrsflhjy data-dict=t_bz_sfzd></vf> </div> <div class=col-sm-4> <vf label=灵活就业证编号 field=jhrlhjybh></vf> </div> <div class=col-sm-4> <vf label=行业类别 field=jhrsyhylb data-dict=t_bz_hylb></vf> </div> </div> <div class="row a3"> <div class=col-sm-4> <vf label=登记次数 field=jydjcs></vf> </div> <div class=col-sm-8> <vf label=工作单位 field=jhrgzdw></vf> </div> </div> </div> </div> '},73:function(a,t,i){var d,e;i(69);d=i(63);e=i(70);a.exports=d||{};if(a.exports.__esModule)a.exports=a.exports.default;if(e){(typeof a.exports==="function"?a.exports.options||(a.exports.options={}):a.exports).template=e}},74:function(a,t,i){var d,e;d=i(64);e=i(71);a.exports=d||{};if(a.exports.__esModule)a.exports=a.exports.default;if(e){(typeof a.exports==="function"?a.exports.options||(a.exports.options={}):a.exports).template=e}},369:function(a,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:true});var d=i(7);var e=f(d);var s=i(8);var l=f(s);var v=i(1056);var c=f(v);function f(a){return a&&a.__esModule?a:{"default":a}}t.default={data:function o(){return{currentMenu:"stat",currentSubmenu:"stat8"}},components:{Navbar:e.default,Topmenu:l.default,Mainarea:c.default}}},370:function(a,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:true});var d=i(1058);var e=v(d);var s=i(1057);var l=v(s);function v(a){return a&&a.__esModule?a:{"default":a}}t.default={data:function c(){return{currentView:"PxDefault",isShow:true}},events:{view_student:"view_student"},computed:{},methods:{closeRegister:function f(){this.isShow=true;this.$refs.mxstudentdetail.init("","")},view_student:function o(a){this.isShow=false;this.$refs.mxstudentdetail.init(a,"RegisterView_"+window.__global.user.districtcode)}},components:{PxDefault:e.default,MxStudentdetail:l.default},ready:function r(){window.winLog("Mainarea.vue ready")}}},371:function(a,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:true});var d=i(73);var e=v(d);var s=i(74);var l=v(s);function v(a){return a&&a.__esModule?a:{"default":a}}t.default={data:function c(){return{currentView:"",id:"",comeHere:"enroll1"}},computed:{},methods:{init:function f(a,t){this.id=a;this.currentView=t}},components:{RegisterView_310109:e.default,RegisterView_310116:l.default},ready:function o(){}}},372:function(a,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:true});var d=i(11);var e=f(d);var s=i(10);var l=f(s);var v=i(12);var c=f(v);function f(a){return a&&a.__esModule?a:{"default":a}}t.default={data:function o(){var a=window.__global.user.roletype;var t=(0,l.default)(["0","1","2","3","4"],"stat8_table",a,this);if(!t){return{}}return t},components:{Searchbar:e.default,Va:c.default},ready:function r(){this.pageNav();this.searchFlag=true;this.dataTableInit();setTimeout(function(){this.searchFlag=false;this.search()}.bind(this),1e3);window.winLog("PxDefault.vue ready")}}},456:function(a,t,i){"use strict";var d=i(4);var e=c(d);var s=i(1055);var l=c(s);var v=i(5);function c(a){return a&&a.__esModule?a:{"default":a}}window.__pageInit=function(){v.km.init(function(){window.app=new e.default({el:"body",components:{App:l.default}})})}},643:function(a,t){},644:function(a,t){},645:function(a,t){},854:function(a,t){a.exports=" <div id=app class=appv> <navbar></navbar> <topmenu :current-menu=currentMenu :current-submenu=currentSubmenu :current-parentmenu=currentParentmenu></topmenu> <mainarea v-ref:mainarea></mainarea> </div> "},855:function(a,t){a.exports=' <div class=mainarea v-show=isShow> <component :is=currentView></component> </div> <div class="mainarea mainarea-f" v-show=!isShow> <div class="pagetitle clearfix"> <span class="glyphicon glyphicon-menu-left back" @click.stop=closeRegister></span> <div class=t></div> </div> <div class=pagecontainer> <div id=d_operation class=pagebody> <mx-studentdetail v-ref:mxstudentdetail></mx-studentdetail> </div> </div> </div> '},856:function(a,t){a.exports=" <div class=actbar> </div> <components :is=currentView></components> "},857:function(a,t){a.exports=' <div class="pagetitle clearfix"> <div class=t></div> <div class=a></div> </div> <div class=pagebody> <div class=row> <div class=col-sm-12> <searchbar v-ref:searchbar :searchbar=searchbarId :searchbar-col=searchbarCol :searchbar-fields=searchbarFields></searchbar> </div> </div> <div class=row> <div id=toolbar class=btn-toolbar> <va></va> </div> <table id=table class=tb1> </table> </div> </div> '},1055:function(a,t,i){var d,e;i(643);d=i(369);e=i(854);a.exports=d||{};if(a.exports.__esModule)a.exports=a.exports.default;if(e){(typeof a.exports==="function"?a.exports.options||(a.exports.options={}):a.exports).template=e}},1056:function(a,t,i){var d,e;i(644);d=i(370);e=i(855);a.exports=d||{};if(a.exports.__esModule)a.exports=a.exports.default;if(e){(typeof a.exports==="function"?a.exports.options||(a.exports.options={}):a.exports).template=e}},1057:function(a,t,i){var d,e;d=i(371);e=i(856);a.exports=d||{};if(a.exports.__esModule)a.exports=a.exports.default;if(e){(typeof a.exports==="function"?a.exports.options||(a.exports.options={}):a.exports).template=e}},1058:function(a,t,i){var d,e;i(645);d=i(372);e=i(857);a.exports=d||{};if(a.exports.__esModule)a.exports=a.exports.default;if(e){(typeof a.exports==="function"?a.exports.options||(a.exports.options={}):a.exports).template=e}}});