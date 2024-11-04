$(document).ready(function() {
    // 사이드바에서 검색 버튼 클릭 이벤트 처리
    $('.sidenav button').on('click', function() {
        const keyword = $('.sidenav input[type="text"]').val().trim(); // 공백 제거
        if (keyword) {
            searchProducts(keyword);
        } else {
            alert('검색어를 입력해 주세요.'); // 빈 검색어 처리
        }
    });

    // 검색 API 호출 함수
    function searchProducts(keyword) {
        $.ajax({
            url: '/api/products/search',
            method: 'GET',
            data: { keyword: keyword },
            success: function(response) {
                renderSearchResults(response);
            },
            error: function() {
                alert('검색 결과를 가져오는 데 오류가 발생했습니다.'); // 사용자 피드백
                console.error('Error fetching search results');
            }
        });
    }

    // 검색 결과 렌더링 함수
    function renderSearchResults(products) {
        const container = $('#search-result-container');
        container.empty(); // 기존 결과 지우기

        if (products.length > 0) {
            products.forEach(product => {
                const productHtml = `
                    <div class="product-item">
                        <img src="${product.contentImg}" alt="${product.title}">
                        <h3>${product.title}</h3>
                        <p>${product.price}원</p>
                    </div>
                `;
                container.append(productHtml);
            });
        } else {
            container.append('<p>검색 결과가 없습니다.</p>');
        }
    }
});
