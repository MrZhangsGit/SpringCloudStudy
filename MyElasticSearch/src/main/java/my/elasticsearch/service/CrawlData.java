package my.elasticsearch.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import my.elasticsearch.po.ColorModeBean;
import my.elasticsearch.po.HuaWeiPhoneBean;
import my.elasticsearch.po.PhoneModel;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 测试：页面改变，数据抓取失败
 * @author zhangs
 * @Description
 * @createDate 2020/4/1
 */
@Slf4j
@Component
public class CrawlData {
    @Autowired
    private PhoneRepository phoneRepository;

    public void crawl() {
        try {
            this.huawei();
        } catch (Exception e) {
            log.error("抓取失败!...{}", e);
        }
    }

    private void huawei() throws IOException {
        String url = "https://consumer.huawei.com/cn/phones/?ic_medium=hwdc&ic_source=corp_header_consumer";
        /**
         * 创建httpclient实例
         */
        CloseableHttpClient httpclient = HttpClients.createDefault();
        /**
         * 创建httpget实例
         */
        HttpGet httpget = new HttpGet(url);

        /**
         * 执行get请求
         */
        CloseableHttpResponse response = httpclient.execute(httpget);
        /**
         * 获取返回实体
         */
        HttpEntity entity=response.getEntity();
        /**
         * 指定编码打印网页内容
         */
        String content = EntityUtils.toString(entity, "utf-8");
        log.info("网页内容：" + content);
        /**
         * 关闭流和释放系统资源
         */
        response.close();


        Document document = Jsoup.parse(content);
        Elements elements = document.select("#content-v3-plp #pagehidedata .plphidedata");
        for (Element element : elements) {
            String jsonStr = element.text();
            List<HuaWeiPhoneBean> list = JSON.parseArray(jsonStr, HuaWeiPhoneBean.class);
            for (HuaWeiPhoneBean bean : list) {
                String productName = bean.getProductName();
                List<ColorModeBean> colorsItemModeList = bean.getColorsItemMode();

                StringBuilder colors = new StringBuilder();
                for (ColorModeBean colorModeBean : colorsItemModeList) {
                    String colorName = colorModeBean.getColorName();
                    colors.append(colorName).append(";");
                }

                List<String> sellingPointList = bean.getSellingPoints();
                StringBuilder sellingPoints = new StringBuilder();
                for (String sellingPoint : sellingPointList) {
                    sellingPoints.append(sellingPoint).append(";");
                }

                PhoneModel phoneModel = new PhoneModel()
                        .setName(productName)
                        .setColors(colors.substring(0, colors.length() - 1))
                        .setSellingPoints(sellingPoints.substring(0, sellingPoints.length() - 1))
                        .setCreateTime(new Date());
                log.info("phoneModel Save To DB:{}", phoneModel);
                phoneRepository.save(phoneModel);
            }
        }
    }
}
