package com.online.bookstore.client;

import com.online.bookstore.dto.MediaData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class MediaClient {

    @Value("${media.base.url}")
    private String mediaEndPoint;

    RestTemplate restTemplate = new RestTemplate();

    public List<MediaData> getPostsInMedia() {
        ResponseEntity<MediaData[]> responseEntity = restTemplate.getForEntity(mediaEndPoint,MediaData[].class);
        Map<String,List<MediaData>> map = new HashMap<>();
        List<MediaData> mediaDataList = Arrays.asList(responseEntity.getBody());
        mediaDataList.forEach(
                mediaData -> {

                }
        );
        return Arrays.asList(responseEntity.getBody());
    }
}
