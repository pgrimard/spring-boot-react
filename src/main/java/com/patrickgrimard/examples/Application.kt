package com.patrickgrimard.examples

import com.patrickgrimard.examples.view.MappingResourceBundleMessageSource
import com.patrickgrimard.examples.view.V8ScriptTemplateConfigurer
import com.patrickgrimard.examples.view.V8ScriptTemplateViewResolver
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.ViewResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import java.util.*

@SpringBootApplication
open class Application : WebMvcConfigurerAdapter() {

    @Bean
    open fun viewResolver(): ViewResolver {
        return V8ScriptTemplateViewResolver("/public/", ".html")
    }

    @Bean
    open fun scriptTemplateConfigurer(): V8ScriptTemplateConfigurer {
        return V8ScriptTemplateConfigurer("static/polyfill.js", "public/server.js")
    }

    @Bean
    open fun init(itemRepository: ItemRepository) = CommandLineRunner {
        itemRepository.save(Item("JavaScript", 0))
        itemRepository.save(Item("React", 1))
        itemRepository.save(Item("React Router", 2))
        itemRepository.save(Item("Redux", 0))
        itemRepository.save(Item("RxJS", 4))
    }

    @Configuration
    open class RestConfiguration : RepositoryRestConfigurerAdapter() {
        override fun configureRepositoryRestConfiguration(config: RepositoryRestConfiguration?) {
            config!!.exposeIdsFor(Item::class.java).setBasePath("/api")
        }
    }

    @Bean
    open fun localeResolver() : LocaleResolver {
        val resolver = AcceptHeaderLocaleResolver()
        resolver.defaultLocale = Locale.US
        return resolver
    }

    @Bean
    open fun messageSource() : MappingResourceBundleMessageSource {
        val source = MappingResourceBundleMessageSource()
        source.setBasenames("messages")
        return source
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }
}