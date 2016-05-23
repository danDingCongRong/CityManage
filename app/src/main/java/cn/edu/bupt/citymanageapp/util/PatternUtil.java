package cn.edu.bupt.citymanageapp.util;

import java.util.regex.Pattern;

/**
 * Created by chenjun14 on 16/5/19.
 */
public class PatternUtil {

    /**
     * 正则表达式：验证手机号
     */
    private static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * 正则表达式：验证密码
     */
    private static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    /**
     * 正则表达式：验证验证码
     */
    public static final String REGEX_CAPTCHA = "^[a-zA-Z0-9]{6}$";

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验验证码
     *
     * @param captcha
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isCaptcha(String captcha) {
        return Pattern.matches(REGEX_CAPTCHA, captcha);
    }

}
