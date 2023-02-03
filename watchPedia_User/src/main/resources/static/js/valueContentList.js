$(function() {
    let selectType = document.querySelector(".css-96eosw");

    // 셀렉트 탭 변경
    document.querySelector(".css-lshjof-VisualUl").addEventListener("click",(e)=>{
        selectType = document.querySelector(".css-96eosw");

        selectType.classList.add("css-kx8pjj")
        selectType.classList.remove("css-96eosw")
        e.target.classList.add("css-96eosw");
        e.target.classList.remove("css-kx8pjj");
        valueContentList(selectType.innerHTML)
    })

    const {createApp} = Vue
    let itemList = createApp({
        data() {
            return {
                contentList: {}
            }
        }
    }).mount('#content-ul');

    function valueContentList(type) {
        $.get("/estimate", function(response){
            console.log("json 호출완료")
            itemList.contentList = response.data;
        })
    }
})