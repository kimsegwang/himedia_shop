document.addEventListener("DOMContentLoaded", function () {
    const searchResultContainer = document.getElementById("searchResultContainer");
    const urlParams = new URLSearchParams(window.location.search);
    const keyword = urlParams.get("keyword");

    if (keyword) {
        // 검색어가 있으면 검색을 수행
        searchProducts(keyword);
    }

    function searchProducts(keyword) {
        fetch(`/api/products/search?keyword=${encodeURIComponent(keyword)}`)
            .then(response => response.json())
            .then(products => renderSearchResults(products))
            .catch(error => console.error("검색 결과를 가져오는 중 오류가 발생했습니다:", error));
    }

    function renderSearchResults(products) {
        searchResultContainer.innerHTML = "";
        if (products.length > 0) {
            products.forEach(product => {
                const productElement = document.createElement("div");
                productElement.classList.add("product-item");
                productElement.innerHTML = `
                    <img src="${product.contentImg}" alt="${product.title}">
                    <h3>${product.title}</h3>
                    <p>${product.price}원</p>
                `;
                searchResultContainer.appendChild(productElement);
            });
        } else {
            searchResultContainer.innerHTML = "<p>검색 결과가 없습니다.</p>";
        }
    }
});
