function checkLogin(callback) {
    $.get('/member/api/session', function(data) {
        if (data.userId) {

            callback(data.userId);
        } else {
            callback(null);
        }
    });
}
window.checkLogin = checkLogin;
function buy() {
    checkLogin((userId) =>{
        if (!userId) {
            alert("로그인 필요");
            window.location.href = '/member/login';
            return;
        }

        const salesVolume = parseInt(document.getElementById("userBuy").value.trim(), 10); // 정수로 변환
        const productId = parseInt(document.getElementById("productId").value.trim(), 10); // 정수로 변환
        const price = parseInt(document.getElementById("price").textContent.trim(), 10); // 정수로 변환

        if (!salesVolume || salesVolume < 1) {
            alert("구매 수량을 입력해 주세요.");
            return;
        }

        const data = {
            productId:productId,
            stock: salesVolume,
            price: price
        };

        // AJAX 요청
        $.ajax({
            url: '/product/detail/api/buy',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function(response) {
                alert("구매 완료");
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
                alert("구매 실패.");
            }
        });
    });
}
$(document).ready(function() {
    checkLogin((userId) => {
        if (userId) {

            document.getElementById("loginPrompt").style.display = "none";
        } else {
            document.getElementById("loginPrompt").style.display = "block"; // 로그인 안 된 경우 보이기
        }
    });
});