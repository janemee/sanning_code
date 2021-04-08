var MoniAddUserJs = function () {
    var handleValidationAddComplex = function () {
        $('#form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                "user.id": {
                    required: true
                },
                "user.scaleMoney": {
                    required: true
                },
                "user.scaleFee": {
                    required: true
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
                    url: '/modules/user/admin/complex/save',
                    success: function (data) {
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
                            parent.layer.close(index);
                        } else {
                            //parent.notifications('error',failedMsg,title);
                            parent.layer.alert(data.msg, {
                                title: '提示',
                                icon: 2,
                                closeBtn: 0
                            });
                        }
                    },
                    error: function (XMLHttpRequest, textStatus) {
                        parent.notifications('success', data.msg, title);
                    },
                    complete: function () {
                        layer.close(loading);
                        //parent.layer.close(index);
                    }
                });
            }
        });
    };

    var handleValidationAddProxy = function () {
        $('#form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                "user.id": {
                    required: true
                },
                "user.scaleMoney": {
                    required: true
                },
                "user.scaleFee": {
                    required: true
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
                    url: '/modules/user/admin/proxy/save',
                    success: function (data) {
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
                            parent.layer.close(index);
                        } else {
                            parent.layer.alert(data.msg, {
                                title: '提示',
                                icon: 2,
                                closeBtn: 0
                            });
                        }
                    },
                    error: function (XMLHttpRequest, textStatus) {
                        parent.notifications('success', data.msg, title);
                    },
                    complete: function () {
                        layer.close(loading);
                        //parent.layer.close(index);
                    }
                });
            }
        });
    };

    var handleValidationAddOrg = function () {
        $('#form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                "user.id": {
                    required: true
                },
                "user.scaleMoney": {
                    required: true
                },
                "user.scaleFee": {
                    required: true
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
                    url: '/modules/user/admin/org/save',
                    success: function (data) {
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
                            parent.layer.close(index);
                        } else {
                            //parent.notifications('error',failedMsg,title);
                            parent.layer.alert(data.msg, {
                                title: '提示',
                                icon: 2,
                                closeBtn: 0
                            });
                        }
                    },
                    error: function (XMLHttpRequest, textStatus) {
                        parent.notifications('success', "删除失败,请检查网络!", title);
                    },
                    complete: function () {
                        layer.close(loading);
                        //parent.layer.close(index);
                    }
                });
            }
        });
    };

    return {
        handleValidationAddComplex: function () {
            handleValidationAddComplex();
        },
        handleValidationAddProxy: function (ids, treeUrl) {
            handleValidationAddProxy(ids, treeUrl, winname);
        },
        handleValidationAddOrg: function (ids, treeUrl) {
            handleValidationAddOrg(ids, treeUrl, winname);
        }
    };

}();