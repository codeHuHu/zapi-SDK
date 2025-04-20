package com.imbzz.zapiclientstarter.service;

import com.imbzz.zapiclientstarter.client.ZApiClient;
import com.imbzz.zapiclientstarter.exception.ApiException;
import com.imbzz.zapiclientstarter.params.ExcelFileAnalysisParams;
import com.imbzz.zapiclientstarter.request.*;
import com.imbzz.zapiclientstarter.response.ResultResponse;

/**
 * @author imbzz
 * @Date 2024/5/7 22:17
 * @version : 1.0
 * @Description: 请求转发sdk接口规范
 */
public interface ApiService {



    <O,T extends ResultResponse> T request(BaseRequest<O,T> request) throws ApiException;

    /**
     *  统用请求
     * @param qiApiClient
     * @param request
     * @return
     * @param <O>
     * @param <T>
     * @throws ApiException
     */
    <O,T extends ResultResponse> T request(ZApiClient qiApiClient, BaseRequest<O,T> request) throws ApiException;


    /**
     * 获取名字
     * @param userName
     * @return
     */
    ResultResponse getUserNameByPost(NameRequest NameRequest) throws ApiException;

    ResultResponse getUserNameByPost(ZApiClient zApiClient,NameRequest NameRequest) throws ApiException;

    /**
     * 获取ip信息
     * @param zApiClient
     * @param request
     * @return
     * @throws ApiException
     */
    ResultResponse getIpInfo(ZApiClient zApiClient, IpInfoRequest request) throws ApiException;

    ResultResponse getIpInfo(IpInfoRequest request) throws ApiException;

    ResultResponse excelFileAnalysisSyncMq(ZApiClient zApiClient, BIChartByAIRequestRequest biChartByAIRequestRequest) throws ApiException;

    ResultResponse excelFileAnalysisSyncMq( BIChartByAIRequestRequest biChartByAIRequestRequest) throws ApiException;

    ResultResponse getWBHotList(ZApiClient zApiClient) throws ApiException;

    ResultResponse getWBHotList() throws ApiException;

    ResultResponse getWeather(ZApiClient zApiClient, WeatherRequestRequest weatherRequestRequest) throws ApiException;

    ResultResponse getWeather(WeatherRequestRequest weatherRequestRequest) throws ApiException;

    ResultResponse getQRcode(ZApiClient zApiClient, QRcodeRequest request) throws ApiException;

    ResultResponse getQRcode(QRcodeRequest request) throws ApiException;

}
