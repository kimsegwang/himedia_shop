$(document).ready(() => {
    // 사이드바 관련 코드
    function toggleNav() {
        var sidenav = document.getElementById("mySidenav");
        var menuBtn = document.getElementById("menuBtn");

        if (sidenav.style.width === "250px") {
            sidenav.style.width = "0";
            menuBtn.classList.remove("openbtn-hidden"); // 삼선 버튼 보이기
            closeAllDropdowns();
        } else {
            sidenav.style.width = "250px";
            menuBtn.classList.add("openbtn-hidden"); // 삼선 버튼 숨기기
            closeAllDropdowns();
        }
    }

    function closeNav() {
        var sidenav = document.getElementById("mySidenav");
        var menuBtn = document.getElementById("menuBtn");
        sidenav.style.width = "0";
        menuBtn.classList.remove("openbtn-hidden"); // 삼선 버튼 보이기
        closeAllDropdowns();
    }

    function closeNavInstant() {
        var sidenav = document.getElementById("mySidenav");
        var menuBtn = document.getElementById("menuBtn");
        sidenav.style.transition = "none"; // 애니메이션 제거
        sidenav.style.width = "0";
        menuBtn.classList.remove("openbtn-hidden");
        closeAllDropdowns();
        setTimeout(function() {
            sidenav.style.transition = ""; // 애니메이션 복원
        }, 50);
    }

    function closeAllDropdowns() {
        $('.dropdown-content').slideUp();
        $('.dropdown-btn').removeClass('active');
    }

    $('.dropdown-btn').on('click', function() {
        const targetId = $(this).data('target');
        const $dropdownContent = $('#' + targetId);
        $('.dropdown-content').not($(this).next()).slideUp();
        $('.dropdown-btn').not(this).removeClass('active');
        $(this).next('.dropdown-content').slideToggle();
        $(this).toggleClass('active');
    });

    window.addEventListener('pageshow', function() {
        closeNavInstant();
    });

    window.addEventListener('beforeunload', function() {
        closeAllDropdowns();
    });

    document.addEventListener('DOMContentLoaded', function() {
        closeAllDropdowns();
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
