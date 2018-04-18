package com.biner.ru.util;

import java.util.regex.Pattern;

public class RegularExpressionUtil {

	// 手机号
	public static final String REGEX_PHONE = "^1\\d{10}$";

	// （8-16位含数字和字母）
	public static final String REGEX_PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";

	// 身份证号
	public static final String REGEX_ID_CARD = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";

	// 身份证有效期限
	public static final String REGEX_ID_CARD_EXPIRE = "^[1-9]{1}[0-9]{3}\\.[0-9]{2}\\.[0-9]{2}-[1-9]{1}[0-9]{3}\\.[0-9]{2}\\.[0-9]{2}$";

	// 校验是否纯数字
	public static final String REGEX_INTEGER = "^\\d+$";

	// 校验日期
	public static final String REGEX_DATE = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))$";
	public static boolean isPhoneRight(String phone) {
		return Pattern.matches(REGEX_PHONE, phone);
	}

	public static boolean isPasswordRight(String password) {
		return Pattern.matches(REGEX_PASSWORD, password);
	}

	public static boolean isIdCardRight(String idCardNumber) {
		return Pattern.matches(REGEX_ID_CARD, idCardNumber);
	}

	public static boolean isIdCardExpireRight(String expireDate) {
		return Pattern.matches(REGEX_ID_CARD_EXPIRE, expireDate);
	}

	public static boolean isInteger(String value) {
		return Pattern.matches(REGEX_INTEGER, value);
	}
	
	public static boolean isDateRight(String value) {
		return Pattern.matches(REGEX_DATE, value);
	}
}
