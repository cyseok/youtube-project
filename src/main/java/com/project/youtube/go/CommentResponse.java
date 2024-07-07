package com.project.youtube.go;

import lombok.Data;
import java.util.List;

@Data
public class CommentResponse {
    private List<CommentThread> items;

    @Data
    public static class CommentThread {
        private CommentSnippet snippet;
    }

    @Data
    public static class CommentSnippet {
        private TopLevelComment topLevelComment;
    }

    @Data
    public static class TopLevelComment {
        private CommentDetails snippet;
    }

    @Data
    public static class CommentDetails {
        private String textDisplay;
    }
}