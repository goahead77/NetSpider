package spider.service;

import org.springframework.stereotype.Component;
import spider.http.ConnectClient;

import java.io.IOException;

/**
 * Created by hzbc on 2016/6/20.
 */
@Component
public class Runner {

    public void run(){
        ConnectClient connectClient=new ConnectClient();
        try {
            connectClient.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
