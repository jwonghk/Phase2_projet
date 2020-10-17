package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a workroom having a collection of thingies
public class WorkRoom implements Writable {
    private String name;
    private List<Thingy> thingies;

    // EFFECTS: constructs workroom with a name and empty list of thingies
    public WorkRoom(String name) {
        this.name = name;
        thingies = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: adds thingy to this workroom
    public void addThingy(Thingy thingy) {
        thingies.add(thingy);
    }

    // EFFECTS: returns an unmodifiable list of thingies in this workroom
    public List<Thingy> getThingies() {
        return Collections.unmodifiableList(thingies);
    }

    // EFFECTS: returns number of thingies in this workroom
    public int numThingies() {
        return thingies.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("thingies", thingiesToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray thingiesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Thingy t : thingies) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}

