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
    const userIdx = window.location.href.split("/user/")[1].split("/")[0]
    const contentType = window.location.href.split(`/user/${userIdx}/`)[1].split("/ratings")[0]
    const loadingIcon = document.querySelector("#loading-icon");

    const allListBtn = document.querySelector("ul.css-1e0vaz3-VisualUl").querySelectorAll("li").item(0)
    const starListBtn = document.querySelector("ul.css-1e0vaz3-VisualUl").querySelectorAll("li").item(1)

    let star5=new Array()
    let star4=new Array()
    let star3=new Array()
    let star2=new Array()
    let star1=new Array()
    let itemBox = new Array()

    let itemList = createApp({
        data() {
            return {
                itemList: {},
                contentType: contentType,
                userIdx: userIdx,
                star5: {},
                star4: {},
                star3: {},
                star2: {},
                star1: {}
            }
        }
    }).mount("#content-list")

    let page = 0;
    let starPage = 0;

    document.querySelector("ul.css-1e0vaz3-VisualUl").addEventListener("click",(e)=>{
        if(!e.target.classList.contains("css-1qee6f7")){
            document.querySelector(".css-1qee6f7").setAttribute("class","css-vko0h7")
            e.target.setAttribute("class","css-1qee6f7")
            if(e.target.innerHTML == '전체'){
                document.querySelector("#content-star-list").style.display = 'none'
                document.querySelector("#content-all-list").style.display = 'block'
            }
            else{
                if(starPage == 0) starItemPlus();
                document.querySelector("#content-all-list").style.display = 'none'
                document.querySelector("#content-star-list").style.display = 'block'
            }
        }
    })

    window.onload = itemPlus()

    function itemPlus(){
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
            },
            success: function (data) {

                itemBox.push(data.content)
                let str = "";
                itemBox.forEach(con => str += JSON.stringify(con))
                str.replaceAll("][",",")
                itemList.itemList = JSON.parse(str)
                page++;
            }
        })
    }

    function starItemPlus(){
        $.ajax({
            url:`/user/${userIdx}/${contentType}/ratings/list?page=${starPage}`,
            headers: {'Content-Type': 'application/json;charset=UTF-8'},
            type: 'GET',
            dataType: "json",
            beforeSend: function(){
                loadingIcon.style.display = 'block';
            },
            complete: function(){
                loadingIcon.style.display = 'none';
            },
            success: function (data) {
                for (let idx of data.content) {
                    if(idx.starPoint == 5) star5.push(idx)
                    if(idx.starPoint == 4) star4.push(idx)
                    if(idx.starPoint == 3) star3.push(idx)
                    if(idx.starPoint == 2) star2.push(idx)
                    if(idx.starPoint == 1) star1.push(idx)
                }
                itemList.star5 = star5
                itemList.star4 = star4
                itemList.star3 = star3
                itemList.star2 = star2
                itemList.star1 = star1
                console.log(star5)
                starPage++;
            }
        })
    }
})