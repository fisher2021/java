package com.upd.common.util.splider;

import com.upd.auth.entity.Operator;
import com.upd.auth.service.OperatorService;
import com.upd.business.entity.Article;
import com.upd.business.form.ArticleForm;
import com.upd.business.service.ArticleService;
import com.upd.business.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * Created by xiao.tao on 2017/10/13.
 */
@Component("doSplider")
public class DoSplider {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private DictService dictService;
    @Autowired
    private OperatorService operatorService;

    private static String culture = "http://culture.people.com.cn";//文化
    private static String edu = "http://edu.people.com.cn";//教育
    private static String finance = "http://finance.people.com.cn";//经济
    private static String military = "http://military.people.com.cn";//军事
    private static String sports = "http://sports.people.com.cn";//体育
    private static String scitech = "http://scitech.people.com.cn";//科技
    private static String travel = "http://travel.people.com.cn";//旅游
    private static String dangjian = "http://www.dangjian.cn";//中央新闻

    /**
     * 保存
     * @param title
     * @param content
     * @param type
     * @param author
     */
    public void saveArt(String title, String content, Integer type, String author){
        ArticleForm form = new ArticleForm();
        form.setTitle(title);
        List<Article> articles = articleService.articleList(form);
        if (articles == null && articles.isEmpty()){
            Article article = new Article();
            article.setTitle(title);
            article.setContent(content);
            article.setType(dictService.get(type));
            article.setAuthor(author);
            article.setTargetOut(false);
            article.setOperator(operatorService.get(1));
            article.setCreateTime("");
            article.setImgUrl("/static/back/img/news.png");
            articleService.add(article);
        }
    }

    /**
     * 读取html代码
     * @param datas
     * @return
     */
    public static String readHtml(List<LinkTypeData> datas) {
        String res = "";
        for (LinkTypeData data : datas)
        {
            res+=data.getContent();
        }
        return res;
    }

    /**
     * 文化网
     */
    public void wenhuawang(String url,Integer dictId){
        Rule rule = new Rule(url, new String[] { "word" }, new String[] { "" }, "news_box", 0, Rule.GET);
        List<LinkTypeData> extracts = ExtractService.extract(rule);
        int times = 0;
        for (LinkTypeData data : extracts) {
            Rule rule1 = new Rule(url+data.getLinkHref(), new String[] { "word" }, new String[] { "" }, "rwb_zw", 1, Rule.GET);
            List<LinkTypeData> extracts1 = ExtractService.extractContent(rule1);
            try {
                saveArt(data.getLinkText(), readHtml(extracts1),dictId,"文化网");
            } catch (Exception e) {
                e.printStackTrace();
            }
            times++;
            if(times>5){
                break;
            }
        }
    }

    /**
     * 党建网
     */
    public void dangjian(String url,Integer dictId){
        Rule rule = new Rule(url, new String[] { "word" }, new String[] { "" }, "djfb-bottom", 0, Rule.GET);
        List<LinkTypeData> extracts = ExtractService.extract(rule);
        int times = 0;
        for (LinkTypeData data : extracts) {
            Rule rule1 = new Rule(url+data.getLinkHref(), new String[] { "word" }, new String[] { "" }, "main-left", 0, Rule.GET);
            List<LinkTypeData> extracts1 = ExtractService.extractContent(rule1);
            try {
                saveArt(data.getLinkText(), readHtml(extracts1),dictId,"中央新闻");
            } catch (Exception e) {
                e.printStackTrace();
            }
            times++;
            if(times>5){
                break;
            }
        }
    }

    public void doSplider(){
        wenhuawang(culture,56);
        wenhuawang(edu,57);
        wenhuawang(finance,58);
        wenhuawang(military,59);
        wenhuawang(sports,60);
        wenhuawang(scitech,61);
        wenhuawang(travel,62);
        dangjian(dangjian,7);
    }

    public static void main(String[] args) {
//        DoSplider doSplider = new DoSplider();
//        doSplider.doSplider();
        String path = "/static/back/img/news.png";
        DoSplider splider = new DoSplider();
        System.out.println(splider.getImg());
        String relativelyPath=System.getProperty("user.dir");
        System.out.println(relativelyPath);
        DoSplider.class.getClassLoader().getResource("").getPath();
        File file = new File(relativelyPath);
        System.out.println(file.getName()+path);

    }
    public String getImg(){
        String path = this.getClass().getClassLoader().getResource("").getPath();
        return path;
    }
}
