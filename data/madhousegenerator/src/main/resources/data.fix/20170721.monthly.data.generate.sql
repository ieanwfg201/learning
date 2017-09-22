-- first_level_monthly
drop table if exists kritter.first_level_monthly_bak08;

CREATE TABLE kritter.first_level_monthly_bak08 AS SELECT * FROM
    kritter.first_level_monthly
WHERE
    impression_time BETWEEN '2017-03-01' AND '2017-07-01';

DELETE FROM kritter.first_level_monthly
WHERE
    impression_time BETWEEN '2017-03-01' AND '2017-07-01';

INSERT INTO kritter.first_level_monthly
SELECT
    processing_time,
    impression_time,
    inventorySource,
    event_type,
    siteId,
    deviceId,
    deviceManufacturerId,
    deviceModelId,
    deviceOsId,
    countryId,
    countryCarrierId,
    countryRegionId,
    exchangeId,
    creativeId,
    adId,
    campaignId,
    advertiserId,
    bidderModelId,
    nofillReason,
    SUM(total_request) total_request,
    SUM(total_impression) total_impression,
    SUM(total_bidValue) total_bidValue,
    SUM(total_click) total_click,
    SUM(total_win) total_win,
    SUM(total_win_bidValue) total_win_bidValue,
    SUM(total_csc) total_csc,
    SUM(total_event_type) total_event_type,
    SUM(demandCharges) demandCharges,
    SUM(supplyCost) supplyCost,
    SUM(earning) earning,
    SUM(conversion) conversion,
    SUM(bidprice_to_exchange) bidprice_to_exchange,
    browserId,
    cpa_goal,
    SUM(exchangepayout) exchangepayout,
    SUM(exchangerevenue) exchangerevenue,
    SUM(networkpayout) networkpayout,
    SUM(networkrevenue) networkrevenue,
    SUM(billedclicks) billedclicks,
    SUM(billedcsc) billedcsc,
    supply_source_type,
    ext_site,
    connection_type
FROM
    (SELECT
        DATE_FORMAT(processing_time, '%Y-%m-01 00:00:00') AS processing_time,
            DATE_FORMAT(impression_time, '%Y-%m-01 00:00:00') AS impression_time,
            inventorySource,
            event_type,
            siteId,
            deviceId,
            deviceManufacturerId,
            deviceModelId,
            deviceOsId,
            countryId,
            countryCarrierId,
            countryRegionId,
            exchangeId,
            creativeId,
            adId,
            campaignId,
            advertiserId,
            bidderModelId,
            nofillReason,
            total_request,
            total_impression,
            total_bidValue,
            total_click,
            total_win,
            total_win_bidValue,
            total_csc,
            total_event_type,
            demandCharges,
            supplyCost,
            earning,
            conversion,
            bidprice_to_exchange,
            browserId,
            cpa_goal,
            exchangepayout,
            exchangerevenue,
            networkpayout,
            networkrevenue,
            billedclicks,
            billedcsc,
            supply_source_type,
            ext_site,
            connection_type
    FROM
        kritter.first_level_daily
    WHERE
        1 = 1
            AND processing_time >= '2017-03-01 00:00:00'
            AND processing_time < '2017-07-01 00:00:00') AS A
GROUP BY processing_time , impression_time , inventorySource , event_type , siteId , deviceId , deviceManufacturerId , deviceModelId , deviceOsId , countryId , countryCarrierId , countryRegionId , exchangeId , creativeId , adId , campaignId , advertiserId , bidderModelId , nofillReason , browserId , cpa_goal , supply_source_type , ext_site , connection_type;

-- first_level_limited_monthly
drop table if exists kritter.first_level_limited_monthly_bak08;
CREATE TABLE kritter.first_level_limited_monthly_bak08 AS SELECT * FROM
    kritter.first_level_limited_monthly
WHERE
    impression_time BETWEEN '2017-03-01' AND '2017-07-01';

DELETE FROM kritter.first_level_limited_monthly
WHERE
    impression_time BETWEEN '2017-03-01' AND '2017-07-01';

INSERT INTO kritter.first_level_limited_monthly(
processing_time,
    impression_time,
    pubId,
    siteId,
    deviceManufacturerId,
    deviceOsId,
    countryId,
    countryCarrierId,
    adId,
    campaignId,
    advId,
    total_request,
    total_impression,
    total_bidValue,
    total_click,
    total_win,
    total_win_bidValue,
    total_csc,
    demandCharges,
    supplyCost,
    earning,
    conversion,
    bidprice_to_exchange,
    cpa_goal,
    exchangepayout,
    exchangerevenue,
    networkpayout,
    networkrevenue,
    billedclicks,
    billedcsc,
    marketplace,
    bidFloor,
    reqslot,
    device_type
)
SELECT
    processing_time,
    impression_time,
    pubId,
    siteId,
    deviceManufacturerId,
    deviceOsId,
    countryId,
    countryCarrierId,
    adId,
    campaignId,
    advId,
    SUM(total_request) total_request,
    SUM(total_impression) total_impression,
    SUM(total_bidValue) total_bidValue,
    SUM(total_click) total_click,
    SUM(total_win) total_win,
    SUM(total_win_bidValue) total_win_bidValue,
    SUM(total_csc) total_csc,
    SUM(demandCharges) demandCharges,
    SUM(supplyCost) supplyCost,
    SUM(earning) earning,
    SUM(conversion) conversion,
    SUM(bidprice_to_exchange) bidprice_to_exchange,
    cpa_goal,
    SUM(exchangepayout) exchangepayout,
    SUM(exchangerevenue) exchangerevenue,
    SUM(networkpayout) networkpayout,
    SUM(networkrevenue) networkrevenue,
    SUM(billedclicks) billedclicks,
    SUM(billedcsc) billedcsc,
    marketplace,
    bidFloor,
    reqslot,
    device_type
FROM
    (SELECT
        DATE_FORMAT(processing_time, '%Y-%m-01 00:00:00') AS processing_time,
            DATE_FORMAT(impression_time, '%Y-%m-01 00:00:00') AS impression_time,
            pubId,
            siteId,
            deviceManufacturerId,
            deviceOsId,
            countryId,
            countryCarrierId,
            adId,
            campaignId,
            advId,
            total_request,
            total_impression,
            total_bidValue,
            total_click,
            total_win,
            total_win_bidValue,
            total_csc,
            demandCharges,
            supplyCost,
            earning,
            conversion,
            bidprice_to_exchange,
            cpa_goal,
            exchangepayout,
            exchangerevenue,
            networkpayout,
            networkrevenue,
            billedclicks,
            billedcsc,
            marketplace,
            bidFloor,
            reqslot,
            device_type
    FROM
        kritter.first_level_limited_daily
    WHERE
        1 = 1
            AND processing_time >= '2017-03-01 00:00:00'
            AND processing_time < '2017-07-01 00:00:00') AS A
GROUP BY processing_time , impression_time , pubId , siteId , deviceManufacturerId , deviceOsId , countryId , countryCarrierId , adId , campaignId , advId , cpa_goal , marketplace , bidFloor , reqslot , device_type;

-- first-level-ext-site_monthly
drop table if exists kritter.first_level_ext_site_monthly_bak08;
CREATE TABLE kritter.first_level_ext_site_monthly_bak08 AS SELECT * FROM
    kritter.first_level_ext_site_monthly
WHERE
    impression_time BETWEEN '2017-03-01' AND '2017-07-01';

-- delete the data
DELETE FROM kritter.first_level_ext_site_monthly
WHERE
    impression_time BETWEEN '2017-03-01' AND '2017-07-01';

INSERT INTO kritter.first_level_ext_site_monthly(
    processing_time,
    impression_time,
    exchangeId,
	siteId,
	ext_site,
	campaignId,
	adId,
    total_request,
    total_impression,
    total_bidValue,
    total_click,
    total_win,
    total_win_bidValue,
    total_csc,
    demandCharges,
    supplyCost,
    earning,
    conversion,
    bidprice_to_exchange,
    cpa_goal,
    exchangepayout,
    exchangerevenue,
    networkpayout,
    networkrevenue,
    billedclicks,
    billedcsc,
    countryId
)
SELECT
    processing_time,
    impression_time,
    exchangeId,
    siteId,
    ext_site,
    campaignId,
    adId,
    SUM(total_request) total_request,
    SUM(total_impression) total_impression,
    SUM(total_bidValue) total_bidValue,
    SUM(total_click) total_click,
    SUM(total_win) total_win,
    SUM(total_win_bidValue) total_win_bidValue,
    SUM(total_csc) total_csc,
    SUM(demandCharges) demandCharges,
    SUM(supplyCost) supplyCost,
    SUM(earning) earning,
    SUM(conversion) conversion,
    SUM(bidprice_to_exchange) bidprice_to_exchange,
    cpa_goal,
    SUM(exchangepayout) exchangepayout,
    SUM(exchangerevenue) exchangerevenue,
    SUM(networkpayout) networkpayout,
    SUM(networkrevenue) networkrevenue,
    SUM(billedclicks) billedclicks,
    SUM(billedcsc) billedcsc,
    countryId
FROM
    (SELECT
        DATE_FORMAT(processing_time, '%Y-%m-01 00:00:00') AS processing_time,
            DATE_FORMAT(impression_time, '%Y-%m-01 00:00:00') AS impression_time,
            exchangeId,
            siteId,
            ext_site,
            campaignId,
            adId,
            total_request,
            total_impression,
            total_bidValue,
            total_click,
            total_win,
            total_win_bidValue,
            total_csc,
            demandCharges,
            supplyCost,
            earning,
            conversion,
            bidprice_to_exchange,
            cpa_goal,
            exchangepayout,
            exchangerevenue,
            networkpayout,
            networkrevenue,
            billedclicks,
            billedcsc,
            countryId
    FROM
        kritter.first_level_ext_site_daily
    WHERE
        1 = 1
            AND processing_time >= '2017-03-01 00:00:00'
            AND processing_time < '2017-07-01 00:00:00') AS A
GROUP BY processing_time , impression_time , exchangeId , siteId , ext_site , campaignId , adId , cpa_goal , countryId;

-- adposition monthly
drop table if exists kritter.ad_position_monthly_bak08;
CREATE TABLE kritter.ad_position_monthly_bak08 AS SELECT * FROM
    kritter.ad_position_monthly
WHERE
    impression_time BETWEEN '2017-03-01' AND '2017-07-01';

-- delete the data
DELETE FROM kritter.ad_position_monthly
WHERE
    impression_time BETWEEN '2017-03-01' AND '2017-07-01';

INSERT INTO kritter.ad_position_monthly(
processing_time,
impression_time,
pubId,
siteId,
countryId,
stateId,
cityId,
advId,
campaignId,
adId,
adpositionId,
total_request,
total_impression,
total_bidValue,
total_click,
total_win,
total_win_bidValue,
total_csc,
demandCharges,
supplyCost,
earning,
conversion,
bidprice_to_exchange,
cpa_goal,
exchangepayout,
exchangerevenue,
networkpayout,
networkrevenue,
billedclicks,
billedcsc
)
SELECT
    processing_time,
    impression_time,
    pubId,
    siteId,
    countryId,
    stateId,
    cityId,
    advId,
    campaignId,
    adId,
    adpositionId,
    SUM(total_request) total_request,
    SUM(total_impression) total_impression,
    SUM(total_bidValue) total_bidValue,
    SUM(total_click) total_click,
    SUM(total_win) total_win,
    SUM(total_win_bidValue) total_win_bidValue,
    SUM(total_csc) total_csc,
    SUM(demandCharges) demandCharges,
    SUM(supplyCost) supplyCost,
    SUM(earning) earning,
    SUM(conversion) conversion,
    SUM(bidprice_to_exchange) bidprice_to_exchange,
    cpa_goal,
    SUM(exchangepayout) exchangepayout,
    SUM(exchangerevenue) exchangerevenue,
    SUM(networkpayout) networkpayout,
    SUM(networkrevenue) networkrevenue,
    SUM(billedclicks) billedclicks,
    SUM(billedcsc) billedcsc
FROM
    (SELECT
        DATE_FORMAT(processing_time, '%Y-%m-01 00:00:00') AS processing_time,
            DATE_FORMAT(impression_time, '%Y-%m-01 00:00:00') AS impression_time,
            pubId,
            siteId,
            countryId,
            stateId,
            cityId,
            advId,
            campaignId,
            adId,
            adpositionId,
            total_request,
            total_impression,
            total_bidValue,
            total_click,
            total_win,
            total_win_bidValue,
            total_csc,
            demandCharges,
            supplyCost,
            earning,
            conversion,
            bidprice_to_exchange,
            cpa_goal,
            exchangepayout,
            exchangerevenue,
            networkpayout,
            networkrevenue,
            billedclicks,
            billedcsc
    FROM
        kritter.ad_position_daily
    WHERE
        1 = 1
            AND processing_time >= '2017-03-01 00:00:00'
            AND processing_time < '2017-07-01 00:00:00') AS A
GROUP BY processing_time , impression_time , pubId , siteId , countryId , stateId , cityId , advId , campaignId , adId , adpositionId , cpa_goal;

-- channel_monthly
drop table if exists kritter.channel_monthly_bak08;
CREATE TABLE kritter.channel_monthly_bak08 AS SELECT * FROM
    kritter.channel_monthly
WHERE
    impression_time BETWEEN '2017-03-01' AND '2017-07-01';

-- delete the data
DELETE FROM kritter.channel_monthly
WHERE
    impression_time BETWEEN '2017-03-01' AND '2017-07-01';

INSERT INTO kritter.channel_monthly(
    processing_time,
    impression_time,
    pubId,
    siteId,
    advId,
    campaignId,
    adId,
    channelId,
    total_request,
    total_impression,
    total_bidValue,
    total_click,
    total_win,
    total_win_bidValue,
    total_csc,
    demandCharges,
    supplyCost,
    earning,
    conversion,
    bidprice_to_exchange,
    cpa_goal,
    exchangepayout,
    exchangerevenue,
    networkpayout,
    networkrevenue,
    billedclicks,
    billedcsc
)
SELECT
    processing_time,
    impression_time,
    pubId,
    siteId,
    advId,
    campaignId,
    adId,
    channelId,
    SUM(total_request) total_request,
    SUM(total_impression) total_impression,
    SUM(total_bidValue) total_bidValue,
    SUM(total_click) total_click,
    SUM(total_win) total_win,
    SUM(total_win_bidValue) total_win_bidValue,
    SUM(total_csc) total_csc,
    SUM(demandCharges) demandCharges,
    SUM(supplyCost) supplyCost,
    SUM(earning) earning,
    SUM(conversion) conversion,
    SUM(bidprice_to_exchange) bidprice_to_exchange,
    cpa_goal,
    SUM(exchangepayout) exchangepayout,
    SUM(exchangerevenue) exchangerevenue,
    SUM(networkpayout) networkpayout,
    SUM(networkrevenue) networkrevenue,
    SUM(billedclicks) billedclicks,
    SUM(billedcsc) billedcsc
FROM
    (SELECT
        DATE_FORMAT(processing_time, '%Y-%m-01 00:00:00') AS processing_time,
            DATE_FORMAT(impression_time, '%Y-%m-01 00:00:00') AS impression_time,
            pubId,
            siteId,
            advId,
            campaignId,
            adId,
            channelId,
            total_request,
            total_impression,
            total_bidValue,
            total_click,
            total_win,
            total_win_bidValue,
            total_csc,
            demandCharges,
            supplyCost,
            earning,
            conversion,
            bidprice_to_exchange,
            cpa_goal,
            exchangepayout,
            exchangerevenue,
            networkpayout,
            networkrevenue,
            billedclicks,
            billedcsc
    FROM
        kritter.channel_daily
    WHERE
        1 = 1
            AND processing_time >= '2017-03-01 00:00:00'
            AND processing_time < '2017-07-01 00:00:00') AS A
GROUP BY processing_time , impression_time , pubId , siteId , advId , campaignId , adId , channelId , cpa_goal;

