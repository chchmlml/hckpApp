package com.instway.app.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.instway.app.AppConfig;
import com.instway.app.AppContext;
import com.instway.app.AppException;
import com.instway.app.R;
import com.instway.app.api.ApiClient;
import com.instway.app.bean.URLs;
import com.instway.app.common.UIHelper;
import com.instway.app.widght.ChangeBirthDialog;
import com.instway.app.widght.HomeButton;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.pgyersdk.update.PgyUpdateManager;

import java.util.HashMap;

/**
 * 首页
 */
public class HomeActivity extends BaseActivity implements HomeButton.HomeBtnOnClickListener {


    private AppContext appContext;


    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    HomeButton l1, l2, l3, l4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        appContext = (AppContext) getApplicationContext();
        PgyUpdateManager.register(this, AppConfig.APP_ID);

        l1 = ((HomeButton) findViewById(R.id.l1));
        l2 = ((HomeButton) findViewById(R.id.l2));
        l3 = ((HomeButton) findViewById(R.id.l3));
        l4 = ((HomeButton) findViewById(R.id.l4));

        l1.setHomeBtbOnClickListener(this);
        l2.setHomeBtbOnClickListener(this);
        l3.setHomeBtbOnClickListener(this);
        l4.setHomeBtbOnClickListener(this);

        //定位
        startLocation();
    }


    /**
     * 定位
     */
    private void startLocation() {
        mLocationClient = new LocationClient(appContext);     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);//设置定位模式
        option.setCoorType("bd09ll");
        option.setScanSpan(1000 * 60 * 10);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return;
            //登陆判断
            try {
                if (!AppContext.isLogin(appContext)) {
                    return;
                }
            } catch (AppException e) {
                e.printStackTrace();
            }

            if (location.getLocType() == 62 || location.getLocType() == 63) {
//                new SweetAlertDialog(appContext, SweetAlertDialog.WARNING_TYPE)
//                        .setTitleText("抱歉，定位失败，去 设置 检查下？")
//                        .setConfirmText("确定")
//                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sDialog) {
//                                Intent intent = new Intent(Intent.ACTION_MAIN);
//                                intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");
//                                startActivity(intent);
//                            }
//                        })
//                        .showCancelButton(true)
//                        .setCancelText("取消")
//                        .setCancelClickListener(null)
//                        .show();
//                return;
                UIHelper.ToastMessage(appContext, "抱歉，定位失败，去 设置 检查下？");
            }
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("latitude", location.getLatitude());
            params.put("longitude", location.getLongitude());
            params.put("code", location.getLocType());
            params.put("time", location.getTime());
            //地址投递
            String newUrl = ApiClient._MakeURL(URLs.LOCATION_DRIVER, params, appContext);
            HttpUtils http = new HttpUtils();
            http.send(HttpRequest.HttpMethod.POST, newUrl, null, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                }

                @Override
                public void onFailure(HttpException e, String s) {
                }
            });
        }
    }

    @Override
    public void onClickDown(HomeButton homebtn) {

    }

    @Override
    public void onClickUp(HomeButton homebtn) {
        switch (homebtn.getId()) {
            case R.id.l1:
                UIHelper.showMainRedirect(appContext, 1);
                break;
            case R.id.l2:
                UIHelper.showMainRedirect(appContext, 2);
                break;
            case R.id.l3:
                UIHelper.showMainRedirect(appContext, 3);
                break;
            case R.id.l4:
                UIHelper.showTeamRedirect(appContext);
                break;
            case R.id.l5:
                UIHelper.callPhone(appContext, "021-58887667");
                break;
            default:
                break;
        }
    }
}
