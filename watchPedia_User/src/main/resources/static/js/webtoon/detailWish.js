wish.addEventListener('click', () => {
    if(document.querySelector("#login-idx")) {
        $.ajax({
            url: '/wish/save',
            headers: {'Content-Type': 'application/json;charset=UTF-8'},
            data: JSON.stringify({
                contentType: "webtoon",
                contentIdx: contentIdx,
                userIdx: loginIdx.title
            }),
            type: "POST",
            dataType: "json",
            success: function (result) {
                if (result == true) {
                    wish.classList.remove('css-1tc9iuk-StylelessButton-ContentActionButton');
                    wish.classList.add('css-15hndx7-StylelessButton-ContentActionButton')
                    wishIcon.item(0).style.display = 'none';
                    wishIcon.item(1).style.display = 'block';
                    watch.classList.remove('css-15hndx7-StylelessButton-ContentActionButton')
                    watch.classList.add('css-1tc9iuk-StylelessButton-ContentActionButton');
                } else {
                    wish.classList.add('css-1tc9iuk-StylelessButton-ContentActionButton');
                    wish.classList.remove('css-15hndx7-StylelessButton-ContentActionButton')
                    wishIcon.item(1).style.display = 'none';
                    wishIcon.item(0).style.display = 'block';
                }
            }, error: function () {
                alert("에러발생!")
            }
        });
    }else{
        loginModalOn()
    }
})