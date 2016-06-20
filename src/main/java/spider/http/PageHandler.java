package spider.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by hzbc on 2016/6/20.
 */
public class PageHandler implements ResponseHandler<Integer> {
    private String url;

    public PageHandler(String url) {
        this.url = url;
    }
    @Override
    public Integer handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        if (response.getStatusLine().getStatusCode()!=200)
            throw new IOException("Bad Response:"+response.getStatusLine());
        Document document = Jsoup.parse(response.getEntity().getContent(), "UTF-8", url);
        Elements elements = document.select(".page font");
        return Integer.valueOf(elements.get(0).text());
    }
}
