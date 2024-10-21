function  buy() {
    const stock = document.getElementById("stock");
    const salesVolume = document.getElementById("userBuy");
    const productId = document.getElementById("productId");
    const userId = document.getElementById("userId");
    const price = document.getElementById("price");

    if (!salesVolume || salesVolume < 1) {
        alert("구매 수량을 입력해 주세요.");
        return;
    }
    if(stock<salesVolume){
        alert("재고 부족");
        return;
    }
    const data = {
        salesVolume: salesVolume,
        stock: productId,
        userId: userId,
        price: price
    };
    // AJAX 요청
    $.ajax({
        url: '/product/detail/api/stock', // 요청을 보낼 URL
        method: 'POST', // HTTP 메서드
        contentType: 'application/json', // 데이터 형식
        data: JSON.stringify(data), // JSON 형식으로 데이터 전송
        success: function (response) {
            //결재 금액
            alert("구매 완료");
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
            alert("구매 실패.");
        }
    });

}


