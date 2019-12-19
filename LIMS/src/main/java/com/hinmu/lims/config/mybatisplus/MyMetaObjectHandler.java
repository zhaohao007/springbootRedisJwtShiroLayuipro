package com.hinmu.lims.config.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.hinmu.lims.model.Constant;
import com.hinmu.lims.util.common.StringUtil;
import com.hinmu.lims.util.jwt.JwtUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.shiro.SecurityUtils;

import java.time.LocalDateTime;


/**
 * 自定义填充处理器
 */
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        //this.setFieldValByName("when_created", LocalDateTime.now(), metaObject);
        Object whenCreated = metaObject.getValue("whenCreated");
        if (whenCreated == null)
            this.setFieldValByName("whenCreated", LocalDateTime.now(), metaObject);
//            this.setFieldValByName("when_created", LocalDateTime.now(), metaObject);
        Object whoCreated = metaObject.getValue("whoCreated");
        try {
            if (whoCreated == null) {
                String token = SecurityUtils.getSubject().getPrincipal().toString();
                String userid = JwtUtil.getClaim(token, Constant.USERID);
                if (StringUtil.isNotBlank(userid))
                    this.setFieldValByName("whoCreated", userid, metaObject);
                   // this.setFieldValByName("who_created", sysAdminUser.getId(), metaObject);
            }
        } catch (Exception e) {
        }
    }


    @Override
    public void updateFill(MetaObject metaObject) {
        // 关闭更新填充、这里不执行
        try {
            this.setFieldValByName("whenModified", LocalDateTime.now(), metaObject);
            String token = SecurityUtils.getSubject().getPrincipal().toString();
            String userid = JwtUtil.getClaim(token, Constant.USERID);
            if (StringUtil.isNotBlank(userid))
                this.setFieldValByName("whoModified", userid, metaObject);
        } catch (Exception e) {
        }
    }
}
