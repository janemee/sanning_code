package com.huimi.core.config;

import com.huimi.core.constant.Constants;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * 自定义token标签
 *
 * @author Administrator
 */
public class TokenTag implements TemplateDirectiveModel {

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        Boolean isAjax = null == params.get("ajax") ? false : true;
        String formId = null == params.get("id") ? Constants.FORM_TOKEN : params.get("id").toString();
        String outTxt = "";
        String tokenValue = UUID.randomUUID().toString().toUpperCase();
        if (isAjax) {
            outTxt = Constants.FORM_TOKEN + "=" + tokenValue;
        } else {
            outTxt = "<input type='hidden' id='" + formId + "' name='" + Constants.FORM_TOKEN + "' value='" + tokenValue
                    + "'/>";
        }
//		redis.put(Caches.SYS.TOKEN_VALUE + tokenValue, tokenValue, Caches.SYS.TOKEN_OVER_TIME);
        env.getOut().write(outTxt);
    }

}
