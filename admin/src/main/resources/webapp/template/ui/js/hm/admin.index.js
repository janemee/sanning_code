//退出登录
$("#logout,.btn_signOut").click(function () {
    //询问框
    layer.confirm('是否确定退出登录?', {
        skin: 'layui-layer-molv',
        btn: ['确定', '取消'] //按钮
    }, function () {
        window.location.href = "/s/logout";
    });
});

//锁屏
// $("#lock").click(function(){
//     window.location.href = "/modules/login/locked";
// });

//修改密码
// $("#modifypwd").click(function(){
//     //询问框
//     layer.confirm('是否确定退出登录?', {
//         skin: 'layui-layer-molv',
//         btn: ['确定','取消'] //按钮
//     }, function(){
//         window.location.href = "/modules/logout";
//     });
// });

//个人信息
// $("#profile").click(function(){
//     //询问框
//     layer.confirm('是否确定退出登录?', {
//         skin: 'layui-layer-molv',
//         btn: ['确定','取消'] //按钮
//     }, function(){
//         window.location.href = "/modules/logout";
//     });
// });

//个人信息
$(".btn-topNav").eq(0).addClass("hover");
$(".btn-topNav").on("click", function () {
    var parentids = $(this).attr("data-ids");
    $(this).addClass("hover").siblings().removeClass("hover");
    $.ajax({
        type: "GET",
        data: "menuId=" + parentids,
        url: "/s/getVerMenuTreeByHorId",
        success: function (data) {
            //var cxt = "";
            if (data) {
                var html = "";
                $.each(data, function (i, n) {
                    var count = i + 1;
                    html = html + "<li>";
                    if (n.isParent == 1) {
                        html += '<a href="javascript:;" class="fistNav">';
                    } else {
                        html += '<a class="J_menuItem fistNav" href="' + n.url + '" data-index="' + parentids + '_' + count + '">';
                    }
                    html += '<i class="' + n.images + '"></i><span class="nav-label">' + n.name + '</span>';
                    if (n.isParent == 1) {
                        html += '<span class="fa arrow"></span>';
                    }
                    html += '</a>';

                    var subMenu = n.subMenu;
                    if (subMenu) {
                        if (subMenu.length > 0) {
                            html += '<ul class="nav nav-second-level" style="display:none">';
                            $.each(subMenu, function (i, n) {
                                var data_index = i + 1;
                                html += '<li>';
                                if (n.isParent == 1) {
                                    html += '  <a href="javascript:;" >';
                                } else {
                                    html += '<a class="J_menuItem" href="' + n.url + '"  data-index="' + parentids + '_' + count + '_' + data_index + '">';

                                }
                                html += '<i class="' + n.images + '"></i><span class="nav-label">' + n.name + '</span>';
                                if (n.isParent == 1) {
                                    html += '<span class="fa arrow"></span>';
                                }
                                html += '</a></li>';
                            });
                            html += '</ul>';
                        }
                    }
                    html += '</li>';

                });
                $("#side-menu").html(html);
            } else {
                parent.notifications('error', data.msg || '加载失败');
            }

        },
        error: function () {
            parent.notifications('error', "操作失败,请检查网络!");
        }
    });


});

$(function () {
    //$(".btn-topNav").eq(0).trigger("click");
    var accordion_body = $('.nav li > .nav-second-level');
    $('.nav').on('click', ".fistNav", function (event) {
        // Disable header links
        event.preventDefault();
        // Show and hide the tabs on click
        if ($(this).parent().attr('class') != 'active') {
            $(this).parent().siblings().find(".nav-second-level").slideUp('normal');
            $(this).next().stop(true, true).slideToggle('normal');
            $(this).parent().siblings().removeClass('active');
            $(this).parent().addClass('active');
        } else {
            $(this).parent().removeClass('active');
            $(this).next().stop(true, true).slideToggle('normal');
        }
    });
})
