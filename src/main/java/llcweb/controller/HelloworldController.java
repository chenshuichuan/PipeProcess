package llcweb.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 10:41
 */
@RestController
public class HelloworldController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/hello")
    public String index(){

        logger.trace("logging trace!");
        logger.debug("logging debug!");
        logger.info("logging info!");
        logger.warn("logging warm!");
        logger.error("logging error!");

        //String str =
        return  "Hello World!123";

    }
}
