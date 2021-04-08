var CashVerifyJs = function () {
    var handValidation = function () {
        $('#form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                "order.state": {
                    required: true
                },
                "order.remark": {
                    required: true
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
                var index = parent.layer.getFrameIndex(window.name);
                var successMsg = "操作成功!";
                var failedMsg = "操作失败!";
                //loading层提示
                var loading = layer.load(2, {shade: [0.3, '#fff']});
                var title = "系统提示";
                $.ajax({
                    type: "POST",
                    async: false,
                    data: $(form).serialize(),
                    url: "/modules/account/cash/doVerify",
                    success: function (data) {
                        if (data.code == 1) {
                            window.parent[winname].$(jqGridId).jqGrid('setGridParam', {
                                url: dataUrl,
                                page: 1
                            }).trigger("reloadGrid");
                            parent.notifications('success', data.msg || successMsg, title);
                        } else {
                            parent.notifications('warning', data.msg || failedMsg, title);
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        parent.notifications('error', '操作失败,请检查网络!', title);
                    },
                    complete: function () {
                        layer.close(loading);
                        parent.layer.close(index);
                    }
                });
            }
        });
    };
    return {
        init: function () {
            handValidation();
        }
    };

}();