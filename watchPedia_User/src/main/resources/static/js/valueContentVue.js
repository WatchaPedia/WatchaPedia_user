$(function() {
    const { createApp } = Vue

    let movList= new Array();
    let tvList= new Array();
    let bookList= new Array();
    let webtoonList= new Array();

    let itemList = createApp({
        data() {
            return {
                itemList: {}
            }
        }
    }).mount('#content-ul');

    window.onload = startLoad;
    function startLoad(){
        $.ajax({
            url: "/estimate/movie?page=0",
            headers: {'Content-Type': 'application/json;charset=UTF-8'},
            type: 'get',
            complete:function(){
                loadingIcon.style.display='none'
            },success:function (data){
                if(data.content.length != 0){
                    movList.push(data.content)
                    let str = ""
                    movList.forEach(con=>str += JSON.stringify(con))
                    str = str.replaceAll("][",",")
                    itemList.itemList = JSON.parse(str)

                    if(itemList.itemList.length <= 4){
                        addList()
                    }
                }else{
                    addList()
                }
            }
        })
    }

let movPage = 1;
let tvPage = 0;
let bookPage = 0;
let webPage = 0;

let movScrollY = 0;
let tvScrollY = 0;
let bookScrollY = 0;
let webScrollY = 0;

const loadingIcon = document.querySelector("#loading-icon");
const movieList = document.querySelector("section.css-vozok1-ContentsSection");
// 셀렉트탭 변경
document.querySelector(".css-lshjof-VisualUl").addEventListener("click",(e)=>{
    let selectType = document.querySelector(".css-96eosw");
    selectType.setAttribute("class","css-kx8pjj")
    e.target.setAttribute("class","css-96eosw");

    document.querySelector("#movie-star").style.display='none'
    document.querySelector("#tv-star").style.display='none'
    document.querySelector("#book-star").style.display='none'
    document.querySelector("#web-star").style.display='none'

    e.target.innerHTML == '영화' ? document.querySelector("#movie-star").style.display='flex' : (e.target.innerHTML == 'TV 프로그램' ? document.querySelector("#tv-star").style.display='flex'
        : (e.target.innerHTML == '책' ? document.querySelector("#book-star").style.display='flex' : document.querySelector("#web-star").style.display='flex'))
    e.target.innerHTML == '영화' ? document.querySelector(".css-q9kfw1").innerHTML ='랜덤 영화' : (e.target.innerHTML == 'TV 프로그램' ? document.querySelector(".css-q9kfw1").innerHTML ='랜덤 TV 프로그램'
        : (e.target.innerHTML == '책' ? document.querySelector(".css-q9kfw1").innerHTML ='랜덤 책' : document.querySelector(".css-q9kfw1").innerHTML ='랜덤 웹툰'))

    let contentType = e.target.innerHTML == '영화' ? 'movie' : (e.target.innerHTML == 'TV 프로그램' ? 'tv'
        : (e.target.innerHTML == '책' ? 'book' : 'webtoon'))
    let page = e.target.innerHTML == '영화' ? movPage : (e.target.innerHTML == 'TV 프로그램' ? tvPage
        : (e.target.innerHTML == '책' ? bookPage : webPage))

    if(page==0){
        window.scrollTo(0,0)
        $.ajax({
            url: `/estimate/${contentType}?page=0`,
            headers: { 'Content-Type': 'application/json;charset=UTF-8' },
            type: 'get',
            beforeSend:function(){
                loadingIcon.style.display='block'
            },
            complete:function(){
                loadingIcon.style.display='none'
            },
            success: function (data) {
                // 각 페이지 +1
                e.target.innerHTML == '영화' ? movPage++ : (e.target.innerHTML == 'TV 프로그램' ? tvPage++
                    : (e.target.innerHTML == '책' ? bookPage++ : webPage++))
                if(data.content.length != 0){
                    // 리스트에 추가
                    e.target.innerHTML == '영화' ? movList.push(data.content) : (e.target.innerHTML == 'TV 프로그램' ? tvList.push(data.content)
                        : (e.target.innerHTML == '책' ? bookList.push(data.content)  : webtoonList.push(data.content)));
                    // 리스트 반복문으로 스트링에 입력
                    let str = "";
                    e.target.innerHTML == '영화' ? movList.forEach(con=>str += JSON.stringify(con)) : (e.target.innerHTML == 'TV 프로그램' ? tvList.forEach(con=>str += JSON.stringify(con))
                        : (e.target.innerHTML == '책' ? bookList.forEach(con=>str += JSON.stringify(con)) : webtoonList.forEach(con=>str += JSON.stringify(con))));
                    str = str.replaceAll("][",",")
                    // json 형태로 변경
                    itemList.itemList = JSON.parse(str)

                    if(itemList.itemList.length <= 4){
                        addList()
                    }
                }else{
                    addList()
                }
            }
            , error: function () {
                console.log("에러")
            }
        });
    }else{
        document.querySelector(".css-96eosw").innerHTML == '영화' ? window.scrollTo(0,movScrollY) : (document.querySelector(".css-96eosw").innerHTML == 'TV 프로그램' ? window.scrollTo(0,tvScrollY)
            : (document.querySelector(".css-96eosw").innerHTML == '책' ? window.scrollTo(0,bookScrollY) : window.scrollTo(0,webScrollY)));
        let jsonStr = "";
        e.target.innerHTML == '영화' ? movList.forEach(con=>jsonStr += JSON.stringify(con)) : (e.target.innerHTML == 'TV 프로그램' ? tvList.forEach(con=>jsonStr += JSON.stringify(con))
            : (e.target.innerHTML == '책' ? bookList.forEach(con=>jsonStr += JSON.stringify(con)) : webtoonList.forEach(con=>jsonStr += JSON.stringify(con))));
        jsonStr = jsonStr.replaceAll("][",",")
        itemList.itemList = JSON.parse(jsonStr)
    }
})

    // 스크롤 시 아이템리스트 추가
    let scrollRec = false;
    function addList() {
        let selectType = document.querySelector("li.css-96eosw").innerHTML;
        let contentType = selectType == '영화' ? 'movie' : (selectType == 'TV 프로그램' ? 'tv'
            : (selectType == '책' ? 'book' : 'webtoon'))
        let page = selectType == '영화' ? movPage : (selectType == 'TV 프로그램' ? tvPage
            : (selectType == '책' ? bookPage : webPage))
        if(page == 'last'){
            scrollRec = false;
        }else {
            selectType == '영화' ? movScrollY = window.scrollY : (selectType == 'TV 프로그램' ? tvScrollY = window.scrollY
                : (selectType == '책' ? bookScrollY = window.scrollY: webScrollY = window.scrollY));
            $.ajax({
                url: `/estimate/${contentType}?page=${page}`,
                headers: {'Content-Type': 'application/json;charset=UTF-8'},
                type: 'GET',
                dataType: "json",
                beforeSend: function () {
                    loadingIcon.style.display = 'block'
                    loadingIcon.style.bottom = 0;
                },
                complete: function () {
                    loadingIcon.style.display = 'none'
                    loadingIcon.style.bottom = '';
                    scrollRec = false;
                },
                success: function (data) {
                    // 리스트에 추가
                    selectType == '영화' ? movList.push(data.content) : (selectType == 'TV 프로그램' ? tvList.push(data.content)
                        : (selectType == '책' ? bookList.push(data.content) : webtoonList.push(data.content)));
                    // 리스트 반복문으로 스트링에 입력
                    let str = "";
                    selectType == '영화' ? movList.forEach(con => str += JSON.stringify(con)) : (selectType == 'TV 프로그램' ? tvList.forEach(con => str += JSON.stringify(con))
                        : (selectType == '책' ? bookList.forEach(con => str += JSON.stringify(con)) : webtoonList.forEach(con => str += JSON.stringify(con))));
                    str = str.replaceAll("][", ",")
                    // json 형태로 변경
                    itemList.itemList = JSON.parse(str)
                    if (data.last == true) {
                        selectType == '영화' ? movPage = 'last' : (selectType == 'TV 프로그램' ? tvPage = 'last'
                            : (selectType == '책' ? bookPage = 'last' : webPage = 'last'))
                    } else {
                        selectType == '영화' ? movPage++ : (selectType == 'TV 프로그램' ? tvPage++
                            : (selectType == '책' ? bookPage++ : webPage++))
                    }
                    if(itemList.itemList.length <= 4){
                        addList()
                    }
                }
                , error: function () {
                    console.log("에러")
                }
            });
        }
    }
    window.addEventListener('scroll',()=>{
        document.querySelector(".css-96eosw").innerHTML == '영화' ? movScrollY=window.scrollY : (document.querySelector(".css-96eosw").innerHTML == 'TV 프로그램' ? tvScrollY=window.scrollY
            : (document.querySelector(".css-96eosw").innerHTML == '책' ? bookScrollY=window.scrollY  : bookScrollY=window.scrollY ));
        if((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
            // 스크롤 가능하면 실행
            if(!scrollRec){
                scrollRec = true;
                addList();
            }
        }
    });
})

