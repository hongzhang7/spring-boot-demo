package com.app.core.model.vo;


import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * VO 视图基类，重载 toString 方法
 *
 * 数据对象流转形式: Params->DTO->DO->VO
 *
 * VO 层为数据展现层，数据来源于从 DO 层数据
 *
 * 默认需要实现一个从 DO 层的构造方法。
 *
 * 同时实现 Serializable 接口，方便数据进行序列化存储
 *
 * @author xunwu.zy
 * @see com.iwallet.biz.dal.dataobject.BaseDO
 * @see com.alipay.survey.models.dto.BaseDTO
 */
public abstract class BaseVO implements Serializable {

    /**
     * 重载默认的 toString 方法便于后续日志展示
     * @return
     */
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
