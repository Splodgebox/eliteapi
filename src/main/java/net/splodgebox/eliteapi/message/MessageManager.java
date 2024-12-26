package net.splodgebox.eliteapi.message;

import net.splodgebox.eliteapi.util.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Manages message data and their associations with annotated fields within the application.
 * This class utilizes a configuration file to store and retrieve localized messages,
 * ensuring synchronization between file-based data and annotated fields at runtime.
 */
public class MessageManager {

    /**
     * Represents a {@link FileManager} instance used for managing and interacting
     * with configuration files. This variable is responsible for handling file
     * operations such as saving, reloading, and updating the configuration data
     * used to manage language and message data within the application.
     * <p>
     * The {@code fileManager} is instantiated during the construction of the
     * {@code MessageManager} class and specifically manages a file named "lang.yml".
     * Its primary function is to facilitate the storage and retrieval of localized
     * messages, ensuring consistency between file-based data and runtime operations.
     * <p>
     * This variable also plays a critical role in processing annotations, such
     * as {@code @Message}, to dynamically feed data into target fields from the
     * configuration file and ensure synchronization of message definitions.
     */
    private final FileManager fileManager;

    /**
     * Constructs a new MessageManager instance.
     * <p>
     * This initializes the internal FileManager to handle language configuration
     * using a file named "lang.yml" located in the plugin's data folder.
     *
     * @param plugin The JavaPlugin instance that this MessageManager is associated with.
     */
    public MessageManager(JavaPlugin plugin) {
        this.fileManager = new FileManager(plugin, "lang.yml", plugin.getDataFolder().getAbsolutePath());
    }

    /**
     * Loads message data from the provided classes or instances. This method processes
     * both static and non-static fields annotated with the {@code @Message} annotation.
     * Static fields are processed from the provided class type, while non-static fields
     * are processed from a provided instance.
     * After processing fields, the configuration file is saved and reloaded.
     *
     * @param messageClassInstances One or more objects or class types to retrieve
     *                              and process {@code @Message}-annotated fields.
     *                              Class types must be {@code Class<?>}, and instances
     *                              must be of some type containing fields annotated
     *                              with {@code @Message}.
     */
    public void loadMessages(Object... messageClassInstances) {
        for (Object instanceOrClass : messageClassInstances) {
            if (instanceOrClass instanceof Class<?>) {
                Class<?> clazz = (Class<?>) instanceOrClass;
                processStaticFields(clazz);
            } else {
                Class<?> clazz = instanceOrClass.getClass();
                processStaticFields(clazz);
                processFields(clazz, instanceOrClass);
            }
        }

        fileManager.save();
        fileManager.reload();
    }

    /**
     * Processes all static fields in the specified class and its superclasses, identifying fields
     * annotated with {@code @Message}. If a static field is found to be annotated, it delegates
     * the processing of that field to the {@code handleField} method.
     *
     * @param clazz the class to inspect for annotated static fields
     */
    private void processStaticFields(Class<?> clazz) {
        while (clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Message.class) && Modifier.isStatic(field.getModifiers())) {
                    handleField(field, null);
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    /**
     * Processes all non-static fields in a given class and its superclasses
     * that are annotated with the {@link Message} annotation.
     * The method checks each field for the annotation and handles it accordingly
     * by delegating to the {@code handleField} method.
     *
     * @param clazz    The class whose non-static fields are being processed.
     *                 Can be the object's class or any superclass in the hierarchy.
     * @param instance The instance of the class whose fields are being processed.
     *                 This is used to set the values of non-static fields.
     */
    private void processFields(Class<?> clazz, Object instance) {
        while (clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Message.class) && !Modifier.isStatic(field.getModifiers())) {
                    handleField(field, instance);
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    /**
     * Handles a field annotated with {@link Message}, retrieves the associated message
     * from the configuration, and assigns it to the field in the given instance.
     *
     * @param field    the field to handle, annotated with {@link Message}
     * @param instance the instance object where the field is set, or null if the field is static
     */
    private void handleField(Field field, Object instance) {
        Message annotation = field.getAnnotation(Message.class);
        String path = annotation.path();
        String defaultMessage = annotation.defaultMessage();

        String message = fileManager.getConfig().getString(path, defaultMessage);
        fileManager.getConfig().set(path, message);

        try {
            field.setAccessible(true);
            field.set(instance, message);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
