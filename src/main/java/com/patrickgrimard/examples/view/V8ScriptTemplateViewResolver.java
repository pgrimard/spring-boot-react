package com.patrickgrimard.examples.view;

import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * Created on 2016-12-03
 *
 * @author Patrick
 */
public class V8ScriptTemplateViewResolver extends UrlBasedViewResolver {
    public V8ScriptTemplateViewResolver() {
        setViewClass(requiredViewClass());
    }

    public V8ScriptTemplateViewResolver(String prefix, String suffix) {
        this();
        setPrefix(prefix);
        setSuffix(suffix);
    }


    @Override
    protected Class<?> requiredViewClass() {
        return V8ScriptTemplateView.class;
    }
}
