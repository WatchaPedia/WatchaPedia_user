const header = document.querySelector('header.css-1mxbo8g-HeaderBarPrimitive');
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
    const{createApp}=Vue;

    const userIdx = window.location.href.split("/user/")[1].split("/")[0]
    const contentType = window.location.href.split(`/user/${userIdx}/`)[1].split("/")[0]
    const type = window.location.href.split(`/${contentType}/`)[1]
    const loadingIcon = document.querySelector("#loading-icon");

    let itemBox = new Array()
    let itemList = createApp({
        data() {
            return {
                itemList: {},
                contentType: contentType,
                userIdx: userIdx,
                type:type
            }
        }
    }).mount("#item-list")


    let page = 0;
    let scrollRec = false;

    window.onload = itemPlus()
    function itemPlus(){
        if(page != 'last'){
            $.ajax({
                url:`/user/${userIdx}/${contentType}/${type}/new?page=${page}`,
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
                    console.log(data)
                    if(data.content.length != 0){
                        itemBox.push(data.content)
                        let str = "";
                        itemBox.forEach(con => str += JSON.stringify(con))
                        str = str.replaceAll("][",",")
                        itemList.itemList = JSON.parse(str)
                        if(data.last ==true) page = 'last'
                        else page++;
                    }else{
                        itemList.itemList = false
                    }
                }
            })
        }
    }

    window.addEventListener('scroll',()=>{
        if((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
            // 스크롤 가능하면 실행
            if(!scrollRec){
                scrollRec = true;
                itemPlus();
            }
        }
    });
})
