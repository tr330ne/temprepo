package org.rusherhack.client.api.feature.hud;

import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.network.chat.Component;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.render.IRenderer2D;
import org.rusherhack.client.api.render.RenderContext;
import org.rusherhack.client.api.render.font.IFontRenderer;
import org.rusherhack.client.api.setting.ColorSetting;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.core.animation.Animation;
import org.rusherhack.core.animation.Easing;
import org.rusherhack.core.setting.BooleanSetting;
import org.rusherhack.core.setting.EnumSetting;
import org.rusherhack.core.utils.ColorUtils;

public abstract class ListHudElement extends HudElement {
   private double width;
   private double height;
   private List<String> cache = new ArrayList<>();
   protected List<ListHudElement.ListItem> members = new ArrayList<>();
   private final boolean sorting;
   private int max = Integer.MAX_VALUE;
   protected final ColorSetting color = (new ColorSetting("Color", new Color(0, 100, 255)) {
         @Override
         public Color getValue() {
            return RusherHackAPI.getHudManager().isGlobalColorSynced()
               ? ColorUtils.transparency(RusherHackAPI.getHudManager().getSecondaryColor(), this.getAlpha())
               : super.getValue();
         }

         @Override
         public boolean isThemeSync() {
            return RusherHackAPI.getHudManager().isThemeColorSynced() ? true : super.isThemeSync();
         }
      })
      .setVisibility(() -> !RusherHackAPI.getHudManager().isThemeColorSynced())
      .setAlphaAllowed(false);
   protected final EnumSetting<ListHudElement.ColorMode> colorMode = new EnumSetting<>("ColorMode", ListHudElement.ColorMode.DYNAMIC)
      .setVisibility(this.color::isRainbow);
   protected final EnumSetting<ListHudElement.SortingMode> sortMode = new EnumSetting<>("Sorting", ListHudElement.SortingMode.LEXICOGRAPHICAL)
      .onChange(this::resort);
   protected final BooleanSetting animations = new BooleanSetting("Animations", true);

   public ListHudElement(String name) {
      this(name, true);
   }

   public ListHudElement(String name, boolean sorting) {
      super(name);
      this.sorting = sorting;
      this.registerSettings(this.color, this.animations, this.colorMode);
      if (sorting) {
         this.registerSettings(this.sortMode);
      }
   }

   @Override
   public void renderContent(RenderContext context, double mouseX, double mouseY) {
      if (!this.members.isEmpty()) {
         this.renderList(context);
      } else {
         if (mc.screen != null && mc.screen.equals(RusherHackAPI.getThemeManager().getHudEditorScreen())) {
            String emptyString = "(empty list)";
            IFontRenderer fr = this.getFontRenderer();
            double width = fr.getStringWidth("(empty list)") + 2.0;
            double height = fr.getFontHeight() + 2.0;
            fr.drawString("(empty list)", 1.0, 1.0, this.color.getValueRGB());
            this.width = width;
            this.height = height;
         } else {
            this.height = 0.0;
         }
      }
   }

   protected void renderList(RenderContext renderContext) {
      PoseStack matrixStack = renderContext.pose();
      HudElement.Alignment alignment = this.getAlignment();
      boolean bottom = alignment != null && (alignment.equals(HudElement.Alignment.BOTTOM_LEFT) || alignment.equals(HudElement.Alignment.BOTTOM_RIGHT));
      double xOffset = 1.0;
      double yOffset = 1.0;
      double maxWidth = 0.0;

      for (int i = 0; i < this.members.size(); i++) {
         ListHudElement.ListItem member = this.members.get(i);
         double itemWidth = member.getWidth();
         double width = itemWidth * (this.animations.getValue() ? member.getAnimation().get() : 1.0);
         if (width > maxWidth) {
            maxWidth = width;
         }

         if (alignment != null) {
            xOffset = switch (alignment) {
               case TOP_LEFT, BOTTOM_LEFT -> -(itemWidth - width) + 1.0;
               case TOP_RIGHT, BOTTOM_RIGHT -> this.getWidth() - width - 1.0;
               case TOP_CENTER -> this.getWidth() / 2.0 - width / 2.0;
            };
         }

         matrixStack.pushPose();
         matrixStack.translate(xOffset, bottom ? this.getHeight() - yOffset - member.getHeight() : yOffset, 0.0);
         member.render(renderContext, i);
         matrixStack.popPose();
         yOffset += member.getHeight() + 1.0;
      }

      double newWidth = Math.max(maxWidth, 10.0) + 2.0;
      double newHeight = Math.max(Math.abs(yOffset), 10.0);
      this.width = newWidth;
      this.height = newHeight;
   }

   @Override
   public void tick() {
      for (ListHudElement.ListItem member : this.members) {
         member.tick();
      }

      this.members.removeIf(ListHudElement.ListItem::readyForRemoval);

      for (int i = 0; i < this.members.size(); i++) {
         if (i > this.max) {
            this.members.remove(i);
            i--;
         }
      }

      ArrayList<String> strings = new ArrayList<>();

      for (ListHudElement.ListItem member : this.members) {
         strings.add(member.getTextCached().getString());
      }

      if (!strings.equals(this.cache)) {
         this.cache = strings;
         if (!this.shouldAutomaticallyResort()) {
            return;
         }

         this.resort();
      }
   }

   public void resort() {
      Comparator<? super ListHudElement.ListItem> comparator = this.getComparator();
      if (comparator != null) {
         this.members.sort(comparator);
      }
   }

   public void setMax(int max) {
      this.max = max;
   }

   public void add(ListHudElement.ListItem member) {
      this.members.add(member);
      member.tick();
      this.resort();
   }

   @Override
   public double getWidth() {
      return !(this.width <= 0.0) || mc.player != null && mc.level != null ? this.width : this.getFontRenderer().getStringWidth(this.getDisplayName());
   }

   @Override
   public double getHeight() {
      return !(this.height <= 0.0) || mc.player != null && mc.level != null ? this.height : this.getFontRenderer().getFontHeight() + 2.0;
   }

   public List<ListHudElement.ListItem> getMembers() {
      return this.members;
   }

   public Comparator<? super ListHudElement.ListItem> getComparator() {
      return this.sorting ? this.sortMode.getValue().comparator : null;
   }

   protected boolean shouldAutomaticallyResort() {
      return this.getComparator() != null;
   }

   @Override
   public boolean shouldSkipPostRender() {
      return true;
   }

   public enum ColorMode {
      DYNAMIC,
      STATIC;
   }

   public abstract class ListItem {
      private final ListHudElement parent;
      private final Animation animation = new Animation(Easing.LINEAR, ListHudElement.this.animations.getValue() ? 200L : 1L);
      private boolean markedForRemoval = false;
      private Component cachedComponent = null;
      private double cachedWidth = -1.0;

      public ListItem(ListHudElement parent) {
         this.parent = parent;
         this.animation.play();
      }

      public abstract Component getText();

      public abstract boolean shouldRemove();

      private Component getTextCached() {
         if (this.cachedComponent == null) {
            this.cachedComponent = this.getText();
         }

         return this.cachedComponent;
      }

      private boolean readyForRemoval() {
         return this.markedForRemoval && (!ListHudElement.this.animations.getValue() || !this.animation.isPlaying());
      }

      public double getHeight() {
         return this.getFontRenderer().getFontHeight();
      }

      public double getWidth() {
         return this.cachedWidth != -1.0 ? this.cachedWidth : this.getFontRenderer().getStringWidth(this.getTextCached().getString());
      }

      public void render(RenderContext renderContext, int indexInList) {
         this.cachedWidth = this.getFontRenderer().drawString(this.getTextCached(), 0.0, 0.0, this.getColor(indexInList));
      }

      public void tick() {
         this.cachedComponent = this.getText();
         double progress = this.animation.get();
         if (this.shouldRemove()) {
            this.markedForRemoval = true;
            if (progress == 1.0 || this.animation.isPlaying()) {
               this.animation.setDirection(true).play();
            }
         } else {
            if (progress == 0.0 || this.animation.isReversed() || this.animation.isPlaying()) {
               this.animation.setDirection(false).play();
            }

            this.markedForRemoval = false;
         }
      }

      @Override
      public String toString() {
         return ChatUtils.stripFormatting(this.getTextCached().getString());
      }

      @Override
      public boolean equals(Object obj) {
         return obj instanceof ListHudElement.ListItem item && this.getText().equals(item.getText());
      }

      public int getColor(int index) {
         return !ListHudElement.this.colorMode.getValue().equals(ListHudElement.ColorMode.STATIC) && ListHudElement.this.color.isRainbow()
            ? ListHudElement.this.color.getRainbowWithOffset(index * 100)
            : ListHudElement.this.color.getValueRGB();
      }

      public Animation getAnimation() {
         return this.animation;
      }

      public IRenderer2D getRenderer() {
         return this.parent.getRenderer();
      }

      public IFontRenderer getFontRenderer() {
         return this.parent.getFontRenderer();
      }

      public ListHudElement getParent() {
         return this.parent;
      }
   }

   public enum SortingMode {
      LEXICOGRAPHICAL(Comparator.comparingDouble(m -> -m.getWidth())),
      ALPHABETICAL(Comparator.comparing(ListHudElement.ListItem::toString));

      public final Comparator<ListHudElement.ListItem> comparator;

      SortingMode(Comparator<ListHudElement.ListItem> comparator) {
         this.comparator = comparator;
      }
   }
}
