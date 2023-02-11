const header = document.querySelector('.css-1mxbo8g-HeaderBarPrimitive');
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

$(document).ready(function () {
    window.addEventListener('resize', () => {
        scrollContent()
    })

    // 메인 스크롤
    function scrollContent(e) {
        let container = document.querySelectorAll(".css-usdi1z");
        for (let idx of container) {
            // 스크롤 위치에 따라 버튼 유무
            idx.querySelector('.css-9dnzub').addEventListener('scroll', () => {
                if (idx.querySelector('.css-9dnzub').scrollLeft >= idx.querySelector('.css-9dnzub').scrollWidth - (idx.querySelector('.css-174lxc3').getBoundingClientRect().width + 10)) {
                    idx.querySelector('.css-vp7uyl').style.display = "none";
                } else {
                    idx.querySelector('.css-vp7uyl').style.display = "flex";
                }
                if (idx.querySelector('.css-9dnzub').scrollLeft == 0) {
                    idx.querySelector('.css-1hestod').style.display = "none";
                } else {
                    idx.querySelector('.css-1hestod').style.display = "block";
                }
            })
            $(idx).hover(scrollBtn,scrollBtnHidden)
            $(idx).find(".css-pf83cl").hover(scrollBtn,scrollBtnHidden)
            $(idx).find(".css-38kpup").hover(scrollBtn,scrollBtnHidden)

            function scrollBtn() {
                if(idx.querySelector(".css-vp7uyl")){
                    idx.querySelector(".css-pf83cl").style.opacity = '1'
                    idx.querySelector(".css-38kpup").style.opacity = '1'
                }
            };
            function scrollBtnHidden() {
                if(idx.querySelector(".css-vp7uyl")){
                    idx.querySelector(".css-pf83cl").style.opacity = '0'
                    idx.querySelector(".css-38kpup").style.opacity = '0'
                }
            };
        }

    }
    document.addEventListener('click',(e)=>{
        let container = document.querySelectorAll(".css-usdi1z");
        // 버튼 클릭 시 이동
        for (let idx of container) {
            if(idx.querySelector(".css-1hestod")){
                if(idx.querySelector(".css-vp7uyl").contains(e.target)){
                    idx.querySelector('.css-9dnzub').scrollBy(idx.querySelector('.e1689zdh0').getBoundingClientRect().width - 17, 0)

                }
                if(idx.querySelector(".css-1hestod").contains(e.target)){
                    idx.querySelector('.css-9dnzub').scrollBy(-idx.querySelector('.e1689zdh0').getBoundingClientRect().width - 17, 0)

                }
            }
        }
    })

    const userIdx = window.location.href.split("/user/")[1].split("/")[0]
    const contentType = window.location.href.split(`/user/${userIdx}/`)[1].split("/ratings")[0]
    const loadingIcon = document.querySelector("#loading-icon");

    let starList5=new Array()
    let starList4=new Array()
    let starList3=new Array()
    let starList2=new Array()
    let starList1=new Array()
    let itemBox = new Array()

    let allList = createApp({
        data() {
            return {
                itemList: {},
                contentType: contentType,
                userIdx: userIdx
            }
        }
    }).mount("#content-all-list")

    let starList = createApp({
        data() {
            return {
                contentType: contentType,
                userIdx: userIdx,
                star5: {},
                star4: {},
                star3: {},
                star2: {},
                star1: {},
                size5: 0,
                size4: 0,
                size3: 0,
                size2: 0,
                size1: 0
            }
        }
    }).mount("#content-star-list")

    let page = 0;
    let starPage = 0;
    let scrollRec = false;

    document.querySelector("ul.css-1e0vaz3-VisualUl").addEventListener("click",(e)=>{
        if(!e.target.classList.contains("css-1qee6f7")){
            document.querySelector(".css-1qee6f7").setAttribute("class","css-vko0h7")
            e.target.setAttribute("class","css-1qee6f7")
            if(e.target.innerHTML == '전체'){
                document.querySelector("#content-star-list").style.display = 'none'
                document.querySelector("#content-all-list").style.display = 'block'
                if(document.querySelector(".css-gtvt2o-EmptySection")){
                    document.querySelector(".css-gtvt2o-EmptySection").style.display = 'flex'
                }
            }
            else{
                if(starPage == 0) starItemPlus();
                document.querySelector("#content-all-list").style.display = 'none'
                document.querySelector("#content-star-list").style.display = 'block'
                if(document.querySelector(".css-gtvt2o-EmptySection")){
                    document.querySelector(".css-gtvt2o-EmptySection").style.display = 'none'
                }
            }
        }
    })

    window.onload = itemPlus()
    function itemPlus(){
        if(page != 'last'){
            $.ajax({
                url:`/user/${userIdx}/${contentType}/ratings/list?page=${page}`,
                headers: {'Content-Type': 'application/json;charset=UTF-8'},
                type: 'GET',
                dataType: "json",
                beforeSend: function(){
                    loadingIcon.style.display = 'block';
                },
                complete: function(){
                    loadingIcon.style.display = 'none';
                    scrollRec = false;
                },
                success: function (data) {
                    if(data.content.length != 0){
                        itemBox.push(data.content)
                        let str = "";
                        itemBox.forEach(con => str += JSON.stringify(con))
                        str = str.replaceAll("][",",")
                        allList.itemList = JSON.parse(str)
                        if(data.last ==true) page = 'last'
                        else page++;
                    }else allList.itemList = false;
                }
            })
        }
    }

    function starItemPlus(){
        if(starPage != 'last'){
            $.ajax({
                url:`/user/${userIdx}/${contentType}/ratings/starpointList?page=${starPage}`,
                headers: {'Content-Type': 'application/json;charset=UTF-8'},
                type: 'GET',
                dataType: "json",
                beforeSend: function(){
                    loadingIcon.style.display = 'block';
                    scrollRec = true;
                },
                complete: function(){
                    loadingIcon.style.display = 'none';
                    scrollRec = false;
                    scrollContent()
                    starListScroll();
                },
                success: function (data) {
                    for (let idx of data.content) {
                        if(idx.starPoint == 5) starList5.push(idx)
                        if(idx.starPoint == 4) starList4.push(idx)
                        if(idx.starPoint == 3) starList3.push(idx)
                        if(idx.starPoint == 2) starList2.push(idx)
                        if(idx.starPoint == 1) starList1.push(idx)
                    }
                    starList.star5 = {}
                    starList.star4 = {}
                    starList.star3 = {}
                    starList.star2 = {}
                    starList.star1 = {}
                    starList.star5 = starList5
                    starList.star4 = starList4
                    starList.star3 = starList3
                    starList.star2 = starList2
                    starList.star1 = starList1
                    starList.size5 = data.size5
                    starList.size4 = data.size4
                    starList.size3 = data.size3
                    starList.size2 = data.size2
                    starList.size1 = data.size1
                    starPage++;
                }
            })
        }
    }

    window.addEventListener('scroll',()=>{
        if((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
            // 스크롤 가능하면 실행
            if(document.querySelector('li.css-1qee6f7').innerHTML == '전체'){
                if(!scrollRec){
                    scrollRec = true;
                    itemPlus();
                }
            }
        }
    });
    function starListScroll(){
        for(let idx of document.querySelectorAll(".css-9dnzub")){
            idx.addEventListener('scroll',(e)=>{
                if(idx.scrollLeft >= idx.scrollWidth-(idx.querySelector('.e1689zdh0').getBoundingClientRect().width+5)) {
                    // 스크롤 가능하면 실행
                    if(!scrollRec){
                        starItemPlus();
                    }
                }
            })
        }
    }


})
