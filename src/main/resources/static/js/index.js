$(document).ready(() => {

    let provinces = [];

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
        provinceSelectElement.append('<option value="">지역을 선택하세요</option>'); // 기본 선택 옵션 추가

        provinces.forEach(item => {
            const option = $('<option></option>')
                .val(`${item.nx},${item.ny}`) // nx, ny 값을 쉼표로 구분해 value에 저장
                .text(item.province);
            provinceSelectElement.append(option); // 드롭다운에 추가
        });
    }

    // 선택한 province의 nx, ny 값을 Controller로 전송
    function sendDataToServer(nx, ny) {
        $.ajax({
            url: '/weather', // Controller URL
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({nx, ny}),
            success: (result) => {
                const weatherHtml = `
                    <p>온도: ${result.temperature}</p>
                    <p>강수 종류: ${result.precipitationType}</p>
                `;
                const products = result.products; // response가 아닌 result에서 products를 가져옵니다.

                // 랜덤 인덱스를 생성하여 하나의 제품 선택
                // 제품 정보를 3번 반복하여 랜덤으로 선택
                const length = products.length <= 3 ? products.length : 3;
                $('.image-section').empty();


                for (let i = 1; i <= length; i++) {
                    // 랜덤 인덱스를 생성하여 하나의 제품 선택
                    const randomIndex = Math.floor(Math.random() * products.length);
                    const randomProduct = products[randomIndex];

                    // 랜덤으로 선택된 제품의 정보
                    const title = randomProduct.title;
                    const price = randomProduct.price;
                    const contentImg = randomProduct.contentImg; // 이미지 경로 (DB에서 가져온 것)

                    // HTML 요소 생성 후 삽입
                    const productHTML = `
        <div id="product${i}">
            <div>
                <img src="/${contentImg}" alt="Random Product Image ${i}">
                <h3>${title}</h3>
                <p>${price}원</p>
            </div>
        </div>
    `;


                    $('.image-section').append(productHTML);
                }


                // #weather-info 요소에 날씨 정보 삽입
                $('.weather-details').html(weatherHtml);

                const temperature = parseFloat(result.temperature);
                const thermometerHeight = (temperature + 40) * (200 / 80); // 예: -40도에서 +40도까지의 범위를 200px로 매핑
                $('#thermometer-fill').css('height', `${thermometerHeight}px`);
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
