package persistence;

import org.json.JSONObject;

/**
 * Writable is to create a method that returns JSON object for subclasses.
 * NOTE: this class is from JsonSerializationDemo.
 */
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
