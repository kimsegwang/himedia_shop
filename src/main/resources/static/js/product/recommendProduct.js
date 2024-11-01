function getRecommendProduct() {
    const productId = document.getElementById("productId").value;

    $.ajax({
        url: `/product/detail/recommendations?productId=${productId}`, // 추천 상품을 가져오는 URL
        type: 'GET', // GET 요청
        success: function(data) {
            $('#recommendations').empty(); // 기존 목록 비우기
            data.forEach(product => { // data의 각 상품을 반복 처리
                $('#recommendations').append(
                    `<li class="recommend-product" data-product-id="${product.id}" style="cursor: pointer;"> 
                        <img style="width: 150px; height: 150px;" src="${product.contentImg}" alt="Product Image" />
                        <br>
                        <strong>제목:</strong> ${product.title} <br>
                        <strong>내용:</strong> ${product.content} <br>
                        <strong>가격:</strong> ${product.price} <br>
                    </li>`
                );
            });

            // 클릭 이벤트를 AJAX 성공 후에 등록
            $('#recommendations').on('click', '.recommend-product', function() {
                const productId = $(this).data('product-id');
                window.location.href = `/product/detail/${productId}`;
            });
        },
        error: function(error) {
            console.error("Error fetching recommendations:", error); // 에러 처리
            alert("추천 상품을 가져오는 데 실패했습니다."); // 사용자에게 알림
        }
    });
}
