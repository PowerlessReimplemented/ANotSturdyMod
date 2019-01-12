package powerlessri.anotsturdymod.library.gui.simpleimpl.events;

public class HoveringEvent {

    public static class Enter {

    }

    public static class Leave {

    }

    public static class Update {
        private long ticksOn;

        public void nextTick() {
            ++ticksOn;
        }

        public Update reset() {
            ticksOn = 0L;
            return this;
        }
    }

}
