package com.project.youtube.dto;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class Comment {
    private String commentId; // 댓글 ID (고유 식별자)
    private String channelId; // 채널(작성자) ID
    private String authorChannelUrl; // 채널(작성자) youtube URL
    private String content; // 댓글 내용
    private Long likeCount; // 댓글 좋아요 수
    private ZonedDateTime publishedAt; // 댓글 작성 시간
    private ZonedDateTime updatedAt; // 댓글 수정 시간
    private Long replyCount; // 댓글의 답글 수
    private boolean creatorComment; // 채널 주인의 댓글 여부 (추첨에 사용한다면 주인 댓글은 뺴고 작동하도록 해줄 수도 있겠죠?)
}
