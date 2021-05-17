package com.gxu.testapp.entity;


import java.io.Serializable;

public class SinoseismEventEntity implements Serializable {

    //工程ID
    int engineeringId;
    //项目ID
    int projectId;
    //事件名称
    String eventName;
    //微震事件时间
    String eventTime;
    //微震事件定位误差
    float eventLocError;
    //微震事件X轴坐标
    float eventXCoordinate;
    //微震事件Y轴坐标
    float eventYCoordinate;
    //微震事件Z轴坐标
    float eventZCoordinate;
    //微震事件辐射能
    float eventEnergy;
    //微震事件P波辐射能
    float eventPEnergy;
    //微震事件S波辐射能
    float eventSEnergy;
    //矩震级
    float momentMagnitudeScale;
    //里氏震级
    float richterScale;
    //当地震级
    float localScale;
    //地震矩
    float seismicMoment;
    //P波地震矩
    float pSeismicMoment;
    //S波地震矩
    float sSeismicMoment;
    //体变势
    float volumeChangePotential;
    //视应力
    float apparentStress;
    //视体积
    float apparentVolume;
    //转角频率
    float cornerFrequency;
    //P波转角频率
    float pCornerFrequency;
    //S波转角频率
    float sCornerFrequency;
    //P波低频幅值
    float pLowFreqAmplitude;
    //S波低频幅值
    float sLowFreqAmplitude;
    //静态应力降
    float staticStressDrop;
    //动态应力降
    float dynamicStressDrop;
    //震源半径
    float sourceRadius;
    //最大滑移速度
    float maxSlipVelocity;
    //微震事件服务器ID
    String eventServerId;
    //被触发传感器个数
    int triggeredSensorCount;
    //被触发传感器ID
    String triggeredSensorId;
    //参与定位传感器ID
    String locateSensorId;
    //信号类型
    int signalType;

    public int getEngineeringId() {
        return engineeringId;
    }

    public void setEngineeringId(int engineeringId) {
        this.engineeringId = engineeringId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public float getEventLocError() {
        return eventLocError;
    }

    public void setEventLocError(float eventLocError) {
        this.eventLocError = eventLocError;
    }

    public float getEventXCoordinate() {
        return eventXCoordinate;
    }

    public void setEventXCoordinate(float eventXCoordinate) {
        this.eventXCoordinate = eventXCoordinate;
    }

    public float getEventYCoordinate() {
        return eventYCoordinate;
    }

    public void setEventYCoordinate(float eventYCoordinate) {
        this.eventYCoordinate = eventYCoordinate;
    }

    public float getEventZCoordinate() {
        return eventZCoordinate;
    }

    public void setEventZCoordinate(float eventZCoordinate) {
        this.eventZCoordinate = eventZCoordinate;
    }

    public float getEventEnergy() {
        return eventEnergy;
    }

    public void setEventEnergy(float eventEnergy) {
        this.eventEnergy = eventEnergy;
    }

    public float getEventPEnergy() {
        return eventPEnergy;
    }

    public void setEventPEnergy(float eventPEnergy) {
        this.eventPEnergy = eventPEnergy;
    }

    public float getEventSEnergy() {
        return eventSEnergy;
    }

    public void setEventSEnergy(float eventSEnergy) {
        this.eventSEnergy = eventSEnergy;
    }

    public float getMomentMagnitudeScale() {
        return momentMagnitudeScale;
    }

    public void setMomentMagnitudeScale(float momentMagnitudeScale) {
        this.momentMagnitudeScale = momentMagnitudeScale;
    }

    public float getRichterScale() {
        return richterScale;
    }

    public void setRichterScale(float richterScale) {
        this.richterScale = richterScale;
    }

    public float getLocalScale() {
        return localScale;
    }

    public void setLocalScale(float localScale) {
        this.localScale = localScale;
    }

    public float getSeismicMoment() {
        return seismicMoment;
    }

    public void setSeismicMoment(float seismicMoment) {
        this.seismicMoment = seismicMoment;
    }

    public float getpSeismicMoment() {
        return pSeismicMoment;
    }

    public void setpSeismicMoment(float pSeismicMoment) {
        this.pSeismicMoment = pSeismicMoment;
    }

    public float getsSeismicMoment() {
        return sSeismicMoment;
    }

    public void setsSeismicMoment(float sSeismicMoment) {
        this.sSeismicMoment = sSeismicMoment;
    }

    public float getVolumeChangePotential() {
        return volumeChangePotential;
    }

    public void setVolumeChangePotential(float volumeChangePotential) {
        this.volumeChangePotential = volumeChangePotential;
    }

    public float getApparentStress() {
        return apparentStress;
    }

    public void setApparentStress(float apparentStress) {
        this.apparentStress = apparentStress;
    }

    public float getApparentVolume() {
        return apparentVolume;
    }

    public void setApparentVolume(float apparentVolume) {
        this.apparentVolume = apparentVolume;
    }

    public float getCornerFrequency() {
        return cornerFrequency;
    }

    public void setCornerFrequency(float cornerFrequency) {
        this.cornerFrequency = cornerFrequency;
    }

    public float getpCornerFrequency() {
        return pCornerFrequency;
    }

    public void setpCornerFrequency(float pCornerFrequency) {
        this.pCornerFrequency = pCornerFrequency;
    }

    public float getsCornerFrequency() {
        return sCornerFrequency;
    }

    public void setsCornerFrequency(float sCornerFrequency) {
        this.sCornerFrequency = sCornerFrequency;
    }

    public float getpLowFreqAmplitude() {
        return pLowFreqAmplitude;
    }

    public void setpLowFreqAmplitude(float pLowFreqAmplitude) {
        this.pLowFreqAmplitude = pLowFreqAmplitude;
    }

    public float getsLowFreqAmplitude() {
        return sLowFreqAmplitude;
    }

    public void setsLowFreqAmplitude(float sLowFreqAmplitude) {
        this.sLowFreqAmplitude = sLowFreqAmplitude;
    }

    public float getStaticStressDrop() {
        return staticStressDrop;
    }

    public void setStaticStressDrop(float staticStressDrop) {
        this.staticStressDrop = staticStressDrop;
    }

    public float getDynamicStressDrop() {
        return dynamicStressDrop;
    }

    public void setDynamicStressDrop(float dynamicStressDrop) {
        this.dynamicStressDrop = dynamicStressDrop;
    }

    public float getSourceRadius() {
        return sourceRadius;
    }

    public void setSourceRadius(float sourceRadius) {
        this.sourceRadius = sourceRadius;
    }

    public float getMaxSlipVelocity() {
        return maxSlipVelocity;
    }

    public void setMaxSlipVelocity(float maxSlipVelocity) {
        this.maxSlipVelocity = maxSlipVelocity;
    }

    public String getEventServerId() {
        return eventServerId;
    }

    public void setEventServerId(String eventServerId) {
        this.eventServerId = eventServerId;
    }

    public int getTriggeredSensorCount() {
        return triggeredSensorCount;
    }

    public void setTriggeredSensorCount(int triggeredSensorCount) {
        this.triggeredSensorCount = triggeredSensorCount;
    }

    public String getTriggeredSensorId() {
        return triggeredSensorId;
    }

    public void setTriggeredSensorId(String triggeredSensorId) {
        this.triggeredSensorId = triggeredSensorId;
    }

    public String getLocateSensorId() {
        return locateSensorId;
    }

    public void setLocateSensorId(String locateSensorId) {
        this.locateSensorId = locateSensorId;
    }

    public int getSignalType() {
        return signalType;
    }

    public void setSignalType(int signalType) {
        this.signalType = signalType;
    }
}
