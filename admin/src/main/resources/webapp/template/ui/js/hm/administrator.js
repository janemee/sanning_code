var AdministratorJs = function () {
    var handleValidation = function () {
        $('#form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                "administrator.username": {
                    required: true,
                    minlength: 2
                },
                "administratorInfo.realname": {
                    required: true,
                    zh_verify: true
                },
                "password": {
                    required: true,
                    regexPassword: true
                },
                "confirm_password": {
                    required: true,
                    regexPassword: true,
                    equalTo: "#password"
                },
                "administratorInfo.telephone": {
                    required: true,
                    isMobile: true,
                    maxlength: 11
                },
                "administratorInfo.email": {
                    required: true,
                    email: true
                },
                "administratorInfo.qq": {
                    required: true,
                    number: true
                }
            },
            messages: {
                "password": {
                    regexPassword: "<i class='fa fa-times-circle'></i>  8-16位，至少含一个字母和一个数字"
                },
                "confirm_password": {
                    regexPassword: "<i class='fa fa-times-circle'></i>  8-16位，至少含一个字母和一个数字"
                }
            },
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
                            /*window.parent[winname].$(jqGridId).jqGrid('setGridParam', {
                                url: dataUrl,
                                page: 1
                            }).trigger("reloadGrid");*/

                            parent.notifications('success',successMsg,title);
                            // parent.layer.alert(successMsg, {
                            //     title: '提示',
                            //     icon: 1,
                            //     closeBtn: 0
                            // });
                            parent.layer.close(index);
                        } else {
                            //parent.notifications('error',failedMsg,title);
                            parent.layer.alert(data.message, {
                                title: '提示',
                                content : data.msg,
                                icon: 2,
                                closeBtn: 0
                            });
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
    }

    return {
        init: function () {
            handleValidation();
        },
        distributeRole: function (ids, treeUrl) {
            distributeRole(ids, treeUrl, winname);
        }
    };

}();