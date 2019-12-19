package com.hinmu.lims.config.paramconfig;

import com.hinmu.lims.model.entity.SysConfigEntity;
import com.hinmu.lims.model.reqbean.SysConfigReqValid;
import com.hinmu.lims.service.ISysConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/***
 * 功能描述: <br>
 * 〈 系统配置〉
 * @Author: yhn
 * @Date: 2019-10-30 9:14
 */
@Component
public class SystemParamConfig {
    @Autowired
    private ISysConfigService sysConfigService;


    /**
     * 日志记录器
     */
    private Logger logger = LoggerFactory.getLogger(SystemParamConfig.class);

    public static String FILE_MAX_SIZE = "FILE_MAX_SIZE";//批上传最大数量
    public static String UPLOAD_FILE_PATH = "UPLOAD_FILE_PATH";//上传文件一级路径
    public static String UPLOAD_IMAGE_FILE_PATH = "UPLOAD_IMAGE_FILE_PATH"; //上传文件二级路径
    public static String STATIC_FILE_URL = "STATIC_FILE_URL"; //静态资源访问根目录
    public static String SEARCH_START_DAY_FACE = "SEARCH_START_DAY_FACE"; //以图搜图  开始时间  已当前时间 多少天前
    public static String SEARCH_SIMILARITY_FACE = "SEARCH_SIMILARITY_FACE"; //以图搜图 相似度
    public static String SEARCH_LIST__SIZE_FACE = "SEARCH_LIST__SIZE_FACE"; //以图搜图 每次查询的总数量
    public static String SNAP_IMAGE_PATH_FACE = "SNAP_IMAGE_PATH_FACE"; //抓拍人脸图片路径
    public static String SNAP_VIDEO_PATH_FACE = "SNAP_VIDEO_PATH_FACE"; //抓拍人脸视频路径
    public static String SNAP_VIDEO_PATH_MP4_FACE = "SNAP_VIDEO_PATH_MP4_FACE"; //转换抓拍人脸视频路径
    public static String USER_UPLOAD_FACE = "USER_UPLOAD_FACE"; //用户人脸原图
    public static String FFMPEG_EXE_PATH = "FFMPEG_EXE_PATH"; //视频转换工具路径



    public static String getFileMaxSize() {
        return categoryMap.get(FILE_MAX_SIZE).getKeyValue();
    }


    public static String getUploadFilePath() {
        return categoryMap.get(UPLOAD_FILE_PATH).getKeyValue();
    }


    public static String getUploadImageFilePath() {
        return categoryMap.get(UPLOAD_IMAGE_FILE_PATH).getKeyValue();
    }


    public static String getStaticFileUrl() {
        return categoryMap.get(STATIC_FILE_URL).getKeyValue();
    }


    public static String getSearchStartDayFace() {
        return categoryMap.get(SEARCH_START_DAY_FACE).getKeyValue();
    }


    public static String getSearchSimilarityFace() {
        return categoryMap.get(SEARCH_SIMILARITY_FACE).getKeyValue();
    }


    public static String getSearchListSizeFace() {
        return categoryMap.get(SEARCH_LIST__SIZE_FACE).getKeyValue();
    }


    public static String getSnapImagePathFace() {
        return categoryMap.get(SNAP_IMAGE_PATH_FACE).getKeyValue();
    }


    public static String getSnapVideoPathFace() {
        return categoryMap.get(SNAP_VIDEO_PATH_FACE).getKeyValue();
    }


    public static String getSnapVideoPathMp4Face() {
        return categoryMap.get(SNAP_VIDEO_PATH_MP4_FACE).getKeyValue();
    }


    public static String getUserUploadFace() {
        return categoryMap.get(USER_UPLOAD_FACE).getKeyValue();
    }


    public static String getFFMPEGExePath() {
        return categoryMap.get(FFMPEG_EXE_PATH).getKeyValue();
    }

    /**
     * 配置信息存放对象
     */
    private static final Map<String, SysConfigEntity> categoryMap = new HashMap<>();


    @PostConstruct
    public void init() {
        read();
    }

    /**
     * 读取配置文件
     */
    public synchronized void read() {
        List<SysConfigEntity> systemConfigList = sysConfigService.list();

        if (systemConfigList != null && systemConfigList.size() > 0) {
            ListIterator<SysConfigEntity> iterator = systemConfigList.listIterator();
            categoryMap.clear();
            while (iterator.hasNext()) {
                SysConfigEntity systemConfig = iterator.next();
                String key = String.valueOf(systemConfig.getKeyName());
                categoryMap.put(key, systemConfig);
            }
        }
    }

    /**
     * 增加/更新
     */
    public void update(SysConfigReqValid req) {
        SysConfigEntity sysConfigEntity = new SysConfigEntity();
        sysConfigEntity.setKeyName(req.getKeyName());
        sysConfigEntity.setKeyValue(req.getKeyValue());
        sysConfigService.updateByKey(sysConfigEntity);
        reload();
    }

    /**
     * 获取配置字符串
     *
     * @param keyname Category分类keyname
     * @return Category
     */
    public static SysConfigEntity get(String keyname) {
        return categoryMap.get(keyname);
    }


    /**
     * 返回集合
     *
     * @return
     */
    public static List<SysConfigEntity> systemConfigList() {
        Set<Map.Entry<String, SysConfigEntity>> entrySet = categoryMap.entrySet();
        Iterator<Map.Entry<String, SysConfigEntity>> entryIterator = entrySet.iterator();
        List<SysConfigEntity> systemConfigList = new ArrayList<>();
        while (entryIterator.hasNext()) {
            Map.Entry<String, SysConfigEntity> entry = entryIterator.next();
            systemConfigList.add(entry.getValue());
        }
        return systemConfigList;
    }

    /**
     * 重载配置文件
     */
    public void reload() {
        read();
        logger.info("SystemConfig重载成功");
    }


}
