package com.gxu.testapp.entity;

public class PDF_diary {

    private int PdfId;
    private String PdfName;
    private String uploadTime;
    private String Uploader;

    public int getPdfId() {
        return PdfId;
    }
    public void setPdfId(int PdfID) {
        this.PdfId = PdfID;
    }

    public String getPdfName() {
        return PdfName;
    }
    public void setPdfName(String PdfName) {
        this.PdfName = PdfName;
    }

    public String getuploadTime() {
        return uploadTime;
    }
    public void setuploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUploader() {
        return Uploader;
    }
    public void setUploader(String Uploader) {
        this.Uploader = Uploader;
    }


}
