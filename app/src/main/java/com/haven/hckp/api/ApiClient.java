package com.haven.hckp.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.telephony.TelephonyManager;
import com.haven.hckp.AppContext;
import com.haven.hckp.AppException;
import com.haven.hckp.bean.CarList;
import com.haven.hckp.bean.DispathList;
import com.haven.hckp.bean.NewsList;
import com.haven.hckp.bean.Result;
import com.haven.hckp.bean.TeamList;
import com.haven.hckp.bean.URLs;
import com.haven.hckp.bean.Update;
import com.haven.hckp.bean.User;
import com.haven.hckp.bean.WellcomeImage;
import com.haven.hckp.common.FileUtils;
import com.haven.hckp.common.ImageUtils;
import com.haven.hckp.common.StringUtils;
import com.lidroid.xutils.util.LogUtils;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API客户端接口：用于访问网络数据
 */
public class ApiClient {

    public static final String UTF_8 = "UTF-8";

    private final static int TIMEOUT_CONNECTION = 20000;
    private final static int TIMEOUT_SOCKET = 20000;
    private final static int RETRY_TIME = 3;

    private static String appCookie;
    private static String appUserAgent;

    //
    public static void cleanCookie() {
        //appCookie = "";
    }

    private static String getCookie(AppContext appContext) {
        if (appCookie == null || appCookie == "") {
            appCookie = appContext.getProperty("cookie");
        }
        return appCookie;
    }

    private static String getUserAgent(AppContext appContext) {
        if (appUserAgent == null || appUserAgent == "") {
            StringBuilder ua = new StringBuilder("HCKP.com");
            ua.append('/' + appContext.getPackageInfo().versionName + '_' + appContext.getPackageInfo().versionCode);//App版本
            ua.append("/Android");//手机系统平台
            ua.append("/" + android.os.Build.VERSION.RELEASE);//手机系统版本
            ua.append("/" + android.os.Build.MODEL); //手机型号
            ua.append("/" + appContext.getAppId());//客户端唯一标识
            appUserAgent = ua.toString();
        }
        return appUserAgent;
    }

    //
    private static HttpClient getHttpClient() {
        HttpClient httpClient = new HttpClient();
        // 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
        httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        // 设置 默认的超时重试处理策略
        httpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        // 设置 连接超时时间
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT_CONNECTION);
        // 设置 读数据超时时间
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIMEOUT_SOCKET);
        // 设置 字符集
        httpClient.getParams().setContentCharset(UTF_8);
        return httpClient;
    }

    private static GetMethod getHttpGet(String url, String cookie, String userAgent) {
        GetMethod httpGet = new GetMethod(url);
        // 设置 请求超时时间
        httpGet.getParams().setSoTimeout(TIMEOUT_SOCKET);
        httpGet.setRequestHeader("Host", URLs.HOST);
        httpGet.setRequestHeader("Connection", "Keep-Alive");
        httpGet.setRequestHeader("Cookie", cookie);
        httpGet.setRequestHeader("User-Agent", userAgent);
        return httpGet;
    }

    private static PostMethod getHttpPost(String url, String cookie, String userAgent) {
        PostMethod httpPost = new PostMethod(url);
        // 设置 请求超时时间
        httpPost.getParams().setSoTimeout(TIMEOUT_SOCKET);
        httpPost.setRequestHeader("Host", URLs.HOST);
        httpPost.setRequestHeader("Connection", "Keep-Alive");
        httpPost.setRequestHeader("Cookie", cookie);
        httpPost.setRequestHeader("User-Agent", userAgent);
        return httpPost;
    }

    public static String _MakeURL(String p_url, Map<String, Object> params,TelephonyManager tm) {

        //获取设备信息
//        Build bd = new Build();
//        params.put("model",bd.MODEL);
//        params.put("androidVersion", android.os.Build.VERSION.RELEASE);
//        params.put("DeviceId", tm.getDeviceId());

        StringBuilder url = new StringBuilder(p_url);
        if (url.indexOf("?") < 0)
            url.append('?');

        for (String name : params.keySet()) {
            try {
                url.append('&');
                url.append(name);
                url.append('=');
                //url.append(String.valueOf(params.get(name)));
                //不做URLEncoder处理
                url.append(URLEncoder.encode(String.valueOf(params.get(name)), UTF_8));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String newUrl = url.toString().replace("?&", "?");
        LogUtils.i("newUrl:" + newUrl);
        return newUrl;
    }

    /**
     * get请求URL
     *
     * @param url
     * @throws AppException
     */
    private static InputStream http_get(AppContext appContext, String url) throws AppException {
        String cookie = getCookie(appContext);
        String userAgent = getUserAgent(appContext);

        HttpClient httpClient = null;
        GetMethod httpGet = null;

        String responseBody = "";
        int time = 0;
        do {
            try {
                httpClient = getHttpClient();
                httpGet = getHttpGet(url, cookie, userAgent);
                int statusCode = httpClient.executeMethod(httpGet);
                if (statusCode != HttpStatus.SC_OK) {
                    throw AppException.http(statusCode);
                }
                responseBody = httpGet.getResponseBodyAsString();
                break;
            } catch (HttpException e) {
                time++;
                if (time < RETRY_TIME) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                    }
                    continue;
                }
                // 发生致命的异常，可能是协议不对或者返回的内容有问题
                e.printStackTrace();
                throw AppException.http(e);
            } catch (IOException e) {
                time++;
                if (time < RETRY_TIME) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                    }
                    continue;
                }
                // 发生网络异常
                e.printStackTrace();
                throw AppException.network(e);
            } catch (Exception e) {
                LogUtils.i(e.getMessage());
            } finally {
                // 释放连接
                httpGet.releaseConnection();
                httpClient = null;
            }
        } while (time < RETRY_TIME);

        //responseBody = responseBody.replaceAll("\\p{Cntrl}", "\r\n");
        if (responseBody.contains("result") && responseBody.contains("errorCode") && appContext.containsProperty("user.uid")) {
            try {
                Result res = Result.parse(new ByteArrayInputStream(responseBody.getBytes()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ByteArrayInputStream(responseBody.getBytes());
    }

    /**
     * 公用post方法
     *
     * @param url
     * @param params
     * @param files
     * @throws AppException
     */
    private static InputStream _post(AppContext appContext, String url, Map<String, Object> params, Map<String, File> files) throws AppException {
        //System.out.println("post_url==> "+url);
        String cookie = getCookie(appContext);
        String userAgent = getUserAgent(appContext);

        HttpClient httpClient = null;
        PostMethod httpPost = null;

        //post表单参数处理
        int length = (params == null ? 0 : params.size()) + (files == null ? 0 : files.size());
        Part[] parts = new Part[length];
        int i = 0;
        if (params != null)
            for (String name : params.keySet()) {
                parts[i++] = new StringPart(name, String.valueOf(params.get(name)), UTF_8);
                //System.out.println("post_key==> "+name+"    value==>"+String.valueOf(params.get(name)));
            }
        if (files != null)
            for (String file : files.keySet()) {
                try {
                    parts[i++] = new FilePart(file, files.get(file));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //System.out.println("post_key_file==> "+file);
            }

        String responseBody = "";
        int time = 0;
        do {
            try {
                httpClient = getHttpClient();
                httpPost = getHttpPost(url, cookie, userAgent);
                httpPost.setRequestEntity(new MultipartRequestEntity(parts, httpPost.getParams()));
                int statusCode = httpClient.executeMethod(httpPost);
                if (statusCode != HttpStatus.SC_OK) {
                    throw AppException.http(statusCode);
                } else if (statusCode == HttpStatus.SC_OK) {
                    Cookie[] cookies = httpClient.getState().getCookies();
                    String tmpcookies = "";
                    for (Cookie ck : cookies) {
                        tmpcookies += ck.toString() + ";";
                    }
                    //保存cookie
                    if (appContext != null && tmpcookies != "") {
                        appContext.setProperty("cookie", tmpcookies);
                        appCookie = tmpcookies;
                    }
                }
                responseBody = httpPost.getResponseBodyAsString();
                //System.out.println("XMLDATA=====>"+responseBody);
                break;
            } catch (HttpException e) {
                time++;
                if (time < RETRY_TIME) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                    }
                    continue;
                }
                // 发生致命的异常，可能是协议不对或者返回的内容有问题
                e.printStackTrace();
                throw AppException.http(e);
            } catch (IOException e) {
                time++;
                if (time < RETRY_TIME) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                    }
                    continue;
                }
                // 发生网络异常
                e.printStackTrace();
                throw AppException.network(e);
            } finally {
                // 释放连接
                httpPost.releaseConnection();
                httpClient = null;
            }
        } while (time < RETRY_TIME);

        responseBody = responseBody.replaceAll("\\p{Cntrl}", "");
        if (responseBody.contains("result") && responseBody.contains("errorCode") && appContext.containsProperty("user.uid")) {
            try {
                Result res = Result.parse(new ByteArrayInputStream(responseBody.getBytes()));
//                if(res.getErrorCode() == 0){
//                    //appContext.Logout();
//                    //appContext.getUnLoginHandler().sendEmptyMessage(1);
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ByteArrayInputStream(responseBody.getBytes());
    }

    /**
     * post请求URL
     *
     * @param url
     * @param params
     * @param files
     * @throws AppException
     * @throws java.io.IOException
     * @throws
     */
    private static Result http_post(AppContext appContext, String url, Map<String, Object> params, Map<String, File> files) throws AppException, IOException {
        return Result.parse(_post(appContext, url, params, files));
    }

    /**
     * 获取网络图片
     *
     * @param url
     * @return
     */
    public static Bitmap getNetBitmap(String url) throws AppException {
        HttpClient httpClient = null;
        GetMethod httpGet = null;
        Bitmap bitmap = null;
        int time = 0;
        do {
            try {
                httpClient = getHttpClient();
                httpGet = getHttpGet(url, null, null);
                int statusCode = httpClient.executeMethod(httpGet);
                if (statusCode != HttpStatus.SC_OK) {
                    throw AppException.http(statusCode);
                }
                InputStream inStream = httpGet.getResponseBodyAsStream();
                bitmap = BitmapFactory.decodeStream(inStream);
                inStream.close();
                break;
            } catch (HttpException e) {
                time++;
                if (time < RETRY_TIME) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                    }
                    continue;
                }
                // 发生致命的异常，可能是协议不对或者返回的内容有问题
                e.printStackTrace();
                throw AppException.http(e);
            } catch (IOException e) {
                time++;
                if (time < RETRY_TIME) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                    }
                    continue;
                }
                // 发生网络异常
                e.printStackTrace();
                throw AppException.network(e);
            } finally {
                // 释放连接
                httpGet.releaseConnection();
                httpClient = null;
            }
        } while (time < RETRY_TIME);
        return bitmap;
    }

    /**
     * 检查版本更新
     *
     * @return
     */
    public static Update checkVersion(AppContext appContext) throws AppException {
        try {
            return Update.parse(http_get(appContext, URLs.UPDATE_VERSION));
        } catch (Exception e) {
            if (e instanceof AppException)
                throw (AppException) e;
            throw AppException.network(e);
        }
    }

    /**
     * 检查是否有可下载的欢迎界面图片
     *
     * @param appContext
     * @return
     * @throws AppException
     */
    public static void checkBackGround(AppContext appContext) throws AppException {
        try {
            WellcomeImage update = WellcomeImage.parse(http_get(appContext, URLs.UPDATE_VERSION));
            String filePath = FileUtils.getAppCache(appContext, "welcomeback");
            // 如果没有图片的链接地址则返回
            if (StringUtils.isEmpty(update.getDownloadUrl())) {
                return;
            }
            if (update.isUpdate()) {
                String url = update.getDownloadUrl();
                String fileName = update.getStartDate().replace("-", "") + "-" + update.getEndDate().replace("-", "");
                List<File> files = FileUtils.listPathFiles(filePath);
                if (!files.isEmpty()) {
                    if (files.get(0).getName().equalsIgnoreCase(fileName)) {
                        return;
                    }
                }
                Bitmap photo = getNetBitmap(url);
                ImageUtils.saveImageToSD(appContext,
                        filePath + fileName + ".png", photo, 100);
            } else {
                FileUtils.clearFileWithPath(filePath);
            }
        } catch (Exception e) {
            if (e instanceof AppException)
                throw (AppException) e;
            throw AppException.network(e);
        }
    }

//	/**
//	 * 登录， 自动处理cookie
//	 * @param url
//	 * @param username
//	 * @param pwd
//	 * @return
//	 * @throws AppException
//	 */
//	public static User login(AppContext appContext, String username, String pwd) throws AppException {
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("username", username);
//		params.put("pwd", pwd);
//		params.put("keep_login", 1);
//
//		String loginurl = URLs.LOGIN_VALIDATE_HTTP;
//		if(appContext.isHttpsLogin()){
//			loginurl = URLs.LOGIN_VALIDATE_HTTPS;
//		}
//
//		try{
//			return User.parse(_post(appContext, loginurl, params, null));
//		}catch(Exception e){
//			if(e instanceof AppException)
//				throw (AppException)e;
//			throw AppException.network(e);
//		}
//	}
//
//	/**
//	 * 我的个人资料
//	 * @param appContext
//	 * @param uid
//	 * @return
//	 * @throws AppException
//	 */
//	public static MyInformation myInformation(AppContext appContext, int uid) throws AppException {
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("uid", uid);
//
//		try{
//			return MyInformation.parse(_post(appContext, URLs.MY_INFORMATION, params, null));
//		}catch(Exception e){
//			if(e instanceof AppException)
//				throw (AppException)e;
//			throw AppException.network(e);
//		}
//	}
//
//	/**
//	 * 更新用户头像
//	 * @param appContext
//	 * @param uid 当前用户uid
//	 * @param portrait 新上传的头像
//	 * @return
//	 * @throws AppException
//	 */
//	public static Result updatePortrait(AppContext appContext, int uid, File portrait) throws AppException {
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("uid", uid);
//
//		Map<String, File> files = new HashMap<String, File>();
//		files.put("portrait", portrait);
//
//		try{
//			return http_post(appContext, URLs.PORTRAIT_UPDATE, params, files);
//		}catch(Exception e){
//			if(e instanceof AppException)
//				throw (AppException)e;
//			throw AppException.network(e);
//		}
//	}
//
//	/**
//	 * 获取用户信息个人专页（包含该用户的动态信息以及个人信息）
//	 * @param uid 自己的uid
//	 * @param hisuid 被查看用户的uid
//	 * @param hisname 被查看用户的用户名
//	 * @param pageIndex 页面索引
//	 * @param pageSize 每页读取的动态个数
//	 * @return
//	 * @throws AppException
//	 */
//	public static UserInformation information(AppContext appContext, int uid, int hisuid, String hisname, int pageIndex, int pageSize) throws AppException {
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("uid", uid);
//		params.put("hisuid", hisuid);
//		params.put("hisname", hisname);
//		params.put("pageIndex", pageIndex);
//		params.put("pageSize", pageSize);
//
//		try{
//			return UserInformation.parse(_post(appContext, URLs.USER_INFORMATION, params, null));
//		}catch(Exception e){
//			if(e instanceof AppException)
//				throw (AppException)e;
//			throw AppException.network(e);
//		}
//	}
//
//	/**
//	 * 更新用户之间关系（加关注、取消关注）
//	 * @param uid 自己的uid
//	 * @param hisuid 对方用户的uid
//	 * @param newrelation 0:取消对他的关注 1:关注他
//	 * @return
//	 * @throws AppException
//	 */
//	public static Result updateRelation(AppContext appContext, int uid, int hisuid, int newrelation) throws AppException {
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("uid", uid);
//		params.put("hisuid", hisuid);
//		params.put("newrelation", newrelation);
//
//		try{
//			return Result.parse(_post(appContext, URLs.USER_UPDATERELATION, params, null));
//		}catch(Exception e){
//			if(e instanceof AppException)
//				throw (AppException)e;
//			throw AppException.network(e);
//		}
//	}
//
//	/**
//	 * 获取用户通知信息
//	 * @param uid
//	 * @return
//	 * @throws AppException
//	 */
//	public static Notice getUserNotice(AppContext appContext, int uid) throws AppException {
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("uid", uid);
//
//		try{
//			return Notice.parse(_post(appContext, URLs.USER_NOTICE, params, null));
//		}catch(Exception e){
//			if(e instanceof AppException)
//				throw (AppException)e;
//			throw AppException.network(e);
//		}
//	}
//
//	/**
//	 * 清空通知消息
//	 * @param uid
//	 * @param type 1:@我的信息 2:未读消息 3:评论个数 4:新粉丝个数
//	 * @return
//	 * @throws AppException
//	 */
//	public static Result noticeClear(AppContext appContext, int uid, int type) throws AppException {
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("uid", uid);
//		params.put("type", type);
//
//		try{
//			return Result.parse(_post(appContext, URLs.NOTICE_CLEAR, params, null));
//		}catch(Exception e){
//			if(e instanceof AppException)
//				throw (AppException)e;
//			throw AppException.network(e);
//		}
//	}
//
//	/**
//	 * 用户粉丝、关注人列表
//	 * @param uid
//	 * @param relation 0:显示自己的粉丝 1:显示自己的关注者
//	 * @param pageIndex
//	 * @param pageSize
//	 * @return
//	 * @throws AppException
//	 */
//	public static FriendList getFriendList(AppContext appContext, final int uid, final int relation, final int pageIndex, final int pageSize) throws AppException {
//		String newUrl = _MakeURL(URLs.FRIENDS_LIST, new HashMap<String, Object>(){{
//			put("uid", uid);
//			put("relation", relation);
//			put("pageIndex", pageIndex);
//			put("pageSize", pageSize);
//		}});
//
//		try{
//			return FriendList.parse(http_get(appContext, newUrl));
//		}catch(Exception e){
//			if(e instanceof AppException)
//				throw (AppException)e;
//			throw AppException.network(e);
//		}
//	}
//

    /**
     * 获取报价单列表
     *
     * @throws AppException
     */
    public static NewsList getNewsList(AppContext appContext, final int pageIndex, final int pageSize, Map<String, Object> params) throws AppException {
        params.put("page", pageIndex);
        params.put("len", pageSize);
        String newUrl = _MakeURL(URLs.NEWS_LIST, params,(TelephonyManager)appContext.getSystemService(Context.TELEPHONY_SERVICE));
        try {
            return NewsList.parse(http_get(appContext, newUrl));
        } catch (Exception e) {
            if (e instanceof AppException)
                throw (AppException) e;
            throw AppException.network(e);
        }
    }
    public static CarList getCarList(AppContext appContext, final int pageIndex, final int pageSize, Map<String, Object> params) throws AppException {
        params.put("page", pageIndex);
        params.put("len", pageSize);
        String newUrl = _MakeURL(URLs.CAR_LIST, params,(TelephonyManager)appContext.getSystemService(Context.TELEPHONY_SERVICE));
        try {
            return CarList.parse(http_get(appContext, newUrl));
        } catch (Exception e) {
            if (e instanceof AppException)
                throw (AppException) e;
            throw AppException.network(e);
        }
    }
//

    /**
     * 获取报价单列表
     *
     * @throws AppException
     */
    public static TeamList getTeamsList(AppContext appContext, final int pageIndex, final int pageSize) throws AppException {
        String newUrl = _MakeURL(URLs.TEAM_LIST, new HashMap<String, Object>() {{
            put("page", pageIndex);
            put("len", pageSize);
        }},(TelephonyManager)appContext.getSystemService(Context.TELEPHONY_SERVICE));
        try {
            return TeamList.parse(http_get(appContext, newUrl));
        } catch (Exception e) {
            if (e instanceof AppException)
                throw (AppException) e;
            throw AppException.network(e);
        }
    }
    public static CarList getCarList(AppContext appContext, final int pageIndex, final int pageSize) throws AppException {
        String newUrl = _MakeURL(URLs.TEAM_LIST, new HashMap<String, Object>() {{
            put("page", pageIndex);
            put("len", pageSize);
        }},(TelephonyManager)appContext.getSystemService(Context.TELEPHONY_SERVICE));
        try {
            return CarList.parse(http_get(appContext, newUrl));
        } catch (Exception e) {
            if (e instanceof AppException)
                throw (AppException) e;
            throw AppException.network(e);
        }
    }

    public static TeamList getTeamsListForsearch(AppContext appContext, final int pageIndex, final int pageSize, final String tcName) throws AppException {
        String newUrl = "";
        newUrl = _MakeURL(URLs.TEAM_LIST_SEARCH, new HashMap<String, Object>() {{
            put("tc_name", tcName);
        }},(TelephonyManager)appContext.getSystemService(Context.TELEPHONY_SERVICE));
        try {
            return TeamList.parse(http_get(appContext, newUrl));
        } catch (Exception e) {
            if (e instanceof AppException)
                throw (AppException) e;
            throw AppException.network(e);
        }
    }

    /**
     * 获取报价单列表
     *
     * @throws AppException
     */
    public static DispathList getDispathList(AppContext appContext, final int pageIndex, final int pageSize, Map<String, Object> params) throws AppException {
        params.put("page", pageIndex);
        params.put("len", pageSize);
        String newUrl = _MakeURL(URLs.DISPARH_LIST, params,(TelephonyManager)appContext.getSystemService(Context.TELEPHONY_SERVICE));
        try {
            return DispathList.parse(http_get(appContext, newUrl));
        } catch (Exception e) {
            if (e instanceof AppException)
                throw (AppException) e;
            throw AppException.network(e);
        }
    }

    /**
     * 获取报价单列表
     *
     * @throws AppException
     */
    public static User getUser(AppContext appContext) throws AppException {
        User u = new User();
        u.setUserIid(appContext.getProperty("userId"));
        u.setUserUsername(appContext.getProperty("userName"));
        u.setUserPhone(appContext.getProperty("userPhone"));
        u.setSessionId(appContext.getProperty("sessionId"));
        return u;
    }

    /**
     * 获取报价单列表
     *
     * @throws AppException
     */
    public static void logout(AppContext appContext) throws AppException {
        appContext.setProperty("userId", "");
        appContext.setProperty("userName", "");
        appContext.setProperty("userPhone", "");
        appContext.setProperty("sessionId", "");

    }


    /**
     * 二维码扫描签到
     * @param appContext
     * @param barcode
     * @return
     * @throws AppException
     */
//	public static String signIn(AppContext appContext, Barcode barcode) throws AppException {
//		try{
//			return StringUtils.toConvertString(http_get(appContext, barcode.getUrl()));
//		}catch(Exception e){
//			if(e instanceof AppException)
//				throw (AppException)e;
//			throw AppException.network(e);
//		}
//	}
}
