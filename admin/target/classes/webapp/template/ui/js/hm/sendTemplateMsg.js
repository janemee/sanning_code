var NoticeTemplateJs = function () {
    var handleValidation = function() {
        $('#form').validate({
            errorElement: 'span', // 默认输入错误消息
            errorClass: 'help-block',//默认的输入错误消息类
            focusInvalid: false, // 不要最后无效输入焦点
            ignore: "",
            rules : {
            	"templateMsg.title": {
					required : true
				},
                "templateMsg.template_id": {
                    required : true
                },
                 "templateMsg.detail" : {
                    required : true
                }
			},
            invalidHandler: function (event, validator) { //显示在表单提交错误警报      
            },
            highlight: function(element){ // 标出错误的输入
                $(element).closest('.form-group').addClass('has-error'); //设置错误类对照组
            },
            unhighlight: function(element){ // 恢复所做的改变的标出
                $(element).closest('.form-group').removeClass('has-error'); //设置错误类对照组
            },
            success: function(label){
                label.closest('.form-group').removeClass('has-error'); //类对照组设置成功
				label.remove();
            },
            submitHandler: function (form) {
            	var index = parent.layer.getFrameIndex(window.name),
            		successMsg = "操作成功!",
                	failedMsg = "操作失败!",
					title = "系统提示";
                //loading层提示
                var loading = layer.load(2, { shade: [0.3,'#fff']});
                $.ajax({
             	   	type: "POST",
             	   	async:false,
             	   	data:$(form).serialize(),
             	   	url: "/modules/system/templateMsg/sendTemplateMsg",
             	   	success: function(data){
             	   		if (data.code == 1){
							window.parent[winname].$(jqGridId).jqGrid('setGridParam',{
             	   				url : dataUrl,
             	   				page : 1
             	   			}).trigger("reloadGrid");
							parent.notifications('success',successMsg,title);
                            parent.layer.close(index);
                        }else{
							parent.notifications('error',data.msg,title);
                        }
             	   },
             	   error:function(XMLHttpRequest, textStatus, errorThrown){
					   parent.notifications('error',"操作失败,请检查网络!",title);
             	   },
             	   complete:function(){
             		   layer.close(loading);
             	   }
             	});
            }
        });
    };
    return {
        init: function () {
        	handleValidation();
        }
    };
    
}();