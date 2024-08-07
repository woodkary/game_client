package com.kary.hahaha3.mapper;

import com.kary.hahaha3.pojo.Theme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author:123
 */
//theme不能由用户修改
@Repository
@Mapper
@Tag(name = "ThemeMapper")
public interface ThemeMapper {
    //invisible to user
    Integer publishTheme(@Param("themeName")String themeName);
    //invisible to user
    Integer changeDescription(@Param("themeName")String themeName,@Param("description")String description);
    Integer publishArticle(@Param("themeName")String themeName,@Param("newArticleId")int newArticleId);
    Integer followTheme(@Param("themeName")String themeName);
    Theme getThemeByName(@Param("themeName")String themeName);
    List<Theme> getAllThemeByPage(@Param("page")Integer page);
}
