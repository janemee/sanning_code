var RoleJs = function () {
    var handleValidation = function() {
        $('#form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
            	 "role.name": {
                    required: true,
                    minlength: 2
            	 },
            	 "role.description": {
            		 minlength: 2
            	 }
            },
            invalidHandler: function (event, validator) { //display error alert on form submit      
            },
            highlight: function(element){ // hightlight error inputs
                $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
            },
            unhighlight: function(element){ // revert the change done by hightlight
                $(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
            },
            success: function(label){
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
                    url: "/s/role/json/saveOrUpdate",
                    success: function(data){
                        if (data.code === 200){
                            $(jqGridId).jqGrid('setGridParam', { url : dataUrl, page : 1 }).trigger("reloadGrid");
                            parent.notifications('success',successMsg,title);
                        }else{
                            parent.notifications('error',data.message,title);
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
    
    //选择功能
    var chooseOperator = function(){
		layer.open({
		    type: 2,
		    scrollbar: false,
		    title: '选择功能',
		    fix: false, //不固定
		    maxmin: true,
		    area: ['300px', '90%'],
		    btn: ['确定', '关闭'],
			yes: function(index, layero){
               var body = layer.getChildFrame('body', index);
			   var iframeWin = window[layero.find('iframe')[0]['name']];
			   iframeWin.yesBtn();
			},
			cancel: function(index){},
		    content: "/modules/system/menu/toZtreeDataOfOperatorPage" //菜单树结构页面
		});
    };
    
    //选择图表
    var chooseIcon = function(){
		layer.open({
		    type: 2,
		    scrollbar: false,
		    title: '选择图表',
		    fix: false, //不固定
		    maxmin: true,
		    area: ['80%', '90%'],
		    btn: ['确定', '关闭'],
			yes: function(index, layero){
               var body = layer.getChildFrame('body', index);
			   var iframeWin = window[layero.find('iframe')[0]['name']];
			   iframeWin.yesBtn();
			},
			cancel: function(index){},
		    content: "/modules/system/menu/getIcons" //菜单树结构页面
		});
    };
    
    return {
        init: function () {
        	handleValidation();
        },
        chooseOperator:function(){
        	chooseOperator();
        },
        chooseIcon:function(){
        	chooseIcon();
        }
    };

}();