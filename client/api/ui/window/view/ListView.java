package org.rusherhack.client.api.ui.window.view;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import org.jetbrains.annotations.Nullable;
import org.rusherhack.client.api.feature.window.Window;
import org.rusherhack.client.api.ui.window.content.ListItemContent;
import org.rusherhack.core.interfaces.INamed;

public class ListView<T extends ListItemContent> extends WindowView {
   protected final ListView<T>.ListItemView itemView;
   protected final List<T> items;
   protected final List<ListView<T>.Column> columns = new ArrayList<>();
   @Nullable
   protected ListView.Column sortColumn = null;
   protected boolean sortAscending = true;
   protected T selectedItem = (T)null;
   protected Consumer<T> deleteCallback = null;

   public ListView(String name, Window window, List<T> items) {
      super(name, window, new ArrayList<>());
      this.items = items;
      this.itemView = new ListView.ListItemView(window, this, this.items);
      this.add(this.itemView);
   }

   @Override
   public void renderViewContent(double mouseX, double mouseY) {
      this.getViewHandler().handleRenderViewContent(this.itemView, mouseX, mouseY);
   }

   public void sortByColumn(ListView<?>.Column column) {
      if (this.sortColumn == column) {
         this.sortAscending = !this.sortAscending;
      } else {
         this.sortColumn = column;
         this.sortAscending = true;
      }

      this.resort();
   }

   public void resort() {
      if (this.sortColumn != null) {
         Comparator<T> comparator = this.sortColumn.getComparator();
         if (this.sortAscending) {
            this.items.sort(comparator);
         } else {
            this.items.sort(comparator.reversed());
         }
      }
   }

   public List<ListView<T>.Column> getColumns() {
      return this.columns;
   }

   public double getColumnWidth(ListView<?>.Column column) {
      return column.widthWeight / this.getColumns().stream().mapToDouble(c -> c.widthWeight).sum() * this.getViewWidth();
   }

   @Nullable
   public ListView.Column getSortColumn() {
      return this.sortColumn;
   }

   public boolean isSortAscending() {
      return this.sortAscending;
   }

   @Nullable
   public T getSelectedItem() {
      return this.selectedItem;
   }

   public void setSelectedItem(ListItemContent selectedItem) {
      this.selectedItem = (T)selectedItem;
   }

   public ListView<T>.Column addColumn(String name) {
      ListView<T>.Column column = new ListView.Column(name);
      this.columns.add(column);
      return column;
   }

   public ListView<T>.Column addColumn(String name, double widthWeight) {
      ListView<T>.Column column = new ListView.Column(name, widthWeight);
      this.columns.add(column);
      return column;
   }

   public ListView<T>.Column addColumn(String name, Comparator<T> comparator, double widthWeight) {
      ListView<T>.Column column = new ListView.Column(name, comparator, widthWeight);
      this.columns.add(column);
      return column;
   }

   public ListView<T>.ListItemView getItemView() {
      return this.itemView;
   }

   public void setDeleteCallback(Consumer<T> deleteCallback) {
      this.deleteCallback = deleteCallback;
   }

   public class Column implements INamed {
      private final String name;
      private Comparator<T> comparator;
      private final double widthWeight;

      public Column(String name) {
         this(name, 1.0);
      }

      public Column(String name, double widthWeight) {
         this(name, null, widthWeight);
         this.setComparator(Comparator.comparing(item -> item.getAsString(this)));
      }

      public Column(String name, Comparator<T> comparator, double widthWeight) {
         this.name = name;
         this.comparator = comparator;
         this.widthWeight = widthWeight;
      }

      @Override
      public String getName() {
         return this.name;
      }

      public Comparator<T> getComparator() {
         return this.comparator;
      }

      public void setComparator(Comparator<T> comparator) {
         this.comparator = comparator;
      }
   }

   public class ListItemView extends ScrollableView {
      private final ListView<T> listView;

      public ListItemView(Window window, ListView<T> listView, List<T> items) {
         super(window, items);
         this.listView = listView;
         this.contentPadding = this.topPadding = this.leftPadding = 0.0;
      }

      @Override
      public boolean keyTyped(int key, int scanCode, int modifiers) {
         if (super.keyTyped(key, scanCode, modifiers)) {
            return true;
         }

         if (this.getWindow().isFocused() && this.listView.getSelectedItem() != null) {
            switch (key) {
               case 261:
                  this.remove(this.listView.getSelectedItem());
                  if (ListView.this.deleteCallback != null) {
                     ListView.this.deleteCallback.accept(this.listView.getSelectedItem());
                  }

                  return true;
               case 262:
               case 263:
               default:
                  break;
               case 264:
                  int index = this.getContent().indexOf(this.listView.getSelectedItem());
                  if (index < this.getContent().size() - 1) {
                     this.listView.setSelectedItem((ListItemContent)this.getContent().get(index + 1));
                  }

                  return true;
               case 265:
                  int index = this.getContent().indexOf(this.listView.getSelectedItem());
                  if (index > 0) {
                     this.listView.setSelectedItem((ListItemContent)this.getContent().get(index - 1));
                  }

                  return true;
            }
         }

         return false;
      }

      public ListView<T> getListView() {
         return this.listView;
      }

      @Override
      public double getWidth() {
         return super.getWidth();
      }

      @Override
      public double getHeight() {
         return super.getHeight();
      }
   }
}
