package com.instway.app.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.instway.app.AppContext;
import com.instway.app.AppManager;
import com.instway.app.R;
import com.instway.app.api.ApiClient;
import com.instway.app.bean.URLs;
import com.instway.app.common.ImageUtil;
import com.instway.app.common.StringUtils;
import com.instway.app.common.UIHelper;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SelectPictuerActivity extends BaseActivity {
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    /**
     * 使用相册中的图片
     */
    public static final int SELECT_PIC_BY_PICK_PHOTO = 2;

    /**
     * 从Intent获取图片路径的KEY
     */
    public static final String KEY_PHOTO_PATH = "photo_path";

    private static final String TAG = "SelectPicActivity";

    @ViewInject(R.id.back_img)
    private ImageView backBtn;

    @ViewInject(R.id.title_tv)
    private TextView mTitleTv;

    @ViewInject(R.id.image_view)
    private ImageView imageView;

    private AppContext appContext;
    /**
     * 获取到的图片路径
     */
    private String picPath;
    private Intent lastIntent;
    private Uri photoUri;

    Bitmap bp = null;
    ImageView imageview;
    float scaleWidth;
    float scaleHeight;

    int h;
    boolean num = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pictuer);
        ViewUtils.inject(this);

        appContext = (AppContext) getApplicationContext();
        initView();
    }

    /**
     * 初始化加载View
     */
    private void initView() {
        mTitleTv.setText("图片上传");

        //显示返回按钮
        backBtn.setVisibility(View.VISIBLE);
        lastIntent = getIntent();
        Bundle bundle = lastIntent.getExtras();
        String src = bundle.getString("src");
        if (!StringUtils.isEmpty(src)) {
            BitmapUtils bitmapUtils = new BitmapUtils(appContext);
            bitmapUtils.display(imageView, src + "?r=" + StringUtils.randomNum());
//            bitmapUtils.display(imageView, src, new BitmapLoadCallBack<BootstrapCircleThumbnail>() {
//                @Override
//                public void onLoadCompleted(BootstrapCircleThumbnail bootstrapCircleThumbnail, String s, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig, BitmapLoadFrom bitmapLoadFrom) {
//                    imageView.setImageBitmap(bitmap);
//                }
//
//                @Override
//                public void onLoadFailed(BootstrapCircleThumbnail bootstrapCircleThumbnail, String s, Drawable drawable) {
//                    //UIHelper.ToastMessage(appContext, "头像加载失败");
//                }
//            });
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

//        Bundle bundle = lastIntent.getExtras();
//        String src = bundle.getString("src");
//        if (!StringUtils.isEmpty(src)) {
//            Display display = getWindowManager().getDefaultDisplay();
//            imageview = (ImageView) findViewById(R.id.img_lancher);
//            bp = imageView.get;
//            int width = bp.getWidth();
//            int height = bp.getHeight();
//            int w = display.getWidth();
//            int h = display.getHeight();
//            scaleWidth = ((float) w) / width;
//            scaleHeight = ((float) h) / height;
//            imageview.setImageBitmap(bp);
//
//            switch (event.getAction()) {
//
//                case MotionEvent.ACTION_DOWN:
//                    if (num == true) {
//                        Matrix matrix = new Matrix();
//                        matrix.postScale(scaleWidth, scaleHeight);
//
//                        Bitmap newBitmap = Bitmap.createBitmap(bp, 0, 0, bp.getWidth(), bp.getHeight(), matrix, true);
//                        imageview.setImageBitmap(newBitmap);
//                        num = false;
//                    } else {
//                        Matrix matrix = new Matrix();
//                        matrix.postScale(1.0f, 1.0f);
//                        Bitmap newBitmap = Bitmap.createBitmap(bp, 0, 0, bp.getWidth(), bp.getHeight(), matrix, true);
//                        imageview.setImageBitmap(newBitmap);
//                        num = true;
//                    }
//                    break;
//            }
//
//        }
        return super.onTouchEvent(event);
    }


    @OnClick({R.id.from_photo, R.id.from_album, R.id.back_img, R.id.upload_btn})
    public void buttonClick(View v) {

        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.from_photo:
                takePhoto();
                break;
            case R.id.from_album:
                pickPhoto();
                break;
            case R.id.upload_btn:
                doUpload();
                break;
        }
    }

    /**
     * 拍照获取图片
     */
    private void takePhoto() {
        //执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//"android.media.action.IMAGE_CAPTURE"
            /***
             * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的
             * 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
             * 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
             */
            ContentValues values = new ContentValues();
            photoUri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            /**-----------------*/
            startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
        } else {
            Toast.makeText(this, "内存卡不存在", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 从相册中取图片
     */
    private void pickPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            doPhoto(requestCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 选择图片后，获取图片的路径
     *
     * @param requestCode
     * @param data
     */
    private void doPhoto(int requestCode, Intent data) {
        if (requestCode == SELECT_PIC_BY_PICK_PHOTO)  //从相册取图片，有些手机有异常情况，请注意
        {
            if (data == null) {
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
            photoUri = data.getData();
            if (photoUri == null) {
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
        }
        String[] pojo = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
            cursor.moveToFirst();
            picPath = cursor.getString(columnIndex);
            //cursor.close();
        }
        Log.i(TAG, "imagePath = " + picPath);
        if (picPath != null && (picPath.endsWith(".png") || picPath.endsWith(".PNG") || picPath.endsWith(".jpg") || picPath.endsWith(".JPG"))) {
//            lastIntent.putExtra(KEY_PHOTO_PATH, picPath);
//            setResult(Activity.RESULT_OK, lastIntent);
//            finish();

            Bitmap bm = ImageUtil.getimage(picPath);
            imageView.setImageBitmap(bm);
        } else {
            Toast.makeText(this, "选择图片文件不正确", Toast.LENGTH_LONG).show();
        }
    }

    private void doUpload() {
        //上传类型
        Bundle bundle = lastIntent.getExtras();
        int picType = bundle.getInt("pic_type");
        HashMap<String, Object> p = new HashMap<String, Object>();
        String newUrl = "";
        switch (picType) {
            case 1:
                newUrl = ApiClient._MakeURL(URLs.UPLOAD_HEADPIC_ID, p, appContext);
                LogUtils.i("--->头像");
                break;
            case 2:
                p.put("pic_type", "idcard");
                newUrl = ApiClient._MakeURL(URLs.UPLOAD_PIC_ID, p, appContext);
                LogUtils.i("--->身份证");
                break;
            case 3:
                p.put("pic_type", "drilic");
                newUrl = ApiClient._MakeURL(URLs.UPLOAD_PIC_ID, p, appContext);
                LogUtils.i("--->驾驶证");
                break;
            case 4:
                p.put("car_id", bundle.getString("car_id"));
                newUrl = ApiClient._MakeURL(URLs.UPLOAD_CAR_ID, p, appContext);
                LogUtils.i("--->行驶证");
                break;
            case 5:
                p.put("tp_tt_id", bundle.getString("tp_tt_id"));
                p.put("type", bundle.getString("tkType"));
                newUrl = ApiClient._MakeURL(URLs.UPLOAD_GET_GOODS, p, appContext);
                LogUtils.i("--->签收拍照");
                break;
        }
        if (StringUtils.isEmpty(picPath)) {
            UIHelper.ToastMessage(appContext, "请选择图片...");
            return;
        }
        RequestParams params = new RequestParams();
        //params.addBodyParameter("input_name", "filename");
        params.addBodyParameter("pic_key", new File(picPath));
        HttpUtils http = new HttpUtils();
        final ProgressDialog pd = ProgressDialog.show(this, null, "正在上传图片...");
        http.send(HttpRequest.HttpMethod.POST,
                newUrl,
                params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        if (isUploading) {
                            //testTextView.setText("upload: " + current + "/" + total);
                        } else {
                            //testTextView.setText("reply: " + current + "/" + total);
                        }
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        JSONObject obj = JSON.parseObject(responseInfo.result);
                        String code = obj.get("code").toString();
                        String msg = obj.get("msg").toString();
                        if (code.equals("1")) {
                            UIHelper.ToastMessage(appContext, msg);
                            finish();
                        } else {
                            UIHelper.ToastMessage(appContext, msg);
                        }
                        pd.dismiss();
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        pd.dismiss();
                        UIHelper.ToastMessage(appContext, "图片上传失败：" + e.getMessage());
                        UIHelper.ToastMessage(appContext, "图片上传失败：" + s);
                    }
                });
    }
}
