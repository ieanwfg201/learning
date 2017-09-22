package com.hbhs.learning.tools.common;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by walter on 17-7-7.
 */
public class GenerateWin {

    public static void main(String[] args) throws Exception{
        GenerateWin win = new GenerateWin();
        System.out.println("#### WIN - 2017-07-05");
        win.generateWin("/home/walter/Desktop/11/win.0705.69.log", 800);

        System.out.println();
        System.out.println("#### WIN - 2017-07-06");
        win.generateWin("/home/walter/Desktop/11/win.0706.69.log", 800);

        System.out.println();
        System.out.println("#### RENDER - 2017-07-06");
        win.generateRender("/home/walter/Desktop/11/render.0705.69.log", 800);

        System.out.println();
        System.out.println("#### RENDER - 2017-07-06");
        win.generateRender("/home/walter/Desktop/11/render.0706.69.log", 800);


    }

    public void generateRender(String path, double max) throws Exception {
        double auction_price_total = 0.0;
        double bidprice_to_exchange_total =0.0;
        double internal_max_bid_total=0.0;
        double advertiser_bid_total=0.0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        String line = reader.readLine();

        boolean atPoint = false;
        boolean alreadyPrint = false;
        String timestamp = "";
        int totalCount = 0;

        while(line!=null){
            if (line.contains("internal_max_bid")
                    &&line.contains("advertiser_bid")
                    &&line.contains("HEALTHY_REQUEST")){
                int index = line.indexOf("internal_max_bid");
                internal_max_bid_total += praseDouble(line.substring(index+"internal_max_bid".length()+1,
                        line.indexOf(",", index)))/1000;

                index = line.indexOf("advertiser_bid");
                advertiser_bid_total += praseDouble(line.substring(index+"advertiser_bid".length()+1,
                        line.indexOf(",", index)))/1000;
                index = line.indexOf("eventTime");
                timestamp = line.substring(index+"eventTime".length()+1, line.indexOf(",", index));
            }
            line = reader.readLine();

            if (auction_price_total>max||
                    bidprice_to_exchange_total>max||
                    internal_max_bid_total>max||
                    advertiser_bid_total>max){
                atPoint = true;
            }
            if (atPoint&&!alreadyPrint){
                System.out.println(line);
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(Long.valueOf(timestamp.trim())*1000);
                System.out.println(timestamp+ ": "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss s").format(cal.getTime())+
                "  At record: "+totalCount);
                alreadyPrint = true;
            }
            totalCount++;
        }
        System.out.println("Total count: "+ totalCount);
        System.out.println("auction_price_total: "+auction_price_total);
        System.out.println("bidprice_to_exchange_total: "+bidprice_to_exchange_total);
        System.out.println("internal_max_bid_total: "+internal_max_bid_total);
        System.out.println("advertiser_bid_total: "+advertiser_bid_total);
        reader.close();
    }

    public void generateWin(String path, double max) throws Exception {
        double auction_price_total = 0.0;
        double bidprice_to_exchange_total =0.0;
        double internal_max_bid_total=0.0;
        double advertiser_bid_total=0.0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        String line = reader.readLine();
        int totalCount = 0;
        boolean atPoint = false;
        boolean alreadyPrint = false;
        String timestamp = "";

        while(line!=null){
            if (line.contains("auction_price")
                    &&line.contains("bidprice_to_exchange")
                    &&line.contains("internal_max_bid")
                    &&line.contains("advertiser_bid")
                    &&line.contains("HEALTHY_REQUEST")){
                int index = line.indexOf("auction_price");
                auction_price_total += praseDouble(line.substring(index+"auction_price".length()+1,
                        line.indexOf(",", index)))/1000;

                index = line.indexOf("bidprice_to_exchange");
                bidprice_to_exchange_total += praseDouble(line.substring(index+"bidprice_to_exchange".length()+1,
                        line.indexOf(",", index)))/1000;

                index = line.indexOf("internal_max_bid");
                internal_max_bid_total += praseDouble(line.substring(index+"internal_max_bid".length()+1,
                        line.indexOf(",", index)))/1000;

                index = line.indexOf("advertiser_bid");
                advertiser_bid_total += praseDouble(line.substring(index+"advertiser_bid".length()+1,
                        line.indexOf(",", index)))/1000;
                index = line.indexOf("eventTime");
                timestamp = line.substring(index+"eventTime".length()+1, line.indexOf(",", index));
            }
            line = reader.readLine();

            if (auction_price_total>max||
                    bidprice_to_exchange_total>max||
                    internal_max_bid_total>max||
                    advertiser_bid_total>max){
                atPoint = true;
            }
            if (atPoint&&!alreadyPrint){
                System.out.println(line);
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(Long.valueOf(timestamp.trim())*1000);
                System.out.println(timestamp+ ": "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss s").format(cal.getTime())+
                " At record: "+totalCount);
                alreadyPrint = true;
            }
            totalCount++;
        }
        System.out.println("Total count: "+ totalCount);
        System.out.println("auction_price_total: "+auction_price_total);
        System.out.println("bidprice_to_exchange_total: "+bidprice_to_exchange_total);
        System.out.println("internal_max_bid_total: "+internal_max_bid_total);
        System.out.println("advertiser_bid_total: "+advertiser_bid_total);
        reader.close();
    }

    private double praseDouble(String str){
        try {
            return Double.valueOf(str.toString());
        }catch (Exception e){

        }
        return 0.0;
    }
}
