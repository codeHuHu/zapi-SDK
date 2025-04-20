package com.imbzz.zapiclientstarter.client;

/**
 * @author imbzz
 * @Date 2023/12/1 15:52
 */
public class ZApiClient {


    private String gatewayHost = "http://localhost:8090";
    private String accessKey;

    private String secretKey;

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }


    public ZApiClient() {
    }

    public ZApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

//    private Map<String,String> getHeaderMap(String body){
//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("accessKey", zApiClient.getAccessKey());
//        //一定不要直接发送秘钥
//        //hashMap.put("secretKey", secretKey);
//        hashMap.put("nonce", RandomUtil.randomNumbers(5));
//        hashMap.put("body",body);
//        hashMap.put("timestamp",String.valueOf(System.currentTimeMillis()/1000));
//        hashMap.put("sign", SignUtils.getSign(body,zApiClient.getSecretKey()));
//        return hashMap;
//    }
//
//    public String getNameByGet(String name) {
//        HashMap<String, Object> paramMap = new HashMap<>();
//        paramMap.put("name", name);
//        String result = HttpUtil.get(gatewayHost + "/api/name/", paramMap);
//        System.out.println(result);
//        return result;
//    }
//
//    public String getNameByPost(String name) {
//        HashMap<String, Object> paramMap = new HashMap<>();
//        paramMap.put("name", name);
//        String result = HttpUtil.post(gatewayHost + "/api/name/", paramMap);
//        System.out.println(result);
//        return result;
//    }
//
//    public String getUserNameByPost(String userName) {
//        Map<String,Object> list = new HashMap<>();
//        list.put("name",userName);
//        String json = JSONUtil.toJsonStr(list);
//        //使用HttpRequest工具发起Post请求，并获取服务器的响应
//        HttpResponse httpResponse = HttpRequest.post(gatewayHost + "/api/apiInterface/nameList")
//                .header("Content-Type", "application/json")
//                .addHeaders(getHeaderMap(json))
//                .body(json)
//                .execute();
//        System.out.println(httpResponse.getStatus());
//        String result = httpResponse.body();
//        System.out.println(result);
//        return result;
//    }


}
