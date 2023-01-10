const container = document.querySelectorAll(".w_exposed_cell");

try{
    // 메인 스크롤
    for(let idx of container) {
        let scrollBox = idx.querySelector('.css-9dnzub');
        let scrollMax = scrollBox.scrollWidth - (idx.querySelector('.css-119xxd7').getBoundingClientRect().width-50);
        // 스크롤 위치에 따라 버튼 유무
        idx.querySelector('.css-9dnzub').addEventListener('scroll', () => {
            if(idx.querySelector(".css-9dnzub").closest("[data-rowindex='1']")||
                idx.querySelector(".css-9dnzub").closest("[data-rowindex='2']")||
                idx.querySelector(".css-9dnzub").closest("[data-rowindex='3']")
            ){
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
            }else if(idx.querySelector(".css-9dnzub").closest("[data-rowindex='11']")){
                if (scrollBox.scrollLeft >= scrollMax-40) {
                    idx.querySelector('.css-vp7uyl').style.display = "none";
                } else {
                    idx.querySelector('.css-vp7uyl').style.display = "flex";
                }
                if (scrollBox.scrollLeft == 0) {
                    idx.querySelector('.css-1hestod').style.display = "none";
                } else {
                    idx.querySelector('.css-1hestod').style.display = "block";
                }
            }
            else{
                if (scrollBox.scrollLeft >= scrollMax-20) {
                    idx.querySelector('.css-vp7uyl').style.display = "none";
                } else {
                    idx.querySelector('.css-vp7uyl').style.display = "flex";
                }
                if (scrollBox.scrollLeft == 0) {
                    idx.querySelector('.css-1hestod').style.display = "none";
                } else {
                    idx.querySelector('.css-1hestod').style.display = "block";
                }
            }
        })
    }

    // 버튼 클릭 시 이동
    for(let idx of container){
        idx.querySelector('.css-vp7uyl').addEventListener('click', function () {
            idx.querySelector('.css-9dnzub').scrollBy(idx.querySelector('.css-9dnzub').getBoundingClientRect().width+15,0)
        })
        idx.querySelector('.css-1hestod').addEventListener('click', function () {
            idx.querySelector('.css-9dnzub').scrollBy(-idx.querySelector('.css-9dnzub').getBoundingClientRect().width+15,0)
        })
    }
}catch (Exception){console.log("오류 발생")}
