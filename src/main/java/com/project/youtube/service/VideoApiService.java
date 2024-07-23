package com.project.youtube.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import com.project.youtube.dto.VideoInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;

@Service
public class VideoApiService {

    @Value("${youtube.api.key}")
    private String apiKey;

    public VideoInfo getVideoInfo(String videoUrl) {


        try {
            YouTube youtube = new YouTube.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(),
                    null
            )
                    .setApplicationName("youtube-video-info-app")
                    .build();

            //String videoUrl1 = extractVideoId(videoUrl);

            System.out.println("videoId : " + videoUrl);

            YouTube.Videos.List videoRequest = youtube.videos()
                    .list(Arrays.asList("snippet", "statistics", "contentDetails"))
                    .setKey(apiKey)
                    .setId(Collections.singletonList(videoUrl));

            VideoListResponse videoResponse = videoRequest.execute();

            if (!videoResponse.getItems().isEmpty()) {
                Video video = videoResponse.getItems().get(0);
                VideoSnippet snippet = video.getSnippet();
                VideoStatistics statistics = video.getStatistics();
                VideoContentDetails contentDetails = video.getContentDetails();

                // 채널 정보 가져오기
                YouTube.Channels.List channelRequest = youtube.channels()
                        .list(Collections.singletonList("snippet"))
                        .setKey(apiKey)
                        .setId(Collections.singletonList(snippet.getChannelId()));

                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+channelRequest);

                ChannelListResponse channelResponse = channelRequest.execute();
                String channelUrl = "https://www.youtube.com/channel/" + snippet.getChannelId();

                if (!channelResponse.getItems().isEmpty()) {
                    Channel channel = channelResponse.getItems().get(0);
                    if (channel.getSnippet().getCustomUrl() != null) {
                        channelUrl = "https://www.youtube.com/" + channel.getSnippet().getCustomUrl();
                    }
                }

                return VideoInfo.builder()
                        .videoId(videoUrl)
                        .title(snippet.getTitle())
                        .thumbnailUrl(snippet.getThumbnails().getHigh().getUrl())
                        .viewCount(statistics.getViewCount() != null ? statistics.getViewCount().longValue() : null)
                        .likeCount(statistics.getLikeCount() != null ? statistics.getLikeCount().longValue() : null)
                        .commentCount(statistics.getCommentCount() != null ? statistics.getCommentCount().longValue() : null)
                        .publishedAt(snippet.getPublishedAt().toString())
                        .channelTitle(snippet.getChannelTitle())
                        .channelId(snippet.getChannelId())
                        .channelUrl(channelUrl)
                        .build();
            }
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("YouTube API 호출 중 오류 발생", e);
        }

        return null;
    }

    private String extractVideoId(String videoUrl) {
        String videoId = "";
        if (videoUrl != null && videoUrl.trim().length() > 0) {
            String[] urlParts = videoUrl.split("v=");
            if (urlParts.length > 1) {
                videoId = urlParts[1];
                int ampersandPosition = videoId.indexOf('&');
                if (ampersandPosition != -1) {
                    videoId = videoId.substring(0, ampersandPosition);
                }
            }
        }
        return videoId;
    }
}
