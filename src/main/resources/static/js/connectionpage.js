function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
    document.getElementById("main").style.marginLeft = "250px"; // 왼쪽으로 메뉴 열기
    document.getElementById("menuBtn").innerHTML = "&times;";
}

function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
    document.getElementById("main").style.marginLeft = "0"; // 왼쪽으로 메뉴 닫기
    document.getElementById("menuBtn").innerHTML = "&#9776;";
}

function toggleNav() {
    if (document.getElementById("mySidenav").style.width === "250px") {
        closeNav();
    } else {
        openNav();
    }
}

function searchFunction() {
    var input = document.getElementById("searchInput").value;
    alert("검색어: " + input);
    // 실제 검색 기능은 추가 구현이 필요합니다.
}
