$(document).ready(() => {
    getReviews(); // 페이지 로드 시 리뷰 가져오기
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
            $('#reviews').empty(); // 기존 목록 비우기
            data.forEach(review => {
                const deleteButton = (review.userId === userId)
                    ? `<button class="delete-button" data-review-id="${review.id}">삭제</button>`
                    : '';
                const reviewImg = review.reviewImage
                    ? `img/${review.reviewImage}`
                    : '';
                $('#reviews').append(
                    `<li id="review-${review.id}">
                        <img src="${reviewImg}" alt="Review Image" />
                        ${deleteButton}
                        <strong>제목:</strong> ${review.title} <br>
                        <strong>내용:</strong> ${review.review} <br>
                        <input type="number" class="rating" step="0.5" min="1" max="5" value="${review.score}" readonly />
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

// 모달 열기
window.openModal = function() {
    document.getElementById("reviewModal").style.display = "block"; // 모달을 보여줍니다.
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

// 리뷰 제출 함수
window.submitReview = function() {
        const reviewText = document.getElementById("review").value.trim();
        const rating = parseFloat(document.getElementById("rating").value.trim());
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
        if (!reviewText) {
            alert("리뷰를 입력해주세요.");
            return;
        }
        const formData = new FormData();

    // 입력값을 FormData에 추가
        for (const [key, value] of Object.entries(reviewData)) {
            if (value) { // 값이 존재할 때만 추가
                formData.append(key, value);
            }
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
            document.getElementById("rating").value = '';
            document.getElementById("title").value = '';
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
