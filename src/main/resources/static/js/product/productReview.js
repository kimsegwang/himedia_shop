$(document).ready(() => {
    getReviews(); // 페이지 로드 시 리뷰 가져오기
    getRecommendProduct(); // 페이지 로드시 추천 제품 가져오기
    document.getElementById("review-image").addEventListener("change", previewImage);
});

// 리뷰 가져오기 함수
function getReviews() {
    const productId = document.getElementById("productId").value;
    const userId = document.getElementById("userId").value;

    $.ajax({
        url: '/api/review?productId=' + productId, // AJAX 요청 URL
        method: 'GET',
        success: function(data) {
            console.log(data.length);
            $('#reviews').empty(); // 기존 목록 비우기
            data.forEach(review => {
                console.log(review.id);
                const deleteButton = (review.userId === userId)
                    ? `<button class="delete-button" data-review-id="${review.id}">삭제</button>`
                    : '';
                const reviewImg = review.reviewImg
                    ? `<strong><img style="width: 150px;height: 150px" src="/${review.reviewImg}" alt="Review Image"  /></strong>`
                    : '';

                $('#reviews').append(
                    `<li id="review-${review.id}">
                        ${reviewImg}<br>
                        <strong>제목:</strong> ${review.title} <br>
                        <strong>내용:</strong> ${review.review} <br>
                         ${generateStars(review.score)} 
                        ${deleteButton}
                        
                    </li>`
                );
            });
        },
        error: function(xhr, status, error) {
            console.error('Error fetching reviews:', error);
        }
    });
}
function generateStars(score) {
    const fullStars = Math.floor(score);
    const halfStar = score % 1 ? '<i class="fa fa-star-half-o filled get-star"></i>' : '';
    return `<div class="star-container">${'<i class="fa fa-star filled get-star"></i>'.repeat(fullStars)}${halfStar}${'<i class="fa fa-star get-star"></i>'.repeat(5 - fullStars - (halfStar ? 1 : 0))}</div>`;
}

// 모달 열기
window.openModal = function() {
    document.getElementById("reviewModal").style.display = "flex"; // 모달을 보여줍니다.
};

// 모달 닫기
function closeModal() {
    document.getElementById("reviewModal").style.display = "none";
}

// 모달 외부 클릭 시 닫기
window.onclick = function(event) {
    if (event.target === document.getElementById("reviewModal")) {
        closeModal();
    }
};

$(document).ready(function() {
    $('.star input').on('input', function() {
        const value = $(this).val();
        $('.star span').css('width', `${value * 10}%`);
    });
});

//리뷰 전송
window.submitReview = function() {
    const reviewText = document.getElementById("review").value.trim();
    const rating = $('.star input').val().trim()/2;
    const productId = document.getElementById("productId").value.trim();
    const userId = document.getElementById("userId").value.trim();
    const title = document.getElementById("title").value.trim();
    const reviewImage = document.getElementById("review-image").files[0];

    // 리뷰 입력 확인
    if (!reviewText) {
        alert("리뷰를 입력해주세요.");
        return;
    }

    // 별점 입력 확인
    if (isNaN(rating) || rating < 1 || rating > 5 || (rating % 0.5 !== 0)) {
        alert("별점을 1에서 5 사이의 0.5 단위로 입력해주세요.");
        return;
    }

    const formData = new FormData();

    // 입력값을 FormData에 추가
    formData.append("review", reviewText);
    formData.append("productId", productId);
    formData.append("userId", userId);
    formData.append("title", title);
    formData.append("rating", rating);

    if (reviewImage) {
        formData.append("reviewImage", reviewImage); // 이미지 추가
    }

    // 리뷰 제출 AJAX 요청
    fetch('/api/review', {
        method: 'POST',
        body: formData,
    })
    .then(response => response.json())
    .then(data => {
        alert(data.message);
        closeModal(); // 모달 닫기
        // 입력란 초기화
        document.getElementById("review").value = '';
        $('.star input').val(0);
        document.getElementById("title").value = '';
        document.getElementById("review-image").value = ''; // 이미지 입력 초기화
        getReviews(); // 리뷰 목록 새로고침
    })
    .catch((error) => {
        console.error('Error:', error);
    });
};

// 리뷰 삭제 함수
$(document).on('click', '.delete-button', function() {
    const reviewId = $(this).data('review-id');

    if (confirm("정말 이 리뷰를 삭제하시겠습니까?")) { // 삭제 확인 팝업
        $.ajax({
            url: '/api/review/' + reviewId,
            method: 'DELETE',
            success: function(response) {
                // 성공적으로 삭제된 후 해당 리뷰 항목 제거
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

        document.getElementById("review-image").value = ''; // 파일 입력 초기화
        preview.src = ''; // 미리보기 이미지 초기화
        imagePreviewContainer.style.display = "none"; // 이미지 미리보기 숨기기
        removeButton.style.display = "none"; // 삭제 버튼 숨기기
    }
