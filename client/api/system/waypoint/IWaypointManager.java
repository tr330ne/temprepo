package org.rusherhack.client.api.system.waypoint;

import java.util.List;

public interface IWaypointManager {
   void addWaypoint(Waypoint var1);

   void removeWaypoint(Waypoint var1);

   List<Waypoint> getWaypoints();

   Waypoint getWaypointForCurrentServer(String var1);

   List<Waypoint> getWaypointsForServer(String var1);
}
