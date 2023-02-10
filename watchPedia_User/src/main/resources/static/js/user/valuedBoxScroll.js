$(document).ready(function () {
    // 버튼 클릭 시 이동
    function clickBtn(){
        let container = document.querySelector(".css-usdi1z");

        // 스크롤 위치에 따라 버튼 유무
        document.querySelector(".css-9dnzub").addEventListener('scroll', () => {
            if (document.querySelector(".css-9dnzub").scrollLeft >= document.querySelector(".css-9dnzub").scrollWidth - (document.querySelector(".css-9dnzub").querySelector('.css-174lxc3').getBoundingClientRect().width+2)) {
                container.querySelector('.css-vp7uyl').style.display = "none";
            } else {
                container.querySelector('.css-vp7uyl').style.display = "flex";
            }
            if (document.querySelector(".css-9dnzub").scrollLeft == 0) {
                container.querySelector('.css-1hestod').style.display = "none";
            } else {
                container.querySelector('.css-1hestod').style.display = "block";
            }
        })
        container.querySelector('.css-vp7uyl').addEventListener('click', function () {
            document.querySelector(".css-9dnzub").scrollBy(container.querySelector('.e1689zdh0').getBoundingClientRect().width-12,0)
        })
        container.querySelector('.css-1hestod').addEventListener('click', function () {
            document.querySelector(".css-9dnzub").scrollBy(-container.querySelector('.e1689zdh0').getBoundingClientRect().width-12,0)

        })
    }

    document.querySelector("#more-btn").href = "/user/"+window.location.href.split("/user/")[1]+"/ratings"

    const userIdx = window.location.href.split("/user/")[1].split("/")[0]
    const contentType = window.location.href.split(`/user/${userIdx}/`)[1].split("/ratings")[0]

    let itemBox = new Array()

    let itemList = createApp({
        data() {
            return {
                itemList: {},
                contentType: contentType,
                userIdx: userIdx,
                size: 0,
                wishSize:0,
                watchSize:0
            }
        }
    }).mount("#item-list")

    let page = 0;
    let scrollRec = false;

    window.onload = itemPlus()

    function itemPlus() {
        if (page != 'last') {
            $.ajax({
                url: `/user/${userIdx}/${contentType}/ratings/list?page=${page}`,
                headers: {'Content-Type': 'application/json;charset=UTF-8'},
                type: 'GET',
                dataType: "json",
                beforeSend: function () {
                    scrollRec = true
                },
                complete: function () {
                    scrollRec = false;
                    clickBtn()
                },
                success: function (data) {
                    if(data.content.length != 0){
                        itemBox.push(data.content)
                        let str = "";
                        itemBox.forEach(con => str += JSON.stringify(con))
                        str = str.replaceAll("][", ",")
                        itemList.itemList = JSON.parse(str)
                        itemList.size = data.size
                        if (data.last == true) page = 'last'
                        else page++;
                    }else itemList.itemList = false
                    itemList.wishSize = data.wishSize
                    itemList.watchSize = data.watchSize
                }
            })
        }
    }
    let scrollBox = document.querySelector(".css-9dnzub")
    scrollBox.addEventListener('scroll',(e)=>{
        if(scrollBox.scrollLeft >= scrollBox.scrollWidth-(scrollBox.querySelector('.e1689zdh0').getBoundingClientRect().width+5)) {
            // 스크롤 가능하면 실행
            if(!scrollRec){
                itemPlus();
            }
        }
    })
})