const main = document.querySelector('.css-14gy7wr');
const loginIdx = document.querySelector("a#login-idx");

// 로그인 안했을 경우 모달창
const needLoginModal = document.querySelector("#modal-container-nGZJ6mVG6nJ8Q5C7ZE4nu");
function loginModalOn(){
    main.style.display = 'block';
    main.classList.add('on');
    main.classList.remove('off');
    needLoginModal.style.display = 'block';
    needLoginModal.classList.add('on');
    needLoginModal.classList.remove('off');
}
// 로그인모달 바깥 클릭 시
document.addEventListener("click",(e)=>{
    if(needLoginModal.style.display == 'block'){
        if(!needLoginModal.querySelector("div.css-ikkedy").contains(e.target)){
            if(alertModal.classList.contains("off")){
                main.style.display = 'none';
                main.classList.add('off');
                main.classList.remove('on');
                needLoginModal.style.display = 'none';
                needLoginModal.classList.add('off');
                needLoginModal.classList.remove('on');
            }
        }
    }
},true)
// 로그인모달 value 초기화
needLoginModal.querySelectorAll("span[data-test='clearButton']").item(0).addEventListener('click',()=>{
    needLoginModal.querySelector("input[name='userEmail']").value = null
})
needLoginModal.querySelectorAll("span[data-test='clearButton']").item(1).addEventListener('click',()=>{
    needLoginModal.querySelector("input[name='userPw']").value = null
})
// 로그인모달 확인 클릭 시
const alertModal = document.querySelector("#modal-container-h-XEqOEytZK6ag0LO6V2w")
document.addEventListener('keydown',(e)=>{
    if(e.key == 'Enter'){
        loginSend()
    }
})
needLoginModal.querySelector("button.css-qr0uqd-StylelessButton").addEventListener('click',loginSend)
function loginSend() {
    $.ajax({
        url: "/user/signup/ajax",
        headers: {'Content-Type': 'application/json;charset=UTF-8'},
        data: JSON.stringify({
            userEmail: document.querySelector("input[name='userEmail']").value,
            userPw: document.querySelector("input[name='userPw']").value
        }),
        type: 'POST',
        dataType: 'json',
        success: function (result) {
            if (result) {
                location.reload();
            } else {
                alertModal.style.display = 'block';
                alertModal.classList.add('on');
                alertModal.classList.remove('off');
            }
        }, error: function () {

        }
    })
}
alertModal.querySelector("button.css-sfhtz9-StylelessButton").addEventListener('click',()=>{
    alertModal.style.display = 'none';
    alertModal.classList.add('off');
    alertModal.classList.remove('on');
})

const reportBnt = document.querySelector('div.css-1d7xpnn-CommentContainer .css-1b4hoch-SVG');
const reportBnt2 = document.querySelector('.e1bglx4g0');
const reportModal = document.querySelector('.css-4in6y9');
const reportModal2 = document.querySelector('#modal-container-H61eIpFx69hqMeWOpdZqm');
const reportClose = document.querySelector('.css-9du7fu');

const myCommentMore = document.querySelector("#modal-container-TEW7x8Ed2FRpVPPru5mqs");

// 메인댓글 더보기 클릭시 모달창
reportBnt.addEventListener('click', () => {
    if (reportModal.classList.contains('css-4in6y9')) {
        reportModal.classList.add('css-ughty8')
        reportModal.classList.remove('css-4in6y9')
    } else {
        reportModal.classList.add('css-4in6y9')
        reportModal.classList.remove('css-ughty8')
    }
});
// 메인댓글 더보기 클릭시 모달창(719px 이하)
reportBnt2.addEventListener('click', () => {
    if(document.querySelector("#not-my-comment")){
        if (reportModal2.classList.contains('off')) {
            main.style.display = 'block';
            main.classList.add('on');
            main.classList.remove('off');
            reportModal2.style.display = 'flex';
            reportModal2.classList.add('on');
            reportModal2.classList.remove('off');
        }
    }else if(document.querySelector("#my-comment")){
        main.style.display = 'block';
        main.classList.add('on');
        main.classList.remove('off');
        myCommentMore.style.display = 'flex';
        myCommentMore.classList.add('on');
        myCommentMore.classList.remove('off');
    }
});
// 취소 버튼 클릭 시 닫기
reportClose.addEventListener('click', () => {
    if (reportModal2.classList.contains('on')) {
        main.style.display = 'none';
        main.classList.add('off');
        main.classList.remove('on');
        reportModal2.style.display = 'none';
        reportModal2.classList.add('off');
        reportModal2.classList.remove('on');
    }
})

// 메인코멘트 신고
const spoilerBtn = document.querySelectorAll("div.css-hkgyal div.css-19hkid5").item(0);
const inapBtn = document.querySelectorAll("div.css-hkgyal div.css-19hkid5").item(1);
const userIdx = document.querySelector("a#user-info").href.split("/user/")[1]

const spoilerModal = document.querySelector("div#modal-container-1arSh67x8qkwczaPieVAx")
const miniSpoBtn = reportModal2.querySelectorAll("div.css-7mldxr div.css-bgi4sk").item(0)
const miniInapBtn = reportModal2.querySelectorAll("div.css-7mldxr div.css-bgi4sk").item(1)


try{
// 스포일러 신고 버튼 클릭 시
    if(!document.querySelector("#login-idx")){
        spoilerBtn.addEventListener("click",loginModalOn)
        miniSpoBtn.addEventListener("click",loginModalOn)
    }else {
        spoilerBtn.addEventListener('click', spoReport)
        miniSpoBtn.addEventListener('click', spoReport)
    }
    function spoReport(){
        if (spoilerModal.classList.contains('off')) {
            if(document.querySelector("div.css-ughty8")){
                reportModal.classList.add('css-4in6y9')
                reportModal.classList.remove('css-ughty8')
            }
            if(reportModal2.classList.contains('on')){
                main.style.display = 'none';
                main.classList.add('off');
                main.classList.remove('on');
                reportModal2.style.display = 'none';
                reportModal2.classList.add('off');
                reportModal2.classList.remove('on');
            }

            main.style.display = 'block';
            main.classList.add('on');
            main.classList.remove('off');
            spoilerModal.style.display = 'flex';
            spoilerModal.classList.add('on');
            spoilerModal.classList.remove('off');
            if(spoilerBtn.innerHTML == '스포일러 신고 취소'){
                spoilerModal.querySelector("div.css-148qwic").innerHTML = "스포일러 신고를 취소하시겠어요?";
            }else{
                spoilerModal.querySelector("div.css-148qwic").innerHTML = "스포일러로 신고하시겠어요?";
            }
        }
    }

// 스포일러 신고 취소 버튼 클릭 시
    spoilerModal.querySelector("button.css-1gdw77k-StylelessButton").addEventListener("click", () => {
        main.style.display = 'none';
        main.classList.add('off');
        main.classList.remove('on');
        spoilerModal.style.display = 'none';
        spoilerModal.classList.add('off');
        spoilerModal.classList.remove('on');
    })
// 스포일러 신고 확인 버튼 클릭 시
    spoilerModal.querySelector("button.css-sfhtz9-StylelessButton").addEventListener('click', () => {
        $.ajax({
            url: '/report/save',
            headers: {'Content-Type': 'application/json;charset=UTF-8'},
            data: JSON.stringify({
                userIdx: userIdx,
                commType: 'comm',
                commIdx: commentIdx,
                text: document.querySelector("div.css-1g78l7j span").innerText,
                spoiler: true,
                inap: false,
                reporter: loginIdx.title
            }),
            type: 'POST',
            dataType: 'json',
            success: function (data) {
                if (data == true) {
                    spoilerBtn.innerText = '스포일러 신고 취소';
                    main.style.display = 'none';
                    main.classList.add('off');
                    main.classList.remove('on');
                    spoilerModal.style.display = 'none';
                    spoilerModal.classList.add('off');
                    spoilerModal.classList.remove('on');
                } else {
                    spoilerBtn.innerText = '스포일러 신고';
                    main.style.display = 'none';
                    main.classList.add('off');
                    main.classList.remove('on');
                    spoilerModal.style.display = 'none';
                    spoilerModal.classList.add('off');
                    spoilerModal.classList.remove('on');
                }
            }, error: function () {
                alert("오류 발생!")
            }
        });
    })


    const inappModal = document.querySelector("div#modal-container-6TxBWpCAEPqm20UjsJdDQ");

// 부적절표현 신고 버튼 클릭 시
    if(!document.querySelector("#login-idx")){
        miniInapBtn.addEventListener("click",loginModalOn)
        inapBtn.addEventListener("click",loginModalOn)
    }else {
        miniInapBtn.addEventListener('click', inapReport)
        inapBtn.addEventListener('click', inapReport)
    }
    function inapReport(){
        if(spoilerModal.classList.contains('off')){
            if(document.querySelector('css-ughty8')){
                reportModal.classList.add('css-4in6y9')
                reportModal.classList.remove('css-ughty8')
            }
            if(reportModal2.classList.contains('on')){
                main.style.display = 'none';
                main.classList.add('off');
                main.classList.remove('on');
                reportModal2.style.display = 'none';
                reportModal2.classList.add('off');
                reportModal2.classList.remove('on');
            }
            main.style.display = 'block';
            main.classList.add('on');
            main.classList.remove('off');
            inappModal.style.display = 'flex';
            inappModal.classList.add('on');
            inappModal.classList.remove('off');
            if(inapBtn.innerHTML == '부적절한 표현 신고 취소'){
                inappModal.querySelector("div.css-148qwic").innerHTML = '부적절 표현 신고를 취소하시겠어요?';
            }else{
                inappModal.querySelector("div.css-148qwic").innerHTML = '부적절 표현으로 신고하시겠어요?';
            }
        }
    }

// 부적절표현 신고 취소 버튼 클릭 시
    inappModal.querySelector("button.css-1gdw77k-StylelessButton").addEventListener("click",()=>{
        main.style.display = 'none';
        main.classList.add('off');
        main.classList.remove('on');
        inappModal.style.display = 'none';
        inappModal.classList.add('off');
        inappModal.classList.remove('on');
    })

// 부적절표현 신고 확인 버튼 클릭 시
    inappModal.querySelector("button.css-sfhtz9-StylelessButton").addEventListener('click', ()=>{
        $.ajax({
            url: '/report/save',
            headers: {'Content-Type':'application/json;charset=UTF-8'},
            data: JSON.stringify({
                userIdx: userIdx,
                commType: 'comm',
                commIdx: commentIdx,
                text: document.querySelector("div.css-1g78l7j span").innerText,
                spoiler: false,
                inap: true,
                reporter: loginIdx.title
            }),
            type:'POST',
            dataType:'json',
            success:function(data){
                if(data == true){
                    inapBtn.innerText = '부적절한 표현 신고 취소';
                    main.style.display = 'none';
                    main.classList.add('off');
                    main.classList.remove('on');
                    inappModal.style.display = 'none';
                    inappModal.classList.add('off');
                    inappModal.classList.remove('on');
                }else{
                    inapBtn.innerText = '부적절표현 신고';
                    main.style.display = 'none';
                    main.classList.add('off');
                    main.classList.remove('on');
                    inappModal.style.display = 'none';
                    inappModal.classList.add('off');
                    inappModal.classList.remove('on');
                }
            },error:function(){
                alert("오류 발생!")
            }
        });
    })}catch(Exception){
    console.log('메인코멘트 - 본인이 작성한 글')
}

const contentIdx = document.querySelector("#content-info").href.split("/")[document.querySelector("#content-info").href.split("/").length-1]
try{
    const myComment = document.querySelector("#my-comment");
    const myCommentEdit = myComment.querySelectorAll("button").item(0)
    const myCommentDelete = myComment.querySelectorAll("button").item(1)
    const myCommentModal = document.querySelector("#modal-container-xaZdPxT85D_xylVtxcown")
    myCommentModal.querySelector("textarea.css-137vxyg").value = document.querySelector("div.css-1g78l7j span").innerText;
    const myCommentDel = document.querySelector("#modal-container-aWTcAcCYlL1n-VRF14lje")
    const myCommentDelModal = document.querySelector("#modal-container-UiAVwwzWAy8FG95dSniCO")

    // 내코멘트 수정 모달창 열기
    myCommentEdit.addEventListener('click', ()=>{
        main.style.display = 'block';
        main.classList.add('on');
        main.classList.remove('off');
        myCommentModal.style.display = 'flex';
        myCommentModal.classList.add('on');
        myCommentModal.classList.remove('off');
    })

    // 내 코멘트 more 모달 닫기
    myCommentMore.querySelector("div.css-9du7fu").addEventListener('click',()=>{
        main.style.display = 'none';
        main.classList.add('off');
        main.classList.remove('on');
        myCommentMore.style.display = 'none';
        myCommentMore.classList.add('off');
        myCommentMore.classList.remove('on');
    })
    // 내 코멘트 more 모달 수정 버튼 클릭시
    myCommentMore.querySelectorAll("div.css-bgi4sk").item(0).addEventListener('click',()=>{
        myCommentMore.style.display = 'none';
        myCommentMore.classList.add('off');
        myCommentMore.classList.remove('on');
        main.style.display = 'block';
        main.classList.add('on');
        main.classList.remove('off');
        myCommentModal.style.display = 'flex';
        myCommentModal.classList.add('on');
        myCommentModal.classList.remove('off');
    })
    // 내 코멘트 more 모달 삭제 버튼 클릭시
    myCommentMore.querySelectorAll("div.css-bgi4sk").item(1).addEventListener('click',()=>{
        myCommentMore.style.display = 'none';
        myCommentMore.classList.add('off');
        myCommentMore.classList.remove('on');
        main.style.display = 'block';
        main.classList.add('on');
        main.classList.remove('off');
        myCommentDel.style.display = 'flex';
        myCommentDel.classList.add('on');
        myCommentDel.classList.remove('off');
    })
    // 내 코멘트 삭제 취소 버튼 클릭 시
    myCommentDel.querySelector("button.css-1gdw77k-StylelessButton").addEventListener('click',()=>{
        main.style.display = 'none';
        main.classList.add('off');
        main.classList.remove('on');
        myCommentDel.style.display = 'none';
        myCommentDel.classList.add('off');
        myCommentDel.classList.remove('on');
    })

    // 마이코멘트 삭제 모달 on
    myCommentDelete.addEventListener('click',()=>{
        main.style.display = 'block';
        main.classList.add('on');
        main.classList.remove('off');
        myCommentDelModal.style.display = 'flex';
        myCommentDelModal.classList.add('on');
        myCommentDelModal.classList.remove('off');
    })
    // 마이코멘트 삭제 모달 off
    myCommentDelModal.querySelector("button.css-1gdw77k-StylelessButton").addEventListener('click',()=>{
        main.style.display = 'none';
        main.classList.add('off');
        main.classList.remove('on');
        myCommentDelModal.style.display = 'none';
        myCommentDelModal.classList.add('off');
        myCommentDelModal.classList.remove('on');
    })

    // 내 코멘트 삭제 확인 버튼 클릭 시
    myCommentDel.querySelector("button.css-sfhtz9-StylelessButton").addEventListener('click',myCommentDelteAjax)
    myCommentDelModal.querySelector("button.css-sfhtz9-StylelessButton").addEventListener('click',myCommentDelteAjax)
    function myCommentDelteAjax(){
        $.ajax({
            url: '/comment/delete',
            headers: { 'Content-Type': 'application/json;charset=UTF-8' },
            data: JSON.stringify({           // HTTP 요청과 함께 서버로 보낼 데이터
                contentType: document.querySelector("#content-info").href.split("/")[document.querySelector("#content-info").href.split("/").length-2],
                contentIdx: contentIdx,
                userIdx: loginIdx.title,
            }),
            type:'POST',
            dataType:'json',
            success:function(data){
                history.back();
            },error:function(){
                alert("오류 발생!")
            }
        });
    }
    const spoilerIcon = document.querySelector("div[data-type='spoiler'] svg");
    document.querySelector(".css-5d0dfn").addEventListener('click', ()=> {
        if (spoilerIcon.classList.contains("css-7zhfhb")){
            spoilerIcon.classList.add("css-1ngtlfw");
            spoilerIcon.classList.remove("css-7zhfhb");
        }else if(spoilerIcon.classList.contains("css-1ngtlfw")){
            spoilerIcon.classList.add("css-7zhfhb");
            spoilerIcon.classList.remove("css-1ngtlfw");
        }
    })

    myCommentModal.querySelector("div.css-6qnjre button").addEventListener('click',myCommentEditAjax)
    myCommentModal.querySelectorAll("div.css-19pxr9t").item(1).querySelectorAll("button").item(1).addEventListener('click',myCommentEditAjax)
    function myCommentEditAjax(){
        $.ajax({
            url: '/comment/save',
            headers: { 'Content-Type': 'application/json;charset=UTF-8' },
            data: JSON.stringify({           // HTTP 요청과 함께 서버로 보낼 데이터
                contentType: document.querySelector("#content-info").href.split("/")[document.querySelector("#content-info").href.split("/").length-2],
                contentIdx: contentIdx,
                userIdx: loginIdx.title,
                text: myCommentModal.querySelector("textarea.css-137vxyg").value,
                spoiler: spoilerIcon.classList.contains("css-7zhfhb") ? false:true
            }),
            type:'POST',
            dataType:'json',
            success:function(data){
                document.querySelector("div.css-1g78l7j span").innerText = myCommentModal.querySelector("textarea.css-137vxyg").value
                main.style.display = 'none';
                main.classList.add('off');
                main.classList.remove('on');
                myCommentModal.style.display = 'none';
                myCommentModal.classList.add('off');
                myCommentModal.classList.remove('on');
            },error:function(){
                alert("오류 발생!")
            }
        });
    }

    // 내코멘트 수정 모달창 닫기
    myCommentModal.querySelector("button.css-1lvet1d-StylelessButton").addEventListener("click",()=>{
        main.style.display = 'none';
        main.classList.add('off');
        main.classList.remove('on');
        myCommentModal.style.display = 'none';
        myCommentModal.classList.add('off');
        myCommentModal.classList.remove('on');
    })
    myCommentModal.querySelector("button.css-1d7tft4-StylelessButton-HeaderCloseButtonSelf").addEventListener("click",()=>{
        main.style.display = 'none';
        main.classList.add('off');
        main.classList.remove('on');
        myCommentModal.style.display = 'none';
        myCommentModal.classList.add('off');
        myCommentModal.classList.remove('on');
    })

    // 빈 공간 클릭시 모달 닫기
    document.addEventListener('click',(e)=>{
        if(myCommentModal.style.display == 'flex'){
            if(!myCommentModal.querySelector("div.css-1p257d1-modalAddStyle").contains(e.target)){
                main.style.display = 'none';
                main.classList.add('off');
                main.classList.remove('on');
                myCommentModal.style.display = 'none';
                myCommentModal.classList.add('off');
                myCommentModal.classList.remove('on');
            }
        }
        if(myCommentMore.style.display == 'flex'){
            if(!myCommentMore.querySelector("div.css-7uunky").contains(e.target)){
                main.style.display = 'none';
                main.classList.add('off');
                main.classList.remove('on');
                myCommentMore.style.display = 'none';
                myCommentMore.classList.add('off');
                myCommentMore.classList.remove('on');
            }
        }
        if(myCommentDel.style.display =='flex'){
            if(!myCommentDel.querySelector("div.css-f35o9y").contains(e.target)){
                main.style.display = 'none';
                main.classList.add('off');
                main.classList.remove('on');
                myCommentDel.style.display = 'none';
                myCommentDel.classList.add('off');
                myCommentDel.classList.remove('on');
            }
        }
        if(myCommentDelModal.style.display =='flex'){
            if(!myCommentDelModal.querySelector("div.css-f35o9y").contains(e.target)){
                main.style.display = 'none';
                main.classList.add('off');
                main.classList.remove('on');
                myCommentDelModal.style.display = 'none';
                myCommentDelModal.classList.add('off');
                myCommentDelModal.classList.remove('on');
            }
        }
    },true)

    // 내코멘트 수정 모달에서 키 입력시
    document.addEventListener("keydown", ()=>{
        let myCommentText = myCommentModal.querySelector("textarea.css-137vxyg").value
        if(myCommentText !== ''){
            myCommentModal.querySelector("div.css-6qnjre button").classList.add('css-3ocf2p-StylelessButton');
            myCommentModal.querySelector("div.css-6qnjre button").classList.remove('css-1ukikc-StylelessButton')

            myCommentModal.querySelectorAll("div.css-19pxr9t").item(1).querySelectorAll("button").item(1).classList.add('css-1id6jxk-StylelessButton');
            myCommentModal.querySelectorAll("div.css-19pxr9t").item(1).querySelectorAll("button").item(1).classList.remove('css-r2q33l-StylelessButton')
        }else{
            myCommentModal.querySelector("div.css-6qnjre button").classList.add('css-1ukikc-StylelessButton')
            myCommentModal.querySelector("div.css-6qnjre button").classList.remove('css-3ocf2p-StylelessButton');

            myCommentModal.querySelectorAll("div.css-19pxr9t").item(1).querySelectorAll("button").item(1).classList.add('css-r2q33l-StylelessButton')
            myCommentModal.querySelectorAll("div.css-19pxr9t").item(1).querySelectorAll("button").item(1).classList.remove('css-1id6jxk-StylelessButton');
        }
        myCommentModal.querySelector("p.css-ynpx67").innerHTML = myCommentText.length + "/10000";
    })
}catch(Exception){
    console.log("메인코멘트 - 본인이 작성한 글이 아닐 경우")
}


const button = document.querySelector('div.css-6qnjre button')
const button2 = document.querySelectorAll("header.css-166ww79-HeaderBarPrimitive-headerAddStyle div.css-19pxr9t").item(1).querySelectorAll('button').item(1)
let cnt = document.querySelector('.css-ynpx67');
const commentBtn = document.querySelectorAll('.e19d4hrp0').item(1);
const comment = document.querySelector('#modal-container-vMpn-C5LFj66HnQjaWi2-');
const close = document.querySelector('.css-1lvet1d-StylelessButton');
const close2 = document.querySelector('.css-1d7tft4-StylelessButton-HeaderCloseButtonSelf');

// 코멘트 열기, 닫기
commentBtn.addEventListener('click',() => {
    if(!document.querySelector("#login-idx")){
        loginModalOn()
    }else {
        if(comment.classList.contains('off')){
            main.style.display = 'block';
            main.classList.add('on');
            main.classList.remove('off');
            comment.style.display = 'flex';
            comment.classList.add('on');
            comment.classList.remove('off');
            comment.querySelector("div.css-6qnjre button").innerHTML = "저장"
            comment.querySelectorAll("header.css-166ww79-HeaderBarPrimitive-headerAddStyle div.css-19pxr9t").item(1).querySelectorAll("button").item(1).innerHTML = "저장"
        }
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
try{
    const checkList = document.querySelectorAll("path.check")
    for(let idx of checkList){
        idx.setAttribute('d','M12.1945 5.00536C11.9313 4.70284 11.552 4.52907 11.1539 4.52907H9.81942C9.98857 4.11339 10.0586 3.67058 10.0586 3.08112C10.0586 1.96053 9.25499 1.45825 8.45762 1.45825C8.20018 1.45825 8.00482 1.53273 7.87667 1.67995C7.75593 1.81851 7.70922 2.00325 7.73485 2.19204V2.86405C7.73485 2.92582 7.71036 2.98702 7.66651 3.0309L6.06378 4.65608C5.7175 5.00652 5.5415 5.37428 5.5415 5.74896V11.6666H10.3161C11.0132 11.6666 11.605 11.1383 11.6893 10.4565L12.5305 6.1121C12.5806 5.71201 12.4582 5.30846 12.1945 5.00536Z');
    }
}catch (Exception){console.log("리코멘트에 좋아요가 없습니다")}

// recomment 신고버튼
const recommentReportModal = document.querySelector("div#modal-container-fZgqMYLrh3NKQOcVpt4wk");

document.addEventListener("click",(e)=>{
    let recomment = document.querySelectorAll('section div.css-1m1whp6');
    for(let idx of recomment){
        if(idx.querySelector('.css-1b4hoch-SVG').contains(e.target)){
            if(idx.querySelector('.css-aa3xw')){
                idx.querySelector('.css-aa3xw').classList.add('css-1pfl1eu');
                idx.querySelector('.css-aa3xw').classList.remove('css-aa3xw');
                recommentIdx = idx.id;
                recommentText = idx.querySelector("div.css-yb0jaq");
                recommentUserIdx = idx.querySelector("a.css-255jr8").href.split("/user/")[1]
                recommentBtn = idx.querySelector("div.css-19hkid5");
            }else {
                idx.querySelector('.css-1pfl1eu').classList.add('css-aa3xw');
                idx.querySelector('.css-1pfl1eu').classList.remove('css-1pfl1eu');
            }
        }

        // 리코멘트 좋아요
        if(idx.querySelectorAll("div.css-199ku80 div.css-ov1ktg>div").item(0).contains(e.target)){
            if(document.querySelector("#login-idx")){
                let btn = idx.querySelectorAll("div.css-199ku80>div.css-ov1ktg>div").item(0)
                let likeSum = btn.querySelector("h4.like-sum").innerHTML
                $.ajax({
                    url: '/comment/recomment/like/save',
                    headers: {'Content-Type':'application/json;charset=UTF-8'},
                    data: JSON.stringify({
                        recommIdx: idx.id,
                        userIdx: loginIdx.title
                    }),
                    type:'POST',
                    dataType:'json',
                    success:function(data){
                        if(data==true){
                            idx.querySelector("h4.like-sum").innerHTML=parseInt(likeSum) + 1
                            btn.querySelector("svg").dataset.src = 'data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMTQiIGhlaWdodD0iMTQiIHZpZXdCb3g9IjAgMCAxNCAxNCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPHBhdGggZmlsbC1ydWxlPSJldmVub2RkIiBjbGlwLXJ1bGU9ImV2ZW5vZGQiIGQ9Ik0xLjc1IDEyLjU0MTdINC42NjY2N1Y1LjU0MTc1SDEuNzVWMTIuNTQxN1oiIGZpbGw9IiNGRjJGNkUiLz4KPHBhdGggZmlsbC1ydWxlPSJldmVub2RkIiBjbGlwLXJ1bGU9ImV2ZW5vZGQiIGQ9Ik0xMi4xOTQ1IDUuMDA1MzZDMTEuOTMxMyA0LjcwMjg0IDExLjU1MiA0LjUyOTA3IDExLjE1MzkgNC41MjkwN0g5LjgxOTQyQzkuOTg4NTcgNC4xMTMzOSAxMC4wNTg2IDMuNjcwNTggMTAuMDU4NiAzLjA4MTEyQzEwLjA1ODYgMS45NjA1MyA5LjI1NDk5IDEuNDU4MjUgOC40NTc2MiAxLjQ1ODI1QzguMjAwMTggMS40NTgyNSA4LjAwNDgyIDEuNTMyNzMgNy44NzY2NyAxLjY3OTk1QzcuNzU1OTMgMS44MTg1MSA3LjcwOTIyIDIuMDAzMjUgNy43MzQ4NSAyLjE5MjA0VjIuODY0MDVDNy43MzQ4NSAyLjkyNTgyIDcuNzEwMzYgMi45ODcwMiA3LjY2NjUxIDMuMDMwOUw2LjA2Mzc4IDQuNjU2MDhDNS43MTc1IDUuMDA2NTIgNS41NDE1IDUuMzc0MjggNS41NDE1IDUuNzQ4OTZWMTEuNjY2NkgxMC4zMTYxQzExLjAxMzIgMTEuNjY2NiAxMS42MDUgMTEuMTM4MyAxMS42ODkzIDEwLjQ1NjVMMTIuNTMwNSA2LjExMjFDMTIuNTgwNiA1LjcxMjAxIDEyLjQ1ODIgNS4zMDg0NiAxMi4xOTQ1IDUuMDA1MzZaIiBmaWxsPSIjRkYyRjZFIi8+Cjwvc3ZnPgo=';
                            btn.querySelectorAll("svg path").item(0).setAttribute('fill','#FF2F6E');
                            btn.querySelectorAll("svg path").item(1).setAttribute('fill','#FF2F6E');
                            btn.querySelectorAll("svg path").item(1).setAttribute('d','M12.1945 5.00536C11.9313 4.70284 11.552 4.52907 11.1539 4.52907H9.81942C9.98857 4.11339 10.0586 3.67058 10.0586 3.08112C10.0586 1.96053 9.25499 1.45825 8.45762 1.45825C8.20018 1.45825 8.00482 1.53273 7.87667 1.67995C7.75593 1.81851 7.70922 2.00325 7.73485 2.19204V2.86405C7.73485 2.92582 7.71036 2.98702 7.66651 3.0309L6.06378 4.65608C5.7175 5.00652 5.5415 5.37428 5.5415 5.74896V11.6666H10.3161C11.0132 11.6666 11.605 11.1383 11.6893 10.4565L12.5305 6.1121C12.5806 5.71201 12.4582 5.30846 12.1945 5.00536Z');
                            btn.classList.add('css-jpkqok');
                            btn.classList.remove('css-1d8juai');
                        }else{
                            idx.querySelector("h4.like-sum").innerHTML=parseInt(likeSum) - 1
                            btn.querySelector("svg").dataset.src = 'data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMTQiIGhlaWdodD0iMTQiIHZpZXdCb3g9IjAgMCAxNCAxNCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPHBhdGggZmlsbC1ydWxlPSJldmVub2RkIiBjbGlwLXJ1bGU9ImV2ZW5vZGQiIGQ9Ik0zLjkzNzgzIDUuNTMzMkgyLjE4NzgzQzEuODY1ODMgNS41MzMyIDEuNjA0NDkgNS43OTQ1NCAxLjYwNDQ5IDYuMTE2NTRWMTEuOTQ5OUMxLjYwNDQ5IDEyLjI3MTkgMS44NjU4MyAxMi41MzMyIDIuMTg3ODMgMTIuNTMzMkgzLjkzNzgzQzQuMjYwNDEgMTIuNTMzMiA0LjUyMTE2IDEyLjI3MTkgNC41MjExNiAxMS45NDk5VjYuMTE2NTRDNC41MjExNiA1Ljc5NDU0IDQuMjYwNDEgNS41MzMyIDMuOTM3ODMgNS41MzMyWiIgZmlsbD0iIzg3ODk4QiIvPgo8cGF0aCBmaWxsLXJ1bGU9ImV2ZW5vZGQiIGNsaXAtcnVsZT0iZXZlbm9kZCIgZD0iTTguMTk3MzMgMy4wNDE3OUw4LjE5MTQ5IDIuNzk1NjJMOC4xNzgwOCAyLjIwMTc5TDguMTc2OTEgMi4xNjc5NUw4LjE3MzQxIDIuMTM0N0M4LjE2ODE2IDIuMDc4NyA4LjE3NTc0IDIuMDU0MiA4LjE3NDU4IDIuMDUzMDRDOC4xODQ0OSAyLjA0NzIgOC4yMjc2NiAyLjAzMzIgOC4zMTEwOCAyLjAzMzJDOC40ODA4MyAyLjAzMzIgOS4zMjYwOCAyLjA4MzM3IDkuMzI2MDggMy4wNzA5NUM5LjMyNjA4IDMuNDUzNjIgOS4yOTQ1OCAzLjc1MTEyIDkuMjI2OTEgNC4wMDg5NUw4Ljk4NjU4IDQuOTIzMDRDOC45NjI2NiA1LjAxNTc5IDkuMDMyMDggNS4xMDYyIDkuMTI3NzQgNS4xMDYySDEwLjA3MjdIMTEuMDEyNUMxMS4yNDA2IDUuMTA2MiAxMS40NTgyIDUuMjA1OTUgMTEuNjA5OCA1LjM3OTJDMTEuNzU4NiA1LjU1MTI5IDExLjgzMDkgNS43NzkzNyAxMS44MDkzIDYuMDA4MDRMMTAuOTcwNSAxMC4zMzkzTDEwLjk2NDcgMTAuMzY4NUwxMC45NjEyIDEwLjM5NzZDMTAuOTEzMyAxMC43Nzc0IDEwLjU2NzQgMTEuMDc0OSAxMC4xNzM3IDExLjA3NDlINS45Nzk0OVY1LjczOTdDNS45Nzk0OSA1LjUyMzg3IDYuMDk4NDkgNS4yOTQwNCA2LjMzNDE2IDUuMDU2NjJMNy45Mzk0OSAzLjQzMDI5TDguMTA0NTggMy4yNjI4N0M4LjE5NzMzIDMuMTY2NjIgOC4xOTczMyAzLjA0MTc5IDguMTk3MzMgMy4wNDE3OVpNMTIuMjY5NiA0LjgwNTc5QzExLjk1MTcgNC40NDAwNCAxMS40OTMyIDQuMjMxMiAxMS4wMTI1IDQuMjMxMkgxMC4wNzI3QzEwLjE2MiAzLjg5MjI5IDEwLjIwMTEgMy41MjA3IDEwLjIwMTEgMy4wNzA5NUMxMC4yMDExIDEuNzU2NyA5LjIyMTY2IDEuMTU4MiA4LjMxMTA4IDEuMTU4MkM3Ljk2ODA4IDEuMTU4MiA3LjcwMDMzIDEuMjY1NTQgNy41MTU5OSAxLjQ3NzI5QzcuMzk2OTkgMS42MTI2MiA3LjI2NTc0IDEuODUxMiA3LjMwMzA4IDIuMjIxNjJMNy4zMTY0OSAyLjgxNTQ1TDUuNzExMTYgNC40NDE3OUM1LjMwODY2IDQuODQ4OTUgNS4xMDQ0OSA1LjI4NTg3IDUuMTA0NDkgNS43Mzk3VjExLjM2NjVDNS4xMDQ0OSAxMS42ODg1IDUuMzY1ODMgMTEuOTQ5OSA1LjY4NzgzIDExLjk0OTlIMTAuMTczN0MxMS4wMTQyIDExLjk0OTkgMTEuNzI5NCAxMS4zMTIzIDExLjgyOTcgMTAuNTA1NUwxMi42NzUgNi4xNDA0NUMxMi43MzUxIDUuNjU2ODcgMTIuNTg2OSA1LjE3MDk1IDEyLjI2OTYgNC44MDU3OVoiIGZpbGw9IiM4Nzg5OEIiLz4KPC9zdmc+Cg=='
                            btn.querySelectorAll("svg path").item(0).setAttribute('fill','#87898B');
                            btn.querySelectorAll("svg path").item(1).setAttribute('fill','#87898B');
                            btn.querySelectorAll("svg path").item(1).setAttribute('d','M8.19733 3.04179L8.19149 2.79562L8.17808 2.20179L8.17691 2.16795L8.17341 2.1347C8.16816 2.0787 8.17574 2.0542 8.17458 2.05304C8.18449 2.0472 8.22766 2.0332 8.31108 2.0332C8.48083 2.0332 9.32608 2.08337 9.32608 3.07095C9.32608 3.45362 9.29458 3.75112 9.22691 4.00895L8.98658 4.92304C8.96266 5.01579 9.03208 5.1062 9.12774 5.1062H10.0727H11.0125C11.2406 5.1062 11.4582 5.20595 11.6098 5.3792C11.7586 5.55129 11.8309 5.77937 11.8093 6.00804L10.9705 10.3393L10.9647 10.3685L10.9612 10.3976C10.9133 10.7774 10.5674 11.0749 10.1737 11.0749H5.97949V5.7397C5.97949 5.52387 6.09849 5.29404 6.33416 5.05662L7.93949 3.43029L8.10458 3.26287C8.19733 3.16662 8.19733 3.04179 8.19733 3.04179ZM12.2696 4.80579C11.9517 4.44004 11.4932 4.2312 11.0125 4.2312H10.0727C10.162 3.89229 10.2011 3.5207 10.2011 3.07095C10.2011 1.7567 9.22166 1.1582 8.31108 1.1582C7.96808 1.1582 7.70033 1.26554 7.51599 1.47729C7.39699 1.61262 7.26574 1.8512 7.30308 2.22162L7.31649 2.81545L5.71116 4.44179C5.30866 4.84895 5.10449 5.28587 5.10449 5.7397V11.3665C5.10449 11.6885 5.36583 11.9499 5.68783 11.9499H10.1737C11.0142 11.9499 11.7294 11.3123 11.8297 10.5055L12.675 6.14045C12.7351 5.65687 12.5869 5.17095 12.2696 4.80579Z');
                            btn.classList.add('css-1d8juai');
                            btn.classList.remove('css-jpkqok');
                        }
                    },error:function(){
                        alert("오류 발생!")
                    }
                });
            }else{loginModalOn()}
        }
    }
})

const recommDelModal = document.querySelector("#modal-container-yjYuIgAkMjkq6O8ZPy5vn");
document.addEventListener('click',(e)=>{
    if(e.target == document.querySelectorAll('.css-1pfl1eu div.css-19hkid5').item(0)
        || e.target == document.querySelectorAll('.css-1pfl1eu div.css-19hkid5').item(1)
    ){
        if(!e.target.innerHTML.indexOf('댓글')){
            if(e.target.innerHTML == '댓글 수정'){
                document.querySelector('.css-1pfl1eu').classList.add('css-aa3xw');
                document.querySelector('.css-1pfl1eu').classList.remove('css-1pfl1eu');
                main.style.display = 'block';
                main.classList.add('on');
                main.classList.remove('off');
                comment.style.display = 'flex';
                comment.classList.add('on');
                comment.classList.remove('off');
                comment.querySelector("div.css-6qnjre button").innerHTML = "수정"
                comment.querySelectorAll("header.css-166ww79-HeaderBarPrimitive-headerAddStyle div.css-19pxr9t").item(1).querySelectorAll("button").item(1).innerHTML = "수정"
                document.querySelector('.css-1k5ei58').value = recommentText.innerText
            }else{
                document.querySelector('.css-1pfl1eu').classList.add('css-aa3xw');
                document.querySelector('.css-1pfl1eu').classList.remove('css-1pfl1eu');
                main.style.display = 'block';
                main.classList.add('on');
                main.classList.remove('off');
                recommDelModal.style.display = 'flex';
                recommDelModal.classList.add('on');
                recommDelModal.classList.remove('off');
            }
        }
        else{
            if(document.querySelector("#login-idx")){
                if(e.target.innerHTML == "부적절한 표현 신고 취소"){
                    recommentReportModal.querySelector("div.css-148qwic").innerText = "부적절 표현 신고를 취소하시겠어요?"
                }else{
                    recommentReportModal.querySelector("div.css-148qwic").innerText = "부적절한 표현으로 신고하시겠어요?"
                }
                document.querySelector('.css-1pfl1eu').classList.add('css-aa3xw');
                document.querySelector('.css-1pfl1eu').classList.remove('css-1pfl1eu');
                main.style.display = 'block';
                main.classList.add('on');
                main.classList.remove('off');
                recommentReportModal.style.display = 'flex';
                recommentReportModal.classList.add('on');
                recommentReportModal.classList.remove('off');
            }else{
                loginModalOn()
            }
        }
    }
})
// 리코멘트 삭제 취소버튼 클릭 시
recommDelModal.querySelector("button.css-1gdw77k-StylelessButton").addEventListener('click',()=>{
    main.style.display = 'none';
    main.classList.add('off');
    main.classList.remove('on');
    recommDelModal.style.display = 'none';
    recommDelModal.classList.add('off');
    recommDelModal.classList.remove('on');
})
// 리코멘트 삭제 확인 버튼 클릭 시
recommDelModal.querySelector("button.css-sfhtz9-StylelessButton").addEventListener('click',()=>{
    let commSum = document.querySelector("div.css-prw2jl span.css-0")
    $.ajax({
        url:`/comment/recomment/delete`,
        headers: { 'Content-Type': 'application/json;charset=UTF-8' },
        data: JSON.stringify({           // HTTP 요청과 함께 서버로 보낼 데이터
            idx: recommentIdx
        }),
        type:'POST',
        dataType:'json',
        success:function(idx){
            commSum.innerText = '댓글 ' + (parseInt(commSum.innerText.split("댓글 ")[1]) - 1);

            main.style.display = 'none';
            main.classList.add('off');
            main.classList.remove('on');
            recommDelModal.style.display = 'none';
            recommDelModal.classList.add('off');
            recommDelModal.classList.remove('on');

            $(`div#${idx}`).remove();
        },error:function(){
            alert("오류 발생!")
        }
    });
})

// 리코멘트 신고 취소버튼 클릭 시
recommentReportModal.querySelector(".css-1gdw77k-StylelessButton").addEventListener('click',()=>{
    main.style.display = 'none';
    main.classList.add('off');
    main.classList.remove('on');
    recommentReportModal.style.display = 'none';
    recommentReportModal.classList.add('off');
    recommentReportModal.classList.remove('on');
})

// 리코멘트 신고 확인 버튼 클릭 시
let recommentBtn;
let recommentIdx;
let recommentText;
let recommentUserIdx;
recommentReportModal.querySelector("button.css-sfhtz9-StylelessButton").addEventListener('click',()=>{
    $.ajax({
        url: '/report/save',
        headers: {'Content-Type':'application/json;charset=UTF-8'},
        data: JSON.stringify({
            userIdx: recommentUserIdx,
            commType: 're',
            commIdx: recommentIdx,
            text: recommentText.innerText,
            spoiler: false,
            inap: true,
            reporter: loginIdx.title
        }),
        type:'POST',
        dataType:'json',
        success:function(data){
            if(data == true){
                recommentBtn.innerText = '부적절한 표현 신고 취소';
                main.style.display = 'none';
                main.classList.add('off');
                main.classList.remove('on');
                recommentReportModal.style.display = 'none';
                recommentReportModal.classList.add('off');
                recommentReportModal.classList.remove('on');
            }else {
                recommentBtn.innerText = '부적절표현 신고';
                main.style.display = 'none';
                main.classList.add('off');
                main.classList.remove('on');
                recommentReportModal.style.display = 'none';
                recommentReportModal.classList.add('off');
                recommentReportModal.classList.remove('on');
            }
        },error:function(){
            alert("오류 발생!")
        }
    });
})

// 공유 버튼 클릭 시
const shareBtn = document.querySelectorAll('.e19d4hrp0').item(2);
const shareModal = document.querySelector('.css-pfmsf9');

//공유 모달창 (719px 이하)
const shareModal2 = document.querySelector('#modal-container-GufKFTLHr9vMoaPtJBz7-');

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

    // 부적절한 표현 신고
    if(document.querySelector('.css-ughty8')) {
        if (!reportModal.contains(e.target)) {
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
    // 리코멘트 바깥 클릭 시
    if(document.querySelector('.css-1pfl1eu')){
        if(!document.querySelector('.css-1pfl1eu').contains(e.target)){
            document.querySelector('.css-1pfl1eu').classList.add('css-aa3xw');
            document.querySelector('.css-1pfl1eu').classList.remove('css-1pfl1eu');
        }
    }
    // 공유모달
    if(document.querySelector('.css-jwq87b')){
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
    if(document.querySelector("div#modal-container-1arSh67x8qkwczaPieVAx").classList.contains('on')){
        if(!document.querySelector("div#modal-container-1arSh67x8qkwczaPieVAx").querySelector(".css-f35o9y").contains(e.target)){
            main.style.display = 'none';
            main.classList.add('off');
            main.classList.remove('on');
            document.querySelector("div#modal-container-1arSh67x8qkwczaPieVAx").style.display = 'none';
            document.querySelector("div#modal-container-1arSh67x8qkwczaPieVAx").classList.add('off');
            document.querySelector("div#modal-container-1arSh67x8qkwczaPieVAx").classList.remove('on');
        }
    }
},true);

// 크기에 따른 공유 모달창
let winWid = window.innerWidth;
window.addEventListener('resize',()=>{
    winWid = window.innerWidth;
})
shareBtn.addEventListener('click', () => {
    if(winWid<=719){
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
    }else{
        if(!shareModal.classList.contains('css-jwq87b')){
            shareModal.classList.add('css-jwq87b');
            shareModal.classList.remove('css-pfmsf9');
        }else {
            shareModal.classList.add('css-pfmsf9');
            shareModal.classList.remove('css-jwq87b');
        }
    }
})

// 코멘트 좋아요
const commentIdx = window.location.href.split('/comment/')[1];
const likeBtn = document.querySelector("button#deckLike");
likeBtn.addEventListener("click", ()=>{
    if(document.querySelector("#login-idx")) {
        let likeSum = document.querySelector("span.css-1n0dvqq").innerHTML.split('좋아요 ')[1]
        $.ajax({
            url: '/comment/like/save',
            headers: {'Content-Type': 'application/json;charset=UTF-8'},
            data: JSON.stringify({           // HTTP 요청과 함께 서버로 보낼 데이터
                userIdx: loginIdx.title,
                commentIdx: commentIdx
            }),
            type: 'POST',           // HTTP 요청 방식(GET, POST)
            dataType: "json",       // 호출 시 데이터 타입
            success: function (data) {
                if (data == true) {
                    likeBtn.classList.add("css-3w1nnz-StylelessButton-StyledActionButton");
                    likeBtn.querySelector("svg.css-vkoibk").classList.add("boing");
                    likeBtn.classList.remove("css-135c2b4-StylelessButton-StyledActionButton");
                    document.querySelector("span.css-1n0dvqq").innerHTML = '좋아요 ' + (parseInt(likeSum) + 1);
                } else {
                    likeBtn.classList.add("css-135c2b4-StylelessButton-StyledActionButton");
                    likeBtn.classList.remove("css-3w1nnz-StylelessButton-StyledActionButton");
                    likeBtn.querySelector("svg.css-vkoibk").classList.remove("boing");
                    document.querySelector("span.css-1n0dvqq").innerHTML = '좋아요 ' + (parseInt(likeSum) - 1);
                }
            }, error: function () {
                alert("에러발생!")
            }
        })
    }else{loginModalOn();}
})

button.addEventListener("click",recommentSave);
button2.addEventListener("click",recommentSave);

const containerNone = document.querySelector("section#recomment-list-none div.css-0");
const listNone = containerNone.querySelector("div.css-1m1whp6");
function recommentSave(){
    let text = document.querySelector('.css-1k5ei58').value
    let commSum = document.querySelector("div.css-prw2jl span.css-0")
    if(button.innerHTML == '수정' && button2.innerHTML == '수정'){
        $.ajax({
            url:`/comment/recomment/edit`,
            headers: { 'Content-Type': 'application/json;charset=UTF-8' },
            data: JSON.stringify({           // HTTP 요청과 함께 서버로 보낼 데이터
                idx: recommentIdx,
                text: text
            }),
            type: 'POST',           // HTTP 요청 방식(GET, POST)
            dataType: "json",       // 호출 시 데이터 타입
            success : function(data) {
                recommentText.innerText = data.text;

                comment.style.display = 'none';
                comment.classList.add('off');
                comment.classList.remove('on');
                main.style.display = 'none';
                main.classList.add('off');
                main.classList.remove('on');
            },error: function() {
                alert("에러발생!")
            }
        })
    }else{
        $.ajax({
            url:`/comment/recomment/save`,
            headers: { 'Content-Type': 'application/json;charset=UTF-8' },
            data: JSON.stringify({           // HTTP 요청과 함께 서버로 보낼 데이터
                userIdx: loginIdx.title,
                commIdx:commentIdx,
                text:text
            }),
            type: 'POST',           // HTTP 요청 방식(GET, POST)
            dataType: "json",       // 호출 시 데이터 타입
            complete:function(){
              document.querySelector(".css-1k5ei58").value = null;
              document.querySelector(".css-ynpx67").innerHTML = '0/10000';
              document.querySelector(".css-3ocf2p-StylelessButton").setAttribute('class','css-1ukikc-StylelessButton');
              document.querySelector(".css-r2q33l-StylelessButton").setAttribute('class','css-1id6jxk-StylelessButton')
            },
            success : function(data) {
                comment.style.display = 'none';
                comment.classList.add('off');
                comment.classList.remove('on');
                main.style.display = 'none';
                main.classList.add('off');
                main.classList.remove('on');

                commSum.innerText = '댓글 ' + (parseInt(commSum.innerText.split("댓글 ")[1]) + 1);

                try {
                    // 코멘트 있을 때
                    let appendComm = listNone.cloneNode(true)
                    appendComm.setAttribute('id', data.idx);
                    appendComm.querySelector('div.css-yb0jaq').innerHTML = data.text;
                    appendComm.querySelector('div.css-72k174').innerHTML = data.name;
                    appendComm.querySelector('div.css-maxfbg').innerHTML = data.regDate;
                    appendComm.querySelectorAll('a.css-255jr8').item(0).href = `/user/${data.userIdx.userIdx}`;
                    appendComm.querySelectorAll('a.css-255jr8').item(1).href = `/user/${data.userIdx.userIdx}`;
                    $(appendComm).find('div.recomment').remove();
                    // document.querySelector("section#recomment-list div.css-0").appendChild(appendComm);
                    document.querySelector("section#recomment-list div.css-0").insertBefore(appendComm,document.querySelector("section#recomment-list div.css-0 div.css-1m1whp6"));
                }catch{
                    // 코멘트 없을 때
                    let appendComm = listNone.cloneNode(true);
                    document.querySelector('section#recomment-list-none').style.display = 'block'
                    appendComm.style.display = 'block'
                    appendComm.setAttribute('id', data.idx);
                    appendComm.querySelector('div.css-yb0jaq').innerHTML = data.text;
                    appendComm.querySelector('div.css-72k174').innerHTML = data.name;
                    appendComm.querySelector('div.css-maxfbg').innerHTML = data.regDate;
                    appendComm.querySelectorAll('a.css-255jr8').item(0).href = `/user/${data.userIdx.userIdx}`;
                    appendComm.querySelectorAll('a.css-255jr8').item(1).href = `/user/${data.userIdx.userIdx}`;
                    $(appendComm).find('div.recomment').remove();
                    containerNone.insertBefore(appendComm, document.querySelector("section#recomment-list-none div.css-1m1whp6:first-of-type"));
                }
            },error: function() {
                alert("에러발생!")
            }
        })
    }
}


// 리코멘트 이전댓글 보기 클릭 시
if(document.querySelector("div.css-5hpf69")){
    const loadingIcon = document.querySelector("#loading-icon");
    let page = 1;
    document.querySelector("button.css-16halel").addEventListener('click',()=>{
        $.ajax({
            url: `/comment/${commentIdx}/new?page=${page}`,
            headers: {'Content-Type' :'application/json;char-set=UTF-8'},
            type: 'GET',
            dataType: 'json',
            beforeSend:function(){
                loadingIcon.style.display='flex'
            },
            complete:function(){
                loadingIcon.style.display='none'
            },
            success: function(data){
                for(let idx of data.comment.content){
                    let appendComm = listNone.cloneNode(true);
                    appendComm.setAttribute('id', idx.idx);
                    appendComm.querySelector('div.css-yb0jaq').innerHTML = idx.text;
                    appendComm.querySelector('div.css-72k174').innerHTML = idx.name;
                    appendComm.querySelector('div.css-maxfbg').innerHTML = idx.regDate;
                    appendComm.querySelectorAll('a.css-255jr8').item(0).href = `/user/${idx.userIdx}`;
                    appendComm.querySelectorAll('a.css-255jr8').item(1).href = `/user/${idx.userIdx}`;
                    let btn = appendComm.querySelectorAll("div.css-199ku80>div.css-ov1ktg>div").item(0);
                    if(idx.like.length > 0){
                        appendComm.querySelector("h4.like-sum").innerHTML = idx.like.length;
                        if(idx.hasLike){
                            btn.querySelector("svg").dataset.src = 'data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMTQiIGhlaWdodD0iMTQiIHZpZXdCb3g9IjAgMCAxNCAxNCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPHBhdGggZmlsbC1ydWxlPSJldmVub2RkIiBjbGlwLXJ1bGU9ImV2ZW5vZGQiIGQ9Ik0xLjc1IDEyLjU0MTdINC42NjY2N1Y1LjU0MTc1SDEuNzVWMTIuNTQxN1oiIGZpbGw9IiNGRjJGNkUiLz4KPHBhdGggZmlsbC1ydWxlPSJldmVub2RkIiBjbGlwLXJ1bGU9ImV2ZW5vZGQiIGQ9Ik0xMi4xOTQ1IDUuMDA1MzZDMTEuOTMxMyA0LjcwMjg0IDExLjU1MiA0LjUyOTA3IDExLjE1MzkgNC41MjkwN0g5LjgxOTQyQzkuOTg4NTcgNC4xMTMzOSAxMC4wNTg2IDMuNjcwNTggMTAuMDU4NiAzLjA4MTEyQzEwLjA1ODYgMS45NjA1MyA5LjI1NDk5IDEuNDU4MjUgOC40NTc2MiAxLjQ1ODI1QzguMjAwMTggMS40NTgyNSA4LjAwNDgyIDEuNTMyNzMgNy44NzY2NyAxLjY3OTk1QzcuNzU1OTMgMS44MTg1MSA3LjcwOTIyIDIuMDAzMjUgNy43MzQ4NSAyLjE5MjA0VjIuODY0MDVDNy43MzQ4NSAyLjkyNTgyIDcuNzEwMzYgMi45ODcwMiA3LjY2NjUxIDMuMDMwOUw2LjA2Mzc4IDQuNjU2MDhDNS43MTc1IDUuMDA2NTIgNS41NDE1IDUuMzc0MjggNS41NDE1IDUuNzQ4OTZWMTEuNjY2NkgxMC4zMTYxQzExLjAxMzIgMTEuNjY2NiAxMS42MDUgMTEuMTM4MyAxMS42ODkzIDEwLjQ1NjVMMTIuNTMwNSA2LjExMjFDMTIuNTgwNiA1LjcxMjAxIDEyLjQ1ODIgNS4zMDg0NiAxMi4xOTQ1IDUuMDA1MzZaIiBmaWxsPSIjRkYyRjZFIi8+Cjwvc3ZnPgo=';
                            btn.querySelectorAll("svg path").item(0).setAttribute('fill','#FF2F6E');
                            btn.querySelectorAll("svg path").item(1).setAttribute('fill','#FF2F6E');
                            btn.querySelectorAll("svg path").item(1).setAttribute('d','M12.1945 5.00536C11.9313 4.70284 11.552 4.52907 11.1539 4.52907H9.81942C9.98857 4.11339 10.0586 3.67058 10.0586 3.08112C10.0586 1.96053 9.25499 1.45825 8.45762 1.45825C8.20018 1.45825 8.00482 1.53273 7.87667 1.67995C7.75593 1.81851 7.70922 2.00325 7.73485 2.19204V2.86405C7.73485 2.92582 7.71036 2.98702 7.66651 3.0309L6.06378 4.65608C5.7175 5.00652 5.5415 5.37428 5.5415 5.74896V11.6666H10.3161C11.0132 11.6666 11.605 11.1383 11.6893 10.4565L12.5305 6.1121C12.5806 5.71201 12.4582 5.30846 12.1945 5.00536Z');
                            btn.classList.add('css-jpkqok');
                            btn.classList.remove('css-1d8juai');
                        }
                    }
                    if(document.querySelector("#login-idx")){
                        if(idx.userIdx == document.querySelector("#login-idx").title){
                            $(appendComm).find('div.recomment').remove();
                        } else{
                            $(appendComm).find('div.recomment-edit').remove();
                            if(idx.hasReport){
                                appendComm.querySelector("div.recomment div.css-19hkid5").innerHTML = '부적절한 표현 신고 취소';
                            }
                        }
                    }else{
                        $(appendComm).find('div.recomment-edit').remove();
                    }
                    document.querySelector("section#recomment-list div.css-0").insertBefore(appendComm,document.querySelector("section#recomment-list div.css-0 div.css-1m1whp6"));
                }
                if(data.comment.last == true){
                    $(`div.css-5hpf69`).remove();
                }else{
                    page++;
                }
            },
            error: function(){
                console.log('에러')
            }
        })
    })
}

// 클립보드 복사
const clipBoard = document.querySelector("#clip-board")
const copyURI = document.querySelector("div.css-pfmsf9>div.css-ve4kut>div.css-3wahtm")
copyURI.addEventListener('click', ()=>{
    clipBoard.style.bottom = '30px';
    let textarea = document.createElement("textarea");
    document.body.appendChild(textarea);
    textarea.value = window.location.href;
    textarea.select();
    document.execCommand("copy");
    document.body.removeChild(textarea);

    setTimeout(function() {
        clipBoard.style.bottom = '-100px';
    }, 2000);
})

document.querySelector("div[aria-label='share-url']").addEventListener('click', ()=>{
    clipBoard.style.bottom = '30px';
    let textarea = document.createElement("textarea");
    document.body.appendChild(textarea);
    textarea.value = window.location.href;
    textarea.select();
    document.execCommand("copy");
    document.body.removeChild(textarea);

    setTimeout(function() {
        clipBoard.style.bottom = '-100px';
    }, 2000);
})