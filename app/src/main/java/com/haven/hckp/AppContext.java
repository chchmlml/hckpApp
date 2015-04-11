package com.haven.hckp;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.haven.hckp.api.ApiClient;
import com.haven.hckp.bean.DispathList;
import com.haven.hckp.bean.NewsList;
import com.haven.hckp.bean.Notice;
import com.haven.hckp.bean.TeamList;
import com.haven.hckp.common.StringUtils;
import com.pgyersdk.crash.PgyCrashManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Properties;
import java.util.UUID;

/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据 Created by haven on 15/1/17.
 */
public class AppContext extends Application {

    public static final int PAGE_SIZE = 10;// 默认分页大小
    private static final int CACHE_TIME = 60 * 60000;// 缓存失效时间

    private Hashtable<String, Object> memCacheRegion = new Hashtable<String, Object>();

    @Override
    public void onCreate() {
        super.onCreate();
        PgyCrashManager.register(this, AppConfig.APP_ID);
        // 注册App异常崩溃处理器
        // Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler());
    }

    /**
     * 是否启动检查更新
     *
     * @return
     */
    public boolean isCheckUp() {
        String perf_checkup = getProperty(AppConfig.CONF_CHECKUP);
        // 默认是开启
        if (StringUtils.isEmpty(perf_checkup))
            return true;
        else
            return StringUtils.toBool(perf_checkup);
    }

    /**
     * 获取App唯一标识
     *
     * @return
     */
    public String getAppId() {
        String uniqueID = getProperty(AppConfig.CONF_APP_UNIQUEID);
        if (StringUtils.isEmpty(uniqueID)) {
            uniqueID = UUID.randomUUID().toString();
            setProperty(AppConfig.CONF_APP_UNIQUEID, uniqueID);
        }
        return uniqueID;
    }

    /**
     * 清除缓存目录
     *
     * @param dir 目录
     * @return
     */
    private int clearCacheFolder(File dir, long curTime) {
        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, curTime);
                    }
                    if (child.lastModified() < curTime) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }

    /**
     * 将对象保存到内存缓存中
     *
     * @param key
     * @param value
     */
    public void setMemCache(String key, Object value) {
        memCacheRegion.put(key, value);
    }

    /**
     * 从内存缓存中获取对象
     *
     * @param key
     * @return
     */
    public Object getMemCache(String key) {
        return memCacheRegion.get(key);
    }

    /**
     * 保存磁盘缓存
     *
     * @param key
     * @param value
     * @throws java.io.IOException
     */
    public void setDiskCache(String key, String value) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput("cache_" + key + ".data", Context.MODE_PRIVATE);
            fos.write(value.getBytes());
            fos.flush();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 获取磁盘缓存数据
     *
     * @param key
     * @return
     * @throws java.io.IOException
     */
    public String getDiskCache(String key) throws IOException {
        FileInputStream fis = null;
        try {
            fis = openFileInput("cache_" + key + ".data");
            byte[] datas = new byte[fis.available()];
            fis.read(datas);
            return new String(datas);
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 保存对象
     *
     * @param ser
     * @param file
     * @throws java.io.IOException
     */
    public boolean saveObject(Serializable ser, String file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = openFileOutput(file, MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            oos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                oos.close();
            } catch (Exception e) {
            }
            try {
                fos.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 读取对象
     *
     * @param file
     * @return
     * @throws java.io.IOException
     */
    public Serializable readObject(String file) {
        if (!isExistDataCache(file))
            return null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = openFileInput(file);
            ois = new ObjectInputStream(fis);
            return (Serializable) ois.readObject();
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            e.printStackTrace();
            // 反序列化失败 - 删除缓存文件
            if (e instanceof InvalidClassException) {
                File data = getFileStreamPath(file);
                data.delete();
            }
        } finally {
            try {
                ois.close();
            } catch (Exception e) {
            }
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return null;
    }

    public boolean containsProperty(String key) {
        Properties props = getProperties();
        return props.containsKey(key);
    }

    public void setProperties(Properties ps) {
        AppConfig.getAppConfig(this).set(ps);
    }

    public Properties getProperties() {
        return AppConfig.getAppConfig(this).get();
    }

    public void setProperty(String key, String value) {
        AppConfig.getAppConfig(this).set(key, value);
    }

    public String getProperty(String key) {
        return AppConfig.getAppConfig(this).get(key);
    }

    public void removeProperty(String... key) {
        AppConfig.getAppConfig(this).remove(key);
    }

    /**
     * 是否左右滑动
     *
     * @return
     */
    public boolean isScroll() {
        String perf_scroll = getProperty(AppConfig.CONF_SCROLL);
        // 默认是关闭左右滑动
        if (StringUtils.isEmpty(perf_scroll))
            return false;
        else
            return StringUtils.toBool(perf_scroll);
    }

    /**
     * 设置是否左右滑动
     *
     * @param b
     */
    public void setConfigScroll(boolean b) {
        setProperty(AppConfig.CONF_SCROLL, String.valueOf(b));
    }

    /**
     * 是否Https登录
     *
     * @return
     */
    public boolean isHttpsLogin() {
        String perf_httpslogin = getProperty(AppConfig.CONF_HTTPS_LOGIN);
        // 默认是http
        if (StringUtils.isEmpty(perf_httpslogin))
            return false;
        else
            return StringUtils.toBool(perf_httpslogin);
    }

    /**
     * 设置是是否Https登录
     *
     * @param b
     */
    public void setConfigHttpsLogin(boolean b) {
        setProperty(AppConfig.CONF_HTTPS_LOGIN, String.valueOf(b));
    }

    /**
     * 清除保存的缓存
     */
    public void cleanCookie() {
        removeProperty(AppConfig.CONF_COOKIE);
    }

    /**
     * 判断缓存数据是否可读
     *
     * @param cachefile
     * @return
     */
    private boolean isReadDataCache(String cachefile) {
        return readObject(cachefile) != null;
    }

    /**
     * 判断缓存是否存在
     *
     * @param cachefile
     * @return
     */
    private boolean isExistDataCache(String cachefile) {
        boolean exist = false;
        File data = getFileStreamPath(cachefile);
        if (data.exists())
            exist = true;
        return exist;
    }

    /**
     * 判断缓存是否失效
     *
     * @param cachefile
     * @return
     */
    public boolean isCacheDataFailure(String cachefile) {
        boolean failure = false;
        File data = getFileStreamPath(cachefile);
        if (data.exists() && (System.currentTimeMillis() - data.lastModified()) > CACHE_TIME)
            failure = true;
        else if (!data.exists())
            failure = true;
        return failure;
    }

    /**
     * 新闻列表
     */
    public NewsList getNewsList(int pageIndex, boolean isRefresh) throws AppException {
        NewsList list = null;
        String key = "orderlist_" + "_" + pageIndex + "_" + PAGE_SIZE;
        if (isNetworkConnected() && (!isReadDataCache(key) || isRefresh)) {
            //if (isNetworkConnected()) {
            try {
                list = ApiClient.getNewsList(this, pageIndex, PAGE_SIZE);
                if (list != null && pageIndex == 0) {
                    Notice notice = list.getNotice();
                    list.setNotice(null);
                    list.setCacheKey(key);
                    saveObject(list, key);
                    list.setNotice(notice);
                }
            } catch (AppException e) {
                list = (NewsList) readObject(key);
                if (list == null)
                    throw e;
            }
        } else {
            list = (NewsList) readObject(key);
            if (list == null)
                list = new NewsList();
        }
        return list;
    }

    /**
     * 车队
     */
    public TeamList getTeamList(int pageIndex, boolean isRefresh) throws AppException {
        TeamList list = null;
        String key = "teamlist_" + "_" + pageIndex + "_" + PAGE_SIZE;
        if (isNetworkConnected() && (!isReadDataCache(key) || isRefresh)) {
            //if (isNetworkConnected()) {
            try {
                list = ApiClient.getTeamsList(this, pageIndex, PAGE_SIZE);
                if (list != null && pageIndex == 0) {
                    Notice notice = list.getNotice();
                    list.setNotice(null);
                    list.setCacheKey(key);
                    saveObject(list, key);
                    list.setNotice(notice);
                }
            } catch (AppException e) {
                list = (TeamList) readObject(key);
                if (list == null)
                    throw e;
            }
        } else {
            list = (TeamList) readObject(key);
            if (list == null)
                list = new TeamList();
        }
        return list;
    }

    /**
     * 运单列表
     */
    public DispathList getDispathList(int pageIndex, boolean isRefresh) throws AppException {
        DispathList list = null;
        String key = "dispathList_" + "_" + pageIndex + "_" + PAGE_SIZE;
        if (isNetworkConnected() && (!isReadDataCache(key) || isRefresh)) {
            //if (isNetworkConnected()) {
            try {
                list = ApiClient.getDispathList(this, pageIndex, PAGE_SIZE);
                if (list != null && pageIndex == 0) {
                    Notice notice = list.getNotice();
                    list.setNotice(null);
                    list.setCacheKey(key);
                    saveObject(list, key);
                    list.setNotice(notice);
                }
            } catch (AppException e) {
                list = (DispathList) readObject(key);
                if (list == null)
                    throw e;
            }
        } else {
            list = (DispathList) readObject(key);
            if (list == null)
                list = new DispathList();
        }
        return list;
    }

    /**
     * 检测网络是否可用
     *
     * @return
     */
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    /**
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }

    /**
     * 应用程序是否发出提示音
     *
     * @return
     */
    public boolean isAppSound() {
        return isAudioNormal() && isVoice();
    }

    /**
     * 是否发出提示音
     *
     * @return
     */
    public boolean isVoice() {
        String perf_voice = getProperty(AppConfig.CONF_VOICE);
        // 默认是开启提示声音
        if (StringUtils.isEmpty(perf_voice))
            return true;
        else
            return StringUtils.toBool(perf_voice);
    }

    /**
     * 检测当前系统声音是否为正常模式
     *
     * @return
     */
    public boolean isAudioNormal() {
        AudioManager mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        return mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL;
    }
}
