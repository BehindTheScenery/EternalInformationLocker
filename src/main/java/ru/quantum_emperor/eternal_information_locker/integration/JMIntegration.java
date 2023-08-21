package ru.quantum_emperor.eternal_information_locker.integration;

import journeymap.client.ui.UIManager;
import journeymap.client.waypoint.Waypoint;
import journeymap.client.waypoint.WaypointStore;

import java.util.Collection;

import static ru.quantum_emperor.eternal_information_locker.EternalInformationLocker.LOGGER;
public class JMIntegration {
    public static void closeMinimap() {
        UIManager.INSTANCE.setMiniMapEnabled(false);
    }

    public static void toggleAllWaypoints(boolean state) {
        Collection<Waypoint> waypoints = WaypointStore.INSTANCE.getAll();
        for (Waypoint waypoint : waypoints) {
            waypoint.setEnable(state);
            waypoint.setDirty();
        }

        WaypointStore.INSTANCE.bulkSave();
    }
}
