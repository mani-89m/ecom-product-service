package com.productservice.integration.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.dynamodb.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@Import(LocalStackConfiguration.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ProductServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateAndGetProducts() throws Exception {
	Product p = new Product();
	p.setName("Test");
	p.setProductId("UUID");
	p.setDescription("Test Desc");

	mockMvc.perform(MockMvcRequestBuilders.post("/product/create").content(asJsonString(p))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.productId").exists());

	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/products").accept(MediaType.APPLICATION_JSON))
		.andDo(MockMvcResultHandlers.print()).andReturn();

	String responseContent = result.getResponse().getContentAsString();
	List<Product> retrievedProducts = asProductObjects(responseContent);
	assertNotNull(retrievedProducts);
	assertEquals(1, retrievedProducts.size());
	assertEquals(p.getProductId(), retrievedProducts.get(0).getProductId());

    }

    private String asJsonString(final Object obj) {
	try {
	    return new ObjectMapper().writeValueAsString(obj);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    private List<Product> asProductObjects(final String content) {
	try {
	    List<Product> productList = new ObjectMapper().readValue(content, new TypeReference<List<Product>>() {
	    });
	    return productList;
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

}
