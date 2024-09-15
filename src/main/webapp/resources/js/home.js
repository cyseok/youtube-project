$(document).ready(function() {

/*
document.addEventListener('DOMContentLoaded', function() {
    fetch('/visitor-log', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({})
    })
        .then(response => response.text())
        .then(data => console.log(data))
        .catch((error) => console.error('Error:', error));
});

 */
    function youtubeSearch() {
        $("#commentList").empty();

        const videoURL = $("#videoUrl").val();
        const videoPrefix1 = 'https://www.youtube.com/watch?v=';
        const videoPrefix2 = 'https://youtube.com/watch?v=';
        const mobilePrefix = 'https://youtu.be/';
        const shortsPrefix1 = 'https://www.youtube.com/shorts/';
        const shortsPrefix2 = 'https://youtube.com/shorts/';

        let videoId;
        try {
            if (videoURL.startsWith(videoPrefix1)) {
                videoId = videoURL.slice(videoPrefix1.length).split('&')[0];
            } else if (videoURL.startsWith(videoPrefix2)) {
                videoId = videoURL.slice(videoPrefix2.length).split('&')[0];
            } else if (videoURL.startsWith(mobilePrefix)) {
                videoId = videoURL.slice(mobilePrefix.length).split('?')[0];
            } else if (videoURL.startsWith(shortsPrefix1)) {
                videoId = videoURL.slice(shortsPrefix1.length);
            } else if (videoURL.startsWith(shortsPrefix2)) {
                videoId = videoURL.slice(shortsPrefix2.length).split('?')[0];
            }
        } catch (error) {
            if (error instanceof ReferenceError) {
                alert("유효하지 않은 동영상 URL입니다.");
            }
        }

        const keyword = $("#keyword").val();
        const count = $("#count").val();

        // 영상 정보
        $.ajax({
            type: "GET",
            url: '/video',
            data: {
                "videoUrl": videoId
            },
            dataType: "text",
            success: function (result, textStatus, xhr) {
                addSearchLog(videoURL);

                var jsonData = JSON.parse(result);
                // 썸네일 이미지
                $('#thumbnailImg').attr('src', jsonData.thumbnailUrl).attr('title', videoURL);
                $('#videoTitle').text(jsonData.title);
                $('#channelTitle').text(jsonData.channelTitle);
                $('#viewCount').text('조회수: ' + numberWithCommas(jsonData.viewCount) + '회');
                //$('#viewCount').text(jsonData.viewCount);
                $('#publishedAt').text('업로드: ' + formatDate(jsonData.publishedAt));
                //$('#publishedAt').text(jsonData.publishedAt);
                $('#likeCountNumber').text(formatLikeCount(jsonData.likeCount));
                $('#imgUrl').attr("href", videoURL);
                $('#channelUrl').attr("href", jsonData.channelUrl).attr('title', jsonData.channelUrl);
                $('#titleUrl').attr("href", videoURL).attr('title', videoURL);

                $('#videoInfo').show();

                $('html, body').animate({
                    scrollTop: $("#videoInfo").offset().top
                }, 100);

            }, error: function (error) {
                alert("영상 정보를 가져올 수 없습니다.");
            }
        });

        //$("#loading").show();
        // 댓글 정보
        $.ajax({
            type: "GET",
            url: '/search',
            data: {
                "videoUrl": videoId
                , "keyword": keyword
                , "count": count
            },
            dataType: "json",
            success: function (data, textStatus, xhr) {
                $("#comment-count").text(data.length);
                var commentList = $("#commentList");
                commentList.empty(); // 기존 댓글 목록 초기화

                for (var i = 0; i < data.length; i++) {
                    var comment = data[i];
                    var commentElement = $(
                        "<div class='comment-container " + (comment.creatorComment ? "creator-comment" : "") + "'>" +
                        "<div class='comment-header'>" +
                        // "<img src='https://via.placeholder.com/40' class='profile-pic' alt='Profile Picture'>" +
                        "<img src='/resources/image/profile.png' class='profile-pic' alt='Profile Picture'>" +

                        "<a href= '" + comment.authorChannelUrl + "' class='channel-id'>" + comment.channelId + "</a>" +
                        "<span class='comment-date'>" + formatDate(comment.publishedAt) + "</span>" +
                        "</div>" +
                        "<div class='comment-content'>" + comment.content + "</div>" +
                        "<div class='comment-footer'>" +
                        // "<img src='${pageContext.request.contextPath}/resources/image/like.png' class='heart-icon'>" +
                        "<img src='/resources/image/like.png' class='heart-icon'>" +
                        "<span class='like-count'>" + comment.likeCount + "</span>" +
                        "<span class='reply-count'> 답글 " + comment.replyCount + " </span>" +
                        "</div>" +
                        "</div>"
                    );
                    commentList.append(commentElement);
                }
                //$('#loading').hide();
            }, error: function (error) {
                alert("댓글 정보를 가져올 수 없습니다.");
            }
        });

    }

    function formatDate(dateString) {
        var date = new Date(dateString);
        var options = {
            year: 'numeric',
            month: 'long',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit',
            second: '2-digit',
            hour12: false, // 24시간 형식 사용
            timeZone: 'Asia/Seoul' // 한국 시간대 사용
        };
        return date.toLocaleString('ko-KR', options);
    }

    // 숫자에 쉼표 추가하는 함수

    // 날짜 포맷팅 함수
    function formatDate(dateString) {
        const options = {
            year: 'numeric',
            month: 'long',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        };
        return new Date(dateString).toLocaleString('ko-KR', options);
    }

    function numberWithCommas(x) {
        return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }

    function formatLikeCount(num) {
        num = parseInt(num, 10);
        if (num < 1000) {
            return num.toString();
        }

        if (num < 10000) {
            return (Math.floor(num / 100) / 10).toFixed(1).replace(/\.0$/, '') + '천';
        }

        var units = ['만', '억', '조'];
        var unitIndex = 0;

        while (num >= 10000 && unitIndex < units.length) {
            num /= 10000;
            unitIndex++;
        }

        return (Math.floor(num * 10) / 10).toFixed(1).replace(/\.0$/, '') + units[unitIndex - 1];
    }

    $('#videoUrl, #keyword, #count').keypress(function (e) {
        if (e.which === 13) {
            e.preventDefault();
            youtubeSearch();
        }
    });

    // youtubeSearch 함수가 먼저 정의된 후에 이벤트 리스너를 추가
    $('#searchButton').click(function () {
        youtubeSearch();
    });

    // user의 search-log 데이터 삽입
    function addSearchLog(videoURL) {

        $.ajax({
            type: "POST",
            url: '/search-log',
            data: {
                "searchUrl": videoURL
            },
            dataType: "text",
            success: function (result, textStatus, xhr) {
                console.log(result);
            }, error: function (error) {
                console.log(error)
                console.log("검색로그 삽입 실패");
            }
        });

    }

});