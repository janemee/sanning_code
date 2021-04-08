var UserJs = function() {
	var handleValidation = function() {
		$('#form').validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			rules: {
				"userInfo.address": {
					maxlength: 100
				}
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
				label.remove();
			},
			submitHandler: function(form) {
				var index = parent.layer.getFrameIndex(window.name);
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
						var successMsg = data.message || "操作成功!",
							failedMsg = data.message || "操作失败!";
						var title = "系统提示";
						if (data.result) {
							parent.notifications('success', successMsg, title);
						} else {
							parent.notifications('error', failedMsg, title);
						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						parent.notifications('error', "操作失败,请检查网络!", title);
					},
					complete: function() {
						layer.close(loading);
						parent.layer.close(index);
					}
				});
			}
		});
	};

	var editUserInfo = function(id) {
		//判断id值是否存在
		if (id == undefined || id == '') {
			layer.alert('id获取异常', {
				icon: 5,
				skin: 'layer-ext-moon'
			});
			return;
		}

		var index = parent.layer.open({
			skin: 'layui-layer-molv',
			type: 2, //iframe 层
			title: "修改个人信息",
			scrollbar: false,
			fix: false, //不固定
			maxmin: true,
			area: ['780px', '500px'],
			btn: ['确定', '关闭'],
			yes: function(index, layero) {
				window.parent[layero.find('iframe')[0]['name']].validateForm();
			},
			cancel: function(index) {},
			content: "/modules/user/editUserInfo?ids=" + id
		});
	};

	var modifyLoginPasswd = function(id) {
		//判断id值是否存在
		if (id == undefined || id == '') {
			layer.alert('id获取异常', {
				icon: 5,
				skin: 'layer-ext-moon'
			});
			return;
		}

		parent.layer.prompt({
			formType: 0,
			value: '000000',
			title: '请输入密码'
		}, function(value, index, elem) {
			$.ajax({
				type: "POST",
				async: false,
				data: {
					'ids': id,
					'pwd': value
				},
				url: "/modules/user/modifyUserLoginPwd",
				success: function(data) {
					var successMsg = data.message || "操作成功!",
						failedMsg = data.message || "操作失败!";
					var title = "系统提示";
					if (data.result) {
						parent.notifications('success', successMsg, title);
					} else {
						parent.notifications('error', failedMsg, title);
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					parent.notifications('success', "操作失败,请检查网络!", title);
				},
				complete: function() {
					parent.layer.close(index);
				}
			});
		});
	};

	var modifyCashPasswd = function(id) {
		//判断id值是否存在
		if (id == undefined || id == '') {
			layer.alert('id获取异常', {
				icon: 5,
				skin: 'layer-ext-moon'
			});
			return;
		}

		parent.layer.prompt({
			formType: 0,
			value: '000000',
			title: '请输入密码'
		}, function(value, index, elem) {
			$.ajax({
				type: "POST",
				async: false,
				data: {
					'ids': id,
					'pwd': value
				},
				url: "/modules/user/modifyUserCashPwd",
				success: function(data) {
					var successMsg = data.message || "操作成功!",
						failedMsg = data.message || "操作失败!";
					var title = "系统提示";
					if (data.result) {
						parent.notifications('success', successMsg, title);
					} else {
						parent.notifications('error', failedMsg, title);
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					parent.notifications('success', "操作失败,请检查网络!", title);
				},
				complete: function() {
					parent.layer.close(index);
				}
			});
		});
	};

	var realNameIdentify = function(id) {
		//判断id值是否存在
		if (id == undefined || id == '') {
			layer.alert('id获取异常', {
				icon: 5,
				skin: 'layer-ext-moon'
			});
			return;
		}
		var index = parent.layer.open({
			skin: 'layui-layer-molv',
			type: 2, //iframe 层
			title: "修改个人信息",
			scrollbar: false,
			fix: false, //不固定
			maxmin: true,
			area: ['780px', '500px'],
			btn: ['确定', '关闭'],
			yes: function(index, layero) {
				window.parent[layero.find('iframe')[0]['name']].validateForm();
			},
			cancel: function(index) {},
			content: "/modules/user/realNameIdentify?ids=" + id
		});
	};

	var doRealNameIdentify = function() {
		$('#form').validate({
			errorElement: 'span',
			errorClass: 'help-block',
			focusInvalid: false,
			ignore: "",
			rules: {
				"userInfo.card_positive_pic": {
					required: true
				},
				"userInfo.card_opposite_pic": {
					required: true
				},
				"userInfo.card_shouchi_pic": {
					required: true
				},
				"userInfo.realname": {
					required: true,
					maxlength: 20,
					zh_verify: true
				},
				"user.idcard_no": {
					required: true,
					maxlength: 18,
					minlength: 18,
					idcardVal: true
				}

			},
			invalidHandler: function(event, validator) {},
			highlight: function(element) {
				$(element).closest('.form-group').addClass('has-error');
			},
			unhighlight: function(element) {
				$(element).closest('.form-group').removeClass('has-error');
			},
			success: function(label) {
				label.closest('.form-group').removeClass('has-error');
				label.remove();
			},
			submitHandler: function(form) {
				var index = parent.layer.getFrameIndex(window.name);
				var loading = layer.load(2, {
					shade: [0.3, '#fff']
				});
				$.ajax({
					type: "POST",
					async: false,
					data: $(form).serialize(),
					url: "/modules/user/doRealNameIdentify",
					success: function(data) {
						var successMsg = data.message || "操作成功!",
							failedMsg = data.message || "操作失败!";
						var title = "系统提示";
						if (data.result) {
							parent.notifications('success', successMsg, title);

						} else {
							parent.notifications('error', failedMsg, title);
						}
					},
					error: function() {
						parent.notifications('error', "操作失败,请检查网络!", title);
					},
					complete: function() {
						layer.close(loading);
						parent.layer.close(index);
					}
				});
			}
		});
	};

	var modifyBankBranchName = function(id, idName) {
		var bankBranchName = $("#" + idName).html();
		//判断id值是否存在
		if (id == undefined || id == '' || bankBranchName == undefined || bankBranchName == '') {
			layer.alert('参数获取异常', {
				icon: 5,
				skin: 'layer-ext-moon'
			});
			return;
		}

		parent.layer.prompt({
			formType: 0,
			value: bankBranchName,
			title: '请输入支行名称'
		}, function(value, index, elem) {
			$.ajax({
				type: "POST",
				async: false,
				data: {
					'ids': id,
					'bankBranchName': value
				},
				url: "/modules/account/bank/modifyBankBranchName",
				success: function(data) {
					var successMsg = data.message || "操作成功!",
						failedMsg = data.message || "操作失败!";
					var title = "系统提示";
					if (data.result) {
						parent.notifications('success', successMsg, title);
						$("#" + idName).html(value);
					} else {
						parent.notifications('error', failedMsg, title);
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					parent.notifications('success', "操作失败,请检查网络!", title);
				},
				complete: function() {
					parent.layer.close(index);
				}
			});
		});
	};

	var modifyUserBrokerage = function(moneyId, timeId) {
		var money = $(moneyId).html();
		var time = $(timeId).html();
		parent.layer.open({
			skin: 'layui-layer-molv',
			type: 1,
			title: "修改佣金",
			closeBtn: 0,
			scrollbar: false,
			fix: true, //不固定
			maxmin: false,
			area: ['530px', '320px'],
			btn: ['确定', '关闭'],
			yes: function(index, layero) {
				var newMoney = layero.find("#remind_money").val();
				var newTime = layero.find("#period_of_validity").val();
				$.post("/modules/user/modifyUserBrokerage", {
					'ids': $("#userId").val(),
					'money': newMoney,
					'time': newTime
				}, function(data) {
					if (data.result) {
						$(moneyId).html(newMoney);
						$(timeId).html(newTime);
						var emd = $("#earned_money_detail");
						emd.html(parseInt($("#exchanged_money_detail").html()) + parseInt($("#apply_exchanging_money_detail").html()) + parseInt(newMoney));
						parent.layer.close(index);
						parent.notifications('success', "操作成功", "提示");
					} else {
						parent.notifications('error', data.msg || "操作失败", "提示");
						parent.layer.close(index);
					}
				})
			},
			cancel: function(index) {
				layer.close(index);
			},
			content: '<form class="form-horizontal m-t" id="form">' +
				'<div class="form-group1">' +
				'<div class="col-xs-12 form-group">' +
				'<label class="col-xs-3 control-label">可用佣金</label>' +
				'<div class="col-xs-8">' +
				'<input id="remind_money" type="text" name="remind_money" value="' + money + '" class="form-control" maxlength="50" >' +
				'</div>' +
				'<label class="required-help col-xs-1">*</label>' +
				'</div>' +
				'</div>' +
				'<div class="form-group1">' +
				'<div class="col-xs-12 form-group">' +
				'<label class="col-xs-3 control-label">有效期</label>' +
				'<div class="col-xs-8">' +
				'<input placeholder="开始日期" class="form-control layer-date" value="' + time + '" id="period_of_validity">' +
				'</div>' +
				'<label class="required-help col-xs-1">*</label>' +
				'</div>' +
				'</div>' +
				'</form>' +
				'<script> var period_of_validity = { elem: "#period_of_validity", format: "YYYY-MM-DD", max: "2099-01-01",min: laydate.now()}; laydate(period_of_validity); </script>'
		});
	};

	return {
		editUserInfo: function(id) {
			editUserInfo(id);
		},
		init: function() {
			handleValidation();
		},
		modifyLoginPasswd: function(id) {
			modifyLoginPasswd(id);
		},
		modifyCashPasswd: function(id) {
			modifyCashPasswd(id);
		},
		realNameIdentify: function(id) {
			realNameIdentify(id);
		},
		doRealNameIdentify: function() {
			doRealNameIdentify();
		},
		modifyBankBranchName: function(id, idName) {
			modifyBankBranchName(id, idName);
		},
		modifyUserBrokerage: function(moneyId, timeId) {
			modifyUserBrokerage(moneyId, timeId);
		}
	};

}();