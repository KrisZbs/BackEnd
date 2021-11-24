package MiniMarketApiTest;

import com.geekbrains.retrofit.api.MiniMarketApiService;
import com.geekbrains.retrofit.model.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MiniMarketApiTest {
    private static MiniMarketApiService apiService;
    private static Gson gson;
    private static Long id = 9L;

    @BeforeAll
    static void beforeAll() {
        apiService = new MiniMarketApiService();
        gson = new Gson();


    }

    @Test
    void testGetProductBiIdProdExict() throws IOException {
        Product product = apiService.getProduct(2);
        Assertions.assertEquals(2L, product.getId());
        Assertions.assertEquals("Bread", product.getTitle());
        Assertions.assertEquals("Food", product.getCategoryTitle());

    }

    @Test
    void testGetProductByIdNotExist() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            Product product = apiService.getProduct(200);
        });
    }

    String getJsonResource(String resource) throws IOException {
        URL url = getClass().getResource(resource);
        byte[] bytes = Files.readAllBytes(Paths.get(getClass().getResource(resource).getFile()));
        return new String(bytes, StandardCharsets.UTF_8);
    }


    void assertProduct(Product expected, Product actually) {
        Assertions.assertEquals(expected.getId(), actually.getId());
        Assertions.assertEquals(expected.getTitle(), actually.getTitle());
        Assertions.assertEquals(expected.getCategoryTitle(), actually.getCategoryTitle());
        Assertions.assertEquals(expected.getPrice(), actually.getPrice());
    }

    @Test
    @Order(1)
    public void testCreateNewProduct() throws IOException {
        Product product = Product.builder()
                .categoryTitle("Food")
                .price(300)
                .title("Fish")
                .build();
         id = apiService.createProduct(product);
        Product expected = apiService.getProduct(id);
        Assertions.assertEquals(id, expected.getId());
    }

    @Test
    @Order(2)
    void testDeleteProductById() throws IOException {
        apiService.deleteProduct(id);
        Assertions.assertThrows(RuntimeException.class, ()->
                apiService.getProduct(id));

    }



    @Test
    void TestGetProducts() throws IOException {
        Type type = new TypeToken<ArrayList<Product>>() {
        }.getType();

        String json = getJsonResource("/Tests/expected.json");
        List<Product> expected = gson.fromJson(json, type);
        List<Product> actually = apiService.getProducts();
        Assertions.assertEquals(expected.size(), actually.size());
        for (int i = 0; 0 < expected.size(); i++) {
            assertProduct(expected.get(i), actually.get(i));

        }


    }

}
