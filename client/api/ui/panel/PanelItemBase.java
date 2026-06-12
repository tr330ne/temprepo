package org.rusherhack.client.api.ui.panel;

import java.util.ArrayList;
import java.util.List;

public abstract class PanelItemBase<T extends IPanelItem> implements IPanelItem {
   protected final PanelBase<?> panel;
   protected final T parent;
   protected final List<T> subItems = new ArrayList<>();

   public PanelItemBase(PanelBase<?> panel, T parent) {
      this.panel = panel;
      this.parent = parent;
   }

   public void addSubItem(T item) {
      this.subItems.add(item);
   }

   public List<T> getSubItemList() {
      return this.subItems;
   }

   public PanelBase<?> getPanel() {
      return this.panel;
   }

   public T getParent() {
      return this.parent;
   }
}
