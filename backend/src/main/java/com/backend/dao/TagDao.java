package com.backend.dao;

import com.backend.dto.post.Tag;

import java.util.List;

public interface TagDao {

    public List<Tag> loadTopTags();

}
