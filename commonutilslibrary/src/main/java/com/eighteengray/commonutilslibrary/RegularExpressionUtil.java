package com.eighteengray.commonutilslibrary;


import java.util.regex.Pattern;


/**
 * 正则表达式工具类，包括常用正则表达式
 */
public class RegularExpressionUtil
{
    //密码的强度必须包含大小写字母和数字的组合，不能使用特殊字符，长度在8-10之间
    public final static Pattern password = Pattern.compile("^(?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$");
    //字符串只能是中文
    public final static Pattern checkChinese = Pattern.compile("^[\\\\u4e00-\\\\u9fa5]{0,}$");
    //由数字，26个英文字母或下划线组成的字符串
    public final static Pattern formatStr = Pattern.compile("^\\\\w+$");
    //校验E-Mail地址
    public final static Pattern email = Pattern.compile("[\\\\w!#$%&'*+/=?^_`{|}~-]+(?:\\\\.[\\\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\\\w](?:[\\\\w-]*[\\\\w])?\\\\.)+[\\\\w](?:[\\\\w-]*[\\\\w])?");
    //校验手机号
    public final static Pattern phone = Pattern.compile("^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\\\d{8}$");
    //校验身份证号码
    public final static Pattern ID = Pattern.compile("^[1-9]\\\\d{5}[1-9]\\\\d{3}((0\\\\d)|(1[0-2]))(([0|1|2]\\\\d)|3[0-1])\\\\d{3}([0-9]|X)$");
    //校验日期
    public final static Pattern date = Pattern.compile("^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$");
    //校验IP-v4地址
    public final static Pattern IPV4 = Pattern.compile("\\\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\\\b");
    //校验IP-v6地址
    public final static Pattern IPV6 = Pattern.compile("(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))");
    //检查URL的前缀
    public final static Pattern URLPre = Pattern.compile("/^[a-zA-Z]+:\\\\/\\\\//");
    //提取URL链接
    public final static Pattern URLContent = Pattern.compile("^(f|ht){1}(tp|tps):\\\\/\\\\/([\\\\w-]+\\\\.)+[\\\\w-]+(\\\\/[\\\\w ./?%&=]*)?");
    //文件路径及扩展名校验
    public final static Pattern filePathEx = Pattern.compile("^([a-zA-Z]\\\\:|\\\\\\\\)\\\\\\\\([^\\\\\\\\]+\\\\\\\\)*[^\\\\/:*?\"<>|]+\\\\.txt(l)?$");
    //提取Color Hex  Codes
    public final static Pattern ColorHexCodes = Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");
    //提取网页图片
    public final static Pattern pageImg = Pattern.compile("\\\\< *[img][^\\\\\\\\>]*[src] *= *[\\\\\"\\\\']{0,1}([^\\\\\"\\\\'\\\\ >]*)");
    //提取页面超链接
    public final static Pattern pageHref = Pattern.compile("(<a\\\\s*(?!.*\\\\brel=)[^>]*)(href=\"https?:\\\\/\\\\/)((?!(?:(?:www\\\\.)?'.implode('|(?:www\\\\.)?', $follow_list).'))[^\"]+)\"((?!.*\\\\brel=)[^>]*)(?:[^>]*)>");
    //查找CSS属性
    public final static Pattern cssAttr = Pattern.compile("^\\\\s*[a-zA-Z\\\\-]+\\\\s*[:]{1}\\\\s[a-zA-Z0-9\\\\s.#]+[;]{1}");
    //抽取注释
    public final static Pattern notes = Pattern.compile("<!--(.*?)-->");
    //匹配HTML标签
    public final static Pattern htmlTag = Pattern.compile("<\\\\/?\\\\w+((\\\\s+\\\\w+(\\\\s*=\\\\s*(?:\".*?\"|'.*?'|[\\\\^'\">\\\\s]+))?)+\\\\s*|\\\\s*)\\\\/?>");


    /**
     * 判断source是否符合pattern的正则
     * @param pattern  正则的规则
     * @param source  输入字符串
     * @return
     */
    public static boolean patternMatch(Pattern pattern, String source)
    {
        if (pattern == null)
            return false;
        return pattern.matcher(source).matches();
    }

}
