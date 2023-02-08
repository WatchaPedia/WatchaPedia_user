morePop.querySelector("div.e1svyhwg29").addEventListener('click',
    document.querySelector("#login-idx")?hateSave:loginModalOn);
morePop2.querySelector("div.css-bgi4sk").addEventListener('click',
    document.querySelector("#login-idx")?hateSave:loginModalOn);

function hateSave() {
    let morePop2Text = morePop2.querySelector("div.css-bgi4sk");
    $.ajax({
        url: '/hate/save',
        headers: {'Content-Type': 'application/json;charset=UTF-8'},
        data: JSON.stringify({           // HTTP 요청과 함께 서버로 보낼 데이터
          userIdx: loginIdx.title,
            contentType: "webtoon",
            contentIdx: contentIdx
        }),
        type: 'POST',           // HTTP 요청 방식(GET, POST)
        dataType: "json",       // 호출 시 데이터 타입
        success: function (data) {
            if (data == true) {
                morePop2Text.querySelector("span").classList.add("css-fwllm4");
                morePop2Text.querySelector("span").classList.remove("css-7zhfhb");
                morePop2Text.querySelector("img").src = 'data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0Ij4KICAgIDxnIGZpbGw9Im5vbmUiIGZpbGwtcnVsZT0iZXZlbm9kZCI+CiAgICAgICAgPHBhdGggZD0iTTAgMGgyNHYyNEgweiIvPgogICAgICAgIDxwYXRoIGZpbGw9IiNGRjJGNkUiIGQ9Ik0xMS41NjYgMTkuMzEyYy4zNzcuNjc4Ljg2IDEuMjg4IDEuNDI4IDEuODA4QTkuMzMzIDkuMzMzIDAgMSAxIDExIDIuNjY3YTkuMzM3IDkuMzM3IDAgMCAxIDguODIyIDYuMjggNy4xNyA3LjE3IDAgMCAwLTIuMjg1LS4yNzRBNy4zMzMgNy4zMzMgMCAwIDAgMTEgNC42NjdhNy4zMDIgNy4zMDIgMCAwIDAtNC41MjUgMS41NjJsNS40OSA1LjQ5YTcuMTQxIDcuMTQxIDAgMCAwLS45NDUgMS44ODRMNS4wODMgNy42NjZhNy4zMzMgNy4zMzMgMCAwIDAgNi40ODIgMTEuNjQ1eiIvPgogICAgICAgIDxwYXRoIGZpbGw9IiNGRjJGNkUiIGQ9Ik0yMi42MTEgMTIuMTYxTDE4LjE3MyAxNi42bC0xLjgzMy0xLjgzM2EuODQ0Ljg0NCAwIDEgMC0xLjE5MyAxLjE5NGwyLjEyNiAyLjEyNmEuODQ3Ljg0NyAwIDAgMCAuMTQ3LjExNy43ODUuNzg1IDAgMCAwIDEuMjE5LjE0Mmw0LjgyNi00LjgyNmE2IDYgMCAxIDEtLjg1NC0xLjM2eiIvPgogICAgPC9nPgo8L3N2Zz4K';

                morePop.querySelector("div.e1svyhwg29").classList.add("css-13md5na-StyledDropdownMenuItem");
                morePop.querySelector("div.e1svyhwg29").classList.remove("css-1t4uwd9-StyledDropdownMenuItem");

                main.style.display = 'none';
                main.classList.remove('on');
                main.classList.add('off');
                morePop2.style.display = 'none';
                morePop2.classList.remove('on');
                morePop2.classList.add('off');

                morePop.style.display = 'none';
                morePop.classList.remove('on');
                morePop.classList.add('off');
            } else {
                morePop2Text.querySelector("span").classList.add("css-7zhfhb");
                morePop2Text.querySelector("span").classList.remove("css-fwllm4");
                morePop2Text.querySelector("img").src = 'data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0Ij4KICAgIDxnIGZpbGw9Im5vbmUiIGZpbGwtcnVsZT0iZXZlbm9kZCI+CiAgICAgICAgPHBhdGggZD0iTTAgMGgyNHYyNEgweiIvPgogICAgICAgIDxwYXRoIGZpbGw9IiM3ODc4NzgiIGQ9Ik02LjA4MyA3LjY2NmE3LjMzMyA3LjMzMyAwIDAgMCAxMC4yNSAxMC4yNUw2LjA4NCA3LjY2N3pNNy40NzUgNi4yM0wxNy43NyAxNi41MjVBNy4zMzMgNy4zMzMgMCAwIDAgNy40NzUgNi4yM3pNMTIgMjEuMzMzYTkuMzMzIDkuMzMzIDAgMSAxIDAtMTguNjY2IDkuMzMzIDkuMzMzIDAgMCAxIDAgMTguNjY2eiIvPgogICAgPC9nPgo8L3N2Zz4K';

                morePop.querySelector("div.e1svyhwg29").classList.add("css-1t4uwd9-StyledDropdownMenuItem");
                morePop.querySelector("div.e1svyhwg29").classList.remove("css-13md5na-StyledDropdownMenuItem");

                main.style.display = 'none';
                main.classList.remove('on');
                main.classList.add('off');
                morePop2.style.display = 'none';
                morePop2.classList.remove('on');
                morePop2.classList.add('off');

                morePop.style.display = 'none';
                morePop.classList.remove('on');
                morePop.classList.add('off');
            }
        }, error: function () {
            alert("에러발생!")
        }
    })
}
