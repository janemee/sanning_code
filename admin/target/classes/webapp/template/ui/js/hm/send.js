var SendJs = function () {
    var handleValidation = function() {
        $('#form').validate({
        	errorElement: 'span',
            errorClass: 'help-block',
            focusInvalid: false,
            ignore: "",
            rules : {
				"mobiles" : {
					required : true,
					minlength:5
				},
				"content" : {
					required : true,
					minlength:5
				}
			},
			invalidHandler: function (event, validator){
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
					title = "系统提示";
                //loading层提示
                var loading = layer.load(2, { shade: [0.3,'#fff']});
                $.ajax({
             	   	type: "POST",
             	   	async:false,
             	   	data:$(form).serialize(),
             	   	url: "/modules/custom/notice/doSendSms",
             	   	success: function(data){
             	   		if (data.result){
							window.parent[winname].$(jqGridId).jqGrid('setGridParam', {
								url: dataUrl,
								page: 1
							}).trigger("reloadGrid");
							parent.notifications('success',successMsg,title);
                        }else{
							parent.notifications('error',data.msg,title);
                        }
             	   },
             	   error:function(XMLHttpRequest, textStatus, errorThrown){
             		   parent.layer.alert('操作失败,请检查网络!', { title:'提示', icon:2, closeBtn: 0 });
             	   },
             	   complete:function(){
             		   layer.close(loading);
             		   parent.layer.close(index);
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