var MenuJs = function () {
    var handleValidation = function() {
        $('#form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules : {
                "menu.operator_name":{
                    required : true
                },
                "menu.name":{
                    required : true,
                    rangelength:[1,20]
                },
				"menu.sort" : {
					required : true,
					digits:true
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
            	var index = parent.layer.getFrameIndex(window.name);
                //loading层提示
                var loading = layer.load(2, { shade: [0.3,'#fff']});
                $.ajax({
             	   	type: "POST",
             	   	async:false,
             	   	data:$(form).serialize(),
             	   	url: "/s/menu/json/saveOrUpdate",
             	   	success: function(data){
             	   		var successMsg =  "操作成功!",
                	        failedMsg =  "操作失败!",
							title = "系统提示";
             	   		if (data.code === 200){
							// window.parent[winname].$(jqGridId).jqGrid('setGridParam', { url : dataUrl, page : 1 }).trigger("reloadGrid");
							// window.parent[winname].location.href = "/s/menu/list";
							parent.notifications('success',successMsg,title);
                        }else{
							parent.notifications('error',data.msg,title);
                        }
             	   },
             	   error:function(XMLHttpRequest, textStatus, errorThrown){
					   parent.notifications('error',"操作失败,请检查网络!","标题");
             	   },
             	   complete:function(){
             		   layer.close(loading);
             		   parent.layer.close(index);
             	   },
                    end: function () {
                        location.reload();
                    }
             	});
            }
        });
    };
    
    //选择功能
    var chooseOperator = function(){
		parent.layer.open({
		    type: 2,
		    scrollbar: false,
		    title: '选择功能',
		    fix: false, //不固定
		    maxmin: true,
		    area: ['300px', '90%'],
		    btn: ['确定', '关闭'],
			yes: function(index, layero){
				window.parent[layero.find('iframe')[0]['name']]
					.yesBtn($("#operatorids-input"),$("#operatorname-input"));
			},
			cancel: function(index){},
		    content: "/modules/system/menu/toZtreeDataOfOperatorPage" //菜单树结构页面
		});
    };
    
    var chooseIcon = function () {
    	parent.layer.open({
		    type: 2,
		    title: '选择图标',
		    fix: false, //不固定
		    maxmin: true,
		    area: ['1010px', '95%'],
		    content: '/s/menu/getIcons', //菜单树结构页面
            success: function(layero, index){
                window.parent[layero.find('iframe')[0]['name']].valDo($('#icon_image'),$('#images'));
            }
		});
    };
    return {
        init: function () {
        	handleValidation();
        },
        chooseIcon:function(){
        	chooseIcon();
        },
        chooseOperator:function(){
        	chooseOperator();
        }
    };

}();