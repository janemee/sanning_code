package com.huimi.apis.config;



import com.huimi.common.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NoEmojiRequest extends HttpServletRequestWrapper {
    private Map<String , String[]> params;
    public NoEmojiRequest(HttpServletRequest request) {
        super(request);
        params = new HashMap<String, String[]>();
        params.putAll(request.getParameterMap());
    }

    @Override
    public String getParameter(String name) {
        String[] values = params.get(name);
        if (null == values){
            return null;
        }
        if (values.length<=0){
            return null;
        }
        return values[0].replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = params.get(name);
        if (null == values){
            return null;
        }
        if (values.length<=0){
            return null;
        }
        for(int i=0;i<values.length;i++){
            if (StringUtils.isNotBlank(values[i])){
                values[i] = values[i].replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
            }
        }
        return values;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Set<Map.Entry<String, String[]>> entries = params.entrySet();
        entries.forEach(ele->{
            String[] values = ele.getValue();
            if (null != values){
                for(int i=0;i<values.length;i++){
                    if (StringUtils.isNotBlank(values[i])){
                        values[i] = values[i].replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
                    }
                }
                ele.setValue(values);
            }
        });
        return params;
    }
}
