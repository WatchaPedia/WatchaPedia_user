const button = document.querySelector('.css-1ukikc-StylelessButton')
const button2 = document.querySelector('.css-r2q33l-StylelessButton')
let cnt = document.querySelectorAll('.css-ynpx67');
const commentBtn = document.querySelector('.e1svyhwg25');
const commentBtn2 = document.querySelector('.e1svyhwg24');
const commentBtn3 = document.querySelector('.eue8w0j10');
const comment = document.querySelector('#modal-container-bjG-x6GsyFLHJ1dtZkGxf');
const close = document.querySelector('.css-1lvet1d-StylelessButton');
const close2 = document.querySelector('.e1k34u8y0');
const main = document.querySelector('.css-14gy7wr');
const commentModal = commentBtn.querySelector("div.e1svyhwg28");
const commentModal2 = main.querySelector("div#comment-modal");
const hasComment = document.querySelector("div.hasComm");
// 코멘트 열기, 닫기
commentBtn.addEventListener('click',() => {
    if(document.querySelector("#login-idx")){
        if(hasComment.style.display == "block"){
            commentModal.style.display='block'
            commentModal.classList.add('on');
            commentModal.classList.remove('off');
        }else{
            main.style.display = 'block';
            main.classList.add('on');
            main.classList.remove('off');
            comment.style.display = 'block';
            comment.classList.add('on');
            comment.classList.remove('off');
        }
    }else{
        loginModalOn()
    }

})
commentBtn2.addEventListener('click',() => {
    if(document.querySelector("#login-idx")) {
        if (hasComment.style.display == "block") {
            main.style.display = 'block';
            main.classList.add('on');
            main.classList.remove('off');
            commentModal2.style.display = 'block'
            commentModal2.classList.add('on');
            commentModal2.classList.remove('off');
        } else {
            main.style.display = 'block';
            main.classList.add('on');
            main.classList.remove('off');
            comment.style.display = 'block';
            comment.classList.add('on');
            comment.classList.remove('off');
        }
    }else {loginModalOn()}
})
commentBtn3.addEventListener('click',() => {
    if(document.querySelector("#login-idx")) {
        if (comment.classList.contains('off')) {
            main.style.display = 'block';
            main.classList.add('on');
            main.classList.remove('off');
            comment.style.display = 'block';
            comment.classList.add('on');
            comment.classList.remove('off');
        } else {
            main.style.display = 'none';
            main.classList.add('off');
            main.classList.remove('on');
            comment.style.display = 'none';
            comment.classList.add('off');
            comment.classList.remove('on');
        }
    }else{loginModalOn()}
})
commentModal2.querySelector("div.css-9du7fu").addEventListener('click',()=>{
    main.style.display = 'none';
    main.classList.add('off');
    main.classList.remove('on');
    commentModal2.style.display='none'
    commentModal2.classList.add('of');
    commentModal2.classList.remove('on');
})
close.addEventListener('click', () =>{
    comment.style.display = 'none';
    comment.classList.add('off');
    comment.classList.remove('on');
    main.style.display = 'none';
    main.classList.add('off');
    main.classList.remove('on');
})
close2.addEventListener('click', () =>{
    comment.style.display = 'none';
    comment.classList.add('off');
    comment.classList.remove('on');
    main.style.display = 'none';
    main.classList.add('off');
    main.classList.remove('on');
})

const spoiler = document.querySelector(".css-5d0dfn");
const spoilerIcon = spoiler.querySelector(".css-hyoixq svg");
spoiler.addEventListener('click', ()=> {
    if (spoilerIcon.classList.contains("css-7zhfhb")){
        spoilerIcon.classList.add("css-1ngtlfw");
        spoilerIcon.classList.remove("css-7zhfhb");
    }else if(spoilerIcon.classList.contains("css-1ngtlfw")){
        spoilerIcon.classList.add("css-7zhfhb");
        spoilerIcon.classList.remove("css-1ngtlfw");
    }
})


// 코멘트 창에 글자 입력 시 이벤트 발생
document.addEventListener("keydown", ()=>{
    let text = document.querySelector('.css-137vxyg').value
    if(text != ''){
        button.classList.add('css-3ocf2p-StylelessButton');
        button.classList.remove('css-1ukikc-StylelessButton')

        button2.classList.add('css-1id6jxk-StylelessButton');
        button2.classList.remove('css-r2q33l-StylelessButton')
    }else{
        button.classList.add('css-1ukikc-StylelessButton')
        button.classList.remove('css-3ocf2p-StylelessButton');

        button2.classList.add('css-r2q33l-StylelessButton')
        button2.classList.remove('css-1id6jxk-StylelessButton');
    }
    cnt.item(0).innerHTML = text.length + "/10000";
    cnt.item(1).innerHTML = text.length + "/10000";
})

// 더보기 모달

const more = document.querySelector('.e1svyhwg27');
const more2 = document.querySelector('.e1svyhwg26');
const morePop = more.querySelector('.e1svyhwg28');
const morePop2 = document.querySelector('#more-modal');
const moreClose = morePop2.querySelector('.css-9du7fu');

// 더보기 버튼 액션
more.addEventListener('click', () => {
    if(morePop.classList.contains('off')){
        morePop.style.display = 'block';
        morePop.classList.add('on');
        morePop.classList.remove('off');
    }else{
        morePop.style.display = 'none';
        morePop.classList.remove('on');
        morePop.classList.add('off');
    }
})
more2.addEventListener('click', () => {
    if(morePop2.classList.contains('off')){
        main.style.display = 'block';
        main.classList.add('on');
        main.classList.remove('off');
        morePop2.style.display = 'block';
        morePop2.classList.add('on');
        morePop2.classList.remove('off');
    }else{
        main.style.display = 'none';
        main.classList.remove('on');
        main.classList.add('off');
        morePop2.style.display = 'none';
        morePop2.classList.remove('on');
        morePop2.classList.add('off');
    }
})

//취소버튼으로 닫기
moreClose.addEventListener('click', () =>{
    morePop2.style.display = 'none';
    morePop2.classList.remove('on');
    morePop2.classList.add('off');
    main.style.display = 'none';
    main.classList.remove('on');
    main.classList.add('off');
})

// 공유 모달
const shareBtn = document.querySelectorAll('.css-1xc2mdf-StylelessButton').item(1);
const shareModal = document.querySelector('#modal-container-GT5oHBRJexESZTqV6ClZl');
const shareClose = document.querySelector('.css-1blo7j2');

shareBtn.addEventListener('click', () => {
    if(shareModal.classList.contains('off')){
        main.style.display = 'block';
        main.classList.add('on');
        main.classList.remove('off');
        shareModal.style.display = 'flex';
        shareModal.classList.add('on');
        shareModal.classList.remove('off');
    }
})
shareClose.addEventListener('click', ()=>{
    if(shareModal.classList.contains('on')){
        main.style.display = 'none';
        main.classList.add('off');
        main.classList.remove('on');
        shareModal.style.display = 'none';
        shareModal.classList.add('off');
        shareModal.classList.remove('on');
    }
})

// 사진 클릭 시 모달
try{
    const photos = document.querySelectorAll(".css-1cw0vk0");
    const photoModal = document.querySelector("#modal-container-BMQHwP4WTewd41fP-ocFV");
    const photoR = document.querySelector(".css-171obip").querySelector(".css-tx4wru-StylelessButton");
    const photoL = document.querySelector(".css-171obip").querySelector(".css-ojnnlc-StylelessButton");

    document.addEventListener('click', ()=>{
        if(document.querySelector("li.on")){
            if(document.querySelector("li.on").classList.contains("last")){
                photoR.style.display = "none";
            }else{
                photoR.style.display = "block";
            }
            if(document.querySelector("li.on").classList.contains("first")){
                photoL.style.display = "none";
            }else{
                photoL.style.display = "block";
            }
        }
    })
    for(let photo of photos){
        photo.addEventListener('click', () => {
            if(photoModal.classList.contains('off')){
                let photoSrc = photo.querySelector(".e1q5rx9q1").getAttribute("src");
                document.querySelector(".css-1mshedn").src = photoSrc;
                photo.classList.add('on');
                main.style.display = 'block';
                main.classList.add('on');
                main.classList.remove('off');
                photoModal.style.display = 'block';
                photoModal.classList.add('on');
                photoModal.classList.remove('off');
            }else{
                photo.classList.remove('on');
                main.style.display = 'none';
                main.classList.remove('on');
                main.classList.add('off');
                photoModal.style.display = 'none';
                photoModal.classList.remove('on');
                photoModal.classList.add('off');
            }
        })

    }
    photoL.addEventListener('click', (e) => {
        let onPhoto = document.querySelector("li.on");
        let photoSrc = onPhoto.previousElementSibling.querySelector(".e1q5rx9q1").getAttribute("src");
        document.querySelector(".css-1mshedn").src = photoSrc;
        onPhoto.previousElementSibling.classList.add("on");
        onPhoto.classList.remove("on");
    });
    photoR.addEventListener('click', (e) => {
        let onPhoto = document.querySelector("li.on");
        let photoSrc = onPhoto.nextElementSibling.querySelector(".e1q5rx9q1").getAttribute("src");
        document.querySelector(".css-1mshedn").src = photoSrc;
        onPhoto.nextElementSibling.classList.add("on");
        onPhoto.classList.remove("on");

    });
    document.addEventListener('click' , (e) =>{
        //이미지 크게보기
        if(photoModal.classList.contains("on")){
            if(!document.querySelector('.css-1mshedn').contains(e.target) &&
                !document.querySelector('.css-ojnnlc-StylelessButton').contains(e.target) &&
                !document.querySelector('.css-tx4wru-StylelessButton').contains(e.target)){
                document.querySelector("li.css-1cw0vk0.on").classList.remove('on');
                photoModal.style.display = 'none';
                photoModal.classList.remove('on');
                photoModal.classList.add('off');
                main.style.display = 'none';
                main.classList.remove('on');
                main.classList.add('off');
            }
        };
    }, true);
}catch(Exception){
    console.log("갤러리가 없습니다")
}

document.addEventListener('click', (e) => {
    // 코멘트 outside 클릭 시 닫기
    if(comment.classList.contains("on")){
        if(!document.querySelector('.css-1p257d1-modalAddStyle').contains(e.target)){
            comment.style.display = 'none';
            comment.classList.add('off');
            comment.classList.remove('on');
            main.style.display = 'none';
            main.classList.add('off');
            main.classList.remove('on');
        }
    };

    if(commentModal.classList.contains("on")){
        commentModal.style.display='none'
        commentModal.classList.add('off');
        commentModal.classList.remove('on');
    }
    if(commentModal2.classList.contains("on")){
        commentModal2.style.display = 'none';
        commentModal2.classList.add('off');
        commentModal2.classList.remove('on');
        main.style.display = 'none';
        main.classList.add('off');
        main.classList.remove('on');
    }

    // 공유창
    if(shareModal.classList.contains("on")){
        if(!shareModal.querySelector('.css-7uunky').contains(e.target)){
            console.log(e.target)
            main.style.display = 'none';
            main.classList.add('off');
            main.classList.remove('on');
            shareModal.style.display = 'none';
            shareModal.classList.add('off');
            shareModal.classList.remove('on');
        }
    };

    // 더보기 창
    if(morePop.classList.contains("on")){
        if(!document.querySelector('.e1svyhwg28').contains(e.target)){
            morePop.style.display = 'none';
            morePop.classList.remove('on');
            morePop.classList.add('off');
        }
    };
    if(morePop2.classList.contains("on")){
        if(!document.querySelector('.css-7uunky').contains(e.target)){
            morePop2.style.display = 'none';
            morePop2.classList.remove('on');
            morePop2.classList.add('off');
            main.style.display = 'none';
            main.classList.remove('on');
            main.classList.add('off');
        }
    };
},true);
