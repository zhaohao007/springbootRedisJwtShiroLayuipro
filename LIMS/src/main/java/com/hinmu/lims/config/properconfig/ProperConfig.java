package com.hinmu.lims.config.properconfig;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@EnableAutoConfiguration
@ConfigurationProperties(prefix = "file")
public class ProperConfig {

//    //服务器IP地址
//    @Value("${file.serverIP}")
//    private String serverIP;
//
//    //服务器IP端口号
//    @Value("${file.serverPort}")
//    private String serverPort;
//
//    /**
//     * 系统上传的文件根路径
//     */
//    @Value("${file.uploadFilePath}")
//    private String uploadFilePath;
//    @Value("${file.uploadImageFileName}")
//    private String uploadImageFileName;
//    @Value("${file.uploadMaxCount}")
//    private String uploadMaxCount;
//    @Value("${file.contextPath}")
//    private String contextPath;
//
//    @Value("${face.advance}")
//    private String advance;
//    @Value("${face.similarity}")
//    private String similarity;
//
//    @Value("${face.search.maxtotal}")
//    private String faceSearchMaxResTotal;
//
//    @Value("${face.snapImagePath}")
//    private String snapImagePath;
//
//
//    @Value("${face.snapVideoPath}")
//    private String snapVideoPath;
//
//    @Value("${face.snapVideoPathMp4}")
//    private String snapVideoPathMp4;
//    @Value("${face.userImage}")
//    private String userImage;
//
//    @Value("${face.faceStaticUrl}")
//    private String faceStaticUrl;
//
//    public String getFaceStaticUrl() {
//        return faceStaticUrl;
//    }
//
//    public void setFaceStaticUrl(String faceStaticUrl) {
//        this.faceStaticUrl = faceStaticUrl;
//    }
//
//    public String getUserImage() {
//        return userImage;
//    }
//
//    public void setUserImage(String userImage) {
//        this.userImage = userImage;
//    }
//
//    private static String ffmpegEXEPath;
//
//    public String getSnapVideoPathMp4() {
//        return snapVideoPathMp4;
//    }
//
//    public void setSnapVideoPathMp4(String snapVideoPathMp4) {
//        this.snapVideoPathMp4 = snapVideoPathMp4;
//    }
//
//    public static  String getFfmpegEXEPath() {
//        return ffmpegEXEPath;
//    }
//    @Value("${ffmpegEXE.path}")
//    public void setFfmpegEXEPath(String ffmpegEXEPath) {
//        ProperConfig.ffmpegEXEPath = ffmpegEXEPath;
//    }
//
//    public String getSnapVideoPath() {
//        return snapVideoPath;
//    }
//
//    public void setSnapVideoPath(String snapVideoPath) {
//        this.snapVideoPath = snapVideoPath;
//    }
//
//    public String getSnapImagePath() {
//        return snapImagePath;
//    }
//
//    public void setSnapImagePath(String snapImagePath) {
//        this.snapImagePath = snapImagePath;
//    }
//
//    public String getFaceSearchMaxResTotal() {
//        return faceSearchMaxResTotal;
//    }
//
//    public void setFaceSearchMaxResTotal(String faceSearchMaxResTotal) {
//        this.faceSearchMaxResTotal = faceSearchMaxResTotal;
//    }
//
//    public String getSimilarity() {
//        return similarity;
//    }
//
//    public void setSimilarity(String similarity) {
//        this.similarity = similarity;
//    }
//
//    public String getAdvance() {
//        return advance;
//    }
//
//    public void setAdvance(String advance) {
//        this.advance = advance;
//    }
//
//    public String getContextPath() {
//        return contextPath;
//    }
//
//    public String getServerIPPort() {
//        return serverIP + ":" + serverPort;
//    }
//
//    public String getUploadFilePath() {
//        return uploadFilePath;
//    }
//
//    public String getUploadImageFileName() {
//        return uploadImageFileName;
//    }
//
//    public String getServerIPPortContext() {
//        return serverIP + ":" + serverPort+"/"+contextPath;
//    }
//
//    public int getUploadMaxCount() {
//        return Integer.valueOf(uploadMaxCount);
//    }

}
