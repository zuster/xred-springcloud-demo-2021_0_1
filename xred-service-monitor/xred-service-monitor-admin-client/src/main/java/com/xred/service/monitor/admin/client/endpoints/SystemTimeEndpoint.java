package com.xred.service.monitor.admin.client.endpoints;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义端点 - 获取服务器时间戳</br>
 * // @Endpoint 表示这是一个自定义时间端点类</br>
 * // Endpoint 中有一个 id，它是端点的唯一标识和访问路径，必须唯一不重复，也不能与系统自带的重复，
 *
 * @author songyh
 * @date 2022/5/27 11:10 PM
 */
@Endpoint(id = "systemTime")
@Component
public class SystemTimeEndpoint {
    private String format = "yyyy-MM-dd HH:mm:ss";

    /**
     * 显示端点指标
     * @return
     */
    @ReadOperation
    public Map<String, Object> info(){
        Map map = new HashMap();
        map.put("Author","XRed");
        map.put("Desc","自定义端点 - 获取服务器时间戳");
        map.put("SystemTime",new SimpleDateFormat(format).format(new Date()));
        return map;
    }

    /**
     * 动态修改指标，以 POST 方式提交
     * @param format
     */
    @WriteOperation
    public void  setFormat(String format){
        this.format = format;
    }
}
