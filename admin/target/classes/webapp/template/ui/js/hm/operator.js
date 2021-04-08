var OperatorJs = function () {
    var handleValidation = function() {
    	//选择类型验证
    	/*jQuery.validator.addMethod("isType", function(value, element) {
    		var typeVal =$("#type option:selected").val();
    		return typeVal !="";
    	},"请选择类型");*/
        $('#form').validate({
        	errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules : {
                "operator.module_id":{
                    required : true
                },
				"operator.name": {
					required : true,
					minlength : 2
				},
				"operator.url" : {
					required : true,
					minlength : 2
				},
				"operator.description" : {
					minlength : 2
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
                	failedMsg = "操作失败!";
                //loading层提示
                var loading = layer.load(2, { shade: [0.3,'#fff']});
				var title = "系统提示";
                $.ajax({
             	   	type: "POST",
             	   	async:false,
             	   	data:$(form).serialize(),
             	   	url: "/modules/system/operator/saveOrUpdate",
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
					   parent.notifications('error',"操作失败,请检查网络!",title);
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
