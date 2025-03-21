<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>메타몽 변신</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pokemon.css">
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
    <div id="pokemon-flavor" class="hidden">
        <h3><span id="korean-flavor-text"></span></h3>
    </div>
</div>



<!-- 버튼 -->
<button id="transform-btn">변신하기</button>
<button id="reset-btn">되돌아가기</button>

<script src="${pageContext.request.contextPath}/js/pokemon.js"></script>

</body>
</html>
