$(document).ready(() => {
    $('#signIn').click((event)=> {
        event.preventDefault(); // 기본 폼 제출 방지

        let userId = $('#user_id').val();
        let password = $('#password').val();
        let referer = document.referrer;
        let formData = {
            username: userId,
            password: password,
            referer :referer
        };
        $.ajax({
            type: 'POST',
            url: '/login', // 서버의 엔드포인트 URL
            data: $.param(formData),
            contentType: 'application/x-www-form-urlencoded; charset=utf-8',
            dataType: 'json', // 서버에서 받을 데이터의 타입
            success: (response) => {
                if (response.loggedIn) {
                    window.location.href = response.url;
                }
                alert(response.message);
            },
            error: (error) => {
                console.error('오류 발생:', error);
                alert(error.responseJSON.message || '로그인 실패');
                window.location.href = error.responseJSON.url || '/'; // 기본 URL로 리다이렉트
            }
        });
    });
});
