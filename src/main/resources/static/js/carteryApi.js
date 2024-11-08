$(document).ready(function() {
    // 정렬 선택 박스에서 값이 변경될 때마다 AJAX 요청 보내기
    $('#sortSelect').change(function() {

        var selectedOption = $(this).val(); // 선택된 정렬 옵션 값 가져오기

        const categoryName = document.getElementById('categoryName').textContent;  // id="categoryName"인 <h1>의 텍스트 가져오기
        const subCategoryName = document.getElementById('subCategoryName').textContent;  // id="subCategoryName"인 <h2>의 텍스트 가져오기

        // AJAX 요청 보내기
        $.ajax({
            url: '/search/order/cartagory', // 서버에 데이터를 요청할 URL
            method: 'GET',  // GET 방식으로 요청
            data: {
                categoryName: categoryName,  // `searchKeyword`로 서버에 전달
                subCategoryName: subCategoryName,  // `searchKeyword`로 서버에 전달
                sortBy: selectedOption  // 선택된 정렬 옵션 값
            },
            success: function(response) {
                // 서버에서 받은 상품 데이터를 HTML로 렌더링
                renderSearchResults(response);
            },
            error: function(xhr, status, error) {
                console.error("AJAX 요청 실패:", error);
            }
        });
    });

    // 검색 결과 렌더링 함수
    function renderSearchResults(products) {
        const container = $('#search-result-container');
        container.empty(); // 기존 결과 지우기

        if (products && products.length > 0) {
            products.forEach(product => {
                const productHtml = `
                    <div class="product-item">
                        <a href="/product/detail/${product.id}" style="text-decoration: none; color: inherit;">
                            <img src="${product.contentImg}" alt="Product Image">
                            <h3>${product.title}</h3>
                            <h4>${product.created}</h4>
                            <p>${product.price} 원</p>
                        </a>
                    </div>
                `;
                container.append(productHtml); // 상품을 동적으로 추가
            });
        } else {
            container.append('<p>검색 결과가 없습니다.</p>'); // 결과가 없으면 메시지 표시
        }
    }
});
document.addEventListener("DOMContentLoaded", function() {
    const selectElement = document.getElementById('sortSelect');

    selectElement.addEventListener('change', function() {
        // 선택된 옵션에 대한 작업은 이미 CSS에서 처리되므로,
        // JavaScript로 특별히 클래스를 추가할 필요는 없습니다.
    });


});
