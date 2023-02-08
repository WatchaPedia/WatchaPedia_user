const container = document.querySelectorAll(".w_exposed_cell");

try{
    window.onload = scrollContent();
    window.addEventListener('resize',()=>{
        scrollContent()
    })
    function scrollContent(){
    // 메인 스크롤
    for(let idx of container) {
        let scrollBox = idx.querySelector('.css-awu20a');
        let scrollMax = scrollBox.scrollWidth - (idx.querySelector('.css-119xxd7').getBoundingClientRect().width+10);
        // 스크롤 위치에 따라 버튼 유무
        idx.querySelector('.css-awu20a').addEventListener('scroll', () => {
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
    for(let idx of container){
        idx.querySelector('.css-vp7uyl').addEventListener('click', function () {
            idx.querySelector('.css-awu20a').scrollBy(idx.querySelector('.css-awu20a').getBoundingClientRect().width+5,0)
        })
        idx.querySelector('.css-1hestod').addEventListener('click', function () {
            idx.querySelector('.css-awu20a').scrollBy(-idx.querySelector('.css-awu20a').getBoundingClientRect().width+5,0)
        })
    }}
}catch (Exception){console.log("오류 발생")}
