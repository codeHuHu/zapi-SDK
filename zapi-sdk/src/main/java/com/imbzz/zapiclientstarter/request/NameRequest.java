package com.imbzz.zapiclientstarter.request;

import com.imbzz.zapiclientstarter.model.enums.RequestMethodEnum;
import com.imbzz.zapiclientstarter.params.NameParams;
import com.imbzz.zapiclientstarter.response.NameResponse;
import lombok.experimental.Accessors;

/**
 * @author zengxh
 * @Date 2024/7/16 23:15
 */
@Accessors(chain = true)
public class NameRequest extends BaseRequest<NameParams, NameResponse> {
    @Override
    public String getMethod() {
        return RequestMethodEnum.POST.getText();
    }

    @Override
    public String getPath() {
        return "/apiInterface/nameList";
    }

    @Override
    public Class<NameResponse> getResponseClass() {
        return NameResponse.class;
    }
}
