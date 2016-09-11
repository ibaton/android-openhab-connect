package se.treehou.ng.ohcommunicator.connector.serializers;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import se.treehou.ng.ohcommunicator.connector.models.OHThing;

public class ThingsDeserializer implements JsonDeserializer<List<OHThing>> {

    private static final String TAG = "WidgetMappingDeserializer";

    @Override
    public List<OHThing> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context){

        List<OHThing> things = new ArrayList<>();

        if(json.isJsonArray()){
            JsonArray jThings = json.getAsJsonArray();
            for(JsonElement e : jThings){
                OHThing entry = context.deserialize(e, OHThing.class);
                things.add(entry);
            }
        }

        return things;
    }
}
