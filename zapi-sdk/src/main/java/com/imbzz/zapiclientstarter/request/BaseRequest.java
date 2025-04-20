package com.imbzz.zapiclientstarter.request;

import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imbzz.zapiclientstarter.client.ZApiClient;
import com.imbzz.zapiclientstarter.enums.RequestMethodEnum;
import com.imbzz.zapiclientstarter.response.ResultResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * @author imbzz
 * @Date 2024/4/29 23:19
 */
public abstract class BaseRequest<O, T extends ResultResponse> {
    /**
     * 参数
     */
    private Map<String,Object> requestParams = new HashMap<>();

    /**
     * 获取方法
     * @return
     * @return {@link RequestMethodEnum}
     */
    public abstract String getMethod();


    /**
     * 获取路径
     * @return {@link String}
     */
    public abstract String getPath();


    /**
     * 获取返回对象
     * @return {@link Class} < {@link T}
     */
    public abstract Class<T> getResponseClass();


    /**
     * 获取参数
     * @return {@link Map}
     */
    @JsonAnyGetter
    public Map<String, Object> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(O params) {
        this.requestParams = new Gson().fromJson(JSONUtil.toJsonStr(params), new TypeToken<Map<String,Object>>(){}.getType());
    }

}
