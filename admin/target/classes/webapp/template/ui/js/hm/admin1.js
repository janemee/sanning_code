var Admin1Js = function () {
    var addValidation = function () {
        $('#form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                "adminInfo.scale_fee": {
                    required: function () {
                        if ($('#J_scale1').is(':checked')) {
                            return true
                        } else {
                            if ($('#J_scale2').is(':checked')) {
                                return false
                            } else {
                                return true
                            }
                        }
                    }
                },
                "adminInfo.scale_money": {
                    required: function () {
                        if ($('#J_scale2').is(':checked')) {
                            return true
                        } else {
                            return false
                        }
                    }
                }
            },
            errorPlacement: function (error, element) {
                element.parent("div").find(".error-tips").html(error);
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
                var loading = layer.load(2, {
                    shade: [0.3, '#fff']
                });
                $.ajax({
                    type: "POST",
                    async: false,
                    data: $(form).serialize(),
                    url: "/modules/xxx/admin/save",
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
                        } else {
                            parent.layer.alert(data.msg, {
                                title: '提示',
                                icon: 2,
                                closeBtn: 0
                            });
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        parent.layer.alert('操作失败,请检查网络!', {
                            title: '提示',
                            icon: 2,
                            closeBtn: 0
                        });
                    },
                    complete: function () {
                        layer.close(loading);
                        parent.layer.close(index);
                    }
                });
            }
        });
    };

    var addValidation8 = function (uri) {
        $('#form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                "adminInfo.scale_fee": {
                    required: function () {
                        if ($('#J_scale1').is(':checked')) {
                            return true
                        } else {
                            if ($('#J_scale2').is(':checked')) {
                                return false
                            } else {
                                return true
                            }
                        }
                    }
                },
                "adminInfo.scale_money": {
                    required: function () {
                        if ($('#J_scale2').is(':checked')) {
                            return true
                        } else {
                            return false
                        }
                    }
                }
            },
            errorPlacement: function (error, element) {
                element.parent("div").find(".error-tips").html(error);
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
                var loading = layer.load(2, {
                    shade: [0.3, '#fff']
                });
                $.ajax({
                    type: "POST",
                    async: false,
                    data: $(form).serialize(),
                    url: uri,
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
                        } else {
                            parent.layer.alert(data.msg, {
                                title: '提示',
                                icon: 2,
                                closeBtn: 0
                            });
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        parent.layer.alert('操作失败,请检查网络!', {
                            title: '提示',
                            icon: 2,
                            closeBtn: 0
                        });
                    },
                    complete: function () {
                        layer.close(loading);
                        parent.layer.close(index);
                    }
                });
            }
        });
    };


    //选择用户
    var chooseUser = function (uri) {
        parent.layer.open({
            skin: 'layui-layer-molv',
            type: 2,
            scrollbar: false,
            title: '选择用户',
            fix: false, //不固定
            maxmin: true,
            area: ['60%', '70%'],
            btn: ['确定', '关闭'],
            yes: function (index, layero) {
                window.parent[layero.find('iframe')[0]['name']]
                    .yesBtn($("#J_val1"), $("#J_val2"), $("#J_val3"), $("#J_val4"));
            },
            cancel: function (index) {
            },
            content: uri //会员列表页
        });
    };

    // 开启/关闭后台登陆权限
    var addAdminId = function (del, level) {
        var ids = jQuery(jqGridId).jqGrid('getGridParam', 'selrow');
        if (ids == '') {
            parent.notifications('error', "请选择一条数据");
            return false;
        }

        var str;
        if (del) {
            str = "关闭后该用户无法登陆后台,是否关闭?";
        } else {
            str = "开启后可以使用该手机号登陆后台,是否开启?";
        }
        layer.confirm(str, {
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            doAjax("/modules/user/addAdminIdForLogin", "userId=" + ids + "&userLevel=" + level + "&del=" + del);
            layer.closeAll('dialog');
        });

    };

    // table底部小计
    var subtotal = function (obj, data) {
        var rowNum = obj.getRowData.length;
        if (rowNum > 0) {
            var a = {};
            a[data[0]] = "小计:";
            for (var i = 1; i < data.length; i++) {
                var val = obj.getCol(data[i], false, 'sum');
                a[data[i]] = val.toFixed(2);
            }
            obj.footerData("set", a);
        }
    };


    return {
        init: function () {
            addValidation();
        },
        initProxy: function () {
            addValidation8("/modules/user/admin/proxy/save");
        },
        initComplex: function () {
            addValidation8("/modules/user/admin/complex/save");
        },
        initAgency: function () {
            addValidation8("/modules/user/admin/agency/save");
        },
        chooseComplexUser: function () {
            chooseUser("/modules/user/admin/complex/findUser");
        },
        chooseProxyUser: function () {
            chooseUser("/modules/user/admin/proxy/findUser");
        },
        chooseAgencyUser: function () {
            chooseUser("/modules/user/admin/agency/findUser");
        },


        initProxyOfMoni: function () {
            addValidation8("/modules/user/moni/proxy/save");
        },
        initComplexOfMoni: function () {
            addValidation8("/modules/user/moni/complex/save");
        },
        initAgencyOfMoni: function () {
            addValidation8("/modules/user/moni/org/save");
        },
        chooseComplexUserOfMoni: function () {
            chooseUser("/modules/user/moni/complex/findUser");
        },
        chooseProxyUserOfMoni: function () {
            chooseUser("/modules/user/moni/proxy/findUser");
        },
        chooseAgencyUserOfMoni: function () {
            chooseUser("/modules/user/moni/org/findUser");
        },



        adminOn: function (level) {
            addAdminId(false, level);
        },
        adminOff: function (level) {
            addAdminId(true, level);
        },
        subtotal: function (obj, data) {
            subtotal(obj, data);
        }
    };

}();