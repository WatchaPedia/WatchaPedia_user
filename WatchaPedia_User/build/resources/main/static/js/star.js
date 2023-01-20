// const drawStar = (e) => {
//   console.log(e.value)
//   const star = e.parentNode.childNodes[1];
//   star.style.width = `${e.value * 20}%`;
//
// }

document.addEventListener('click', (e) => {
  let width = e.target.parentElement.querySelector("span").style.width;
  let spanWid = width.substring(0,(width.length-1))
  const star = e.target.parentNode.childNodes[1];
  if(e.target.type == 'range'){
    if (e.target.value * 20 == spanWid) {
      console.log("동일함")
      star.style.width = 0;
    }else{
      star.style.width = `${e.target.value * 20}%`;
    }
  }
})
