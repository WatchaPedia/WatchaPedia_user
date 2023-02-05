$(function() {
    const { createApp } = Vue

    let movList= {};
    let tvList= {};
    let bookList= {};
    let webtoonList= {};

    let itemList = createApp({
        data() {
            return {
                itemList: {}
            }
        }
    }).mount('#content-ul');

    $.ajax({
        url: "/estimate/page?page=0",
        headers: {'Content-Type': 'application/json;charset=UTF-8'},
        type: 'get',
        complete:function(){
            loadingIcon.style.display='none'
        },success:function (data){
            itemList.itemList = data.content
        }
    })

let movPage = 1;
let tvPage = 0;
let bookPage = 0;
let webPage = 0;

const loadingIcon = document.querySelector("#loading-icon");
const movieList = document.querySelector("section.css-vozok1-ContentsSection");
// 셀렉트탭 변경
document.querySelector(".css-lshjof-VisualUl").addEventListener("click",(e)=>{
    let selectType = document.querySelector(".css-96eosw");
    selectType.setAttribute("class","css-kx8pjj")
    e.target.setAttribute("class","css-96eosw");

    let contentType = e.target.innerHTML == '영화' ? 'movie' : (e.target.innerHTML == 'TV 프로그램' ? 'tv'
        : (e.target.innerHTML == '책' ? 'book' : 'webtoon'))
    let page = e.target.innerHTML == '영화' ? movPage : (e.target.innerHTML == 'TV 프로그램' ? tvPage
        : (e.target.innerHTML == '책' ? bookPage : webPage))
    console.log(page)
    if(page == 0){
        $.ajax({
            url: `/estimate/page?page=${page}`,
            headers: {'Content-Type': 'application/json;charset=UTF-8'},
            data: JSON.stringify({
                contentType:contentType
            }),
            type: 'get',
            dataType: "json",
            beforeSend:function(){
                loadingIcon.style.display='block'
            },
            complete:function(){
                loadingIcon.style.display='none'
            },
            success: function (data) {
                console.log(data)
                e.target.innerHTML == '영화' ? movList = data.content : (e.target.innerHTML == 'TV 프로그램' ? tvList = data.content
                    : (e.target.innerHTML == '책' ? bookList = data.content : webtoonList = data.content));
                e.target.innerHTML == '영화' ? itemList.itemList = data.content : (e.target.innerHTML == 'TV 프로그램' ? itemList.itemList = data.content
                    : (e.target.innerHTML == '책' ? itemList.itemList = data.content : itemList.itemList = data.content));
                console.log(itemList)
                $('.css-1nukiuq-VisualUl-ReviewUl').replaceWith(data);
                if(data.last == true){
                    loading = true;
                    $(`div.css-5hpf69`).remove();
                }else{
                    loading = false;
                    page++;
                }
            }
            , error: function () {
                console.log("에러")
            }
        });
    }else{
        let clonList = movieList.cloneNode(true);


        document.querySelector("div.css-12hxjcc-StyledHideableBlock").appendChild(clonList);
    }
})
})