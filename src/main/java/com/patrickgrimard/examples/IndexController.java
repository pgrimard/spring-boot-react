package com.patrickgrimard.examples;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2016-11-22
 *
 * @author Patrick
 */
@Controller
public class IndexController {

    @GetMapping("/{path:(?!.*.js|.*.css|.*.jpg).*$}")
    public String index(Model model, HttpServletRequest request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> state = new HashMap<>();

        String root = request.getServletPath().equals("/index.html") ? "/" : request.getServletPath();

        if(request.getQueryString() != null)
            state.put("location", String.format("%s?%s", root, request.getQueryString()));
        else
            state.put("location", root);

        model.addAttribute("state", mapper.writeValueAsString(state));
        return "index";
    }

    @GetMapping(value = "/items", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Map<String, Object> hello() {
        Map<String, Object> items = new HashMap<>();
        items.put("0", "javascript");
        items.put("1", "react");
        items.put("2", "react-router");
        items.put("3", "redux");
        return items;
    }
}
