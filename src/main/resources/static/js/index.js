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
            url: '/weather',
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

                    $('.image-section').empty();
                    chosenProducts = []; // 이전 선택된 제품 초기화

                    // 랜덤하게 3개의 제품 선택
                    const length = products.length <= 9 ? products.length : 9;
                    const selectedProducts = [];

                    for (let i = 0; i < length; i++) {
                        let randomIndex;
                        do {
                            randomIndex = Math.floor(Math.random() * products.length);
                        } while (chosenProducts.includes(randomIndex));

                        chosenProducts.push(randomIndex);
                        selectedProducts.push(products[randomIndex]);
                    }

                    // 슬라이더 컨테이너 생성
                    const sliderContainer = $('<div class="slider-container"></div>');
                    const productSlider = $('<div class="product-slider"></div>');

                    // 네비게이션 버튼 추가
                    const prevButton = $('<button class="slider-nav prev">&lt;</button>');
                    const nextButton = $('<button class="slider-nav next">&gt;</button>');

                    // 선택된 랜덤 제품들로 슬라이드 생성
                    selectedProducts.forEach((product, index) => {
                        const productHTML = `
                            <div class="product-slide">
                                <a href="/product/detail/${product.id}" class="btn btn-primary">
                                    <div>
                                        <img src="${product.contentImg}" alt="Product Image ${index + 1}">
                                        <h3>${product.title}</h3>
                                        <p>${product.price}원</p>
                                    </div>
                                </a>
                            </div>
                        `;
                        productSlider.append(productHTML);
                    });

                    // 무한 슬라이드를 위해 처음과 끝에 복제된 슬라이드 추가
                    const firstSlideClone = productSlider.children().first().clone();
                    const lastSlideClone = productSlider.children().last().clone();
                    productSlider.append(firstSlideClone);
                    productSlider.prepend(lastSlideClone);

                    sliderContainer.append(prevButton);
                    sliderContainer.append(productSlider);
                    sliderContainer.append(nextButton);
                    $('.image-section').append(sliderContainer);

                    // 슬라이더 초기화
                    let currentSlide = 1;
                    const slideCount = productSlider.children().length;
                    let sliding = false;

                    function updateSlider() {
                        const slideWidth = $('.product-slide').outerWidth(true);
                        productSlider.css('transform', `translateX(-${currentSlide * slideWidth}px)`);
                    }

                    function slideNext() {
                        if (sliding) return;
                        sliding = true;
                        currentSlide++;
                        productSlider.css('transition', 'transform 0.5s ease-in-out');
                        updateSlider();

                        if (currentSlide === slideCount - 1) {
                            setTimeout(() => {
                                productSlider.css('transition', 'none');
                                currentSlide = 1;
                                updateSlider();
                                sliding = false;
                            }, 500);
                        } else {
                            setTimeout(() => {
                                sliding = false;
                            }, 500);
                        }
                    }

                    function slidePrev() {
                        if (sliding) return;
                        sliding = true;
                        currentSlide--;
                        productSlider.css('transition', 'transform 0.5s ease-in-out');
                        updateSlider();

                        if (currentSlide === 0) {
                            setTimeout(() => {
                                productSlider.css('transition', 'none');
                                currentSlide = slideCount - 2;
                                updateSlider();
                                sliding = false;
                            }, 500);
                        } else {
                            setTimeout(() => {
                                sliding = false;
                            }, 500);
                        }
                    }

                    // 이벤트 리스너 설정
                    nextButton.on('click', slideNext);
                    prevButton.on('click', slidePrev);

                    // 자동 슬라이드 설정
                    let autoSlideInterval = setInterval(slideNext, 3000);

                    // 마우스가 슬라이더 위에 있을 때 자동 슬라이드 멈춤
                    sliderContainer.on('mouseenter', () => {
                        clearInterval(autoSlideInterval);
                    });

                    // 마우스가 슬라이더를 벗어날 때 자동 슬라이드 재시작
                    sliderContainer.on('mouseleave', () => {
                        autoSlideInterval = setInterval(slideNext, 3000);
                    });

                    // 초기 위치 설정
                    function updateSlider() {
                        const slideWidth = $('.product-slide').outerWidth(true);
                        const offset = currentSlide * slideWidth;
                        productSlider.css('transform', `translateX(${-offset}px)`);
                    }

// 슬라이더 초기화 시에 추가
                    $(window).on('resize', function() {
                        updateSlider();
                    });

                    // 날씨 정보 업데이트
                    $('.weather-details').html(weatherHtml);
                    const temperature = parseFloat(result.temperature);
                    const thermometerHeight = (temperature + 40) * (200 / 80);
                    $('#thermometer-fill').css('height', `${thermometerHeight}px`);
                } else {
                    $('.weather-details').html('<p>데이터가 없습니다.</p>');
                    $('.image-section').html('<p>해당 지역에 대한 추천상품이 없습니다.</p>');
                }
            },
            error: function() {
                console.error('Error sending data to server');
            }
        });
    }

    $('#provinceSelect').on('change', function() {
        const selectedValue = $(this).val();
        if (selectedValue) {
            const [nx, ny] = selectedValue.split(',');
            sendDataToServer(nx, ny);
        }
    });

    loadProvinces();
});