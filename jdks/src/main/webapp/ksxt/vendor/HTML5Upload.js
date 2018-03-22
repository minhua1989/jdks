var SWFUpload={
	'UPLOAD_ERROR': {
		'FILE_CANCELLED': 'FILE_CANCELLED'
	},
	'QUEUE_ERROR': {
		'QUEUE_LIMIT_EXCEEDED': 'QUEUE_LIMIT_EXCEEDED',
		'FILE_EXCEEDS_SIZE_LIMIT': 'FILE_EXCEEDS_SIZE_LIMIT',
		'ZERO_BYTE_FILE': 'ZERO_BYTE_FILE',
		'INVALID_FILETYPE': 'INVALID_FILETYPE'
	}
};

function HTML5Upload(opts){
	var self=this;
	this.stats={
		in_progress:0,
		files_queued:0,
		successful_uploads:0,
		upload_errors:0,
		upload_cancelled:0,
		queue_errors:0
	};
	this.files=[];//文件列表
	this.file_types='';// 分隔符 ; 为空时不限类型
	this.file_types_description='';
	this.file_size_limit=-1;//单位(K) -1 不限
	this.file_upload_limit=-1;//允许上传的文件总数 -1 不限
	this.upload_url='';
	this.status='waiting';
	this.holder='';//holder id
	this.customSettings={};
	this.current_fileidx=-1;

	//private method
	this.checkfiletype=function (file){
		if(self.file_types==''){return null;}
    var file_types=self.file_types.replace(/\*\./g,'').split(';');
    var mimes=self.get_mimes(file_types);
		console.log(file.type+'|'+mimes);
    if(file.type!=''){
      if($.inArray(file.type,mimes)==-1){
      	return file;
      }
			return null;
    }else{
    	var ext=file.name.substring(file.name.lastIndexOf('\.') + 1);
    	if((';'+self.file_types+';').indexOf(';*.'+ext+';')==-1){
    		return file;
    	}
    	return null;
    }
	};
	this.checkfilesize=function (file){
		if(self.file_size_limit==-1){return null;}
		console.log('******size: '+file.size);
		if(file.size>parseFloat(self.file_size_limit)*1024){
			return file;
		}
		return null;
	};
	this.checkfileempty=function (file){
		if(file.size==0){
			return file;
		}
		return null;
	};
	this.checkfilelimit=function (){
		if(self.file_upload_limit==-1){return true;}
		var n=self.stats['in_progress']+self.stats['files_queued']+self.stats['successful_uploads'];
		if(n>=self.file_upload_limit){return false;}
		return true;
	};
	this.checkfiles=function (file){
    var r=self.checkfiletype(file);
    if(r!==null){
    	self.file_queue_error_handler(r,'INVALID_FILETYPE','');
    	return false;
    }
    var r=self.checkfilesize(file);
    if(r!==null){
    	self.file_queue_error_handler(r,'FILE_EXCEEDS_SIZE_LIMIT','');
    	return false;
    }
    var r=self.checkfileempty(file);
    if(r!==null){
    	self.file_queue_error_handler(r,'ZERO_BYTE_FILE','');
    	return false;
    }
    var r=self.checkfilelimit();
    if(!r){
    	self.file_queue_error_handler(null,'QUEUE_LIMIT_EXCEEDED','');
    	return false;
    }
    return true;
	};
	this.get_mimes=function (extensions){
		var mime_types={
			"gif":"image/gif"
			,"jpeg":["image/jpeg","image/pjpeg"]
			,"jpg":["image/jpeg","image/pjpeg"]
			,"jpe":["image/jpeg","image/pjpeg"]
			,"png":["image/png","image/x-png"]
			,"mp4":["video/mp4"]
			,"rar":["application/rar","application/x-rar","application/x-rar-compressed","application/octet-stream"]
			,"zip":["application/zip","application/x-zip","application/x-zip-compressed","application/octet-stream"]
			,"xlsx":["application/ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"]
			,"xls":["application/ms-excel","application/vnd.ms-excel","application/msexcel","application/x-msexcel","application/x-ms-excel","application/x-excel","application/x-dos_ms_excel","application/xls","application/x-xls"]
		};
    var mimes=[];
    for(var m in extensions){
      var ext=extensions[m];
      if(ext in mime_types){
        var mime=mime_types[ext];
        if($.isArray(mime)){
          jQuery.merge(mimes,mime);
        }else{
          mimes.push(mime);
        }
      }
    }
    return mimes;
	}

	//public method
	this.setUploadURL=function (url){
		self.upload_url=url;
		return true;
	};
	this.setPostParams=function (paramobj){
		for(var i in paramobj){
			self['post_params'][i]=paramobj[i];
		}
		return true;
	};
	this.setFileTypes=function (types,description){
		self.file_types=types;
		self.file_types_description=description;
		return true;
	};
	this.setFileSizeLimit=function (size){
		self.file_size_limit=size;
		return true;
	};
	this.setFilePostName=function (postname){
		self.file_post_name=postname;
		return true;
	};
	this.setFileUploadLimit=function (limit){
		self.file_upload_limit=limit;
		return true;
	};
	this.setStats=function (statsobj){
		self.stats=statsobj;
		return true;
	};
	this.getStats=function (){
		return self.stats;
	};
	this.cancelUpload=function (id){
		self.xhr.abort();
		var file=null;
		for(var i=0;i<self.files.length;i++){
			if(self.files[i].id==id){
				file=self.files[i];
				self.files[i].status='CANCELLED';
			}
		}
		self.upload_error_handler(file,'FILE_CANCELLED','');
	};
	this.startUpload=function (){
    var formData=new FormData();
    for(var i=0;i<self.files.length;i++){
    	if(self.files[i].status!='QUEUED'){continue;}
      formData.append(self.file_post_name,self.files[i].fo);
      self.current_fileidx=i;
      self.files[self.current_fileidx].status='IN_PROGRESS';
      break;
    }
    formData.append('postname', self.file_post_name);
    formData.append('customdata', 'abc');
    if(typeof self['customSettings']['datapath']=='undefined'){
    	self['customSettings']['datapath']='';
    }
    self.xhr=new XMLHttpRequest();
    var f=self.upload_url+'?datapath='+encodeURIComponent(self['customSettings']['datapath']);
    for(var i in self['post_params']){
    	f+='&'+i+'='+self['post_params'][i];
    }
    self.upload_start_handler(self.files[self.current_fileidx].fo);
    self.xhr.open('POST',f);
    var endpoint = window.store.get('endpoint') || '';
    self.xhr.setRequestHeader('X-My-Custom-Header', endpoint);
    self.xhr.onreadystatechange=function (){
    	console.log('%c self.xhr.readyState: '+self.xhr.readyState,'color:blue');
    };
    self.xhr.upload.onprogress=function (event){
      if(event.lengthComputable){
        self.upload_progress_handler(self.files[self.current_fileidx].fo,event.loaded,event.total);
      }
    };
    self.xhr.onload=function (){
      if(self.xhr.status===200){
        var rt=self.xhr.responseText;
        self.files[self.current_fileidx].serverData=rt;
        self.upload_success_handler(self.files[self.current_fileidx].fo,rt);
        self.upload_complete_handler(self.files[self.current_fileidx].fo);
        self.files[self.current_fileidx].status='COMPLETE';
      }else{
        console.log('xhr.responseText: '+self.xhr.responseText);
        console.log('xhr.status: '+self.xhr.status);
        var errorcode='';
        var message='';
      	self.upload_error_handler(self.files[self.current_fileidx].fo,errorcode,message);
        self.files[self.current_fileidx].status='ERROR';
      }
      //holder.className='normal';
      self.status='waiting';
    };
    self.status='uploading';
    self.xhr.send(formData);
	};

	//event handler
	this.swfupload_loaded_handler=function (){};//html5upload对象加载完成
	this.file_queued_handler=function (file){};//一个文件添加到队列里	
	this.file_queue_error_handler=function (file, errorCode, message){};//选择文件出错	
	this.file_dialog_complete_handler=function (){};//所有文件都添加到列队	
	this.upload_start_handler=function (){};//一个文件开始上传	
	this.upload_error_handler=function (file, errorCode, message){};//一个文件上传出错	
	this.upload_progress_handler=function (file, bytesLoaded, bytesTotal){};//一个文件上传中，更新进度条
	this.upload_success_handler=function (file, serverData){};//一个文件上传成功
	this.upload_complete_handler=function (){};//一个文件上传完成

	this.queuefiles=function (files){
		var n=0;
  	for(var i=0;i<files.length;i++){
			if(!self.checkfiles(files[i])){return false;}
  		var id='f_'+String(new Date().getTime())+Math.ceil(Math.random()*1000);
  		files[i].id=id;
  		self.files.push({
  			fo: files[i],
  			id: id,
  			status: 'QUEUED',
  			serverData: ''
  		});
			self.file_queued_handler(files[i]);
			n++;
  	}
  	self.file_dialog_complete_handler(n,files);
  	return true;
	};
	this.initfileinput=function (){
		var idx=self['customSettings']['idx'];
		if($('#file_'+idx).size()==0){alert('');return;}
		document.getElementById('file_'+idx).onchange=function (e){
    	e.preventDefault();
      self.queuefiles(e.target.files);
		};
	};
	this.initholder=function (){
		if(self.holder.size()==0){return;}

		window.ondragover=function (e){e.preventDefault();return false;};
		window.ondrop=function (e){e.preventDefault();return false;};
		self.holder.get(0).ondragover=function (){
			console.log('%c ondragover','color:blue');
	    if(self.status=='uploading'){return;}
	    self.holder.removeClass('ua-normal').addClass('ua-hover');
	    return false;
		};
		self.holder.get(0).ondragend=function (){
			console.log('%c ondragend','color:blue');
	    if(self.status=='uploading'){return;}
	    self.holder.removeClass('ua-hover').addClass('ua-normal');
	    return false;
		};
		self.holder.get(0).ondrop=function (e){
			console.log('%c ondrop','color:blue');
  		e.preventDefault();

      var files=e.dataTransfer.files;
      console.log(files);
	    self.holder.removeClass('ua-hover').addClass('ua-normal');
      self.queuefiles(files);

      /*
  		var files=[];
  		var items=[];
			var len = e.dataTransfer.items.length;
		  for (var i = 0; i < len; i++) {
		  	items.push(e.dataTransfer.items[i].webkitGetAsEntry());
		  }
			folderRead(items,function (file,next){
				files.push(file);
				next();
			},function (){
	      console.log(files);
		    self.holder.removeClass('ua-hover').addClass('ua-normal');
	      self.queuefiles(files);
			},true);
			*/

		};
	};

	if(opts){
		for(var i in opts){
			if(i=='custom_settings'){
				for(var j in opts['custom_settings']){
					//self['customSettings'][j]=jQuery.extend(true,{},opts['custom_settings'][j]);
					self['customSettings'][j]=opts['custom_settings'][j];
				}
			}else{
				self[i]=opts[i];
			}
		}
	}
};

// folderRead( entries,
//     function (file, next) { log(file); next(); },
//     function () { });

function folderRead(root,fileCb,doneCb,isroot){

	function processDir(entries){
	  if(entries.length>0){
	    var entry=entries.shift();
	    if(entry.isFile){
	      entry.file(function(file){
	        fileCb(file,function(){
	          processDir(entries);
	        });
	      })
	    }else{
	      folderRead(entry,fileCb,function(){
	        processDir(entries);
	      });
	    }
	  }else{
	    doneCb();
	  }
	}

	if(isroot){
	  if(root.length>0){
      var entry=root.shift();
      if(entry.isFile){
        entry.file(function(file){
          fileCb(file,function(){
            processDir(root);
          });
        })
      }else{
        folderRead(entry,fileCb,function(){
          processDir(root);
        });
      }
	  }else{
      doneCb();
	  }
	}else{
	  root.createReader().readEntries(function (entries){
		  processDir(entries);
	  });
	}
}
