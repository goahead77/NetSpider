package spider.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hzbc on 2016/6/20.
 */
public class ProvinceHandler implements ResponseHandler<List<String>> {
    private String url;

    public ProvinceHandler(String url) {
        this.url = url;
    }

    @Override
    public List<String> handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        List<String> provinceList=new ArrayList<>();
        if (response.getStatusLine().getStatusCode()!=200)
            throw new IOException("Bad Response:"+response.getStatusLine());
        Document document = Jsoup.parse(response.getEntity().getContent(), "UTF-8", url);
        Elements elements = document.select(".area_list li a");
        provinceList.addAll(elements.stream().map(Element::text).collect(Collectors.toList()));
        return provinceList;
    }
}
