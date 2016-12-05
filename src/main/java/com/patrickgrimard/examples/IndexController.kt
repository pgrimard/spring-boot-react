package com.patrickgrimard.examples

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.util.*
import javax.servlet.http.HttpServletRequest

/**
 * Created on 2016-11-22

 * @author Patrick
 */
@Controller
class IndexController(private val itemRepository: ItemRepository) {

    @GetMapping("/{path:(?!.*.js|.*.css|.*.jpg|api).*$}")
    @Throws(JsonProcessingException::class)
    fun index(model: Model, request: HttpServletRequest): String {
        val mapper = ObjectMapper()

        val req = HashMap<String, Any>()
        val root = if (request.servletPath == "/index.html") "/" else request.servletPath
        if (request.queryString != null)
            req.put("location", String.format("%s?%s", root, request.queryString))
        else
            req.put("location", root)
        model.addAttribute("req", req)

        val initialState = HashMap<String, Any>()
        initialState.put("items", itemRepository.findAll())
        model.addAttribute("initialState", mapper.writeValueAsString(initialState))
        return "index"
    }
}
