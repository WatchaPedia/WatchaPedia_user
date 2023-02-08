// 스크롤 이벤트
try {
    const navbar = document.querySelector('.css-6k8tqb');
    const logo = document.querySelector('.css-12v09xw');
    const textList = document.querySelectorAll('.css-q65tx9-StylelessButton');
    const selectText = document.querySelector('button.css-x6oby2-StylelessButton');
    const label = document.querySelector('.css-kyr608');
    const evaluation = document.querySelector('.css-1kqg656');
    const navbarHeight = navbar.getBoundingClientRect().height;
    const loginBtn = document.querySelector("button.css-fn0ezc-StylelessButton")
    const signup = document.querySelector("button.css-139vxi-StylelessButton")
    document.addEventListener('scroll', () => { // 스크롤시 이벤트 발생
        if (window.scrollY < (navbarHeight * 2)) {
            navbar.classList.remove('css-6k8tqb');
            navbar.classList.add('css-5brfx4');

            logo.classList.remove('css-12v09xw');
            logo.classList.add('css-1l2btz0');

            selectText.setAttribute("style",'color:rgba(255, 255, 255, 0.7);')
            for (const text of textList) {
                text.classList.remove('css-q65tx9-StylelessButton');
                text.classList.add('css-wb1ml1-StylelessButton');
            }

            label.classList.remove('css-kyr608');
            label.classList.add('css-y4utrt');

            if(document.querySelector("#login-idx")) {
                evaluation.classList.remove('css-1kqg656');
                evaluation.classList.add('css-f74c5f');
            }else{
                loginBtn.setAttribute("style",'color:rgba(255, 255, 255, 0.7);')
                signup.setAttribute('style','border:1px solid rgba(255, 255, 255, 0.25);' +
                    'color: rgba(255, 255, 255, 0.7);');
            }
        } else { // 스크롤을 navbar 높이의 두배만큼 내렸을때 navbar--bold 클래스 추가
            navbar.classList.remove('css-5brfx4');
            navbar.classList.add('css-6k8tqb');

            logo.classList.remove('css-1l2btz0');
            logo.classList.add('css-12v09xw');

            selectText.setAttribute("style",'color:#353535;')
            for (const text of textList) {
                text.classList.remove('css-wb1ml1-StylelessButton');
                text.classList.add('css-q65tx9-StylelessButton');
            }

            label.classList.remove('css-y4utrt');
            label.classList.add('css-kyr608');

            if(document.querySelector("#login-idx")){
                evaluation.classList.remove('css-f74c5f');
                evaluation.classList.add('css-1kqg656');
            }else{
                loginBtn.setAttribute("style",'color:#74747b')
                signup.setAttribute('style','border:1px solid rgba(116,116,123,0.5);' +
                    'color: #353535;');
            }
        }
    })
}catch(Exception){console.log("로그인안됨")}

// 인물 스크롤
try{
    const people = document.querySelector("[data-rowindex='7']")

    const peoLeft = people.querySelector('.css-1hestod');
    const peoRight = people.querySelector(".css-vp7uyl");
    const peopleScroll = people.querySelector('.css-awu20a');
    window.onload = scrollContent();
    window.addEventListener('resize',()=>{
        scrollContent()
    })
    function scrollContent(){
    // 스크롤 위치에 따라 버튼 유무
    const peoMax= peopleScroll.scrollWidth-peopleScroll.getBoundingClientRect().width;
    peopleScroll.addEventListener('scroll', () => {
        if(peopleScroll.scrollLeft >= peoMax){
            peoRight.style.display="none";
        }else{
            peoRight.style.display="flex";
        }
        if(peopleScroll.scrollLeft==0){
            peoLeft.style.display="none";
        }else{
            peoLeft.style.display="block";
        }
    })
        if(0>= peoMax){
            people.querySelector('.css-vp7uyl').style.display = "none";
            people.querySelector('.css-1hestod').style.display = "none";
        }
    peoRight.addEventListener('click', function () {
        let peoWid = people.querySelector('.e5xrf7a0').getBoundingClientRect().width+5;
        peopleScroll.scrollBy(peoWid,0)
    })
    peoLeft.addEventListener('click', function () {
        let peoWid = people.querySelector('.e5xrf7a0').getBoundingClientRect().width + 5;
        peopleScroll.scrollBy(-peoWid, 0)

})}}catch (Exception){console.log("오류 발생")}



// 갤러리, 영상 container
// 갤러리 스크롤
try{
    const gallery = document.querySelector("section.gallery")
    const gallLeft = gallery.querySelector('.css-1hestod');
    const gallRight = gallery.querySelector(".css-vp7uyl");
    const galleryScroll = gallery.querySelector('.css-15xcaei');

    window.onload = scrollContent();
    window.addEventListener('resize',()=>{
        scrollContent()
    })
    function scrollContent(){
    const galMax = galleryScroll.scrollWidth-galleryScroll.getBoundingClientRect().width-5;

    // 스크롤 위치에 따라 버튼 유무
    galleryScroll.addEventListener('scroll', () => {
        if(galleryScroll.scrollLeft >= galMax){
            gallRight.style.display="none";
        }else{
            gallRight.style.display="flex";
        }
        if(galleryScroll.scrollLeft==0){
            gallLeft.style.display="none";
        }else{
            gallLeft.style.display="block";
        }
    })
        if(0>= galMax){
            gallLeft.style.display = "none";
            gallRight.style.display = "none";
        }
    gallRight.addEventListener('click', function () {
        let gallWid = gallery.querySelector('.css-1cduxg0-VisualUl').getBoundingClientRect().width+8;
        galleryScroll.scrollBy(gallWid,0)
    })
    gallLeft.addEventListener('click', function () {
        let gallWid = gallery.querySelector('.css-1cduxg0-VisualUl').getBoundingClientRect().width+8;
        galleryScroll.scrollBy(-gallWid,0)
})}}catch (Exception){console.log("갤러리가 없습니다")}

// 영상 스크롤
try{
    const video = document.querySelector("section.video")
    const vedLeft = video.querySelector('.css-1hestod');
    const vedRight = video.querySelector(".css-vp7uyl");
    const videoScroll = video.querySelector('.css-15xcaei');

    window.onload = scrollContent();
    window.addEventListener('resize',()=>{
        scrollContent()
    })
    function scrollContent(){
    const vedMax = videoScroll.scrollWidth-videoScroll.getBoundingClientRect().width;
    // 스크롤 위치에 따라 버튼 유무
    videoScroll.addEventListener('scroll', () => {
        if(videoScroll.scrollLeft >= vedMax){
            vedRight.style.display="none";
        }else{
            vedRight.style.display="flex";
        }
        if(videoScroll.scrollLeft==0){
            vedLeft.style.display="none";
        }else{
            vedLeft.style.display="block";
        }
    })
        if(0>= vedMax){
            video.querySelector('.css-vp7uyl').style.display = "none";
            video.querySelector('.css-1hestod').style.display = "none";
        }

    vedRight.addEventListener('click', function () {
        let vedWid = video.querySelector('.e10pt7680').getBoundingClientRect().width+8;
        videoScroll.scrollBy(vedWid,0)
    })
    vedLeft.addEventListener('click', function () {
        let vedWid = video.querySelector('.e10pt7680').getBoundingClientRect().width+8;
        videoScroll.scrollBy(-vedWid,0)
})}}catch (Exception){console.log("영상이 없습니다")}


// 코멘트 스크롤
try{
    const comment = document.querySelector("[data-rowindex='10']")

    const comLeft = comment.querySelector('.css-1hestod');
    const comRight = comment.querySelector(".css-vp7uyl");
    const commentScroll = comment.querySelector('.css-15xcaei');

    window.onload = scrollContent();
    window.addEventListener('resize',()=>{
        scrollContent()
    })
    function scrollContent(){
    const comMax= commentScroll.scrollWidth-comment.querySelector('.css-174lxc3').getBoundingClientRect().width-15;
    // 스크롤 위치에 따라 버튼 유무
    commentScroll.addEventListener('scroll', () => {
        if(commentScroll.scrollLeft >= comMax){
            comRight.style.display="none";
        }else{
            comRight.style.display="flex";
        }
        if(commentScroll.scrollLeft==0){
            comLeft.style.display="none";
        }else{
            comLeft.style.display="block";
        }
    })
        if(0>= comMax){
            comment.querySelector('.css-vp7uyl').style.display = "none";
            comment.querySelector('.css-1hestod').style.display = "none";
        }
    comRight.addEventListener('click', function () {
        let comWid = comment.querySelector('.emmoxnt0').getBoundingClientRect().width+12;
        commentScroll.scrollBy(comWid,0)
    })
    comLeft.addEventListener('click', function () {
        let comWid = comment.querySelector('.emmoxnt0').getBoundingClientRect().width+12;
        commentScroll.scrollBy(-comWid,0)
})}}catch (Exception){console.log("오류 발생")}

// 클립보드 복사
const clipBoard = document.querySelector("#clip-board")
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