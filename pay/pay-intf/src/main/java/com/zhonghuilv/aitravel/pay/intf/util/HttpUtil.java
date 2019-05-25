package com.zhonghuilv.aitravel.pay.intf.util;

import com.zhonghuilv.aitravel.pay.intf.common.excption.ServiceLogicException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Mango
 */
public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class.getName());
    ;
    private final static int CONNECT_TIMEOUT = 5000; // in milliseconds  
    private final static String DEFAULT_ENCODING = "UTF-8";

    public static String postData(String urlStr, String data) {
        return postData(urlStr, data, null);
    }

    public static String postData(String urlStr, String data, String contentType) {

        BufferedReader reader = null;
        OutputStreamWriter writer = null;
        try {
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(CONNECT_TIMEOUT);
            if (contentType != null)
                conn.setRequestProperty("content-type", contentType);
            writer = new OutputStreamWriter(conn.getOutputStream(), DEFAULT_ENCODING);
            if (data == null)
                data = "";
            writer.write(data);
            writer.flush();
            writer.close();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), DEFAULT_ENCODING));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            logger.error("Error connecting to " + urlStr + ": " + e.getMessage(), e);
            throw new ServiceLogicException("访问微信服务器出错:" + e.getMessage());

        } finally {
            IOUtils.closeQuietly(reader, writer);
        }
    }
}
