// JavaScript to toggle dropdowns
$(document).ready(function() {
    // 초기 상태에서 모든 하부 항목을 숨기기
    $('.dropdown-content').hide();

    // 카테고리 버튼 클릭 시 하부 항목 토글
    $('.dropdown-btn').on('click', function() {
        const targetId = $(this).data('target'); // 클릭된 버튼의 타겟 ID
        const $dropdownContent = $('#' + targetId); // 해당되는 드롭다운 콘텐츠

        // 현재 클릭된 버튼 이외의 모든 하부 항목을 닫기
        $('.dropdown-content').not($(this).next()).slideUp();
        $('.dropdown-btn').not(this).removeClass('active');

        // 현재 클릭된 항목 열기/닫기
        $(this).next('.dropdown-content').slideToggle();
        $(this).toggleClass('active');
    });

    $('.subcategory-item').on('click', function() {
        // const category = $(this).data('category'); // data-category 속성에서 카테고리 ID 가져오기
        // const subCategory = $(this).data('subcategory'); // data-subcategory 속성에서 하위 카테고리 ID 가져오기

        if (category && subCategory) {
            // URL 형식에 맞게 페이지로 이동
            window.location.href = `/category/${category}/${subCategory}`;
        }
    });

    // 페이지가 로드되거나 다시 보여질 때 모든 드롭다운 닫기
    window.addEventListener('pageshow', function() {
        closeNavInstant(); // 즉시 사이드바 닫기
    });


    // 페이지를 떠날 때 모든 드롭다운 닫기
    window.addEventListener('beforeunload', function() {
        closeAllDropdowns();
    });

    // 페이지가 처음 로드될 때 모든 드롭다운 닫기
    document.addEventListener('DOMContentLoaded', function() {
        closeAllDropdowns();
    });
});

function toggleNav() {
    var sidenav = document.getElementById("mySidenav");
    var menuBtn = document.getElementById("menuBtn");

    if (sidenav.style.width === "250px") {
        sidenav.style.width = "0";
        menuBtn.classList.remove("openbtn-hidden"); // 삼선 버튼 보이기
        // 사이드바를 닫을 때 모든 카테고리 항목 닫기
        closeAllDropdowns();

    } else {
        sidenav.style.width = "250px";
        menuBtn.classList.add("openbtn-hidden"); // 삼선 버튼 숨기기
        // 사이드바를 열 때 모든 카테고리 항목 닫기
        closeAllDropdowns();
    }
}

function closeNav() {
    var sidenav = document.getElementById("mySidenav");
    var menuBtn = document.getElementById("menuBtn");
    sidenav.style.width = "0";
    menuBtn.classList.remove("openbtn-hidden"); // 삼선 버튼 보이기

    // 사이드바를 닫을 때 모든 카테고리 항목 닫기
    closeAllDropdowns();
}

// 애니메이션 없이 즉시 사이드바 닫기
function closeNavInstant() {
    var sidenav = document.getElementById("mySidenav");
    var menuBtn = document.getElementById("menuBtn");
    sidenav.style.transition = "none"; // 애니메이션 제거
    sidenav.style.width = "0";
    menuBtn.classList.remove("openbtn-hidden");

    // 모든 카테고리 닫기
    closeAllDropdowns();

    // 애니메이션 설정 복구
    setTimeout(function() {
        sidenav.style.transition = ""; // 애니메이션 복원
    }, 50);
}

// 모든 드롭다운 닫기
function closeAllDropdowns() {
    $('.dropdown-content').slideUp();
    $('.dropdown-btn').removeClass('active');
}

$(document).ready(function() {
    // 이미지가 로드될 때, 크기를 동일하게 맞춤
    $(".product-slider .slide img").each(function() {
        const maxHeight = 200; // 고정하고자 하는 높이
        const maxWidth = 150; // 고정하고자 하는 너비
        $(this).css({
            "width": maxWidth + "px",
            "height": maxHeight + "px",
            "object-fit": "cover"
        });
    });

    let currentSlideIndex = 0;
    const slidesToShow = 3; // 화면에 표시할 슬라이드 수
    const slideWidth = $(".product-slider .slide").outerWidth(true); // 각 슬라이드의 너비 (간격 포함)
    const slideCount = $(".product-slider .slide").length;
    const slideInterval = 3000; // 3초마다 전환

    // 슬라이더 너비 설정 (한 번에 3개씩 보이도록)
    $(".product-slider-container").css("width", slideWidth * slidesToShow);

    function nextSlide() {
        currentSlideIndex++;
        if (currentSlideIndex > slideCount - slidesToShow) {
            // 마지막 슬라이드에 도달하면 다시 처음으로
            currentSlideIndex = 0;
            $(".product-slider").css("transition", "none"); // 애니메이션 제거
            $(".product-slider").css("transform", `translateX(0px)`);
            setTimeout(() => {
                $(".product-slider").css("transition", "transform 1s ease"); // 애니메이션 복원
            }, 50);
        } else {
            $(".product-slider").css("transform", `translateX(-${currentSlideIndex * slideWidth}px)`);
        }
    }

    // 일정 시간마다 슬라이드 전환
    setInterval(nextSlide, slideInterval);

    $(document).ready(function() {
        // 메인 페이지 또는 카테고리 페이지의 검색 버튼 클릭 이벤트
        $('.search-container button').on('click', function() {
            const keyword = $('.search-container input[type="text"]').val().trim();
            if (keyword) {
                // 검색 페이지로 이동하고 URL에 검색어 전달
                window.location.href = `/search?keyword=${encodeURIComponent(keyword)}`;
            }
        });
    });

});
