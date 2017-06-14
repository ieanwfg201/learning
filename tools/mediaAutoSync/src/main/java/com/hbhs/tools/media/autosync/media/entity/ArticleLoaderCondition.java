package com.hbhs.tools.media.autosync.media.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * Created by walter on 17-6-7.
 */
@Getter@Setter
@ToString
public class ArticleLoaderCondition {
    private Date startDate;
    private Date endDate;
}
