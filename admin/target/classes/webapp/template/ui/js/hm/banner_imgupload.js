$(document).ready(function () {
    var BASE_URL = '/ui/js/plugins/webuploader';
    var $list = $('#fileList'),
        ratio = window.devicePixelRatio || 1,
        thumbnailWidth = 300 * ratio,
        thumbnailHeight = 200 * ratio,
        uploader,
        uploader2,
        uploader3;

    // 初始化Web Uploader
    uploader = WebUploader.create({
        auto: true,
        swf: BASE_URL + '/Uploader.swf',
        server: '/modules/statics/uploadFile?dir=banner',
        pick: '#filePicker',
        /*fileNumLimit:1 ,*/
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/jpg,image/jpeg,image/png'
        }
    });

    //首页banner
    uploader.on( 'uploadBeforeSend', function( block, data ) {
        var $oldfile = document.getElementById("old_banner_pic");
        if($oldfile){
            $oldfile.remove();
            //$oldfile.destroy();
        }
    });
    // 当有文件添加进来的时候
    uploader.on( 'fileQueued', function( file ) {
        var $li = $(
                '<div id="' + file.id + '" class="file-item thumbnail">' +
                '<img>' +
                '<div class="info">' + file.name + '</div>' +
                '</div>'
            ),
            $img = $li.find('img');
        $('#rt_'+file.source.ruid).parent().siblings(".uploader-list").html($li);
        //$list.append( $li );
        // 创建缩略图
        uploader.makeThumb( file, function( error, src ) {
            if ( error ) {
                $img.replaceWith('<span>不能预览</span>');
                return;
            }
            $img.attr( 'src', src );
        }, thumbnailWidth, thumbnailHeight );
    });
    // 文件上传过程中创建进度条实时显示。
    uploader.on( 'uploadProgress', function( file, percentage ) {
        var $li = $( '#'+file.id ),
            $percent = $li.find('.progress span');
        // 避免重复创建
        if ( !$percent.length ) {
            $percent = $('<p class="progress"><span></span></p>')
                .appendTo( $li ).find('span');
        }
        $percent.css( 'width', percentage * 100 + '%' );
    });
    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader.on( 'uploadSuccess', function( file , response ) {
        $( '#'+file.id ).addClass('upload-state-done');
        if(response.msg=="上传成功"){
            $('#rt_'+file.source.ruid).parent().siblings(".filepath").val(response.path);
            $("#banner_pic").val(response.data.fileId);

        }
    });
    // 文件上传失败，现实上传出错。
    uploader.on( 'uploadError', function( file ) {
        var $li = $( '#'+file.id ),
            $error = $li.find('div.error');
        // 避免重复创建
        if ( !$error.length ) {
            $error = $('<div class="error"></div>').appendTo( $li );
        }
        $error.text('上传失败');
    });
    // 完成上传完了，成功或者失败，先删除进度条。
    uploader.on( 'uploadComplete', function( file ) {
        $( '#'+file.id ).find('.progress').remove();
    });

});