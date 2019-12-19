package com.hinmu.lims.util.upload;


import com.hinmu.lims.model.respbean.ImgFileBean;
import com.hinmu.lims.util.date.DateUtil;
import com.hinmu.lims.util.page.AppStatic;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hao on 2018/7/27.
 */
public class UploadImgUtil {


    /**
     * @param picList    上传过来的源文件 MultipartFile
     * @param path       系统配置表中的文件路径  D：//img/file/
     * @param uploadFileDomain       系统配置表中的文件域名部分
     * @param uploadFileName       系统配置表中的文件所在目录命名
     * @param zipThumb       图片是否压缩缩略图
     *
     * @return
     * @throws Exception
     */
    public List<ImgFileBean> uploadFileOrImg(MultipartFile[] picList,
                                             String path, String uploadFileDomain, String uploadFileName,
                                             boolean zipThumb) throws Exception {

        List<ImgFileBean> attachmentImgFileBeanList = new ArrayList<>();
        if (picList == null) return null;
        for (int i = 0; i < picList.length; i++) {
            ImgFileBean imgFileBean = new ImgFileBean();


            MultipartFile fileitem = picList[i];
            String fileType = fileitem.getContentType();//文件类型
            Long fileSize = fileitem.getSize();//文件长度 单位：字节
            String fileNameReal = fileitem.getOriginalFilename();//文件名字
            String suffixName = fileNameReal.substring(fileNameReal.lastIndexOf("."));//文件类型

            imgFileBean.setFileType(fileType);
            imgFileBean.setFileSize(fileSize);
            if (!AppStatic.image_Type.contains(fileType) && !AppStatic.office_file_Type.contains(fileType)) {
                //不做处理
                System.out.println(fileType);
                System.out.println("不做处理");
                continue;
            } else {
                String todayYYYYMMDDString = DateUtil.getDateTimeFormatyyyymmdd(new Date());
                String targetDir_temp = path + File.separator +uploadFileName+ File.separator+ todayYYYYMMDDString;//    d://project/uploadimg/20171127/12
                String targetDir = ImageFileUtil.createDir(targetDir_temp);//存储的文件夹
                String fileName = ImageFileUtil.uploadFile(fileitem, suffixName, targetDir);
                imgFileBean.setFileName(fileNameReal);//此处填写真实名字
                if (AppStatic.image_Type.contains(fileType) && zipThumb) {//图片类型 需要压缩下
                    //压缩图片 并获取压缩图片名
                    File temp = new File(targetDir + File.separator + fileName);
                    String thumb_fileName = ImageFileUtil.createThumbPic(temp, targetDir);//压缩图片名
                    //**  20171127/12/201344.jpg **//*
                    String src = uploadFileDomain + "/" +uploadFileName + "/" +todayYYYYMMDDString + "/" + fileName;
                    //**  20171127/12/201344_thumb.jpg **//*
                    String thumb_src = uploadFileDomain + "/" +uploadFileName + "/" +todayYYYYMMDDString + "/" + thumb_fileName;
                    imgFileBean.setFileSrc(src);
                    imgFileBean.setThumb_fileSrc(thumb_src);

                } else {//文件类型 不需要压缩
                    String src = uploadFileDomain + "/" +uploadFileName + "/" +todayYYYYMMDDString + "/" + fileName;
                    imgFileBean.setFileSrc(src);

                }
                imgFileBean.setFileType(fileType);
                attachmentImgFileBeanList.add(imgFileBean);
            }
        }
        return attachmentImgFileBeanList;
    }
}
