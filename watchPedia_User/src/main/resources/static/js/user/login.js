function sendit(){
    const userEmail = document.getElementById('userEmail');
    const userPw = document.getElementById('userPw');

    const expEmailText = /^[A-Za-z0-9\-\.]+@[A-Za-z0-9\-\.]+\.[A-Za-z0-9]+$/;

    console.log("클릭됨")
    if(!expEmailText.test(userEmail.value)){
        alert('이메일 형식을 확인하세요');
        return false;
    }

    if(userEmail.context.value == ''){
        // 아무것도 입력되지 않는 비밀번호 칸
        alert('이메일을 입력하세요');
        userEmail.focus();
        return false;
        // 안넘어가게함
    }

    if(userPw.context.value == ''){
        // 아무것도 입력되지 않는 비밀번호 칸
        alert('비밀번호를 입력하세요');
        userPw.focus();
        return false;
        // 안넘어가게함
    }

    return true;
    // 넘어가게함
}