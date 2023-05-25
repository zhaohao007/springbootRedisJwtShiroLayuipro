package com.hinmu.lims.controller;

import com.hinmu.lims.config.message.MessageService;
import com.hinmu.lims.model.respbean.PageResult;
import com.hinmu.lims.model.respbean.RespResultMessage;

public abstract class BaseController {

    /**
     * 渲染失败数据
     *
     * @return result
     */
    protected RespResultMessage renderError(String msg) {
        return new RespResultMessage().internal_server_error500(msg);
    }

    /**
     * 渲染成功数据
     *
     * @return result
     */
    protected RespResultMessage renderSuccess() {
        return new RespResultMessage().ok(null);

    }

    /**
     * 渲染成功数据（带信息）
     *
     * @return result
     */
    protected RespResultMessage renderSuccess(Object data) {
        return new RespResultMessage().ok(data);
    }

    /**
     * 渲染成功数据（带信息）
     *
     * @return result
     */
    protected RespResultMessage renderPageSuccess(PageResult page) {
        return new RespResultMessage().ok(page);
    }
}
