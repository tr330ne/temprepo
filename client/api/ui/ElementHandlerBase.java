package org.rusherhack.client.api.ui;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import org.rusherhack.client.api.Globals;
import org.rusherhack.client.api.render.IRenderable2D;
import org.rusherhack.client.api.render.IRenderer2D;
import org.rusherhack.client.api.render.RenderContext;
import org.rusherhack.core.interfaces.IClickable;
import org.rusherhack.core.interfaces.IDraggable;
import org.rusherhack.core.interfaces.IHideable;
import org.rusherhack.core.interfaces.INamed;
import org.rusherhack.core.interfaces.IScrollable;
import org.rusherhack.core.interfaces.ITickable;
import org.rusherhack.core.interfaces.IToggleable;
import org.rusherhack.core.interfaces.ITypeable;
import org.rusherhack.core.serialize.JsonSerializable;

public abstract class ElementHandlerBase<T extends ElementBase>
   implements Globals,
   IRenderable2D,
   IDraggable,
   IScrollable,
   ITypeable,
   ITickable,
   JsonSerializable {
   private final boolean scaledWithMinecraftGui;
   public boolean positionsInitialized = false;

   public ElementHandlerBase(boolean scaledWithMinecraftGui) {
      this.scaledWithMinecraftGui = scaledWithMinecraftGui;
   }

   public abstract List<T> getElements();

   public Optional<T> getElement(String name) {
      for (T element : this.getElements()) {
         if (element instanceof INamed named && named.getName().equalsIgnoreCase(name)) {
            return Optional.of(element);
         }
      }

      return Optional.empty();
   }

   public abstract void initialize();

   public abstract void setDefaultPositions();

   public abstract void moveElementToTop(T var1);

   @Override
   public void render(RenderContext context, double mouseX, double mouseY) {
      PoseStack matrixStack = context.pose();
      float scale = this.getScale();
      mouseX /= scale;
      mouseY /= scale;
      matrixStack.pushPose();
      matrixStack.translate(0.0F, 0.0F, 200.0F);
      matrixStack.scale(scale, scale, 1.0F);
      this.renderElements(context, mouseX, mouseY);
      matrixStack.popPose();
   }

   public void renderElements(RenderContext renderContext, double mouseX, double mouseY) {
      PoseStack matrixStack = renderContext.pose();
      IRenderer2D renderer = this.getRenderer();
      boolean building = renderer.isBuilding();
      if (!building) {
         renderer.begin(matrixStack, this.getFontRenderer());
      }

      for (T element : this.getElements()) {
         if (this.isEnabled(element)) {
            this.renderElement(element, renderContext, mouseX, mouseY);
         }
      }

      if (!building) {
         renderer.end();
      }
   }

   @Override
   public void tick() {
      for (T element : this.getElements()) {
         if (this.isEnabled(element) && element instanceof ITickable tickable) {
            tickable.tick();
         }
      }
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      float scale = this.getScale();
      mouseX /= scale;
      mouseY /= scale;
      return this.consumeMouseClick(mouseX, mouseY, button);
   }

   @Override
   public void mouseReleased(double mouseX, double mouseY, int button) {
      float scale = this.getScale();
      mouseX /= scale;
      mouseY /= scale;
      this.consumeMouseRelease(mouseX, mouseY, button);
   }

   @Override
   public void mouseMoved(double mouseX, double mouseY) {
      float scale = this.getScale();
      mouseX /= scale;
      mouseY /= scale;
      this.consumeMouseMove(mouseX, mouseY);
   }

   @Override
   public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
      float scale = this.getScale();
      mouseX /= scale;
      mouseY /= scale;
      return this.consumeMouseScroll(mouseX, mouseY, delta);
   }

   @Override
   public boolean charTyped(char character) {
      List<T> elements = this.getElements();
      ListIterator<T> iterator = elements.listIterator(elements.size());
      boolean consumed = false;

      while (iterator.hasPrevious()) {
         T element = iterator.previous();
         if (this.isEnabled(element) && element instanceof ITypeable typeable && this.consumeElementCharTyped(element, character)) {
            consumed = true;
            break;
         }
      }

      return consumed;
   }

   @Override
   public boolean keyTyped(int key, int scanCode, int modifiers) {
      List<T> elements = this.getElements();
      ListIterator<T> iterator = elements.listIterator(elements.size());
      boolean consumed = false;

      while (iterator.hasPrevious()) {
         T element = iterator.previous();
         if (this.isEnabled(element) && element instanceof ITypeable typeable && this.consumeElementKeyTyped(element, key, scanCode, modifiers)) {
            consumed = true;
            break;
         }
      }

      return consumed;
   }

   @Override
   public boolean isHovered(double mouseX, double mouseY) {
      for (T element : this.getElements()) {
         if (this.isEnabled(element) && element instanceof IClickable clickable && clickable.isHovered(mouseX, mouseY)) {
            return true;
         }
      }

      return false;
   }

   protected void renderElement(T element, RenderContext context, double mouseX, double mouseY) {
      if (element instanceof IRenderable2D renderable) {
         PoseStack matrixStack = context.pose();
         matrixStack.pushPose();
         matrixStack.translate(element.getX(), element.getY(), 0.0);
         renderable.render(context, mouseX, mouseY);
         matrixStack.popPose();
      }
   }

   protected boolean consumeMouseClick(double mouseX, double mouseY, int button) {
      List<T> elements = this.getElements();
      ListIterator<T> iterator = elements.listIterator(elements.size());

      while (iterator.hasPrevious()) {
         T element = iterator.previous();
         if (this.isEnabled(element) && element instanceof IClickable clickable && this.consumeElementMouseClick(element, mouseX, mouseY, button)) {
            this.moveElementToTop(element);
            return true;
         }
      }

      return false;
   }

   protected boolean consumeElementMouseClick(T element, double mouseX, double mouseY, int button) {
      return element instanceof IClickable clickable ? clickable.mouseClicked(mouseX, mouseY, button) : false;
   }

   protected void consumeMouseRelease(double mouseX, double mouseY, int button) {
      for (T element : this.getElements()) {
         if (element instanceof IClickable clickable && this.consumeElementMouseRelease(element, mouseX, mouseY, button)) {
            break;
         }
      }
   }

   protected boolean consumeElementMouseRelease(T element, double mouseX, double mouseY, int button) {
      if (element instanceof IClickable clickable) {
         clickable.mouseReleased(mouseX, mouseY, button);
      }

      return false;
   }

   protected void consumeMouseMove(double mouseX, double mouseY) {
      for (T element : this.getElements()) {
         if (this.isEnabled(element) && element instanceof IDraggable && this.consumeElementMouseMove(element, mouseX, mouseY)) {
            break;
         }
      }
   }

   protected boolean consumeElementMouseMove(T element, double mouseX, double mouseY) {
      if (element instanceof IDraggable draggable) {
         draggable.mouseMoved(mouseX, mouseY);
      }

      return false;
   }

   protected boolean consumeMouseScroll(double mouseX, double mouseY, double delta) {
      List<T> elements = this.getElements();
      ListIterator<T> iterator = elements.listIterator(elements.size());

      while (iterator.hasPrevious()) {
         T element = iterator.previous();
         if (this.isEnabled(element) && element instanceof IScrollable && this.consumeElementMouseScroll(element, mouseX, mouseY, delta)) {
            return true;
         }
      }

      return false;
   }

   protected boolean consumeElementMouseScroll(T element, double mouseX, double mouseY, double delta) {
      return element instanceof IScrollable scrollable ? scrollable.mouseScrolled(mouseX, mouseY, delta) : false;
   }

   protected boolean consumeElementCharTyped(T element, char character) {
      return element instanceof ITypeable typeable ? typeable.charTyped(character) : false;
   }

   protected boolean consumeElementKeyTyped(T element, int key, int scanCode, int modifiers) {
      return element instanceof ITypeable typeable ? typeable.keyTyped(key, scanCode, modifiers) : false;
   }

   public float getScale() {
      return this.scaledWithMinecraftGui ? 1.0F : 2.0F / (float)mc.getWindow().getGuiScale();
   }

   protected boolean isEnabled(T element) {
      return element instanceof IToggleable toggleable && !toggleable.isToggled() ? false : !(element instanceof IHideable hideable && hideable.isHidden());
   }

   public boolean isElementHovered(T element, double mouseX, double mouseY) {
      double x = element.getX();
      double y = element.getY();
      double width = element instanceof ScaledElementBase scaled ? scaled.getScaledWidth() : element.getWidth();
      double height = element instanceof ScaledElementBase scaled ? scaled.getScaledHeight() : element.getHeight();
      return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
   }

   public JsonElement serialize() {
      JsonArray array = new JsonArray();

      for (T element : this.getElements()) {
         if (element instanceof JsonSerializable serializable && serializable.shouldSerialize(false)) {
            array.add(serializable.serialize());
         }
      }

      return array;
   }

   public boolean deserialize(JsonElement obj) {
      for (JsonElement serializedElement : obj.getAsJsonArray()) {
         if (serializedElement.isJsonObject()) {
            JsonObject object = serializedElement.getAsJsonObject();
            if (object.has("name")) {
               String name = object.get("name").getAsString();
               Optional<T> e = this.getElement(name);
               e.ifPresentOrElse(element -> {
                  if (element instanceof JsonSerializable serializable) {
                     serializable.deserialize(object);
                     this.moveElementToTop((T)element);
                  }
               }, () -> {});
            }
         }
      }

      this.positionsInitialized = true;
      return true;
   }

   @Override
   public boolean shouldSerialize(boolean autosave) {
      return this.positionsInitialized;
   }
}
