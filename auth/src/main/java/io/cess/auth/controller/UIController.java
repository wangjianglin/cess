package io.cess.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lin
 * @date 28/06/2017.
 */
@Controller
@RequestMapping("/ui")
public class UIController {

    @RequestMapping()
    public String index(){
        return "/ui/index";
    }
}
