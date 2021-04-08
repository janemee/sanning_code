var ArticleJs = function () {
    var handleValidation = function() {
    	jQuery.validator.addMethod("positiveinteger", function(value, element) {
    		   var aint=parseInt(value);	
    		    return aint>0&& (aint+"")==value;   
    		  }, "请输入大于零正整数");   
        $('#form').validate({
        	errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules : {
				"article.categoryids":{
					required : true
				},
				"article.title": {
					required : true,
					minlength : 2
				},
				"articleData.content": {
					required : true,
					minlength : 2
				},
				"article.link" : {
					url:true
				}
				/*"article.color" : {
					required : true,
					minlength : 2,
				},
				"article.keywords" : {
					required:true,
					minlength : 2,
				},
				"article.description":{
					required:true,
					minlength : 2,
				},
				"article.weight":{
					required:true,
					positiveinteger:true,
				},
				"article.remarks":{
					minlength : 2,
				}*/
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
             	   	url: "/modules/article/saveOrUpdate",
             	   	success: function(data){
             	   		if (data.result){
							window.parent[winname].$(jqGridId).jqGrid('setGridParam', { url : dataUrl, page : 1 }).trigger("reloadGrid");
							parent.notifications('success',successMsg,title);
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
    //选择栏目
    var chooseCategory = function(){
		layer.open({
		    type: 2,
		    scrollbar: false,
		    title: '选择栏目',
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
		    content: "/modules/article/getCategoryTree" //菜单树结构页面
		});
    }
    
    return {
        init: function () {
        	handleValidation();
        },
        chooseCategory:function(){
        	chooseCategory();
		}
    };
    
}();