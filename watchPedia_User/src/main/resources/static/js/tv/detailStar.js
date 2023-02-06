const contentIdx = document.querySelector("div.css-11zdk8l-PosterWithRankingInfoBlock img").id;
const loginIdx = document.querySelector("#login-idx")

document.addEventListener('click', (e) => {
    if (e.target.type == 'range') {
      if(document.querySelector("#login-idx")) {
      let width = e.target.parentElement.querySelector("span").style.width;
      let spanWid = width.substring(0, (width.length - 1))
      const star = e.target.parentNode.childNodes[1];
      if (e.target.type == 'range') {
        if (e.target.value * 20 == spanWid) {
          star.style.width = 0;
          e.target.value = 0;
        } else {
          star.style.width = `${e.target.value * 20}%`;
        }
      }

      $.ajax({
        url: '/estimate', // 클라이언트가 요청을 보낼 서버의 URL 주소
        headers: {'Content-Type': 'application/json;charset=UTF-8'},
        data: JSON.stringify({           // HTTP 요청과 함께 서버로 보낼 데이터
          starContentType: "tv",
          starContentIdx: contentIdx,
          starUserIdx: loginIdx.title,
          starPoint: e.target.value
        }),
        type: 'POST',           // HTTP 요청 방식(GET, POST)
        dataType: "json",       // 호출 시 데이터 타입
        success: function (data) {
          e.target.value = data.starPoint;
          star.style.width = `${e.target.value * 20}%`;
          if (data.commentText == null) {
            if (data.starPoint > 0) {
              document.querySelector("div.css-sd2jre-SectionBlock").style.display = "block";
              document.querySelector("section.css-1v9og64-LeaveCommentSection").style.display = "block";
            } else if (data.starPoint == 0) {
              document.querySelector("div.css-sd2jre-SectionBlock").style.display = "none";
              document.querySelector("section.css-1v9og64-LeaveCommentSection").style.display = "none";
            }
          }
        }, error: function () {
          alert("에러발생!")
        }
      });
      }else{
        loginModalOn()
      }
    }
})
