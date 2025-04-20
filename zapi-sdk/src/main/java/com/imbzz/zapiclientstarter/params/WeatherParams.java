package com.imbzz.zapiclientstarter.params;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @author imbzz
 * @Date 2024/2/28 23:31
 */
@Data
public class WeatherParams implements Serializable {
    /**
     * 城市名
     */
    private String city;

    /**
     * ip
     */
    private String ip;

}
