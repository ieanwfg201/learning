package com.hbhs.learning.tools.common;

import org.junit.Test;
import sun.rmi.log.LogInputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by walter on 17-6-7.
 */
public class GenerateSQL {
    private final static String CAMPAIGN_ID = "CAMPAIGN_ID";
    private final static String CHANNEL_ID = "CHANNEL_ID";
    private final static String CONVERSION_ID = "CONVERSION_ID";
    private final static String IDFA_ID = "IDFA_ID";
    private final static String REF_ID = "REF_ID";
    private final static String GUID_ID = "GUID_ID";
    String str = "'c0Gl16gkcGTVaddg', '00349', 'MA4g32GBhD9gk0C9', 'b5d51d24-b858-4843-8bed-40f0ec963d06', 'sB0kH31aDNYnv0t0Fh95Zm010i4wN0', 'b5d51d24-b858-4843-8bed-40f0ec963d06'\n" +
            "'c0Gl16gkcGTVaddg', '00349', 'MA4g32GBhD9gk0C9', '74a7394a-4099-44b7-b53f-f3ece409cef9', 'Jywik001i30Z0kH19BH5opD50XYqX0', '74a7394a-4099-44b7-b53f-f3ece409cef9'\n" +
            "'c0Gl16gkcGTVaddg', '00349', 'MA4g32GBhD9gk0C9', '1f7fe771-d06d-46ac-9c33-ab2a98ab6c6d', 'A10xp01wi00YwHXZDviq2i5k9PU0U0', '1f7fe771-d06d-46ac-9c33-ab2a98ab6c6d'\n" +
            "'c0Gl16gkcGTVaddg', '00349', 'MA4g32GBhD9gk0C9', 'a1efe336-4bf3-4a43-8e5f-7714df579e2e', 'E050DwJ15Z09hw500kNmHUvDiY10mg', 'a1efe336-4bf3-4a43-8e5f-7714df579e2e'\n" +
            "'c0Gl16gkcGTVaddg', '00349', 'MA4g32GBhD9gk0C9', '9d1cd860-d3ce-4ef6-9905-c3b5e00786ca', '00DcU5NH0i02Y0Zkm91bB0T0w1NZ6h', '9d1cd860-d3ce-4ef6-9905-c3b5e00786ca'\n" +
            "'c0Gl16gkcGTVaddg', '00349', 'MA4g32GBhD9gk0C9', '37b939ac-4849-496e-8d14-7ffbe7a6171c', 'O1euS0w100k69w5iYHC0O0h8zZDqk0', '37b939ac-4849-496e-8d14-7ffbe7a6171c'\n" +
            "'c0Gl16gkcGTVaddg', '00349', 'MA4g32GBhD9gk0C9', '722734fa-7cd4-439a-9585-bef1e60e9f68', 'Li00IFkw11mHy9Y3Z5wuODh0I50k00', '722734fa-7cd4-439a-9585-bef1e60e9f68'\n" +
            "'c0Gl16gkcGTVaddg', '00349', 'MA4g32GBhD9gk0C9', '5828f7b5-b3b5-4f77-b328-547d67ba960d', 'iik0y60lH4h9w0F0ZOD1Ygkd50TJ10', '5828f7b5-b3b5-4f77-b328-547d67ba960d'\n" +
            "'c0Gl16gkcGTVaddg', '00349', 'MA4g32GBhD9gk0C9', '7e62de99-18f4-4db0-ac59-381b91fcf256', 'mx5ZY000i2Wk0hOnsBD0Y9m810xH1k', '7e62de99-18f4-4db0-ac59-381b91fcf256'\n" +
            "'c0Gl16gkcGTVaddg', '00349', 'MA4g32GBhD9gk0C9', '2a3c85c0-64b7-45ee-b025-1a3ff26e388f', 'XA09XRk100a10k05KiHx0hYaDZv0O5', '2a3c85c0-64b7-45ee-b025-1a3ff26e388f'\n" +
            "'c0Gl16gkcGTVaddg', '00349', 'MA4g32GBhD9gk0C9', '17886bbe-a6cb-4ed3-a875-66568a8990ab', 'B10H009mid05pycX0PZDi0OXEk31xY', '17886bbe-a6cb-4ed3-a875-66568a8990ab'\n" +
            "'c0Gl16gkcGTVaddg', '00349', 'MA4g32GBhD9gk0C9', '359487cf-39bc-4af2-b36d-ad7141a97f93', 'mx5ZY000i9K20jkFbvD009Y010SH1k', '359487cf-39bc-4af2-b36d-ad7141a97f93'\n" +
            "'c0Gl16gkcGTVaddg', '00349', 'MA4g32GBhD9gk0C9', 'ebf73383-b951-4b18-b207-32ed15077d2c', 'UX0Dv0nJk5i1a1CYWYg0T00UHZ0x9h', 'ebf73383-b951-4b18-b207-32ed15077d2c'\n" +
            "'c0Gl16gkcGTVaddg', '00349', 'MA4g32GBhD9gk0C9', '4835a506-faf1-459e-9084-01082c4d6391', 'T50XZ00S21Kik0ok0jYHi0LN98Vx1D', '4835a506-faf1-459e-9084-01082c4d6391'\n" +
            "'c0Gl16gkcGTVaddg', '00349', 'MA4g32GBhD9gk0C9', 'b5f026ea-6d30-4852-ad8e-573754e63c54', 'eOA91C5hzH0k09lN00v10YkSZxDQi0', 'b5f026ea-6d30-4852-ad8e-573754e63c54'\n" +
            "'c0Gl16gkcGTVaddg', '00349', 'MA4g32GBhD9gk0C9', '7a7c532f-aaec-433a-a1bf-88476c783410', 'ptZ0YY19EC51000FS0HiDRgHxh0k6V', '7a7c532f-aaec-433a-a1bf-88476c783410'\n" +
            "'c0Gl16gkcGTVaddg', '00349', 'MA4g32GBhD9gk0C9', '', 'Dki13q0HxAY6Q15900ZJDFUs00io0b', '1496828543429-6106723366546657369'\n" +
            "'c0Gl16gkcGTVaddg', '00349', 'MA4g32GBhD9gk0C9', '6b5cdee6-86f5-484e-9ab7-1919cd1a756c', 'w0kC05DWz9AY00Y9H1D1ZSYi0Zh0px', '6b5cdee6-86f5-484e-9ab7-1919cd1a756c'\n" +
            "'c0Gl16gkcGTVaddg', '00349', 'MA4g32GBhD9gk0C9', 'b95ff79d-b4a0-4c45-8ff2-5126695fb9c6', 'rMx8Y20H0jr1D00l0Y021k5kK0i6Z9', 'b95ff79d-b4a0-4c45-8ff2-5126695fb9c6'\n" +
            "'c0Gl16gkcGTVaddg', '00349', 'MA4g32GBhD9gk0C9', '943476dc-1b4c-42cb-9855-dc278fb265ca', 'R4Zi4IL1YDiY1L000k0Hq1i905b0x9', '943476dc-1b4c-42cb-9855-dc278fb265ca'\n";

    @Test
    public void testGe(){
        List<String[]> list = new ArrayList<>();
        String[] lines = str.split("\n");
        for (String line : lines) {
            String[] fields = line.replaceAll("'","").replaceAll(" ","").split(",");
            list.add(fields);
        }

        List<String> addres = new ArrayList<>();
        for (String[] strs : list) {
            StringBuilder str = new StringBuilder();
            str.append("INSERT INTO call_postbacklog(campaignId, conversionId, deviceId,parameter,createTime, isPostBackAll,guid,channel_price,advertiser_price)\n");
            str.append("VALUES(")
                    .append("'").append(strs[0]).append("'")
                    .append(",'").append(strs[2]).append("'")
                    .append(",'{").append(strs[3].equals("")?"":"idfa="+strs[3]).append("}'")
                    .append(",'{ref_id=").append(strs[4]).append("}'")
                    .append(",").append("NOW()").append("")
                    .append(",").append("0").append("")
                    .append(",'").append(strs[5]).append("'")
                    .append(",").append("0.0").append("")
                    .append(",").append("0.0").append("")
                    .append(");\n");

            str.append("insert into callbacklog(createTime,channelID,logType,act_url,act_params,responseCode,responseBody,campaignId,call_postbacklog_id)\n")
                    ;
            str.append("VALUES(")
                    .append("NOW()")
                    .append(",'").append(strs[1]).append("'")
                    .append(",'Info'")
                    .append(",'http://boomclick.offerstrack.net/advBack.php'")
                    .append(",'click_id=").append(strs[4]).append("'")
                    .append(",200")
                    .append(",'success!'")
                    .append(",'").append(strs[0]).append("'")
                    .append(",(SELECT id from call_postbacklog where campaignId='"+strs[0]+"' and guid='"+strs[5]+"')")
                    .append(");\n");

            addres.add("http://boomclick.offerstrack.net/advBack.php?click_id="+strs[4]);

            System.out.println(str);
        }


        for (String addre : addres) {
            System.out.println(addre);
        }
    }
}
