var giveGiftJson = function () {
    var handleValidation = function () {
        $('#form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                "giftNumber": {
                    required: true
                },
                "giftPage": {
                    required: true
                },
                "giftBox": {
                    required: true
                },

            },
            invalidHandler: function (event, validator) { //显示在表单提交错误警报      
            },
            highlight: function (element) { // 标出错误的输入
                $(element).closest('.form-group').addClass('has-error'); //设置错误类对照组
            },
            unhighlight: function (element) { // 恢复所做的改变的标出
                $(element).closest('.form-group').removeClass('has-error'); //设置错误类对照组
            },
            success: function (label) {
                label.closest('.form-group').removeClass('has-error'); //类对照组设置成功
                label.remove();
            },
            submitHandler: function (form) {
                var index = parent.layer.getFrameIndex(window.name),
                    failedMsg = "操作失败!",
                    title = "系统提示";
                //loading层提示
                var loading = layer.load(2, {shade: [0.3, '#fff']});
                $.ajax({
                    type: "POST",
                    async: false,
                    data: $(form).serialize(),
                    url: form.action,
                    success: function (data) {
                        if (data.code === 200) {
                            var ccc = layer.confirm('添加赠送礼物任务成功', {
                                btn: ['继续添加','关闭']
                            }, function(){
                                parent.notifications('info', "请重新选择设备，避免任务重复", title);
                                layer.close(ccc);
                            }, function(){
                                window.location.reload();
                                layer.close(ccc);
                            });

                        } else {
                            parent.notifications('error', data.msg || failedMsg, title);
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
    var chooseEquipmentGroup = function () {
        let val = $('#hotTaskId option:selected').val();
        parent.layer.open({
            type: 2,
            title: '选择设备分组',
            fix: false, //不固定
            maxmin: true,
            area: ['900px', '60%'],
            btn: ['确定', '关闭'],
            yes: function (index, layero) {
                // layer.close(ii);
                var ids = jQuery(jqGridId).jqGrid('getGridParam', 'selarrrow');
                if (ids == '') {
                    parent.notifications('error', "请至少选择一条数据");
                    return false;
                }
                window.parent[layero.find('iframe')[0]['name']].callbackGroups(ids, $("#equipments"), $("#equipmentsVal"));
            },
            end: function () {

            },
            cancel: function (index) {
            },
            content: "/s/task/equipmentGroupSome?taskId=" + val,
            // success: function(layero, index){
            //     window.parent[layero.find('iframe')[0]['name']].valDo($('#EquipmentList'),$('#images'));
            // }
        });
    };


    var chooseEquipment = function () {
        let val = $('#hotTaskId option:selected').val();
        parent.layer.open({
            type: 2,
            title: '选择设备',
            fix: false, //不固定
            maxmin: true,
            area: ['900px', '60%'],
            btn: ['确定', '关闭'],
            yes: function (index, layero) {
                // layer.close(ii);
                var ids = jQuery(jqGridId).jqGrid('getGridParam', 'selarrrow');
                if (ids == '') {
                    parent.notifications('error', "请至少选择一条数据");
                    return false;
                }
                window.parent[layero.find('iframe')[0]['name']].callbackDevices(ids, $("#equipments"), $("#equipmentsVal"));
            },
            end: function () {

            },
            cancel: function (index) {
            },
            content: "/s/task/equipmentSome?taskId=" + val,
            // success: function(layero, index){
            //     window.parent[layero.find('iframe')[0]['name']].valDo($('#EquipmentList'),$('#images'));
            // }
        });
    };

    var selectGroupEquipment = function () {
        //let val = $('#equipmentGroups option:selected').val();
        let val = $("#equipments").val();
        parent.layer.open({
            type: 2,
            title: '选择设备',
            fix: false, //不固定
            maxmin: true,
            area: ['900px', '60%'],
            yes: function (index, layero) {
                // layer.close(ii);
                var ids = jQuery(jqGridId).jqGrid('getGridParam', 'selarrrow');
                if (ids == '') {
                    parent.notifications('error', "请至少选择一条数据");
                    return false;
                }
                window.parent[layero.find('iframe')[0]['name']].callbackDevices(ids, $("#equipments"), $("#equipmentsVal"));
            },
            end: function () {

            },
            cancel: function (index) {
            },
            content: "/s/task/equipmentToGroups?equipmentGroups=" + val,
            // success: function(layero, index){
            //     window.parent[layero.find('iframe')[0]['name']].valDo($('#EquipmentList'),$('#images'));
            // }
        });
    };
    return {
        init: function () {
            handleValidation();
        },
        chooseEquipment:function(){
            chooseEquipment();
        },
        chooseEquipmentGroup:function(){
            chooseEquipmentGroup();
        },selectGroupEquipment:function(){
            selectGroupEquipment();
        }
    };
}();