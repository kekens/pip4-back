package pip.pip4back.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @RequestMapping({ "/main", "/auth" })
    public String index() {
        return "forward:/index.html";
    }
}
