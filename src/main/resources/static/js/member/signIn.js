$(document).ready(() => {
    $('#signIn').click((event) => {
        event.preventDefault(); // 기본 폼 제출 동작 방지

        let userId = $('#user_id').val();
        let password = $('#password').val();

        let formData = {
            username: userId,
            password: password
        };

        $.ajax({
            type: 'POST',
            url: '/login', // 서버의 엔드포인트 URL
            data: $.param(formData), // 데이터를 URL 인코딩된 형식으로 변환
            contentType: 'application/x-www-form-urlencoded; charset=utf-8',
            dataType: 'json', // 서버에서 받을 데이터의 타입
            success: (response) => {
                console.log('res :: ', response);
                if (response.loggedIn) {
                    window.location.href = response.url; // 로그인 성공 시 URL 리다이렉트
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
