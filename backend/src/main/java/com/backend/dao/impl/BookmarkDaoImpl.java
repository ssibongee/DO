package com.backend.dao.impl;

import com.backend.dao.BookmarkDao;
import com.backend.mapper.BookmarkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookmarkDaoImpl implements BookmarkDao {

    @Autowired
    BookmarkMapper bookmarkMapper;


    @Override
    public void saveBookmark(Long uid, Long pid) {
        bookmarkMapper.saveBookmark(uid, pid);
    }

    @Override
    public void deleteBookmark(Long uid, Long pid) {
        bookmarkMapper.deleteBookmark(uid, pid);
    }

    @Override
    public Long isBookmark(Long uid, Long pid) {
        return bookmarkMapper.isBookmark(uid, pid);
    }
}
