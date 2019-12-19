package com.hinmu.lims.util.upload;

import com.hinmu.lims.util.date.DateUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by hao on 2017/11/28.
 */
public class ImageFileUtil {

    public static String createDir(String targetDir){
        File targetDirFile = new File(targetDir);
        if(!targetDirFile.exists()){
            targetDirFile.mkdirs();
        }
        return targetDir;
    }

    /**
     *  d://project/uploadimg/20171127/12
     * @param id
     * @param path
     * @return
     */
    public static String createDir(Integer id,String path){
        String todayYYYYMMDDstr = DateUtil.getDateFormat(new Date());
        String relati_dir = File.separator+todayYYYYMMDDstr+File.separator+id;// "/20171107/1"
        String targetDir = path+File.separator+todayYYYYMMDDstr+File.separator+id;
        File targetDirFile = new File(targetDir);
        if(!targetDirFile.exists()){
            targetDirFile.mkdirs();
        }
        return targetDir;
    }

    public static String uploadFile(MultipartFile file, String suffixName, String targetDir)
            throws IOException {
        String fileName = new Date().getTime()+suffixName;
        System.out.println(file.getContentType());
        File targetFile = new File(targetDir, fileName);
        if (!targetFile.exists()) {
            targetFile.createNewFile();
        }
        file.transferTo(targetFile);
        return fileName;
    }


    /**
     * 压缩图片 文件名为原图名_sub
     *
     * @param file
     * @param realPath
     * @return
     * @throws IOException
     */
    public static String createThumbPic1(File file,String realPath) throws IOException {
        String path = realPath+File.separator+file.getName().replace(".", "_thumb.");
        String fileName = file.getName();
        File fo = new File(path); // 将要转换出的小图文件
        int nw = 100;
        AffineTransform transform = new AffineTransform();
        BufferedImage buffer = ImageIO.read(file); // 读取图片
        int width = buffer.getWidth();
        int height = buffer.getHeight();
        int nh = (nw * height) / width;
        double sx = (double) nw / width;
        double sy = (double) nh / height;
        transform.setToScale(sx, sy);
        AffineTransformOp ato = new AffineTransformOp(transform, null);
        BufferedImage bid = new BufferedImage(nw, nh, BufferedImage.TYPE_3BYTE_BGR);
        ato.filter(buffer, bid);
        String type = fileName.substring(fileName.indexOf('.') + 1, fileName.length());
        ImageIO.write(bid, type, fo);
        return fo.getName();
    }
    public static String createThumbPic(File file,String realPath) throws IOException {
        try {
            String path = realPath+File.separator+file.getName().replace(".", "_thumb.");
            File fo = new File(path); // 将要转换出的小图文件

            Thumbnails.of(file.getPath()).scale(0.25f).outputQuality(0.5f)
                    .toFile(path);
            return fo.getName();
        }catch (Exception e){//P过的图片保存为jpg格式时，默认的模式是CMYK模式 会报异常
            return file.getName();//此处我们不做缩放，直接原图给出
        }

    }






    /**
     * 保存原图
     * @param base64Code    base64字符串
     * @param targetDirPath  要存储的文件夹路径名
     * @param fileName      存储的文件名
     * @param type         文件后缀类型
     * @return
     */
    public static boolean uploadUserPhotoSave(String base64Code, String targetDirPath,
                                              String fileName, String type) {
        //todo linxu 下改好路径
        String targetPath = targetDirPath + File.separator + fileName;
        //String targetPath = targetDirPath + "\\" + fileName;

        try {
            File dirPathFile = new File(targetDirPath);
            if (!dirPathFile.exists()) dirPathFile.mkdirs();
            File filePath = new File(targetPath);
            if (!filePath.exists()) filePath.createNewFile();
            byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
            FileOutputStream out = new FileOutputStream(targetPath);
            out.write(buffer);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
