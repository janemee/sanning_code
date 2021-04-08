var LoanModifyJs = function () {
    var handValidation = function() {
            $('#form').validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",
                rules: {
                	"loan.name" : {
						required : true,
						maxlength : 15,
						minlength:4
					},
					email:{
						required : true,
						email:true
					},
					"loan.opentime":{
						required : true,
						date:true
					},
					"loan.money":{
						required : true,
						digits:true 
					},
					"loan.apr":{
						required : true,
						number:true
					},
					"loan.awardscale":{
						required : true,
						number:true
					}
                },
                
                messages : {
    				"loan.name" : {
    					required : "请输入名称",
    					minlength :"名称必须4个字符以上",
    					maxlength :"名称必须15个字符以下"
    				},
    				email: {
    					required:"请输入Email",
    					email:"email格式不正确"
    				},
    				"loan.opentime" : {
    					required : "时间不能为空",
    					data : "时间格式错误"
    				},
    				"loan.money":{
    					required:"请输入",
    					digits:"必须为整数"
    				},
    				"loan.apr":{
						required : "请输入",
						number:"必须为数字"
					},
					"loan.awardscale":{
						required : true,
						number : "请输入数字"
					}
    			},
                	invalidHandler: function (event, validator) { //display error alert on form submit      
                },
                highlight: function (element) { // hightlight error inputs
                    $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
                },
                unhighlight: function (element) { // revert the change done by hightlight
                    $(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
                },
                success: function (label) {
                    label.closest('.form-group').removeClass('has-error'); // set success class to the control group
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
	             	   	url: "/modules/loan/doLoanModify",
	             	   	success: function(data){
	             	   		if (data.result){
								window.parent[winname].$(jqGridId).jqGrid('setGridParam', { url : dataUrl, page : 1 }).trigger("reloadGrid");
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
    }
    return {
        init: function () {
        	handValidation();
        }
    };

}();