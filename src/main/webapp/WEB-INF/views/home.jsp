<%--
  Created by IntelliJ IDEA.
  User: yunseok
  Date: 2024. 6. 29.
  Time: 오전 1:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>YouTube 댓글 검색 (댓글 추첨기)</title>
    <jsp:include page="/WEB-INF/include/head.jsp"/>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/home.js"></script>
</head>
<body>
<div class="container">
    <div class="search-container">
        <div class="logo">
            <a href="https://www.youtube.com/">
                <!-- <img src="https://www.youtube.com/img/desktop/yt_1200.png" alt="YouTube Logo"> -->
                <img src="${pageContext.request.contextPath}/resources/image/logo.png" alt="YouTube Logo">
            </a>
        </div>

        <h2 class="text-center mb-4">YouTube 댓글 검색 (추첨 가능!)</h2>
        <br>
        <form action="/search" method="GET" enctype="multipart/form-data">
            <div class="mb-3">
                <label for="videoUrl" class="form-label">YouTube 동영상 URL:</label>
                <input type="url" class="form-control" id="videoUrl" required
                       placeholder="https://www.youtube.com/watch?v=...">
            </div>
            <div class="mb-3">
                <label for="keyword" class="form-label">검색 키워드:</label>
                <p>(작성하지 않으면 <b>전체 댓글</b>이 출력됩니다.)</p>
                <input type="text" class="form-control" id="keyword"
                       placeholder="검색할 키워드를 입력하세요">
            </div>

            <div class="mb-3">
                <label for="keyword" class="form-label">댓글 개수 설정 :</label>
                <p>(댓글 개수가 <b>0</b>이라면 <b>전체 댓글 출력</b>, 댓글 개수를 <b>1개 이상</b> 설정하신다면 설정한 개수만큼 <b>랜덤하게</b> 출력됩니다.)</p>
                <input type="number" class="form-control" id="count" value="0" min="0"
                       placeholder="개수를 ">
            </div>
            <div class="d-grid">
                <button type="button" id="searchButton" class="btn btn-primary">검색</button>
            </div>

            <div class="messages-color">
                <div class="messages">

                <p>💬 참고해주세요 !!</p>
                <p>1. 쇼츠도 사용 가능합니다.</p>
                <p>1. 댓글만 검색됩니다. (대댓글 x, 대댓글은 포함되지 않기 때문에 영상의 댓글 수보다 작을 수 있습니다.)</p>
                <p>2. 댓글은 최근 순서대로 출력됩니다.</p>
                <p>3. 키워드를 입력한다면 해당 단어를 포함한 댓글만 출력됩니다.</p>
                <p>4. 댓글 개수를 1개 이상 설정하신다면 설정한 개수만큼 <b>랜덤하게</b> 출력됩니다.</p>
                <p>5. 채널 주인의 댓글은 파란색 테두리로 나옵니다.</p>
                <br>
                <p>❗️❗️ 주의 </p>
                <p>1. 댓글이 많은 영상일 수록 데이터를 불러오는 시간이 조금 걸립니다..</p>
                <p>2. 댓글 추첨 시에도 모든 댓글의 데이터를 불러온 후 랜덤하게 출력되기 때문에 댓글이 많다면 시간이 조금 더 소요될 수 있습니다.</p>
                </div>
            </div>
            <div class="info">
                <br>
                <p> 문의 : cysuk123@naver.com</p>
                <p> 개발자 취준생이 하루에 만든 페이지..</p>
            </div>
        </form>
    </div>

    <div id="videoInfo" class="video-container" style="display: none;">
        <a id="imgUrl" href="">
            <img id="thumbnailImg" src="" alt="썸네일" title="">
        </a>
        <div class="video-details">
            <a id="titleUrl" href="" title="">
                <h2 id="videoTitle"></h2>
            </a>
            <a id="channelUrl" href="" title="">
                <p id="channelTitle"></p>
            </a>
            <p id="viewCount"></p>
            <p id="publishedAt"></p>
            <img src="${pageContext.request.contextPath}/resources/image/like.png" alt="Like" class="like-icon">
            <span id="likeCountNumber"></span>
        </div>
    </div>

    <div class="comment-list">
        <span id="comment-number">댓글 수 : </span>
        <span id="comment-count"></span>
    </div>
    <br>
    <div class="comment-list" id="commentList"></div>
</div>

<div id="loading" style="display: none;">
    <div class="spinner-border text-primary" role="status">
        <span class="visually-hidden">Loading...</span>
    </div>
</div>
</body>

</html>