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