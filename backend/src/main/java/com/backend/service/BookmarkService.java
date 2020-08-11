package com.backend.service;

public interface BookmarkService {

    public void saveBookmark(Long uid, Long pid); // 북마크 등록

    public void deleteBookmark(Long uid, Long pid); // 북마크 삭제

    public Long isBookmark(Long uid, Long pid); // 북마크로 등록되어있는지 확인

}
