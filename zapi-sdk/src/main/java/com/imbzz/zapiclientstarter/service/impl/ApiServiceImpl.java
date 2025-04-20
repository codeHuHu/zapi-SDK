package com.imbzz.zapiclientstarter.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.imbzz.zapiclientstarter.client.ZApiClient;
import com.imbzz.zapiclientstarter.exception.ApiException;
import com.imbzz.zapiclientstarter.exception.ErrorCode;
import com.imbzz.zapiclientstarter.exception.ErrorResponse;
import com.imbzz.zapiclientstarter.request.*;
import com.imbzz.zapiclientstarter.response.ResultResponse;
import com.imbzz.zapiclientstarter.service.ApiService;
import com.imbzz.zapiclientstarter.utils.SignUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author imbzz
 * @Date 2024/5/7 22:21
 */
@Data
@Slf4j
public class ApiServiceImpl implements ApiService {

    /**
     * sdk对象
     */
    private ZApiClient zApiClient;

    /**
     * 网关HOST
     */
    private String gatewayHost ;



    /**
     * 通用转发
     * @param request
     * @return
     * @param <O>
     * @param <T>
     * @throws ApiException
     */
    @Override
    public <O, T extends ResultResponse> T request(BaseRequest<O, T> request) throws ApiException {
        try {
            return res(request);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.OPERATION_ERROR, e.getMessage());
        }
    }

    /**
     * 统一转发核心方法
     * @param request
     * @return
     * @param <T>
     * @param <O>
     */
    private <T extends ResultResponse, O> T res(BaseRequest<O, T> request) throws ApiException {
        //校验client
        if(this.zApiClient == null || StringUtils.isAnyBlank(this.zApiClient.getAccessKey(),this.zApiClient.getSecretKey()) ){
            throw new ApiException(ErrorCode.PARAMS_ERROR,"未配置AccessKey/SecretKey");
        }
        //获取返回对象
        T rsp;
        try {
            Class<T> clazz = request.getResponseClass();
            rsp = clazz.newInstance();
        } catch (Exception e) {
           throw new ApiException(ErrorCode.OPERATION_ERROR,e.getMessage());
        }
        //doRequest
        HttpResponse httpResponse = doRequest(request);
        String body = httpResponse.body();
        //hashMap的data
        Map<String,Object> data = new HashMap<>();
        //如果不为200
        //获取响应，失败处理结果
        //成功解析成Json返回，否则直接返回字符串
        //todo 测
        if(httpResponse.getStatus() != 200){
            ErrorResponse errorResponse = JSONUtil.toBean(body, ErrorResponse.class);
            data.put("errorMessage",errorResponse.getMessage());
            data.put("code",errorResponse.getCode());
        }else {
            try {
                //调用成功，结果只含data
                data = new Gson().fromJson(body, new TypeToken<Map<String, Object>>() {
                }.getType());
            } catch (JsonSyntaxException e) {
                data.put("value", body);
            }
        }
        rsp.setData(data);
        return rsp;

    }

    private <T extends ResultResponse, O> HttpResponse doRequest(BaseRequest<O, T> request) throws ApiException {
        //获取执行请求对象并执行
        try(HttpResponse httpResponse= getHttpRequestByRequestMethod(request).execute()){
            //返回结果
            return httpResponse;
        }catch (Exception e){
            throw new ApiException(ErrorCode.OPERATION_ERROR,e.getMessage());
        }

    }

    /**
     * 根据请求方法获取执行请求对象
     * @param request
     * @return
     * @param <O>
     * @param <T>
     */
    private <O, T extends ResultResponse> HttpRequest getHttpRequestByRequestMethod(BaseRequest<O, T> request) throws ApiException {
        if (ObjectUtils.isEmpty(request)) {
            throw new ApiException(ErrorCode.OPERATION_ERROR, "请求参数错误");
        }
        String path = request.getPath().trim();
        String method = request.getMethod().trim().toUpperCase();

        if (ObjectUtils.isEmpty(method)) {
            throw new ApiException(ErrorCode.OPERATION_ERROR, "请求方法不存在");
        }
        if (StringUtils.isBlank(path)) {
            throw new ApiException(ErrorCode.OPERATION_ERROR, "请求路径不存在");
        }
        //todo 待理解为什么这样取路径
//        if (path.startsWith(gatewayHost)) {
//            path = path.substring(gatewayHost.length());
//        }
        log.info("请求方法：{}，请求路径：{}，请求参数：{}", method, path, request.getRequestParams());
        HttpRequest httpRequest;
        switch (method) {
            case "GET": {
                httpRequest = HttpRequest.get(splicingGetRequest(request, path));
                break;
            }
            case "POST": {
                httpRequest = HttpRequest.post(gatewayHost + path);
                break;
            }
            default: {
                throw new ApiException(ErrorCode.OPERATION_ERROR, "不支持该请求");
            }
        }
        return httpRequest.addHeaders(getHeaders(JSONUtil.toJsonStr(request), zApiClient)).body(JSONUtil.toJsonStr(request.getRequestParams()));
    }

    /**
     * 获取请求头
     *
     * @param body        请求体
     * @param zApiClient qi api客户端
     * @return {@link Map}<{@link String}, {@link String}>
     */
    private Map<String, String> getHeaders(String body, ZApiClient zApiClient) {
        Map<String, String> hashMap = new HashMap<>(4);
        hashMap.put("accessKey", zApiClient.getAccessKey());
        hashMap.put("nonce", RandomUtil.randomNumbers(5));
        String encodedBody = SecureUtil.md5(body);
        hashMap.put("body", encodedBody);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", SignUtils.getSign(encodedBody, zApiClient.getSecretKey()));
        return hashMap;
    }

    /**
     * 拼接get方法
     * @param request
     * @param path
     * @return
     * @param <T>
     * @param <O>
     */
    private <T extends ResultResponse, O> String splicingGetRequest(BaseRequest<O, T> request, String path) {
        StringBuilder urlBuilder = new StringBuilder(gatewayHost);
        // urlBuilder最后是/结尾且path以/开头的情况下，去掉urlBuilder结尾的/
        if (urlBuilder.toString().endsWith("/") && path.startsWith("/")) {
            urlBuilder.setLength(urlBuilder.length() - 1);
        }
        urlBuilder.append(path);
        if (!request.getRequestParams().isEmpty()) {
            urlBuilder.append("?");
            for (Map.Entry<String, Object> entry : request.getRequestParams().entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue().toString();
                urlBuilder.append(key).append("=").append(value).append("&");
            }
            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        }
        log.info("GET请求路径：{}", urlBuilder);
        return urlBuilder.toString();
    }


    @Override
    public <O, T extends ResultResponse> T request(ZApiClient zApiClient, BaseRequest<O, T> request) throws ApiException {
        checkConfig(zApiClient);
        return request(request);
    }

    @Override
    public ResultResponse getUserNameByPost(NameRequest NameRequest) throws ApiException{
        return request(NameRequest);
    }

    @Override
    public ResultResponse getUserNameByPost(ZApiClient zApiClient ,NameRequest NameRequest) throws ApiException{
        return request(zApiClient,NameRequest);
    }

    public ResultResponse getIpInfo(ZApiClient zApiClient, IpInfoRequest request) throws ApiException {
        return this.request(zApiClient, request);
    }

    public ResultResponse getIpInfo(IpInfoRequest request) throws ApiException {
        return this.request(request);
    }

    @Override
    public ResultResponse excelFileAnalysisSyncMq(ZApiClient zApiClient,BIChartByAIRequestRequest biChartByAIRequestRequest) throws ApiException {
        return this.request(zApiClient, biChartByAIRequestRequest);
    }

    @Override
    public ResultResponse excelFileAnalysisSyncMq(BIChartByAIRequestRequest biChartByAIRequestRequest) throws ApiException {
        return this.request( biChartByAIRequestRequest);
    }

    public ResultResponse getWBHotList() throws ApiException {
        WBHotListRequest request = new WBHotListRequest();
        return this.request(request);
    }

    public ResultResponse getWBHotList(ZApiClient zApiClient) throws ApiException {
        WBHotListRequest request = new WBHotListRequest();
        return this.request(zApiClient, request);
    }


    public ResultResponse getWeather(WeatherRequestRequest weatherRequestRequest) throws ApiException {
        return this.request(weatherRequestRequest);
    }

    public ResultResponse getWeather(ZApiClient zApiClient,WeatherRequestRequest weatherRequestRequest) throws ApiException {
        return this.request(zApiClient, weatherRequestRequest);
    }

    public ResultResponse getQRcode(ZApiClient zApiClient, QRcodeRequest request) throws ApiException {
        return this.request(zApiClient, request);
    }

    public ResultResponse getQRcode(QRcodeRequest request) throws ApiException {
        return this.request(request);
    }
    /**
     * 检测配置
     * @param zApiClient
     */
    private void checkConfig(ZApiClient zApiClient) throws ApiException{
        if(zApiClient == null &&  this.zApiClient == null){
            throw new ApiException(ErrorCode.PARAMS_ERROR,"未配置AccessKey/SecretKey");
        }
        if(zApiClient != null && !StringUtils.isAnyBlank(zApiClient.getAccessKey(),zApiClient.getSecretKey())){
            this.setZApiClient(zApiClient);
        }
    }



}
