package ru.mephi.vikingdemo.service;

import net.datafaker.Faker;
import org.springframework.stereotype.Component;
import ru.mephi.vikingdemo.model.BeardStyle;
import ru.mephi.vikingdemo.model.HairColor;
import ru.mephi.vikingdemo.model.Viking;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

@Component
public class VikingFactory {

    private final Faker faker = new Faker(new Locale("en"));
    private final Random random = new Random();

    public Viking createRandomViking() {
        return new Viking(
                UUID.randomUUID(),
                faker.name().firstName(),
                18 + random.nextInt(43),
                160 + random.nextInt(41),
                HairColor.values()[random.nextInt(HairColor.values().length)],
                BeardStyle.values()[random.nextInt(BeardStyle.values().length)],
                createRandomEquipment()
        );
    }

    private List<ru.mephi.vikingdemo.model.EquipmentItem> createRandomEquipment() {
        return List.of(
                EquipmentFactory.createItem(),
                EquipmentFactory.createItem()
        );
    }
}