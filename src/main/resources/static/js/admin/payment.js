$(document).ready(function() {
    // 사용자 ID 목록 가져오기
    $.ajax({
        url: '/admin/getUserIds',  // 사용자 ID 목록을 가져오는 API
        method: 'GET',
        success: function(data) {
            const userList = $('#user-list');
            userList.empty(); // 기존 목록 초기화

            data.forEach(function(user) {
                const listItem = $('<li></li>')
                    .text(user.userId)
                    .click(function() {
                        $('#user-id').val(user.userId); // 선택한 사용자 ID 설정
                    });
                userList.append(listItem); // 리스트에 사용자 ID 추가
            });
        },
        error: function(xhr, status, error) {
            console.error('Error fetching user IDs:', error);
            alert('사용자 목록을 가져오는 데 실패했습니다.');
        }
    });

    $('#submit-payment').on('click', function() {
        const userId = $('#user-id').val();
        const deposit = parseInt($('#deposit').val()) || 0; // 기본값 0
        const withdrawal = parseInt($('#withdrawal').val()) || 0; // 기본값 0

        // 입력값 유효성 검사
        if (!userId) {
            alert('사용자 ID를 선택해야 합니다.');
            return;
        }
        if (deposit === 0 && withdrawal === 0) {
            alert("입력값은 0 이상이어야 합니다.");
            return; // AJAX 요청을 실행하지 않도록 early return
        }

        // AJAX 요청 실행
        $.ajax({
            url: '/admin/payment',  // 실제 API 경로로 수정
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                userId: userId,
                deposit: deposit,
                withdrawal: withdrawal
            }),
            success: function(response) {
                alert('결제가 성공적으로 추가되었습니다.');
                // 입력 필드 초기화
                $('#deposit').val(0);
                $('#withdrawal').val(0);
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
                alert('결제 추가에 실패했습니다.');
            }
        });
    });
});
