$(document).ready(function() {
    // 모든 하위 카테고리 초기화: 사이드바가 열릴 때 애니메이션 없이 닫힌 상태로 유지하기
    function resetDropdowns() {
        $('.dropdown-content').hide(); // 모든 드롭다운을 애니메이션 없이 닫기
        $('.dropdown-btn').removeClass('active'); // 'active' 클래스 제거
    }

    // 페이지 로드 시 드롭다운 초기화
    resetDropdowns();

    // 사이드바 열기
    $('#menuBtn').on('click', function () {
        $('#mySidenav').css({ 'width': '250px', 'transition': 'width 0.5s' });
        $(this).css('visibility', 'hidden'); // 삼선 메뉴 버튼 숨기기
        resetDropdowns(); // 사이드바가 열릴 때마다 모든 드롭다운을 애니메이션 없이 닫음
    });

    // 사이드바 닫기
    $('.closebtn').on('click', function () {
        $('#mySidenav').css({ 'width': '0', 'transition': 'width 0.5s' });
        $('#menuBtn').css('visibility', 'visible'); // 삼선 메뉴 버튼 다시 보이기
    });

    // 카테고리 버튼 클릭 시 하위 항목 열기/닫기 (개별 애니메이션 적용)
    $('.dropdown-btn').on('click', function () {
        $(this).next('.dropdown-content').slideToggle(); // 클릭된 항목만 토글
        $(this).toggleClass('active');
    });

    // 검색 기능
    $('.search-container button').on('click', function() {
        const keyword = $('#searchInput').val().trim();
        if (keyword) {
            window.location.href = `/search?keyword=${encodeURIComponent(keyword)}`;
        }
    });
});
