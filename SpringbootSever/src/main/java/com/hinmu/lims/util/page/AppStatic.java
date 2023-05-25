package com.hinmu.lims.util.page;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hao on 2017/11/22.
 */
public class AppStatic {








    /** WEB后台上传图片路径 */
    public static final String WEB_APP_IMAGE_PATH = "/upload/image/";
    /** WEB后台上传视频路径 */
    public static final String WEB_APP_VIDEO_PATH = "/upload/video/";


    /** WEB后台已登录变量名 */
    public static final String WEB_APP_SESSSION_LOGI_USER = "loginUser";
    /** WEB验证码变量名 */
    public static String WEB_APP_AUTH_CODE = "randauthcode";
    /** WEB短信验证码变量名 */
    public static String WEB_APP_AUTH_MSGCODE = "msgcode";


    /** LED 节目名称*/
    public static  final  String LED_PROGRAM_NAME ="LED001";


    /** WEB会话变量名 */
    public static final String WEB_APP_SESSION_ADMIN = "admin";
    /** WEB后台登陆用户组ID */
    public static final String WEB_APP_SESSSION_USER_GROUP_ID = "loginusergroupid";

    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     *  升序
     */
    public static final String ASC = "asc";




    public static final String image_Type="(image/gif)|(image/jpeg)|(image/jpg)|(image/png)|(image/gif)";//  ：gif图片格式 ：jpg图片格式 png图片格式
    public static final String office_file_Type="(application/pdf)|(application/octet-stream)|(application/x-zip-compressed)|(application/vnd.openxmlformats-officedocument.spreadsheetml.sheet)|(application/msword)|(application/vnd.openxmlformats-officedocument.wordprocessingml.document)|(application/pdf)|(application/vnd.ms-excel)|(application/x-xls)";//  ：gif图片格式 ：jpg图片格式 png图片格式
    public static final Map<String,String> office_file_Type_map= new HashMap();//  ：gif图片格式 ：jpg图片格式 png图片格式

    static {
        office_file_Type_map.put("png","image/png");
        office_file_Type_map.put("gif","image/gif");
        office_file_Type_map.put("jpeg","image/jpeg");
        office_file_Type_map.put("jpg","image/jpeg");
        office_file_Type_map.put("bmp","application/x-bmp");
        office_file_Type_map.put("flv"," ");
        office_file_Type_map.put("swf","application/x-shockwave-flash");
        office_file_Type_map.put("mkv","application/x-shockwave-flash");
        office_file_Type_map.put("avi","video/avi");
        office_file_Type_map.put("rm","application/vnd.rn-realmedia");
        office_file_Type_map.put("rmvb","application/vnd.rn-realmedia-vbr");
        office_file_Type_map.put("mp4","video/mpeg4");
        office_file_Type_map.put("mp3","audio/mp3");
        office_file_Type_map.put("rar","application/x-zip-compressed");
        office_file_Type_map.put("zip","application/x-zip-compressed");
        office_file_Type_map.put("doc","application/msword");
        office_file_Type_map.put("docx","application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        office_file_Type_map.put("pdf","application/pdf");
        office_file_Type_map.put("xls","application/vnd.ms-excel");
        office_file_Type_map.put("xlsx","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        office_file_Type_map.put("ppt","application/x-ppt");
        office_file_Type_map.put("pptx","application/vnd.ms-powerpoint");
        office_file_Type_map.put("txt","(text/plain)");
        office_file_Type_map.put("xml","(text/xml)");
    }

}
