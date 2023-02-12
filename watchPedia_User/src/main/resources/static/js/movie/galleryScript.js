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

const main = document.querySelector("#img-container");
const photos = document.querySelectorAll("li.css-1cw0vk0");
const photoR = document.querySelector(".css-tx4wru-StylelessButton");
const photoL = document.querySelector(".css-ojnnlc-StylelessButton");

for(let photo of photos){
    photo.addEventListener('click', () => {
        if(main.classList.contains('off')){
            let photoSrc = photo.querySelector(".img").style
                .backgroundImage.split('url("')[1].split('")')[0];
            document.querySelector("#img-view").src = photoSrc;
            photo.classList.add('on');
            main.style.display = 'block';
            main.classList.add('on');
            main.classList.remove('off');
        }else{
            photo.classList.remove('on');
            main.style.display = 'none';
            main.classList.remove('on');
            main.classList.add('off');
        }
    })
}
document.addEventListener('click',(e) => {
    if (document.querySelector("li.on").classList.contains("last")) {
        photoR.style.display = "none";
    } else {
        photoR.style.display = "block";
    }
    if (document.querySelector("li.on").classList.contains("first")) {
        photoL.style.display = "none";
    } else {
        photoL.style.display = "block";
    }

});
photoL.addEventListener('click', (e) => {
    let onPhoto = document.querySelector("li.on");
    let photoSrc = onPhoto.previousElementSibling.querySelector("div.img").style
        .backgroundImage.split('url("')[1].split('")')[0];
    document.querySelector("#img-view").src = photoSrc;
    onPhoto.previousElementSibling.classList.add("on");
    onPhoto.classList.remove("on");
});
photoR.addEventListener('click', (e) => {
    let onPhoto = document.querySelector("li.on");
    let photoSrc = onPhoto.nextElementSibling.querySelector("div.img").style
        .backgroundImage.split('url("')[1].split('")')[0];
    document.querySelector("#img-view").src = photoSrc;
    onPhoto.nextElementSibling.classList.add("on");
    onPhoto.classList.remove("on");

});
document.addEventListener('click' , (e) =>{
    //이미지 크게보기
    if(main.classList.contains("on")){
        if(!document.querySelector('#img-view').contains(e.target) &&
            !document.querySelector('.css-ojnnlc-StylelessButton').contains(e.target) &&
            !document.querySelector('.css-tx4wru-StylelessButton').contains(e.target)){
            document.querySelector("li.css-1cw0vk0.on").classList.remove('on');
            main.style.display = 'none';
            main.classList.remove('on');
            main.classList.add('off');
        }
    };
}, true);