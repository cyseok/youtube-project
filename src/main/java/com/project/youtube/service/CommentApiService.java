package com.project.youtube.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.project.youtube.dto.Comment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CommentApiService {

    @Value("${youtube.api.key}")
    private String apiKey;

    public List<Comment> getCommentsWithKeyword(String videoUrl, String keyword, int count) {
        List<Comment> commentList = new ArrayList<>();
        try {
            YouTube youtube = new YouTube.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(),
                    null
            )
                    .setApplicationName("youtube-comment-app")
                    .build();

            String nextPageToken = null;
            String videoOwnerId = getVideoOwnerId(youtube, videoUrl);
            int totalRetrieved = 0;

            if(count == 0) {
                while (true) {

                    YouTube.CommentThreads.List request = youtube.commentThreads()
                            .list(Collections.singletonList("snippet,replies"))
                            .setKey(apiKey)
                            .setVideoId(videoUrl)
                            .setTextFormat("plainText")
                            .setMaxResults(100L)  // Set to the maximum allowed value
                            .setPageToken(nextPageToken);

                    CommentThreadListResponse response = request.execute();

                    List<CommentThread> items = response.getItems();

                    for (CommentThread commentThread : items) {

                        if (count > 0 && totalRetrieved >= count) {
                            break;
                        }

                        com.google.api.services.youtube.model.Comment topLevelComment = commentThread.getSnippet().getTopLevelComment();
                        com.google.api.services.youtube.model.CommentSnippet snippet = topLevelComment.getSnippet();

                        if (isCommentMatchingKeyword(snippet.getTextDisplay(), keyword)) {
                            Comment dto = Comment.builder()
                                    .commentId(topLevelComment.getId())
                                    .channelId(snippet.getAuthorDisplayName())
                                    .authorChannelUrl(snippet.getAuthorChannelUrl())
                                    .content(snippet.getTextDisplay())
                                    .likeCount(snippet.getLikeCount())
                                    .publishedAt(ZonedDateTime.parse(snippet.getPublishedAt().toStringRfc3339()))
                                    .updatedAt(ZonedDateTime.parse(snippet.getUpdatedAt().toStringRfc3339()))
                                    .replyCount(commentThread.getSnippet().getTotalReplyCount())
                                    .creatorComment(snippet.getAuthorChannelId().getValue().equals(videoOwnerId))
                                    .build();

                            totalRetrieved++;
                            commentList.add(dto);
//                        if (count > 0 && commentList.size() >= count) {
//                            break;  // 원하는 수의 댓글을 얻었으면 루프 종료
//                        }
                        }
                    }

                    nextPageToken = response.getNextPageToken();

                    if (nextPageToken == null) {
                        break;  // 더 이상 페이지가 없으면 루프 종료
                    }
                }

            } else {
                while (true) {

                    YouTube.CommentThreads.List request = youtube.commentThreads()
                            .list(Collections.singletonList("snippet,replies"))
                            .setKey(apiKey)
                            .setVideoId(videoUrl)
                            .setTextFormat("plainText")
                            .setMaxResults(100L)  // Set to the maximum allowed value
                            .setPageToken(nextPageToken);

                    CommentThreadListResponse response = request.execute();
                    // nextPageToken = response.getNextPageToken();
                    List<CommentThread> items = response.getItems();
                    System.out.println(items);
                    Collections.shuffle(items);

                    for (CommentThread commentThread : items) {

                        if (count > 0 && totalRetrieved >= count) {
                            break;
                        }

                        com.google.api.services.youtube.model.Comment topLevelComment = commentThread.getSnippet().getTopLevelComment();
                        com.google.api.services.youtube.model.CommentSnippet snippet = topLevelComment.getSnippet();

                        if (isCommentMatchingKeyword(snippet.getTextDisplay(), keyword)) {
                            Comment dto = Comment.builder()
                                    .commentId(topLevelComment.getId())
                                    .channelId(snippet.getAuthorDisplayName())
                                    .authorChannelUrl(snippet.getAuthorChannelUrl())
                                    .content(snippet.getTextDisplay())
                                    .likeCount(snippet.getLikeCount())
                                    .publishedAt(ZonedDateTime.parse(snippet.getPublishedAt().toStringRfc3339()))
                                    .updatedAt(ZonedDateTime.parse(snippet.getUpdatedAt().toStringRfc3339()))
                                    .replyCount(commentThread.getSnippet().getTotalReplyCount())
                                    .creatorComment(snippet.getAuthorChannelId().getValue().equals(videoOwnerId))
                                    .build();

                            totalRetrieved++;
                            commentList.add(dto);
                        }
                    }
                    nextPageToken = response.getNextPageToken();

                    if (nextPageToken == null) {
                        break;  // 더 이상 페이지가 없으면 루프 종료
                    }
                }
            }
            //System.out.println(commentList);

        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("YouTube API 호출 중 오류 발생", e);
        }

        return commentList;
    }

    private boolean isCommentMatchingKeyword(String commentText, String keyword) {
        return keyword.isEmpty() || commentText.toLowerCase().contains(keyword.toLowerCase());
    }

    private String getVideoOwnerId(YouTube youtube, String videoId) throws IOException {
        YouTube.Videos.List videoRequest = youtube.videos()
                .list(Collections.singletonList("snippet"))
                .setId(Collections.singletonList(videoId))
                .setKey(apiKey);

        com.google.api.services.youtube.model.VideoListResponse videoResponse = videoRequest.execute();
        if (!videoResponse.getItems().isEmpty()) {
            return videoResponse.getItems().get(0).getSnippet().getChannelId();
        }
        return "";
    }
}