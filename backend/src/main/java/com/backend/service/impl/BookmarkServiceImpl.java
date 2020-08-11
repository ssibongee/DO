package com.backend.service.impl;

import com.backend.dao.BookmarkDao;
import com.backend.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookmarkServiceImpl implements BookmarkService {

    @Autowired
    BookmarkDao bookmarkDao;

    @Override
    public void saveBookmark(Long uid, Long pid) {
        bookmarkDao.saveBookmark(uid, pid);
    }

    @Override
    public void deleteBookmark(Long uid, Long pid) {
        bookmarkDao.deleteBookmark(uid, pid);
    }

    @Override
    public Long isBookmark(Long uid, Long pid) {
        return bookmarkDao.isBookmark(uid, pid);
    }
}
