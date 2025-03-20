package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.dto.PokeRecordDTO;
import util.ApiUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

public class RandomPokeInfoServiceImpl extends ApiUtils implements PokeInfoService {

    public PokeRecordDTO getPokeInfo() {

        try {

            // API 호출을 통해 포켓몬 정보 가져오기
            String pokeName = getRandomPokeName();

            Optional<String> pokemonData = getApiResponse(dotenv.get("POKE_API_URL") + pokeName, "GET", null);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode pokemonNode = objectMapper.readTree(pokemonData.orElse(null));

            PokeRecordDTO pokeRecordDTO = new PokeRecordDTO(
                                                        pokeName,
                                                        extractTypes(pokemonNode),
                                                        extractMoves(pokemonNode),
                                                        extractImageUrl(pokemonNode),
                                                        extractKoreanName(pokemonNode),
                                                        extractKoreanFlavorText(pokemonNode)
                                                        );
            logger.log(Level.INFO, pokeRecordDTO.toString());

            return pokeRecordDTO;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "포켓몬 Api에 문제가 발생했습니다.: ", e);
            return null;
        }
    }


    private List<String> extractTypes(JsonNode pokemonNode) {
        List<String> types = new ArrayList<>();
        JsonNode typesNode = pokemonNode.get("types");
        if (typesNode != null) {
            for(JsonNode type : typesNode){
                types.add(type.get("type").get("name").asText());
            }
        }
        return types;
    }

    private List<String> extractMoves(JsonNode pokemonNode) {
        List<String> moves = new ArrayList<>();
        JsonNode movesNode = pokemonNode.get("moves");
        if (movesNode != null) {
            for(JsonNode move : movesNode){
                moves.add(move.get("move").get("name").asText());
            }
        }
        return moves;
    }

    private String extractImageUrl(JsonNode pokemonNode) {
        JsonNode spritesNode = pokemonNode.get("sprites");
        if (spritesNode != null) {
            return spritesNode.get("front_default").asText();
        }
        return null;
    }

    private String extractKoreanName(JsonNode pokemonNode) {
        JsonNode speciesNode = pokemonNode.get("species");
        if (speciesNode != null) {
            String speciesUrl = speciesNode.get("url").asText();
            try {
                Optional<String> speciesData = getApiResponse(speciesUrl, "GET", null);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode speciesInfo = objectMapper.readTree(speciesData.orElse(null));

                JsonNode namesNode = speciesInfo.get("names");
                if (namesNode != null) {
                    for (JsonNode name : namesNode) {
                        if ("ko".equals(name.get("language").get("name").asText())) {
                            return name.get("name").asText();
                        }
                    }
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, "한국어 이름을 가져오는 데 문제가 발생했습니다.", e);
            }
        }
        return null;
    }

    private String extractKoreanFlavorText(JsonNode pokemonNode) {
        JsonNode speciesNode = pokemonNode.get("species");
        if (speciesNode != null) {
            String speciesUrl = speciesNode.get("url").asText();
            try {
                Optional<String> speciesData = getApiResponse(speciesUrl, "GET", null);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode speciesInfo = objectMapper.readTree(speciesData.orElse(null));

                JsonNode flavorTextEntriesNode = speciesInfo.get("flavor_text_entries");
                if (flavorTextEntriesNode != null) {
                    for (JsonNode flavorTextEntry : flavorTextEntriesNode) {
                        if ("ko".equals(flavorTextEntry.get("language").get("name").asText())) {
                            return flavorTextEntry.get("flavor_text").asText();
                        }
                    }
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, "한국어 설명을 가져오는 데 문제가 발생했습니다.", e);
            }
        }
        return "";
    }



    private String getRandomPokeName() throws JsonProcessingException {
        Optional<String> allPokemonData = getApiResponse(dotenv.get("POKE_API_URL"), "GET", null);

        if (allPokemonData.isEmpty()) {
            throw new IllegalStateException("포켓몬 데이터를 가져오는 데 실패했습니다.");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode results = objectMapper.readTree(allPokemonData.get()).get("results");

        if (results.isEmpty()) {
            throw new IllegalStateException("포켓몬 목록이 비어 있습니다.");
        }

        int randomIndex = (int) (Math.random() * results.size());
        return results.get(randomIndex).get("name").asText();
    }

}
