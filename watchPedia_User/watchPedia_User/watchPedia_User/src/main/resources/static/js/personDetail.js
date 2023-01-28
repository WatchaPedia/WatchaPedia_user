const container = document.querySelector(".css-1e9niz8");
const sidebar = document.querySelector(".css-1kk08rn");
const side = document.querySelectorAll(".css-1kk08rn button");

function scrollIntoView(selector){
    const scrollTo = container.querySelector(selector);

    // 현재 위치에서의 클릭한 id 위치의 값을 구한 후 스크롤된 위치를 더하고 header 높이를 뺌
    let scrollY = scrollTo.getBoundingClientRect().top + window.scrollY -10
        - document.querySelector(".css-6k8tqb").getBoundingClientRect().height
    window.scrollTo({
        top: scrollY,
        behavior: "smooth"
    })
}
sidebar.addEventListener('click', (e)=>{
    scrollIntoView(`#${e.target.classList.item(1)}`);
    e.target.id = 'on'
    for(let idx of side){
        if(e.target != idx){
            idx.removeAttribute('id')
        }
    }
})

window.addEventListener('scroll', () => {
    for(let idx of side){
        let className = idx.classList.item(1);
        let position = container.querySelector(`#${className}`)
        let positionTop = position.getBoundingClientRect().top + window.scrollY -10
            - document.querySelector(".css-6k8tqb").getBoundingClientRect().height
        let positionHeight = position.getBoundingClientRect().height

        if(window.scrollY >= positionTop && window.scrollY <= positionTop+positionHeight){
            idx.id='on'
            for(let j of side){
                if(idx != j){
                    j.removeAttribute('id')
                }
            }
        }
    }
})

// const observerOptions = {
//     root : null, // null은 viewport, 외에 엘리먼트로 지정가능
//     rootMargin : '0px', // 컨테이너 기준 마진
//     threshold : 0.3 // 1로 넣으면 완벽하게 뷰포트에 보여야 변화됨 / 엔트리가 어느정도 화면에 노출돼야 감지할 것인지 설정(0 ~ 1)
// }
// const observerCallback = (entries, observer) => {
//     entries.forEach((entry) => {
//         if(!entry.isIntersecting && entry.intersectionRatio > 0) {
//             console.log('y');
//             const index = sectionIds.indexOf(`#${entry.target.id}`); // 현재 스크롤 이벤트에 따른 현재 제일 큰 엘리먼트의 id값을 가져옴
//             // console.log(index)
//             if(entry.boundingClientRect.y < 0) { // 현재 스크롤이 0보다 작을 때
//                 selectedNavIndex = index + 1;
//             } else {
//                 selectedNavIndex =  index -1;
//             }
//         }
//     })
// }
//
// const observer = new IntersectionObserver(observerCallback, observerOptions);
// sections.forEach((section) => observer.observe(section));