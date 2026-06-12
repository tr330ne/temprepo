package org.rusherhack.core.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.rusherhack.core.feature.IFeature;
import org.rusherhack.core.interfaces.IHideable;
import org.rusherhack.core.utils.StringUtils;

public abstract class AbstractCommand implements IFeature, IHideable {
   private final String name;
   private final String description;
   private final List<String> aliases = new ArrayList<>();
   private final AbstractCommand parent;
   private final List<AbstractCommand> subCommands = new ArrayList<>();

   public AbstractCommand(String name, String description) {
      this(null, name, description);
   }

   public AbstractCommand(AbstractCommand parent, String name, String description) {
      this.parent = parent;
      this.name = name;
      this.description = description;
      this.addAliases(name);
   }

   public void addAliases(String... aliases) {
      this.aliases.addAll(Arrays.asList(aliases));
   }

   public void registerSubCommand(AbstractCommand command) {
      this.subCommands.add(command);
   }

   @Override
   public String getName() {
      return this.name;
   }

   @Override
   public String getDisplayName() {
      return StringUtils.toTitleCase(this.name);
   }

   @Override
   public String getDescription() {
      return this.description;
   }

   @Override
   public String[] getAliases() {
      return this.aliases.toArray(new String[0]);
   }

   public AbstractCommand getParent() {
      return this.parent;
   }

   public List<AbstractCommand> getSubCommands() {
      return this.subCommands;
   }

   @Override
   public boolean isHidden() {
      return false;
   }

   @Override
   public String getReferenceKey() {
      return "feature.command." + IFeature.super.getReferenceKey();
   }
}
