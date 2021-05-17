package com.gxu.testapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gxu.testapp.MainActivity;
import com.gxu.testapp.R;
import com.gxu.testapp.entity.SinoseismEventEntity;

import org.json.JSONException;
import org.json.JSONObject;

public class EventDetail extends AppCompatActivity {

    Button back;
    SinoseismEventEntity sinoseismEventEntity=new SinoseismEventEntity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail);

        TextView engineeringIdText=findViewById(R.id.engineeringId);
        TextView projectIdText=findViewById(R.id.projectId);
        TextView eventNameText=findViewById(R.id.eventName);
        TextView eventTimeText=findViewById(R.id.eventTime);
        TextView eventLocErrorText=findViewById(R.id.eventLocError);
        TextView eventXCoordinateText=findViewById(R.id.eventXCoordinate);
        TextView eventYCoordinateText=findViewById(R.id.eventYCoordinate);
        TextView eventZCoordinateText=findViewById(R.id.eventZCoordinate);
        TextView eventEnergyText=findViewById(R.id.eventEnergy);
        TextView eventPEnergyText=findViewById(R.id.eventPEnergy);
        TextView eventSEnergyText=findViewById(R.id.eventSEnergy);
        TextView momentMagnitudeScaleText=findViewById(R.id.momentMagnitudeScale);
        TextView richterScaleText=findViewById(R.id.richterScale);
        TextView localScaleText=findViewById(R.id.localScale);

        TextView seismicMomentText=findViewById(R.id.seismicMoment);
        TextView pSeismicMomentText=findViewById(R.id.pSeismicMoment);
        TextView sSeismicMomentText=findViewById(R.id.sSeismicMoment);
        TextView volumeChangePotentialText=findViewById(R.id.volumeChangePotential);
        TextView apparentStressText=findViewById(R.id.apparentStress);
        TextView apparentVolumeText=findViewById(R.id.apparentVolume);
        TextView cornerFrequencyText=findViewById(R.id.cornerFrequency);
        TextView pCornerFrequencyText=findViewById(R.id.pCornerFrequency);
        TextView sCornerFrequencyText=findViewById(R.id.sCornerFrequency);
        TextView pLowFreqAmplitudeText=findViewById(R.id.pLowFreqAmplitude);
        TextView sLowFreqAmplitudeText=findViewById(R.id.sLowFreqAmplitude);
        TextView staticStressDropText=findViewById(R.id.staticStressDrop);
        TextView dynamicStressDropText=findViewById(R.id.dynamicStressDrop);
        TextView sourceRadiusText=findViewById(R.id.sourceRadius);

        TextView maxSlipVelocityText=findViewById(R.id.maxSlipVelocity);
        TextView eventServerIdText=findViewById(R.id.eventServerId);
        TextView triggeredSensorCountText=findViewById(R.id.triggeredSensorCount);
        TextView triggeredSensorIdText=findViewById(R.id.triggeredSensorId);
        TextView locateSensorIdText=findViewById(R.id.locateSensorId);
        TextView signalTypeText=findViewById(R.id.signalType);

        back=findViewById(R.id.back);


        final Bundle bundle=this.getIntent().getExtras();
        String data=bundle.getString("data");


        sinoseismEventEntity=JsonResult(data);
        engineeringIdText.setText(""+sinoseismEventEntity.getEngineeringId());
        projectIdText.setText(""+sinoseismEventEntity.getProjectId());
        eventNameText.setText(sinoseismEventEntity.getEventName());
        eventTimeText.setText(sinoseismEventEntity.getEventTime());
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

        
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(EventDetail.this, MainActivity.class);
                Bundle bundle1=new Bundle();
                bundle.putSerializable("event",sinoseismEventEntity);
                startActivity(intent);
            }
        });
    }

    public SinoseismEventEntity JsonResult(String data)
    {
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


}