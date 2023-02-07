if(document.querySelector("a#login-idx")) {
    const loginIdx = document.querySelector("a#login-idx").title;

    const editBtn = document.querySelector("button#edit-btn");
    const deleteBtn = document.querySelector("button#delete-btn");
    const deleteModal = document.querySelector("div#modal-container-a26PPl5LlbKLO3pXDfQ9Z");
    const deleteCancel = document.querySelector("button.css-1gdw77k-StylelessButton");
    const deleteOK = document.querySelector("button.css-sfhtz9-StylelessButton");

    const commentContainer = document.querySelector("div.css-sd2jre-SectionBlock");
    const hasComment = document.querySelector("div.hasComm");

    button.addEventListener('click', commentSave)
    button2.addEventListener('click', commentSave)

    function commentSave() {
        let spoilerCheck = document.querySelector("div.css-hyoixq svg").classList;
        let inputText = document.querySelector("div.css-iowq1w textarea").value;
        $.ajax({
            url: '/comment/save', // 클라이언트가 요청을 보낼 서버의 URL 주소
            headers: {'Content-Type': 'application/json;charset=UTF-8'},
            data: JSON.stringify({           // HTTP 요청과 함께 서버로 보낼 데이터
                contentType: "movie",
                contentIdx: contentIdx,
                userIdx: loginIdx,
                text: inputText,
                spoiler: spoilerCheck.contains("css-7zhfhb") ? false : true,
            }),
            type: 'POST',           // HTTP 요청 방식(GET, POST)
            dataType: "json",       // 호출 시 데이터 타입
            success: function (data) {
                main.style.display = 'none';
                main.classList.add('off');
                main.classList.remove('on');
                comment.style.display = 'none';
                comment.classList.add('off');
                comment.classList.remove('on');

                document.querySelector("a.css-1bh5fq7-StylelessLocalLink-LinkToMyComment").href = '/comment/' + data;
                document.querySelector("div.css-1fucs4t-StyledText").innerHTML = inputText;
                commentContainer.style.display = 'block';
                hasComment.style.display = 'block';
                document.querySelector("section.css-1v9og64-LeaveCommentSection").style.display = 'none'
            }, error: function () {
                alert("에러발생!")
            }
        });
    };

// 수정 버튼 클릭 시
    editBtn.addEventListener('click', commentEdit);

    function commentEdit() {
        main.style.display = 'block';
        main.classList.add('on');
        main.classList.remove('off');
        comment.style.display = 'block';
        comment.classList.add('on');
        comment.classList.remove('off');
        button.innerHTML = "수정"
    };

// 삭제 버튼 클릭 시
    deleteBtn.addEventListener('click', commentDelete);

    function commentDelete() {
        main.style.display = 'block';
        main.classList.add('on');
        main.classList.remove('off');
        deleteModal.style.display = 'block';
        deleteModal.classList.add('on');
        deleteModal.classList.remove('off');
    }

// 삭제 취소 버튼 클릭 시
    deleteCancel.addEventListener("click", () => {
        main.style.display = 'none';
        main.classList.add('off');
        main.classList.remove('on');
        deleteModal.style.display = 'none';
        deleteModal.classList.add('off');
        deleteModal.classList.remove('on');
    })

// 삭제 확인 버튼 클릭 시
    deleteOK.addEventListener("click", () => {
        $.ajax({
            url: '/comment/delete',
            headers: {'Content-Type': 'application/json;charset=UTF-8'},
            data: JSON.stringify({           // HTTP 요청과 함께 서버로 보낼 데이터
                contentType: "movie",
                contentIdx: contentIdx,
                userIdx: loginIdx,
                text: ""
            }),
            type: 'POST',           // HTTP 요청 방식(GET, POST)
            dataType: "json",       // 호출 시 데이터 타입
            success: function (data) {
                // 모달창 닫기
                main.style.display = 'none';
                main.classList.add('off');
                main.classList.remove('on');
                deleteModal.style.display = 'none';
                deleteModal.classList.add('off');
                deleteModal.classList.remove('on');

                // 텍스트 비우기
                document.querySelector("textarea.css-137vxyg").innerHTML = null;
                document.querySelector("textarea.css-137vxyg").value = null;

                // 스포일러 체크 해제
                if (spoilerIcon.classList.contains("css-1ngtlfw")) {
                    spoilerIcon.classList.add("css-7zhfhb");
                    spoilerIcon.classList.remove("css-1ngtlfw");
                }
                if (data == true) {
                    commentContainer.style.display = 'block';
                    document.querySelector("section.css-1v9og64-LeaveCommentSection").style.display = 'block'
                    hasComment.style.display = 'none';
                } else {
                    commentContainer.style.display = 'none';
                    hasComment.style.display = 'none';
                }
            }, error: function () {
                alert("에러발생!")
            }
        });
    })

// 상단 코멘트 버튼 클릭 시
    const hasCommentBtn = commentModal.querySelectorAll("div.e1svyhwg29");
    hasCommentBtn.item(0).addEventListener('click', commentEdit);
    hasCommentBtn.item(1).addEventListener('click', commentDelete);
// 상단 코멘트 버튼 클릭 시 (719px 미만)
    const hasCommentBtn2 = commentModal2.querySelectorAll("div.css-bgi4sk");
    hasCommentBtn2.item(0).addEventListener('click', commentEdit);
    hasCommentBtn2.item(1).addEventListener('click', commentDelete);
}

document.addEventListener('click', (e) => {
    if (e.target.parentElement.classList.contains("css-hy68ty")) {
        if(document.querySelector("#login-idx")){
            let comm = e.target.parentElement.parentElement;
            // 코멘트 좋아요 클릭
            let commentIdx = comm.querySelector("div.css-ob93md a").href.split("/comment/")[1];
            let likeSum = parseInt(comm.querySelector("em.like-sum").innerHTML);

            $.ajax({
                url: '/comment/like/save',
                headers: {'Content-Type': 'application/json;charset=UTF-8'},
                data: JSON.stringify({           // HTTP 요청과 함께 서버로 보낼 데이터
                    userIdx: document.querySelector("#login-idx").title,
                    commentIdx: commentIdx
                }),
                type: 'POST',           // HTTP 요청 방식(GET, POST)
                dataType: "json",       // 호출 시 데이터 타입
                success: function (data) {
                    if (data == true) {
                        comm.querySelector("em.like-sum").innerHTML = likeSum + 1;
                        comm.querySelector("div.css-hy68ty button").classList.add("css-jj4q3s-StylelessButton-UserActionButton");
                        comm.querySelector("div.css-hy68ty button").classList.remove("css-1h18l7j-StylelessButton");
                    } else {
                        comm.querySelector("em.like-sum").innerHTML = likeSum - 1;
                        comm.querySelector("div.css-hy68ty button").classList.add("css-1h18l7j-StylelessButton");
                        comm.querySelector("div.css-hy68ty button").classList.remove("css-jj4q3s-StylelessButton-UserActionButton");
                    }
                }, error: function () {
                    alert("에러발생!")
                }
            })
        }else{loginModalOn()}
    }
})

let commentList = document.querySelectorAll("li.css-1fryc54");
document.addEventListener('click', (e) => {
    if (e.target.classList.contains("css-13mdv8k-StylelessButton")) {
        let comm = e.target.parentElement.parentElement.parentElement;
        // 스포댓글 보기 버튼 클릭 시
        comm.querySelector("span.css-xstsdj").style.display = 'none';

        comm.querySelector("div.css-1yrlzf9-StyledText").classList.add("css-qxbzku-StyledText");
        comm.querySelector("div.css-1yrlzf9-StyledText").classList.remove("css-1yrlzf9-StyledText");

        comm.querySelector("div.css-1jm9uak").classList.add("css-1atijos");
        comm.querySelector("div.css-1jm9uak").classList.remove("css-1jm9uak");

        comm.querySelector("span.css-zoh368").style.backgroundImage = "url('data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0Ij4KICAgIDxnIGZpbGw9Im5vbmUiIGZpbGwtcnVsZT0iZXZlbm9kZCI+CiAgICAgICAgPGcgZmlsbD0iIzc4Nzg3OCI+CiAgICAgICAgICAgIDxwYXRoIGQ9Ik02Ljc1IDkuNDg1aC0zYTEgMSAwIDAgMC0xIDF2MTBhMSAxIDAgMCAwIDEgMWgzYTEgMSAwIDAgMCAxLTF2LTEwYTEgMSAwIDAgMC0xLTFNMjAuNjU3IDguNTY2YTIuMzYzIDIuMzYzIDAgMCAwLTEuNzc5LS44MTNIMTYuNjJsLjE2NC0uNjI3Yy4xMzctLjUyOC4yMDEtMS4xMi4yMDEtMS44NjMgMC0xLjkxOS0xLjM3NS0yLjc3OC0yLjczOC0yLjc3OC0uNDQ0IDAtLjc2Ni4xMjMtLjk4Ni4zNzYtLjIuMjI3LS4yODIuNTMtLjI0My45MzVsLjAzIDEuMjMtMi45MDMgMi45NGMtLjU5My42LS44OTQgMS4yMy0uODk0IDEuODcydjkuNjQ3YS41LjUgMCAwIDAgLjUuNWg3LjY4N2EyLjM4OCAyLjM4OCAwIDAgMCAyLjM0OC0yLjA3bDEuNDQ1LTcuNDUyYTIuNDQgMi40NCAwIDAgMC0uNTc0LTEuODk3Ii8+CiAgICAgICAgPC9nPgogICAgPC9nPgo8L3N2Zz4K')";
        comm.querySelector("span.css-43cye7").style.backgroundImage = "url('data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0Ij4KICAgIDxwYXRoIGZpbGw9IiM3ODc4NzgiIGZpbGwtcnVsZT0iZXZlbm9kZCIgZD0iTTkuODU3IDE3Ljc4Nkw2IDIxdi00LjkxYy0xLjg0MS0xLjM3My0zLTMuMzY5LTMtNS41OUMzIDYuMzU4IDcuMDMgMyAxMiAzczkgMy4zNTggOSA3LjVjMCA0LjE0Mi00LjAzIDcuNS05IDcuNS0uNzM5IDAtMS40NTYtLjA3NC0yLjE0My0uMjE0eiIvPgo8L3N2Zz4K')";

        if (comm.querySelector("div.css-hy68ty button").classList.contains("css-1jrmj77-StylelessButton")) {
            comm.querySelector("button.css-1jrmj77-StylelessButton").classList.add('css-1h18l7j-StylelessButton');
            comm.querySelector("button.css-1jrmj77-StylelessButton").classList.remove('css-1jrmj77-StylelessButton');
        } else {
            comm.querySelector("div.css-hy68ty button").removeAttribute('disabled')
        }

        comm.querySelector("div.css-hy68ty button").removeAttribute('disabled')
    }
});

// 코멘트 로딩
const commentUl = document.querySelector("ul.css-nh9j5x-VisualUl-CommentHorizontalUl")
const spoilerLi = document.querySelector(".spoiler-li")
const notSpoilerLi = document.querySelector(".not-spoiler-li")
const loadingIcon = document.querySelector("#loading-icon");
let loading = false;
const movieIdx = window.location.href.split("/movie/")[1]
let page = 1;
function addList() {
    $.ajax({
        url: `/movie/${movieIdx}/new?page=${page}`,
        headers: {'Content-Type': 'application/json;charset=UTF-8'},
        type: 'GET',
        dataType: "json",
        beforeSend:function(){
            loadingIcon.style.display='block'
            loadingIcon.style.right = 0;
        },
        complete:function(){
            loadingIcon.style.display='none'
        },
        success: function (data) {
            for (let idx of data.commentList.content) {
                if(idx.spoiler == true){
                    let appendLi = spoilerLi.cloneNode(true)
                    appendLi.querySelector("div.css-1cvf9dk").querySelector("a.css-1f9m1s4-StylelessLocalLink").href = `/user/${idx.user.userIdx}`;
                    appendLi.querySelector("div.css-1cvf9dk div.css-1agoci2").innerHTML = idx.name;
                    appendLi.querySelector("div.css-ob93md a").href = `/comment/${idx.idx}`;
                    appendLi.querySelector("div.css-1yrlzf9-StyledText").innerHTML = idx.text;
                    appendLi.querySelector("em.like-sum").innerHTML = idx.likeSum;
                    appendLi.querySelector("em.recomm-sum").innerHTML = idx.recommSum;
                    if(idx.hasLike == true){
                        appendLi.querySelector("button.css-1jrmj77-StylelessButton").setAttribute('class',"css-jj4q3s-StylelessButton-UserActionButton");
                    }
                    appendLi.style.display='inline-block'
                    commentUl.appendChild(appendLi)
                }else{
                    let appendLi = notSpoilerLi.cloneNode(true)
                    appendLi.querySelector("div.css-1cvf9dk").querySelector("a.css-1f9m1s4-StylelessLocalLink").href = `/user/${idx.user.userIdx}`;
                    appendLi.querySelector("div.css-1cvf9dk div.css-1agoci2").innerHTML = idx.name;
                    appendLi.querySelector("div.css-ob93md a").href = `/comment/${idx.idx}`;
                    appendLi.querySelector("div.css-qxbzku-StyledText").innerHTML = idx.text;
                    appendLi.querySelector("em.like-sum").innerHTML = idx.likeSum;
                    appendLi.querySelector("em.recomm-sum").innerHTML = idx.recommSum;
                    if(idx.hasLike == true){
                        appendLi.querySelector("button.css-1h18l7j-StylelessButton").setAttribute('class',"css-jj4q3s-StylelessButton-UserActionButton");
                    }
                    appendLi.style.display='inline-block'
                    commentUl.appendChild(appendLi)
                }
            }
            commentList = document.querySelectorAll("div.css-bawlbm")
            if(data.commentList.last == true){
                loading = true;
                $(`div.css-5hpf69`).remove();
            }else{
                loading = false;
                page++;
            }
        }
        , error: function () {
        }
    });
}

try {
    const commentScroll = document.querySelector("[data-rowindex='10']").querySelector('.css-15xcaei')
    commentScroll.addEventListener('scroll', () => {
        let comMax = commentScroll.scrollWidth - commentScroll.querySelector('.css-174lxc3').getBoundingClientRect().width - 15;
        if (commentScroll.scrollLeft >= comMax) {
            if (!loading)    //실행 가능 상태라면?
            {
                loading = true; //실행 불가능 상태로 변경
                addList();
            }
        }
    });
}catch (Exception){console.log("코멘트가 없음")}