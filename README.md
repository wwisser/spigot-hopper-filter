# Hopper Item Filter
Simple Spigot plugin which enables custom filtering of a given set of items for hoppers.

## Edge Cases

- This plugin is made for moving items between hoppers and chests and the pickup mechanism. If the player manipulates
  the contents of the hopper, items could get stuck. In case the requirements do not allow this, a more complex approach
  is necessary.
- Concurrent hopper filter views are currently not supported
- If a player's rank exceeds and items are locked, he needs to break the item hopper to get them back
- Hopper filters are not player bound, access needs to be considered based on environment (e.g. city plot world). This
  also means players can bypass the locked slots by asking other players with a higher rank to fill them.
- Data integrity is not guaranteed in this version, filters are only deleted if the associated block is broken by a
  player.