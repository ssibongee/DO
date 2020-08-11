package com.backend.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookmarkMapper {

    public void saveBookmark(Long uid, Long pid); // 북마크 등록

    public void deleteBookmark(Long uid, Long pid); // 북마크 삭제

    public Long isBookmark(Long uid, Long pid); // 북마크로 등록되어있는지 확인

}
