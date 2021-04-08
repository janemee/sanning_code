/**
 * 下拉框
 */
let config = {
    ".chosen-select": {},
    ".chosen-select-deselect": {
        allow_single_deselect: !0
    },
    ".chosen-select-no-single": {
        disable_search_threshold: 10
    },
    ".chosen-select-no-results": {
        no_results_text: "Oops, nothing found!"
    },
    ".chosen-select-width": {
        width: "95%"
    }
};
for (let selector in config) {
    $(selector).chosen(config[selector]);
}

/**
 * 查看界面
 * @param id
 * @param title
 * @param width
 * @param height
 * @param url
 */

function hm_fn_viewFun1(title, width, height, url, id) {
    //判断id值是否存在
    if (id === undefined || id === '') {
        layer.alert('id获取异常', {icon: 5, skin: 'layer-ext-moon'});
        return;
    }

    //判断url中是否已经挂参
    if (url.indexOf("?") === -1) {
        url = url + "?ids=" + id;
    } else {
        url = url = "&ids=" + id;
    }

    parent.layer.open({
        // skin: 'layui-layer-molv',
        type: 2, //iframe 层
        title: title,
        scrollbar: false,
        fix: false, //不固定
        maxmin: true,
        area: [width, height],
        btn: ['关闭'],
        cancel: function (index) {
        },
        content: url
    });
}

/**
 * 编辑界面
 * @param id
 * @param title
 * @param width
 * @param height
 * @param url
 */

// function hm_fn_editFun(title,width,height,url,id,winname){
// 	//判断id值是否存在
// 	if(id == undefined || id == ''){
// 		layer.alert('id获取异常', { icon: 5, skin: 'layer-ext-moon'});
// 		return ;
// 	}

// 	//判断url中是否已经挂参
// 	if(url.indexOf("?") == -1){
// 		url = url + "?ids=" + id + "&winname="+winname;
// 	}else{
// 		url = url + "&ids="+ id + "&winname="+winname;
// 	}

// 	let index = parent.layer.open({
// 		skin: 'layui-layer-molv',
// 	    type: 2, //iframe 层 
// 	    title: title,
// 	    scrollbar:false,
// 	    fix: false, //不固定
// 	    maxmin: true,
// 	    area: [width, height],
// 	    btn: ['确定', '关闭'],
// 		yes: function(index, layero){
// 		   window.parent[layero.find('iframe')[0]['name']].validateForm();
// 		},
// 		cancel: function(index){},
// 	    content: url
// 	});
// }


function hm_fn_editFun(title, width, height, url, id, winname, closeFunc) {
    //判断id值是否存在
    if (id == undefined || id == '') {
        layer.alert('id获取异常', {icon: 5, skin: 'layer-ext-moon'});
        return;
    }

    //判断url中是否已经挂参
    if (url.indexOf("?") == -1) {
        url = url + "?ids=" + id + "&winname=" + winname;
    } else {
        url = url + "&ids=" + id + "&winname=" + winname;
    }

    let iii = parent.layer.open({
        // skin: 'layui-layer-molv',
        type: 2, //iframe 层
        title: title,
        scrollbar: true,
        fix: false, //不固定
        maxmin: true,
        area: [width, height],
        btn: ['确定', '关闭'],
        yes: function (index, layero) {
            layer.close(iii);
            window.parent[layero.find('iframe')[0]['name']].validateForm();
        },
        end: function () {
            location.reload();
        },
        cancel: function (index) {
        },
        content: url
    });
}

//编辑弹窗 操作后不刷新
function hm_fn_editFun_load_page(title, width, height, url, id, winname, page) {
    //判断id值是否存在
    if (id == undefined || id == '') {
        layer.alert('id获取异常', {icon: 5, skin: 'layer-ext-moon'});
        return;
    }

    //判断url中是否已经挂参
    if (url.indexOf("?") == -1) {
        url = url + "?ids=" + id + "&winname=" + winname;
    } else {
        url = url + "&ids=" + id + "&winname=" + winname;
    }

    let iii = parent.layer.open({
        // skin: 'layui-layer-molv',
        type: 2, //iframe 层
        title: title,
        scrollbar: true,
        fix: false, //不固定
        maxmin: true,
        area: [width, height],
        btn: ['确定', '关闭'],
        yes: function (index, layero) {
            layer.close(iii);
            window.parent[layero.find('iframe')[0]['name']].validateForm();
            $(jqGridId).jqGrid('setGridParam', {url: dataUrl, page: page}).trigger("reloadGrid");
        },
        end: function () {
            // location.reload();
            $(jqGridId).jqGrid('setGridParam', {url: dataUrl, page: page}).trigger("reloadGrid");
        },
        cancel: function (index) {
            $(jqGridId).jqGrid('setGridParam', {url: dataUrl, page: page}).trigger("reloadGrid");
        },
        content: url
    });
}


//子账户入金出金
function hm_fn_editFun1(title, width, height, url, id, winname, closeFunc, adminname) {
    //判断id值是否存在
    if (id == undefined || id == '') {
        layer.alert('id获取异常', {icon: 5, skin: 'layer-ext-moon'});
        return;
    }

    //判断url中是否已经挂参
    if (url.indexOf("?") == -1) {
        if (adminname) {
            url = url + "?ids=" + id + "&winname=" + winname + "&adminname=" + adminname;
        } else {
            url = url + "?ids=" + id + "&winname=" + winname;
        }
    } else {
        if (adminname) {
            url = url + "&ids=" + id + "&winname=" + winname + "&adminname=" + adminname;
        } else {
            url = url + "&ids=" + id + "&winname=" + winname;
        }
    }

    let index = parent.layer.open({
        // skin: 'layui-layer-molv',
        type: 2, //iframe 层
        title: title,
        scrollbar: false,
        fix: false, //不固定
        maxmin: true,
        area: [width, height],
        btn: ['确定', '关闭'],
        yes: function (index, layero) {
            window.parent[layero.find('iframe')[0]['name']].validateForm();
        },
        end: closeFunc,
        cancel: function (index) {
        },
        content: url
    });
}


//帮忙中心 内容管理
function hm_fn_conlistFun(title, width, height, url, id, winname, closeFunc) {
    //判断id值是否存在
    if (id == undefined || id == '') {
        layer.alert('id获取异常', {icon: 5, skin: 'layer-ext-moon'});
        return;
    }

    //判断url中是否已经挂参
    if (url.indexOf("?") == -1) {
        url = url + "?ids=" + id + "&winname=" + winname;
    } else {
        url = url + "&ids=" + id + "&winname=" + winname;
    }

    let index = parent.layer.open({
        skin: 'layui-layer-molv',
        type: 2, //iframe 层
        title: title,
        scrollbar: false,
        fix: false, //不固定
        maxmin: true,
        area: [width, height],
        btn: ['确定', '关闭'],
        yes: closeFunc,
        end: closeFunc,
        cancel: function (index) {
        },
        content: url
    });
}

/**
 * 添加页面
 * @param title
 * @param width
 * @param height
 * @param url
 * @param formId
 * @param winname
 */
function hm_fn_addFun(title, width, height, url, formId, winname) {
    //判断id值是否存在
    if (formId == undefined || formId == '') {
        layer.alert('请注意,表单id未传入!!!', {title: "提示", icon: 5, skin: 'layer-ext-moon', closeBtn: 0});
    }

    //判断url中是否已经挂参
    if (url.indexOf("?") == -1) {
        url = url + "?winname=" + winname;
    } else {
        url = url + "&winname=" + winname;
    }
    let index = parent.layer.open({
        // skin: 'layui-layer-molv',
        type: 2, //iframe 层
        title: title,
        scrollbar: false,
        fix: false, //不固定
        maxmin: true,
        area: [width, height],
        btn: ['确定', '关闭'],
        yes: function (index, layero) {
            window.parent[layero.find('iframe')[0]['name']].validateForm();
        },
        end: function () {
            location.reload();
        },
        cancel: function (index) {
        },
        content: url
    });
}


/**
 * 添加页面内部读参
 * @param title
 * @param width
 * @param height
 * @param url
 * @param formId
 * @param winname
 */
function hm_fn_newPageFun(that) {
    let _self = $(that),
        title = _self.attr('data-title'),
        width = _self.attr('data-width'),
        height = _self.attr('data-height'),
        url = _self.attr('data-url'),
        formId = _self.attr('data-tid'),
        winname = window.name;
    //判断id值是否存在
    if (formId == undefined || formId == '') {
        layer.alert('请注意,表单id未传入!!!', {title: "提示", icon: 5, skin: 'layer-ext-moon', closeBtn: 0});
    }
    let index = parent.layer.open({
        // skin: 'layui-layer-molv',
        type: 2, //iframe 层
        title: title,
        scrollbar: false,
        fix: false, //不固定
        maxmin: true,
        area: [width, height],
        btn: ['确定', '关闭'],
        yes: function (index, layero) {
            window.parent[layero.find('iframe')[0]['name']].validateForm();
        },
        cancel: function (index) {
        },
        content: url + "?winname=" + winname
    });
}

/**
 * 添加下级菜单
 * @param title
 * @param width
 * @param height
 * @param url
 * @param formId
 * @param parentIds
 * @param winname
 */
function hm_fn_addSubMenuFun(title, width, height, url, formId, parentIds, winname) {
    //判断id值是否存在
    if (formId == undefined || formId == '') {
        layer.alert('请注意,表单id未传入!!!', {title: "提示", icon: 5, skin: 'layer-ext-moon', closeBtn: 0});
    }
    let index = parent.layer.open({
        // skin: 'layui-layer-molv',
        type: 2, //iframe 层
        title: title,
        scrollbar: false,
        fix: false, //不固定
        maxmin: true,
        area: [width, height],
        btn: ['确定', '关闭'],
        yes: function (index, layero) {
            window.parent[layero.find('iframe')[0]['name']].validateForm();
        },
        cancel: function (index) {
        },
        content: url + "?ids=" + parentIds + "&winname=" + winname
    });
}

/**
 * 提交数据
 * @param formid
 * @param url
 * @param jqGridId
 * @param dataUrl
 */
function hm_fn_submitFun(formid, url, jqGridId, dataUrl) {
    let successMsg = successMsg || "操作成功!";
    let failedMsg = failedMsg || "操作失败!";

    //loading层提示
    let loading = layer.load(0, {shade: [0.3, '#fff']});

    $.ajax({
        type: "POST",
        async: false,
        data: $(formid).serialize(),
        url: url,
        success: function (data) {
            if (data.result) {
                $(jqGridId).jqGrid('setGridParam', {url: dataUrl, page: 1}).trigger("reloadGrid");
                layer.msg(successMsg, {icon: 1});
            } else {
                layer.alert(data.txt, {title: '提示', icon: 2, closeBtn: 0});
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            layer.alert('操作失败,请检查网络!', {title: '提示', icon: 2, closeBtn: 0});
        },
        complete: function () {
            layer.close(loading);
        }
    });
}

function hm_fn_alert(title, content, yes_callback, cancel_callback) {
    layer.alert(content, {
        title: title,
        // icon: 2,
        closeBtn: 0,
        anim: 1,
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            yes_callback();
            layer.close(index);
        },
        btn2: function (index, layero) {
            cancel_callback();
            layer.close(index);
        }
    });
}

/**
 * ztree 页面
 */
function hm_fn_treeLayer(title, width, height, url) {
    layer.open({
        // skin: 'layui-layer-molv',
        type: 2, //iframe 层
        title: title,
        scrollbar: false,
        fix: false, //不固定
        maxmin: true,
        area: [width, height],
        btn: ['确定', '关闭'],
        yes: function (index, layero) {
            let body = layer.getChildFrame('body', index);
            let iframeWin = window[layero.find('iframe')[0]['name']];
            hm_fn_bingToParentWin();
        },
        cancel: function (index) {

        },
        content: url
    });
}

/**
 * ztree  -- 带回参数到父窗口
 */
function hm_fn_bingToParentWin() {
    let index = parent.layer.getFrameIndex(window.name);
    parent.layer.iframeAuto(index);
    let zTree = $.fn.zTree.getZTreeObj("ztree_layer");
    checkCount = zTree.getCheckedNodes(true);
    i = checkCount.length;
    if (i > 0) {
        let ids = checkCount[i - 1].ids;
        let name = checkCount[i - 1].name;
    } else {
        parent.layer.msg('请选择功能');
        return;
    }
    parent.$('#parent-input').val(ids); //隐藏输入框放置ids
    parent.$('#parent-span').html(name); //a标签中放置名称
    parent.layer.msg('操作成功');
    parent.layer.close(index);
}


/**
 * 删除
 * @param jqGridId
 * @param dataUrl
 * @param url
 * @param id
 */
function hm_fn_del(jqGridId, dataUrl, url, id, delMsg) {
    if (id == undefined || id == '') {
        layer.alert('id获取异常', {icon: 5, skin: 'layer-ext-moon'});
        return;
    }
    if (delMsg == undefined || id == '') {
        delMsg = "确定删除吗?";
    }
    // let iii = layer.load(2, {
    //     shade: [0.1, '#fff']
    // });
    let i = parent.layer.confirm(delMsg, {
        // skin: 'layui-layer-molv',
        icon: 0,
        title: '提示',
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.ajax({
            url: url,
            async: true,
            data: "ids=" + id,
            success: function (data) {
                if (data.code === 200) {
                    parent.notifications('success', "操作成功");
                    $(jqGridId).jqGrid('setGridParam', {url: dataUrl, page: 1}).trigger("reloadGrid");
                } else {
                    parent.notifications('error', data.message);
                }
            },
            error: function () {
                parent.notifications('error', "操作失败,请检查网络!");
            },
            complete: function () {
                // layer.close(iii);
                parent.layer.close(i);
            }
        });
    }, function () {
        // layer.close(iii);
    });
}

function hm_fn_del_1(jqGridId, dataUrl, url, id, delMsg) {
    if (id == undefined || id == '') {
        layer.alert('id获取异常', {icon: 5, skin: 'layer-ext-moon'});
        return;
    }
    if (delMsg == undefined || id == '') {
        delMsg = "确定提前结束吗?";
    }
    let iii = layer.load(2, {
        shade: [0.1, '#fff']
    });
    let i = parent.layer.confirm(delMsg, {
        // skin: 'layui-layer-molv',
        icon: 0,
        title: '提示',
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.ajax({
            url: url,
            async: true,
            data: "ids=" + id,
            success: function (data) {
                if (data.code === 200) {
                    parent.notifications('success', "操作成功");
                    $(jqGridId).jqGrid('setGridParam', {url: dataUrl, page: 1}).trigger("reloadGrid");
                } else {
                    parent.notifications('error', data.msg);
                }
            },
            error: function () {
                parent.notifications('error', "操作失败,请检查网络!");
            },
            complete: function () {
                layer.close(iii);
                parent.layer.close(i);
            }
        });
    }, function () {
        layer.close(iii);
    });
}

/**
 * 通知类弹窗事件(发布, 撤回等...)
 */
function hm_fn_doAction(jqGridId, dataUrl, url, id, title) {
    if (id == undefined || id == '') {
        layer.alert('id获取异常', {icon: 5, skin: 'layer-ext-moon'});
        return;
    }
    let i = parent.layer.confirm('确定' + title + '吗？', {
        // skin: 'layui-layer-molv',
        icon: 0,
        title: '提示',
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.ajax({
            async: "true",
            url: url,
            data: "ids=" + id,
            success: function (data) {
                if (data.code === 200) {
                    parent.notifications('success', title + "成功");
                    $(jqGridId).jqGrid('setGridParam', {url: dataUrl, page: 1}).trigger("reloadGrid");
                } else {
                    parent.notifications('error', data.msg);
                }
            },
            error: function () {
                parent.notifications('error', title + "失败,请检查网络!");
            },
            complete: function () {
                parent.layer.close(i);
            }
        });
    });
}

/**
 * 通知类弹窗事件(发布, 撤回等...)
 */
function hm_fn_doAction(jqGridId, dataUrl, url, id, title,page) {
    if (id == undefined || id == '') {
        layer.alert('id获取异常', {icon: 5, skin: 'layer-ext-moon'});
        return;
    }
    let i = parent.layer.confirm('确定' + title + '吗？', {
        // skin: 'layui-layer-molv',
        icon: 0,
        title: '提示',
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.ajax({
            async: "true",
            url: url,
            data: "ids=" + id,
            success: function (data) {
                if (data.code === 200) {
                    parent.notifications('success', title + "成功");
                    $(jqGridId).jqGrid('setGridParam', {url: dataUrl, page: page}).trigger("reloadGrid");
                } else {
                    parent.notifications('error', data.msg);
                }
            },
            error: function () {
                parent.notifications('error', title + "失败,请检查网络!");
            },
            complete: function () {
                parent.layer.close(i);
            }
        });
    });
}

/**
 * 导出结算明细
 */
function hm_fn_doExport(jqGridId, dataUrl, url, id, startTime, endTime, title) {
    let i = parent.layer.confirm('确定' + title + '吗？', {
        // skin: 'layui-layer-molv',
        icon: 0,
        title: '提示',
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.ajax({
            type: "POST",
            async: "true",
            url: url,
            data: "ids=" + id + "&searchStartTime=" + startTime + "&searchEndTime=" + endTime,
            success: function (data) {
                if (data.code === 200) {
                    parent.notifications('success', title + "成功");
                    $(jqGridId).jqGrid('setGridParam', {url: dataUrl, page: 1}).trigger("reloadGrid");
                } else {
                    parent.notifications('error', data.msg);
                }
            },
            error: function () {
                parent.notifications('error', title + "失败,请检查网络!");
            },
            complete: function () {
                parent.layer.close(i);
            }
        });
    });
}

/**
 * 结算
 */
function hm_fn_doSettle(jqGridId, dataUrl, url, data, title) {
    if (data == undefined || data == '') {
        layer.alert('数据异常', {icon: 5, skin: 'layer-ext-moon'});
        return;
    }
    let i = parent.layer.confirm('确定' + title + '吗？', {
        // skin: 'layui-layer-molv',
        icon: 0,
        title: '提示',
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.ajax({
            type: "POST",
            async: "false",
            url: url,
            data: {
                "modelListJson": data
            },
            success: function (data) {
                if (data.code === 200) {
                    parent.notifications('success', title + "成功");
                    $(jqGridId).jqGrid('setGridParam', {url: dataUrl, page: 1}).trigger("reloadGrid");
                } else {
                    parent.notifications('error', data.msg);
                }
            },
            error: function () {
                parent.notifications('error', title + "失败,请检查网络!");
            },
            complete: function () {
                parent.layer.close(i);
            }
        });
    });
}

/**
 *
 * @param title
 * @param width
 * @param height
 * @param url
 */
function hm_fn_addLoan(param, title, width, height, url) {
    //判断id值是否存在
    if (param == undefined || param == '') {
        layer.alert('请注意,参数未传入!!!', {title: "提示", icon: 5, skin: 'layer-ext-moon', closeBtn: 0});
    }
    let index = parent.layer.open({
        // skin: 'layui-layer-molv',
        type: 2, //iframe 层
        title: title,
        scrollbar: false,
        fix: false, //不固定
        maxmin: true,
        area: [width, height],
        btn: ['确定', '关闭'],
        yes: function (index, layero) {
            window.parent[layero.find('iframe')[0]['name']].validateForm();
        },
        cancel: function (index) {
        },
        content: url + '?type=' + param
    });
}


/**
 * 公共通知方法
 */
function notifications(type, msg, title) {
    let message;
    if (type == 'success') {
        message = msg || "操作成功";
    } else if (type == 'info') {
        message = msg || "操作成功";
    } else if (type == 'warning') {
        message = msg || "参数错误";
    } else if (type == 'error') {
        message = msg || "操作失败";
    }

    title = title || "系统提示";
    parent.toastr.options = {
        "closeButton": true,
        "debug": false,
        "progressBar": false,
        "positionClass": "toast-top-right",
        "onclick": null,
        "showDuration": "400",
        "hideDuration": "1000",
        "timeOut": "7000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };
    parent.toastr[type](message, title);
}

/**
 * 条件查询下拉框效果
 */
function dropdownSelectHoverFun(that) {
    let self = $(that);
    self.addClass("open");
    self.hover(
        function () {
            self.addClass("open");
        },
        function (e) {
            if (e.target.nodeName !== "INPUT") {
                if (e.target.nodeName !== "SELECT" && e.target.nodeName !== "OPTION") {
                    self.removeClass("open");
                }
            }
        });
}

/**
 * 公共数据查询
 */
function dropdownSearchFun(that) {
    let self = $(that),
        url = self.attr("data-url"),
        tableId = "#" + self.attr("data-tid"),
        data = self.parents("form").serialize(),
        searchForm = $('.search-form-adv').find('form');
    if (self.siblings('#clear-btn').length) {
        $('#search-input').val('');
    } else {
        searchForm.find(".type").each(function () {
            $(this).children().eq(0).attr("selected", "selected");
        });
        searchForm.find('input').val('');
        if (searchForm[0]) {
            searchForm[0].reset()
        }
    }
    let url_w = url.indexOf('?') > -1 ? "&" : "?";
    $(tableId).jqGrid('setGridParam', {
        datatype: 'json',
        url: url + url_w + data,
        page: 1
    }).trigger("reloadGrid");
}

/**
 * 查询文件清除
 */
function dropdownSearchCleanFun(that) {
    let self = $(that),
        url = self.attr("data-url"),
        tableId = "#" + self.attr("data-tid"),
        searchForm = self.parents("form");
    searchForm.find(".type").each(function () {
        $(this).children().eq(0).attr("selected", "selected");
    });
    searchForm.find('input').val('');
    $('#search-input').val('');
    if (searchForm[0]) {
        searchForm[0].reset()
    }
    let url_w = url.indexOf('?') > -1 ? "&" : "?";
    $(tableId).jqGrid('setGridParam', {
        datatype: 'json',
        url: url,
        page: 1
    }).trigger("reloadGrid");
}

/**
 * 公共查询方法
 */
// function gridReload(that) {
// 	let self = $(that),
// 			tableId = "#" + self.attr("data-tid"),
// 			searchVal = self.siblings('#search-input').val();
//   $(tableId).jqGrid('setGridParam', {
//       datatype:'json',
//       postData:{
//       	'search_val':searchVal
//       },
//       page: 1
//   }).trigger("reloadGrid");
// }

/**
 * 公共刷新方法
 */
function refreshFun(that) {
    let self = $(that).parent().find('#clear-btn');
    //帮助中心调用此处方法 但页面没有元素 增加判断
    if (self == undefined || self.html() == undefined) {
        self = $(that);
    }
    let url = self.attr("data-url"),
        tableId = "#" + self.attr("data-tid"),
        searchForm = self.parents("form");
    searchForm.find(".type").each(function () {
        $(this).children().eq(0).attr("selected", "selected");
    });
    searchForm.find('input').val('');
    $('#search-input').val('');
    if (searchForm[0]) {
        searchForm[0].reset()
    }
    let url_w = url.indexOf('?') > -1 ? "&" : "?";
    $(tableId).jqGrid('setGridParam', {
        datatype: 'json',
        url: url,
        page: 1
    }).trigger("reloadGrid");
    // let self = $(that);
    // tableId = "#" + self.attr("data-tid");
    // $(tableId).trigger("reloadGrid"); //重新载入
}

/**
 * 导出excel
 */
function exportFun(that) {
    layer.confirm('确定要导出吗？', {
        btn: ['确定', '取消']
    }, function (index) {
        layer.close(index);
        let self = $(that),
            url = self.attr("data-url"),
            url_w = url.indexOf('?') > -1 ? "&" : "?";
        tableId = "#" + self.attr("data-tid");
        if ($('#search-input').val()) {
            let data = $('.search-form').find('form').serialize();
        } else {
            let data = $('.search-form-adv').find('form').serialize();
        }
        let colModels = $(tableId).jqGrid('getGridParam', 'colModel'),
            colNames = $(tableId).jqGrid('getGridParam', 'colNames'),
            colModel = "";
        for (let i = 1; i < colModels.length; i++) {
            let jo = JSON.parse(JSON.stringify(colModels[i]));
            colModel += jo.name + ",";
            let index1 = jo.index;
        }
        colNames = colNames.toString().substring(3);
        window.location.href = url + url_w + data + "&colNames=" + colNames + "&colModel=" + colModel;
    });
}

/**
 * 用户信息导出
 */
function exportsFun(that) {
    parent.layer.confirm('是否确定导出?', {
        // skin: 'layui-layer-molv',
        btn: ['确定', '取消'] //按钮
    }, function () {
        parent.layer.closeAll();
        let self = $(that),
            url = self.attr("data-url"),
            url_w = url.indexOf('?') > -1 ? "&" : "?";
        if ($('#search-input').val()) {
            let data = $('.search-form').find('form').serialize();
        } else {
            let data = $('.search-form-adv').find('form').serialize();
        }
        window.location.href = url + url_w + data;
    });
}

/**
 * 宣传管理页面查看方法
 */
function hm_fn_viewFun(title, width, height, url, id, winname, closeFunc) {
    //判断id值是否存在
    if (id == undefined || id == '') {
        layer.alert('id获取异常', {icon: 5, skin: 'layer-ext-moon'});
        return;
    }

    //判断url中是否已经挂参
    if (url.indexOf("?") == -1) {
        url = url + "?ids=" + id + "&winname=" + winname;
    } else {
        url = url + "&ids=" + id + "&winname=" + winname;
    }

    let index = parent.layer.open({
        // skin: 'layui-layer-molv',
        type: 2, //iframe 层
        title: title,
        scrollbar: false,
        fix: false, //不固定
        maxmin: true,
        area: [width, height],
        btn: ['确定', '关闭'],
        yes: function (index, layero) {
            window.parent[layero.find('iframe')[0]['name']].validateForm();
        },
        end: closeFunc,
        cancel: function (index) {
        },
        content: url
    });
}

/**
 * 展示页面
 */
function hm_fn_viewFun2(title, width, height, url, id, winname, closeFunc) {
    //判断id值是否存在
    console.log(id);
    if (id == undefined || id == '') {
        layer.alert('id获取异常', {icon: 5, skin: 'layer-ext-moon'});
        return;
    }

    //判断url中是否已经挂参
    if (url.indexOf("?") == -1) {
        url = url + "?ids=" + id + "&winname=" + winname;
    } else {
        url = url + "&ids=" + id + "&winname=" + winname;
    }

    let index = parent.layer.open({
        // skin: 'layui-layer-molv',
        type: 2, //iframe 层
        title: title,
        scrollbar: false,
        fix: false, //不固定
        maxmin: true,
        area: [width, height],
        btn: ['确定'],
        // yes: function(index, layero){
        // window.parent[layero.find('iframe')[0]['name']].validateForm();
        // },
        yes: closeFunc,
        end: closeFunc,
        content: url
    });
}

/**
 * 查看界面
 * @param url
 */

function hm_fn_viewFun5(url) {
    layer.open({
        type: 2, //iframe 层
        title: false,
        scrollbar: false,
        fix: false, //不固定
        maxmin: false,
        area: ["100%", "100%"],
        btn: ['关闭'],
        cancel: function (index) {
        },
        content: url
    });
}

/**
 * 代理商添加下级代理商，选择用户返回数据
 * @param title
 * @param width
 * @param height
 * @param url
 * @param id
 * @param winname
 * @param closeFunc
 */
function hm_fn_viewFun3(title, width, height, url, winname, closeFunc) {
    // //判断id值是否存在
    // if (id == undefined || id == '') {
    //     layer.alert('id获取异常', {icon: 5, skin: 'layer-ext-moon'});
    //     return;
    // }

    //判断url中是否已经挂参
    if (url.indexOf("?") == -1) {
        url = url + "?winname=" + winname;
    } else {
        url = url + "&winname=" + winname;
    }

    let index = parent.layer.open({
        // skin: 'layui-layer-molv',
        type: 2, //iframe 层
        title: title,
        scrollbar: false,
        fix: false, //不固定
        maxmin: true,
        area: [width, height],
        btn: ['确定'],
        yes: function (index, layero) {
            let data = window.parent[layero.find('iframe')[0]['name']].validateForm();
            $("#phone").val(data["phone"]);
            $("#phoneLabel").html(data["phone"]);
            $("#userId").val(data["userId"]);
            $("#realName").val(data["realName"]);
            $("#realNameLabel").html(data["realName"]);
            parent.layer.close(index);
        },
        end: closeFunc,
        content: url
    });
}

/**
 * 厂家设置运费规则选择城市
 * @param title
 * @param width
 * @param height
 * @param url
 * @param winname
 * @param closeFunc
 * @param ids 初始传入的ids,在city页面设置初始勾选
 * @param rowId 编辑的jqgrid rowId,用于设置数据
 */
function hm_fn_viewFun4(title, width, height, url, winname, rowId, ids, closeFunc) {
    // //判断id值是否存在
    // if (id == undefined || id == '') {
    //     layer.alert('id获取异常', {icon: 5, skin: 'layer-ext-moon'});
    //     return;
    // }

    //判断url中是否已经挂参
    if (url.indexOf("?") == -1) {
        url = url + "?winname=" + winname;
    } else {
        url = url + "&winname=" + winname;
    }
    if (ids) {
        url = url + "&ids=" + ids;
    }

    let index = parent.layer.open({
        // skin: 'layui-layer-molv',
        type: 2, //iframe 层
        title: title,
        scrollbar: false,
        fix: false, //不固定
        maxmin: true,
        area: [width, height],
        btn: ['确定'],
        yes: function (index, layero) {
            let data = window.parent[layero.find('iframe')[0]['name']].validateForm();
            $(jqGridId).jqGrid("setCell", rowId, "dest", data["names"]);
            $(jqGridId).jqGrid("setCell", rowId, "ids", data["ids"]);
            parent.layer.close(index);
        },
        end: closeFunc,
        content: url
    });
}

/**
 * 好友邀请树状图
 */
function hm_fn_viewtreeFun(title, width, height, url, id, winname, closeFunc) {
    //判断id值是否存在
    if (id == undefined || id == '') {
        layer.alert('id获取异常', {icon: 5, skin: 'layer-ext-moon'});
        return;
    }

    //判断url中是否已经挂参
    /*if(url.indexOf("?") == -1){
        url = url + "?ids=" + id + "&winname="+winname;
    }else{
        url = url + "&ids="+ id + "&winname="+winname;
    }
*/
    let index = parent.layer.open({
        // skin: 'layui-layer-molv',
        type: 2, //iframe 层
        title: title,
        scrollbar: false,
        fix: false, //不固定
        maxmin: true,
        area: [width, height],
        btn: ['确定', '关闭'],
        yes: closeFunc,
        end: closeFunc,
        cancel: function (index) {
        },
        content: url + "?ids=" + id + "&winname=" + winname
    });
}

//重置管理员
function hm_fn_resetFun(jqGridId, dataUrl, url, id) {
    if (id == undefined || id == '') {
        layer.alert('id获取异常', {icon: 5, skin: 'layer-ext-moon'});
        return;
    }
    let i = parent.layer.confirm('确定重置管理员密码吗？', {
        // skin: 'layui-layer-molv',
        icon: 0,
        title: '提示',
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.ajax({
            async: "true",
            url: url,
            data: "ids=" + id,
            success: function (data) {
                if (data.result) {
                    parent.notifications('success', "重置成功");
                    $(jqGridId).jqGrid('setGridParam', {url: dataUrl, page: 1}).trigger("reloadGrid");
                } else {
                    parent.notifications('error', data.msg);
                }
            },
            error: function () {
                parent.notifications('error', "操作失败,请检查网络!");
            },
            complete: function () {
                parent.layer.close(i);
            }
        });
    });
}


/*禁止空格输入 */
function banInputSpace(e) {
    let keynum;
    if (window.event) { // IE
        keynum = e.keyCode
    } else if (e.which) {  // Netscape/Firefox/Opera
        keynum = e.which
    }
    if (keynum == 32) {
        return false;
    }
    return true;
}

function doAjax(uri, datas, index) {
    $.ajax({
        async: true,
        url: uri,
        data: datas,
        success: function (data) {
            if (data.result) {
                parent.notifications('success', "操作成功");
                $(jqGridId).jqGrid('setGridParam', {url: dataUrl, page: 1}).trigger("reloadGrid");
                parent.layer.close(index);
            } else {
                parent.layer.close(index);
                parent.notifications('error', data.msg);
            }
        },
        error: function () {
            parent.layer.close(index);
            parent.notifications('error', "操作失败,请检查网络!");
        }
    })
}

function doAjax(uri, datas) {
    $.ajax({
        async: true,
        url: uri,
        data: datas,
        success: function (data) {
            if (data.result) {
                parent.notifications('success', "操作成功");
                $(jqGridId).jqGrid('setGridParam', {url: dataUrl, page: 1}).trigger("reloadGrid");
            } else {
                parent.notifications('error', data.msg);
            }
        },
        error: function () {
            parent.notifications('error', "操作失败,请检查网络!");
        }
    })
}

function subtotal(obj, data) {
    let rowNum = obj.getRowData.length;
    if (rowNum > 0) {
        let a = {};
        a[data[0]] = "小计:";
        for (let i = 1; i < data.length; i++) {
            let val = obj.getCol(data[i], false, 'sum');
            a[data[i]] = val.toFixed(2);
        }
        obj.footerData("set", a);
    }
}

// 校验任务是否选择设备 或 分组
function checkedListOrGroup() {
    let title = '系统提示';
    // 选择设备还是分组
    let val = $('input:radio[name="deviceStyle"]:checked').val();
    if (val == 'equipment') {
        //设备
        let equipments = $("#equipments").val();
        if (equipments == '' || equipments == null) {
            parent.notifications('error', "请选择设备", title);
            return false;
        }
    }
    if (val == 'grouping') {
        //分组
        let equipments = $("#grouping").val();
        if (equipments == null || equipments == '') {
            parent.notifications('error', "dddd", title);
            return false;
        }
    }
    return true;
}

// 校验超级热度下任务是否选择设备 或 分组
function checkedLiveHOTListOrGroup() {
    let title = '系统提示';
    // 选择设备还是分组
    let val = $('input:radio[name="deviceStyle"]:checked').val();
    if (val == 'equipment') {
        //设备
        let equipments = $("#equipments").val();
        if (equipments == '' || equipments == null) {
            parent.notifications('error', "请选择设备", title);
            return false;
        }
    }
    if (val == 'grouping') {
        //分组
        let equipments = $("#equipments").val();
        if (equipments == null || equipments == '') {
            parent.notifications('error', "请选择分组", title);
            return false;
        }
    }
    return true;
}