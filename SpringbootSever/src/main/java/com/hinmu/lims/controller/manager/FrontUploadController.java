package com.hinmu.lims.controller.manager;



import com.hinmu.lims.config.paramconfig.SystemParamConfig;
import com.hinmu.lims.controller.BaseController;
import com.hinmu.lims.exception.ApiClientException;
import com.hinmu.lims.model.respbean.ImgFileBean;
import com.hinmu.lims.model.respbean.RespResultMessage;
import com.hinmu.lims.util.upload.UploadImgUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 上传图片和文件
 * Created by hao on 2018/8/14.
 */
@RestController
@RequestMapping("/api/admin/upload")
public class FrontUploadController extends BaseController {



    /**
     * 图片上传
     */
    @PostMapping("/mediafile")
    @ResponseBody
//    @MethodLog(remarks = "文件上传")
    public RespResultMessage uploadImg(HttpServletRequest request, MultipartFile[] file) throws Exception {
        RespResultMessage respResultMessage=new RespResultMessage();
        if(file!=null && file.length> Integer.parseInt(SystemParamConfig.getFileMaxSize())){
            throw new ApiClientException( "上传图片文件数超过最大限制"+ SystemParamConfig.getFileMaxSize()+"个");
        }
        if (SystemParamConfig.getStaticFileUrl() == null) {
            throw new ApiClientException("静态访问路径未配置");
        }
//        String uploadFileDomain = "http://"+properConfig.getServerIPPortContext();
        List<ImgFileBean> imgFileBeanList = new UploadImgUtil().uploadFileOrImg(file, SystemParamConfig.getUploadFilePath(),
               SystemParamConfig.getStaticFileUrl() , SystemParamConfig.getUploadImageFilePath(),false);
        return respResultMessage.ok(imgFileBeanList);
    }
}
