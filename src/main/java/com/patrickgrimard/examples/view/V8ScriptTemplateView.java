package com.patrickgrimard.examples.view;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.V8Value;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * Created on 2016-12-03
 *
 * @author Patrick
 */
public class V8ScriptTemplateView extends AbstractUrlBasedView {

    private static final String DEFAULT_CONTENT_TYPE = "text/html";

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private static final String DEFAULT_RESOURCE_LOADER_PATH = "classpath:";

    private static final String DEFAULT_RENDER_FUNCTION = "render";

    private String[] scripts;

    private String renderFunction;

    private Charset charset;

    private String[] resourceLoaderPaths;

    private ResourceLoader resourceLoader;

    public V8ScriptTemplateView() {
        setContentType(null);
    }

    public V8ScriptTemplateView(String url) {
        super(url);
        setContentType(null);
    }

    public void setScripts(String... scripts) {
        this.scripts = scripts;
    }

    public void setRenderFunction(String renderFunction) {
        this.renderFunction = renderFunction;
    }

    @Override
    public void setContentType(String contentType) {
        super.setContentType(contentType);
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public void setResourceLoaderPath(String resourceLoaderPath) {
        String[] paths = StringUtils.commaDelimitedListToStringArray(resourceLoaderPath);
        this.resourceLoaderPaths = new String[paths.length + 1];
        this.resourceLoaderPaths[0] = "";
        for (int i = 0; i < paths.length; i++) {
            String path = paths[i];
            if (!path.endsWith("/") && !path.endsWith(":")) {
                path = path + "/";
            }
            this.resourceLoaderPaths[i + 1] = path;
        }
    }

    @Override
    protected void initApplicationContext(ApplicationContext context) {
        super.initApplicationContext(context);

        V8ScriptTemplateConfig viewConfig = autodetectViewConfig();

        if (this.scripts == null && viewConfig.getScripts() != null) {
            this.scripts = viewConfig.getScripts();
        }
        if (this.renderFunction == null) {
            this.renderFunction = (viewConfig.getRenderFunction() != null ? viewConfig.getRenderFunction() : DEFAULT_RENDER_FUNCTION);
        }
        if (this.getContentType() == null) {
            setContentType(viewConfig.getContentType() != null ? viewConfig.getContentType() : DEFAULT_CONTENT_TYPE);
        }
        if (this.charset == null) {
            this.charset = (viewConfig.getCharset() != null ? viewConfig.getCharset() : DEFAULT_CHARSET);
        }
        if (this.resourceLoaderPaths == null) {
            String resourceLoaderPath = viewConfig.getResourceLoaderPath();
            setResourceLoaderPath(resourceLoaderPath == null ? DEFAULT_RESOURCE_LOADER_PATH : resourceLoaderPath);
        }
        if (this.resourceLoader == null) {
            this.resourceLoader = getApplicationContext();
        }
    }

    @Override
    protected void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
        super.prepareResponse(request, response);

        setResponseContentType(request, response);
        response.setCharacterEncoding(this.charset.name());
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        V8 v8 = V8.createV8Runtime("window");

        List<V8Value> runtimeObjects = new ArrayList<>();

        List<V8Value> scriptResults = Arrays.stream(this.scripts)
                .map(script -> {
                    try {
                        return v8.executeScript(getResourceAsString(script));
                    } catch (Exception e) {
                        throw new IllegalStateException(String.format("Failed to execute script %s", script), e);
                    }
                })
                .filter(o -> o instanceof V8Value)
                .map(o -> (V8Value) o)
                .collect(toList());
        runtimeObjects.addAll(scriptResults);

        String template = getResourceAsString(getUrl());
        V8Object modelAttributes = mapToV8Object(v8, runtimeObjects, model);
        runtimeObjects.add(modelAttributes);

        Object html = v8.executeJSFunction(this.renderFunction, template, modelAttributes);
        response.getWriter().write(String.valueOf(html));

        runtimeObjects.forEach(V8Value::release);
        v8.release();
    }

    private V8Object mapToV8Object(V8 v8, List<V8Value> runtimeObjects, Map<String, Object> model) {
        V8Object result = new V8Object(v8);

        model.forEach((key, object) -> {
            if (object == null) {
                result.addNull(key);
            } else if (object instanceof V8Value) {
                result.add(key, (V8Value) object);
            } else if (object instanceof Integer) {
                result.add(key, (Integer) object);
            } else if (object instanceof Double) {
                result.add(key, (Double) object);
            } else if (object instanceof Long) {
                result.add(key, ((Long) object).doubleValue());
            } else if (object instanceof Float) {
                result.add(key, (Float) object);
            } else if (object instanceof Boolean) {
                result.add(key, (Boolean) object);
            } else if (object instanceof String) {
                result.add(key, (String) object);
            } else if (object instanceof Map) {
                result.add(key, mapToV8Object(v8, runtimeObjects, (Map<String, Object>) object));
            } else if (object instanceof Iterable) {
                result.add(key, modelToV8Array(v8, runtimeObjects, (Iterable<Object>) object));
            } else if (object.getClass().isArray()) {
                result.add(key, modelToV8Array(v8, runtimeObjects, Arrays.asList((Object[]) object)));
            } else {
                throw new IllegalArgumentException("Unsupported Object of type: " + object.getClass());
            }
        });

        runtimeObjects.add(result);
        return result;
    }

    private V8Array modelToV8Array(V8 v8, List<V8Value> runtimeObjects, Iterable<Object> iterable) {
        V8Array result = new V8Array(v8);

        iterable.forEach(object -> {
            if (object == null) {
                result.pushNull();
            } else if (object instanceof V8Value) {
                result.push((V8Value) object);
            } else if (object instanceof Integer) {
                result.push((Integer) object);
            } else if (object instanceof Double) {
                result.push((Double) object);
            } else if (object instanceof Long) {
                result.push(((Long) object).doubleValue());
            } else if (object instanceof Float) {
                result.push((Float) object);
            } else if (object instanceof Boolean) {
                result.push((Boolean) object);
            } else if (object instanceof String) {
                result.push((String) object);
            } else if (object instanceof Map) {
                result.push(mapToV8Object(v8, runtimeObjects, (Map<String, Object>) object));
            } else if (object instanceof Iterable) {
                result.push(modelToV8Array(v8, runtimeObjects, (Iterable<Object>) object));
            } else if (object.getClass().isArray()) {
                result.push(modelToV8Array(v8, runtimeObjects, Arrays.asList((Object[]) object)));
            } else {
                throw new IllegalArgumentException("Unsupported Object of type: " + object.getClass());
            }
        });

        runtimeObjects.add(result);
        return result;
    }

    protected Resource getResource(String location) {
        for (String path : this.resourceLoaderPaths) {
            Resource resource = this.resourceLoader.getResource(path + location);
            if (resource.exists()) return resource;
        }

        throw new IllegalStateException(String.format("Resource %s not found", location));
    }

    protected String getResourceAsString(String path) throws IOException {
        Resource resource = getResource(path);
        InputStreamReader reader = new InputStreamReader(resource.getInputStream(), this.charset);
        return FileCopyUtils.copyToString(reader);
    }

    protected V8ScriptTemplateConfig autodetectViewConfig() throws BeansException {
        try {
            return BeanFactoryUtils.beanOfTypeIncludingAncestors(
                    getApplicationContext(), V8ScriptTemplateConfig.class, true, false);
        } catch (NoSuchBeanDefinitionException ex) {
            throw new ApplicationContextException("Expected a single V8ScriptTemplateConfig bean in the current " +
                    "Servlet web application context or the parent root context: V8ScriptTemplateConfigurer is " +
                    "the usual implementation. This bean may have any name.", ex);
        }
    }
}
