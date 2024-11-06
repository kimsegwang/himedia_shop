$(document).ready(() => {
    // 사이드바 관련 코드
    function toggleNav() {
        var sidenav = document.getElementById("mySidenav");
        var menuBtn = document.getElementById("menuBtn");

        if (sidenav.style.width === "250px") {
            sidenav.style.width = "0";
            menuBtn.style.visibility = "visible"; // 삼선 버튼 보이기
        } else {
            sidenav.style.width = "250px";
            menuBtn.style.visibility = "hidden"; // 삼선 버튼 숨기기
            closeAllDropdownsInstant(); // 모든 드롭다운을 애니메이션 없이 닫기
        }
    }

    function closeNav() {
        var sidenav = document.getElementById("mySidenav");
        var menuBtn = document.getElementById("menuBtn");
        sidenav.style.width = "0";
        menuBtn.style.visibility = "visible"; // 삼선 버튼 보이기
        closeAllDropdownsInstant(); // 애니메이션 없이 모든 드롭다운 닫기
    }

    function closeNavInstant() {
        var sidenav = document.getElementById("mySidenav");
        var menuBtn = document.getElementById("menuBtn");
        sidenav.style.transition = "none"; // 애니메이션 제거
        sidenav.style.width = "0";
        menuBtn.style.visibility = "visible"; // 삼선 버튼 보이기
        closeAllDropdownsInstant(); // 모든 드롭다운 즉시 닫기
        setTimeout(function() {
            sidenav.style.transition = ""; // 애니메이션 복원
        }, 50);
    }

    // 모든 드롭다운을 애니메이션 없이 즉시 닫는 함수
    function closeAllDropdownsInstant() {
        $('.dropdown-content').css('display', 'none'); // 애니메이션 없이 닫기
        $('.dropdown-btn').removeClass('active');
    }

    // 개별 드롭다운 열기/닫기 토글
    $('.dropdown-btn').on('click', function() {
        const targetId = $(this).data('target');
        $('.dropdown-content').not($(this).next()).slideUp();
        $('.dropdown-btn').not(this).removeClass('active');
        $(this).next('.dropdown-content').slideToggle();
        $(this).toggleClass('active');
    });

    // 페이지 로드시, 사이드바 닫기
    window.addEventListener('pageshow', function() {
        closeNavInstant();
    });

    // 페이지를 떠날 때 모든 드롭다운 닫기
    window.addEventListener('beforeunload', function() {
        closeAllDropdownsInstant();
    });

    document.addEventListener('DOMContentLoaded', function() {
        closeAllDropdownsInstant();
    });

    $('#menuBtn').on('click', function() {
        toggleNav();
    });

    $('.closebtn').on('click', function() {
        closeNav();
    });

    // 검색 기능 구현
    $('.search-container button').on('click', function() {
        const keyword = $('#searchInput').val();
        if (keyword && keyword.trim()) {
            window.location.href = `/search?keyword=${encodeURIComponent(keyword)}`;
        } else {
            alert("검색어를 입력하세요.");
        }
    });
});
