function sendit(){
    const userName = document.getElementById('userName');
    const userEmail = document.getElementById('userEmail');
    const userPw = document.getElementById('userPw');
    const userSsn1 = document.getElementById('userSsn1');
    const userSsn2 = document.getElementById('userSsn2')

    const expEmailText = /^[A-Za-z0-9\-\.]+@[A-Za-z0-9\-\.]+\.[A-Za-z0-9]+$/;

    if(userName.value == ''){
        // 아무것도 입력되지 않는 아이디 칸
        alert('이름을 입력하세요');
        userName.focus();
        return false;
        // 안넘어가게함
    }

    if(!expEmailText.test(userEmail.value)){
        alert('이메일 형식을 확인하세요');
        return false;
    }

    if(userPw.value == ''){
        // 아무것도 입력되지 않는 비밀번호 칸
        alert('비밀번호를 입력하세요');
        userPw.focus();
        return false;
        // 안넘어가게함
    }

    if(userEmail.value == ''){
        // 아무것도 입력되지 않는 비밀번호 칸
        alert('비밀번호를 입력하세요');
        userEmail.focus();
        return false;
        // 안넘어가게함
    }

    if(userSsn1.value == ''){
        alert('주민번호 앞자리를 입력하세요');
        userSsn1.focus();
        return false;
        // 안넘어가게함
    }

    if(userSsn2.value == ''){
        alert('주민번호 뒷자리를 입력하세요');
        userSsn2.focus();
        return false;
        // 안넘어가게함
    }

    return true;
    // 넘어가게함

}