package controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dto.PokeRecordDTO;
import service.RandomPokeInfoServiceImpl;

import java.io.IOException;

@WebServlet("/random")
public class RandomPokeController extends HttpServlet {
    private static final RandomPokeInfoServiceImpl pokeInfoService = RandomPokeInfoServiceImpl.getInstance() ;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


            PokeRecordDTO pokemonDTO = pokeInfoService.getPokeInfo();

            if (pokemonDTO != null) {
                // Jackson을 사용하여 JSON 응답을 반환
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponse = objectMapper.writeValueAsString(pokemonDTO);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonResponse);
            } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }


    }
}
