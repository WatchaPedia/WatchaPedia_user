// 메인 스크롤
window.addEventListener('resize',clickBtn)
// 버튼 클릭 시 이동
function clickBtn(){
    let container = document.querySelector(".css-usdi1z");
    let scrollBox = document.querySelector(".css-9dnzub");
    let scrollMax = scrollBox.scrollWidth - (scrollBox.querySelector('.e1689zdh0').getBoundingClientRect().width+2);

    // 스크롤 위치에 따라 버튼 유무
    scrollBox.addEventListener('scroll', () => {
        if (scrollBox.scrollLeft >= scrollMax) {
            container.querySelector('.css-vp7uyl').style.display = "none";
        } else {
            container.querySelector('.css-vp7uyl').style.display = "flex";
        }
        if (scrollBox.scrollLeft == 0) {
            container.querySelector('.css-1hestod').style.display = "none";
        } else {
            container.querySelector('.css-1hestod').style.display = "block";
        }
    })
    container.querySelector('.css-vp7uyl').addEventListener('click', function () {
        console.log("실행")
        scrollBox.scrollBy(container.querySelector('.e1689zdh0').getBoundingClientRect().width-12,0)
    })
    container.querySelector('.css-1hestod').addEventListener('click', function () {
        scrollBox.scrollBy(-container.querySelector('.e1689zdh0').getBoundingClientRect().width-12,0)
    })
}

document.querySelector("#more-btn").href = "/user/"+window.location.href.split("/user/")[1]+"/ratings"

$(document).ready(function () {
    const userIdx = window.location.href.split("/user/")[1].split("/")[0]
    const contentType = window.location.href.split(`/user/${userIdx}/`)[1].split("/ratings")[0]

    let itemBox = new Array()

    let itemList = createApp({
        data() {
            return {
                itemList: {},
                contentType: contentType,
                userIdx: userIdx,
                size: 0
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
                    itemBox.push(data.content)
                    let str = "";
                    itemBox.forEach(con => str += JSON.stringify(con))
                    str = str.replaceAll("][", ",")
                    itemList.itemList = JSON.parse(str)
                    itemList.size = data.size
                    if (data.last == true) page = 'last'
                    else page++;
                }
            })
        }
    }
    let scrollBox = document.querySelector(".css-9dnzub")
    scrollBox.addEventListener('scroll',(e)=>{
        console.log("실행?")
        if(scrollBox.scrollLeft >= scrollBox.scrollWidth-(scrollBox.querySelector('.e1689zdh0').getBoundingClientRect().width+5)) {
            // 스크롤 가능하면 실행
            if(!scrollRec){
                itemPlus();
            }
        }
    })
})