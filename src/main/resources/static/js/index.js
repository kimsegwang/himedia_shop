$(document).ready(() => {

    let provinces = [];
    let chosenProducts = []; // 선택한 제품을 추적하는 배열

    // JSON 파일 로드
    function loadProvinces() {
        $.ajax({
            url: '/js/regionData.json', // JSON 파일 경로
            method: 'GET',
            dataType: 'json',
            success: function (data) {
                provinces = data;
                renderProvinceList(); // 리스트 렌더링
            },
            error: function () {
                console.error('Error loading provinces JSON');
            }
        });
    }

    // province 리스트 드롭다운으로 렌더링
    function renderProvinceList() {
        const provinceSelectElement = $('#provinceSelect');
        provinceSelectElement.empty(); // 이전 내용을 지웁니다.

        provinces.forEach(item => {
            const option = $('<option></option>')
                .val(`${item.nx},${item.ny}`) // nx, ny 값을 쉼표로 구분해 value에 저장
                .text(item.province);

            if (item.province === '서울 특별시') {
                option.prop('selected', true);
            }

            provinceSelectElement.append(option);
        });

        const selectedValue = provinceSelectElement.val();
        if (selectedValue) {
            const [nx, ny] = selectedValue.split(',');
            sendDataToServer(nx, ny); // 선택된 좌표를 서버로 전송
        }
    }

    // 선택한 province의 nx, ny 값을 Controller로 전송하고 db에서 맞는 온도랑 강수값으로 상품가져와야됨
    function sendDataToServer(nx, ny) {
        $.ajax({
            url: '/weather', // Controller URL
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ nx, ny }),
            success: (result) => {
                console.log(result);
                if (result.products != null) {
                    const weatherHtml = `
                        <p>온도: ${result.temperature}</p>
                        <p>강수 종류: ${result.precipitationType}</p>
                    `;
                    const products = result.products;

                    const length = products.length <= 3 ? products.length : 3;
                    $('.image-section').empty();
                    chosenProducts = []; // 이전 선택된 제품 초기화

                    for (let i = 1; i <= length; i++) {
                        let randomIndex;
                        // 중복되지 않는 랜덤 인덱스를 찾습니다.
                        do {
                            randomIndex = Math.floor(Math.random() * products.length);
                        } while (chosenProducts.includes(randomIndex)); // 중복 검사

                        chosenProducts.push(randomIndex); // 선택된 인덱스를 추가

                        const randomProduct = products[randomIndex];
                        console.log(randomProduct)
                        const title = randomProduct.title;
                        const price = randomProduct.price;
                        const id = randomProduct.id;

                        const contentImg = randomProduct.contentImg; // 이미지 경로 (DB에서 가져온 것)

                        // HTML 요소 생성 후 삽입
                        const productHTML = `
    <div id="product${i}">
        <a href="/product/detail/${id}" class="btn btn-primary">
            <div>
                <img src="${contentImg}" alt="Random Product Image ${i}">
                <h3>${title}</h3>
                <p>${price}원</p>
            </div>
        </a>
    </div>
`;

                        $('.image-section').append(productHTML);
                    }

                    // #weather-info 요소에 날씨 정보 삽입
                    $('.weather-details').html(weatherHtml);

                    const temperature = parseFloat(result.temperature);
                    const thermometerHeight = (temperature + 40) * (200 / 80); // 예: -40도에서 +40도까지의 범위를 200px로 매핑
                    $('#thermometer-fill').css('height', `${thermometerHeight}px`);
                } else {
                    const weatherHtml = `
                        <p>온도: 데이터가 없습니다.</p>
                        <p>강수 종류: 데이터가 없습니다.</p>
                    `;
                    $('.weather-details').html(weatherHtml);
                    $('.image-section').html('<p>해당 지역에 대한 추천상품이 없습니다.</p>');
                }
            },
            error: function () {
                console.error('Error sending data to server');
            }
        });
    }

    // 드롭다운에서 선택된 항목 처리
    $('#provinceSelect').on('change', function () {
        const selectedValue = $(this).val();
        if (selectedValue) {
            const [nx, ny] = selectedValue.split(','); // 쉼표로 구분된 nx, ny 값을 가져옴
            sendDataToServer(nx, ny); // 선택된 좌표를 서버로 전송
        }
    });

    loadProvinces(); // 페이지 로드 시 JSON 파일 로드
});
