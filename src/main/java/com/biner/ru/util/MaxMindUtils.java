package com.biner.ru.util;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import org.apache.log4j.Logger;

import com.biner.ru.common.Constants;
import com.biner.ru.model.IpInfo;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Postal;
import com.maxmind.geoip2.record.Subdivision;

/**
 * @date 2018-09-28
 * @author Biner
 * @description MaxMind的数据库GeoLite2相关工具类
 */
public class MaxMindUtils {

	private static Logger logger = Logger.getLogger(MaxMindUtils.class);
	
	private static DatabaseReader reader;
	
	public static synchronized DatabaseReader getDataBaseReader() {
		if (reader == null) {
			// A File object pointing to your GeoIP2 or GeoLite2 database
			File database = new File(Constants.geoLitePath);
			
			// This creates the DatabaseReader object. To improve performance, reuse
			// the object across lookups. The object is thread-safe.
			try {  
				reader = new DatabaseReader.Builder(database).build();
			} catch (IOException e) {
				logger.info("Get MaxMind DatabaseReader has error.", e);
			}
		}
		return reader;
	}
	
	public static IpInfo getIpInfo(String ip) throws Exception {
		InetAddress ipAddress = InetAddress.getByName(ip);
		getDataBaseReader();
		// Replace "city" with the appropriate method for your database, e.g.,
		// "country".
		CityResponse response = reader.city(ipAddress);

		Country country = response.getCountry();

		Subdivision subdivision = response.getMostSpecificSubdivision();

		City city = response.getCity();

		Postal postal = response.getPostal();
		
		return new IpInfo(ip, country.getNames().get("zh-CN"), subdivision.getNames().get("zh-CN"),
				city.getNames().get("zh-CN"), postal.getCode());
	}
	
	public static void getIpInfoTest(String ip) throws Exception {
		InetAddress ipAddress = InetAddress.getByName(ip);
		getDataBaseReader();
		// Replace "city" with the appropriate method for your database, e.g.,
		// "country".
		CityResponse response = reader.city(ipAddress);

		Country country = response.getCountry();
		System.out.println(country.getIsoCode());            // 'US'
		System.out.println(country.getName());               // 'United States'
		System.out.println(country.getNames().get("zh-CN")); // '美国'

		Subdivision subdivision = response.getMostSpecificSubdivision();
		System.out.println(subdivision.getName());    // 'Minnesota'
		System.out.println(subdivision.getNames().get("zh-CN"));    // 'Minnesota'
		System.out.println(subdivision.getIsoCode()); // 'MN'

		City city = response.getCity();
		System.out.println(city.getNames().get("zh-CN"));
		System.out.println(city.getName()); // 'Minneapolis'

		Postal postal = response.getPostal();
		System.out.println(postal.getCode()); // '55455'
		
	}
	
	public static void main(String[] args) throws Exception {
		String ip = "128.101.101.101";
		getIpInfoTest(ip);
		ip = "220.181.132.199";
		System.out.println(getIpInfo(ip));
	}
}
