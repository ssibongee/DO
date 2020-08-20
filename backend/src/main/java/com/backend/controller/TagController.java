package com.backend.controller;

import com.backend.dto.post.Tag;
import com.backend.service.TagService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {"Tag"})
@RestController
@CrossOrigin(origins = {"*"})
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping("/api/v4")
    public List<Map<String, String>> makeWordCloud() {
        List<Tag> tagList = tagService.loadTopTags();
        List<Map<String, String>> resultList = new ArrayList<>();

        for(Tag tag : tagList) {
            Map<String, String> map = new HashMap<>();
            map.put("text", tag.getName());
            map.put("value", tag.getHits().toString() + "000");
            resultList.add(map);
        }

        return resultList;
    }
}
