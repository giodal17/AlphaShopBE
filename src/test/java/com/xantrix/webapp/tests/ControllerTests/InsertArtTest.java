package com.xantrix.webapp.tests.ControllerTests;

import com.xantrix.webapp.Application;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.time.LocalDate;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ContextConfiguration(classes = Application.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class InsertArtTest
{
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

    @BeforeEach
    void setup() throws JSONException, IOException
	{
		mockMvc = MockMvcBuilders
				.webAppContextSetup(wac)
				.build();
	}

	private String JsonData =
			"{\r\n"
					+ "    \"codArt\": \"123Test\",\r\n"
					+ "    \"descrizione\": \"Articolo Unit Test Inserimento\",\r\n"
					+ "    \"um\": \"PZ\",\r\n"
					+ "    \"codStat\": \"TESTART\",\r\n"
					+ "    \"pzCart\": 6,\r\n"
					+ "    \"pesoNetto\": 1.75,\r\n"
					+ "    \"idStatoArt\": \"1\",\r\n"
					+ "    \"dataCreaz\": \"2023-08-22\",\r\n"
					+ "    \"barcode\": [\r\n"
					+ "        {\r\n"
					+ "            \"barcode\": \"12345678\",\r\n"
					+ "            \"idTipoArt\": \"CP\"\r\n"
					+ "        }\r\n"
					+ "    ],\r\n"
					+ "    \"ingredienti\": {\r\n"
					+ "		\"codArt\" : \"123Test\",\r\n"
					+ "		\"info\" : \"TEST INGREDIENTI\"\r\n"
					+ "	},\r\n"
					+ "    \"iva\": {\r\n"
					+ "        \"idIva\": 22\r\n"
					+ "    },\r\n"
					+ "    \"famAssort\": {\r\n"
					+ "        \"id\": 1\r\n"
					+ "    }\r\n"
					+ "}";

    @Test
    @Order(1)
    void testInsArticolo() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.post("/api/articoli/inserisci")
						.contentType(MediaType.APPLICATION_JSON)
						.content(JsonData)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andDo(print());


	}

    @Test
    @Order(2)
    void testErrInsArticolo1() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.post("/api/articoli/inserisci")
						.contentType(MediaType.APPLICATION_JSON)
						.content(JsonData)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotAcceptable())
				.andExpect(jsonPath("$.codice").value(406))
				.andExpect(jsonPath("$.messaggio").value("Articolo 123Test presente in anagrafica! Impossibile utilizzare il metodo POST"))
				.andDo(print());
	}

	String ErrJsonData =
			"{\r\n"
					+ "    \"codArt\": \"123Test\",\r\n"
					+ "    \"descrizione\": \"\",\r\n" //<-- Descrizione Assente
					+ "    \"um\": \"PZ\",\r\n"
					+ "    \"codStat\": \"TESTART\",\r\n"
					+ "    \"pzCart\": 6,\r\n"
					+ "    \"pesoNetto\": 1.75,\r\n"
					+ "    \"idStatoArt\": \"1\",\r\n"
					+ "    \"dataCreaz\": \"2019-05-14\",\r\n"
					+ "    \"barcode\": [\r\n"
					+ "        {\r\n"
					+ "            \"barcode\": \"12345678\",\r\n"
					+ "            \"idTipoArt\": \"CP\"\r\n"
					+ "        }\r\n"
					+ "    ],\r\n"
					+ "    \"ingredienti\": {\r\n"
					+ "		\"codArt\" : \"123Test\",\r\n"
					+ "		\"info\" : \"TEST INGREDIENTI\"\r\n"
					+ "	},\r\n"
					+ "    \"iva\": {\r\n"
					+ "        \"idIva\": 22\r\n"
					+ "    },\r\n"
					+ "    \"famAssort\": {\r\n"
					+ "        \"id\": 1\r\n"
					+ "    }\r\n"
					+ "}";

    @Test
    @Order(3)
    void testErrInsArticolo2() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.post("/api/articoli/inserisci")
						.contentType(MediaType.APPLICATION_JSON)
						.content(ErrJsonData)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.codice").value(400))
				.andExpect(jsonPath("$.messaggio").value("Il campo Descrizione deve avere un numero di caratteri compreso tra 6 e 80"))
				.andDo(print());
	}

	private String JsonDataMod =
			"{\r\n"
					+ "    \"codArt\": \"123Test\",\r\n"
					+ "    \"descrizione\": \"Articolo Unit Test Modifica\",\r\n"
					+ "    \"um\": \"PZ\",\r\n"
					+ "    \"codStat\": \"TESTART\",\r\n"
					+ "    \"pzCart\": 6,\r\n"
					+ "    \"pesoNetto\": 1.75,\r\n"
					+ "    \"idStatoArt\": \"1\",\r\n"
					+ "    \"dataCreaz\": \"2019-05-14\",\r\n"
					+ "    \"barcode\": [\r\n"
					+ "        {\r\n"
					+ "            \"barcode\": \"12345678\",\r\n"
					+ "            \"idTipoArt\": \"CP\"\r\n"
					+ "        }\r\n"
					+ "    ],\r\n"
					+ "    \"ingredienti\": {\r\n"
					+ "		\"codArt\" : \"123Test\",\r\n"
					+ "		\"info\" : \"TEST INGREDIENTI\"\r\n"
					+ "	},\r\n"
					+ "    \"iva\": {\r\n"
					+ "        \"idIva\": 22\r\n"
					+ "    },\r\n"
					+ "    \"famAssort\": {\r\n"
					+ "        \"id\": 1\r\n"
					+ "    }\r\n"
					+ "}";

    @Test
    @Order(4)
    void testUpdArticolo() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.put("/api/articoli/modifica")
						.contentType(MediaType.APPLICATION_JSON)
						.content(JsonDataMod)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.data").value((LocalDate.now().toString())))
				.andExpect(jsonPath("$.message").value(("Modifica Articolo 123Test eseguita con successo")))
				.andDo(print());
	}

	private String ErrJsonDataMod1 =
			"{\r\n"
					+ "    \"codArt\": \"123Test\",\r\n"
					+ "    \"descrizione\": \"\",\r\n"
					+ "    \"um\": \"PZ\",\r\n"
					+ "    \"codStat\": \"TESTART\",\r\n"
					+ "    \"pzCart\": 6,\r\n"
					+ "    \"pesoNetto\": 1.75,\r\n"
					+ "    \"idStatoArt\": \"1\",\r\n"
					+ "    \"dataCreaz\": \"2019-05-14\",\r\n"
					+ "    \"barcode\": [\r\n"
					+ "        {\r\n"
					+ "            \"barcode\": \"12345678\",\r\n"
					+ "            \"idTipoArt\": \"CP\"\r\n"
					+ "        }\r\n"
					+ "    ],\r\n"
					+ "    \"ingredienti\": {\r\n"
					+ "		\"codArt\" : \"123Test\",\r\n"
					+ "		\"info\" : \"TEST INGREDIENTI\"\r\n"
					+ "	},\r\n"
					+ "    \"iva\": {\r\n"
					+ "        \"idIva\": 22\r\n"
					+ "    },\r\n"
					+ "    \"famAssort\": {\r\n"
					+ "        \"id\": 1\r\n"
					+ "    }\r\n"
					+ "}";

    @Test
    @Order(5)
    void testErrUpdArt1() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.put("/api/articoli/modifica")
				.contentType(MediaType.APPLICATION_JSON)
				.content(ErrJsonDataMod1)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.codice").value(400))
		.andExpect(jsonPath("$.messaggio").value("Il campo Descrizione deve avere un numero di caratteri compreso tra 6 e 80"))
		.andDo(print());
	}

	private String ErrJsonDataMod2 =
			"{\r\n"
					+ "    \"codArt\": \"1234Test\",\r\n"
					+ "    \"descrizione\": \"Articolo Unit Test Modifica\",\r\n"
					+ "    \"um\": \"PZ\",\r\n"
					+ "    \"codStat\": \"TESTART\",\r\n"
					+ "    \"pzCart\": 6,\r\n"
					+ "    \"pesoNetto\": 1.75,\r\n"
					+ "    \"idStatoArt\": \"1\",\r\n"
					+ "    \"dataCreaz\": \"2019-05-14\",\r\n"
					+ "    \"barcode\": [\r\n"
					+ "        {\r\n"
					+ "            \"barcode\": \"12345678\",\r\n"
					+ "            \"idTipoArt\": \"CP\"\r\n"
					+ "        }\r\n"
					+ "    ],\r\n"
					+ "    \"ingredienti\": {\r\n"
					+ "		\"codArt\" : \"124Test\",\r\n"
					+ "		\"info\" : \"TEST INGREDIENTI\"\r\n"
					+ "	},\r\n"
					+ "    \"iva\": {\r\n"
					+ "        \"idIva\": 22\r\n"
					+ "    },\r\n"
					+ "    \"famAssort\": {\r\n"
					+ "        \"id\": 1\r\n"
					+ "    }\r\n"
					+ "}";

    @Test
    @Order(6)
    void testErrUpdArt2() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.put("/api/articoli/modifica")
						.contentType(MediaType.APPLICATION_JSON)
						.content(ErrJsonDataMod2)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.codice").value(404))
				.andExpect(jsonPath("$.messaggio").value("Articolo 1234Test non presente in anagrafica! Impossibile utilizzare il metodo PUT"))
				.andDo(print());
	}

    @Test
    @Order(7)
    void testDelArticolo() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/articoli/elimina/123Test")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.codice").value("200 OK"))
				.andExpect(jsonPath("$.messaggio").value("Eliminazione Articolo 123Test Eseguita Con Successo"))
				.andDo(print());
	}



}