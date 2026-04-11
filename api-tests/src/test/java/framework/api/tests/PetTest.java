package framework.api.tests;

import framework.api.datafactory.PetDataFactory;
import framework.api.model.Pet;
import framework.api.request.PetRequests;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class PetTest extends TestBase {

    private static final String PET_UPLOAD_FILE_PATH = "src/test/resources/dummyPhoto.jpg";

    private final PetRequests petRequests = new PetRequests();

    @Test(groups = {"positive"}, description = "POST /pet/{petId}/uploadImage - Upload a file for an existing pet and verify ApiResponse")
    public void uploadPetImage() {
        Pet pet = PetDataFactory.createRandomPet();
        petRequests.createPet(pet);
        long petId = pet.getId();

        response = petRequests.uploadPetImage(petId, PET_UPLOAD_FILE_PATH, "api-automation metadata");

        validatableResponse = response.then().statusCode(200);
        assertEquals(response.jsonPath().getInt("code"), 200);
        String message = response.jsonPath().getString("message");
        assertTrue(message.contains("File uploaded"), "Expected 'File uploaded' in message, got: " + message);
    }


    @Test(groups = {"positive"}, description = "POST /pet - Add a new pet to the store")
    public void createPet() {
        Pet pet = PetDataFactory.createRandomPet();

        response = petRequests.createPet(pet);

        validatableResponse = response.then().statusCode(200);

        assertEquals(response.jsonPath().getString("name"), pet.getName());
        assertEquals(response.jsonPath().getString("status"), "available");
    }

    @Test(groups = {"positive"}, description = "GET /pet/{petId} - Retrieve a pet by ID after creation")
    public void getPetById() {
        Pet pet = PetDataFactory.createRandomPet();
        Response createResponse = petRequests.createPet(pet);
        long petId = createResponse.jsonPath().getLong("id");

        response = petRequests.getPetById(String.valueOf(petId));

        validatableResponse = response.then().statusCode(200);
        assertEquals(response.jsonPath().getLong("id"), petId);
        assertEquals(response.jsonPath().getString("name"), pet.getName());
    }

    @Test(groups = {"positive"}, description = "PUT /pet - Update an existing pet and verify status via GET")
    public void updatePet() {
        Pet pet = PetDataFactory.createRandomPet();
        Response createResponse = petRequests.createPet(pet);
        long petId = createResponse.jsonPath().getLong("id");

        Pet updated = PetDataFactory.createUpdatedPet(petId);
        response = petRequests.updatePet(updated);

        validatableResponse = response.then().statusCode(200);
        assertEquals(response.jsonPath().getString("name"), updated.getName());
        assertEquals(response.jsonPath().getString("status"), "sold");

        Response getResponse = petRequests.getPetById(String.valueOf(petId));
        assertEquals(getResponse.jsonPath().getString("status"), "sold");
    }

    @Test(groups = {"positive"}, description = "DELETE /pet/{petId} - Delete a pet and verify GET returns 404")
    public void deletePet() {
        Pet pet = PetDataFactory.createRandomPet();
        Response createResponse = petRequests.createPet(pet);
        long petId = createResponse.jsonPath().getLong("id");

        response = petRequests.deletePet(petId);
        validatableResponse = response.then().statusCode(200);

        Response getResponse = petRequests.getPetById(String.valueOf(petId));
        assertEquals(getResponse.statusCode(), 404);
    }

    @Test(groups = {"positive"}, description = "GET /pet/findByStatus - Created pet appears when filtering by status=available")
    public void findByStatusAvailable() {
        Pet pet = PetDataFactory.createRandomPet();
        petRequests.createPet(pet);

        response = petRequests.getPetByStatus("available");
        validatableResponse = response.then().statusCode(200);

        List<Long> ids = response.jsonPath().getList("id", Long.class);
        assertTrue(ids.contains(pet.getId()), "Expected created pet id=" + pet.getId() + " in available results");
    }

    @Test(groups = {"positive"}, description = "GET /pet/findByStatus - Pet appears when filtering by status=pending after update")
    public void findByStatusPending() {
        Pet pet = PetDataFactory.createRandomPet();
        petRequests.createPet(pet);
        long petId = pet.getId();

        Pet pending = PetDataFactory.createUpdatedPet(petId);
        pending.setStatus("pending");
        pending.setName(pet.getName());
        petRequests.updatePet(pending);

        response = petRequests.getPetByStatus("pending");
        validatableResponse = response.then().statusCode(200);

        List<Long> ids = response.jsonPath().getList("id", Long.class);
        assertTrue(ids.contains(petId), "Expected pet id=" + petId + " in pending results");
    }

    @Test(groups = {"positive"}, description = "GET /pet/findByStatus - Pet appears when filtering by status=sold after update")
    public void findByStatusSold() {
        Pet pet = PetDataFactory.createRandomPet();
        petRequests.createPet(pet);
        long petId = pet.getId();

        Pet sold = PetDataFactory.createUpdatedPet(petId);
        petRequests.updatePet(sold);

        response = petRequests.getPetByStatus("sold");
        validatableResponse = response.then().statusCode(200);

        List<Long> ids = response.jsonPath().getList("id", Long.class);
        assertTrue(ids.contains(petId), "Expected pet id=" + petId + " in sold results");
    }

    @Test(groups = {"negative"}, description = "POST /pet/{petId}/uploadImage - Returns 404 for a non-existent pet ID")
    public void uploadPetImageNonExistentPet() {
        long nonExistentPetId = 9_000_000_000_000_000_001L;
        response = petRequests.uploadPetImage(nonExistentPetId, PET_UPLOAD_FILE_PATH, null);

        assertEquals(response.statusCode(), 404);
        assertEquals(response.jsonPath().getInt("code"), 404);
    }

    @Test(groups = {"negative"}, description = "GET /pet/{petId} - Returns 404 for a non-existent pet ID")
    public void getNonExistentPet() {
        response = petRequests.getPetById("999999999");

        assertEquals(response.statusCode(), 404);
    }

    @Test(groups = {"negative"}, description = "GET /pet/{petId} - Returns 400 or 404 for an invalid ID format")
    public void getWithInvalidIdFormat() {
        response = petRequests.getPetById("invalid_id");

        int statusCode = response.statusCode();
        assertTrue(statusCode == 404 || statusCode == 400, "Expected 404 or 400 for invalid id, got: " + statusCode);
    }

    @Test(groups = {"negative"}, description = "DELETE /pet/{petId} - Returns 404 when deleting a non-existent pet")
    public void deleteNonExistentPet() {
        response = petRequests.deletePet(999999999L);

        assertEquals(response.statusCode(), 404);
    }

    @Test(groups = {"negative"}, description = "GET /pet/{petId} - Returns 400 or 404 for a negative pet ID")
    public void getWithNegativeId() {
        response = petRequests.getPetById("-1");

        int statusCode = response.statusCode();
        assertTrue(statusCode == 404 || statusCode == 400, "Expected 404 or 400 for negative id, got: " + statusCode);
    }

    @Test(groups = {"negative"}, description = "GET /pet/findByStatus - Returns empty list for an invalid status value")
    public void findByInvalidStatus() {
        response = petRequests.getPetByStatus("invalid_status_value");

        validatableResponse = response.then().statusCode(200);

        assertTrue(response.jsonPath().getList("").isEmpty(), "Expected empty list for invalid status filter");
    }

}
