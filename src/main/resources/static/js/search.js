$(document).ready(function() {
    // 정렬 선택 박스에서 값이 변경될 때마다 AJAX 요청 보내기
    $('#sortSelect').change(function() {

        var selectedOption = $(this).val(); // 선택된 정렬 옵션 값 가져오기

        // searchKeyword 값을 HTML에서 동적으로 가져오기
        var searchKeyword = $('#search-text').text().trim();  // `id="search-text"`에서 값을 가져옵니다.
        if (!searchKeyword || searchKeyword.trim() === "") {
            alert("검색어를 입력해주세요.");
            return;  // 비어 있으면 요청을 보내지 않음
        }
        // AJAX 요청 보내기
        $.ajax({
            url: '/search/order', // 서버에 데이터를 요청할 URL
            method: 'GET',  // GET 방식으로 요청
            data: {
                searchKeyword: searchKeyword,  // `searchKeyword`로 서버에 전달
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

$(document).ready(function() {
    let currentPage = 0; // 현재 페이지 (0부터 시작)
    const pageSize = 9;  // 한 번에 로드할 데이터 개수
    const searchKeyword = $('#search-text').text().trim(); // 검색 키워드 (기존 HTML에서 받아오기)

    // 스크롤 이벤트를 감지하여 무한 스크롤 동작
    $(window).scroll(function() {
        // 사용자가 페이지 하단에 거의 다 도달했을 때
        if ($(window).scrollTop() + $(window).height() >= $(document).height() - 200) {
            // 페이지가 아직 로딩 중이 아니면
            if (!$('#loading').length) {
                // 로딩 상태 표시
                $('body').append('<div id="loading">Loading...</div>');

                // 다음 페이지 데이터를 요청
                currentPage++;

                // 데이터 로드
                loadMoreData(currentPage);
            }
        }
    });

    // 데이터 로드 함수
    function loadMoreData(page) {

        $.ajax({
            url: '/search-list', // 검색 URL (필요에 따라 수정)
            type: 'GET',
            data: {
                keyword: searchKeyword,
                page: page,  // 페이지 번호
            },
            success: function(response) {
                // 로딩 상태 제거
                $('#loading').remove();

                if (response && response.length > 0) {
                    // 새로운 데이터가 있으면 화면에 추가
                    appendProducts(response);
                } else {
                    // 더 이상 데이터가 없으면
                    $(window).off('scroll'); // 스크롤 이벤트 제거
                    $('.container').append('<div id="no-more-data" style="text-align: center;">No more products</div>');  // 더 이상 데이터 없음 표시
                }
            },
            error: function() {
                console.error('데이터 로딩 실패');
                $('#loading').remove();
            }
        });
    }

    // 받아온 데이터를 화면에 추가하는 함수
    function appendProducts(products) {
        const container = $('#search-result-container');

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
            container.append(productHtml);  // 기존 컨테이너에 추가
        });
    }
});
