package com.hinmu.lims.model.respbean;


/**
 * Created by hao on 2017/11/28.
 */
public class ImgFileBean {
    /**
     * 文件类型
     */
    private String fileType;
    /**
     * 文件大小字节
     */
    private Long fileSize;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 原图路径
     */
    private String fileSrc;
    /**
     * 缩略图路径
     */
    private String thumb_fileSrc;


    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSrc() {
        return fileSrc;
    }

    public void setFileSrc(String fileSrc) {
        this.fileSrc = fileSrc;
    }

    public String getThumb_fileSrc() {
        return thumb_fileSrc;
    }

    public void setThumb_fileSrc(String thumb_fileSrc) {
        this.thumb_fileSrc = thumb_fileSrc;
    }
}
