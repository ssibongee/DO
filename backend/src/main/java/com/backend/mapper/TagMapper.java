package com.backend.mapper;

import com.backend.dto.post.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper {

    public List<Tag> loadTopTags();

}
