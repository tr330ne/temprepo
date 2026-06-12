package org.rusherhack.client.api.utils.objects;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.rusherhack.core.serialize.JsonSerializable;

public record PlayerRelation(String username, String alias, PlayerRelation.State state) implements JsonSerializable {
   public JsonElement serialize() {
      JsonObject object = new JsonObject();
      object.addProperty("username", this.username);
      object.addProperty("alias", this.alias);
      object.addProperty("state", this.state.name());
      return object;
   }

   public boolean deserialize(JsonElement jsonElement) {
      return false;
   }

   public enum State {
      FRIEND,
      ENEMY,
      NEUTRAL;
   }
}
