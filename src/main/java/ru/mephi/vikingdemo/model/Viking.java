package ru.mephi.vikingdemo.model;

import java.util.List;
import java.util.UUID;

public record Viking(
        UUID id,
        String name,
        int age,
        int heightCm,
        HairColor hairColor,
        BeardStyle beardStyle,
        List<EquipmentItem> equipment
) {
}
