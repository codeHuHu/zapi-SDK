package com.imbzz.zapiclientstarter.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author imbzz
 * @Date 2024/4/29 23:20
 */
@Data
@NoArgsConstructor
public class ResultResponse implements Serializable {
    private static final long serialVersionUID = -6486005224268968744L;

    Map<String , Object> data = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
