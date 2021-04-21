package com.kidd.opdb.mapper;

import com.kidd.opdb.model.UrlScanPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UrlScanPoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UrlScanPo record);

    int insertSelective(UrlScanPo record);

    UrlScanPo selectByPrimaryKey(Integer id);

    List<String> selectAllDomain();

    int updateByPrimaryKeySelective(UrlScanPo record);

    int updateByPrimaryKey(UrlScanPo record);

    int batchInsert(List<UrlScanPo> list);

    int updateByDomain(@Param("urlDomain") String urlDomain,@Param("mailCount") Integer mailCount);
}