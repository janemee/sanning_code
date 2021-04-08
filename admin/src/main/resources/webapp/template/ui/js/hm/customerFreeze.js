var CustomerFreezeJs = function () {
    var freezeValidation = function() {
	        $('#form').validate({
	            errorElement: 'span', //default input error message container
	            errorClass: 'help-block', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            ignore: "",
	            rules: {
	            	"customerfreeze.remark" : {
						required : true,
						maxlength : 150
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
	            },
	            submitHandler: function (form) {
	            	var index = parent.layer.getFrameIndex(window.name);
	            	var successMsg = "操作成功!";
	                var failedMsg = "操作失败!";
	                //loading层提示
	                var loading = layer.load(2, { shade: [0.3,'#fff']});
	                $.ajax({
	             	   	type: "POST",
	             	   	async:false,
	             	   	data:$(form).serialize(),
	             	   	url: "/modules/freeze/doCustomerFreeze",
	             	   	success: function(data){
	             	   	if (data.result){
             	   			$(jqGridId).jqGrid('setGridParam', { url : dataUrl, page : 1 }).trigger("reloadGrid");
             	   		parent.layer.alert(successMsg, { title:'提示', icon:1, closeBtn: 0 });
                        }else{
	                        	parent.layer.alert(data.msg, { title:'提示', icon:2, closeBtn: 0 });
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
	}

    return {
        init: function () {
        	freezeValidation();
        }
    };

}();