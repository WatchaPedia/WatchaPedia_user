$(document).ready(function () {
    // 스크롤 액션
    window.addEventListener("click",(e)=>{
        if(document.querySelectorAll("ul.css-1e0vaz3-VisualUl li").item(1).contains(e.target)) scrollContent();
    })
    window.addEventListener('resize', () => {
        scrollContent()
    })

// 메인 스크롤
    function scrollContent() {
        let container = document.querySelectorAll(".css-usdi1z");
        for (let idx of container) {
            let scrollBox = idx.querySelector('.css-9dnzub');
            let scrollMax = scrollBox.getBoundingClientRect.scrollWidth - (idx.querySelector('.e1689zdh0').getBoundingClientRect().width + 10);
            // 스크롤 위치에 따라 버튼 유무
            scrollBox.addEventListener('scroll', () => {
                if (scrollBox.scrollLeft >= scrollMax) {
                    idx.querySelector('.css-vp7uyl').style.display = "none";
                } else {
                    idx.querySelector('.css-vp7uyl').style.display = "flex";
                }
                if (scrollBox.scrollLeft == 0) {
                    idx.querySelector('.css-1hestod').style.display = "none";
                } else {
                    idx.querySelector('.css-1hestod').style.display = "block";
                }
            })
            if (0 >= scrollMax) {
                idx.querySelector('.css-vp7uyl').style.display = "none";
                idx.querySelector('.css-1hestod').style.display = "none";
            }
            $(idx).hover(scrollBtn)
            $(idx).find(".css-pf83cl").hover(scrollBtn)
            $(idx).find(".css-38kpup").hover(scrollBtn)

            function scrollBtn() {
                if(idx.querySelector(".css-vp7uyl")){
                    idx.querySelector(".css-pf83cl").style.opacity = '1'
                    idx.querySelector(".css-38kpup").style.opacity = '1'
                }
            };
        }

// 버튼 클릭 시 이동
        for (let idx of container) {
            if(idx.querySelector(".css-1hestod")){
                idx.querySelector('.css-vp7uyl').addEventListener('click', function () {
                    idx.querySelector('.css-9dnzub').scrollBy(idx.querySelector('.e1689zdh0').getBoundingClientRect().width - 17, 0)
                })
                idx.querySelector('.css-1hestod').addEventListener('click', function () {
                    idx.querySelector('.css-9dnzub').scrollBy(-idx.querySelector('.e1689zdh0').getBoundingClientRect().width - 17, 0)
                })
            }
        }
    }
})