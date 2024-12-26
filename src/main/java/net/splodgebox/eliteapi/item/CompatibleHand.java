package net.splodgebox.eliteapi.item;

import org.bukkit.inventory.EquipmentSlot;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Enum representing the different hands that can be used in an operation or event,
 * such as the main hand or the off hand.
 */
public enum CompatibleHand {
    MAIN_HAND,
    OFF_HAND;

    private static final Map<String, Method> methodCache = new HashMap<>();

    /**
     * Retrieves the hand (main hand or off hand) associated with the given event.
     *
     * @param event The event object from which to determine the hand.
     * @return A {@code CompatibleHand} representing the hand associated with the event object.
     * Defaults to {@code MAIN_HAND} if the method invocation fails or if the hand is not {@code OFF_HAND}.
     */
    public static CompatibleHand getHand(Object event) {
        try {
            Method getHandMethod = getCachedMethod(event.getClass(), "getHand");
            EquipmentSlot equipmentSlot = (EquipmentSlot) getHandMethod.invoke(event);
            return fromSlot(equipmentSlot);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // Return MAIN_HAND by default for errors or missing methods
            return MAIN_HAND;
        }
    }

    /**
     * Determines the appropriate CompatibleHand based on the given equipment slot.
     *
     * @param equipmentSlot The equipment slot to evaluate.
     * @return CompatibleHand corresponding to the equipment slot.
     * Returns MAIN_HAND if the slot is EquipmentSlot.HAND, otherwise OFF_HAND.
     */
    public static CompatibleHand getHand(EquipmentSlot equipmentSlot) {
        return fromSlot(equipmentSlot);
    }

    /**
     * Retrieves and caches a method using the class name as a key.
     *
     * @param clazz      The class to search for the method.
     * @param methodName The name of the method to retrieve.
     * @return The retrieved Method instance.
     * @throws NoSuchMethodException If the method does not exist.
     */
    private static Method getCachedMethod(Class<?> clazz, String methodName) throws NoSuchMethodException {
        String className = clazz.getName();
        return methodCache.computeIfAbsent(className, key -> {
            try {
                return clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Maps an EquipmentSlot to a CompatibleHand.
     *
     * @param equipmentSlot The equipment slot to map.
     * @return Corresponding CompatibleHand. Defaults to MAIN_HAND for HAND, OFF_HAND otherwise.
     */
    private static CompatibleHand fromSlot(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.HAND ? MAIN_HAND : OFF_HAND;
    }
}