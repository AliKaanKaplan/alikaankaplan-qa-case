package framework.api.datafactory;

import com.github.javafaker.Faker;
import framework.api.model.Category;
import framework.api.model.Pet;
import framework.api.model.Tag;

import java.util.Collections;

public class PetDataFactory {

    private static final Faker FAKER = new Faker();

    public static Pet createRandomPet() {
        Pet pet = new Pet();
        pet.setId(FAKER.number().numberBetween(100000, 999999));
        pet.setName(FAKER.animal().name());
        pet.setCategory(new Category(1, "Dogs"));
        pet.setPhotoUrls(Collections.singletonList("https://picsum.photos/200/300"));
        pet.setTags(Collections.singletonList(new Tag(1, "friendly")));
        pet.setStatus("available");
        return pet;
    }

    public static Pet createUpdatedPet(long existingId) {
        Pet pet = new Pet();
        pet.setId(existingId);
        pet.setName(FAKER.animal().name() + " Updated");
        pet.setCategory(new Category(1, "Dogs"));
        pet.setPhotoUrls(Collections.singletonList("https://picsum.photos/200/301"));
        pet.setTags(Collections.singletonList(new Tag(1, "updated")));
        pet.setStatus("sold");
        return pet;
    }
}
