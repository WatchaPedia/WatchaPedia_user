const header = document.querySelector('.css-4g5dyy-HeaderBarPrimitive');
const div1 = document.querySelector('.css-1d4ojes');
const div2 = document.querySelector('.css-1bvesam');
const div3 = document.querySelector('.css-10bjf2k');
const headerHeight = header.getBoundingClientRect().height;

document.addEventListener('scroll', () => { // 스크롤시 이벤트 발생
    if(window.scrollY > (headerHeight*2)){ // 스크롤을 navbar 높이의 두배만큼 내렸을때 navbar--bold 클래스 추가
        div1.classList.add('css-7d0ya9');
        div1.classList.remove('css-1d4ojes');

        div2.classList.add('css-mp8yzl');
        div2.classList.remove('css-1bvesam');

        div3.classList.add('css-15eb5z0');
        div3.classList.remove('css-10bjf2k');
    } else {
        div1.classList.add('css-1d4ojes');
        div1.classList.remove('css-7d0ya9');

        div2.classList.add('css-1bvesam');
        div2.classList.remove('css-mp8yzl');

        div3.classList.add('css-10bjf2k');
        div3.classList.remove('css-15eb5z0');
    }
})

let commentList = document.querySelectorAll("div.css-bawlbm")
const loginIdx = document.querySelector("#login-idx")

document.addEventListener('click',(e)=>{
    if(e.target.parentElement.classList.contains("css-hy68ty")){
        if(loginIdx){
            let idx = e.target.parentElement.parentElement
            let likeSum = idx.querySelector("em.like-sum")
            $.ajax({
                url: '/comment/like/save',
                headers: {'Content-Type': 'application/json;charset=UTF-8'},
                data: JSON.stringify({           // HTTP 요청과 함께 서버로 보낼 데이터
                    userIdx: parseInt(loginIdx.title),
                    commentIdx: parseInt(idx.id)
                }),
                type: 'POST',           // HTTP 요청 방식(GET, POST)
                dataType: "json",       // 호출 시 데이터 타입
                success: function (data) {
                    if (data == true) {
                        idx.querySelector("button.css-1h18l7j-StylelessButton").classList.add("css-jj4q3s-StylelessButton-UserActionButton");
                        idx.querySelector("button.css-1h18l7j-StylelessButton").classList.remove("css-1h18l7j-StylelessButton");
                        likeSum.innerHTML = parseInt(likeSum.innerHTML) + 1;
                    } else {
                        idx.querySelector("button.css-jj4q3s-StylelessButton-UserActionButton").classList.add("css-1h18l7j-StylelessButton");
                        idx.querySelector("button.css-jj4q3s-StylelessButton-UserActionButton").classList.remove("css-jj4q3s-StylelessButton-UserActionButton");
                        likeSum.innerHTML = parseInt(likeSum.innerHTML) - 1;
                    }
                }, error: function () {
                    alert("에러발생!")
                }
            })
        }else{
            document.querySelector(".css-14gy7wr").style.display='block';
        }
    }
})


// 이전 댓글 더보기
const contentType = window.location.href.split('/')[window.location.href.split('/').length-3]
const contentIdx = window.location.href.split('/')[window.location.href.split('/').length-2]
let loading = false;
let page = 1;

const commentUl = document.querySelector("div.css-tbg13q-CommentLists ul.css-10n5vg9-VisualUl")
const cloneLi = document.querySelector("div#clone-li")

const loadingIcon = document.querySelector("#loading-icon");
function addList() {
    $.ajax({
        url: `/${contentType}/${contentIdx}/comments/new?page=${page}`,
        headers: {'Content-Type': 'application/json;charset=UTF-8'},
        type: 'GET',
        dataType: "json",
        beforeSend:function(){
            loadingIcon.style.display='flex'
        },
        complete:function(){
            loadingIcon.style.display='none'
            spoAct()
        },
        success: function (data) {
            for (let idx of data.commentList.content) {
                let appendLi = cloneLi.cloneNode(true)
                appendLi.style.display = 'block'
                appendLi.setAttribute('id', idx.idx);
                appendLi.querySelector("div.css-1cvf9dk").querySelector("a.css-1f9m1s4-StylelessLocalLink").href = `/user/${idx.user.userIdx}`;
                appendLi.querySelector("div.css-1cvf9dk").querySelector("a.css-1f9m1s4-StylelessLocalLink").title = idx.name;
                appendLi.querySelector("div.css-1cvf9dk div.css-1agoci2").innerHTML = idx.name;
                appendLi.querySelector("div.css-4tkoly a").href = `/comment/${idx.idx}`;
                appendLi.querySelector("div.css-4tkoly span").innerHTML = idx.text;
                if(idx.spoiler){
                    appendLi.querySelector("div.css-4tkoly span").style.display='none'
                    appendLi.querySelector("div.css-1atijos").setAttribute("class","css-1jm9uak")
                    appendLi.querySelector("span.css-64x8kr").style.backgroundImage='url(data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0Ij4KICAgIDxnIGZpbGw9IiNkNGQ0ZDQiIGZpbGwtcnVsZT0iZXZlbm9kZCI+CiAgICAgICAgPHBhdGggZD0iTTMgMjFoNVY5SDN6TTE0LjkwMiA3Ljk5NGg0LjkzOGMuODggMCAxLjU5MS43IDEuNTkxIDEuNTY2IDAgLjg2Ni0uNzEyIDEuNTY3LTEuNTkgMS41NjdoLTQuOTM5Yy0uMzk4IDAtLjYxNS0uMDU1LS44MTItLjE1OGExLjA4NSAxLjA4NSAwIDAgMS0uNDUzLS40NDdjLS4xMDQtLjE5Mi0uMTYtLjQwNS0uMTYtLjc5OHYtLjMyN2MwLS4zOTMuMDU2LS42MDYuMTYtLjguMTA1LS4xOTEuMjU4LS4zNDIuNDUzLS40NDUuMTk3LS4xMDMuNDE0LS4xNTguODEyLS4xNTgiLz4KICAgICAgICA8cGF0aCBkPSJNMTQuMDYgMTFIMTlhMS41OCAxLjU4IDAgMCAxIDEuNTkgMS41NjhjMCAuODY1LS43MTIgMS41NjYtMS41OSAxLjU2NmgtNC45NGMtLjM5OCAwLS42MTUtLjA1NS0uODEtLjE1N2ExLjA4MSAxLjA4MSAwIDAgMS0uNDU0LS40NDhjLS4xMDUtLjE5Mi0uMTYtLjQwNS0uMTYtLjc5OHYtLjMyN2MwLS4zOTIuMDU1LS42MDYuMTYtLjguMTA0LS4xOTEuMjU4LS4zNDIuNDUzLS40NDUuMTk2LS4xMDMuNDEzLS4xNTguODExLS4xNTgiLz4KICAgICAgICA8cGF0aCBkPSJNMTMuMjIgMTQuMDA5aDQuOTM4Yy44NzkgMCAxLjU5LjcgMS41OSAxLjU2NiAwIC44NjYtLjcxMSAxLjU2Ny0xLjU5IDEuNTY3SDEzLjIyYy0uMzk4IDAtLjYxNS0uMDU1LS44MTEtLjE1N2ExLjA5NSAxLjA5NSAwIDAgMS0uNDU0LS40NDhjLS4xMDQtLjE5Mi0uMTYtLjQwNi0uMTYtLjc5OHYtLjMyOGMwLS4zOTEuMDU2LS42MDUuMTYtLjc5OGExLjA5IDEuMDkgMCAwIDEgLjQ1NC0uNDQ2Yy4xOTYtLjEwMy40MTMtLjE1OC44MS0uMTU4Ii8+CiAgICAgICAgPHBhdGggZD0iTTEyLjM3OCAxNy4wMTdoNC45NGMuODc4IDAgMS41OS42NjggMS41OSAxLjQ5IDAgLjgyNC0uNzEyIDEuNDkyLTEuNTkgMS40OTJoLTQuOTRjLS4zOTggMC0uNjE1LS4wNS0uODEtLjE1YTEuMDU4IDEuMDU4IDAgMCAxLS40NTQtLjQyNWMtLjEwNS0uMTgzLS4xNi0uMzg2LS4xNi0uNzZ2LS4zMTJjMC0uMzczLjA1NS0uNTc3LjE2LS43Ni4xMDQtLjE4My4yNTgtLjMyNy40NTMtLjQyNS4xOTYtLjA5OS40MTMtLjE1LjgxMS0uMTUiLz4KICAgICAgICA8cGF0aCBkPSJNMTMuMjAyIDUuMTYyYS45NC45NCAwIDAgMCAuMjc1LS42NjVWMy4yOTZjLS4wNjYtLjUzLjItLjc5Ni43OTUtLjc5Ni44OTYgMCAyLjM4Ny40NzcgMi4zODcgMi4zODcgMCAxLjI3My0uMjY2IDIuMzMzLS43OTYgMy4xODJ2MTEuOTNIOS41VjkuNjZjMC0uNTMxLjI2NS0xLjA2MS43OTUtMS41OTFsMi45MDctMi45MDd6Ii8+CiAgICA8L2c+Cjwvc3ZnPgo=)';
                    appendLi.querySelector("span.css-64x8kr").setAttribute("class","css-zoh368")
                    appendLi.querySelector("span.css-q0vi8").style.backgroundImage= 'url(data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0Ij4KICAgIDxwYXRoIGZpbGw9IiNkNGQ0ZDQiIGZpbGwtcnVsZT0iZXZlbm9kZCIgZD0iTTkuODcgMTguMDE5bC0zLjMxNCAzLjMxNHYtNC41N2MtMi4zNTYtMS40MTItMy44OS0zLjcxNi0zLjg5LTYuMzE5IDAtNC4yOTUgNC4xOC03Ljc3NyA5LjMzNC03Ljc3NyA1LjE1NSAwIDkuMzMzIDMuNDgyIDkuMzMzIDcuNzc3IDAgNC4yOTYtNC4xNzggNy43NzgtOS4zMzMgNy43NzgtLjczMyAwLTEuNDQ2LS4wNy0yLjEzLS4yMDN6Ii8+Cjwvc3ZnPgo=)';
                    appendLi.querySelector("span.css-q0vi8").setAttribute("class","css-43cye7")

                }else{
                    appendLi.querySelector("div.css-4tkoly span.css-xstsdj").style.display='none'
                }
                appendLi.querySelector("em.like-sum").innerHTML = idx.likeSum;
                appendLi.querySelector("em.recomm-sum").innerHTML = idx.recommSum;
                if(idx.hasLike == true){
                    appendLi.querySelector("div.css-hy68ty button").setAttribute('class',"css-jj4q3s-StylelessButton-UserActionButton");
                }else{
                    appendLi.querySelector("div.css-hy68ty button").setAttribute('class',"css-1h18l7j-StylelessButton");
                }
                commentUl.appendChild(appendLi)
            }
            if(data.commentList.last == true){
                loading = true;
                $(`div.css-5hpf69`).remove();
            }else{
                loading = false;
                page++;
            }
            commentList = document.querySelectorAll("div.css-bawlbm")
        }
        , error: function () {
        }
    });
}


window.addEventListener('scroll',()=>{
    if((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
        if(!loading)    //실행 가능 상태라면?
        {
            loading = true; //실행 불가능 상태로 변경
            addList();
        }
    }
});

window.onload = spoAct()

// 스포일러 댓글
function spoAct(){
    let commList = document.querySelectorAll("div.css-tbg13q-CommentLists div.css-bawlbm")
    for(let idx of commList){
        if(idx.querySelector("div.css-1jm9uak")){
            idx.querySelector("div.css-hy68ty button").setAttribute("disabled",'disabled')
        }
    }
}
document.addEventListener('click',(e)=>{
    if(e.target.classList.contains("css-13mdv8k-StylelessButton")){
        console.log("클릭")
        let parent = e.target.parentElement.parentElement.parentElement;
        e.target.parentElement.style.display='none'
        parent.querySelector("a.css-1f9m1s4-StylelessLocalLink span").style.display = 'block'

        parent.querySelector("div.css-hy68ty button").disabled = ''
        if(parent.querySelector("button.css-1jrmj77-StylelessButton")){
            parent.querySelector("button.css-1jrmj77-StylelessButton").setAttribute('class','css-1h18l7j-StylelessButton')
        }
        parent.querySelector("div.css-1jm9uak").setAttribute("class",'css-1atijos')
        parent.querySelector("span.css-zoh368").style.backgroundImage='url(data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0Ij4KICAgIDxnIGZpbGw9Im5vbmUiIGZpbGwtcnVsZT0iZXZlbm9kZCI+CiAgICAgICAgPGcgZmlsbD0iIzc4Nzg3OCI+CiAgICAgICAgICAgIDxwYXRoIGQ9Ik02Ljc1IDkuNDg1aC0zYTEgMSAwIDAgMC0xIDF2MTBhMSAxIDAgMCAwIDEgMWgzYTEgMSAwIDAgMCAxLTF2LTEwYTEgMSAwIDAgMC0xLTFNMjAuNjU3IDguNTY2YTIuMzYzIDIuMzYzIDAgMCAwLTEuNzc5LS44MTNIMTYuNjJsLjE2NC0uNjI3Yy4xMzctLjUyOC4yMDEtMS4xMi4yMDEtMS44NjMgMC0xLjkxOS0xLjM3NS0yLjc3OC0yLjczOC0yLjc3OC0uNDQ0IDAtLjc2Ni4xMjMtLjk4Ni4zNzYtLjIuMjI3LS4yODIuNTMtLjI0My45MzVsLjAzIDEuMjMtMi45MDMgMi45NGMtLjU5My42LS44OTQgMS4yMy0uODk0IDEuODcydjkuNjQ3YS41LjUgMCAwIDAgLjUuNWg3LjY4N2EyLjM4OCAyLjM4OCAwIDAgMCAyLjM0OC0yLjA3bDEuNDQ1LTcuNDUyYTIuNDQgMi40NCAwIDAgMC0uNTc0LTEuODk3Ii8+CiAgICAgICAgPC9nPgogICAgPC9nPgo8L3N2Zz4K)'
        parent.querySelector("span.css-zoh368").setAttribute('class','css-64x8kr')
        parent.querySelector("span.css-43cye7").style.backgroundImage='url(data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0Ij4KICAgIDxwYXRoIGZpbGw9IiM3ODc4NzgiIGZpbGwtcnVsZT0iZXZlbm9kZCIgZD0iTTkuODU3IDE3Ljc4Nkw2IDIxdi00LjkxYy0xLjg0MS0xLjM3My0zLTMuMzY5LTMtNS41OUMzIDYuMzU4IDcuMDMgMyAxMiAzczkgMy4zNTggOSA3LjVjMCA0LjE0Mi00LjAzIDcuNS05IDcuNS0uNzM5IDAtMS40NTYtLjA3NC0yLjE0My0uMjE0eiIvPgo8L3N2Zz4K)'
        parent.querySelector("span.css-43cye7").setAttribute('class','css-q0vi8')
    }
})
