$(document).ready(() => {
    function openModal() {
        document.getElementById("reviewModal").style.display = "block";
    }

    function closeModal() {
        document.getElementById("reviewModal").style.display = "none";
    }

// 클릭 외부를 클릭 시 모달 닫기
    window.onclick = function(event) {
        if (event.target == document.getElementById("reviewModal")) {
            closeModal();
        }
    }
    function submitReview() {
        const reviewText = document.getElementById("review").value;

        // 유효성 검사
        if (!reviewText) {
            alert("리뷰를 입력해주세요.");
            return;
        }

        if (isNaN(rating) || rating < 1 || rating > 5 || (rating % 0.5 !== 0)) {
            alert("별점을 1에서 5 사이의 0.5 단위로 입력해주세요.");
            return;
        }

        fetch('/submitReview', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                review: reviewText,
                productId: productId,
                rating: rating
            }),
        })
            .then(response => response.json())
            .then(data => {
                document.getElementById("responseMessage").innerText = data.message;
                closeModal(); // 모달 닫기
                document.getElementById("review").value = ''; // 입력란 초기화
                document.getElementById("rating").value = '';
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    }
});