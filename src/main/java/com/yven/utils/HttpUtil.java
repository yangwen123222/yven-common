package com.yven.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * get和post请求工具类
 *
 * @author yven
 */
public class HttpUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * 执行一个HTTP GET请求，返回请求响应的HTML
     *
     * @param url         请求的URL地址
     * @param queryString 请求的查询参数,可以为null
     * @param charset     字符集
     * @param pretty      是否美化
     * @return 返回请求响应的HTML
     */
    public static String doGet(String url, String queryString, String charset, boolean pretty) {
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        try {
            if (StringUtils.isNotBlank(queryString)) {
                // 对get请求参数做了http请求默认编码，好像没有任何问题，汉字编码后，就成为%式样的字符串
                method.setQueryString(URIUtil.encodeQuery(queryString));
            }
            // 执行请求
            client.executeMethod(method);
            // 获取请求结果
            response = getResponseStr(charset, pretty, method);
        } catch (URIException e) {
            log.error("执行HTTP Get请求时，编码查询字符串“" + queryString + "”发生异常！", e);
        } catch (IOException e) {
            log.error("执行HTTP Get请求" + url + "时，发生异常！", e);
        } finally {
            method.releaseConnection();
        }
        return response.toString();
    }

    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     *
     * @param url     请求的URL地址
     * @param params  请求的查询参数,可以为null
     * @param charset 字符集
     * @param pretty  是否美化
     * @return 返回请求响应的HTML
     */
    public static String doPost(String url, Map<String, String> params, String charset, boolean pretty) {
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        HttpMethod method = new PostMethod(url);
        // 设置Http Post数据
        if (params != null) {

            // 这种方式设置无效
            /*HttpMethodParams p = new HttpMethodParams();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                p.setParameter(entry.getKey(), entry.getValue());
            }
            method.setParams(p);*/

            // 方式一
            for (Map.Entry<String, String> entry : params.entrySet()) {
                ((PostMethod) method).setParameter(entry.getKey(), entry.getValue() == null ? "" : entry.getValue());
            }

            // 方式二
           /*List<NameValuePair> list = new ArrayList<>();
            Set<String> set = params.keySet();
            Iterator<String> it = set.iterator();
            while(it.hasNext())
            {
                String key = it.next();
                list.add(new  NameValuePair(key, params.get(key)));
            }
            NameValuePair[] data  = new NameValuePair[params.size()];
            ((PostMethod) method).setRequestBody(list.toArray(data));*/
        }
        try {
            client.executeMethod(method);
            response = getResponseStr(charset, pretty, method);
        } catch (IOException e) {
            log.error("执行HTTP Post请求" + url + "时，发生异常！", e);
        } finally {
            method.releaseConnection();
        }
        return response.toString();
    }


    /**
     * 执行一个HTTP 请求，返回请求响应的HTML
     * @param url
     * @param queryString json字符串
     * @return
     */
    public static String doPost(String url, String queryString) {

        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient client = HttpClients.createDefault();
        String response = null;

        try {
            //解决中文乱码问题
            StringEntity entity = new StringEntity(queryString, "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            HttpResponse resp = client.execute(httpPost);
            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity he = resp.getEntity();
                response = EntityUtils.toString(he, "UTF-8");
            }
        } catch (URIException e) {
            log.error("执行HTTP " + " 请求时，编码查询字符串“" + queryString + "”发生异常！", e);
        } catch (IOException e) {
            log.error("执行HTTP " + " 请求" + url + "时，发生异常！", e);
        } finally {
        }
        return response;
    }


    private static StringBuffer getResponseStr(String charset, boolean pretty, HttpMethod method)
        throws IOException {
        StringBuffer response = new StringBuffer();

        if (method.getStatusCode() == HttpStatus.SC_OK) {
            BufferedReader reader =
                new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
            String line;
            while ((line = reader.readLine()) != null) {
                if (pretty) {
                    response.append(line).append(System.getProperty("line.separator"));

                } else {
                    response.append(line);
                }

            }
            reader.close();
        }
        return response;
    }
}
