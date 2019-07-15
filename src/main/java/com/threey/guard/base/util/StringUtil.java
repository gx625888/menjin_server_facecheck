package com.threey.guard.base.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {


	
	/**
	 * 相关说明：判断字符串是否为空
	 * 开发者：汤云涛
	 * 时间：2015年6月29日 下午6:17:31
	 */
	public static boolean isEmpty(String str){
		return (str==null || "".equals(str))?true:false;
	}
	
	/**
	 * 
	 * @相关说明：是否为整形
	 * @开发者：汤云涛
	 * @时间：2015年7月28日 下午6:01:34
	 */
	public static Boolean isInteger(String str){
		try{
			Integer.parseInt(str);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @相关说明：是否为double形
	 * @开发者：汤云涛
	 * @时间：2015年7月28日 下午6:01:34
	 */
	public static Boolean isDouble(String str){
		try{
			Double.parseDouble(str);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	public static String division(double divisor, double dividend){
		return (new BigDecimal(divisor).divide(new BigDecimal(dividend))).setScale(2).toString();
	}
	
	public static String getRandomStr(int longth){
		
		longth = (longth<=0?100000:longth);
		return Integer.toString((int)(Math.random()*longth));
	};
	/**
	 * 相关说明：
	 * 开发者：汤云涛
	 * 时间：2015年1月12日 下午4:33:04
	 */
	public static String formatNumber(String number, String format){
		
		DecimalFormat df = new DecimalFormat(format);
		return df.format(Double.parseDouble(number));
	}
	/**
	 * 相关说明：格式化信息
	 * 开发者：汤云涛
	 * 时间：2015年1月9日 下午3:16:01
	 */
	public static String formatMsg(String template, Object...args) {
		return MessageFormat.format(template, args);
	}

	/**
	 * 相关说明：把数组中的字符串拼接成'','',''的形式从数据库中做in查询
	 */
	public static String makeSqlIds(String[] choiceboxs) {
		String ids = "";
		for (int a = 0; a < choiceboxs.length; a++) {
			ids += "'" + choiceboxs[a] + "',";
		}
		if (ids.endsWith(",")) {
			ids = ids.substring(0, ids.length() - 1);
		}
		return ids;
	}

	/**
	 * 相关说明：判断num是否是BigDecimal类型
	 */
	public static boolean checkIsNum(String num) {
		try {
			new BigDecimal(num);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 相关说明：可以作为数据库ID使用
	 */
	public static String getUuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static String getRandomString(int length) { //length表示生成字符串的长度
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";
	    Random random = new Random();
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	 }     
	
	public static String getTUuid() {
		Date date = DateTimeUtil.getFormatDate("2017-01-01");
//		String id = getRandomString(2)+Long.toHexString((System.currentTimeMillis()-date.getTime())/1000);
		String id = ""+((System.currentTimeMillis()-date.getTime())/1000);
		String aString = ""+(int)(Math.random()*10);
		String bString = ""+(int)(Math.random()*10);
		if(id.length()==8) {
			id = aString+bString+id;
		}else if(id.length()==9) {
			id = aString+id;
		}
		return id;
	}

	public static String replaceStr(String newChar) {
		if (null == newChar || "".equals(newChar)) {
			return "";
		} else {
			return newChar.replace("-", "");
		}
	}

	/**
	 * 相关说明：保留小数点后两位有效数字
	 */
	public static String doubleFormat(Double value) {
		long l1 = Math.round(value * 100);
		double ret = l1 / 100.0;
		return String.valueOf(ret);
	}

	/**
	 * 相关说明：流水号生成方式
	 */
	public static String generateTransId() {
		String transId = "23000" + getTimetmp() + getSequence();
		return transId;
	}

	/**
	 * 相关说明：获取当前时间戳,例如：20121225
	 */
	public static String getTimetmp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dt = sdf.format(new Date());
		return dt;
	}

	/**
	 * 相关说明：获取当前时间戳,例如：20121225111111
	 */
	public static String getTimetmp2() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String dt = sdf.format(new Date());
		return dt;
	}

	/**
	 * 相关说明：获取随机的10个数字
	 */
	public static String getSequence() {
		Random random = new Random();
		int thisId = random.nextInt(999999999);
		return String.format("%010d", thisId);
	}

	/**
	 * 相关说明：获取随机的11个数字
	 */
	public static String getRandomCard() {
		Random random = new Random();
		int thisId = random.nextInt(999999999);
		return String.format("0" + "%010d", thisId);
	}
	
	
	public static String getRandomNum() {
		Random random = new Random();
		int thisId = random.nextInt(9);
		return String.format("%02d", thisId);
	}
	

	/**
	 * 相关说明：整数主键
	 */
	public static String isNaNId() {
		return getTimetmp2() + getSequence();
	}
	
	public static String isDishId() {
		return getTimetmp2() + getRandomNum();
	}

	/**
	 * 相关说明：生成订单号
	 */
	public static String generateOrderNumber() {
		return "96" + getSequence() + getTimetmp2();
	}

	/**
	 * 相关说明：判断字符串中是否有中文 开发者：tangchang 时间：2014-12-16 下午2:16:17
	 */
	public static boolean isChineseChar(String str) {
		boolean temp = false;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			temp = true;
		}
		return temp;
	}
	
	
    /**
     * 判断对象是否为空
     *
     * @param obj Object
     * @return boolean 空返回true,非空返回false
     */
    public static boolean isNull(Object obj) {
        return (null == obj) ? true : false;
    }

    /**
     * Description:判断字段空null <br>
     *
     * @param s
     * @return boolean
     */
    public static boolean isNull(String s) {
        if (s == null || "".equals(s.trim()) || s.length()<=0) {
            return true;
        }

        return false;
    }
	
	public static void main(String[] args) {


    	for (int i=0;i<40;i++){
			System.out.println(new Random().nextInt(1));
			System.out.println(new Random().nextInt(1));
			System.out.println(new Random().nextInt(2));
		}



	}
	
    public static String nullToSring(Object obj){
        if(null == obj || "".equals(obj) || "null".equals(obj)){
            return "";
        }
        return String.valueOf(obj);
    }

	/**
	 * [简要描述]:获得当天的日期,格式yyyyMMdd<br/>
	 * [详细描述]:<br/>
	 *
	 * @return
	 * @exception
	 */
	public static String getCurrDateStr() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String currDate = sdf.format(date);
		return currDate;
	}


	public static List<String> getRecentDays(int day){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List days = new ArrayList();
		Calendar calendar = Calendar.getInstance();
		for (int i=0;i<=day;i++){
			calendar.add(Calendar.DAY_OF_MONTH ,i==0?0:-1);
			days.add(sdf.format(calendar.getTime()));
		}
		return days;
	}

	public static boolean isTel(String phone){
		String regex1 = "(\\+\\d+)?1[3458]\\d{9}$";
		String regex2="(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|" +
				"(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)";
		return Pattern.matches(regex1, phone)||Pattern.matches(regex2, phone);
	}

}
