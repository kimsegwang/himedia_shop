$(document).ready(function() {
    $('#fetch-review-counts').click(function() {
        fetchReviewCounts(); // 버튼 클릭 시 리뷰 개수 가져오기
    });

    $('#prev-page').on('click', function() {
   
        if (currentPage > 0) {
            currentPage--;
            const productId = $('#product-review-list-container .review-item.active').data('product-id');
            if (productId) {
                getReviews(productId, currentPage);
            }
        }
    });

    $('#next-page').on('click', function() {

        currentPage++;
        const productId = $('#product-review-list-container .review-item.active').data('product-id');
        if (productId) {
            getReviews(productId, currentPage);
        }
    });
});

let currentPage = 0; // 현재 페이지
const size = 5; // 페이지당 리뷰 수

function fetchReviewCounts() {
    $.ajax({
        url: '/admin/product-review-count-list',  // API 엔드포인트
        method: 'GET',
        success: function(data) {
            displayReviewCounts(data);
        },
        error: function(xhr, status, error) {
            console.error("에러 발생:", error);
            alert("리뷰 개수를 가져오는 데 실패했습니다.");
        }
    });
}

function displayReviewCounts(data) {
    const container = $('#product-review-list-container');
    container.empty(); // 기존 내용 초기화

    data.forEach(function(review) {
        const reviewItem = `<div class="review-item" data-product-id="${review.id}">
            <strong>제품 ID:</strong> ${review.id} <br>
            <strong>제품 제목:</strong> ${review.title} <br>
            <strong>리뷰 개수:</strong> ${review.reviewcount}
        </div>`;
        container.append(reviewItem);
    });

    // 클릭 이벤트 추가
    $(document).on('click', '.review-item', function() {
        $('.review-item').removeClass('active'); // 이전 active 제거
        $(this).addClass('active'); // 클릭한 제품에 active 추가
        const productId = $(this).data('product-id');
        currentPage = 0; // 페이지 초기화
        getReviews(productId, currentPage); // 해당 제품의 리뷰 가져오기
    });
}

function getReviews(productId, page = currentPage) {
    const startTime = performance.now(); // 요청 시작 시간

    $.ajax({
        url: `/api/reviews?productId=${productId}&page=${page}&size=${size}`, // AJAX 요청
        method: 'GET',
        success: function(data) {
            const endTime = performance.now(); // 종료 시간 기록
            const duration = endTime - startTime; // 소요 시간 계산
            console.log(`요청 시간: ${duration}ms`);
            console.log(`Fetching reviews for product ID: ${productId}, page: ${page}`);
            displayProductReviews(data.reviews); // 리뷰 표시 함수 호출
            updatePagination(data); // 페이지네이션 업데이트
        },
        error: function(xhr, status, error) {
            console.error('Error fetching reviews:', error);
        }
    });
}



function updatePagination(data) {
    $('#current-page').text(currentPage + 1); // 현재 페이지 표시
    $('#prev-page').prop('disabled', currentPage === 0);
    $('#next-page').prop('disabled', currentPage >= data.totalPages - 1);
}

function displayProductReviews(reviews) {
    const container = $('#review-list');
    container.empty(); // 기존 내용 초기화

    if (reviews.length === 0) {
        container.append('<p>리뷰가 없습니다.</p>');
        return;
    }

    // 테이블 생성
    const table = $('<table></table>').addClass('reviews-table');
    const headerRow = $('<tr></tr>').append(
        '<th>작성자 ID</th>',
        '<th>리뷰 제목</th>',
        '<th>리뷰 내용</th>',
        '<th>리뷰 날짜</th>',
        '<th>점수</th>',
        '<th>이미지</th>',
        '<th>작업</th>' // 작업 버튼 열 추가
    );
    table.append(headerRow);

    // 리뷰를 반복하여 테이블 행 생성
    reviews.forEach(function(review) {
        const reviewRow = $('<tr></tr>').attr('id', `review-${review.id}`).append(
            `<td>${review.userId}</td>`,
            `<td>${review.title}</td>`,
            `<td>${review.review}</td>`,
            `<td>${review.reviewDate}</td>`,
            `<td>${review.score}</td>`,
            `<td><img src="${review.reviewImg}" alt="리뷰 이미지" style="width: 100px;"></td>`,
            `<td><button class="delete-button" data-review-id="${review.id}">삭제</button></td>`
        );
        table.append(reviewRow);
    });

    // 테이블을 컨테이너에 추가
    container.append(table);
}

$(document).on('click', '.delete-button', function() {
    const reviewId = $(this).data('review-id');

    if (confirm("정말 이 리뷰를 삭제하시겠습니까?")) {
        $.ajax({
            url: '/api/review/' + reviewId,
            method: 'DELETE',
            success: function(response) {
                // DOM에서 리뷰 행 제거
                $(`#review-${reviewId}`).remove();
                alert("리뷰가 성공적으로 삭제되었습니다.");

                // 현재 페이지에서 리뷰가 없으면 이전 페이지로 이동
                const productId = $('#product-review-list-container .review-item.active').data('product-id');
                getReviews(productId, currentPage); // 현재 제품의 리뷰를 새로 고침
            },
            error: function(xhr, status, error) {
                console.error('Error deleting review:', error);
                alert("리뷰 삭제에 실패했습니다.");
            }
        });
    }
});
