create or replace view vw_userkscc_hm as
select ks.userid,kscc.kscc,kscc.used from t_user_kscc ks
left join t_ksccmg kscc  on ks.ksccid=kscc.id;

create or replace view v_kckwy_rel as
		select a.id,a.kcid,a.kwyid,a.deleted,b.realname,c.kcname,a.ksccid from t_kckwy_rel a
	left join t_admininfo b on a.kwyid=b.id
  left join t_kcinfo c on a.kcid=c.id
          where   a.deleted='0' and b.deleted='0' and c.deleted='0'  and b.used='0' and c.used='0'  ;
          
create or replace view  v_UserKscc as
select ks.userid,kscc.kscc,kscc.used,ks.ksccid,kscc.starttime,kscc.endtime,kscc.tmgz,kscc.dagz from t_user_kscc ks
left join t_ksccmg kscc  on ks.ksccid=kscc.id
        where kscc.deleted='0';
 
 create or replace view  v_kckscc_rel_hm as       
        		select a.id,a.kcid,a.ksccid,a.deleted,b.kscc,c.kcname,c.maxrs,b.nf,replace(b.ksrq,"-","") ksrq,b.dj from t_kckscc_rel a
	left join t_ksccmg b on a.ksccid=b.id
  left join t_kcinfo c on a.kcid=c.id
        where  a.deleted='0' and b.deleted='0' and c.deleted='0'  and b.used='0' and c.used='0' ;
        
 create or replace view  v_user_kscc_hm as       
        select ks.userid,kscc.kscc,kscc.used,ks.ksccid,kscc.starttime,kscc.endtime,kscc.tmgz,kscc.dagz from t_user_kscc ks
left join t_ksccmg kscc  on ks.ksccid=kscc.id
        where 1=1 and kscc.deleted='0';
        
create or replace view v_ksccmg_hm as
		select id,kscc,starttime,endtime,danxs,danxfs,duoxs, duoxfs ,pdts,pdtfs,addtime,tmgz,dagz,zfs from t_ksccmg a
        where  deleted='0'          ;
        
 create or replace view  v_sttitle_hm as       
		select id,title,content,stlx,
		(	CASE   stlx 
WHEN '1' THEN "单选题"
WHEN '2' THEN "多选题"
WHEN '3' THEN "判断题"
WHEN '4' THEN "操作题"
        ELSE ""
    END
		)
		stlxmc,sydj,(
		CASE  used  
WHEN '0' THEN "有效"
WHEN '1' THEN "无效"
        ELSE ""
    END
		) used from t_sttitle
        where deleted='0'       ;
        
create or replace view v_userinfo as
select `a`.`ID` AS `ID`,`a`.`ksxtsfzjh` AS `ksxtsfzjh`,`b`.`ksxtzkzh` AS `ksxtzkzh`,`a`.`ksxtxm` AS `ksxtxm`,
`a`.`used` AS `USED`,`b`.`ksccid` AS `ksccid`,`d`.`name` AS `usedname`,`a`.`addtime` AS `addtime`,`a`.`adduser` AS `adduser`,
`a`.`updatetime` AS `updatetime`,`a`.`updateuser` AS `updateuser`,(case `b`.`sfdl` when '0' then '未登录' when '1' then '已登录' else '未登录' end) AS `sfdlmc`,
`b`.`sfdl` AS `sfdl`,`c`.`kscc` AS `kscc`,`a`.`deltime` AS `deltime`,`a`.`deleted` AS `deleted`,`b`.`zwh` AS `zwh` ,c.nf,c.ksrq,c.starttime,c.endtime,c.dj
 from (((`t_userinfo` `a` left join `t_user_kscc` `b` on(((`a`.`ID` = `b`.`userid`) and (`b`.`used` = '0')))) 
 left join `t_ksccmg` `c` on(((`b`.`ksccid` = `c`.`id`) and (`c`.`used` = '0')))) 
 left join `t_bz_tab` `d` on(((`a`.`used` = `d`.`code`) and (`d`.`type` = 2))));
 
 create or replace view v_ksccmg as
select `t_ksccmg`.`kslx` AS `kslx`,`t_ksccmg`.`id` AS `id`,`t_ksccmg`.`kscc` AS `kscc`,`t_ksccmg`.`starttime` AS `starttime`,`t_ksccmg`.`endtime` AS `endtime`,
`t_ksccmg`.`danxs` AS `danxs`,`t_ksccmg`.`danxfs` AS `danxfs`,`t_ksccmg`.`duoxs` AS `duoxs`,`t_ksccmg`.`duoxfs` AS `duoxfs`,`t_ksccmg`.`pdts` AS `pdts`,
`t_ksccmg`.`pdtfs` AS `pdtfs`,`t_ksccmg`.`used` AS `used`,`t_ksccmg`.`addtime` AS `addtime`,`t_ksccmg`.`adduser` AS `adduser`,`t_ksccmg`.`adduserid` AS `adduserid`,
`t_ksccmg`.`updateuser` AS `updateuser`,`t_ksccmg`.`updateuserid` AS `updateuserid`,`t_ksccmg`.`updatetime` AS `updatetime`,`t_ksccmg`.`deltime` AS `deltime`,
`t_ksccmg`.`deluser` AS `deluser`,`t_ksccmg`.`deluserid` AS `deluserid`,`t_ksccmg`.`remark` AS `remark`,`t_ksccmg`.`tmgz` AS `tmgz`,`t_ksccmg`.`dagz` AS `dagz`,
`t_ksccmg`.`deleted` AS `deleted`,`c`.`name` AS `dagzmc`,`b`.`name` AS `tmgzmc`,`d`.`name` AS `sydjmc`,`t_ksccmg`.`dj` AS `dj`,`t_ksccmg`.`kszcll` AS `kszcll`,
`t_ksccmg`.`kszcsc` AS `kszcsc`,`t_ksccmg`.`nf` AS `nf`,`t_ksccmg`.`ksrq` AS `ksrq`,`t_ksccmg`.`zfs` AS `zfs`,`t_ksccmg`.`kszcll` AS `lljk`,
`t_ksccmg`.`kszcsc` AS `scks`,`t_ksccmg`.`kslx` AS `ksxl`,`t_ksccmg`.`sfcjsc` AS `sfcjsc`,CONCAT(t_ksccmg.nf,t_ksccmg.ksrq,t_ksccmg.starttime) kssj  
 from (((`t_ksccmg` 
 left join `t_bz_tab` `b` on(((`t_ksccmg`.`tmgz` = `b`.`code`) and (`b`.`type` = '6') and (`t_ksccmg`.`deleted` = '0')))) 
 left join `t_bz_tab` `c` on(((`t_ksccmg`.`dagz` = `c`.`code`) and (`c`.`type` = '7')))) 
 left join `t_bz_tab` `d` on(((`t_ksccmg`.`dj` = `d`.`code`) and (`d`.`type` = '5')))) order by kssj desc;
 
 CREATE OR REPLACE VIEW vf_all_bz_map AS
 	select type,code,name ch
	 from t_bz_tab;
	 
create or replace view v_userinfo as
select `a`.`ID` AS `ID`,`a`.`ksxtsfzjh` AS `ksxtsfzjh`,`b`.`ksxtzkzh` AS `ksxtzkzh`,`a`.`ksxtxm` AS `ksxtxm`,
`a`.`used` AS `USED`,`b`.`ksccid` AS `ksccid`,`d`.`name` AS `usedname`,`a`.`addtime` AS `addtime`,`a`.`adduser` AS `adduser`,
`a`.`updatetime` AS `updatetime`,`a`.`updateuser` AS `updateuser`,(case `b`.`sfdl` when '0' then '未登录' when '1' then '已登录' else '未登录' end) AS `sfdlmc`,
`b`.`sfdl` AS `sfdl`,`c`.`kscc` AS `kscc`,`a`.`deltime` AS `deltime`,`a`.`deleted` AS `deleted`,`b`.`zwh` AS `zwh` ,c.nf,c.ksrq,c.starttime,c.endtime,c.dj,CONCAT(c.nf,c.ksrq,c.starttime) kssj ,e.name as djmc
 from (((`t_userinfo` `a` left join `t_user_kscc` `b` on(((`a`.`ID` = `b`.`userid`) and (`b`.`used` = '0')))) 
 left join `t_ksccmg` `c` on(((`b`.`ksccid` = `c`.`id`) and (`c`.`used` = '0')))) 
 left join `t_bz_tab` `d` on(((`a`.`used` = `d`.`code`) and (`d`.`type` = 2)))
left join `t_bz_tab` `e` on(((`c`.`dj` = `e`.`code`) and (`e`.`type` = '5'))));

create or replace view v_kssjxx_log_hm as
select u.ksxtxm,u.ksxtsfzjh,uk.ksxtzkzh,uk.zwh,kc.kscc,t.ksccid,t.ip,t.logtype,
(
		CASE  t.logtype  
WHEN '1' THEN "登录"
WHEN '2' THEN "考试记录"
        ELSE ""
    END
		) logtypename ,t.sttitle,t.stlx,
		(	CASE   t.stlx 
WHEN '1' THEN "单选题"
WHEN '2' THEN "多选题"
WHEN '3' THEN "判断题"
WHEN '4' THEN "操作题"
        ELSE ""
    END
		)
		stlxmc,
t.xuanxiangmc,t.xsda,t.addtime,t.stxh from t_kssjxx_log t 
left join t_userinfo u on u.ID=t.ksid
left join t_user_kscc uk on uk.ksccid=t.ksccid and uk.userid=t.ksid
left join t_ksccmg kc on kc.id=t.ksccid
order by addtime desc;

create or replace view v_kszqts4 as
select `t_kssjxx`.`ksid` AS `ksid`,`t_kssjxx`.`ksccid` AS `ksccid`,`cc`.`id` AS `userksccid`,sum(`t_kssjxx`.`fenshu`) AS `llksfs` from (`t_kssjxx` left join `t_user_kscc` `cc` on(((`cc`.`userid` = `t_kssjxx`.`ksid`) and (`cc`.`ksccid` = `t_kssjxx`.`ksccid`)))) where ((`t_kssjxx`.`bzda` = '1') and (`t_kssjxx`.`xsda` = '1') and (`t_kssjxx`.`deleted` = '0')
and (`t_kssjxx`.`sftjsj` = '1')) 
group by `t_kssjxx`.`ksid`,`t_kssjxx`.`ksccid`,`cc`.`id`;

 create or replace view v_queryAllMenuinfo_hm as
select 
(	CASE   lv 
WHEN 1 THEN name
        ELSE CONCAT('    ',name)
    END
		) name1,name,id,parentid,"DESC" from t_menuinfo order by orderno asc;
		
		
		
CREATE OR REPLACE VIEW V_ROLE_MENUS AS
SELECT
    r.ROLEID,
    m.ID,m.NAME,m.DESC,m.LV,m.PARENTID,m.SORT
  FROM T_ROLEMENUS r LEFT JOIN T_MENUINFO m ON r.MENUID = m.ID
  ORDER BY r.ROLEID, m.lv, m.sort;
  
create or replace view v_pxpcinfo as
select `a`.`id` AS `id`,`a`.`pxpcmc` AS `pxpcmc`,`a`.`pxpcdm` AS `pxpcdm`,
`a`.`pxjg` AS `pxjg`,`a`.`nf` AS `nf`,`a`.`dj` AS `dj`,`a`.`used` AS `used`,
`a`.`starttime` AS `starttime`,`a`.`endtime` AS `endtime`,`a`.`pxrs` AS `pxrs`,
`a`.`addtime` AS `addtime`,`a`.`updatetime` AS `updatetime`,
`a`.`deleted` AS `deleted`,`a`.`deltime` AS `deltime`,`a`.`code` AS `code`,
`b`.`name` AS `djmc` from (`t_pxpcinfo` `a` 
left join `t_bz_tab` `b` on(((`a`.`dj` = `b`.`code`) and (`b`.`type` = '5'))))
order by a.nf desc,a.starttime desc;


 create or replace view  v_kckscc_rel_hm as       
        		select a.id,a.kcid,a.ksccid,a.deleted,b.kscc,c.kcname,c.maxrs,b.nf,replace(b.ksrq,"-","") ksrq,b.dj,
CONCAT(b.nf,b.ksrq,b.starttime) kssj  				from t_kckscc_rel a
	left join t_ksccmg b on a.ksccid=b.id
  left join t_kcinfo c on a.kcid=c.id
        where  a.deleted='0' and b.deleted='0' and c.deleted='0'  and b.used='0' and c.used='0' ;
       
        create or replace view  v_kskckwy as  
        select `a`.`ksccid` AS `ksccid`,`a`.`kcid` AS `kcid`,`a`.`kcname` AS `kcname`,`b`.`kwymc` AS `kwymc`,
`a`.`kscc` AS `ksccname`,
a.kssj as kssj from (`v_kckscc_rel_hm` `a` 
left join `v_kwyname2` `b` on(((`a`.`ksccid` = `b`.`ksccid`) and (`a`.`kcid` = `b`.`kcid`)))) order by kssj desc;