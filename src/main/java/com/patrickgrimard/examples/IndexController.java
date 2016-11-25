package com.patrickgrimard.examples;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    private final ItemRepository itemRepository;

    public IndexController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/{path:(?!.*.js|.*.css|.*.jpg|api).*$}")
    public String index(Model model, HttpServletRequest request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> req = new HashMap<>();
        String root = request.getServletPath().equals("/index.html") ? "/" : request.getServletPath();
        if(request.getQueryString() != null)
            req.put("location", String.format("%s?%s", root, request.getQueryString()));
        else
            req.put("location", root);
        model.addAttribute("req", mapper.writeValueAsString(req));

        Map<String, Object> initialState = new HashMap<>();
        initialState.put("items", itemRepository.findAll());
        model.addAttribute("initialState", mapper.writeValueAsString(initialState));
        return "index";
    }
}
