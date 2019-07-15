package com.threey.guard.base.util;

public class Constants {
	
	public static final String ADMIN_MID="123456789";
	
	
	/**
	 * 定义Session Key
	 * @author ZL
	 *
	 */
	public static interface SessionKey{
		/** 存放登录用户对象key */
		public static String LOGIN_USER = "ConsoleUser";
		/** 登录用户权限集合Key */
		public static String LOGIN_USER_PRIVILEGES = "LOGIN_USER_PRIVILEGES";
	}
	/**
	 * 后台用户类型声明
	 */
	public static interface UserType {
		//管理员类型 0超级管理，1公司管理员，2省管理员，3市管理员，4小区管理员
		/**
		 * 超级管理员
		 * */
		public final static int ROOT_MANAGER = 0;
		/** 1公司管理员*/
		public final static int COM_MASTER = 1;
		/** 2省管理员*/
		public final static int PRO_MANAGER =2;
		/** 3市管理员*/
		public final static int CITY_MANAGER =3;
		/** 4小区管理员*/
		public final static int RES_MANAGER =4;
	}

	/**
	 * @author ZL
	 *
	 */
	public static interface ManagerUser{
		/** manager_user表记录是否可用标识 0：可用 1：不可用 */
		public static String DELETE_0 = "0";
		public static String DELETE_1 = "1";
		
		/** 网站根管理员标识 0：不是 1：是*/
		public static String ROOT_USER_0 = "0";
		public static String ROOT_USER_1 = "1";
		
		/** 商家根管理员标识 0：不是 1：是*/
		public static String M_ROOT_USER_0 = "0";
		public static String M_ROOT_USER_1 = "1";
		
		/** 新增商户初始密码*/
		public static String ORIGN_M_PASSWORD = "123456";
	}
	
	/**
	 * 定义Memcache中键值常量
	 * @author ZL
	 *
	 */
	public interface Memcache{
		public static final String STOREDISHKEY = "storeDish_{0}"; // 门店点菜页面菜品信息数据缓存
		
		public static final String STOREKANJIADISHKEY = "storeKanJiaDish_{0}"; //门店点页面看家菜分类菜品信息数据缓存
		
		public static final String CKEMPTYKEY = "emptyDish_{0}";//门店沽清菜品信息数据缓存
		
		public static final String ZKMEMCACHE = "zkMemcache"; // 折扣方案缓存键值
		
		public static final String YZM = "yzm_{0}"; // 手机短信验证码
		
		public static final String AREA_RANKING="areaRanking";//区域排名
		
		public static final String LAST_MONTH_RECORD="lastMonthRecord";//上个月可提取记录
		
		public static final String NATION_RANK="nationRank";
		
		public static final String RELATION_AMOUNT="relationAmount";
		
		public static final String RELATION_DISH="relationDish";
	}

	public interface  CookieCode{
		String PC_MID = "mid";
	}
	
}
