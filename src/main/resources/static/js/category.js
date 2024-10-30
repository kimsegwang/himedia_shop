$(document).ready(function() {
    // 페이지 로드 시 사이드바와 하위 카테고리 초기화
    closeSidebarAndDropdowns();

    // 뒤로 가기를 통한 페이지 로드시에도 초기화
    window.addEventListener('pageshow', function(event) {
        if (event.persisted || window.performance && window.performance.navigation.type === 2) {
            closeSidebarAndDropdowns();
        }
    });

    // 카테고리 버튼 클릭 시 하부 항목 토글
    $('.dropdown-btn').on('click', function () {
        $('.dropdown-content').not($(this).next()).slideUp();
        $('.dropdown-btn').not(this).removeClass('active');
        $(this).next('.dropdown-content').slideToggle();
        $(this).toggleClass('active');
    });

    // 사이드바 열기/닫기
    $('#menuBtn').on('click', function () {
        $('#mySidenav').css({ 'width': '250px', 'transition': 'width 0.5s' });
        $(this).css('visibility', 'hidden'); // 삼선 메뉴 버튼 숨기기 (visibility로)
    });
    $('#closeBtn').on('click', function () {
        $('#mySidenav').css({ 'width': '0', 'transition': 'width 0.5s' });
        $('#menuBtn').css('visibility', 'visible'); // 삼선 메뉴 버튼 다시 보이기
    });

    // 검색 버튼 클릭 시 카테고리 페이지로 이동
    $('.sidenav button').on('click', function() {
        const keyword = $('.sidenav input[type="text"]').val();
        if (keyword) {
            window.location.href = `/category/search?keyword=${encodeURIComponent(keyword)}`;
        }
    });



    // 사이드바와 드롭다운을 즉시 닫는 함수
    function closeSidebarAndDropdowns() {
        $('#mySidenav').css({ 'width': '0', 'transition': 'none' }); // 애니메이션 없이 즉시 닫기
        $('.dropdown-content').hide(); // 모든 드롭다운 닫기
        $('.dropdown-btn').removeClass('active'); // 'active' 클래스 제거
        $('#menuBtn').css('visibility', 'visible'); // 삼선 메뉴 버튼 다시 보이기
    }
});
