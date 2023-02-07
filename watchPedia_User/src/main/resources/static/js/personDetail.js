const container = document.querySelector(".css-1e9niz8");
const sidebar = document.querySelector(".css-1kk08rn");                                     // 사이트바 태그 
const side = document.querySelectorAll(".css-1kk08rn button");                              // 사이드바 버튼 태그

function scrollIntoView(selector){                                                                  //selector : `#${e.target.classList.item(1)}`
    const scrollTo = container.querySelector(selector);

    // 현재 위치에서의 클릭한 id 위치의 값을 구한 후 스크롤된 위치를 더하고 header 높이를 뺌
    let scrollY = scrollTo.getBoundingClientRect().top                                              // getBoundingClientRect 뷰포인트 위치정보 제공(세로기준)
        + window.scrollY -10
        - document.querySelector(".css-6k8tqb").getBoundingClientRect().height              // <header> : css-6k8tqb
    window.scrollTo({
        top: scrollY,
        behavior: "smooth"
    })
}

sidebar.addEventListener('click', (e)=>{                                           // 사이드바 클릭시 이벤트 발생
    scrollIntoView(`#${e.target.classList.item(1)}`);                                   // classList.item => 인덱스를 => 클래스 값으로 변환
    e.target.id = 'on'                                                                               // 버튼에 id=on속성이 정해짐
    for(let idx of side){
        if(e.target != idx){
            idx.removeAttribute('id')
        }
    }
})

window.addEventListener('scroll', () => {                                                   // 스크롤시 이벤트 발생
    for(let idx of side){
        let className = idx.classList.item(1);                                                    // 인덱스를 => 클래스 값으로 변환
        let position = container.querySelector(`#${className}`)
        let positionTop = position.getBoundingClientRect().top
            + window.scrollY -10
            - document.querySelector(".css-6k8tqb").getBoundingClientRect().height
        let positionHeight = position.getBoundingClientRect().height

        if(window.scrollY >= positionTop && window.scrollY <= positionTop+positionHeight){
            idx.id='on'                                                                                   // 버튼에 id=on속성이 정해짐
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


let count = 0;                              // 더보기를 누른 횟수 초기화값 0

function more(){

    let param = {
        "perIdx":$('#personIdx').val(),      //val =  <input type="hidden" id="personIdx"> 의 value값
        "count":count                        // 더보기를 누른 횟수
    };

    $.ajax({
        type:"post",
        url: "/movieMore",
        data: param,
        success: function(data) {
            console.log(data);
            let movdata = data.movieList;
            let isEnd = data.isEnd;
            let str = "";
            for(let i=0; i<movdata.length; i++){
                str = str+`<a class="w_exposed_cell css-11g9kr1" data-rowindex="1" href="/ko-KR/contents/mWJXZ7Q" id="movieList">
                                                        <div type="movies" class="css-1726275">
                                                            <div class="css-1vjd65c" id="movdata">${movdata[i].makingDate}</div>
                                                            <div type="movies" class="css-1fqhpd6">
                                                                <div class=" css-eyiymt-StyledLazyLoadingImage ezcopuc0">
                                                                    <img src="${movdata[i].thumbnail}" class="css-qhzw1o-StyledImg ezcopuc1">
                                                                </div>
                                                            </div>
                                                            <div class="css-1huturz" id="movieTitle">${movdata[i].title}</div>
                                                            <div class="css-uideuz">${movdata[i].movRole}</div>
                                                            <div class="css-1fk9ffn">
                                                                <div class="css-bql08h">평균
                                                                    <svg width="12" height="10"
                                                                         viewBox="0 0 12 10"
                                                                         xmlns="http://www.w3.org/2000/svg"
                                                                         fill="#787878" class="css-ebm5wj">
                                                                        <path fill-rule="evenodd" clip-rule="evenodd"
                                                                              d="M6 8.02L3.14233 9.91131C2.91094 10.0644 2.61352 9.84836 2.68767 9.58097L3.60334 6.27872L0.921531 4.14536C0.704379 3.97262 0.817982 3.62299 1.0952 3.61087L4.51878 3.46128L5.719 0.251483C5.81619 -0.00842059 6.18381 -0.00842094 6.281 0.251483L7.48122 3.46128L10.9048 3.61087C11.182 3.62299 11.2956 3.97262 11.0785 4.14536L8.39666 6.27872L9.31233 9.58097C9.38648 9.84836 9.08906 10.0644 8.85767 9.91131L6 8.02Z">
                                                                        </path>
                                                                    </svg>
                                                                    <span id="moviePoint">${movdata[i].starAvg}</span>
                                                                </div>
                                                            </div>
                                                            <div class="css-13lviui">`;
                if(movdata[i].isWatcha){
                    str = str + `<div class="isWatcha"><div class="css-vvy31y" /></div>`;
                }
                if(movdata[i].isNetflix){
                    str = str + `<div class="isNetflix"><div class="css-1uf1oz6" /></div>`;
                }


                str = str+ `</div>
                                                        </div>
                                                    </a>`;
            }
            $('#Movie-Actors > a').last().after(str)
            count++;
            if(isEnd){
                $(`#morebtn`).hide()                // 더이상 보낼께 없을때 더보기 없어지는 코드
            }
        },
        error: function(e) {
            console.log("error: " + JSON.stringify(e));
        }
    })
}

//---------------------------------------------------------tv-----------------------------------------------------------

let count2 = 0;                              // 더보기를 누른 횟수 초기화값 0

function tvmore(){

    let param = {
        "perIdx":$('#personIdx').val(),      //val =  <input type="hidden" id="personIdx"> 의 value값(html + 타임리프에 걸린값) => 이사람이 누군지 알려고
        "count":count2                        // 더보기를 누른 횟수
    };

    $.ajax({
        type:"post",
        url: "/tvMore",
        data: param,
        success: function(data) {
            console.log(data);
            let tvdata = data.tvList;
            let isEnd = data.isEnd;
            let str = "";
            for(let i=0; i<tvdata.length; i++){
                str = str+`<a class="w_exposed_cell css-11g9kr1" data-rowindex="6" href="/ko-KR/contents/tl9g0BW" id="tvList">
                                                        <div type="tv_seasons" class="css-1726275">
                                                        <div class="css-1vjd65c" id="tvdata">${tvdata[i].tvMakingDate}</div>
                                                        <div type="tv_seasons" class="css-1fqhpd6">
                                                            <div class=" css-eyiymt-StyledLazyLoadingImage ezcopuc0">
                                                                <img src="${tvdata[i].tvThumbnail}"class="css-qhzw1o-StyledImg ezcopuc1" id="tvpos"></div>
                                                        </div>
                                                        <div class="css-1huturz" id="tvTitle">${tvdata[i].tvTitle}</div>
                                                        <div class="css-uideuz" id="tvRole">${tvdata[i].tvRole}</div>
                                                        <div class="css-1fk9ffn">
                                                            <div class="css-bql08h">평균
                                                                    <svg width="12" height="10"
                                                                     viewBox="0 0 12 10"
                                                                     xmlns="http://www.w3.org/2000/svg"
                                                                     fill="#787878" class="css-ebm5wj">
                                                                    <path fill-rule="evenodd" clip-rule="evenodd"
                                                                          d="M6 8.02L3.14233 9.91131C2.91094 10.0644 2.61352 9.84836 2.68767 9.58097L3.60334 6.27872L0.921531 4.14536C0.704379 3.97262 0.817982 3.62299 1.0952 3.61087L4.51878 3.46128L5.719 0.251483C5.81619 -0.00842059 6.18381 -0.00842094 6.281 0.251483L7.48122 3.46128L10.9048 3.61087C11.182 3.62299 11.2956 3.97262 11.0785 4.14536L8.39666 6.27872L9.31233 9.58097C9.38648 9.84836 9.08906 10.0644 8.85767 9.91131L6 8.02Z">
                                                                    </path>
                                                                </svg>
                                                                <span id="tvPoint">${tvdata[i].starAvg}</span>
                                                            </div>
                                                        </div>
                                                        <div class="css-13lviui">`;
                if(tvdata[i].isWatcha){
                    str = str + `<div class="isWatcha"><div class="css-vvy31y" /></div>`;
                }
                if(tvdata[i].isNetflix){
                    str = str + `<div class="isNetflix"><div class="css-1uf1oz6" /></div>`;
                }


                str = str+ `</div>
                                                        </div>
                                                    </a>`;
            }
            $('#TvSeason-Actor > a').last().after(str)
            count++;
            if(isEnd){
                $(`#tvmorebtn`).hide()                // 더이상 보낼께 없을때 더보기 없어지는 코드
            }
        },
        error: function(e) {
            console.log("error: " + JSON.stringify(e));
        }
    })
}
//---------------------------------------------------------웹툰----------------------------------------------------------

let count3 = 0;                              // 더보기를 누른 횟수 초기화값 0

function webtoonmore(){

    let param = {
        "perIdx":$('#personIdx').val(),      //val =  <input type="hidden" id="personIdx"> 의 value값(html + 타임리프에 걸린값) => 이사람이 누군지 알려고
        "count":count3                        // 더보기를 누른 횟수
    };

    $.ajax({
        type:"post",
        url: "/webtoonMore",
        data: param,
        success: function(data) {
            console.log(data);
            let webtoondata = data.webtoonList;
            let isEnd = data.isEnd;
            let str = "";
            for(let i=0; i<webtoondata.length; i++){
                str = str+`<a class="w_exposed_cell css-11g9kr1" data-rowindex="6" href="/ko-KR/contents/tl9g0BW" id="webtoonList">
                                                        <div type="webtoon_seasons" class="css-1726275">
                                                        <div class="css-1vjd65c" id="webtoondata"${webtoondata[i].webSerDetail}</div>
                                                        <div type="webtoon_seasons" class="css-1fqhpd6">
                                                            <div class=" css-eyiymt-StyledLazyLoadingImage ezcopuc0">
                                                                <img src="${webtoondata[i].webThumbnail}"class="css-qhzw1o-StyledImg ezcopuc1" id="webtoonpos"></div>
                                                        </div>
                                                        <div class="css-1huturz" id="webtoonTitle">${webtoondata[i].webTitle}</div>
                                                        <div class="css-uideuz" id="webtoonRole">${webtoondata[i].webtoonRole}</div>
                                                        <div class="css-1fk9ffn">
                                                            <div class="css-bql08h">평균
                                                                    <svg width="12" height="10"
                                                                     viewBox="0 0 12 10"
                                                                     xmlns="http://www.w3.org/2000/svg"
                                                                     fill="#787878" class="css-ebm5wj">
                                                                    <path fill-rule="evenodd" clip-rule="evenodd"
                                                                          d="M6 8.02L3.14233 9.91131C2.91094 10.0644 2.61352 9.84836 2.68767 9.58097L3.60334 6.27872L0.921531 4.14536C0.704379 3.97262 0.817982 3.62299 1.0952 3.61087L4.51878 3.46128L5.719 0.251483C5.81619 -0.00842059 6.18381 -0.00842094 6.281 0.251483L7.48122 3.46128L10.9048 3.61087C11.182 3.62299 11.2956 3.97262 11.0785 4.14536L8.39666 6.27872L9.31233 9.58097C9.38648 9.84836 9.08906 10.0644 8.85767 9.91131L6 8.02Z">
                                                                    </path>
                                                                </svg>
                                                                <span id="webtoonPoint">${webtoondata[i].starAvg}</span>
                                                            </div>
                                                        </div>
                                                        <div class="css-13lviui">`;
                if(webtoondata[i].isWatcha){
                    str = str + `<div class="isWatcha"><div class="css-vvy31y" /></div>`;
                }
                if(webtoondata[i].isNetflix){
                    str = str + `<div class="isNetflix"><div class="css-1uf1oz6" /></div>`;
                }


                str = str+ `</div>
                                                        </div>
                                                    </a>`;
            }
            $('#webtoonSeason-Actor > a').last().after(str)
            count++;
            if(isEnd){
                $(`#webtoonmorebtn`).hide()                // 더이상 보낼께 없을때 더보기 없어지는 코드
            }
        },
        error: function(e) {
            console.log("error: " + JSON.stringify(e));
        }
    })
}

//---------------------------------------------------------book----------------------------------------------------------

let count4 = 0;                              // 더보기를 누른 횟수 초기화값 0

function bookmore(){

    let param = {
        "perIdx":$('#personIdx').val(),      //val =  <input type="hidden" id="personIdx"> 의 value값(html + 타임리프에 걸린값) => 이사람이 누군지 알려고
        "count":count4                        // 더보기를 누른 횟수
    };

    $.ajax({
        type:"post",
        url: "/bookMore",
        data: param,
        success: function(data) {
            console.log(data);
            let bookdata = data.bookList;
            let isEnd = data.isEnd;
            let str = "";
            for(let i=0; i<bookdata.length; i++){
                str = str+`<a class="w_exposed_cell css-11g9kr1" data-rowindex="6" href="/ko-KR/contents/tl9g0BW" id="bookList">
                                                        <div type="book_seasons" class="css-1726275">
                                                        <div class="css-1vjd65c" id="webtoondata"${bookdata[i].bookAtDate}</div>
                                                        <div type="book_seasons" class="css-1fqhpd6">
                                                            <div class=" css-eyiymt-StyledLazyLoadingImage ezcopuc0">
                                                                <img src="${bookdata[i].bookThumbnail}"class="css-qhzw1o-StyledImg ezcopuc1" id="bookpos"></div>
                                                        </div>
                                                        <div class="css-1huturz" id="bookTitle">${bookdata[i].bookTitle}</div>
                                                        <div class="css-uideuz" id="bookRole">${bookdata[i].bookRole}</div>
                                                        <div class="css-1fk9ffn">
                                                            <div class="css-bql08h">평균
                                                                    <svg width="12" height="10"
                                                                     viewBox="0 0 12 10"
                                                                     xmlns="http://www.w3.org/2000/svg"
                                                                     fill="#787878" class="css-ebm5wj">
                                                                    <path fill-rule="evenodd" clip-rule="evenodd"
                                                                          d="M6 8.02L3.14233 9.91131C2.91094 10.0644 2.61352 9.84836 2.68767 9.58097L3.60334 6.27872L0.921531 4.14536C0.704379 3.97262 0.817982 3.62299 1.0952 3.61087L4.51878 3.46128L5.719 0.251483C5.81619 -0.00842059 6.18381 -0.00842094 6.281 0.251483L7.48122 3.46128L10.9048 3.61087C11.182 3.62299 11.2956 3.97262 11.0785 4.14536L8.39666 6.27872L9.31233 9.58097C9.38648 9.84836 9.08906 10.0644 8.85767 9.91131L6 8.02Z">
                                                                    </path>
                                                                </svg>
                                                                <span id="bookPoint">${bookdata[i].starAvg}</span>
                                                            </div>
                                                        </div>
                                                        <div class="css-13lviui">`;
                if(bookdata[i].isWatcha){
                    str = str + `<div class="isWatcha"><div class="css-vvy31y" /></div>`;
                }
                if(bookdata[i].isNetflix){
                    str = str + `<div class="isNetflix"><div class="css-1uf1oz6" /></div>`;
                }


                str = str+ `</div>
                                                        </div>
                                                    </a>`;
            }
            $('#bookSeason-Actor > a').last().after(str)
            count++;
            if(isEnd){
                $(`#bookmorebtn`).hide()                // 더이상 보낼께 없을때 더보기 없어지는 코드
            }
        },
        error: function(e) {
            console.log("error: " + JSON.stringify(e));
        }
    })
}