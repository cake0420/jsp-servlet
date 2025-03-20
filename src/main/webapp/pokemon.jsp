<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>메타몽 변신</title>
    <link rel="stylesheet" type="text/css" href="pokemon.css">
</head>
<body>
<h1>메타몽 포켓몬 도감</h1>

<!-- 기본적으로 메타몽이 보이도록 설정 (hidden 제거) -->
<div id="basic-views">
    <h2>메타몽</h2>
    <img id = "basic-image" src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png" alt="포켓몬 이미지" width="200">
</div>

<!-- 포켓몬 정보와 이미지를 표시할 영역 -->
<!-- HTML: hidden 속성 대신 class 사용 -->

<div id="pokemon-container">
    <div id="pokemon-info" class="hidden">
        <h2>변신된 포켓몬: <span id="pokemon-name"></span></h2>
        <img id="pokemon-image" src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png" alt="포켓몬 이미지" width="200">
    </div>
</div>

<div id="pokemon-flavor" class="hidden">
    <h3><span id="korean-flavor-text"></span></h3>
</div>

<!-- 버튼 -->
<button id="transform-btn">변신하기</button>
<button id="reset-btn">되돌아가기</button>

<script>

    async function fetchRandomPokemonData() {
        try {
            const response = await fetch('/random'); // DittoController 서블릿의 URL
            const data = await response.json(); // JSON 형식으로 응답받기
            return data; // 받아온 데이터 반환
        } catch (error) {
            console.error('Error fetching data:', error);
            return null; // 에러 발생 시 null 반환
        }
    }

    // 변신하기 버튼 클릭 시
    document.getElementById('transform-btn').addEventListener('click', async function() {
        document.getElementById("basic-views").classList.add("hidden"); // 기존 화면 숨기기
        document.getElementById("pokemon-info").classList.remove("hidden"); // 보이게 하기
        document.getElementById("pokemon-flavor").classList.remove("hidden");

        const data = await fetchRandomPokemonData(); // 랜덤 포켓몬 데이터 가져오기
        if (data) {
            document.getElementById('pokemon-name').textContent = data.koreanName; // 포켓몬 이름 표시
            document.getElementById('pokemon-image').src = data.imageUrl; // 포켓몬 이미지 변경

            let replaceString = data.koreanFlavorText.replace(/\n/g, '<br>');
            document.getElementById('korean-flavor-text').innerHTML = replaceString;

        } else {
            alert('포켓몬 정보를 가져오는데 실패했습니다.');
        }
    });

    document.getElementById('reset-btn').addEventListener('click', async function () {
        document.getElementById("basic-views").classList.remove("hidden"); // 기존 화면 숨기기
        document.getElementById("pokemon-info").classList.add("hidden"); // 보이게 하기
        document.getElementById("pokemon-flavor").classList.add("hidden");

        // 메타몽 정보 다시 불러오기
    });

</script>
</body>
</html>
