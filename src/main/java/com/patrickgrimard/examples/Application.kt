package com.patrickgrimard.examples

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter
import org.springframework.web.servlet.ViewResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.view.script.ScriptTemplateConfigurer
import org.springframework.web.servlet.view.script.ScriptTemplateViewResolver

@SpringBootApplication
open class Application : WebMvcConfigurerAdapter() {

    @Bean
    open fun viewResolver(): ViewResolver {
        return ScriptTemplateViewResolver("/public/", ".html")
    }

    @Bean
    open fun scriptTemplateConfigurer(): ScriptTemplateConfigurer {
        val configurer = ScriptTemplateConfigurer()
        configurer.engineName = "nashorn"
        configurer.setScripts(
                "static/polyfill.js",
                "public/server.js"
        )
        configurer.renderFunction = "render"
        configurer.isSharedEngine = false
        return configurer
    }

    @Configuration
    open class RestConfiguration : RepositoryRestConfigurerAdapter() {
        override fun configureRepositoryRestConfiguration(config: RepositoryRestConfiguration?) {
            config!!.exposeIdsFor(Item::class.java).setBasePath("/api")
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }
}