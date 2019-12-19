package com.hinmu.lims.util.common;

import com.hinmu.lims.config.paramconfig.SystemParamConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @program: AI_CASE_MS
 * @ClassName FFmpegUtils
 * @description: 视频转换
 * @author: yhn
 * @create: 2019-12-07 11:22
 * @Version 1.0
 **/
@Component
public class ConvertVideo{





    //ffmpeg -i demo.mp4 -c copy -an demp_enc.mp4
    public static void convetor(String videoInputPath, String videoOutPath) throws Exception {
            List<String> command = new ArrayList<String>();
            command.add(SystemParamConfig.getFFMPEGExePath());
            command.add("-i");
            command.add(videoInputPath);
            command.add("-c");
            command.add("copy");
            command.add("-an");
            command.add(videoOutPath);
            ProcessBuilder builder = new ProcessBuilder(command);
            Process process = null;
            process = builder.start();

            // 使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
            InputStream errorStream = process.getErrorStream();
            InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = br.readLine()) != null) {
            }
            if (br != null) {
                br.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (errorStream != null) {
                errorStream.close();
            }
    }

    public static String generateImage(String imgData,String filePath) throws IOException { // 对字节数组字符串进行Base64解码并生成图片
        // 图像数据为空
        if (StringUtils.isEmpty(imgData)){
            return null;
        }
        String replaceAll = UUID.randomUUID().toString().replaceAll("-", "");
        String fileName = System.currentTimeMillis() + replaceAll + ".jpg";

        imgData = imgData.replace("data:image/jpeg;base64,","");
        byte[] bytes = base64StringToImage(imgData);

        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        BufferedImage bi1 =ImageIO.read(bais);
        //可以是jpg,png,gif格式
        String pathAndFileName = filePath + fileName;

        File w2 = new File(pathAndFileName);
        //不管输出什么格式图片，此处不需改动
        ImageIO.write(bi1, "jpg", w2);
        return pathAndFileName;
    }

    public static void main(String[] args) throws IOException {
        String readFileContent = readFileContent("C:\\Users\\yhn\\Desktop\\base64.txt");

        byte[] bytes = base64StringToImage(readFileContent);

        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        BufferedImage bi1 =ImageIO.read(bais);
        //可以是jpg,png,gif格式
        File w2 = new File("d:\\66.jpg");
        //不管输出什么格式图片，此处不需改动
        ImageIO.write(bi1, "jpg", w2);
    }

    static byte[]  base64StringToImage(String base64String) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();//base64转为二进制
        String imgBase64=base64String;
        byte[] date_blob = decoder.decodeBuffer(imgBase64);
        for (int i = 0; i < date_blob.length; ++i) {
            if (date_blob[i] < 0) {
                date_blob[i] += 256;
            }
        }
        return date_blob;
    }
    public static String readFileContent(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }

}
