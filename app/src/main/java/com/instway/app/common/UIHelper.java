package com.instway.app.common;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.instway.app.AppContext;
import com.instway.app.AppManager;
import com.instway.app.R;
import com.instway.app.bean.Car;
import com.instway.app.bean.Dispath;
import com.instway.app.bean.News;
import com.instway.app.bean.Notice;
import com.instway.app.bean.Team;
import com.instway.app.ui.HomeActivity;
import com.instway.app.ui.HomeDetailActivity;
import com.instway.app.ui.HomeDispathDetailActivity;
import com.instway.app.ui.MainActivity;
import com.instway.app.ui.MyCarEditActivity;
import com.instway.app.ui.MyCarsActivity;
import com.instway.app.ui.OrderDetailActivity;
import com.instway.app.ui.OrderFilterActivity;
import com.instway.app.ui.PersonalActivity;
import com.instway.app.ui.PersonalEditActivity;
import com.instway.app.ui.RegisterActivity;
import com.instway.app.ui.SelectPictuerActivity;
import com.instway.app.ui.TeamDetailActivity;
import com.instway.app.ui.TeamFindActivity;
import com.instway.app.ui.TeamFragment;
import com.instway.app.ui.loginActivity;

import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 应用程序UI工具包：封装UI相关的一些操作
 */
public class UIHelper {
    private final static String TAG = "UIHelper";

    public final static int LISTVIEW_ACTION_INIT = 0x01;
    public final static int LISTVIEW_ACTION_REFRESH = 0x02;
    public final static int LISTVIEW_ACTION_SCROLL = 0x03;
    public final static int LISTVIEW_ACTION_CHANGE_CATALOG = 0x04;

    public final static int LISTVIEW_DATA_MORE = 0x01;
    public final static int LISTVIEW_DATA_LOADING = 0x02;
    public final static int LISTVIEW_DATA_FULL = 0x03;
    public final static int LISTVIEW_DATA_EMPTY = 0x04;

    public final static int LISTVIEW_DATATYPE_NEWS = 0x01;

    //
    // /** 表情图片匹配 */
    // private static Pattern facePattern = Pattern
    // .compile("\\[{1}([0-9]\\d*)\\]{1}");
    //
    // /** 全局web样式 */
    // // 链接样式文件，代码块高亮的处理
    // public final static String linkCss =
    // "<script type=\"text/javascript\" src=\"file:///android_asset/shCore.js\"></script>"
    // +
    // "<script type=\"text/javascript\" src=\"file:///android_asset/brush.js\"></script>"
    // +
    // "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shThemeDefault.css\">"
    // +
    // "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shCore.css\">"
    // + "<script type=\"text/javascript\">SyntaxHighlighter.all();</script>";
    // public final static String WEB_STYLE = linkCss +
    // "<style>* {font-size:14px;line-height:20px;} p {color:#333;} a {color:#3E62A6;} img {max-width:310px;} "
    // +
    // "img.alignleft {float:left;max-width:120px;margin:0 10px 5px 0;border:1px solid #ccc;background:#fff;padding:2px;} "
    // +
    // "pre {font-size:9pt;line-height:12pt;font-family:Courier New,Arial;border:1px solid #ddd;border-left:5px solid #6CE26C;background:#f6f6f6;padding:5px;overflow: auto;} "
    // +
    // "a.tag {font-size:15px;text-decoration:none;background-color:#bbd6f3;border-bottom:2px solid #3E6D8E;border-right:2px solid #7F9FB6;color:#284a7b;margin:2px 2px 2px 0;padding:2px 4px;white-space:nowrap;}</style>";
    // /**
    // * 显示首页
    // *
    // * @param activity
    // */
    // public static void showHome(Activity activity) {
    // Intent intent = new Intent(activity, Main.class);
    // activity.startActivity(intent);
    // activity.finish();
    // }
    //
    // /**
    // * 显示登录页面
    // *
    // * @param activity
    // */
    // public static void showLoginDialog(Context context) {
    // Intent intent = new Intent(context, LoginDialog.class);
    // if (context instanceof Main)
    // intent.putExtra("LOGINTYPE", LoginDialog.LOGIN_MAIN);
    // else if (context instanceof Setting)
    // intent.putExtra("LOGINTYPE", LoginDialog.LOGIN_SETTING);
    // else
    // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    // context.startActivity(intent);
    // }
    //

    //
    // /**
    // * 显示帖子详情
    // *
    // * @param context
    // * @param postId
    // */
    // public static void showQuestionDetail(Context context, int postId) {
    // Intent intent = new Intent(context, QuestionDetail.class);
    // intent.putExtra("post_id", postId);
    // context.startActivity(intent);
    // }
    //
    // /**
    // * 显示相关Tag帖子列表
    // *
    // * @param context
    // * @param tag
    // */
    // public static void showQuestionListByTag(Context context, String tag) {
    // Intent intent = new Intent(context, QuestionTag.class);
    // intent.putExtra("post_tag", tag);
    // context.startActivity(intent);
    // }
    //
    // /**
    // * 显示我要提问页面
    // *
    // * @param context
    // */
    // public static void showQuestionPub(Context context) {
    // Intent intent = new Intent(context, QuestionPub.class);
    // context.startActivity(intent);
    // }
    //
    // /**
    // * 显示动弹详情及评论
    // *
    // * @param context
    // * @param tweetId
    // */
    // public static void showTweetDetail(Context context, int tweetId) {
    // Intent intent = new Intent(context, TweetDetail.class);
    // intent.putExtra("tweet_id", tweetId);
    // context.startActivity(intent);
    // }
    //
    // /**
    // * 显示动弹一下页面
    // *
    // * @param context
    // */
    // public static void showTweetPub(Activity context) {
    // Intent intent = new Intent(context, TweetPub.class);
    // context.startActivityForResult(intent, REQUEST_CODE_FOR_RESULT);
    // }
    //
    // public static void showTweetPub(Activity context, String atme, int atuid)
    // {
    // Intent intent = new Intent(context, TweetPub.class);
    // intent.putExtra("at_me", atme);
    // intent.putExtra("at_uid", atuid);
    // context.startActivityForResult(intent, REQUEST_CODE_FOR_RESULT);
    // }
    //
    // /**
    // * 显示博客详情
    // *
    // * @param context
    // * @param blogId
    // */
    // public static void showBlogDetail(Context context, int blogId) {
    // Intent intent = new Intent(context, BlogDetail.class);
    // intent.putExtra("blog_id", blogId);
    // context.startActivity(intent);
    // }
    //
    // /**
    // * 显示软件详情
    // *
    // * @param context
    // * @param ident
    // */
    // public static void showSoftwareDetail(Context context, String ident) {
    // Intent intent = new Intent(context, SoftwareDetail.class);
    // intent.putExtra("ident", ident);
    // context.startActivity(intent);
    // }
    //


    public static void showHomeRedirect(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void showMainRedirect(Context context, int index) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("index", index);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void showNewsRedirect(Context context, News news) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("news_id", news.getTp_diy_id());
        context.startActivity(intent);
    }

    public static void showTeamDetialRedirect(Context context, Team news) {
        Intent intent = new Intent(context, TeamDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("tc_id", news.getTp_tc_id());
        intent.putExtra("tc_name", news.getTp_tc_name());
        intent.putExtra("tc_user", news.getTp_tc_user());
        intent.putExtra("tc_phone", news.getTp_tc_phone());
        intent.putExtra("tc_fax", news.getTp_tc_fax());
        intent.putExtra("tc_sj", news.getTp_tc_sj());
        intent.putExtra("tc_status", news.getTp_tc_d_status());
        context.startActivity(intent);
    }

//    public static void showCarDetailRedirect(Context context, Car car) {
//        Intent intent = new Intent(context, MyCarDetailActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Bundle b = new Bundle();
//        b.putString("car_id", StringUtils.toString(car.getTp_car_id()));
//        b.putString("car_no", StringUtils.toString(car.getTp_car_no()));
//        b.putString("car_name", StringUtils.toString(car.getTp_car_name()));
//        b.putString("car_height", StringUtils.toString(car.getTp_car_hight()));
//        b.putString("car_width", StringUtils.toString(car.getTp_car_width()));
//        b.putString("car_weight", StringUtils.toString(car.getTp_car_weight()));
//        b.putString("car_outdate", StringUtils.toString(car.getTp_car_outdate()));
//        b.putString("car_length", StringUtils.toString(car.getTp_car_length()));
//        intent.putExtras(b);
//        context.startActivity(intent);
//    }

    public static void showDispathDetailRedirect(Context context, Map<String, Object> news) {
        Intent intent = new Intent(context, HomeDispathDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle b = new Bundle();
        b.putString("tt_id", StringUtils.toString(news.get("tp_tt_id")));
//        b.putString("tp_o_sn", StringUtils.toString(news.get("tp_o_sn")));
//        b.putString("tp_o_getuser", StringUtils.toString(news.get("tp_o_getuser")));
//        b.putString("tp_o_loaddate", StringUtils.toString(news.get("tp_o_loaddate")));
//        b.putString("tp_o_reachdate", StringUtils.toString(news.get("tp_o_reachdate")));
//        b.putString("tp_og_name", StringUtils.toString(news.get("tp_og_name")));
//        b.putString("tp_og_nums", StringUtils.toString(news.get("tp_og_nums")));
//        b.putString("tp_og_goodspack", StringUtils.toString(news.get("tp_og_goodspack")));
//        b.putString("tp_tt_status", StringUtils.toString(news.get("tp_tt_status")));
//        b.putString("tp_tt_type", StringUtils.toString(news.get("tp_tt_type")));
//        b.putString("tp_di_id", StringUtils.toString(news.get("tp_di_id")));
//
//        b.putString("tp_tt_getweight", StringUtils.toString(news.get("tp_tt_getweight")));
//        b.putString("tp_tt_getnums", StringUtils.toString(news.get("tp_tt_getnums")));
//        b.putString("tp_tt_sendweight", StringUtils.toString(news.get("tp_tt_sendweight")));
//        b.putString("tp_tt_sendnums", StringUtils.toString(news.get("tp_tt_sendnums")));

        intent.putExtras(b);
        context.startActivity(intent);
    }

    /**
     * 新闻超链接点击跳转
     */
    public static void showFilterRedirect(Context context) {
        Intent intent = new Intent(context, OrderFilterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void showLoginRedirect(Context context) {
        Intent intent = new Intent(context, loginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle b = new Bundle();
        b.putString("index", "1");
        intent.putExtras(b);
        context.startActivity(intent);
    }


    public static void showMyCarAddRedirect(Context context) {
        Intent intent = new Intent(context, MyCarEditActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle b = new Bundle();
        b.putString("car_id", "");
        b.putString("car_no", "");
        b.putString("car_weight", "");
        b.putString("car_length", "");
        intent.putExtras(b);
        context.startActivity(intent);
    }

    public static void showMyEditAddRedirect(Context context, Car car) {
        Intent intent = new Intent(context, MyCarEditActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle b = new Bundle();
        b.putString("car_id", car.getTp_car_id());
        b.putString("car_no", car.getTp_car_no());
        b.putString("car_weight", car.getTp_car_weight());
        b.putString("car_length", car.getTp_car_length());
        b.putString("car_width", car.getTp_car_width());
        b.putString("car_height", car.getTp_car_hight());
        b.putString("car_drivingpic", car.getTp_car_drivingpic());
        intent.putExtras(b);
        context.startActivity(intent);
    }

    public static void showTakephotoRedirect(Context context, int type, String src) {
        Intent intent = new Intent(context, SelectPictuerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle b = new Bundle();
        b.putInt("pic_type", type);
        b.putString("src", src);
        intent.putExtras(b);
        context.startActivity(intent);
    }

    public static void callPhone(Context context, String number) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void showTakephotoRedirect2(Context context, int type, String carId, String src) {
        Intent intent = new Intent(context, SelectPictuerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle b = new Bundle();
        b.putInt("pic_type", type);
        b.putString("car_id", carId);
        b.putString("src", src);
        intent.putExtras(b);
        context.startActivity(intent);
    }

    public static void showTakephotoRedirect3(Context context, int type, String tp_tt_id, String tkType) {
        Intent intent = new Intent(context, SelectPictuerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle b = new Bundle();
        b.putInt("pic_type", type);
        b.putString("tp_tt_id", tp_tt_id);
        b.putString("tkType", tkType);
        intent.putExtras(b);
        context.startActivity(intent);
    }

    public static void showPersonalEditAddRedirect(Context context, Map<String, Object> data) {
        Intent intent = new Intent(context, PersonalEditActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle b = new Bundle();
        b.putString("username", StringUtils.toString(data.get("ts_u_username")));
        b.putString("oldPwd", StringUtils.toString(data.get("ts_u_password")));
        b.putString("phone", StringUtils.toString(data.get("ts_u_phone")));
        intent.putExtras(b);
        context.startActivity(intent);
    }

    /**
     * 发送通知广播
     *
     * @param context
     */
    public static void sendBroadCast(Context context) {
        Intent intent = new Intent("com.instway.app.action.APPWIDGET_UPDATE");
//        intent.putExtra("atmeCount", notice.getAtmeCount());
//        intent.putExtra("msgCount", notice.getMsgCount());
//        intent.putExtra("reviewCount", notice.getReviewCount());
//        intent.putExtra("newFansCount", notice.getNewFansCount());
        context.sendBroadcast(intent);
    }

    public static void showMycarsRedirect(Context context) {
        Intent intent = new Intent(context, MyCarsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void showPersonalRedirect(Context context) {
        Intent intent = new Intent(context, PersonalActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void showRegisterRedirect(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void showDispathDetailRedirect(AppContext context, Dispath dispath) {
        Intent intent = new Intent(context, HomeDetailActivity.class);
        intent.putExtra("di_id", dispath.getTp_di_id());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void showTeamfindRedirect(Context context) {
        Intent intent = new Intent(context, TeamFindActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void showTeamRedirect(Context context) {
        Intent intent = new Intent(context, TeamFragment.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    // /**
    // * 动态点击跳转到相关新闻、帖子等
    // *
    // * @param context
    // * @param id
    // * @param catalog
    // * 0其他 1新闻 2帖子 3动弹 4博客
    // */
    // public static void showActiveRedirect(Context context, Active active) {
    // String url = active.getUrl();
    // // url为空-旧方法
    // if (StringUtils.isEmpty(url)) {
    // int id = active.getObjectId();
    // int catalog = active.getActiveType();
    // switch (catalog) {
    // case Active.CATALOG_OTHER:
    // // 其他-无跳转
    // break;
    // case Active.CATALOG_NEWS:
    // showNewsDetail(context, id);
    // break;
    // case Active.CATALOG_POST:
    // showQuestionDetail(context, id);
    // break;
    // case Active.CATALOG_TWEET:
    // showTweetDetail(context, id);
    // break;
    // case Active.CATALOG_BLOG:
    // showBlogDetail(context, id);
    // break;
    // }
    // } else {
    // showUrlRedirect(context, url);
    // }
    // }
    //
    // /**
    // * 显示评论发表页面
    // *
    // * @param context
    // * @param id
    // * 新闻|帖子|动弹的id
    // * @param catalog
    // * 1新闻 2帖子 3动弹 4动态
    // */
    // public static void showCommentPub(Activity context, int id, int catalog)
    // {
    // Intent intent = new Intent(context, CommentPub.class);
    // intent.putExtra("id", id);
    // intent.putExtra("catalog", catalog);
    // context.startActivityForResult(intent, REQUEST_CODE_FOR_RESULT);
    // }
    //
    // /**
    // * 显示评论回复页面
    // *
    // * @param context
    // * @param id
    // * @param catalog
    // * @param replyid
    // * @param authorid
    // */
    // public static void showCommentReply(Activity context, int id, int
    // catalog,
    // int replyid, int authorid, String author, String content) {
    // Intent intent = new Intent(context, CommentPub.class);
    // intent.putExtra("id", id);
    // intent.putExtra("catalog", catalog);
    // intent.putExtra("reply_id", replyid);
    // intent.putExtra("author_id", authorid);
    // intent.putExtra("author", author);
    // intent.putExtra("content", content);
    // if (catalog == CommentList.CATALOG_POST)
    // context.startActivityForResult(intent, REQUEST_CODE_FOR_REPLY);
    // else
    // context.startActivityForResult(intent, REQUEST_CODE_FOR_RESULT);
    // }
    //
    // /**
    // * 显示留言对话页面
    // *
    // * @param context
    // * @param catalog
    // * @param friendid
    // */
    // public static void showMessageDetail(Context context, int friendid,
    // String friendname) {
    // Intent intent = new Intent(context, MessageDetail.class);
    // intent.putExtra("friend_name", friendname);
    // intent.putExtra("friend_id", friendid);
    // context.startActivity(intent);
    // }
    //
    // /**
    // * 显示留言回复界面
    // *
    // * @param context
    // * @param friendId
    // * 对方id
    // * @param friendName
    // * 对方名称
    // */
    // public static void showMessagePub(Activity context, int friendId,
    // String friendName) {
    // Intent intent = new Intent();
    // intent.putExtra("user_id",
    // ((AppContext) context.getApplication()).getLoginUid());
    // intent.putExtra("friend_id", friendId);
    // intent.putExtra("friend_name", friendName);
    // intent.setClass(context, MessagePub.class);
    // context.startActivityForResult(intent, REQUEST_CODE_FOR_RESULT);
    // }
    //
    // /**
    // * 显示转发留言界面
    // *
    // * @param context
    // * @param friendName
    // * 对方名称
    // * @param messageContent
    // * 留言内容
    // */
    // public static void showMessageForward(Activity context, String
    // friendName,
    // String messageContent) {
    // Intent intent = new Intent();
    // intent.putExtra("user_id",
    // ((AppContext) context.getApplication()).getLoginUid());
    // intent.putExtra("friend_name", friendName);
    // intent.putExtra("message_content", messageContent);
    // intent.setClass(context, MessageForward.class);
    // context.startActivity(intent);
    // }
    //
    // /**
    // * 调用系统安装了的应用分享
    // *
    // * @param context
    // * @param title
    // * @param url
    // */
    // public static void showShareMore(Activity context, final String title,
    // final String url) {
    // Intent intent = new Intent(Intent.ACTION_SEND);
    // intent.setType("text/plain");
    // intent.putExtra(Intent.EXTRA_SUBJECT, "分享：" + title);
    // intent.putExtra(Intent.EXTRA_TEXT, title + " " + url);
    // context.startActivity(Intent.createChooser(intent, "选择分享"));
    // }
    //
    // /**
    // * 分享到'新浪微博'或'腾讯微博'的对话框
    // *
    // * @param context
    // * 当前Activity
    // * @param title
    // * 分享的标题
    // * @param url
    // * 分享的链接
    // */
    // public static void showShareDialog(final Activity context,
    // final String title, final String url) {
    // final String spiltUrl;
    // //判断分享的链接是否包含my
    // if(url.indexOf("my")>0){
    // spiltUrl = "http://m.oschina.net/" + url.substring(url.indexOf("blog"));
    // }else{
    // spiltUrl = "http://m.oschina.net/" + url.substring(22);
    // }
    // AlertDialog.Builder builder = new AlertDialog.Builder(context);
    // builder.setIcon(android.R.drawable.btn_star);
    // builder.setTitle(context.getString(R.string.share));
    // builder.setItems(R.array.app_share_items,
    // new DialogInterface.OnClickListener() {
    // AppConfig cfgHelper = AppConfig.getAppConfig(context);
    // AccessInfo access = cfgHelper.getAccessInfo();
    //
    // public void onClick(DialogInterface arg0, int arg1) {
    // switch (arg1) {
    // case 0:// 新浪微博
    // // 分享的内容
    // final String shareMessage = title + " " + url;
    // // 初始化微博
    // if (SinaWeiboHelper.isWeiboNull()) {
    // SinaWeiboHelper.initWeibo();
    // }
    // // 判断之前是否登陆过
    // if (access != null) {
    // SinaWeiboHelper.progressDialog = new ProgressDialog(
    // context);
    // SinaWeiboHelper.progressDialog
    // .setProgressStyle(ProgressDialog.STYLE_SPINNER);
    // SinaWeiboHelper.progressDialog
    // .setMessage(context
    // .getString(R.string.sharing));
    // SinaWeiboHelper.progressDialog
    // .setCancelable(true);
    // SinaWeiboHelper.progressDialog.show();
    // new Thread() {
    // public void run() {
    // SinaWeiboHelper.setAccessToken(
    // access.getAccessToken(),
    // access.getAccessSecret(),
    // access.getExpiresIn());
    // SinaWeiboHelper.shareMessage(context,
    // shareMessage);
    // }
    // }.start();
    // } else {
    // SinaWeiboHelper
    // .authorize(context, shareMessage);
    // }
    // break;
    // case 1:// 腾讯微博
    // QQWeiboHelper.shareToQQ(context, title, url);
    // break;
    // case 2:// 微信朋友圈
    // WXFriendsHelper.shareToWXFriends(context, title, spiltUrl);
    // break;
    // case 3:// 截图分享
    // addScreenShot(context, new OnScreenShotListener() {
    //
    // @SuppressLint("NewApi")
    // public void onComplete(Bitmap bm) {
    // Intent intent = new Intent(context,ScreenShotShare.class);
    // intent.putExtra("title", title);
    // intent.putExtra("url", url);
    // intent.putExtra("cut_image_tmp_path",ScreenShotView.TEMP_SHARE_FILE_NAME);
    // try {
    // ImageUtils.saveImageToSD(context,ScreenShotView.TEMP_SHARE_FILE_NAME,bm,
    // 100);
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // context.startActivity(intent);
    // }
    // });
    // break;
    // case 4:// 更多
    // showShareMore(context, title, spiltUrl);
    // break;
    // }
    // }
    // });
    // builder.create().show();
    // }
    //
    // /**
    // * 收藏操作选择框
    // *
    // * @param context
    // * @param thread
    // */
    // public static void showFavoriteOptionDialog(final Activity context,
    // final Thread thread) {
    // AlertDialog.Builder builder = new AlertDialog.Builder(context);
    // builder.setIcon(R.drawable.ic_dialog_menu);
    // builder.setTitle(context.getString(R.string.select));
    // builder.setItems(R.array.favorite_options,
    // new DialogInterface.OnClickListener() {
    // public void onClick(DialogInterface arg0, int arg1) {
    // switch (arg1) {
    // case 0:// 删除
    // thread.start();
    // break;
    // }
    // }
    // });
    // builder.create().show();
    // }
    //
    // /**
    // * 消息列表操作选择框
    // *
    // * @param context
    // * @param msg
    // * @param thread
    // */
    // public static void showMessageListOptionDialog(final Activity context,
    // final Messages msg, final Thread thread) {
    // AlertDialog.Builder builder = new AlertDialog.Builder(context);
    // builder.setIcon(R.drawable.ic_dialog_menu);
    // builder.setTitle(context.getString(R.string.select));
    // builder.setItems(R.array.message_list_options,
    // new DialogInterface.OnClickListener() {
    // public void onClick(DialogInterface arg0, int arg1) {
    // switch (arg1) {
    // case 0:// 回复
    // showMessagePub(context, msg.getFriendId(),
    // msg.getFriendName());
    // break;
    // case 1:// 转发
    // showMessageForward(context, msg.getFriendName(),
    // msg.getContent());
    // break;
    // case 2:// 删除
    // thread.start();
    // break;
    // }
    // }
    // });
    // builder.create().show();
    // }
    //
    // /**
    // * 消息详情操作选择框
    // *
    // * @param context
    // * @param msg
    // * @param thread
    // */
    // public static void showMessageDetailOptionDialog(final Activity context,
    // final Comment msg, final Thread thread) {
    // AlertDialog.Builder builder = new AlertDialog.Builder(context);
    // builder.setIcon(R.drawable.ic_dialog_menu);
    // builder.setTitle(context.getString(R.string.select));
    // builder.setItems(R.array.message_detail_options,
    // new DialogInterface.OnClickListener() {
    // public void onClick(DialogInterface arg0, int arg1) {
    // switch (arg1) {
    // case 0:// 转发
    // showMessageForward(context, msg.getAuthor(),
    // msg.getContent());
    // break;
    // case 1:// 删除
    // thread.start();
    // break;
    // }
    // }
    // });
    // builder.create().show();
    // }
    //
    // /**
    // * 评论操作选择框
    // *
    // * @param context
    // * @param id
    // * 某条新闻，帖子，动弹的id 或者某条消息的 friendid
    // * @param catalog
    // * 该评论所属类型：1新闻 2帖子 3动弹 4动态
    // * @param comment
    // * 本条评论对象，用于获取评论id&评论者authorid
    // * @param thread
    // * 处理删除评论的线程，若无删除操作传null
    // */
    // public static void showCommentOptionDialog(final Activity context,
    // final int id, final int catalog, final Comment comment,
    // final Thread thread) {
    // AlertDialog.Builder builder = new AlertDialog.Builder(context);
    // builder.setIcon(R.drawable.ic_dialog_menu);
    // builder.setTitle(context.getString(R.string.select));
    // if (thread != null) {
    // builder.setItems(R.array.comment_options_2,
    // new DialogInterface.OnClickListener() {
    // public void onClick(DialogInterface arg0, int arg1) {
    // switch (arg1) {
    // case 0:// 回复
    // showCommentReply(context, id, catalog,
    // comment.getId(), comment.getAuthorId(),
    // comment.getAuthor(),
    // comment.getContent());
    // break;
    // case 1:// 删除
    // thread.start();
    // break;
    // }
    // }
    // });
    // } else {
    // builder.setItems(R.array.comment_options_1,
    // new DialogInterface.OnClickListener() {
    // public void onClick(DialogInterface arg0, int arg1) {
    // switch (arg1) {
    // case 0:// 回复
    // showCommentReply(context, id, catalog,
    // comment.getId(), comment.getAuthorId(),
    // comment.getAuthor(),
    // comment.getContent());
    // break;
    // }
    // }
    // });
    // }
    // builder.create().show();
    // }
    //
    // /**
    // * 博客列表操作
    // *
    // * @param context
    // * @param thread
    // */
    // public static void showBlogOptionDialog(final Context context,
    // final Thread thread) {
    // new AlertDialog.Builder(context)
    // .setIcon(android.R.drawable.ic_dialog_info)
    // .setTitle(context.getString(R.string.delete_blog))
    // .setPositiveButton(R.string.sure,
    // new DialogInterface.OnClickListener() {
    // public void onClick(DialogInterface dialog,
    // int which) {
    // if (thread != null)
    // thread.start();
    // else
    // ToastMessage(context,
    // R.string.msg_noaccess_delete);
    // dialog.dismiss();
    // }
    // })
    // .setNegativeButton(R.string.cancle,
    // new DialogInterface.OnClickListener() {
    // public void onClick(DialogInterface dialog,
    // int which) {
    // dialog.dismiss();
    // }
    // }).create().show();
    // }
    //
    // /**
    // * 动弹操作选择框
    // *
    // * @param context
    // * @param thread
    // */
    // public static void showTweetOptionDialog(final Context context,
    // final Thread thread) {
    // new AlertDialog.Builder(context)
    // .setIcon(android.R.drawable.ic_dialog_info)
    // .setTitle(context.getString(R.string.delete_tweet))
    // .setPositiveButton(R.string.sure,
    // new DialogInterface.OnClickListener() {
    // public void onClick(DialogInterface dialog,
    // int which) {
    // if (thread != null)
    // thread.start();
    // else
    // ToastMessage(context,
    // R.string.msg_noaccess_delete);
    // dialog.dismiss();
    // }
    // })
    // .setNegativeButton(R.string.cancle,
    // new DialogInterface.OnClickListener() {
    // public void onClick(DialogInterface dialog,
    // int which) {
    // dialog.dismiss();
    // }
    // }).create().show();
    // }
    //
    // /**
    // * 是否重新发布动弹操对话框
    // *
    // * @param context
    // * @param thread
    // */
    // public static void showResendTweetDialog(final Context context,
    // final Thread thread) {
    // new AlertDialog.Builder(context)
    // .setIcon(android.R.drawable.ic_dialog_info)
    // .setTitle(context.getString(R.string.republish_tweet))
    // .setPositiveButton(R.string.sure,
    // new DialogInterface.OnClickListener() {
    // public void onClick(DialogInterface dialog,
    // int which) {
    // dialog.dismiss();
    // if (context == TweetPub.mContext
    // && TweetPub.mMessage != null)
    // TweetPub.mMessage
    // .setVisibility(View.VISIBLE);
    // thread.start();
    // }
    // })
    // .setNegativeButton(R.string.cancle,
    // new DialogInterface.OnClickListener() {
    // public void onClick(DialogInterface dialog,
    // int which) {
    // dialog.dismiss();
    // }
    // }).create().show();
    // }
    //
    // /**
    // * 显示图片对话框
    // *
    // * @param context
    // * @param imgUrl
    // */
    // public static void showImageDialog(Context context, String imgUrl) {
    // Intent intent = new Intent(context, ImageDialog.class);
    // intent.putExtra("img_url", imgUrl);
    // context.startActivity(intent);
    // }
    //
    // public static void showImageZoomDialog(Context context, String imgUrl) {
    // Intent intent = new Intent(context, ImageZoomDialog.class);
    // intent.putExtra("img_url", imgUrl);
    // context.startActivity(intent);
    // }
    //
    // /**
    // * 显示系统设置界面
    // *
    // * @param context
    // */
    // public static void showSetting(Context context) {
    // Intent intent = new Intent(context, Setting.class);
    // context.startActivity(intent);
    // }
    //
    // /**
    // * 显示搜索界面
    // *
    // * @param context
    // */
    // public static void showSearch(Context context) {
    // Intent intent = new Intent(context, Search.class);
    // context.startActivity(intent);
    // }
    //
    // /**
    // * 显示扫一扫界面
    // * @param context
    // */
    // public static void showCapture(Context context) {
    // Intent intent = new Intent(context, CaptureActivity.class);
    // context.startActivity(intent);
    // }
    //
    // /**
    // * 显示软件界面
    // *
    // * @param context
    // */
    // public static void showSoftware(Context context) {
    // Intent intent = new Intent(context, SoftwareLib.class);
    // context.startActivity(intent);
    // }
    //
    // /**
    // * 显示我的资料
    // *
    // * @param context
    // */
    // public static void showUserInfo(Activity context) {
    // AppContext ac = (AppContext) context.getApplicationContext();
    // if (!ac.isLogin()) {
    // showLoginDialog(context);
    // } else {
    // Intent intent = new Intent(context, UserInfo.class);
    // context.startActivity(intent);
    // }
    // }
    //
    // /**
    // * 显示路径选择对话框
    // *
    // * @param context
    // */
    // public static void showFilePathDialog(Activity context,
    // ChooseCompleteListener listener) {
    // new PathChooseDialog(context, listener).show();
    // }
    //
    // /**
    // * 显示用户动态
    // *
    // * @param context
    // * @param uid
    // * @param hisuid
    // * @param hisname
    // */
    // public static void showUserCenter(Context context, int hisuid,
    // String hisname) {
    // Intent intent = new Intent(context, UserCenter.class);
    // intent.putExtra("his_id", hisuid);
    // intent.putExtra("his_name", hisname);
    // context.startActivity(intent);
    // }
    //
    // /**
    // * 显示用户收藏夹
    // *
    // * @param context
    // */
    // public static void showUserFavorite(Context context) {
    // Intent intent = new Intent(context, UserFavorite.class);
    // context.startActivity(intent);
    // }
    //
    // /**
    // * 显示用户好友
    // *
    // * @param context
    // */
    // public static void showUserFriend(Context context, int friendType,
    // int followers, int fans) {
    // Intent intent = new Intent(context, UserFriend.class);
    // intent.putExtra("friend_type", friendType);
    // intent.putExtra("friend_followers", followers);
    // intent.putExtra("friend_fans", fans);
    // context.startActivity(intent);
    // }
    //
    // /**
    // * 加载显示用户头像
    // *
    // * @param imgFace
    // * @param faceURL
    // */
    // public static void showUserFace(final ImageView imgFace,
    // final String faceURL) {
    // showLoadImage(imgFace, faceURL,
    // imgFace.getContext().getString(R.string.msg_load_userface_fail));
    // }
    //
    // /**
    // * 加载显示图片
    // *
    // * @param imgFace
    // * @param faceURL
    // * @param errMsg
    // */
    // public static void showLoadImage(final ImageView imgView,
    // final String imgURL, final String errMsg) {
    // // 读取本地图片
    // if (StringUtils.isEmpty(imgURL) || imgURL.endsWith("portrait.gif")) {
    // Bitmap bmp = BitmapFactory.decodeResource(imgView.getResources(),
    // R.drawable.widget_dface);
    // imgView.setImageBitmap(bmp);
    // return;
    // }
    //
    // // 是否有缓存图片
    // final String filename = FileUtils.getFileName(imgURL);
    // // Environment.getExternalStorageDirectory();返回/sdcard
    // String filepath = imgView.getContext().getFilesDir() + File.separator
    // + filename;
    // File file = new File(filepath);
    // if (file.exists()) {
    // Bitmap bmp = ImageUtils.getBitmap(imgView.getContext(), filename);
    // imgView.setImageBitmap(bmp);
    // return;
    // }
    //
    // // 从网络获取&写入图片缓存
    // String _errMsg = imgView.getContext().getString(
    // R.string.msg_load_image_fail);
    // if (!StringUtils.isEmpty(errMsg))
    // _errMsg = errMsg;
    // final String ErrMsg = _errMsg;
    // final Handler handler = new Handler() {
    // public void handleMessage(Message msg) {
    // if (msg.what == 1 && msg.obj != null) {
    // imgView.setImageBitmap((Bitmap) msg.obj);
    // try {
    // // 写图片缓存
    // ImageUtils.saveImage(imgView.getContext(), filename,
    // (Bitmap) msg.obj);
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // } else {
    // ToastMessage(imgView.getContext(), ErrMsg);
    // }
    // }
    // };
    // new Thread() {
    // public void run() {
    // Message msg = new Message();
    // try {
    // Bitmap bmp = ApiClient.getNetBitmap(imgURL);
    // msg.what = 1;
    // msg.obj = bmp;
    // } catch (AppException e) {
    // e.printStackTrace();
    // msg.what = -1;
    // msg.obj = e;
    // }
    // handler.sendMessage(msg);
    // }
    // }.start();
    // }
    //
    // /**
    // * url跳转
    // *
    // * @param context
    // * @param url
    // */
    // public static void showUrlRedirect(Context context, String url) {
    // URLs urls = URLs.parseURL(url);
    // if (urls != null) {
    // showLinkRedirect(context, urls.getObjType(), urls.getObjId(),
    // urls.getObjKey());
    // } else {
    // openBrowser(context, url);
    // }
    // }
    //
    // public static void showLinkRedirect(Context context, int objType,
    // int objId, String objKey) {
    // switch (objType) {
    // case URLs.URL_OBJ_TYPE_NEWS:
    // showNewsDetail(context, objId);
    // break;
    // case URLs.URL_OBJ_TYPE_QUESTION:
    // showQuestionDetail(context, objId);
    // break;
    // case URLs.URL_OBJ_TYPE_QUESTION_TAG:
    // showQuestionListByTag(context, objKey);
    // break;
    // case URLs.URL_OBJ_TYPE_SOFTWARE:
    // showSoftwareDetail(context, objKey);
    // break;
    // case URLs.URL_OBJ_TYPE_ZONE:
    // showUserCenter(context, objId, objKey);
    // break;
    // case URLs.URL_OBJ_TYPE_TWEET:
    // showTweetDetail(context, objId);
    // break;
    // case URLs.URL_OBJ_TYPE_BLOG:
    // showBlogDetail(context, objId);
    // break;
    // case URLs.URL_OBJ_TYPE_OTHER:
    // openBrowser(context, objKey);
    // break;
    // }
    // }
    //
    // /**
    // * 打开浏览器
    // *
    // * @param context
    // * @param url
    // */
    // public static void openBrowser(Context context, String url) {
    // try {
    // Uri uri = Uri.parse(url);
    // Intent it = new Intent(Intent.ACTION_VIEW, uri);
    // context.startActivity(it);
    // } catch (Exception e) {
    // e.printStackTrace();
    // ToastMessage(context, "无法浏览此网页", 500);
    // }
    // }
    //
    // /**
    // * 获取webviewClient对象
    // *
    // * @return
    // */
    // public static WebViewClient getWebViewClient() {
    // return new WebViewClient() {
    // @Override
    // public boolean shouldOverrideUrlLoading(WebView view, String url) {
    // showUrlRedirect(view.getContext(), url);
    // return true;
    // }
    // };
    // }
    //
    // /**
    // * 获取TextWatcher对象
    // *
    // * @param context
    // * @param tmlKey
    // * @return
    // */
    // public static TextWatcher getTextWatcher(final Activity context,
    // final String temlKey) {
    // return new TextWatcher() {
    // public void onTextChanged(CharSequence s, int start, int before,
    // int count) {
    // // 保存当前EditText正在编辑的内容
    // ((AppContext) context.getApplication()).setProperty(temlKey,
    // s.toString());
    // }
    //
    // public void beforeTextChanged(CharSequence s, int start, int count,
    // int after) {
    // }
    //
    // public void afterTextChanged(Editable s) {
    // }
    // };
    // }
    //
    // /**
    // * 编辑器显示保存的草稿
    // *
    // * @param context
    // * @param editer
    // * @param temlKey
    // */
    // public static void showTempEditContent(Activity context, EditText editer,
    // String temlKey) {
    // String tempContent = ((AppContext) context.getApplication())
    // .getProperty(temlKey);
    // if (!StringUtils.isEmpty(tempContent)) {
    // SpannableStringBuilder builder = parseFaceByText(context,
    // tempContent);
    // editer.setText(builder);
    // editer.setSelection(tempContent.length());// 设置光标位置
    // }
    // }
    //
    // /**
    // * 将[12]之类的字符串替换为表情
    // *
    // * @param context
    // * @param content
    // */
    // public static SpannableStringBuilder parseFaceByText(Context context,
    // String content) {
    // SpannableStringBuilder builder = new SpannableStringBuilder(content);
    // Matcher matcher = facePattern.matcher(content);
    // while (matcher.find()) {
    // // 使用正则表达式找出其中的数字
    // int position = StringUtils.toInt(matcher.group(1));
    // int resId = 0;
    // try {
    // if (position > 65 && position < 102)
    // position = position - 1;
    // else if (position > 102)
    // position = position - 2;
    // resId = GridViewFaceAdapter.getImageIds()[position];
    // Drawable d = context.getResources().getDrawable(resId);
    // d.setBounds(0, 0, 35, 35);// 设置表情图片的显示大小
    // ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);
    // builder.setSpan(span, matcher.start(), matcher.end(),
    // Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    // } catch (Exception e) {
    // }
    // }
    // return builder;
    // }
    //
    // /**
    // * 清除文字
    // *
    // * @param cont
    // * @param editer
    // */
    // public static void showClearWordsDialog(final Context cont,
    // final EditText editer, final TextView numwords) {
    // AlertDialog.Builder builder = new AlertDialog.Builder(cont);
    // builder.setTitle(R.string.clearwords);
    // builder.setPositiveButton(R.string.sure,
    // new DialogInterface.OnClickListener() {
    // public void onClick(DialogInterface dialog, int which) {
    // dialog.dismiss();
    // // 清除文字
    // editer.setText("");
    // numwords.setText("160");
    // }
    // });
    // builder.setNegativeButton(R.string.cancle,
    // new DialogInterface.OnClickListener() {
    // public void onClick(DialogInterface dialog, int which) {
    // dialog.dismiss();
    // }
    // });
    // builder.show();
    // }
    //

    /**
     * 发送通知广播
     *
     * @param context
     * @param notice
     */
    public static void sendBroadCast(Context context, Notice notice) {
//		if (!((AppContext) context.getApplicationContext()).isLogin() || notice == null)
//			return;
        Intent intent = new Intent("net.oschina.app.action.APPWIDGET_UPDATE");
        intent.putExtra("atmeCount", notice.getAtmeCount());
        intent.putExtra("msgCount", notice.getMsgCount());
        intent.putExtra("reviewCount", notice.getReviewCount());
        intent.putExtra("newFansCount", notice.getNewFansCount());
        context.sendBroadcast(intent);
    }

    //
    // /**
    // * 发送广播-发布动弹
    // *
    // * @param context
    // * @param notice
    // */
    // public static void sendBroadCastTweet(Context context, int what,
    // Result res, Tweet tweet) {
    // if (res == null && tweet == null)
    // return;
    // Intent intent = new Intent("net.oschina.app.action.APP_TWEETPUB");
    // intent.putExtra("MSG_WHAT", what);
    // if (what == 1)
    // intent.putExtra("RESULT", res);
    // else
    // intent.putExtra("TWEET", tweet);
    // context.sendBroadcast(intent);
    // }
    //
    // /**
    // * 组合动态的动作文本
    // *
    // * @param objecttype
    // * @param objectcatalog
    // * @param objecttitle
    // * @return
    // */
    // @SuppressLint("NewApi")
    // public static SpannableString parseActiveAction(String author,
    // int objecttype, int objectcatalog, String objecttitle) {
    // String title = "";
    // int start = 0;
    // int end = 0;
    // if (objecttype == 32 && objectcatalog == 0) {
    // title = "加入了开源中国";
    // } else if (objecttype == 1 && objectcatalog == 0) {
    // title = "添加了开源项目 " + objecttitle;
    // } else if (objecttype == 2 && objectcatalog == 1) {
    // title = "在讨论区提问：" + objecttitle;
    // } else if (objecttype == 2 && objectcatalog == 2) {
    // title = "发表了新话题：" + objecttitle;
    // } else if (objecttype == 3 && objectcatalog == 0) {
    // title = "发表了博客 " + objecttitle;
    // } else if (objecttype == 4 && objectcatalog == 0) {
    // title = "发表一篇新闻 " + objecttitle;
    // } else if (objecttype == 5 && objectcatalog == 0) {
    // title = "分享了一段代码 " + objecttitle;
    // } else if (objecttype == 6 && objectcatalog == 0) {
    // title = "发布了一个职位：" + objecttitle;
    // } else if (objecttype == 16 && objectcatalog == 0) {
    // title = "在新闻 " + objecttitle + " 发表评论";
    // } else if (objecttype == 17 && objectcatalog == 1) {
    // title = "回答了问题：" + objecttitle;
    // } else if (objecttype == 17 && objectcatalog == 2) {
    // title = "回复了话题：" + objecttitle;
    // } else if (objecttype == 17 && objectcatalog == 3) {
    // title = "在 " + objecttitle + " 对回帖发表评论";
    // } else if (objecttype == 18 && objectcatalog == 0) {
    // title = "在博客 " + objecttitle + " 发表评论";
    // } else if (objecttype == 19 && objectcatalog == 0) {
    // title = "在代码 " + objecttitle + " 发表评论";
    // } else if (objecttype == 20 && objectcatalog == 0) {
    // title = "在职位 " + objecttitle + " 发表评论";
    // } else if (objecttype == 101 && objectcatalog == 0) {
    // title = "回复了动态：" + objecttitle;
    // } else if (objecttype == 100) {
    // title = "更新了动态";
    // }
    // title = author + " " + title;
    // SpannableString sp = new SpannableString(title);
    // // 设置用户名字体大小、加粗、高亮
    // sp.setSpan(new AbsoluteSizeSpan(14, true), 0, author.length(),
    // Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    // sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,
    // author.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    // sp.setSpan(new ForegroundColorSpan(Color.parseColor("#0e5986")), 0,
    // author.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    // // 设置标题字体大小、高亮
    // if (!StringUtils.isEmpty(objecttitle)) {
    // start = title.indexOf(objecttitle);
    // if (objecttitle.length() > 0 && start > 0) {
    // end = start + objecttitle.length();
    // sp.setSpan(new AbsoluteSizeSpan(14, true), start, end,
    // Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    // sp.setSpan(
    // new ForegroundColorSpan(Color.parseColor("#0e5986")),
    // start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    // }
    // }
    // return sp;
    // }
    //
    // /**
    // * 组合动态的回复文本
    // *
    // * @param name
    // * @param body
    // * @return
    // */
    // public static SpannableString parseActiveReply(String name, String body)
    // {
    // SpannableString sp = new SpannableString(name + "：" + body);
    // // 设置用户名字体加粗、高亮
    // sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,
    // name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    // sp.setSpan(new ForegroundColorSpan(Color.parseColor("#0e5986")), 0,
    // name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    // return sp;
    // }
    //
    // /**
    // * 组合消息文本
    // *
    // * @param name
    // * @param body
    // * @return
    // */
    // public static void parseMessageSpan(LinkView view, String name,
    // String body, String action) {
    // Spanned span = null;
    // SpannableStringBuilder style = null;
    // int start = 0;
    // int end = 0;
    // String content = null;
    // if (StringUtils.isEmpty(action)) {
    // content = name + "：" + body;
    // span = Html.fromHtml(content);
    // view.setText(span);
    // end = name.length();
    // } else {
    // content = action + name + "：" + body;
    // span = Html.fromHtml(content);
    // view.setText(span);
    // start = action.length();
    // end = start + name.length();
    // }
    // view.setMovementMethod(LinkMovementMethod.getInstance());
    //
    // Spannable sp = (Spannable) view.getText();
    // URLSpan[] urls = span.getSpans(0, sp.length(), URLSpan.class);
    //
    // style = new SpannableStringBuilder(view.getText());
    // // style.clearSpans();// 这里会清除之前所有的样式
    // for (URLSpan url : urls) {
    // style.removeSpan(url);// 只需要移除之前的URL样式，再重新设置
    // MyURLSpan myURLSpan = view.new MyURLSpan(url.getURL());
    // style.setSpan(myURLSpan, span.getSpanStart(url),
    // span.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    // }
    //
    // // 设置用户名字体加粗、高亮
    // style.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), start,
    // end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    // style.setSpan(new ForegroundColorSpan(Color.parseColor("#0e5986")),
    // start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    // view.setText(style);
    // }
    //
    // /**
    // * 组合回复引用文本
    // *
    // * @param name
    // * @param body
    // * @return
    // */
    // public static SpannableString parseQuoteSpan(String name, String body) {
    // SpannableString sp = new SpannableString("回复：" + name + "\n" + body);
    // // 设置用户名字体加粗、高亮
    // sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 3,
    // 3 + name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    // sp.setSpan(new ForegroundColorSpan(Color.parseColor("#0e5986")), 3,
    // 3 + name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    // return sp;
    // }
    //

    /**
     * 弹出Toast消息
     *
     * @param msg
     */
    public static void ToastMessage(Context cont, String msg) {
        Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
    }

    public static void ToastMessage(Context cont, int msg) {
        Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
    }

    public static void ToastMessage(Context cont, String msg, int time) {
        Toast.makeText(cont, msg, time).show();
    }

    public static void SendNotify(Context appContext) {
        // 获得通知管理器
//		NotificationManager manager = (NotificationManager) appContext.getSystemService(appContext.NOTIFICATION_SERVICE);
//		// 构建一个通知对象(需要传递的参数有三个,分别是图标,标题和 时间)
//		Notification notification = new Notification(R.drawable.ic_launcher, "通知", System.currentTimeMillis());
//		Intent intent = new Intent(MainActivity.this, MainActivity.class);
//		PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
//		notification.setLatestEventInfo(getApplicationContext(), "通知标题", "通知显示的内容", pendingIntent);
//		notification.flags = Notification.FLAG_AUTO_CANCEL;// 点击后自动消失
//		notification.defaults = Notification.DEFAULT_SOUND;// 声音默认
//		manager.notify(id, notification);// 发动通知,id由自己指定，每一个Notification对应的唯一标志
        // 其实这里的id没有必要设置,只是为了下面要用到它才进行了设置
    }

    //
    // /**
    // * 点击返回监听事件
    // *
    // * @param activity
    // * @return
    // */
    // public static View.OnClickListener finish(final Activity activity) {
    // return new View.OnClickListener() {
    // public void onClick(View v) {
    // activity.finish();
    // }
    // };
    // }
    //
    // /**
    // * 显示关于我们
    // *
    // * @param context
    // */
    // public static void showAbout(Context context) {
    // Intent intent = new Intent(context, About.class);
    // context.startActivity(intent);
    // }
    //
    // /**
    // * 显示用户反馈
    // *
    // * @param context
    // */
    // public static void showFeedBack(Context context) {
    // Intent intent = new Intent(context, FeedBack.class);
    // context.startActivity(intent);
    // }
    //
    // /**
    // * 显示用户举报
    // * @param context
    // * @param link
    // */
    // public static void showReport(Context context, String link, int obj_id) {
    // Intent intent = new Intent(context, ReportUi.class);
    // intent.putExtra(Report.REPORT_LINK, link);
    // intent.putExtra(Report.REPORT_ID, obj_id);
    // context.startActivity(intent);
    // }
    //
    // /**
    // * 菜单显示登录或登出
    // *
    // * @param activity
    // * @param menu
    // */
    // public static void showMenuLoginOrLogout(Activity activity, Menu menu) {
    // if (((AppContext) activity.getApplication()).isLogin()) {
    // menu.findItem(R.id.main_menu_user).setTitle(
    // R.string.main_menu_logout);
    // menu.findItem(R.id.main_menu_user).setIcon(
    // R.drawable.ic_menu_logout);
    // } else {
    // menu.findItem(R.id.main_menu_user).setTitle(
    // R.string.main_menu_login);
    // menu.findItem(R.id.main_menu_user)
    // .setIcon(R.drawable.ic_menu_login);
    // }
    // }
    //
    // /**
    // * 快捷栏显示登录与登出
    // *
    // * @param activity
    // * @param qa
    // */
    // public static void showSettingLoginOrLogout(Activity activity,
    // QuickAction qa) {
    // if (((AppContext) activity.getApplication()).isLogin()) {
    // qa.setIcon(MyQuickAction.buildDrawable(activity,
    // R.drawable.ic_menu_logout));
    // qa.setTitle(activity.getString(R.string.main_menu_logout));
    // } else {
    // qa.setIcon(MyQuickAction.buildDrawable(activity,
    // R.drawable.ic_menu_login));
    // qa.setTitle(activity.getString(R.string.main_menu_login));
    // }
    // }
    //
    // /**
    // * 快捷栏是否显示文章图片
    // *
    // * @param activity
    // * @param qa
    // */
    // public static void showSettingIsLoadImage(Activity activity, QuickAction
    // qa) {
    // if (((AppContext) activity.getApplication()).isLoadImage()) {
    // qa.setIcon(MyQuickAction.buildDrawable(activity,
    // R.drawable.ic_menu_picnoshow));
    // qa.setTitle(activity.getString(R.string.main_menu_picnoshow));
    // } else {
    // qa.setIcon(MyQuickAction.buildDrawable(activity,
    // R.drawable.ic_menu_picshow));
    // qa.setTitle(activity.getString(R.string.main_menu_picshow));
    // }
    // }
    //
    // /**
    // * 用户登录或注销
    // *
    // * @param activity
    // */
    // public static void loginOrLogout(Activity activity) {
    // AppContext ac = (AppContext) activity.getApplication();
    // if (ac.isLogin()) {
    // ac.Logout();
    // ToastMessage(activity, "已退出登录");
    // } else {
    // showLoginDialog(activity);
    // }
    // }
    //
    // /**
    // * 文章是否加载图片显示
    // *
    // * @param activity
    // */
    // public static void changeSettingIsLoadImage(Activity activity) {
    // AppContext ac = (AppContext) activity.getApplication();
    // if (ac.isLoadImage()) {
    // ac.setConfigLoadimage(false);
    // ToastMessage(activity, "已设置文章不加载图片");
    // } else {
    // ac.setConfigLoadimage(true);
    // ToastMessage(activity, "已设置文章加载图片");
    // }
    // }
    //
    // public static void changeSettingIsLoadImage(Activity activity, boolean b)
    // {
    // AppContext ac = (AppContext) activity.getApplication();
    // ac.setConfigLoadimage(b);
    // }
    //
    // /**
    // * 清除app缓存
    // *
    // * @param activity
    // */
    // public static void clearAppCache(Activity activity) {
    // final AppContext ac = (AppContext) activity.getApplication();
    // final Handler handler = new Handler() {
    // public void handleMessage(Message msg) {
    // if (msg.what == 1) {
    // ToastMessage(ac, "缓存清除成功");
    // } else {
    // ToastMessage(ac, "缓存清除失败");
    // }
    // }
    // };
    // new Thread() {
    // public void run() {
    // Message msg = new Message();
    // try {
    // ac.clearAppCache();
    // msg.what = 1;
    // } catch (Exception e) {
    // e.printStackTrace();
    // msg.what = -1;
    // }
    // handler.sendMessage(msg);
    // }
    // }.start();
    // }
    //

    /**
     * 发送App异常崩溃报告
     *
     * @param cont
     * @param crashReport
     */
    public static void sendAppCrashReport(final Context cont, final String crashReport) {
        Resources resource = cont.getResources();
        new SweetAlertDialog(cont, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(resource.getString(R.string.app_error))
                .setContentText(resource.getString(R.string.app_error_message))
                .setConfirmText("确定")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        //todo 发送错误报告
                        // 退出
                        AppManager.getAppManager().AppExit(cont);
                        System.exit(0);
                    }
                })
                .showCancelButton(true)
                .setCancelText("取消")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        // 退出
                        AppManager.getAppManager().AppExit(cont);
                        System.exit(0);
                    }
                })
                .show();
    }

    public static Drawable getIconByStatus(Context context, int status) {
        Drawable dDrawable = null;
        switch (status) {
            case 1:
            case 2:
                dDrawable = context.getResources().getDrawable(R.drawable.trans_status_3);
                break;
            case 3:
                dDrawable = context.getResources().getDrawable(R.drawable.trans_status_1);
                break;
            case 4:
                dDrawable = context.getResources().getDrawable(R.drawable.trans_status_0);
                break;
            case 5:
            case 6:
            case 7:
                dDrawable = context.getResources().getDrawable(R.drawable.trans_status_2);
                break;
        }
        return dDrawable;
    }
    //
    // /**
    // * 退出程序
    // *
    // * @param cont
    // */
    // public static void Exit(final Context cont) {
    // AlertDialog.Builder builder = new AlertDialog.Builder(cont);
    // builder.setIcon(android.R.drawable.ic_dialog_info);
    // builder.setTitle(R.string.app_menu_surelogout);
    // builder.setPositiveButton(R.string.sure,
    // new DialogInterface.OnClickListener() {
    // public void onClick(DialogInterface dialog, int which) {
    // dialog.dismiss();
    // // 退出
    // AppManager.getAppManager().AppExit(cont);
    // }
    // });
    // builder.setNegativeButton(R.string.cancle,
    // new DialogInterface.OnClickListener() {
    // public void onClick(DialogInterface dialog, int which) {
    // dialog.dismiss();
    // }
    // });
    // builder.show();
    // }
    //
    // /**
    // * 添加截屏功能
    // */
    // @SuppressLint("NewApi")
    // public static void addScreenShot(Activity context,
    // OnScreenShotListener mScreenShotListener) {
    // BaseActivity cxt = null;
    // if (context instanceof BaseActivity) {
    // cxt = (BaseActivity) context;
    // cxt.setAllowFullScreen(false);
    // ScreenShotView screenShot = new ScreenShotView(cxt,
    // mScreenShotListener);
    // LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
    // LayoutParams.MATCH_PARENT);
    // context.getWindow().addContentView(screenShot, lp);
    // }
    // }
    //
    // /**
    // * 添加网页的点击图片展示支持
    // */
    // @SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
    // public static void addWebImageShow(final Context cxt, WebView wv) {
    // wv.getSettings().setJavaScriptEnabled(true);
    // wv.addJavascriptInterface(new OnWebViewImageListener() {
    //
    // @Override
    // public void onImageClick(String bigImageUrl) {
    // if (bigImageUrl != null)
    // UIHelper.showImageZoomDialog(cxt, bigImageUrl);
    // }
    // }, "mWebViewImageListener");
    // }
    //
    // @SuppressWarnings("deprecation")
    // public static void showQuestionOption(final Activity context, View aim,
    // final Post postDetail) {
    // LayoutInflater inflater = (LayoutInflater) context
    // .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    // final View mMenuView = inflater.inflate(R.layout.widget_bar_option_menu,
    // null);
    // int w = context.getWindowManager().getDefaultDisplay().getWidth();
    // final PopupWindow option = new PopupWindow(mMenuView, w/2 - 50,
    // LayoutParams.WRAP_CONTENT, true);
    // option.setOutsideTouchable(true);
    // option.setAnimationStyle(R.style.popupMenu);
    // ColorDrawable dw = new ColorDrawable(000000);
    // option.setBackgroundDrawable(dw);
    // View.OnClickListener click = new View.OnClickListener() {
    // public void onClick(View v) {
    // int id = v.getId();
    // switch (id) {
    // case R.id.question_option_share:
    // LogUtils.i(TAG, postDetail.getBody());
    // showShareDialog(context, postDetail.getTitle(), postDetail.getUrl());
    // break;
    // case R.id.question_option_report:
    // AppContext ac = (AppContext) context.getApplication();
    // if(!ac.isLogin()){
    // UIHelper.showLoginDialog(context);
    // return;
    // }
    // showReport(context, postDetail.getUrl(), postDetail.getId());
    // break;
    // default:
    // break;
    // }
    // option.dismiss();
    // }
    // };
    // LinearLayout mShare = (LinearLayout)
    // mMenuView.findViewById(R.id.question_option_share);
    // mShare.setOnClickListener(click);
    // LinearLayout mReport = (LinearLayout)
    // mMenuView.findViewById(R.id.question_option_report);
    // mReport.setOnClickListener(click);
    // //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
    // mMenuView.setOnTouchListener(new OnTouchListener() {
    //
    // public boolean onTouch(View v, MotionEvent event) {
    //
    // int height = mMenuView.findViewById(R.id.pop_layout).getTop();
    // int y=(int) event.getY();
    //
    // if(event.getAction()== MotionEvent.ACTION_UP){
    // if(y<height){
    // option.dismiss();
    // }
    // }
    // return true;
    // }
    // });
    // if (option.isShowing()) {
    // option.dismiss();
    // } else {
    // option.showAsDropDown(aim, -10, 0);
    // }
    // }
}
