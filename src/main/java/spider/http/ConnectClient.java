package spider.http;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import spider.entity.School;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by hzbc on 2016/6/20.
 */

/**
 * @since v1.2
 */
public class ConnectClient {

    private static String url="http://xuexiao.eol.cn/?cengci=%s_cengci&local1=%s_local1&page=%s";
    private static String index="http://xuexiao.eol.cn";
    private static String[] levels={"初中","高中"};//学校层次


    private static PoolingHttpClientConnectionManager manager=new PoolingHttpClientConnectionManager(10, TimeUnit.SECONDS);

    public HttpClient httpClient(){
        return HttpClientBuilder
                .create()
                .setConnectionManager(manager)
                .build();
    }


    /**
     * 抓取学校数据
     * @param level 层次
     * @param local 省
     * @param page 页码
     * @return 学校集合信息
     * @throws IOException 网络请求时的各种异常
     */
    public void catchSchool(String level,String local,int page) throws IOException {
        url=String.format(url,level,local,page);
        HttpGet httpGet=new HttpGet(url);
        List<School> schools= httpClient().execute(httpGet,new SchoolHandler(url));
        for(School school:schools){
            System.out.println("学校："+school.toString());
        }
    }

    public List<String> getProvince() throws IOException {
        HttpGet httpGet=new HttpGet(index);
        return httpClient().execute(httpGet,new ProvinceHandler(url));
    }

    public int getPage(String level,String local) throws IOException {
        url=String.format(url,level,local,1);
        HttpGet httpGet=new HttpGet(url);
        return httpClient().execute(httpGet,new PageHandler(url));

    }

    public void run() throws IOException {
        List<String> provinces=getProvince();
        for(String level:levels){
            for(String province:provinces){
                int pages=getPage(level,province);
                for(int i=1;i<=pages;i++){
                    catchSchool(level,province,i);
                }
            }
        }
    }
}
