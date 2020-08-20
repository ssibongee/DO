package com.backend.service.impl;

import com.backend.dao.TagDao;
import com.backend.dto.post.Post;
import com.backend.dto.post.Tag;
import com.backend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagDao tagDao;

    @Override
    public List<Tag> loadTopTags() {
        return tagDao.loadTopTags();
    }

    @Override
    public List<Post> getAllPostByTag(String name) {
        return tagDao.getAllPostByTag(name);
    }
}
