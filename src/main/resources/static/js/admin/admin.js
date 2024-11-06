let selectedFile = null; // 파일은 1개만 선택 가능

$(document).ready(() => {

    fileChaged();
    });
$(document).ready(() => {
    const subCategories = {
        '상의': ['T-Shirts', 'Shirts', 'Sweaters'],
        '하의': ['Jeans', 'Slacks', 'Shorts'],
        '아우터': ['Coats', 'Jackets', 'Hoodies'],
        '액세서리': ['Bags', 'Hats', 'Scarves']
    };

    // 카테고리 선택 시 하위 카테고리 표시
    $('#product-category').on('change', function () {
        const selectedCategory = $(this).val();
        const subCategoryContainer = $('#sub-category-container');
        const subCategoryOptions = $('#sub-category-options');

        subCategoryOptions.empty(); // 이전 하위 항목 지우기

        if (subCategories[selectedCategory]) {
            subCategoryContainer.show(); // 하위 항목 컨테이너 표시
            subCategories[selectedCategory].forEach(item => {
                const optionHTML = `
                    <div class="sub-category-item">
                        <input type="radio" name="subCategory" value="${item}" id="${item}">
                        <label for="${item}">${item}</label>
                    </div>
                `;
                subCategoryOptions.append(optionHTML);
            });
        } else {
            subCategoryContainer.hide(); // 하위 항목 컨테이너 숨기기
        }
    });

    // 등록 버튼 클릭 시
    $('#submitBtn').on('click', (event) => {
        event.preventDefault(); // 폼의 기본 제출 방지

        let formData = new FormData($('#writeForm')[0]);
        const selectedSubCategory = $('input[name="subCategory"]:checked').val(); // 하나의 선택된 서브 카테고리 가져오기

        if (selectedSubCategory && !formData.has('subCategory')) {
            formData.append('subCategory', selectedSubCategory); // 한 번만 추가하도록 수정
        }



        // Ajax 요청
        $.ajax({
            type: 'POST',
            url: '/admin/product-registration', // 서버의 엔드포인트 URL
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                alert('게시글이 성공적으로 등록되었습니다!');
                window.location.href = '/admin'; // 등록 후 페이지 리다이렉트
            },
            error: function(error) {
                console.error('오류 발생:', error);
                alert('게시글 등록 중 오류가 발생하였습니다.');
            }
        });
    });
});

let fileChaged = () => {
    // 파일 선택 시 이벤트
    $('#file').on('change', function(e) {
        const file = e.target.files[0]; // 첫 번째 파일만 선택

        selectedFile = file; // 선택된 파일을 변수에 저장
        updateFileList(); // 파일 목록 업데이트
    });
}

// 파일 목록 업데이트 함수 (파일 하나만)
let updateFileList = () => {
    $('#fileList').empty(); // 기존 목록 비우기

    if (selectedFile) {
        $('#fileList').append(`
                    <li>
                        ${selectedFile.name} <button type="button" class="remove-btn">X</button>
                    </li>
                `);

        // X 버튼 클릭 시 파일 제거
        $('.remove-btn').on('click', function () {
            selectedFile = null; // 선택된 파일 제거
            $('#file').val(''); // 파일 input 초기화
            updateFileList(); // 파일 목록 갱신
        });
    }
}


$(document).ready(function() {
    // 버튼 클릭 시 유저 잔액 가져오기
    $('#fetch-balance').click(function() {
        fetchUserBalances();
    });

    function fetchUserBalances() {
        $.ajax({
            url: '/admin/user-balance', // API 엔드포인트
            method: 'GET',
            success: function(data) {
                displayUserBalances(data); // 데이터 표시 함수 호출
            },
            error: function(xhr, status, error) {
                console.error("에러 발생:", error);
                alert("유저 잔액을 가져오는 데 실패했습니다.");
            }
        });
    }

    function displayUserBalances(balances) {
        const container = $('#user-balance-list');
        container.empty(); // 기존 내용 초기화

        if (balances.length === 0) {
            container.append('<p>잔액 정보가 없습니다.</p>');
            return;
        }

        balances.forEach(function(balance) {
            const balanceItem = `<div>
                <strong>유저 ID:</strong> ${balance.userId} <br>
                <strong>잔액:</strong> ${balance.balance}
            </div>`;
            container.append(balanceItem); // 각 유저 잔액을 컨테이너에 추가
        });
    }
});
$(document).ready(function() {
    $('#revenue-counts').on('click', function() {
        $.ajax({
            url: '/admin/revenue', // API 엔드포인트 (수익 정보를 가져오는 URL)
            method: 'GET',
            success: function(data) {
                // BalanceSumResponseDTO 데이터 처리
                $('#total-deposit').text(data.totalDeposit);
                $('#total-withdrawal').text(data.totalWithdrawal);
                $('#total-revenue').text(data.totalWithdrawalPurchased); // 총 수익에 해당하는 부분
            },
            error: function(xhr, status, error) {
                console.error("에러 발생:", error);
                alert("수익 수치를 가져오는 데 실패했습니다.");
            }
        });
    });
});