package com.biner.ru.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author KangBinbin
 * @date 2018年1月18日
 * @description 用来发送http请求
 */
public class HttpUtils {

	private static Logger logger = Logger.getLogger(HttpUtils.class);

	private static final CloseableHttpClient httpclient = HttpClients.createDefault();

	/**
	 * 发送HttpGet请求
	 * 
	 * @param url
	 * @return
	 */
	public static String sendGet(String url) {
		logger.info("Http Get Request Begining url:::" + url);
		HttpGet httpget = new HttpGet(url);
		CloseableHttpResponse response = null;
		try {
			response = execute(httpget, url);
		} catch (IOException e) {
			logger.error("Http Get Request url[" + url + "]error", e);
		}
		String result = null;

		try {

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity);
			}
		} catch (ParseException e) {
			logger.error("Http Get Request url[" + url + "]error", e);
		} catch (IOException e) {
			logger.error("Http Get Request url[" + url + "]error", e);
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				logger.error("Http Get Request url[" + url + "]error", e);
			}
		}
		logger.info("Http Get Request end url[" + url + "]:::Result[" + result + "]");
		return result;
	}

	/**
	 * 发送HttpPost请求，参数为map
	 * 
	 * @param url
	 * @param map
	 * @return
	 */
	public static String sendPost(String url, Map<String, String> map) {
		logger.info("Http Post Request Begining url[" + url + "]:::Params[" + map == null ? "" : map.toString() + "]");
		HttpPost httppost = new HttpPost(url);
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
		httppost.setEntity(entity);
		CloseableHttpResponse response = null;
		try {
			response = execute(httppost, url);
		} catch (IOException e) {
			logger.error("Http Post Request first url[" + url + "]error" + ", para : " + JSON.toJSONString(map), e);
			try {
				logger.error("Http Post Request second url[" + url + "] begin");
				response = execute(httppost, url);
			} catch (IOException e1) {
				logger.error("Http Post Request first url[" + url + "]error end ", e1);
			}
		}
		HttpEntity entity1 = response.getEntity();

		String result = null;
		try {
			result = EntityUtils.toString(entity1);
		} catch (ParseException | IOException e) {
			logger.error("Http Post Request url[" + url + "]error", e);
		}
		logger.info("Http Post Request end url[" + url + "]:::Params[" + map.toString() + "]:::Result[" + result + "]");
		return result;
	}

	/**
	 * @author KangBinbin
	 * @date 2018年1月30日
	 * @description 
	 */
	public static CloseableHttpResponse execute(HttpUriRequest request, String url) throws IOException {
		CloseableHttpResponse response = httpclient.execute(request);
		return response;
	}


	/**
	 * 
	 * @Title: downloadPicture @Description: 下载PDF，得到base64 @param @param
	 *         urlList @param @param path @author Shawn @return void @throws
	 */
	public static String downloadPDF(String urlList) {
		URL url = null;
		try {
			url = new URL(urlList);
			DataInputStream dataInputStream = new DataInputStream(url.openStream());
			ByteArrayOutputStream output = new ByteArrayOutputStream();

			byte[] buffer = new byte[1024];
			int length;

			while ((length = dataInputStream.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}

			byte[] buff = output.toByteArray();
			System.out.println(buff.length);

			@SuppressWarnings("restriction")
			String str = com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(buff);
			dataInputStream.close();
			return str;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @author KangBinbin
	 * @date 2018年2月3日
	 * @description 下载url指向的文件到指定的文件名路径
	 *
	 */
	public static void downloadFile(String urlPath, String path) {
		URL url = null;
		DataInputStream dataInputStream = null;
		ByteArrayOutputStream output = null;
		FileOutputStream fileOutputStream = null;
		try {
			url = new URL(urlPath);
			dataInputStream = new DataInputStream(url.openStream());
			output = new ByteArrayOutputStream();
			fileOutputStream = new FileOutputStream(new File(path));

			byte[] buffer = new byte[1024];
			int length;

			while ((length = dataInputStream.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
			output.flush();
			fileOutputStream.write(output.toByteArray());
		} catch (MalformedURLException e) {
			logger.error("Download File By url has error.", e);
		} catch (IOException e) {
			logger.error("Download File By url has error.", e);
		} finally {
			if (dataInputStream != null) {
				try {
					dataInputStream.close();
				} catch (IOException e) {
				}
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
				}
			}
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
				}
			}
		}
	}

}
