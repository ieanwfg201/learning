package com.hbhs.learning.example.string;


import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CheckingStringSame {

    @Test
    public void testAll() throws Exception {
        String billingFilePath = "/home/walter/billing.0426.log";
        Map<String, String> billingType = checkingLine(billingFilePath, "impressionId","CPM");
        String postFilePath = "/home/walter/post.0426.log";
        Map<String, String> postMap = checkingLine(postFilePath,"impressionId", "RENDER");
        Set<String> keySet = new HashSet<>();
        for (String key : postMap.keySet()) {
            if (billingType.containsKey(key)) keySet.add(key);
        }
        System.out.println("######## All not matched post:");
        for (String key : postMap.keySet()) {
            if (!keySet.contains(key)) System.out.println(postMap.get(key));
        }
        System.out.println("Sample: ");
        int index = 0;
        for (String key : postMap.keySet()) {
            if (index==10) break;
            if (keySet.contains(key)) System.out.println(postMap.get(key));
            index++;
        }

        System.out.println("######## All not matched Billing:");
        for (String key : billingType.keySet()) {
            if (!keySet.contains(key)) System.out.println(billingType.get(key));
        }


    }

    public Map<String, String> checkingLine(String filePath, String keyName, String eventType) throws Exception{
        Map<String, String> map = new HashMap<>();
        List<String> lines = Files.readAllLines(new File(filePath).toPath(), Charset.forName("UTF-8"));
        System.out.println("Process total lines: "+ lines.size());
        for (String line : lines) {
            int keyIndexStart = line.indexOf(keyName);
            if (keyIndexStart>0&&line.indexOf(eventType)>0){
                int keyIndexEnd = line.indexOf(",", keyIndexStart);
                String key = line.substring(keyIndexStart+keyName.length(), keyIndexEnd);
                map.put(key, line);
            }
        }
        System.out.println("Process after total lines: "+ map.size());
        return map;
    }

    @Test
    public void testGroupByTime() throws Exception{
        String billingFilePath = "/home/walter/test.log";
        List<String> lines = Files.readAllLines(new File(billingFilePath).toPath(), Charset.forName("UTF-8"));
        Map<String, Integer> dateCount = new TreeMap<>();
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (String line : lines) {
            int start = line.indexOf("time");
            if (start>0&&line.indexOf("RENDER")>0){
                start +="time".length()+1;
                Long second = Long.valueOf(line.substring(start, line.indexOf(",", start)));
                cal.setTimeInMillis(second*1000);
                String timeStr = df.format(cal.getTime());
                Integer count = dateCount.get(timeStr);
                if (count==null) count=0;
                dateCount.put(timeStr, ++count);
            }
        }
        for (String key : dateCount.keySet()) {
            System.out.println(key+": "+dateCount.get(key));
        }
    }

    @Test
    public void testString(){
        String line1 = "168## PostImpressionRequestResponse(version:1, requestId:01525400-f187-6401-5ba9-c7623c92f0b4, event:RENDER, status:HEALTHY_REQUEST, srcRequestId:01525400-15e7-a801-5ba9-c761c3a87523, impressionId:01525400-15e7-a801-5ba9-c761c3a87523:7d, adId:125, campaignId:53, srcUrlHash:6f7c2b6cb744e99657361447d977661, siteId:34, countryId:1, deviceId:-1, deviceManufacturerId:344, deviceModelId:6190, deviceOsId:1, userAgent:Apache-HttpClient/4.5.2 (Java/1.8.0_73), ipAddress:180.166.211.58, xForwardedFor:180.166.211.58, urlExtraParameters:{iid=AAAAAAAAAAAAAAAAAAABvAAABREAAjcPdGVzdFFBX2RldmljZV8xMhNpZmE6dGVzdFFBX2RldmljZV8xMw50ZXN0UUFfYXBwaWRfMQ, kid=ifa:testQA_device_1}, time:1493202068, eventTime:1493202067, auction_currency:CNY, exchangeId:120, countryCarrierId:-1, selectedSiteCategoryId:-1, urlVersion:1, inventorySource:2, internal_max_bid:0.5, advertiser_bid:1.0, bidderModelId:-1, marketplace_id:2, slotId:41, deviceBrowserId:-1, cpa_goal:0.0, supply_source_type:3, ext_supply_attr_internal_id:8668, connectionTypeId:2, adv_inc_id:102, pub_inc_id:120, deviceType:0, bidFloor:0.0, kritterUserId:ifa:testQA_device_1, externalSiteAppId:testQA_appid_1, dpidmd5:testQA_device_1, stateId:0, cityId:0, adpositionId:444, channelId:1297)";
        String line2 = "23## PostImpressionRequestResponse(version:1, requestId:01525400-f187-6401-5ba9-d6db27934d88, event:RENDER, status:HEALTHY_REQUEST, srcRequestId:01525400-e734-e601-5ba9-d6dad7b1f493, impressionId:01525400-e734-e601-5ba9-d6dad7b1f493:7d, adId:125, campaignId:53, srcUrlHash:fc8af468e4add5c72ea3738ab314df97, siteId:34, countryId:1, deviceId:-1, deviceManufacturerId:344, deviceModelId:6190, deviceOsId:1, userAgent:Apache-HttpClient/4.5.2 (Java/1.8.0_73), ipAddress:180.166.211.58, xForwardedFor:180.166.211.58, urlExtraParameters:{iid=AAAAAAAAAAAAAAAAAAAB0wAABRIAAjcPdGVzdFFBX2RldmljZV8xMhNpZmE6dGVzdFFBX2RldmljZV8xMw50ZXN0UUFfYXBwaWRfMQ, kid=ifa:testQA_device_1}, time:1493203082, eventTime:1493203081, auction_currency:CNY, exchangeId:120, countryCarrierId:-1, selectedSiteCategoryId:-1, urlVersion:1, inventorySource:2, internal_max_bid:0.5, advertiser_bid:1.0, bidderModelId:-1, marketplace_id:1, slotId:41, deviceBrowserId:-1, cpa_goal:0.0, supply_source_type:3, ext_supply_attr_internal_id:8668, connectionTypeId:2, adv_inc_id:102, pub_inc_id:120, deviceType:0, bidFloor:0.0, kritterUserId:ifa:794F8E82-B64C-4719-83DC-02686A21EFBC, externalSiteAppId:41c3fa72-397a-3305-a812-44dde2783ce7, dpidmd5:testQA_device_1, stateId:25, cityId:299, adpositionId:467, channelId:1298)";
        Map<String, String> map1 = new HashMap<>();
        String[] args = line1.split(",");
        for (String arg : args) {
            String[] keyValue = arg.split(":", 2);
            map1.put(keyValue[0].trim(), keyValue[1].trim());
        }

        args = line2.split(",");
        for (String arg : args) {
            String[] keyValue = arg.split(":", 2);
            String key = keyValue[0].trim(); String value = keyValue[1].trim();
            if (map1.containsKey(key)&&!value.equals(map1.get(key)))
                System.out.println(key+": "+map1.get(key)+",  "+value);
        }
    }
}
