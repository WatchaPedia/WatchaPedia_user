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

    console.log('hi');
    let jumin = userSsn1.value + userSsn2.value +'';
    jumin = jumin.split(''); // 주민등록번호를 한자리씩 쪼개서 배열에 담기
    let ckarr = [2,3,4,5,6,7,8,9,2,3,4,5]; // 곱해줄 숫자 배열

    // 1. 각자리에 2,3,4,5,6,7,8,9,2,3,4,5를 곱해줌. 단 마지막 자리는 빼놓음
    for(let i=0; i<jumin.length-1; i++){
        jumin[i] = jumin[i] * ckarr[i];
    }
    let juminlast = jumin[jumin.length-1]; // 주민등록번호 마지막자리 따로 빼두기

    // 2. 각 자리의 숫자를 모두 더함
    let sum = 0;
    for(let i=0; i<jumin.length-1; i++){
        sum += jumin[i];
    }

    //3. 11로 나눈 나머지 값을 구함
    sum = sum % 11;

    // 4. 11에서 결과값을 뺌(단, 마지막 결과가 두자리인 경우 다시 10으로 나눈 나머지 값을 구함)

    sum = 11 - sum;

    if(sum > 9){
        sum = sum % 10;
    }

    // 5. 결과가 주민등록번호 마지막 자리와 일치하면 유효한 주민등록번호임
    if(sum != juminlast && juminlast != undefined){
        // 결과값과 주민등록번호 마지막 번호가 일치하지않는다면
        alert("주민번호가 유효하지 않습니다");
        return false;
    }

    return true;
    // 넘어가게함

}