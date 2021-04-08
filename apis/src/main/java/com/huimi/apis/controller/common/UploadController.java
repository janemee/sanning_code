package com.huimi.apis.controller.common;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.RandomUtil;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.utils.OSSClientUtils;
import com.huimi.core.constant.ConfigNID;
import com.huimi.core.service.cache.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/web/upload")
@Api(tags = "文件上传")
public class UploadController extends WebController {

    @Autowired
    private RedisService redisService;

    private static final String UPLOAD_PATH = "/userFile";

    @AllArgsConstructor
    public enum Type {
        /**
         * 身份证图片 正面
         */
        T1(1, "member/id_card_1", ".jpg"),
        /**
         * 身份证图片 背面
         */
        T2(2, "member/id_card_2", ".jpg"),
        /**
         * 头像上传
         */
        T3(3, "public/avatar_pic", ".jpg"),
        /**
         * 银行卡
         */
        T4(4, "member/bank_card_pic", ".jpg"),
        /**
         * 聊天图片
         */
        T5(5, "member/msg_pic", ".jpg"),
        /**
         * 评价图片
         */
        T6(6, "member/comment_pic", ".jpg"),
        ;

        @Getter
        final int val;
        @Getter
        final String path;
        @Getter
        final String suffix;

        public static Type getEnum(int val) {
            for (Type type : Type.values()) {
                if (type.val == val) {
                    return type;
                }
            }
            return null;
        }
    }

    @ApiOperation(value = "文件上传-H5", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "base64转码图片", required = true, dataType = "String"),
            @ApiImplicitParam(name = "type", value = "类型:1身份证正面，2身份证背面，3头像，4银行卡，5聊天，6评论", required = true, dataType = "Integer"),
    })
    @PostMapping("/doUploadH5")
    public ResultEntity uploadForH5(String file, Integer type) {
        Integer loginUserId = getLoginUserId();
        if (null == loginUserId) {
            return fail("请先登录");
        }
        if (file.indexOf(",") > 0) {
            file = file.substring(file.indexOf(",") + 1);
        }
        Type fileType = Type.getEnum(type);
        byte[] b = Base64.decode(file);

        String fileName = RandomUtil.randomUUID() + fileType.getSuffix();
        String filePath = "/" + fileType.getPath() + "/" + fileName;
        String url = OSSClientUtils.uploadFile(b, filePath);
        //上传到图片服务器
        Map<String, Object> map = new HashMap<>();
        // 写入地址返回前端
        map.put("path", redisService.get(ConfigNID.IMAGE_SEVER_URL) + "/" + url);
        return ok(map);
    }

    @ApiOperation(value = "文件上传-IOS", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "图片流", required = true, dataType = "MultipartFile"),
            @ApiImplicitParam(name = "type", value = "类型:1身份证正面，2身份证背面，3头像，4银行卡，5聊天，6评论", required = true, dataType = "Integer"),
    })
    @PostMapping("/doUploadIOS")
    public ResultEntity uploadForIOS(MultipartFile file, Integer type) {
        Integer loginUserId = getLoginUserId();
        if (null == loginUserId) {
            return fail("请先登录");
        }
        //判断图片是否为空
        if (file.isEmpty()) {
            return ResultEntity.fail("图片为空");
        }
        String originalFileName = file.getOriginalFilename();
        String extName = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = RandomUtil.randomUUID() + "." + extName;
        Type fileType = Type.getEnum(type);

        //上传到图片服务器
//        map.put("src", configureRo.getBaseAliyunOssUrl() + "/" + path);
        try {
            String filePath = "/" + fileType.getPath() + "/" + fileName;
            String url = OSSClientUtils.uploadFile(file.getBytes(), filePath);
            Map<String, Object> map = new HashMap<>();
            map.put("path", redisService.get(ConfigNID.IMAGE_SEVER_URL) + "/" + url);

            return ok(map);
        } catch (IOException e) {
            e.printStackTrace();
            return fail("文件上传失败");
        }
    }
}
