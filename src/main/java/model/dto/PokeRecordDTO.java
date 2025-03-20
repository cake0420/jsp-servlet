package model.dto;

import java.util.List;

public record PokeRecordDTO(String name,
                            List<String> types,
                            List<String> moves,
                            String imageUrl,
                            String koreanName,
                            String koreanFlavorText) {
}
