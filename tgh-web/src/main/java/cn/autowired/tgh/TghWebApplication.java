package cn.autowired.tgh;

import cn.autowired.tgh.dto.WebsocketDto;
import cn.autowired.tgh.web.api.WebSocketServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author bacch
 */
@SpringBootApplication
@MapperScan("cn.autowired.tgh.dao")
public class TghWebApplication
{

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(TghWebApplication.class, args);

    }

}
