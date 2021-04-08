var CategoryJs = function () {
    var handleValidation = function() {
        $('#form').validate({
            errorElement: 'span',
            errorClass: 'help-block',
            focusInvalid: false,
            ignore: "",
            rules: {
            	"category.parentids":{
					required : true
				},
				"category.parentname":{
					required : true
				},
				"category.name":{
					required:true,
					minlength:2
				},
				"category.nid" : {
					required : true,
					minlength:2
				},
				"category.sort":{
					number:true,
					required:true
				},
				"category.showmodes":{
					required : true
				}
            },
            invalidHandler: function (event, validator) {
            },
            highlight: function(element){
                $(element).closest('.form-group').addClass('has-error');
            },
            unhighlight: function(element){
                $(element).closest('.form-group').removeClass('has-error');
            },
            success: function(label){
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },
            submitHandler: function (form) {
            	var index = parent.layer.getFrameIndex(window.name),
            		successMsg = "操作成功!",
                	failedMsg = "操作失败!",
					title = "系统提示",
                	loading = layer.load(2, { shade: [0.3,'#fff']});
                $.ajax({
             	   	type: "POST",
             	   	async:false,
             	   	data:$(form).serialize(),
             	   	url: "/modules/category/saveOrUpdate",
             	   	success: function(data){
             	   		if (data.result){
							parent.notifications('success',successMsg,title);
             	   			window.location.href="/modules/category/index";
                        }else{
							parent.notifications('error',data.msg,title);
                        }
             	   },
             	   error:function(XMLHttpRequest, textStatus, errorThrown){
					   parent.notifications('error','操作失败,请检查网络!',title);
             	   },
             	   complete:function(){
             		   layer.close(loading);
             		   parent.layer.close(index);
             	   }
             	});
            }
        });
    }
    
    var saveSort = function(){
    	$.ajax({
     	   	type: "POST",
     	   	async:false,
     	   	data:$("#sort").serialize(),
     	   	url: "/modules/category/saveSort",
     	   	success: function(data){
     	   		if (data.result){
	     	   		layer.alert('保存成功',{
	     	   			skin: 'layui-layer-molv',
	     	   			closeBtn: 0
	     	   		}, function(){
	     	   			window.location.href="/modules/category/index";
	     	   		});
                }else{
                	parent.layer.alert(data.msg,{title:'提示', icon:2, closeBtn: 0 });
                }
     	   },
     	   error:function(XMLHttpRequest, textStatus, errorThrown){
     		   parent.layer.alert('操作失败,请检查网络!', { title:'提示', icon:2, closeBtn: 0 });
     	   },
     	   complete:function(){}
     	});
    };
    
    return {
        init: function () {
        	handleValidation();
        },
    	saveSort:function(){
    		saveSort();
    	}
    };

}();