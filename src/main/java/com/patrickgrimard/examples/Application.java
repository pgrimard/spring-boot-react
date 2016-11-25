package com.patrickgrimard.examples;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.script.ScriptTemplateConfigurer;
import org.springframework.web.servlet.view.script.ScriptTemplateViewResolver;

@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ViewResolver viewResolver() {
        return new ScriptTemplateViewResolver("/public/", ".html");
    }

    @Bean
    public ScriptTemplateConfigurer scriptTemplateConfigurer() {
        ScriptTemplateConfigurer configurer = new ScriptTemplateConfigurer();
        configurer.setEngineName("nashorn");
        configurer.setScripts(
                "static/polyfill.js",
                "public/server.js"
        );
        configurer.setRenderFunction("render");
        configurer.setSharedEngine(false);
        return configurer;
    }

    @Bean
    public CommandLineRunner init(final ItemRepository itemRepository) {
        return (args) -> {
            itemRepository.save(new Item("JavaScript"));
            itemRepository.save(new Item("React"));
            itemRepository.save(new Item("React Router"));
            itemRepository.save(new Item("Redux"));
            itemRepository.save(new Item("RxJS"));
        };
    }

    @Configuration
    public static class RestConfiguration extends RepositoryRestConfigurerAdapter {
        @Override
        public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
            config.exposeIdsFor(Item.class).setBasePath("/api");
        }
    }
}
