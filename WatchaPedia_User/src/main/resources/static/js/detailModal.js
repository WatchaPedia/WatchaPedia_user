const button = document.querySelector('.css-1ukikc-StylelessButton')
const button2 = document.querySelector('.css-r2q33l-StylelessButton')
let cnt = document.querySelectorAll('.css-ynpx67');
const commentBtn = document.querySelector('.e1svyhwg25');
const commentBtn2 = document.querySelector('.e1svyhwg24');
const commentBtn3 = document.querySelector('.eue8w0j10');
const comment = document.querySelector('.css-rpyl6s');
const close = document.querySelector('.css-1lvet1d-StylelessButton');
const close2 = document.querySelector('.e1k34u8y0');
const main = document.querySelector('.css-14gy7wr');

// 코멘트 열기, 닫기
commentBtn.addEventListener('click',() => {
    if(comment.classList.contains('off')){
        main.style.display = 'block';
        main.classList.add('on');
        main.classList.remove('off');
        comment.style.display = 'block';
        comment.classList.add('on');
        comment.classList.remove('off');
    }else{
        main.style.display = 'none';
        main.classList.add('off');
        main.classList.remove('on');
        comment.style.display = 'none';
        comment.classList.add('off');
        comment.classList.remove('on');
    }
})
commentBtn2.addEventListener('click',() => {
    if(comment.classList.contains('off')){
        main.style.display = 'block';
        main.classList.add('on');
        main.classList.remove('off');
        comment.style.display = 'block';
        comment.classList.add('on');
        comment.classList.remove('off');
    }else{
        main.style.display = 'none';
        main.classList.add('off');
        main.classList.remove('on');
        comment.style.display = 'none';
        comment.classList.add('off');
        comment.classList.remove('on');
    }
})
commentBtn3.addEventListener('click',() => {
    if(comment.classList.contains('off')){
        main.style.display = 'block';
        main.classList.add('on');
        main.classList.remove('off');
        comment.style.display = 'block';
        comment.classList.add('on');
        comment.classList.remove('off');
    }else{
        main.style.display = 'none';
        main.classList.add('off');
        main.classList.remove('on');
        comment.style.display = 'none';
        comment.classList.add('off');
        comment.classList.remove('on');
    }
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
const morePop2 = document.querySelector('.css-7uunky');
const moreClose = document.querySelector('.css-9du7fu');

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
},true)