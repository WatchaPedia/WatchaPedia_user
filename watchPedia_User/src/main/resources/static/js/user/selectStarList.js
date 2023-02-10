$(document).ready(function () {
    const userIdx = window.location.href.split("/user/")[1].split("/")[0]
    const starPoint = window.location.href.split("/ratings/")[1]
    const contentType = window.location.href.split(`/user/${userIdx}/`)[1].split("/ratings")[0]
    const loadingIcon = document.querySelector("#loading-icon");

    let itemBox = new Array()

    let itemList = createApp({
        data() {
            return {
                itemList: {},
                contentType: contentType,
                userIdx: userIdx,
                starPoint: starPoint
            }
        }
    }).mount("#item-list")


    let page = 0;
    let scrollRec = false;

    window.onload = itemPlus()
    function itemPlus(){
        if(page != 'last'){
            $.ajax({
                url:`/user/${userIdx}/${contentType}/ratings/${starPoint}/new?page=${page}`,
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
                        itemList.itemList = JSON.parse(str)
                        if(data.last ==true) page = 'last'
                        else page++;
                    }else itemList.itemList = false;

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
