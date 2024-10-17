
$(document).ready(() => {

    $('#signup').click(() => {
        event.preventDefault();
        let userId = $('#user_id').val().trim();
        let password = $('#password').val().trim();
        let location = $('#location').val().trim();
        let phone = $('#phoneNumber').val().trim();
        let name = $('#user-name').val().trim();

        let formData ={
            userId : userId,
            password : password,
            location : location,
            phone:phone,
            name:name
        }

        $.ajax(
            {
                type: 'POST',
                url: '/member/join',
                data: JSON.stringify(formData),
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function(response) {
                    alert('회원가입이 성공했습니다.\n로그인해주세요.')
                    window.location.href = response.url;
                },
                error: function(error) {
                    // 실패 시 실행될 콜백 함수
                    console.error('오류 발생:', error.responseJSON.message);
                    window.location.href =  error.responseJSON.url;
                    alert('회원가입 중 오류가 발생했습니다.');
                }
            }
        )


    });
});