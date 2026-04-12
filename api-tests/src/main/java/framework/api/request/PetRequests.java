package framework.api.request;

import framework.api.config.Endpoints;
import framework.api.model.Pet;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;

public class PetRequests {

    public Response createPet(Pet pet) {
        return given()
                .contentType(ContentType.JSON)
                .body(pet)
            .when()
                .post(Endpoints.PET);
    }

    public Response getPetById(String petId) {
        return given()
            .when()
                .get(Endpoints.PET + "/" + petId);
    }

    public Response getPetByStatus(String status) {
        return given()
                .queryParam("status", status)
            .when()
                .get(Endpoints.PET_FIND_BY_STATUS);
    }

    public Response updatePet(Pet pet) {
        return given()
                .contentType(ContentType.JSON)
                .body(pet)
            .when()
                .put(Endpoints.PET);
    }

    public Response deletePet(long petId) {
        return given()
            .when()
                .delete(Endpoints.PET + "/" + petId);
    }

    public Response uploadPetImage(long petId, String filePath, String additionalMetadata) {
        if (additionalMetadata != null) {
            return given()
                    .multiPart("file", new File(filePath))
                    .multiPart("additionalMetadata", additionalMetadata)
                .when()
                    .post(Endpoints.PET + "/" + petId + "/uploadImage");
        }
        return given()
                .multiPart("file", new File(filePath))
            .when()
                .post(Endpoints.PET + "/" + petId + "/uploadImage");
    }
}
