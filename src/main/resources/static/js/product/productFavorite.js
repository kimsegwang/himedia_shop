$(document).ready(function() {
    const productId = $('#productId').val().trim();
    let favorite = false; // 찜 상태를 관리하기 위한 변수

    // 로그인 체크 후 찜 목록 가져오기
    checkLogin(function(userId) {
        if (userId) {
            getUserFavorites(userId, productId);

            // 페이지 이동 시 찜 상태를 서버에 업데이트
            $(window).on('beforeunload', function() {
                const userId = $('#userId').val().trim(); // 사용자 ID 가져오기
                    $.ajax({
                        url: '/product/detail/api/wishlist',
                        type: 'POST',
                        contentType: 'application/json',
                        data: JSON.stringify({ userId, favorite, productId }),
                        success: function() {
                            console.log('찜 목록이 성공적으로 업데이트되었습니다.');
                        },
                        error: function() {
                            console.error('찜 목록 업데이트에 실패했습니다.');
                        }
                    });
            });
        }
    });

    // 찜 목록 확인 함수
    function getUserFavorites(userId, productId) {
        $.ajax({
            url: `/product/detail/api/wishlistCheck?userId=${userId}&productId=${productId}`,
            method: 'GET',
            success: ()=> {
                favorite = true; // 찜 상태 설정
                updateButtonUI(); // 버튼 UI 업데이트
            },
            error: ()=> {
                    favorite = false; // 찜 목록에 없으면 false 설정
                    updateButtonUI(); // 버튼 UI 업데이트
            }
        });
    }


    // 찜 버튼 클릭 시 호출되는 함수
    window.toggleFavorite = function() {
        favorite = !favorite; // 찜 상태 토글
        updateButtonUI(); // 버튼 UI 업데이트
    };

    // 버튼 UI 업데이트 함수
    function updateButtonUI() {
        const button = $('#favoriteBtn');
        if (favorite) {
            button.text('찜 해제').removeClass('not-favorited').addClass('favorited');
        } else {
            button.text('찜 하기').removeClass('favorited').addClass('not-favorited');
        }
        button.addClass('clicked');
        setTimeout(() => button.removeClass('clicked'), 300); // 클릭 효과 제거
    }
});
