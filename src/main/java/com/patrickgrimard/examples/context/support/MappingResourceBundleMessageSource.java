package com.patrickgrimard.examples.context.support;

import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.*;

/**
 * Created on 2016-12-06
 *
 * @author Patrick
 */
public class MappingResourceBundleMessageSource extends ResourceBundleMessageSource {

    public Map<String, Object> getMessageMap(String basename, Locale locale) {
        Map<String, Object> messages = new HashMap<>();
        ResourceBundle resourceBundle = this.getResourceBundle(basename, locale);

        if(resourceBundle == null) return Collections.emptyMap();

        Enumeration<String> keys = resourceBundle.getKeys();

        while(keys.hasMoreElements()) {
            String key = keys.nextElement();
            Object value = resourceBundle.getObject(key);
            messages.put(key, value);
        }

        return messages;
    }
}
