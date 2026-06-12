package org.rusherhack.client.api.ui.panel;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import org.rusherhack.client.api.render.IRenderable2D;
import org.rusherhack.client.api.render.IRenderer2D;
import org.rusherhack.client.api.render.font.IFontRenderer;
import org.rusherhack.client.api.ui.ScaledElementBase;
import org.rusherhack.core.feature.IFeature;
import org.rusherhack.core.interfaces.IClickable;
import org.rusherhack.core.interfaces.IHideable;
import org.rusherhack.core.interfaces.INamed;
import org.rusherhack.core.interfaces.IReferenceable;
import org.rusherhack.core.interfaces.IScrollable;
import org.rusherhack.core.interfaces.ITickable;
import org.rusherhack.core.interfaces.ITypeable;
import org.rusherhack.core.serialize.JsonSerializable;

public abstract class PanelBase<T extends IPanelItem>
   extends ScaledElementBase
   implements IRenderable2D,
   ITickable,
   IClickable,
   IScrollable,
   ITypeable,
   IHideable,
   JsonSerializable,
   INamed,
   IReferenceable {
   private final String name;
   private String displayName;
   private boolean visible = true;
   protected final List<T> items = new ArrayList<>();
   protected final PanelHandlerBase handler;

   public PanelBase(PanelHandlerBase<? extends PanelBase<T>> handler, String name) {
      this.name = this.displayName = name;
      this.handler = handler;
   }

   public abstract T createFeatureItem(IFeature var1);

   public T addItem(T item) {
      this.items.add(item);
      return item;
   }

   public T addItem(IFeature feature) {
      return this.addItem(this.createFeatureItem(feature));
   }

   public List<T> getItemList() {
      return this.items;
   }

   @Override
   public String getName() {
      return this.name;
   }

   @Override
   public String getDisplayName() {
      return this.displayName;
   }

   public void setDisplayName(String displayName) {
      this.displayName = displayName;
   }

   public PanelHandlerBase getPanelHandler() {
      return this.handler;
   }

   @Override
   public boolean isHidden() {
      return !this.visible;
   }

   public void setVisible(boolean visible) {
      this.visible = visible;
   }

   @Override
   public IRenderer2D getRenderer() {
      return this.handler.getRenderer();
   }

   @Override
   public IFontRenderer getFontRenderer() {
      return this.handler.getFontRenderer();
   }

   public JsonElement serialize() {
      JsonObject panelObj = new JsonObject();
      panelObj.addProperty("name", this.getName());
      panelObj.addProperty("x", this.getX());
      panelObj.addProperty("y", this.getY());
      panelObj.addProperty("visible", !this.isHidden());
      return panelObj;
   }

   public boolean deserialize(JsonElement obj) {
      if (obj instanceof JsonObject panelObj && panelObj.has("name") && panelObj.get("name").getAsString().equalsIgnoreCase(this.getName())) {
         if (panelObj.has("x") && panelObj.has("y")) {
            double x = panelObj.get("x").getAsDouble();
            double y = panelObj.get("y").getAsDouble();
            this.setX(x);
            this.setY(y);
         }

         if (panelObj.has("visible")) {
            this.setVisible(panelObj.get("visible").getAsBoolean());
         }

         return true;
      } else {
         return false;
      }
   }

   public void sort() {
   }

   @Override
   public String getReferenceKey() {
      return String.format("ui.panel.%s", this.getName().replace(" ", "_").toLowerCase());
   }
}
