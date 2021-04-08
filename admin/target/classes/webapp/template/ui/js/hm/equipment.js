var EquipmentJs = function () {
    var handleValidation = function () {
        $('#form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                "deviceUid": {
                    required: true,
                    minlength: 2
                },
                "deviceCode": {
                    required: true
                },
                "type": {
                    required: true
                },
                "groupId": {
                    required: true
                },
                "state": {
                    required: true
                }
            },
            messages: {},
            errorPlacement: function (error, element) {
                element.parent(".col-sm-8").find(".msg_tip").html(error);
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
                //$(label).remove();
            },
            submitHandler: function (form) {
                var index = parent.layer.getFrameIndex(window.name),
                    successMsg = "操作成功!",
                    failedMsg = "操作失败!";
                var title = "系统提示";
                //loading层提示
                var loading = layer.load(2, {shade: [0.3, '#fff']});
                $.ajax({
                    type: "POST",
                    async: false,
                    data: $(form).serialize(),
                    url: form.action,
                    success: function (data) {
                        if (data.code === 200) {
                            parent.notifications('success', successMsg, title);
                            parent.layer.close(index);
                        } else {
                            parent.notifications('error', data.msg || failedMsg, title);
                        }
                    },
                    error: function (XMLHttpRequest, textStatus) {
                        parent.notifications('error', data.msg, title);
                    },
                    complete: function () {
                        layer.close(loading);
                        location.reload();
                        //parent.layer.close(index);
                    }
                });
            }
        });
    };

    var distributeRole = function (ids, treeUrl) {
        parent.layer.open({
            skin: 'layui-layer-molv',
            type: 2,
            scrollbar: false,
            title: '选择角色',
            fix: false, //不固定
            maxmin: true,
            area: ['380px', 'auto'],
            btn: ['确定', '关闭'],
            yes: function (index, layero) {
                //var body = layer.getChildFrame('body', index);
                //var iframeWin = window[layero.find('iframe')[0]['name']];
                //iframeWin.yesBtn();
                window.parent[layero.find('iframe')[0]['name']].yesBtn();
                window.parent[winname].$(jqGridId).jqGrid('setGridParam', {
                    url: dataUrl,
                    page: 1
                }).trigger("reloadGrid");
            },
            cancel: function (index) {
            },
            content: treeUrl + "?ids=" + ids //菜单树结构页面
        });
    };

    return {
        init: function () {
            handleValidation();
        },
        distributeRole: function (ids, treeUrl) {
            distributeRole(ids, treeUrl, winname);
        }
    };

}();