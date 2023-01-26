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

// 스크롤 액션
const container = document.querySelectorAll(".css-usdi1z");
window.onload = scrollContent();
window.addEventListener('resize',()=>{
    scrollContent()
})

// 메인 스크롤
function scrollContent() {
for (let idx of container) {
    let scrollBox = idx.querySelector('.css-9dnzub');
    let scrollMax = scrollBox.scrollWidth - (idx.querySelector('.e1689zdh0').getBoundingClientRect().width + 10);
    // 스크롤 위치에 따라 버튼 유무
    idx.querySelector('.css-9dnzub').addEventListener('scroll', () => {
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
    if(0>= scrollMax){
        idx.querySelector('.css-vp7uyl').style.display = "none";
        idx.querySelector('.css-1hestod').style.display = "none";
    }
}

// 버튼 클릭 시 이동
for (let idx of container) {
    idx.querySelector('.css-vp7uyl').addEventListener('click', function () {
        idx.querySelector('.css-9dnzub').scrollBy(idx.querySelector('.e1689zdh0').getBoundingClientRect().width - 17, 0)
    })
    idx.querySelector('.css-1hestod').addEventListener('click', function () {
        idx.querySelector('.css-9dnzub').scrollBy(-idx.querySelector('.e1689zdh0').getBoundingClientRect().width - 17, 0)
    })
}}
