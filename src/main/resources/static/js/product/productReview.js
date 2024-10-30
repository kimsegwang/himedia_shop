$(document).ready(() => {
    getReviews(); // 페이지 로드 시 리뷰 가져오기
    getRecommendProduct(); // 페이지 로드시 추천 제품 가져오기
    document.getElementById("review-image").addEventListener("change", previewImage);
});

// 리뷰 가져오기 함수
let currentPage = 0; // 현재 페이지
const size = 5; // 페이지당 리뷰 수

function getReviews(page = currentPage) {
    const productId = document.getElementById("productId").value;
    const userId = document.getElementById("userId").value;
    const startTime = performance.now();
    $.ajax({
        url: `/api/reviews?productId=${productId}&page=${page}&size=${size}`,
        method: 'GET',
        success: function(data) {
            const endTime = performance.now(); // 종료 시간 기록
            const duration = endTime - startTime; // 소요 시간 계산
            console.log(`요청 시간: ${duration}ms`);
            $('#reviews').empty(); // 기존 목록 비우기

            data.reviews.forEach(review => {
                const deleteButton = (review.userId === userId)
                    ? `<button class="delete-button" data-review-id="${review.id}">삭제</button>`
                    : '';
                const reviewImg = review.reviewImg
                    ? `<strong><img style="width: 150px; height: 150px;" src="${review.reviewImg.startsWith('/') ? review.reviewImg : '/' + review.reviewImg}" alt="Review Image" /></strong>`
                    : '';

                $('#reviews').append(
                    `<li id="review-${review.id}">
                        ${reviewImg}<br>
                        <strong>제목:</strong> ${review.title} <br>
                        <strong>내용:</strong> ${review.review} <br>
                        <strong>작성자:</strong> ${review.userId} <br>
                        ${generateStars(review.score)} 
                        ${deleteButton}
                    </li>`
                );
            });

            updatePagination(data);
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

$(document).ready(() => {
    $('#prev-page').on('click', function() {
        if (currentPage > 0) {
            currentPage--;
            getReviews(currentPage);
        }
    });

    $('#next-page').on('click', function() {
        currentPage++;
        getReviews(currentPage);
    });
});

function generateStars(score) {
    const fullStars = Math.floor(score);
    const halfStar = score % 1 ? '<i class="fa fa-star-half-o filled get-star"></i>' : '';
    return `<div class="star-container">${'<i class="fa fa-star filled get-star"></i>'.repeat(fullStars)}${halfStar}${'<i class="fa fa-star get-star"></i>'.repeat(5 - fullStars - (halfStar ? 1 : 0))}</div>`;
}

// 모달 열기 및 닫기
window.openModal = function() {
    document.getElementById("reviewModal").style.display = "flex";
};

function closeModal() {
    document.getElementById("reviewModal").style.display = "none";
}

window.onclick = function(event) {
    if (event.target === document.getElementById("reviewModal")) {
        closeModal();
    }
};

// 리뷰 전송
window.submitReview = function() {
    const reviewText = document.getElementById("review").value.trim();
    const rating = $('.star input').val(); // 0에서 10 사이의 값을 0.5 단위로
    const productId = document.getElementById("productId").value.trim();
    const userId = document.getElementById("userId").value.trim();
    const title = document.getElementById("title").value.trim();
    const reviewImage = document.getElementById("review-image").files[0];

    if (!reviewText) {
        alert("리뷰를 입력해주세요.");
        return;
    }

    if (isNaN(rating) || rating < 1 || rating > 5 || (rating % 0.5 !== 0)) {
        alert("별점을 1에서 5 사이의 0.5 단위로 입력해주세요.");
        return;
    }

    const formData = new FormData();
    formData.append("review", reviewText);
    formData.append("productId", productId);
    formData.append("userId", userId);
    formData.append("title", title);
    formData.append("score", rating); // 별점 추가
    if (reviewImage) {
        formData.append("reviewImage", reviewImage);
    }

    fetch('/api/review', {
        method: 'POST',
        body: formData,
    })
        .then(response => response.json())
        .then(data => {

            alert(data.message);
            closeModal();
            clearReviewForm(); // 입력란 초기화
            getReviews(currentPage);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
};

// 리뷰 삭제 함수
$(document).on('click', '.delete-button', function() {
    const reviewId = $(this).data('review-id');

    if (confirm("정말 이 리뷰를 삭제하시겠습니까?")) {
        $.ajax({
            url: '/api/review/' + reviewId,
            method: 'DELETE',
            success: function(response) {
                $(`#review-${reviewId}`).remove();
                alert("리뷰가 성공적으로 삭제되었습니다.");
            },
            error: function(xhr, status, error) {
                console.error('Error deleting review:', error);
                alert("리뷰 삭제에 실패했습니다.");
            }
        });
    }
});

// 이미지 미리보기 함수
function previewImage(event) {
    const file = event.target.files[0];
    const preview = document.getElementById("preview");
    const imagePreviewContainer = document.getElementById("image-preview");
    const removeButton = document.getElementById("remove-image-button");

    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            preview.src = e.target.result;
            imagePreviewContainer.style.display = "block";
            removeButton.style.display = "block"; // 삭제 버튼 표시
        };
        reader.readAsDataURL(file);
    }
}

// 이미지 삭제 함수
function removeImage() {
    const preview = document.getElementById("preview");
    const imagePreviewContainer = document.getElementById("image-preview");
    const removeButton = document.getElementById("remove-image-button");

    document.getElementById("review-image").value = '';
    preview.src = '';
    imagePreviewContainer.style.display = "none";
    removeButton.style.display = "none";
}

// 리뷰 입력란 초기화
function clearReviewForm() {
    document.getElementById("review").value = '';
    $('.star input').val(0);
    document.getElementById("title").value = '';
    document.getElementById("review-image").value = '';
    removeImage(); // 이미지 미리보기 초기화
}
