package net.afnf.sboot1.web;

import java.util.HashMap;
import java.util.Map;

import net.afnf.sboot1.service.HelloWorldService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TopController {

    @Autowired
    private HelloWorldService helloWorldService;

    @RequestMapping("/")
    public ModelAndView top(@RequestParam(value = "param1", required = false) String param1) {

        Map<String, String> model = new HashMap<String, String>();
        model.put("message", helloWorldService.getHelloMessage());
        model.put("param1", "param1 is " + param1);
        return new ModelAndView("top", model);
    }
}
