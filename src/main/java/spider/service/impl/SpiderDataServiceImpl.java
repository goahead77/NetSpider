package spider.service.impl;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spider.entity.School;
import spider.repository.SchoolRepository;
import spider.service.SpiderDataService;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
@Service
public class SpiderDataServiceImpl implements SpiderDataService {

    @Autowired
    SchoolRepository schoolRepository;

    private Logger logger= LoggerFactory.getLogger(SpiderDataServiceImpl.class);

    private static WebClient webClient;
    private static String MAIN_URL="http://xuexiao.eol.cn";
    private static String GET_SCHOOL="http://xuexiao.eol.cn/?cengci=%s_cengci&local1=%s_local1&page=%s";
    private static String[] levels={"初中","高中"};//学校层次

    private static final String CN_COLON="\u003a";//表示中文状态下的分号
    private static final String EN_COLON="\uff1a";//表示英文状态下的分号

    static {
        webClient=new WebClient();
        webClient.setJavaScriptTimeout(0);
    }

    @Override
    public  void spider() throws IOException {
        try {
            List<String> provinces = getProvinces();
            for (String province : provinces) {
                for (String level : levels) {
                    int page = getPage(province, level);
                    for (int i = 1; i <= page; i++) {
                        schools(province, level, i);
                    }
                }
            }
        }catch (Exception ex){
            logger.error(ex.getMessage());
        }finally {
            webClient.close();
        }
    }
    private List<String> getProvinces() throws IOException {
        HtmlPage page=webClient.getPage(MAIN_URL);
        HtmlSelect province = (HtmlSelect) page.getByXPath("//select[@name='province']").get(0);
        List<HtmlOption> options=province.getOptions();
        List<String> provinces= options.stream().map(HtmlOption::getText).collect(Collectors.toList());
        return provinces;
    }

    private int getPage(String province,String level) throws IOException {
        int perPageNum=5;//根绝页面可以看到，每页显示的数目是5条
        String url=String.format(GET_SCHOOL, URLEncoder.encode(level,"utf-8"),URLEncoder.encode(province,"utf-8"),1);
        HtmlPage page=webClient.getPage(url);
        logger.info(page.asText());
        HtmlFont font=(HtmlFont)page.getByXPath("//font[@color='red']").get(0);//
        String total=font.getTextContent();//总记录数
        int temp=Integer.valueOf(total);
        int pageNum=temp%perPageNum==0?temp/perPageNum:temp/perPageNum+1;//总页数
        return pageNum;
    }

    /**
     * 抓取到所有学校信息，并持久化
     * @param province 省
     * @param level 层次
     * @param pageNum 页数
     * @throws IOException 异常
     */
    private void schools(String province,String level,int pageNum) throws IOException {
        String url=String.format(GET_SCHOOL,URLEncoder.encode(level,"utf-8"),URLEncoder.encode(province,"utf-8"),pageNum);
        HtmlPage page=webClient.getPage(url);
        List<HtmlDivision> divisions= (List<HtmlDivision>) page.getByXPath("//div[@class='right_box']");
        List<HtmlAnchor> htmlAnchors= (List<HtmlAnchor>) page.getByXPath("//a[@style='color: #D34803;']");
        HtmlAnchor htmlAnchor=null;
        List<String> shools=new ArrayList<>();
        String detailUrl="";
        HtmlDivision htmlDivision=null;
        String detailInfo="";
        School school=null;
        byte[] temp;
        for(int i=0;i<htmlAnchors.size();i++){
            school=new School();
            htmlDivision=divisions.get(i);
            temp= htmlDivision.getTextContent().trim().getBytes();
            detailInfo=new String(temp,"utf-8");
            logger.info("详细信息："+detailInfo);
            int addrStart=detailInfo.indexOf(new String("地址".getBytes(),"utf-8"));
            int postStart=detailInfo.indexOf(new String("邮编".getBytes(),"utf-8"));
            int telStart=detailInfo.indexOf(new String("电话".getBytes(),"utf-8"));
            String address=detailInfo.substring(addrStart,postStart).trim();//中文冒号！也是奇葩
            address=splitString(address);
            String post=detailInfo.substring(postStart,telStart).trim();
            post=splitString(post);
            String tel=detailInfo.substring(telStart,detailInfo.length()).trim();
            tel=splitString(tel);

            school.setSTel(tel);
            school.setPostCode(post);
            school.setAddress(address);
            logger.info("address:"+address);
            logger.info("post:"+post);
            logger.info("tel:"+tel);


            htmlAnchor=htmlAnchors.get(i);
            detailUrl=htmlAnchor.getAttribute("href");
            detailUrl=detailUrl.replace("index","intro");
            page=webClient.getPage(detailUrl);
            if(page.getByXPath("//div[@class='pad_20 line_22']").size()>0){
                htmlDivision= (HtmlDivision) page.getByXPath("//div[@class='pad_20 line_22']").get(0);
                logger.info("学校简介："+htmlDivision.getTextContent().trim());
                school.setSDesc(htmlDivision.getTextContent().trim());
            }else{
                logger.info("学校简介：暂无！");
                school.setSDesc("暂无！");
            }
            logger.info("学校名称："+htmlAnchor.getTextContent());
            school.setSName(htmlAnchor.getTextContent());
            school.setLevel(level);
            school.setProvince(province);
            schoolRepository.save(school);
        }
    }

    private String splitString(String old){
        try{
            if(old.contains(EN_COLON)){
                old= old.split(EN_COLON)[1];
            }else if(old.contains(CN_COLON))
                old=old.split(CN_COLON)[1];
        }catch (Exception ex){
            logger.error(ex.getMessage());
            return "未知";
        }
        return old;
    }
}
