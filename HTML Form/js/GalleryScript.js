const header = document.querySelector('.css-1n41o32');
const div1 = document.querySelector('.css-1d4ojes');
const div2 = document.querySelector('.css-1bvesam');
const div3 = document.querySelector('.css-10bjf2k');
const headerHeight = header.getBoundingClientRect().height;
document.addEventListener('scroll', () => { // 스크롤시 이벤트 발생
    if(window.scrollY > (headerHeight*2)){ // 스크롤을 navbar 높이의 두배만큼 내렸을때 navbar--bold 클래스 추가
        div1.classList.remove('css-1d4ojes');
        div1.classList.add('css-7d0ya9');

        div2.classList.remove('css-1bvesam');
        div2.classList.add('css-mp8yzl');

        div3.classList.remove('css-10bjf2k');
        div3.classList.add('css-15eb5z0');
    } else {
        div1.classList.remove('css-7d0ya9');
        div1.classList.add('css-1d4ojes');

        div2.classList.remove('css-mp8yzl');
        div2.classList.add('css-1bvesam');

        div3.classList.remove('css-15eb5z0');
        div3.classList.add('css-10bjf2k');
    }
})