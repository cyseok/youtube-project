package com.project.youtube.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoInfo {
    private String videoId; // 영상 ID
    private String title; // 영상 제목
    private String thumbnailUrl; // 썸네일 URL
    private String publishedAt;  // 영상 게시일
    private String channelTitle; // 채널이름
    private String channelId;   // 채널 ID
    private String channelUrl; // 영상 URL
    private Long viewCount; // 조회수
    private Long likeCount; // 좋아요 수
    private Long commentCount; // 댓글 수 (대댓글 까지의 수)
}
