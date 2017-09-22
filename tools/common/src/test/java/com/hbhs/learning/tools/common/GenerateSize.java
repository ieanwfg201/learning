package com.hbhs.learning.tools.common;


import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class GenerateSize {
    private static final String demandCharges = "demandCharges";
    private static final String internalDemandCharges = "internalDemandCharges";
    private static final String supplyCost = "supplyCost";
    private static final String exchangepayout = "exchangepayout";
    private static final String eventTime = "eventTime";
    private static final String exchangerevenue = "exchangerevenue";
    public static void main(String[] args) throws Exception {
        GenerateSize generator = new GenerateSize();

        generator.generate("/home/walter/Desktop/billing.69.log", 500);
    }

    public void generate(String path, double max) throws Exception{
        List<String> lines = Files.readAllLines(new File(path).toPath(), Charset.forName("UTF-8"));
        double demandChargesTotal =0.0;
        double internalDemandChargesTotal =0.0;
        double supplyCostTotal =0.0;
        double exchangepayoutTotal =0.0;
        double exchangerevenueTotal =0.0;
        boolean atPoint = false;
        boolean alreadyPrint = false;
        String timestamp = "";

        for (String line : lines) {
            if (line.contains(demandCharges)
                    &&line.contains(internalDemandCharges)
                    &&line.contains(supplyCost)
                    &&line.contains(exchangepayout)
                    &&line.contains(exchangerevenue)
                    &&line.contains(eventTime)
                    &&line.contains("billingType:CPM")){
                int index = line.indexOf(demandCharges);
                demandChargesTotal += praseDouble(line.substring(index+demandCharges.length()+1, line.indexOf(",", index)));

                index = line.indexOf(internalDemandCharges);
                internalDemandChargesTotal += praseDouble(line.substring(index+internalDemandCharges.length()+1, line.indexOf(",", index)));

                index = line.indexOf(supplyCost);
                supplyCostTotal += praseDouble(line.substring(index+supplyCost.length()+1, line.indexOf(",", index)));

                index = line.indexOf(exchangepayout);
                exchangepayoutTotal += praseDouble(line.substring(index+exchangepayout.length()+1, line.indexOf(",", index)));

                index = line.indexOf(exchangerevenue);
                exchangerevenueTotal += praseDouble(line.substring(index+exchangerevenue.length()+1, line.indexOf(",", index)));

                timestamp = line.substring(index+eventTime.length()+1, line.indexOf(",", index));
            }
            if (demandChargesTotal>max||
                    internalDemandChargesTotal>max||
                    supplyCostTotal>max||
                    exchangepayoutTotal>max||
                    exchangerevenueTotal>max){
                atPoint = true;
            }
            if (atPoint&&!alreadyPrint){
                System.out.println(line);
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(Long.valueOf(timestamp.trim()));
                System.out.println(timestamp+ ": "+new SimpleDateFormat("yyyy-MM-dd HH:mm:sss").format(cal.getTime()));
                alreadyPrint = true;
            }
        }
        System.out.println("demandChargesTotal: "+demandChargesTotal);
        System.out.println("internalDemandChargesTotal: "+internalDemandChargesTotal);
        System.out.println("supplyCostTotal: "+supplyCostTotal);
        System.out.println("exchangepayoutTotal: "+exchangepayoutTotal);
        System.out.println("exchangerevenueTotal: "+exchangerevenueTotal);
    }

    private double praseDouble(String str){
        try {
            return Double.valueOf(str.toString());
        }catch (Exception e){

        }
        return 0.0;
    }
}
