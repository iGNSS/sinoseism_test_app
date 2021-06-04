package com.gxu.testapp.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.gxu.testapp.R;
import com.gxu.testapp.entity.SinoseismEventEntity;
import com.gxu.testapp.event.SinoseismEvent;
import com.gxu.testapp.util.TimeUtils;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import static com.gxu.testapp.ui.MainActivity.isPausePush;

public class AppMainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView engineeringIdText;
    TextView projectIdText;
    TextView eventNameText;
    TextView eventTimeText;
    TextView eventLocErrorText;
    TextView eventXCoordinateText;
    TextView eventYCoordinateText;
    TextView eventZCoordinateText;
    TextView eventEnergyText;
    TextView eventPEnergyText;
    TextView eventSEnergyText;
    TextView momentMagnitudeScaleText;
    TextView richterScaleText;
    TextView localScaleText;
    TextView seismicMomentText;
    TextView pSeismicMomentText;
    TextView sSeismicMomentText;
    TextView volumeChangePotentialText;
    TextView apparentStressText;
    TextView apparentVolumeText;
    TextView cornerFrequencyText;
    TextView pCornerFrequencyText;
    TextView sCornerFrequencyText;
    TextView pLowFreqAmplitudeText;
    TextView sLowFreqAmplitudeText;
    TextView staticStressDropText;
    TextView dynamicStressDropText;
    TextView sourceRadiusText;
    TextView maxSlipVelocityText;
    TextView eventServerIdText;
    TextView triggeredSensorCountText;
    TextView triggeredSensorIdText;
    TextView locateSensorIdText;
    TextView signalTypeText;

    Button startPushButton;
    Button pausePushButton;
    Toolbar toolbar;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_main);
        EventBus.getDefault().register(this);
        initViews();

        toolbar = (Toolbar) findViewById(R.id.waveeven_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle=getIntent().getExtras();
        if (bundle != null){
            String data=bundle.getString("data");
            if (data != null) {
                SinoseismEventEntity sinoseismEventEntity = JsonResult(data);
                refreshUI(sinoseismEventEntity);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews(){
        engineeringIdText=findViewById(R.id.engineeringId);
        projectIdText=findViewById(R.id.projectId);
        eventNameText=findViewById(R.id.eventName);
        eventTimeText=findViewById(R.id.eventTime);
        eventLocErrorText=findViewById(R.id.eventLocError);
        eventXCoordinateText=findViewById(R.id.eventXCoordinate);
        eventYCoordinateText=findViewById(R.id.eventYCoordinate);
        eventZCoordinateText=findViewById(R.id.eventZCoordinate);
        eventEnergyText=findViewById(R.id.eventEnergy);
        eventPEnergyText=findViewById(R.id.eventPEnergy);
        eventSEnergyText=findViewById(R.id.eventSEnergy);
        momentMagnitudeScaleText=findViewById(R.id.momentMagnitudeScale);
        richterScaleText=findViewById(R.id.richterScale);
        localScaleText=findViewById(R.id.localScale);
        seismicMomentText=findViewById(R.id.seismicMoment);
        pSeismicMomentText=findViewById(R.id.pSeismicMoment);
        sSeismicMomentText=findViewById(R.id.sSeismicMoment);
        volumeChangePotentialText=findViewById(R.id.volumeChangePotential);
        apparentStressText=findViewById(R.id.apparentStress);
        apparentVolumeText=findViewById(R.id.apparentVolume);
        cornerFrequencyText=findViewById(R.id.cornerFrequency);
        pCornerFrequencyText=findViewById(R.id.pCornerFrequency);
        sCornerFrequencyText=findViewById(R.id.sCornerFrequency);
        pLowFreqAmplitudeText=findViewById(R.id.pLowFreqAmplitude);
        sLowFreqAmplitudeText=findViewById(R.id.sLowFreqAmplitude);
        staticStressDropText=findViewById(R.id.staticStressDrop);
        dynamicStressDropText=findViewById(R.id.dynamicStressDrop);
        sourceRadiusText=findViewById(R.id.sourceRadius);
        maxSlipVelocityText=findViewById(R.id.maxSlipVelocity);
        eventServerIdText=findViewById(R.id.eventServerId);
        triggeredSensorCountText=findViewById(R.id.triggeredSensorCount);
        triggeredSensorIdText=findViewById(R.id.triggeredSensorId);
        locateSensorIdText=findViewById(R.id.locateSensorId);
        signalTypeText=findViewById(R.id.signalType);

        startPushButton = findViewById(R.id.app_activity_main_start_push_btn);
        pausePushButton = findViewById(R.id.app_activity_main_pause_push_btn);
        startPushButton.setOnClickListener(this);
        pausePushButton.setOnClickListener(this);

        startPushButton.setEnabled(isPausePush);
        pausePushButton.setEnabled(!isPausePush);
        //MiPushClient.resumePush(this, null);
    }

    private SinoseismEventEntity JsonResult(String data) {
        SinoseismEventEntity tmp_sinoseismEventEntity=new SinoseismEventEntity();
        try {
            JSONObject event=new JSONObject(data);
            int engineeringId=event.getInt("engineeringId");
            tmp_sinoseismEventEntity.setEngineeringId(engineeringId);
            int projectId=event.getInt("projectId");
            tmp_sinoseismEventEntity.setProjectId(projectId);
            String eventName=event.getString("eventName");
            tmp_sinoseismEventEntity.setEventName(eventName);
            String  eventTime=event.getString("eventTime");
            tmp_sinoseismEventEntity.setEventTime(eventTime);
            float eventLocError=(float) event.getDouble("eventLocError");
            tmp_sinoseismEventEntity.setEventLocError(eventLocError);
            float eventXCoordinate=(float) event.getDouble("eventXCoordinate");
            tmp_sinoseismEventEntity.setEventXCoordinate(eventXCoordinate);
            float eventYCoordinate=(float) event.getDouble("eventYCoordinate");
            tmp_sinoseismEventEntity.setEventLocError(eventYCoordinate);
            float eventZCoordinate=(float) event.getDouble("eventZCoordinate");
            tmp_sinoseismEventEntity.setEventXCoordinate(eventZCoordinate);
            float eventEnergy=(float) event.getDouble("eventEnergy");
            tmp_sinoseismEventEntity.setEventEnergy(eventEnergy);
            float eventPEnergy=(float) event.getDouble("eventPEnergy");
            tmp_sinoseismEventEntity.setEventPEnergy(eventPEnergy);
            float eventSEnergy=(float) event.getDouble("eventSEnergy");
            tmp_sinoseismEventEntity.setEventPEnergy(eventSEnergy);
            float momentMagnitudeScale=(float) event.getDouble("momentMagnitudeScale");
            tmp_sinoseismEventEntity.setMomentMagnitudeScale(momentMagnitudeScale);
            float richterScale=(float) event.getDouble("richterScale");
            tmp_sinoseismEventEntity.setRichterScale(richterScale);
            float localScale=(float) event.getDouble("localScale");
            tmp_sinoseismEventEntity.setLocalScale(localScale);
            float seismicMoment=(float) event.getDouble("seismicMoment");
            tmp_sinoseismEventEntity.setSeismicMoment(seismicMoment);
            float pSeismicMoment=(float) event.getDouble("pSeismicMoment");
            tmp_sinoseismEventEntity.setpSeismicMoment(pSeismicMoment);
            float sSeismicMoment=(float) event.getDouble("sSeismicMoment");
            tmp_sinoseismEventEntity.setsSeismicMoment(sSeismicMoment);
            float volumeChangePotential=(float) event.getDouble("volumeChangePotential");
            tmp_sinoseismEventEntity.setVolumeChangePotential(volumeChangePotential);
            float apparentStress=(float) event.getDouble("apparentStress");
            tmp_sinoseismEventEntity.setApparentStress(apparentStress);
            float apparentVolume=(float) event.getDouble("apparentStress");
            tmp_sinoseismEventEntity.setApparentVolume(apparentVolume);
            float cornerFrequency=(float) event.getDouble("cornerFrequency");
            tmp_sinoseismEventEntity.setCornerFrequency(cornerFrequency);
            float pCornerFrequency=(float) event.getDouble("pCornerFrequency");
            tmp_sinoseismEventEntity.setpCornerFrequency(pCornerFrequency);
            float sCornerFrequency=(float) event.getDouble("sCornerFrequency");
            tmp_sinoseismEventEntity.setsCornerFrequency(sCornerFrequency);
            float pLowFreqAmplitude=(float) event.getDouble("pLowFreqAmplitude");
            tmp_sinoseismEventEntity.setpLowFreqAmplitude(pLowFreqAmplitude);
            float sLowFreqAmplitude=(float) event.getDouble("sLowFreqAmplitude");
            tmp_sinoseismEventEntity.setsLowFreqAmplitude(sLowFreqAmplitude);
            float staticStressDrop=(float) event.getDouble("staticStressDrop");
            tmp_sinoseismEventEntity.setStaticStressDrop(staticStressDrop);
            float dynamicStressDrop=(float) event.getDouble("dynamicStressDrop");
            tmp_sinoseismEventEntity.setDynamicStressDrop(dynamicStressDrop);
            float sourceRadius=(float) event.getDouble("sourceRadius");
            tmp_sinoseismEventEntity.setSourceRadius(sourceRadius);
            float maxSlipVelocity=(float) event.getDouble("maxSlipVelocity");
            tmp_sinoseismEventEntity.setMaxSlipVelocity(maxSlipVelocity);
            String eventServerId=event.getString("eventServerId");
            tmp_sinoseismEventEntity.setEventServerId(eventServerId);
            int triggeredSensorCount=event.getInt("triggeredSensorCount");
            tmp_sinoseismEventEntity.setTriggeredSensorCount(triggeredSensorCount);
            String  triggeredSensorId=event.getString("triggeredSensorId");
            tmp_sinoseismEventEntity.setTriggeredSensorId(triggeredSensorId);
            String  locateSensorId=event.getString("locateSensorId");
            tmp_sinoseismEventEntity.setLocateSensorId(locateSensorId);
            int signalType=event.getInt("signalType");
            tmp_sinoseismEventEntity.setSignalType(signalType);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  tmp_sinoseismEventEntity;
    }

    @SuppressLint("SetTextI18n")
    private void refreshUI(SinoseismEventEntity sinoseismEventEntity){
        engineeringIdText.setText(""+sinoseismEventEntity.getEngineeringId());
        projectIdText.setText(""+sinoseismEventEntity.getProjectId());
        eventNameText.setText(sinoseismEventEntity.getEventName());
        eventTimeText.setText(TimeUtils.formatSinoseismEventTime(sinoseismEventEntity.getEventTime()));
        eventLocErrorText.setText(""+sinoseismEventEntity.getEventLocError());
        eventXCoordinateText.setText(""+sinoseismEventEntity.getEventXCoordinate());
        eventYCoordinateText.setText(""+sinoseismEventEntity.getEventYCoordinate());
        eventZCoordinateText.setText(""+sinoseismEventEntity.getEventZCoordinate());
        eventEnergyText.setText(""+sinoseismEventEntity.getEventEnergy());
        eventPEnergyText.setText(""+sinoseismEventEntity.getEventPEnergy());
        eventSEnergyText.setText(""+sinoseismEventEntity.getEventSEnergy());
        momentMagnitudeScaleText.setText(""+sinoseismEventEntity.getMomentMagnitudeScale());
        richterScaleText.setText(""+sinoseismEventEntity.getRichterScale());
        localScaleText.setText(""+sinoseismEventEntity.getLocalScale());
        seismicMomentText.setText(""+sinoseismEventEntity.getSeismicMoment());
        pSeismicMomentText.setText(""+sinoseismEventEntity.getpSeismicMoment());
        sSeismicMomentText.setText(""+sinoseismEventEntity.getsSeismicMoment());
        volumeChangePotentialText.setText(""+sinoseismEventEntity.getVolumeChangePotential());
        apparentStressText.setText(""+sinoseismEventEntity.getApparentStress());
        apparentVolumeText.setText(""+sinoseismEventEntity.getApparentVolume());
        cornerFrequencyText.setText(""+sinoseismEventEntity.getCornerFrequency());
        pCornerFrequencyText.setText(""+sinoseismEventEntity.getpCornerFrequency());
        sCornerFrequencyText.setText(""+sinoseismEventEntity.getsCornerFrequency());
        pLowFreqAmplitudeText.setText(""+sinoseismEventEntity.getpLowFreqAmplitude());
        sLowFreqAmplitudeText.setText(""+sinoseismEventEntity.getsLowFreqAmplitude());
        staticStressDropText.setText(""+sinoseismEventEntity.getStaticStressDrop());
        dynamicStressDropText.setText(""+sinoseismEventEntity.getDynamicStressDrop());
        sourceRadiusText.setText(""+sinoseismEventEntity.getSourceRadius());
        maxSlipVelocityText.setText(""+sinoseismEventEntity.getMaxSlipVelocity());
        eventServerIdText.setText(sinoseismEventEntity.getEventServerId());
        triggeredSensorCountText.setText(""+sinoseismEventEntity.getTriggeredSensorCount());
        triggeredSensorIdText.setText(sinoseismEventEntity.getTriggeredSensorId());
        locateSensorIdText.setText(sinoseismEventEntity.getLocateSensorId());
        signalTypeText.setText(""+sinoseismEventEntity.getSignalType());
        Toast.makeText(this,"微震事件项目ID:"+sinoseismEventEntity.getProjectId()+",数据已显示",Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSinoseismEventArrived(SinoseismEvent event){
        SinoseismEventEntity sinoseismEventEntity = JsonResult(event.getEventJson());
        refreshUI(sinoseismEventEntity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.app_activity_main_start_push_btn:{
                if (isPausePush){
                    MiPushClient.resumePush(this, null);
                    isPausePush = false;
                    startPushButton.setEnabled(false);
                    pausePushButton.setEnabled(true);
                    Toast.makeText(this, "已恢复推送！", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.app_activity_main_pause_push_btn:{
                if (!isPausePush){
                    MiPushClient.pausePush(this, null);
                    isPausePush = true;
                    startPushButton.setEnabled(true);
                    pausePushButton.setEnabled(false);
                    Toast.makeText(this, "已停止推送！", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }
}
