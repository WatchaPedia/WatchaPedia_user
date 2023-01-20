const wishWatch = document.querySelectorAll('.e1svyhwg23');

const wish = wishWatch.item(0);
const wishIcon = wish.querySelectorAll('.injected-svg');

wish.addEventListener('click',()=>{
    if(wish.classList.contains('css-1tc9iuk-StylelessButton-ContentActionButton')){
        wish.classList.remove('css-1tc9iuk-StylelessButton-ContentActionButton');
        wish.classList.add('css-15hndx7-StylelessButton-ContentActionButton')
        wishIcon.item(0).style.display='none';
        wishIcon.item(1).style.display='block';
    }else{
        wish.classList.add('css-1tc9iuk-StylelessButton-ContentActionButton');
        wish.classList.remove('css-15hndx7-StylelessButton-ContentActionButton')
        wishIcon.item(1).style.display='none';
        wishIcon.item(0).style.display='block';
    }
    if(wishWatch.item(1).classList.contains('css-15hndx7-StylelessButton-ContentActionButton')){
        wishWatch.item(1).classList.remove('css-15hndx7-StylelessButton-ContentActionButton')
        wishWatch.item(1).classList.add('css-1tc9iuk-StylelessButton-ContentActionButton');
    }
})

const watch = wishWatch.item(1);
const watchIcon = watch.querySelectorAll('.injected-svg');

watch.addEventListener('click',()=>{
    if(watch.classList.contains('css-1tc9iuk-StylelessButton-ContentActionButton')){
        watch.classList.remove('css-1tc9iuk-StylelessButton-ContentActionButton');
        watch.classList.add('css-15hndx7-StylelessButton-ContentActionButton')
    }else{
        watch.classList.add('css-1tc9iuk-StylelessButton-ContentActionButton');
        watch.classList.remove('css-15hndx7-StylelessButton-ContentActionButton')
    }
    if(wishWatch.item(0).classList.contains('css-15hndx7-StylelessButton-ContentActionButton')){
        wishWatch.item(0).classList.remove('css-15hndx7-StylelessButton-ContentActionButton')
        wishWatch.item(0).classList.add('css-1tc9iuk-StylelessButton-ContentActionButton');

        wishIcon.item(1).style.display='none';
        wishIcon.item(0).style.display='block';
    }
})