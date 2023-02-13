window.onload= function () {
    console.log('-window.onload-');

};

function sendit() {
    const qnaText = document.getElementsByName("qnaText");
    const qnaName = document.getElementsByName("qnaName");

    if (qnaText[0].value == "") {
        alert("내용을 입력하세요.");
        qnaText.focus();
        return false;
    }

    if (confirm("문의를 등록 하시겠습니까?")) {
        alert('등록되었습니다.');
        document.getElementById('new_request').submit();
    }else{
        return;
    }


}