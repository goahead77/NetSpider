package spider.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.entity.ContentType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import spider.entity.School;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hzbc on 2016/6/20.
 */

/**
 * @since v1.2
 */
public class SchoolHandler implements ResponseHandler<List<School>> {

    private static List<String> provinceList=new ArrayList<>();
    private static int perPageNumbers=5;//每页显示的记录数

    private String level;
    private String local;
    private String page;
    private String url;

    public SchoolHandler(String level,String local,String page,String url){
        this.level=level;
        this.local=local;
        this.page=page;
        this.url=url;
    }

    public SchoolHandler(String url){
        this.url=url;
    }

    public List<School> handleResponse(HttpResponse response) throws ClientProtocolException, IOException {

        List<School> schools=new ArrayList<>();

        if (response.getStatusLine().getStatusCode()!=200)
            throw new IOException("Bad Response:"+response.getStatusLine());
        Charset charset = ContentType.parse(response.getEntity().getContentType().getValue()).getCharset();
        if(charset==null){
            charset=ContentType.TEXT_HTML.getCharset();
        }
        Document document = Jsoup.parse(response.getEntity().getContent(), "UTF-8",url);
        Elements elements = document.select(".right_box h2 a");
        Element temp;
        for(Element element:elements){
            elements = document.select(".right_box h3");
            temp=elements.get(element.siblingIndex());
            String desc=temp.text();
            School school=new School();
            school.setSName(element.text());
            schools.add(school);
        }

        return schools;
    }

}
