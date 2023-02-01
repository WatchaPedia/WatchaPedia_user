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
const loginIdx = document.querySelector("#login-idx").title

document.addEventListener('click',(e)=>{
    if(e.target.parentElement.classList.contains("css-hy68ty")){
        let idx = e.target.parentElement.parentElement
        let likeSum = idx.querySelector("em.like-sum")
        $.ajax({
            url: '/comment/like/save',
            headers: {'Content-Type': 'application/json;charset=UTF-8'},
            data: JSON.stringify({           // HTTP 요청과 함께 서버로 보낼 데이터
                userIdx: parseInt(loginIdx),
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
    }
})


const contentType = window.location.href.split('/')[window.location.href.split('/').length-3]
const contentIdx = window.location.href.split('/')[window.location.href.split('/').length-2]
let loading = false;
let page = 1;

const commentUl = document.querySelector("div.css-tbg13q-CommentLists ul.css-10n5vg9-VisualUl")
const cloneLi = commentUl.querySelector("div.css-bawlbm")

function addList() {
    $.ajax({
        url: `/${contentType}/${contentIdx}/comments/new?page=${page}`,
        headers: {'Content-Type': 'application/json;charset=UTF-8'},
        type: 'GET',
        dataType: "json",
        success: function (data) {
            for (let idx of data.commentList.content) {
                let appendLi = cloneLi.cloneNode(true)
                appendLi.setAttribute('id', idx.idx);
                appendLi.querySelector("div.css-1cvf9dk").querySelector("a.css-1f9m1s4-StylelessLocalLink").href = `/user/${idx.user.userIdx}`;
                appendLi.querySelector("div.css-1cvf9dk").querySelector("a.css-1f9m1s4-StylelessLocalLink").title = idx.name;
                appendLi.querySelector("div.css-1cvf9dk div.css-1agoci2").innerHTML = idx.name;
                appendLi.querySelector("div.css-4tkoly a").href = `/comment/${idx.idx}`;
                appendLi.querySelector("div.css-4tkoly span").innerHTML = idx.text;
                appendLi.querySelector("em.like-sum").innerHTML = idx.likeSum;
                appendLi.querySelector("em.recomm-sum").innerHTML = idx.recommSum;
                if(idx.hasLike == true){
                    appendLi.querySelector("div.css-hy68ty button").setAttribute('class',"css-jj4q3s-StylelessButton-UserActionButton");
                }else{

                    appendLi.querySelector("div.css-hy68ty button").setAttribute('class',"css-1h18l7j-StylelessButton");
                }
                commentUl.appendChild(appendLi)
            }
            commentList = document.querySelectorAll("div.css-bawlbm")
            loading = false;
            page ++;
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