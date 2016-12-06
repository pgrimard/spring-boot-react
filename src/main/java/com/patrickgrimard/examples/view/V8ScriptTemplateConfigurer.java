package com.patrickgrimard.examples.view;

import java.nio.charset.Charset;

/**
 * {@link V8ScriptTemplateConfig} implementation that will be detected if defined as a
 * {@link org.springframework.context.annotation.Bean} in the application context.  This implementation defines
 * a fluent API for setting config properties if needed.
 * <p>
 * Created on 2016-12-04
 *
 * @author Patrick
 */
public class V8ScriptTemplateConfigurer implements V8ScriptTemplateConfig {

    private String[] scripts;

    private String renderFunction;

    private String contentType;

    private Charset charset;

    private String resourceLoaderPath;

    private String resourceBundleBasename;

    /**
     * Convenience constructor which allows setting the scripts.  Makes it very easy to define a
     * {@code V8ScriptTemplateConfigurer} with all the default properties.
     * <p>
     * // Add the following to an &#64;Configuration class
     * &#64;Bean
     * public V8ScriptTemplateConfigurer scriptTemplateConfigurer() {
     *     return new V8ScriptTemplateConfigurer("static/server.js");
     * }
     * </pre>
     *
     * @param scripts The paths to scripts that will be executed.
     */
    public V8ScriptTemplateConfigurer(String... scripts) {
        this.scripts = scripts;
    }

    @Override
    public String[] getScripts() {
        return scripts;
    }

    /**
     * Set's the scripts to be executed by the {@link com.eclipsesource.v8.V8} runtime.
     *
     * @param scripts The paths to scripts that will be executed.
     * @return This configurer.
     */
    public V8ScriptTemplateConfigurer scripts(String... scripts) {
        this.scripts = scripts;
        return this;
    }

    @Override
    public String getRenderFunction() {
        return renderFunction;
    }

    /**
     * Sets the name of the render function to call if not using the default one.
     *
     * <p>Default is "render".
     *
     * @param renderFunction The name of the render function.
     * @return This configurer.
     */
    public V8ScriptTemplateConfigurer renderFunction(String renderFunction) {
        this.renderFunction = renderFunction;
        return this;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets the content type of the response.
     *
     * @param contentType The content type to use.
     * @return This configurer.
     */
    public V8ScriptTemplateConfigurer contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    @Override
    public Charset getCharset() {
        return charset;
    }

    /**
     * Sets the charset to use when reading script files.
     *
     * @param charset The charset to use.
     * @return This configurer.
     */
    public V8ScriptTemplateConfigurer charset(Charset charset) {
        this.charset = charset;
        return this;
    }

    @Override
    public String getResourceLoaderPath() {
        return resourceLoaderPath;
    }

    /**
     * Set the resource loader path(s) via a Spring resource location.
     * Accepts multiple locations as a comma-separated list of paths.
     * Standard URLs like "file:" and "classpath:" and pseudo URLs are supported
     * as understood by Spring's {@link org.springframework.core.io.ResourceLoader}.
     * Relative paths are allowed when running in an ApplicationContext.
     *
     * <p>Default is "classpath:".
     *
     * @param resourceLoaderPath The resource loader path.
     * @return This configurer.
     */
    public V8ScriptTemplateConfigurer resourceLoaderPath(String resourceLoaderPath) {
        this.resourceLoaderPath = resourceLoaderPath;
        return this;
    }

    @Override
    public String getResourceBundleBasename() {
        return resourceBundleBasename;
    }

    /**
     * Set the resource bundle basename to use for i18n if not using the default.
     *
     * <p>Default is "messages".
     *
     * @param resourceBundleBasename The resource bundle basename.
     * @return This configurer.
     */
    public V8ScriptTemplateConfigurer resourceBundleBasename(String resourceBundleBasename) {
        this.resourceBundleBasename = resourceBundleBasename;
        return this;
    }
}
