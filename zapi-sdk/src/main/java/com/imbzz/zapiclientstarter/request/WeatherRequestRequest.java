package com.imbzz.zapiclientstarter.request;

import com.imbzz.zapiclientstarter.enums.RequestMethodEnum;
import com.imbzz.zapiclientstarter.params.ExcelFileAnalysisParams;
import com.imbzz.zapiclientstarter.params.WeatherParams;
import com.imbzz.zapiclientstarter.response.ResultResponse;
import lombok.experimental.Accessors;

/**
 * @Author: QiMu
 * @Date: 2023/09/22 09:04:04
 * @Version: 1.0
 * @Description: 获取ip地址请求
 */
@Accessors(chain = true)
public class WeatherRequestRequest extends BaseRequest<WeatherParams, ResultResponse> {

    @Override
    public String getPath() {
        return "/weather";
    }

    /**
     * 获取响应类
     *
     * @return {@link Class}<{@link ResultResponse}>
     */
    @Override
    public Class<ResultResponse> getResponseClass() {
        return ResultResponse.class;
    }


    @Override
    public String getMethod() {
        return RequestMethodEnum.POST.getValue();
    }
}
