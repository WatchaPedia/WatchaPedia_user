const userIdx = document.querySelector("#login-idx").title

document.addEventListener('click', (e) => {
  let selectType = document.querySelector(".css-96eosw").innerHTML
  let contentIdx = null;
  if(e.target.type == 'range'){
    let width = e.target.parentElement.querySelector("span").style.width;
    let spanWid = width.substring(0,(width.length-1))
    const star = e.target.parentNode.childNodes[1];
    if(e.target.type == 'range'){
      if (e.target.value * 20 == spanWid) {
        star.style.width = 0;
        e.target.value = 0;
        contentIdx = e.target.id
      }else{
        star.style.width = `${e.target.value * 20}%`;
        contentIdx = e.target.id
      }
    }

    $.ajax({
      url: '/estimate', // 클라이언트가 요청을 보낼 서버의 URL 주소
      headers: { 'Content-Type': 'application/json;charset=UTF-8' },
      data: JSON.stringify({           // HTTP 요청과 함께 서버로 보낼 데이터
        starContentType: selectType == '영화' ? 'movie' : (selectType == 'TV 프로그램' ? 'tv'
              : (selectType == '책' ? 'book' : 'webtoon')),
        starContentIdx: contentIdx,
        starUserIdx: userIdx,
        starPoint: e.target.value
      }),
      type: 'POST',           // HTTP 요청 방식(GET, POST)
      dataType: "json",       // 호출 시 데이터 타입
      success : function(data) {
        e.target.value = data.starPoint;
        star.style.width = `${e.target.value * 20}%`;
      },error: function() {
        alert("에러발생!")
      }
    });
  }
})
