const main = document.querySelector('.css-14gy7wr');

const reportBnt = document.querySelector('.css-1b4hoch-SVG');
const reportBnt2 = document.querySelector('.e1bglx4g0');
const reportModal = document.querySelector('.css-4in6y9');
const reportModal2 = document.querySelector('#modal-container-H61eIpFx69hqMeWOpdZqm');
const reportClose = document.querySelector('.css-9du7fu');

// 메인댓글 더보기 클릭시 모달창
reportBnt.addEventListener('click', () => {
    if(reportModal.classList.contains('css-4in6y9')){
        reportModal.classList.add('css-ughty8')
        reportModal.classList.remove('css-4in6y9')
    }else {
        reportModal.classList.add('css-4in6y9')
        reportModal.classList.remove('css-ughty8')
    }
});
// 메인댓글 더보기 클릭시 모달창(719px 이하)
reportBnt2.addEventListener('click', () => {
    if(reportModal2.classList.contains('off')){
        main.style.display = 'block';
        main.classList.add('on');
        main.classList.remove('off');
        reportModal2.style.display = 'flex';
        reportModal2.classList.add('on');
        reportModal2.classList.remove('off');
    }else {
        main.style.display = 'none';
        main.classList.add('off');
        main.classList.remove('on');
        reportModal2.style.display = 'none';
        reportModal2.classList.add('off');
        reportModal2.classList.remove('on');
    }
});
// 취소 버튼 클릭 시 닫기
reportClose.addEventListener('click', () => {
    if(reportModal2.classList.contains('on')){
        main.style.display = 'none';
        main.classList.add('off');
        main.classList.remove('on');
        reportModal2.style.display = 'none';
        reportModal2.classList.add('off');
        reportModal2.classList.remove('on');
    }
})

const button = document.querySelector('.css-1ukikc-StylelessButton')
const button2 = document.querySelector('.css-r2q33l-StylelessButton')
let cnt = document.querySelector('.css-ynpx67');
const commentBtn = document.querySelectorAll('.e19d4hrp0').item(1);
const comment = document.querySelector('#modal-container-vMpn-C5LFj66HnQjaWi2-');
const close = document.querySelector('.css-1lvet1d-StylelessButton');
const close2 = document.querySelector('.css-1d7tft4-StylelessButton-HeaderCloseButtonSelf');

// 코멘트 열기, 닫기
commentBtn.addEventListener('click',() => {
    if(comment.classList.contains('off')){
        main.style.display = 'block';
        main.classList.add('on');
        main.classList.remove('off');
        comment.style.display = 'flex';
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
    let text = document.querySelector('.css-1k5ei58').value
    if(text !== ''){
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
    cnt.innerHTML = text.length + "/10000";
})

// recomment 신고버튼
const recomment = document.querySelectorAll('.css-1m1whp6');
try{
    for(let idx of recomment){
        idx.querySelector('.css-1b4hoch-SVG').addEventListener('click', function () {
            if(idx.querySelector('.css-aa3xw')){
                if(document.querySelector('.css-1pfl1eu')){
                    document.querySelector('.css-1pfl1eu').classList.add('css-aa3xw');
                    document.querySelector('.css-1pfl1eu').classList.remove('css-1pfl1eu');
                }
                idx.querySelector('.css-aa3xw').classList.add('css-1pfl1eu');
                idx.querySelector('.css-aa3xw').classList.remove('css-aa3xw');
            }else {
                idx.querySelector('.css-1pfl1eu').classList.add('css-aa3xw');
                idx.querySelector('.css-1pfl1eu').classList.remove('css-1pfl1eu');
            }

        })
    }
}catch (Exception) {console.log('에러발생!')}

// 공유 버튼 클릭 시
const shareBtn = document.querySelectorAll('.e19d4hrp0').item(2);
const shareModal = document.querySelector('.css-pfmsf9');

//공유 모달창 (719px 이하)
const shareModal2 = document.querySelector('#modal-container-GufKFTLHr9vMoaPtJBz7-');
let windowWidth = window.innerWidth;

window.addEventListener('resize',(e)=>{
    windowWidth = window.innerWidth
})

if(windowWidth<=719){
    shareBtn.addEventListener('click', () => {
        if(shareModal2.classList.contains('off')){
            main.style.display = 'block';
            main.classList.add('on');
            main.classList.remove('off');
            shareModal2.style.display = 'flex';
            shareModal2.classList.add('on');
            shareModal2.classList.remove('off');
        }else {
            main.style.display = 'none';
            main.classList.add('off');
            main.classList.remove('on');
            shareModal2.style.display = 'none';
            shareModal2.classList.add('off');
            shareModal2.classList.remove('on');
        }
    })
}else{
    shareBtn.addEventListener('click', () => {
        if(!shareModal.classList.contains('css-jwq87b')){
            shareModal.classList.add('css-jwq87b');
            shareModal.classList.remove('css-pfmsf9');
        }else {
            shareModal.classList.add('css-pfmsf9');
            shareModal.classList.remove('css-jwq87b');
        }
    })
}
// 공유 모달창 close
const shareClose = document.querySelector('.css-1blo7j2');

shareClose.addEventListener('click', () => {
    main.style.display = 'none';
    main.classList.add('off');
    main.classList.remove('on');
    shareModal2.style.display = 'none';
    shareModal2.classList.add('off');
    shareModal2.classList.remove('on');
})


// 아웃사이드 클릭 시 close
document.addEventListener('click',(e)=>{
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
    if(reportModal.classList.contains('css-ughty8')){
        if(!reportModal.contains(e.target)){
            reportModal.classList.add('css-4in6y9')
            reportModal.classList.remove('css-ughty8')
        }
    }
    if(reportModal2.classList.contains('on')){
        if(!reportModal2.querySelector('.css-7uunky').contains(e.target)){
            main.style.display = 'none';
            main.classList.add('off');
            main.classList.remove('on');
            reportModal2.style.display = 'none';
            reportModal2.classList.add('off');
            reportModal2.classList.remove('on');
        }
    }
    if(document.querySelector('.css-1pfl1eu')){
        if(!document.querySelector('.css-1pfl1eu').contains(e.target)){
            document.querySelector('.css-1pfl1eu').classList.add('css-aa3xw');
            document.querySelector('.css-1pfl1eu').classList.remove('css-1pfl1eu');
        }
    }
    if(shareModal.classList.contains('css-jwq87b')){
        if(!shareModal.contains(e.target)) {
            shareModal.classList.add('css-pfmsf9');
            shareModal.classList.remove('css-jwq87b');
        }
    }

    if(shareModal2.classList.contains('on')){
        if(!shareModal2.querySelector('.css-7uunky').contains(e.target)){
            main.style.display = 'none';
            main.classList.add('off');
            main.classList.remove('on');
            shareModal2.style.display = 'none';
            shareModal2.classList.add('off');
            shareModal2.classList.remove('on');
        }
    }
},true)