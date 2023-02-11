window.onload = function(){
const container = document.querySelector(".css-usdi1z");
document.querySelector("#analysis-link").href='/user/'+window.location.href.split('/user/')[1]+'/analysis'

// 메인 스크롤
    const scrollBox = container.querySelector(".css-9dnzub");
    let scrollMax = scrollBox.scrollWidth - (container.querySelector('.e1689zdh0').getBoundingClientRect().width + 2);
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

// 버튼 클릭 시 이동
    container.querySelector('.css-vp7uyl').addEventListener('click', function () {
        scrollBox.scrollBy(container.querySelector('.emmoxnt0').getBoundingClientRect().width + 7, 0)
    })
    container.querySelector('.css-1hestod').addEventListener('click', function () {
        scrollBox.scrollBy(-container.querySelector('.emmoxnt0').getBoundingClientRect().width + 7, 0)
    })
}