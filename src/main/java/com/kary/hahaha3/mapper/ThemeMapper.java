package com.kary.hahaha3.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author:123
 */
//theme不能由用户修改
public interface ThemeMapper {
    //invisible to user
    Integer publishTheme(@Param("themeName")String themeName);
    //invisible to user
    Integer changeDescription(@Param("themeName")String themeName,@Param("description")String description);
    Integer publishArticle(@Param("themeName")String themeName,@Param("newArticleId")int newArticleId);
    Integer followTheme(@Param("themeName")String themeName);
}
