# ItemBuilder
*ItemBuilder is a small framework specialized in making the process of creating custom items easier.*

*Currently the framework works for any version of Minecraft higher or equal to 1.14 .*

# Maven Dependency
In order to add the framework as a dependency, you'll firstly need to add the JitPack repository to your pom.xml:

```xml
  <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>
```

*Next, add the dependency to your pom.xml:*

```xml
  <dependency>
      <groupId>com.github.Cristike</groupId>
      <artifactId>ItemBuilder</artifactId>
      <version>VERSION</version>
  </dependency>
```

# Usage 
*In this section we will be creating a custom potato. It will have a custom name, lore and an enchant.*  
*To get started, we will consider the following to be the contents of the file config.yml:*
```yaml
  Potato:
    Display-Name: '&e&lSharp Potato'
    Lore:
      - '&7This potato has a sharp edge.'
      - '&7Be careful when you are holding it!'
```
*Now we can start creating the new item.*  
*'config' is the default configuration*

```java
  String displayName = config.getString("Potato.Display-Name");
  List<String> lore = config.getStringList("Potato.Lore");
  
  ItemBuilder builder = new ItemBuilder(Material.POTATO, displayName, lore);
```

*Currently, if we would build the item, the color codes from the name and lore wouldn't work. We can solve this problem by either using another*  
*constructor, or by using the following functions: 
[colorDisplayName()](https://github.com/Cristike/ItemBuilder/blob/a9d617fe9a8bf7ccac4bb623bc73201540e82568/src/main/java/me/cristike/itembuilder/ItemBuilder.java#L166)
and [colorLore()](https://github.com/Cristike/ItemBuilder/blob/a9d617fe9a8bf7ccac4bb623bc73201540e82568/src/main/java/me/cristike/itembuilder/ItemBuilder.java#L215).*  

*We will stick with the first method, and modify the code from above into this:*

```java
  String displayName = config.getString("Potato.Display-Name");
  List<String> lore = config.getStringList("Potato.Lore");
  
  ItemBuilder builder = new ItemBuilder(Material.POTATO, displayName, lore, true);
```

*There is one thing left to do now: add a custom enchant, Sharpness V to be more exact. For this we can use the
[addEnchant()](https://github.com/Cristike/ItemBuilder/blob/a9d617fe9a8bf7ccac4bb623bc73201540e82568/src/main/java/me/cristike/itembuilder/ItemBuilder.java#L301) 
function:*

```java
  builder.addEnchant(Enchantment.DAMAGE_ALL, 5);
```

*Finally, we can build the item and add it to the inventory of a player p:*

```java
   ItemStack item = builder.build();
   p.getInventory().addItem(item);
```

*The final result is this:*  
![](https://cdn.discordapp.com/attachments/957707270228168835/984485955182415902/unknown.png)
