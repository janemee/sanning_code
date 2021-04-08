package com.huimi.common.tools;

import org.slf4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 描述: 聚合数据调用接口工具类 作者: 陌上人 时间: 2016/11/29 15:19
 */
public class HttpsRequestUtil {

	//private static Logger log = LoggerFactory.getLogger(JuHeHttpKit.class);

	public static final String GET = "GET";
	public static final String POST = "POST";
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

	public static String send(String strUrl, String method, String EpayXMLstr) throws Exception {
		HttpURLConnection connection = null;
		BufferedReader reader = null;
		String rs = null;
		try {
			StringBuilder sb = new StringBuilder();
			if (method == null || method.equals(GET)) {
				strUrl = strUrl + "?" + EpayXMLstr;
			}
			URL url = new URL(strUrl);
			connection = (HttpURLConnection) url.openConnection();
			if (method == null || method.equals(GET)) {
				method = GET;
				connection.setRequestMethod(GET);
			} else {
				method = POST;
				connection.setRequestMethod(POST);
				connection.setDoOutput(true);
			}
			connection.setRequestProperty("User-agent", userAgent);
			connection.setUseCaches(false);
			connection.setConnectTimeout(DEF_CONN_TIMEOUT);
			connection.setReadTimeout(DEF_READ_TIMEOUT);
			connection.setInstanceFollowRedirects(false);
			connection.connect();

			if (EpayXMLstr != null && method.equals(POST)) {
				try {
					DataOutputStream out = new DataOutputStream(connection.getOutputStream());
					out.writeBytes(EpayXMLstr);
				} catch (Exception e) {
					e.printStackTrace();
					return "-1";
				}
			}
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
			String strRead;
			while ((strRead = reader.readLine()) != null) {
				sb.append(strRead);
			}
			rs = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (connection != null) {
				connection.disconnect();
			}
		}

		return rs;
	}

	// 将map型转为请求参数型
	@SuppressWarnings("rawtypes")
	public static String urlencode(Map<String, Object> data) {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", DEF_CHATSET)).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		}
		return sb.toString();
	}

	public static class LogUtils {
		public static void log(String msg, Logger log) {
			log.info(msg);
		}
		public static void log(String msg, Exception e, Logger log) {
			log.info(msg + " 异常 message = [" + e.getMessage() + "]", e);
		}

		public static void error(String msg, Exception e, Logger log) {
			log.error(msg + " 异常 message = [" + e.getMessage() + "]", e);
		}

	}
}
