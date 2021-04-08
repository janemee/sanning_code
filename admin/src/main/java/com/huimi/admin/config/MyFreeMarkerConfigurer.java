package com.huimi.admin.config;

import com.huimi.admin.tags.ShiroTags;
import freemarker.cache.TemplateLoader;
import freemarker.template.TemplateException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.List;

/**
 *
 */
public class MyFreeMarkerConfigurer extends FreeMarkerConfigurer {
    @Override
    protected TemplateLoader getAggregateTemplateLoader(List<TemplateLoader> templateLoaders) {
        return super.getAggregateTemplateLoader(templateLoaders);
    }

    @Override
    public void afterPropertiesSet() throws IOException, TemplateException {
        super.afterPropertiesSet();
        this.getConfiguration().setSharedVariable("shiro", new ShiroTags());
    }
}
