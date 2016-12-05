package com.patrickgrimard.examples.view;

import java.nio.charset.Charset;

/**
 * Configuration interface for V8 script execution runtime.
 *
 * Created on 2016-12-04
 *
 * @author Patrick
 */
public interface V8ScriptTemplateConfig {

    /**
     * @return The scripts to be executed by V8.
     */
    String[] getScripts();

    /**
     * @return The name of the render function which should produce a {@code String}.
     */
    String getRenderFunction();

    /**
     * @return The content type of the response.
     */
    String getContentType();

    /**
     * @return The {@link Charset} to use when reading script files.
     */
    Charset getCharset();

    /**
     * @return The resource loader paths.
     */
    String getResourceLoaderPath();
}
