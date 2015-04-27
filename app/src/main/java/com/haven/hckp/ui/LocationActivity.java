package com.haven.hckp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.haven.hckp.AppContext;
import com.haven.hckp.LocationApplication;
import com.haven.hckp.R;
import com.lidroid.xutils.util.LogUtils;

public class LocationActivity extends Activity {
	private LocationClient mLocationClient;
	private TextView LocationResult,ModeInfor;
	private Button startLocation;
	private RadioGroup selectMode,selectCoordinates;
	private EditText frequence;
	private LocationMode tempMode = LocationMode.Hight_Accuracy;
	private String tempcoor="gcj02";
	private CheckBox checkGeoLocation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location);
		mLocationClient = ((LocationApplication)getApplication()).mLocationClient;
		
		LocationResult = (TextView)findViewById(R.id.textView1);
		ModeInfor= (TextView)findViewById(R.id.modeinfor);
		 ((LocationApplication)getApplication()).mLocationResult = LocationResult;
		 frequence = (EditText)findViewById(R.id.frequence);
		 checkGeoLocation = (CheckBox)findViewById(R.id.geolocation);
		startLocation = (Button)findViewById(R.id.addfence);
		frequence = (EditText)findViewById(R.id.frequence);
		checkGeoLocation = (CheckBox)findViewById(R.id.geolocation);
		startLocation = (Button)findViewById(R.id.addfence);
		startLocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				InitLocation();

				if(startLocation.getText().equals(getString(R.string.startlocation))){
					LogUtils.i("start service ..........");
					mLocationClient.start();
					startLocation.setText(getString(R.string.stoplocation));
				}else{
					LogUtils.i("stop service ..........");
					mLocationClient.stop();
					startLocation.setText(getString(R.string.startlocation));
				}


			}
		});
		mLocationClient.start();

		selectMode = (RadioGroup)findViewById(R.id.selectMode);
		selectCoordinates= (RadioGroup)findViewById(R.id.selectCoordinates);
		selectMode.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				String ModeInformation = null;
				switch (checkedId) {
					case R.id.radio_hight:
						tempMode = LocationMode.Hight_Accuracy;
						ModeInformation = getString(R.string.hight_accuracy_desc);
						break;
					case R.id.radio_low:
						tempMode = LocationMode.Battery_Saving;
						ModeInformation = getString(R.string.saving_battery_desc);
						break;
					case R.id.radio_device:
						tempMode = LocationMode.Device_Sensors;
						ModeInformation = getString(R.string.device_sensor_desc);
						break;
					default:
						break;
				}
				ModeInfor.setText(ModeInformation);
			}
		});
		selectCoordinates.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
					case R.id.radio_gcj02:
						tempcoor = "gcj02";
						break;
					case R.id.radio_bd09ll:
						tempcoor = "bd09ll";
						break;
					case R.id.radio_bd09:
						tempcoor = "bd09";
						break;
					default:
						break;
				}
			}
		});
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		mLocationClient.stop();
		super.onStop();
	}

	private void InitLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);
		option.setCoorType(tempcoor);
		int span=1000;
		try {
			span = Integer.valueOf(frequence.getText().toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		option.setScanSpan(span);
		option.setIsNeedAddress(checkGeoLocation.isChecked());
		mLocationClient.setLocOption(option);
	}
}
