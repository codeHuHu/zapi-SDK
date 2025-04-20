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
public class ExcelFileAnalysisParams implements Serializable {
    /**
     * 名称
     */
    private String name;

    /**
     * 分析目标
     */
    private String goal;

    /**
     * 图表类型
     */
    private String chartType;

    /**
     * 需要解析的文件
     */
    @JsonIgnore
    private MultipartFile file;
}
