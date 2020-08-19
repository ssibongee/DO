package com.backend.service;

import com.backend.dto.post.Tag;

import java.util.List;

public interface TagService {

    public List<Tag> loadTopTags();
}
