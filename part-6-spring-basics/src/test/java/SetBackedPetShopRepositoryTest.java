import com.mentoring.yussuf.entity.Pet;
import com.mentoring.yussuf.repository.SetBackedPetRepository;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class SetBackedPetShopRepositoryTest {

    private final SetBackedPetRepository petRepository = new SetBackedPetRepository(new HashSet<>());

    /*/ Set is not a class, it is an interface, you can only instantiate classes
     implementing Set (HashSet, LinkedHashSet or TreeSet) /*/

    @Test
    public void shouldSavePetToRepository() {

        var fluffyCat = Pet.builder().age(10).price(10).sold(true).description("Cute cat!").name("Fluffy").gender("F").species("Cat").build();

        petRepository.save(fluffyCat);

        assertThat(petRepository.findById(fluffyCat.getId())).contains(fluffyCat);
    }


    @Test
    public void shouldFindPetGivenPetId() {

        var fluffyCat = Pet.builder().age(10).price(10).sold(true).description("Cute cat!").name("Fluffy").gender("F").species("Cat").build();
        var jakDog = Pet.builder().age(21).price(1210).sold(false).description("Cute doggo!").name("Jak").gender("M").species("Dog").build();

        petRepository.save(fluffyCat);
        petRepository.save(jakDog);

        assertThat(petRepository.findById(jakDog.getId())).contains(jakDog);
    }

    @Test
    public void shouldFilterPetsBySpecies() {

        var fluffyCat = Pet.builder().age(10).price(10).sold(false).description("Cute cat!").name("Fluffy").gender("F").species("Cat").build();
        var jakDog = Pet.builder().age(21).price(1210).sold(false).description("Cute doggo!").name("Jak").gender("M").species("Dog").build();

        petRepository.save(fluffyCat);
        petRepository.save(jakDog);

        var dogFilter = petRepository.findPetsBy("Dog", true);

        assertThat(dogFilter).containsExactly(jakDog);
    }

    @Test
    public void shouldDeletePetFromRepository() {

        var fluffyCat = Pet.builder().age(10).price(10).sold(true).description("Cute cat!").name("Fluffy").gender("F").species("Cat").build();

        petRepository.save(fluffyCat);
        petRepository.delete(fluffyCat.getId());

        assertThat(petRepository.findById(fluffyCat.getId())).isNotPresent();
    }
}
