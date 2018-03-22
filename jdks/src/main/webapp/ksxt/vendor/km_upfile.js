var km_upload={
	uList:{}
	,init:function (obj){
		var needs=['custom_settings.idx','custom_settings.count','custom_settings.tech','upload_url'];
    for(var i=0;i<needs.length;i++){
    	var a=(needs[i].indexOf('.')!=-1)?needs[i].split('.'):[needs[i]];
    	var b=obj;
    	for(var j=0;j<a.length;j++){
    		if(typeof b=='undefined'){window.Alert('缺少设置');return;}
    		console.log(a[j]);
    		b=b[a[j]];
    	}
    }
    var idx=obj['custom_settings']['idx'];
    if( $('#d_upload_'+idx).size()==0 ){window.Alert('缺少设置');return;}

    obj['post_params']['postname']='upfile_'+idx;
    obj['file_post_name']='upfile_'+idx;
    obj['button_placeholder_id']='btnSelect_'+idx;
    obj['custom_settings']['inited']=false;
    obj['custom_settings']['htmltemp']=km_upload_util.getDefaultHtmlTemp(obj['custom_settings']['tech'],obj['custom_settings']['holder']);
    obj['custom_settings']['filelisttemp']={};
    obj['custom_settings']['filelisttemp']['queue']=km_upload_util.getDefaultHtmlQueue();
    obj['custom_settings']['filelisttemp']['success']=km_upload_util.getDefaultHtmlSuccess();
    obj['file_upload_limit']=obj['custom_settings']['count']; //允许上传的文件总数
    obj['file_queue_limit']=obj['custom_settings']['count']; //每次最多选择几个文件
    if(typeof obj['post_params']=='undefined'){obj['post_params']={};}
    if(typeof obj['custom_settings']['autoupload']=='undefined'){obj['custom_settings']['autoupload']=false;}
    if(typeof obj['custom_settings']['ismulti']=='undefined'){obj['custom_settings']['ismulti']=false;}
    if(typeof obj['custom_settings']['files']=='undefined'){obj['custom_settings']['files']=[];}
    
    //for flash
    if(obj['custom_settings']['tech']=='flash'){
	    obj['use_query_string']=false;
	    obj['requeue_on_error']=false;
	    obj['http_success']=[201,202];
	    obj['assume_success_timeout']=0;
	    obj['prevent_swf_caching']=false;
	    obj['preserve_relative_urls']=false;
	    obj['button_disabled']=false;
	    obj['button_cursor']=SWFUpload.CURSOR.HAND;
	    obj['button_window_mode']=SWFUpload.WINDOW_MODE.TRANSPARENT;
	    obj['button_action']=obj['custom_settings']['ismulti']?SWFUpload.BUTTON_ACTION.SELECT_FILES:SWFUpload.BUTTON_ACTION.SELECT_FILE;
    }

    var s=obj['custom_settings']['htmltemp'];
    s=s.replace(/\[idx\]/g,idx);
    s=s.replace(/\[multiple\]/g,(obj['custom_settings']['ismulti'])?'multiple':'');
    var d='<div id="d_uploadcontrol_'+idx+'" class="uploadcontrol">'+s+'</div>';
    $('#d_upload_'+idx).append(d);
    $('#d_uploadcontrol_'+idx).find('.btnu').on('click',function (){
      $('#file_'+idx).trigger('click');
    });

    /* handler */
    obj['swfupload_loaded_handler']=km_upload.uploader_loaded_handler;
    obj['file_queued_handler']=km_upload.file_queued_handler;
    obj['file_queue_error_handler']=km_upload.file_queue_error_handler;
    obj['file_dialog_complete_handler']=km_upload.file_dialog_complete_handler;
    obj['upload_start_handler']=km_upload.upload_start_handler;
    obj['upload_progress_handler']=km_upload.upload_progress_handler;
    if(typeof obj['custom_settings']['upload_start_handler']!='undefined'){
      obj['upload_start_handler']=obj['custom_settings']['upload_start_handler'];
    }
    if(typeof obj['custom_settings']['upload_progress_handler']!='undefined'){
      obj['upload_progress_handler']=obj['custom_settings']['upload_progress_handler'];
    }
    obj['upload_error_handler']=km_upload.upload_error_handler;
    obj['upload_success_handler']=km_upload.upload_success_handler;
    obj['upload_complete_handler']=km_upload.upload_complete_handler;

    if(obj['custom_settings']['tech']=='flash'){
      km_upload.uList[idx]=new SWFUpload(obj);
    }else if(obj['custom_settings']['tech']=='html5'){
      km_upload.uList[idx]=new HTML5Upload(obj);
      km_upload.uList[idx].setPostParams(obj['post_params']);
      km_upload.uList[idx].swfupload_loaded_handler();
      km_upload.uList[idx].initfileinput();
      km_upload.uList[idx].initholder();//允许拖拽
    }
	}
	,initFiles:function (idx){ //初始化已上传的文件
    if(km_upload.uList[idx]['customSettings']['inited']){return;}
    km_upload.uList[idx]['customSettings']['inited']=true;
    var m=0;
    var n=km_upload.uList[idx]['customSettings']['files'].length;
    var a=[];
    for(var i=0;i<n;i++){
      if(typeof km_upload.uList[idx]['customSettings']['files'][i]['filestatus']=='undefined'||km_upload.uList[idx]['customSettings']['files'][i]['filestatus']!='COMPLETE'){
        continue;
      }
      if(typeof km_upload.uList[idx]['customSettings']['files'][i]['serverdata']=='undefined'||km_upload.uList[idx]['customSettings']['files'][i]['serverdata']==''){
        continue;
      }
      var id=km_upload.uList[idx]['customSettings']['files'][i]['id'];
      if(id==''){
        id='f_'+String(new Date().getTime())+Math.ceil(Math.random()*1000);
        km_upload.uList[idx]['customSettings']['files'][i]['id']=id;
      }
      var name=km_upload.uList[idx]['customSettings']['files'][i]['name'];
      var serverdata=km_upload.uList[idx]['customSettings']['files'][i]['serverdata'];

      if(typeof km_upload.uList[idx]['customSettings']['initFileListTemp']=='function'){
        var d=km_upload.uList[idx]['customSettings']['initFileListTemp'](idx,km_upload.uList[idx]['customSettings']['files'][i]);
      }else{
        var d=$d.cc(null,'div',[['class','filelist']]);
        var s=km_upload.uList[idx]['customSettings']['filelisttemp']['success'];
        s=s.replace(/\[id\]/g,id);
        s=s.replace(/\[idx\]/g,idx);
        s=s.replace(/\[name\]/g,name);
        s=s.replace(/\[filetype\]/g,km_upload_util.getFileTypeClassName(name,'file'));
        d.innerHTML=s;
      }
      $('#d_upload_'+idx).parent().parent().find('.pro').append(d);
      $('#hidFileID_'+idx+'_'+id).val(serverdata);
      a.push(km_upload.uList[idx]['customSettings']['files'][i]);
      m++;
    }
    km_upload.uList[idx]['customSettings']['files']=a;
    km_upload.uList[idx].setStats({
      in_progress:0
      ,files_queued:0
      ,successful_uploads:m
      ,upload_errors:0
      ,upload_cancelled:0
      ,queue_errors:0
    });
    km_upload.showlist(idx);
	}
	,updateStats: function (idx,stats){
		var s=km_upload.uList[idx].getStats();
		for(var i in stats){
			// console.log('%c '+i+' '+stats[i],'color:orange');
			if(stats[i].charAt(0)=='+'){s[i]=parseInt(s[i],10)+parseInt(stats[i].substring(1),10);}
			if(stats[i].charAt(0)=='-'){s[i]=parseInt(s[i],10)-parseInt(stats[i].substring(1),10);}
		}
		km_upload.uList[idx].setStats(s);
	}
	,next:function (idx){
		var s=km_upload.uList[idx].getStats();
		if(s.files_queued>0){
			km_upload.startUpload(idx);
		}else{
			//列队里的全部文件上传完成
			if(typeof km_upload.uList[idx]['customSettings']['afterUpload']!='undefined'){
				km_upload.uList[idx]['customSettings']['afterUpload']();
			}
		}
	}
	,startUpload:function (idx){
		var s=km_upload.uList[idx].getStats();
		if(s.files_queued==0){
			window.Alert('请选择文件');
			return;
		}
		$("#btnUpload_"+idx).hide();
		km_upload.updateStats(idx,{
			in_progress: '+1',
			files_queued: '-1'
		});
		km_upload.uList[idx].startUpload();
	}
	,delelem:function (id,idx,elem){
		if(!elem){elem=$('#'+id+'_'+idx+'_status').get(0).parentNode;}
		elem.parentNode.removeChild(elem);

		km_upload.updateStats(idx,{
			successful_uploads: '-1'
		});

		var a=[];
		var n=km_upload.uList[idx]['customSettings']['files'].length;
		for(var i=0;i<n;i++){
			var fid=km_upload.uList[idx]['customSettings']['files'][i].id;
			if(fid!=id){
				a.push(km_upload.uList[idx]['customSettings']['files'][i]);
			}
		}
		km_upload.uList[idx]['customSettings']['files']=a;
		km_upload.showlist(idx);

		if(typeof km_upload.uList[idx]['customSettings']['afterDelete']=='function'){
			km_upload.uList[idx]['customSettings']['afterDelete'](idx);
		}
	}
	,updatefile:function (id,idx,uo){
		var n=km_upload.uList[idx]['customSettings']['files'].length;
		for(var i=0;i<n;i++){
			var fid=km_upload.uList[idx]['customSettings']['files'][i].id;
			if(fid==id){
				for(var j in uo){
					km_upload.uList[idx]['customSettings']['files'][i][j]=uo[j];
				}
				break;
			}
		}
	}
	,showlist:function (idx){
		km_upload.log(JSON.stringify(km_upload.uList[idx].getStats()));

		var n=km_upload.uList[idx]['customSettings']['files'].length;
		if(n<km_upload.uList[idx]['customSettings'].count){
			$('#d_uploadcontrol_'+idx).get(0).children[0].style.width=km_upload.uList[idx]['customSettings']['button_width']+'px';
			$('#d_uploadcontrol_'+idx).get(0).children[1].style.width='300px';
			$('#d_uploadcontrol_'+idx).get(0).children[1].style.paddingLeft='20px';
		}else{
			$('#d_uploadcontrol_'+idx).get(0).children[0].style.width='0px';
			$('#d_uploadcontrol_'+idx).get(0).children[1].style.width='0px';
			$('#d_uploadcontrol_'+idx).get(0).children[1].style.paddingLeft='0px';
		}

		var s=km_upload.uList[idx].getStats();
		var files_queued=parseInt(s['files_queued'],10);
		if(files_queued>0){
			$('#btnUpload_'+idx).get(0).style.display='inline-block';
		}else{
			$('#btnUpload_'+idx).get(0).style.display='none';
		}
    $('#d_upload_' + idx).css({
      // visibility: 'visible',
      height: '33px',
      overflowY: 'hidden'
    })
		if($('#btnUpload_'+idx).closest('.d-upload').find('.pro .filelist').size() > 0){
			$('#btnUpload_'+idx).closest('.d-upload').find('.icon i').remove();
		}else{
			$('#btnUpload_'+idx).closest('.d-upload').find('.icon').append('<i class="glyphicon glyphicon-cloud-upload"></i>');
		}

		if($('#d_uploadcontrol_'+idx).get(0).children[0].style.width=='0px'&&$('#btnUpload_'+idx).get(0).style.display=='none'){
			$('#d_uploadcontrol_'+idx).get(0).style.height='0px';
		}else{
			$('#d_uploadcontrol_'+idx).get(0).style.height='22px';
		}

		for(var i=0;i<n;i++){
			var id=km_upload.uList[idx]['customSettings']['files'][i]['id'];
			var index=km_upload.uList[idx]['customSettings']['files'][i]['index'];
			var filestatus=km_upload.uList[idx]['customSettings']['files'][i]['filestatus'];
			var serverdata=km_upload.uList[idx]['customSettings']['files'][i]['serverdata'];

			km_upload.log('showlist');
			km_upload.log('id:'+id+'\nindex:'+index+'\nfilestatus:'+filestatus+'\nserverdata:'+serverdata);

			if(filestatus=='QUEUED'||filestatus==-1){

			}else if(filestatus=='IN_PROGRESS'||filestatus==-2){

			}else if(filestatus=='ERROR'){

			}else if(filestatus=='COMPLETE'||filestatus==-4){

			}else if(filestatus=='CANCELLED'||filestatus==-5){

			}
		}
	}
	,log:function (str){
		console.log(str);
	}

	/* method
	 * 动态设置上传对象的参数
	 */
	,setUploadURL:function (idx,url){
		km_upload.uList[idx].setUploadURL(url);
	}
	,setPostParams:function (idx,paramobj){
		km_upload.uList[idx].setPostParams(paramobj);
	}
	,setFileTypes:function (idx,types,description){
		km_upload.uList[idx].setFileTypes(types,description);
	}
	,setFileSizeLimit:function (idx,size){
		km_upload.uList[idx].setFileSizeLimit(size);
	}
	,setFilePostName:function (idx,postname){
		km_upload.uList[idx].setFilePostName(postname);
	}
	,setFileUploadLimit:function (idx,limit){
		km_upload.uList[idx].setFileUploadLimit(limit);
	}

	/* handler
	 * function 中的 this 代表 HTML5Upload 对象 即 km_upload.uList[idx]
	 */
	,uploader_loaded_handler:function (){ //HTML5Upload 或者 swf 对象加载完成
		var idx=this['customSettings'].idx;
		km_upload.initFiles(idx);
	}
	,file_queued_handler:function (file){
		km_upload.log('===file_queued_handler: file='+file.id);
		this['customSettings'].files.push(file);
		var idx=this['customSettings'].idx;

		var s=this['customSettings']['filelisttemp']['queue'];
		s=s.replace(/\[idx\]/g,idx);
		s=s.replace(/\[id\]/g,file.id);
		s=s.replace(/\[name\]/g,file.name);
		s=s.replace(/\[filetype\]/g,km_upload_util.getFileTypeClassName(name,'file'));
		var d='<div class="filelist">'+s+'</div>';
		$('#d_upload_'+idx).parent().parent().find('.pro').append(d);
		if(document.recalc){document.recalc();}
	}
	,file_queue_error_handler:function (file, errorCode, message){
		var idx=this['customSettings'].idx;
		km_upload.updateStats(idx,{
			queue_errors: '+1'
		});
		switch (errorCode) {
		case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
			window.Alert("选择的文件数超过限制。");
			return;
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			window.Alert("选择的文件大小超过限制");
			return;
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			window.Alert("选择的文件是空文件。");
			return;
		case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
			window.Alert("选择的文件类型不正确。");
			return;
		default:
			window.Alert("Error Code: "+errorCode+", File name: "+file.name+", File size: "+file.size+", Message: "+message);
			return;
		}
	}
	,file_dialog_complete_handler:function (numFilesSelected, numFilesQueued){ //所有选择的文件都已添加到队列中
		km_upload.log('===file_dialog_complete_handler '+ numFilesSelected);
		if(numFilesSelected==0){return;}
		var idx=this['customSettings'].idx;
		km_upload.updateStats(idx,{
			files_queued: '+'+numFilesSelected
		});
		if(!this['customSettings']['autoupload']){
			km_upload.showlist(idx);
		}else{
			km_upload.startUpload(idx);
		}
	}
	,upload_start_handler:function (file){
		var idx=this['customSettings'].idx;
		var s="";
		s+="开始上传";
		$('#'+file.id+'_'+idx+'_status').get(0).innerHTML=s;
		km_upload.updatefile(file.id,idx,{filestatus:'IN_PROGRESS'});
	}
	,upload_progress_handler:function (file, bytesLoaded, bytesTotal){
		var percent=Math.ceil((bytesLoaded/bytesTotal)*100);
		var idx=this['customSettings'].idx;
		var s="";
		s+="正在上传，"+percent+"%";
		$('#'+file.id+'_'+idx+'_status').get(0).innerHTML=s;
	}
	,upload_error_handler:function (file, errorCode, message){
		var idx=this['customSettings'].idx;
		if(errorCode===SWFUpload.UPLOAD_ERROR.FILE_CANCELLED){ //用户取消了上传
			var s="";
			s+="已取消";
			$('#'+file.id+'_'+idx+'_status').get(0).innerHTML=s;
			$('#'+file.id+'_'+idx+'_action').get(0).innerHTML='';

			km_upload.updateStats(idx,{
				upload_cancelled: '+1',
				in_progress: '-1'
			});

			km_upload.updatefile(file.id,idx,{filestatus:'CANCELLED'});

			//从files中删除，等同于删除操作
			/*
			var a=[];
			var n=km_upload.uList[idx]['customSettings']['files'].length;
			for(var i=0;i<n;i++){
				var fid=km_upload.uList[idx]['customSettings']['files'][i].id;
				if(fid!=file.id){
					a.push(km_upload.uList[idx]['customSettings']['files'][i]);
				}
			}
			km_upload.uList[idx]['customSettings']['files']=a;
			km_upload.showlist(idx);
			*/

			if(typeof km_upload.uList[idx]['customSettings']['afterDelete']!='undefined'){
				km_upload.uList[idx]['customSettings']['afterDelete'](idx);
			}

			km_upload.next(idx);
			return;
		}
		
		if(!window['__global']['FileAPI_support']){
			switch (errorCode) {
			case SWFUpload.UPLOAD_ERROR.MISSING_UPLOAD_URL:
				window.Alert("没有设置服务器端处理程序的URL。");
				break;
			case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
				window.Alert("一次只能上传一个文件。");
				break;
			case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
			case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
				break;
			default:
				window.Alert("Error Code: "+errorCode+", File name: "+file.name+", File size: "+file.size+", Message: "+message);
				break;
			}
		}else{
			window.Alert("Error Code: "+errorCode+", File name: "+file.name+", File size: "+file.size+", Message: "+message);
		}

		km_upload.updateStats(idx,{
			upload_errors: '+1',
			in_progress: '-1'
		});

		km_upload.updatefile(file.id,idx,{filestatus:'ERROR'});
		km_upload.showlist(idx);

		km_upload.next(idx);
	}
	,upload_success_handler:function (file, serverData){
		km_upload.log('===upload_success_handler serverData: '+serverData);

		var idx=this['customSettings'].idx;
		$('#hidFileID_'+idx+'_'+file.id).val(serverData);
		km_upload.updatefile(file.id,idx,{serverdata:serverData});

		km_upload.updateStats(idx,{
			successful_uploads: '+1',
			in_progress: '-1'
		});

		if(typeof this['customSettings']['afterComplete']!='undefined'){
			this['customSettings']['afterComplete'](idx,file);
		}
	}
	,upload_complete_handler:function (file){
		km_upload.log('===upload_complete_handler');

		var idx=this['customSettings'].idx;
		var s="";
		s+="已上传";
		$('#'+file.id+'_'+idx+'_status').get(0).innerHTML=s;
		var s="";
		//s+="<span class='txt-t txt-blue' onclick=\"km_upload.delelem('"+file.id+"','"+idx+"')\">删除</span>";
		s+='<i class="text-success glyphicon glyphicon-ok-circle"></i>';
		$('#'+file.id+'_'+idx+'_action').get(0).innerHTML=s;
		km_upload.updatefile(file.id,idx,{filestatus:'COMPLETE'});
		km_upload.showlist(idx);
		km_upload.next(idx);
	}
};

var km_upload_util={
  getFileTypeClassName:function (name,type){
    var ext = name.substring(name.lastIndexOf('.')+1);
    var ft = (typeof public_ext[ext]=='undefined')?'glyphicon glyphicon-file':public_ext[ext];
    var filetype = '' + ft + '';
    if(type=='directory'){
      filetype = 'icon-folder';
    }
    var s='<i class="'+filetype+'"></i>';
    return s;
  },
  getDefaultHtmlTemp:function (t,holder){
    if(t=='html5'){
      var s="";
      s+="<div class='btn-upload'>";
        s+="<div><input id='file_[idx]' type='file' [multiple]></div>";
        s+="<div class='btnu'>添加文件...</div>";
      s+="</div>";
      if(holder){
	      s+="<div class='btn-upload' style='padding-left:20px;width:300px;color:#aaa;overflow:hidden;'>";
	      s+="点击“添加文件...”或拖拽文件到下框";
	      s+="</div>";
      }
      s+="<div class='btn-upload'>";
        s+="<input id='btnUpload_[idx]' type='button' value='开始上传' class='btn btn-small' onclick=\"km_upload.startUpload('[idx]')\"/>";
      s+="</div>";
    }else if(t=='flash'){
      var s="";
      s+="<div class='btn-upload'>";
        s+="<div class='btnu'>选择文件...</div>";
        s+="<span id='btnSelect_[idx]'></span>&nbsp;";
      s+="</div>";
      s+="<div class='btn-upload'>";
        s+="<input id='btnUpload_[idx]' type='button' value='上传' class='btn btn-small' onclick=\"km_upload.startUpload('[idx]')\"/>";
      s+="</div>";
    }
    return s;
  },
  getDefaultHtmlQueue:function (){
    var s='';
    s+='<div class="filetype">[filetype]</div>';
    s+='<div class="fileinfo">';
      s+='<div id="[id]_[idx]" class="name">';
        s+='[name] <input type="hidden" name="hidFileID_[idx]" id="hidFileID_[idx]_[id]" value="">';
      s+='</div>';
      s+='<div id="[id]_[idx]_status" class="status">等待上传</div>';
    s+='</div>';
    s+='<div id="[id]_[idx]_action" class="action">';
      //s+='<a href="javascript:;" onclick=\'km_upload.uList["[idx]"].cancelUpload("[id]")\'>';
      s+='<i class="glyphicon glyphicon-minus-sign"></i>';
      //s+='</a>';
    s+='</div>';
    return s;
  },
  getDefaultHtmlSuccess:function (){
    var s='';
    s+='<div class="filetype">[filetype]</div>';
    s+='<div class="fileinfo">';
      s+='<div id="[id]_[idx]" class="name">';
        s+='[name] <input type="hidden" name="hidFileID_[idx]" id="hidFileID_[idx]_[id]" value="">';
      s+='</div>';
      s+='<div id="[id]_[idx]_status" class="status">已上传</div>';
    s+='</div>';
    s+='<div id="[id]_[idx]_action" class="action">';
      s+='<a href="javascript:;" onclick=\'km_upload.delelem("[id]","[idx]")\'>删除</a>';
      //s+='<i class="glyphicon glyphicon-ok-circle"></i>';
    s+='</div>';
    return s;
  }
};

var public_ext = {
  'xlsx': 'glyphicon glyphicon-file',
  'xls': 'glyphicon glyphicon-file',
  'pptx': 'glyphicon glyphicon-file',
  'ppt': 'glyphicon glyphicon-file',
  'docx': 'glyphicon glyphicon-file',
  'doc': 'glyphicon glyphicon-file',

  'txt': 'glyphicon glyphicon-file',
  'md': 'glyphicon glyphicon-file',

  'zip': 'glyphicon glyphicon-file',
  'rar': 'glyphicon glyphicon-file',
  
  'mp3': 'glyphicon glyphicon-file',
  'wav': 'glyphicon glyphicon-file',
  'wma': 'glyphicon glyphicon-file',
  'asf': 'glyphicon glyphicon-file',
  'mid': 'glyphicon glyphicon-file',
  'm4a': 'glyphicon glyphicon-file',
  
  'mp4': 'glyphicon glyphicon-file',
  'avi': 'glyphicon glyphicon-file',
  'ogg': 'glyphicon glyphicon-file',
  'wmv': 'glyphicon glyphicon-file',
  'mpeg': 'glyphicon glyphicon-file',
  'mkv': 'glyphicon glyphicon-file',
  'rmvb': 'glyphicon glyphicon-file',
  'mov': 'glyphicon glyphicon-file',
  
  'jpg': 'glyphicon glyphicon-file',
  'jpeg': 'glyphicon glyphicon-file',
  'png': 'glyphicon glyphicon-file',
  'gif': 'glyphicon glyphicon-file',
  'bmp': 'glyphicon glyphicon-file',
  'webm': 'glyphicon glyphicon-file',
  'tiff': 'glyphicon glyphicon-file',
  'eps': 'glyphicon glyphicon-file'
};

