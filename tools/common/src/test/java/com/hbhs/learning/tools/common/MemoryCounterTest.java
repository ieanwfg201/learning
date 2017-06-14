package com.hbhs.learning.tools.common;

import org.junit.Test;

import static org.junit.Assert.*;


public class MemoryCounterTest {

    @Test
    public void testSizeCount(){
        MemoryCounter counter = new MemoryCounter();
        long size = counter.estimate(1);
        System.out.println(size);

        AccountBudgetInfo entity = new AccountBudgetInfo();
        entity.setAccountAdvBurn(1.0);
        entity.setAccountInternalBurn(1.0);
        entity.setAccountId(1);
        size = counter.estimate(entity);
        System.out.println(size);

        CampaignBudgetInfo info = new CampaignBudgetInfo();
        size = counter.estimate(info);
        System.out.println(size);
    }

    private static class AccountBudgetInfo {
        private int accountId;
        private Double accountInternalBurn;
        private Double accountAdvBurn;

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public Double getAccountInternalBurn() {
            return accountInternalBurn;
        }

        public void setAccountInternalBurn(Double accountInternalBurn) {
            this.accountInternalBurn = accountInternalBurn;
        }

        public Double getAccountAdvBurn() {
            return accountAdvBurn;
        }

        public void setAccountAdvBurn(Double accountAdvBurn) {
            this.accountAdvBurn = accountAdvBurn;
        }
    }

    public class CampaignBudgetInfo {
        private int campaignId;
        private Double campaignDailyInternalBurn = 1.0;
        private Double campaignDailyAdvBurn =1D;
        private Double campaignTotalInternalBurn =1D;
        private Double campaignTotalAdvBurn=1D;
        private Double accountTotalBurn=1D;
        private Double campaignPayout=1D;

        public int getCampaignId() {
            return campaignId;
        }

        public void setCampaignId(int campaignId) {
            this.campaignId = campaignId;
        }

        public Double getCampaignDailyInternalBurn() {
            return campaignDailyInternalBurn;
        }

        public void setCampaignDailyInternalBurn(Double campaignDailyInternalBurn) {
            this.campaignDailyInternalBurn = campaignDailyInternalBurn;
        }

        public Double getCampaignDailyAdvBurn() {
            return campaignDailyAdvBurn;
        }

        public void setCampaignDailyAdvBurn(Double campaignDailyAdvBurn) {
            this.campaignDailyAdvBurn = campaignDailyAdvBurn;
        }

        public Double getCampaignTotalInternalBurn() {
            return campaignTotalInternalBurn;
        }

        public void setCampaignTotalInternalBurn(Double campaignTotalInternalBurn) {
            this.campaignTotalInternalBurn = campaignTotalInternalBurn;
        }

        public Double getCampaignTotalAdvBurn() {
            return campaignTotalAdvBurn;
        }

        public void setCampaignTotalAdvBurn(Double campaignTotalAdvBurn) {
            this.campaignTotalAdvBurn = campaignTotalAdvBurn;
        }

        public Double getAccountTotalBurn() {
            return accountTotalBurn;
        }

        public void setAccountTotalBurn(Double accountTotalBurn) {
            this.accountTotalBurn = accountTotalBurn;
        }

        public Double getCampaignPayout() {
            return campaignPayout;
        }

        public void setCampaignPayout(Double campaignPayout) {
            this.campaignPayout = campaignPayout;
        }
    }

}