package com.kidd.opdb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UrlScanPo {
    private Integer id;

    private String urlRaw;

    private String urlDomain;

    private String urlParam;

    private String urlKeyword;

    private Byte injectble;

    private Integer mailCount;

    private Integer mobileCount;

}