function checkLogin(callback) {
    $.get('/member/api/session', function(data) {
        if (data.userId) {
            callback(data.userId);
        } else {
            document.getElementById("loginPrompt").style.display = "block";
            callback(null);
        }
    });
}

function buy() {
    checkLogin((userId) =>{
        if (!userId) {
            alert("로그인 필요");
            window.location.href = '/member/login';
            return;
        }

        const salesVolume = parseInt(document.getElementById("userBuy").value.trim(), 10); // 정수로 변환
        const productId = parseInt(document.getElementById("productId").value.trim(), 10); // 정수로 변환
        // 가격 텍스트 추출 후 숫자만 남기고, 그 값을 정수로 변환
        const priceText = document.getElementById("price").textContent.trim();
        const price = parseInt(priceText.replace(/[^0-9]/g, ''), 10); // 숫자만 추출하여 정수로 변환

        console.log(price);
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
                $.get(`/product/detail/api/stock/${productId}`, function(updatedStock) {
                    // 서버에서 받아온 최신 재고 값으로 업데이트
                    document.querySelector('.product-stock').textContent = `재고: ${updatedStock} 개`;
                });
            },
            error: (error)=>{
                console.error('Error:', error);
                alert(error.responseText);
            }
        });
    });
}

// 페이지 로드 시 로그인 상태 체크
$(document).ready(function() {
    checkLogin((userId)=> {
        if (userId) {
            document.getElementById("loginPrompt").style.display = "none";
        }
    });
});