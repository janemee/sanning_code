var FreezeJs = function() {
	var handValidation = function() {
		$('#form').validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			rules: {
				"userIdentify.realname_verify_remark": {
					maxlength: 150
				},
			},

			invalidHandler: function(event, validator) { //display error alert on form submit
			},
			highlight: function(element) { // hightlight error inputs
				$(element).closest('.form-group').addClass('has-error'); // set error class to the control group
			},
			unhighlight: function(element) { // revert the change done by hightlight
				$(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
			},
			success: function(label) {
				label.closest('.form-group').removeClass('has-error'); // set success class to the control group
			},
			submitHandler: function(form) {
				var index = parent.layer.getFrameIndex(window.name);
				var successMsg = "操作成功!";
				var failedMsg = "操作失败!";
				//loading层提示
				var loading = layer.load(2, {
					shade: [0.3, '#fff']
				});
				$.ajax({
					type: "POST",
					async: false,
					data: $(form).serialize(),
					url: "/modules/user/doFreeze",
					success: function(data) {
						if (data.result) {
							window.parent[winname].$(jqGridId).jqGrid('setGridParam', {
								url: dataUrl,
								page: 1
							}).trigger("reloadGrid");
                            parent.notifications('success',successMsg,title);
						} else {
							parent.layer.alert(data.msg, {
								title: '提示',
								icon: 2,
								closeBtn: 0
							});
                            parent.notifications('error',data.msg,title);
						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						parent.layer.alert('操作失败,请检查网络!', {
							title: '提示',
							icon: 2,
							closeBtn: 0
						});
					},
					complete: function() {
						layer.close(loading);
						parent.layer.close(index);
					}
				});
			}
		});
	};

	var editUsermsgDetail = function() {
		$('#form').validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			rules: {
				/*"user.realname": {
					required: true,
					zh_verify: true
				},
				"user.idcard_no": {
					required: true,
					idcardVal: true,
					maxlength: 18
				},*/
			},
			errorPlacement:function(error,element) {
				element.parent("span").find(".error-tips").html(error);
		  },
			invalidHandler: function(event, validator) { //display error alert on form submit
			},
			highlight: function(element) { // hightlight error inputs
				$(element).closest('.form-group').addClass('has-error'); // set error class to the control group
			},
			unhighlight: function(element) { // revert the change done by hightlight
				$(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
			},
			success: function(label) {
				label.closest('.form-group').removeClass('has-error'); // set success class to the control group
			},
			submitHandler: function(form) {
				var index = parent.layer.getFrameIndex(window.name);
				var successMsg = "操作成功!";
				var failedMsg = "操作失败!";
				//loading层提示
				var loading = layer.load(2, {
					shade: [0.3, '#fff']
				});
				$.ajax({
					type: "POST",
					async: false,
					data: $(form).serialize(),
					url: "/modules/user/saveUserInfo",
					success: function(data) {
						if (data.result) {
							window.parent[winname].$(jqGridId).jqGrid('setGridParam', {
								url: dataUrl,
								page: 1
							}).trigger("reloadGrid");
							parent.layer.alert(successMsg, {
								title: '提示',
								icon: 1,
								closeBtn: 0
							});
						} else {
							parent.layer.alert(data.msg, {
								title: '提示',
								icon: 2,
								closeBtn: 0
							});
						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						parent.layer.alert('操作失败,请检查网络!', {
							title: '提示',
							icon: 2,
							closeBtn: 0
						});
					},
					complete: function() {
						layer.close(loading);
						parent.layer.close(index);
					}
				});
			}
		});
	}


	return {
		init: function() {
			handValidation();
		},
		editUsermsgDetail: function() {
			editUsermsgDetail();
		}
	};

}();