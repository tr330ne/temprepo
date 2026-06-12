package org.rusherhack.client.api.system.waypoint;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.world.phys.Vec3;
import org.rusherhack.client.api.utils.objects.Dimension;
import org.rusherhack.core.interfaces.INamed;
import org.rusherhack.core.serialize.JsonSerializable;

public class Waypoint implements INamed, JsonSerializable {
   private String name;
   private String server;
   private Vec3 pos;
   private Dimension dimension;
   private boolean enabled = true;

   public Waypoint(String name, String server, Vec3 pos, Dimension dimension) {
      this.name = name;
      this.server = server;
      this.pos = pos;
      this.dimension = dimension;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setServer(String server) {
      this.server = server;
   }

   public void setPos(Vec3 pos) {
      this.pos = pos;
   }

   public void setDimension(Dimension dimension) {
      this.dimension = dimension;
   }

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   @Override
   public String getName() {
      return this.name;
   }

   public String getServer() {
      return this.server;
   }

   public Vec3 getPos() {
      return this.pos;
   }

   public Dimension getDimension() {
      return this.dimension;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public JsonElement serialize() {
      JsonObject object = new JsonObject();
      object.addProperty("name", this.name);
      object.addProperty("server", this.server);
      object.addProperty("x", this.pos.x);
      object.addProperty("y", this.pos.y);
      object.addProperty("z", this.pos.z);
      object.addProperty("dimension", this.dimension.name());
      object.addProperty("enabled", this.enabled);
      return object;
   }

   public boolean deserialize(JsonElement obj) {
      return false;
   }
}
