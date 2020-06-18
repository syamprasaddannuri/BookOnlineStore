package com.online.bookstore.client;

import com.online.bookstore.dto.MediaData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Component
public class MediaClient {

    @Value("${media.base.url}")
    private String mediaEndPoint;

    RestTemplate restTemplate = new RestTemplate();

    public Map<String,Set<MediaData>> getPostsInMedia() {
        ResponseEntity<MediaData[]> responseEntity = restTemplate.getForEntity(mediaEndPoint,MediaData[].class);
        Map<String, Set<MediaData>> map = new HashMap<>();
        List<MediaData> mediaDataList = Arrays.asList(responseEntity.getBody());
        mediaDataList.forEach(
                mediaData -> {
                    String title = mediaData.getTitle();
                    String description = mediaData.getBody();
                    String[] titleArray = title.split(" ");
                    for(int i = 0 ; i < titleArray.length ; i++) {
                        Set<MediaData> set = map.get(titleArray[i]);
                        set.add(mediaData);
                        map.put(titleArray[i],set);
                    }
                    String[] descriptionArray = description.split(" ");
                    for(int i = 0 ; i < descriptionArray.length ; i++) {
                        Set<MediaData> set = map.get(titleArray[i]);
                        set.add(mediaData);
                        map.put(titleArray[i],set);
                    }
                }
        );
        return map;
    }
}
